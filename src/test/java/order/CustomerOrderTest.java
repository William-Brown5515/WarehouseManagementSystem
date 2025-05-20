package order;

import main.financial.FinancialReport;
import main.order.CustomerOrder;
import main.order.OrderedProduct;
import main.products.InventoryManager;
import main.products.Product;
import main.products.ProductTypes;
import main.suppliers.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomerOrderTest {

    private InventoryManagerStub inventory;
    private CustomerOrder order;

    private Product product;

    @BeforeEach
    void setup() {
        inventory = new InventoryManagerStub();
        FinancialReport report = new FinancialReport();
        order = new CustomerOrder(report, inventory);
        Supplier supplier = new Supplier("Test Supplier", "test@email.com", "00000000000");
        product = new Product("TestProduct", 10, 8.0, 5.0, supplier, ProductTypes.CONSTRUCTION_MATERIAL);
        inventory.addProduct(product);
    }

    @Test
    void testAddItem_productNotFound_throws() {
        // Ensure a relevant exception is thrown when given an invalid product ID
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> order.addItem("invalidId", 1));
        assertEquals("Product not found", ex.getMessage());
    }

    @Test
    void testAddItem_insufficientStock_throws() {
        // Ensure a relevant exception is thrown when not enough stock available
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> order.addItem(product.getProductID(), 20));
        assertEquals("Insufficient stock for product " + product.getName(), ex.getMessage());
    }

    @Test
    void testAddItem_zeroOrNegativeQuantity_throws() {
        // Ensure a relevant exception is thrown when quantity is zero or negative
        IllegalArgumentException exZero = assertThrows(IllegalArgumentException.class,
                () -> order.addItem(product.getProductID(), 0));
        assertTrue(exZero.getMessage().contains("Negative quantity"));

        IllegalArgumentException exNegative = assertThrows(IllegalArgumentException.class,
                () -> order.addItem(product.getProductID(), -5));
        assertTrue(exNegative.getMessage().contains("Negative quantity"));
    }

    @Test
    void testAddItem_success() {
        // Ensure an item is successfully added to the order
        order.addItem(product.getProductID(), 3);

        // Check orderedProducts size
        assertEquals(1, order.getOrderedProducts().size());
        OrderedProduct orderedProduct = order.getOrderedProducts().getFirst();
        assertEquals(product, orderedProduct.getProduct());
        assertEquals(3, orderedProduct.getQuantity());

        // Check stock reduced in inventory stub
        assertEquals(7, inventory.getProductById(product.getProductID()).getQuantity());

        // Check total price updated (3 * 8.0)
        assertEquals(24.0, order.getTotalPrice(), 0.001);
    }

    @Test
    void testRecalculateTotalPrice_correctCalculation() {
        // Ensure that the order recalculates the total price correctly
        order.addOrderedProduct(new OrderedProduct(product, 2));
        order.addOrderedProduct(new OrderedProduct(product, 3));
        order.recalculateTotalPrice();

        assertEquals(40.0, order.getTotalPrice(), 0.001);
    }

    @Test
    void testCompleteOrder_updatesStateAndCallsReport() {
        // Ensure the order is completed and date noted
        order.completeOrder();

        assertEquals("Paid, being Delivered", order.getOrderStatus());
        assertNotNull(order.getOrderDate());
    }

    @Test
    void testDeliverOrder_updatesStatusAndDeliveredFlag() {
        // Ensures the correct attributes are updated when an order is delivered
        order.deliverOrder();

        assertTrue(order.isDelivered());
        assertEquals("Delivered", order.getOrderStatus());
    }

    // Stub class - to isolate the orderManager class for testing
    private static class InventoryManagerStub extends InventoryManager {
        private final Map<String, Product> products = new HashMap<>();

        public void addProduct(Product product) {
            products.put(product.getProductID(), product);
        }

        @Override
        public Product getProductById(String productID) {
            return products.get(productID);
        }

        @Override
        public void reduceStock(String productID, int amount) {
            Product product = products.get(productID);
            if (product != null) {
                product.setQuantity(product.getQuantity() - amount);
            }
        }

        @Override
        public void addStock(String productID, int amount) {
            Product product = products.get(productID);
            if (product != null) {
                product.setQuantity(product.getQuantity() + amount);
            }
        }
    }
}
