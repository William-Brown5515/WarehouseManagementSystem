package products;

import main.products.InventoryManager;
import main.products.Product;
import main.suppliers.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryManagerTest {

    private InventoryManager inventory;
    private Supplier supplier;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        inventory = new InventoryManager();
        supplier = new Supplier("Test Supplier", "test@supplier.com", "0123456789");
        product1 = new Product("Hammer", 50, 20.0, 10.0, supplier, "Tool");
        product2 = new Product("Screwdriver", 15, 15.0, 7.0, supplier, "Tool");
        inventory.addProduct(product1);
        inventory.addProduct(product2);
    }

    @Test
    void testAddProduct_null_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> inventory.addProduct(null));
    }

    @Test
    void testGetProductById_validId_returnsProduct() {
        Product found = inventory.getProductById(product1.getProductID());
        assertEquals(product1, found);
    }

    @Test
    void testGetProductById_invalidId_returnsNull() {
        assertNull(inventory.getProductById("INVALID_ID"));
    }

    @Test
    void testGetProductById_nullOrEmpty_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> inventory.getProductById(null));
        assertThrows(IllegalArgumentException.class, () -> inventory.getProductById(""));
        assertThrows(IllegalArgumentException.class, () -> inventory.getProductById("  "));
    }

    @Test
    void testRemoveProductById_removesProduct() {
        inventory.removeProductById(product1.getProductID());
        assertNull(inventory.getProductById(product1.getProductID()));
    }

    @Test
    void testRemoveProductById_nullOrEmpty_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> inventory.removeProductById(null));
        assertThrows(IllegalArgumentException.class, () -> inventory.removeProductById(""));
        assertThrows(IllegalArgumentException.class, () -> inventory.removeProductById("  "));
    }

    @Test
    void testAddStock_increasesQuantity() {
        int originalQty = product1.getQuantity();
        inventory.addStock(product1.getProductID(), 10);
        assertEquals(originalQty + 10, product1.getQuantity());
    }

    @Test
    void testAddStock_invalidInput_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> inventory.addStock(null, 10));
        assertThrows(IllegalArgumentException.class, () -> inventory.addStock("", 10));
        assertThrows(IllegalArgumentException.class, () -> inventory.addStock(product1.getProductID(), 0));
        assertThrows(IllegalArgumentException.class, () -> inventory.addStock(product1.getProductID(), -5));
    }

    @Test
    void testReduceStock_decreasesQuantity() {
        int originalQty = product1.getQuantity();
        inventory.reduceStock(product1.getProductID(), 10);
        assertEquals(originalQty - 10, product1.getQuantity());
    }

    @Test
    void testReduceStock_insufficientStock_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> inventory.reduceStock(product2.getProductID(), 100));
    }

    @Test
    void testReduceStock_productNotFound_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> inventory.reduceStock("INVALID_ID", 5));
    }

    @Test
    void testReduceStock_invalidInput_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> inventory.reduceStock(null, 5));
        assertThrows(IllegalArgumentException.class, () -> inventory.reduceStock("", 5));
        assertThrows(IllegalArgumentException.class, () -> inventory.reduceStock(product1.getProductID(), 0));
        assertThrows(IllegalArgumentException.class, () -> inventory.reduceStock(product1.getProductID(), -1));
    }

    @Test
    void testLowStock_returnsCorrectProducts() {
        List<Product> lowStock = inventory.lowStock();
        assertTrue(lowStock.contains(product2));
        assertFalse(lowStock.contains(product1));
    }

    @Test
    void testGetProductsBySupplier_returnsCorrectProducts() {
        List<Product> productsBySupplier = inventory.getProductsBySupplier(supplier);
        assertTrue(productsBySupplier.contains(product1));
        assertTrue(productsBySupplier.contains(product2));
    }
}
