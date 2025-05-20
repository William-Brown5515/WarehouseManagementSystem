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

        @Override
        public void addItem(String productId, int quantity) {
            // no-op for test
        }
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
        assertNotNull(order.getOrderID());
        assertEquals(0.0, order.getTotalPrice(), 0.001);
        assertEquals("Order in Progress", order.getOrderStatus());
        assertFalse(order.isDelivered());
        assertNull(order.getOrderDate());
        assertTrue(order.getOrderedProducts().isEmpty());
    }

    @Test
    void addOrderedProduct_addsProductAndRecalculatesPrice() {
        OrderedProduct op = new OrderedProduct(product, 2);
        order.addOrderedProduct(op);

        List<OrderedProduct> orderedProducts = order.getOrderedProducts();
        assertEquals(1, orderedProducts.size());
        assertEquals(product, orderedProducts.get(0).getProduct());
        assertEquals(2, orderedProducts.get(0).getQuantity());

        // totalPrice should be recalculated (default recalcTotalPrice does nothing, so totalPrice remains 0)
        assertEquals(0.0, order.getTotalPrice(), 0.001);
    }

    @Test
    void updateTotalPrice_setsNewPrice() {
        order.updateTotalPrice(100.0);
        assertEquals(100.0, order.getTotalPrice(), 0.001);
    }

    @Test
    void updateOrderDate_setsCurrentTime() {
        order.updateOrderDate();
        assertNotNull(order.getOrderDate());
        // Roughly check that orderDate is near now
        assertTrue(order.getOrderDate().isBefore(java.time.LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void updateOrderStatus_changesStatus() {
        order.updateOrderStatus("Completed");
        assertEquals("Completed", order.getOrderStatus());
    }

    @Test
    void updateDelivered_changesDeliveredFlag() {
        order.updateDelivered(true);
        assertTrue(order.isDelivered());

        order.updateDelivered(false);
        assertFalse(order.isDelivered());
    }

    @Test
    void changeProductQuantity_increasesQuantity_reducesStock() {
        // Add product to order with quantity 2
        order.addOrderedProduct(new OrderedProduct(product, 2));

        int initialQty = product.getQuantity();

        // Increase quantity to 5
        order.changeProductQuantity(product, 5);

        List<OrderedProduct> orderedProducts = order.getOrderedProducts();
        assertEquals(5, orderedProducts.get(0).getQuantity());

        // Stock should reduce by 3 (5-2)
        assertEquals(initialQty - 3, product.getQuantity());
    }

    @Test
    void changeProductQuantity_decreasesQuantity_addsStock() {
        order.addOrderedProduct(new OrderedProduct(product, 5));

        int initialQty = product.getQuantity();

        // Decrease quantity to 2
        order.changeProductQuantity(product, 2);

        List<OrderedProduct> orderedProducts = order.getOrderedProducts();
        assertEquals(2, orderedProducts.get(0).getQuantity());

        // Stock should increase by 3 (5-2)
        assertEquals(initialQty + 3, product.getQuantity());
    }

    @Test
    void changeProductQuantity_productNotInOrder_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> order.changeProductQuantity(product, 5));
        assertEquals("Product not found in the order", ex.getMessage());
    }

    @Test
    void removeItem_removesProductAndUpdatesStockAndPrice() {
        // Add product to order with quantity 3
        order.addOrderedProduct(new OrderedProduct(product, 3));
        int initialQty = product.getQuantity();

        order.removeItem(product.getProductID());

        assertTrue(order.getOrderedProducts().isEmpty());
        // Stock should be restored
        assertEquals(initialQty + 3, product.getQuantity());
        // total price should be recalculated (no products so 0)
        assertEquals(0.0, order.getTotalPrice(), 0.001);
    }

    @Test
    void removeItem_productNotInOrder_doesNothing() {
        // Removing a product not in the order should do nothing and not throw
        order.removeItem(product.getProductID());
        assertTrue(order.getOrderedProducts().isEmpty());
    }

    @Test
    void currentQuantity_returnsCorrectQuantity() {
        assertEquals(0, order.currentQuantity(product));

        order.addOrderedProduct(new OrderedProduct(product, 4));
        assertEquals(4, order.currentQuantity(product));
    }

    @Test
    void currentQuantity_nullProduct_returnsZero() {
        assertEquals(0, order.currentQuantity(null));
    }

    @Test
    void currentOrder_printsEmptyMessage_whenNoProducts() {
        // Since currentOrder prints to System.out, just call it (no exception expected)
        order.currentOrder();
    }

    @Test
    void currentOrder_printsProductInfo_whenHasProducts() {
        order.addOrderedProduct(new OrderedProduct(product, 2));
        order.currentOrder();
    }

    // Minimal stub for inventory management
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
