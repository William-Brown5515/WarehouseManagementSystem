package order;

import main.financial.FinancialReport;
import main.order.BaseOrder;
import main.order.OrderedProduct;
import main.products.InventoryManager;
import main.products.Product;
import main.products.ProductTypes;
import main.suppliers.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BaseOrderTest {

    private InventoryManagerStub inventory;
    private TestOrder order;
    private Product product;

    // A concrete subclass for testing abstract BaseOrder
    private static class TestOrder extends BaseOrder {
        public TestOrder(FinancialReport report, InventoryManager inventory) {
            super(report, inventory);
        }

        // Need placeholder method to override abstract method
        @Override
        public void addItem(String productId, int quantity) {}
    }

    @BeforeEach
    void setup() {
        inventory = new InventoryManagerStub();
        FinancialReport report = new FinancialReport();
        order = new TestOrder(report, inventory);

        Supplier supplier = new Supplier("Supplier", "supplier@test.com", "123456789");
        product = new Product("Product1", 10, 8.0, 5.0, supplier, ProductTypes.CONSTRUCTION_MATERIAL);
        inventory.addProduct(product);
    }

    @Test
    void constructor_initializesFields() {
        // Verify initial state of a new order is correct
        assertNotNull(order.getOrderID());
        assertEquals(0.0, order.getTotalPrice(), 0.001);
        assertEquals("Order in Progress", order.getOrderStatus());
        assertFalse(order.isDelivered());
        assertNull(order.getOrderDate());
        assertTrue(order.getOrderedProducts().isEmpty());
    }

    @Test
    void addOrderedProduct_addsProductAndRecalculatesPrice() {
        // Verify adding a product updates order contents and recalculates total price
        OrderedProduct op = new OrderedProduct(product, 2);
        order.addOrderedProduct(op);

        List<OrderedProduct> orderedProducts = order.getOrderedProducts();
        assertEquals(1, orderedProducts.size());
        assertEquals(product, orderedProducts.getFirst().getProduct());
        assertEquals(2, orderedProducts.getFirst().getQuantity());

        assertEquals(0.0, order.getTotalPrice(), 0.001);
    }

    @Test
    void updateDelivered_changesDeliveredFlag() {
        // Verify delivery status flag can be updated correctly
        order.updateDelivered(true);
        assertTrue(order.isDelivered());

        order.updateDelivered(false);
        assertFalse(order.isDelivered());
    }

    @Test
    void changeProductQuantity_increasesQuantity_reducesStock() {
        // Verify increasing product quantity reduces inventory stock accordingly
        order.addOrderedProduct(new OrderedProduct(product, 2));
        int initialQty = product.getQuantity();
        order.changeProductQuantity(product, 5);

        List<OrderedProduct> orderedProducts = order.getOrderedProducts();
        assertEquals(5, orderedProducts.getFirst().getQuantity());
        assertEquals(initialQty - 3, product.getQuantity());
    }

    @Test
    void changeProductQuantity_decreasesQuantity_addsStock() {
        // Verify decreasing product quantity adds stock back to inventory
        order.addOrderedProduct(new OrderedProduct(product, 5));
        int initialQty = product.getQuantity();
        order.changeProductQuantity(product, 2);

        List<OrderedProduct> orderedProducts = order.getOrderedProducts();
        assertEquals(2, orderedProducts.get(0).getQuantity());
        assertEquals(initialQty + 3, product.getQuantity());
    }

    @Test
    void changeProductQuantity_productNotInOrder_throws() {
        // Verify exception is thrown when changing quantity of a product not in order
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> order.changeProductQuantity(product, 5));
        assertEquals("Product not found in the order", ex.getMessage());
    }

    @Test
    void removeItem_removesProductAndUpdatesStockAndPrice() {
        // Verify removing product restores inventory stock and recalculates total price
        order.addOrderedProduct(new OrderedProduct(product, 3));
        int initialQty = product.getQuantity();

        order.removeItem(product.getProductID());

        assertTrue(order.getOrderedProducts().isEmpty());
        assertEquals(initialQty + 3, product.getQuantity());
        assertEquals(0.0, order.getTotalPrice(), 0.001);
    }

    @Test
    void removeItem_productNotInOrder_doesNothing() {
        // Verify removing a product not in the order does not throw or change state
        order.removeItem(product.getProductID());
        assertTrue(order.getOrderedProducts().isEmpty());
    }

    @Test
    void currentQuantity_returnsCorrectQuantity() {
        // Verify currentQuantity returns the correct quantity for a product in the order
        assertEquals(0, order.currentQuantity(product));

        order.addOrderedProduct(new OrderedProduct(product, 4));
        assertEquals(4, order.currentQuantity(product));
    }

    @Test
    void currentQuantity_nullProduct_returnsZero() {
        // Verify currentQuantity returns zero when given null product
        assertEquals(0, order.currentQuantity(null));
    }

    @Test
    void currentOrder_printsEmptyMessage_whenNoProducts() {
        // Verify currentOrder outputs appropriate message when order is empty
        order.currentOrder();
    }

    @Test
    void currentOrder_printsProductInfo_whenHasProducts() {
        // Verify currentOrder outputs product information when order has products
        order.addOrderedProduct(new OrderedProduct(product, 2));
        order.currentOrder();
    }

    // Using an inventory stub to isolate the BaseOrder class
    private static class InventoryManagerStub extends InventoryManager {
        private Product productInInventory;

        public void addProduct(Product p) {
            productInInventory = p;
        }

        @Override
        public Product getProductById(String productID) {
            if (productInInventory != null && productInInventory.getProductID().equals(productID)) {
                return productInInventory;
            }
            return null;
        }

        @Override
        public void reduceStock(String productID, int amount) {
            if (productInInventory != null && productInInventory.getProductID().equals(productID)) {
                productInInventory.setQuantity(productInInventory.getQuantity() - amount);
            }
        }

        @Override
        public void addStock(String productID, int amount) {
            if (productInInventory != null && productInInventory.getProductID().equals(productID)) {
                productInInventory.setQuantity(productInInventory.getQuantity() + amount);
            }
        }
    }
}
