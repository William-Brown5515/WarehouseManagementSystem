package order;

import main.financial.FinancialReport;
import main.order.BusinessOrder;
import main.order.OrderedProduct;
import main.products.InventoryManager;
import main.products.Product;
import main.products.ProductTypes;
import main.suppliers.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BusinessOrderTest {

    private InventoryManagerStub inventory;
    private BusinessOrder order;
    private Product product;

    @BeforeEach
    void setup() {
        inventory = new InventoryManagerStub();
        FinancialReport report = new FinancialReport();
        order = new BusinessOrder(report, inventory);

        Supplier supplier = new Supplier("Test Supplier", "test@example.com", "1234567890");
        product = new Product("Test Product", 10, 8.0, 5.0, supplier, ProductTypes.CONSTRUCTION_MATERIAL);
        inventory.addProduct(product);
    }

    @Test
    void addItem_throwsIfQuantityZeroOrLess() {
        // Verify addItem throws if quantity is zero or negative
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> order.addItem(product.getProductID(), 0));
        assertEquals("Quantity must be greater than 0", ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class,
                () -> order.addItem(product.getProductID(), -1));
        assertEquals("Quantity must be greater than 0", ex.getMessage());
    }

    @Test
    void addItem_throwsIfProductNotFound() {
        // Verify addItem throws if product ID not found in inventory
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> order.addItem("invalidId", 1));
        assertTrue(ex.getMessage().contains("Product with id"));
    }

    @Test
    void addItem_addsOrderedProductAndUpdatesTotal() {
        // Verify addItem adds product and updates total price correctly
        order.addItem(product.getProductID(), 3);

        assertEquals(1, order.getOrderedProducts().size());
        OrderedProduct op = order.getOrderedProducts().getFirst();
        assertEquals(product, op.getProduct());
        assertEquals(3, op.getQuantity());
        assertEquals(15.0, order.getTotalPrice(), 0.001);
    }

    @Test
    void recalculateTotalPrice_calculatesCorrectly() {
        // Verify total price calculation after adding multiple ordered products
        order.addOrderedProduct(new OrderedProduct(product, 2));
        order.addOrderedProduct(new OrderedProduct(product, 3));

        assertEquals(25.0, order.getTotalPrice(), 0.001);
    }

    @Test
    void completeOrder_setsStatusAndDate() {
        // Verify completeOrder sets order status and date properly
        order.completeOrder();

        assertEquals("Paid, being Delivered", order.getOrderStatus());
        assertNotNull(order.getOrderDate());
    }

    @Test
    void deliverOrder_updatesStockAndStatus() {
        // Verify deliverOrder marks order delivered, updates status, and adds stock back
        order.addItem(product.getProductID(), 2);

        int oldQty = product.getQuantity();

        order.deliverOrder();

        assertTrue(order.isDelivered());
        assertEquals("Delivered", order.getOrderStatus());
        assertEquals(oldQty + 2, product.getQuantity());
    }

    @Test
    void isArrived_returnsFalseIfOrderDateNull() {
        // Verify isArrived returns false if orderDate is null
        assertFalse(order.isArrived());
    }

    @Test
    void isArrived_returnsTrueIfOneMinutePassed() {
        // Verify isArrived returns true if more than one minute has passed since orderDate
        order.completeOrder();

        LocalDateTime past = LocalDateTime.now().minusMinutes(2);
        order.updateOrderDate(past);

        assertTrue(order.isArrived());
    }

    private static class InventoryManagerStub extends InventoryManager {
        private final Map<String, Product> products = new HashMap<>();

        public void addProduct(Product p) {
            products.put(p.getProductID(), p);
        }

        @Override
        public Product getProductById(String id) {
            return products.get(id);
        }

        @Override
        public void addStock(String id, int amount) {
            Product p = products.get(id);
            if (p != null) {
                p.setQuantity(p.getQuantity() + amount);
            }
        }
    }
}
