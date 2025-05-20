package order;

import main.order.OrderedProduct;
import main.products.Product;
import main.products.ProductTypes;
import main.suppliers.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderedProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        Supplier supplier = new Supplier("Supplier", "email@example.com", "1234567890");
        product = new Product("Item", 10, 20.0, 15.0, supplier, ProductTypes.MACHINERY);
    }

    @Test
    void testConstructor_andGetters() {
        // Verify that constructor sets product and quantity correctly, and getters return them
        OrderedProduct orderedProduct = new OrderedProduct(product, 5);
        assertEquals(product, orderedProduct.getProduct());
        assertEquals(5, orderedProduct.getQuantity());
    }

    @Test
    void testSetQuantity_updatesQuantity() {
        // Verify that setQuantity correctly updates the quantity field
        OrderedProduct orderedProduct = new OrderedProduct(product, 3);
        orderedProduct.setQuantity(10);
        assertEquals(10, orderedProduct.getQuantity());
    }

    @Test
    void testToString_containsProductNameAndQuantity() {
        // Verify that toString method output contains product name and quantity
        OrderedProduct orderedProduct = new OrderedProduct(product, 2);
        String toString = orderedProduct.toString();
        assertTrue(toString.contains(product.getName()));
        assertTrue(toString.contains(String.valueOf(2)));
    }
}
