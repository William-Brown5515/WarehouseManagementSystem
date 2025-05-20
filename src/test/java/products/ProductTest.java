package products;

import main.products.Product;
import main.suppliers.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Supplier supplier;

    @BeforeEach
    void setUp() {
        supplier = new Supplier("Test Supplier", "test@example.com", "1234567890");
    }

    @Test
    void testConstructor_andGetters() {
        Product product = new Product("Widget", 10, 20.0, 15.0, supplier, "Construction");

        assertEquals("Widget", product.getName());
        assertNotNull(product.getProductID());
        assertEquals(10, product.getQuantity());
        assertEquals(20.0, product.getCustomerPrice(), 0.001);
        assertEquals(15.0, product.getSupplierPrice(), 0.001);
        assertEquals(supplier, product.getSupplier());
    }

    @Test
    void testSetName_updatesName() {
        Product product = new Product("Widget", 5, 10.0, 8.0, supplier, "Construction");
        product.setName("New Widget");
        assertEquals("New Widget", product.getName());
    }

    @Test
    void testSetQuantity_updatesQuantity() {
        Product product = new Product("Widget", 5, 10.0, 8.0, supplier, "Construction");
        product.setQuantity(20);
        assertEquals(20, product.getQuantity());
    }

    @Test
    void testSetCustomerPrice_updatesPrice() {
        Product product = new Product("Widget", 5, 10.0, 8.0, supplier, "Construction");
        product.setCustomerPrice(25.0);
        assertEquals(25.0, product.getCustomerPrice(), 0.001);
    }

    @Test
    void testSetSupplierPrice_updatesPrice() {
        Product product = new Product("Widget", 5, 10.0, 8.0, supplier, "Construction");
        product.setSupplierPrice(12.0);
        assertEquals(12.0, product.getSupplierPrice(), 0.001);
    }

    @Test
    void testToString_containsKeyDetails() {
        Product product = new Product("Widget", 5, 10.0, 8.0, supplier, "Construction");
        String output = product.toString();
        assertTrue(output.contains("Widget"));
        assertTrue(output.contains(product.getProductID()));
        assertTrue(output.contains("5"));
        assertTrue(output.contains("10.0"));
        assertTrue(output.contains("8.0"));
        assertTrue(output.contains("Construction"));
    }
}
