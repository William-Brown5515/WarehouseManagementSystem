package products;

import main.products.ProductTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTypesTest {

    @BeforeEach
    void setUp() {
        // Reset TYPES list before each test since it's static and mutable
        ProductTypes.TYPES.clear();
        ProductTypes.TYPES.add("Tool");
        ProductTypes.TYPES.add("Safety Equipment");
        ProductTypes.TYPES.add("Construction Material");
        ProductTypes.TYPES.add("Machinery");
    }

    @Test
    void testInitialTypes() {
        assertTrue(ProductTypes.TYPES.contains("Tool"));
        assertTrue(ProductTypes.TYPES.contains("Safety Equipment"));
        assertTrue(ProductTypes.TYPES.contains("Construction Material"));
        assertTrue(ProductTypes.TYPES.contains("Machinery"));
        assertEquals(4, ProductTypes.TYPES.size());
    }

    @Test
    void testAddProductType_addsNewType() {
        ProductTypes.addProductType("Electrical");
        assertTrue(ProductTypes.TYPES.contains("Electrical"));
        assertEquals(5, ProductTypes.TYPES.size());
    }

    @Test
    void testAddProductType_duplicateType_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProductTypes.addProductType("tool");  // case-insensitive duplicate
        });
        assertTrue(exception.getMessage().contains("already exists"));
    }

    @Test
    void testAddProductType_nullOrEmpty_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> ProductTypes.addProductType(null));
        assertThrows(IllegalArgumentException.class, () -> ProductTypes.addProductType(""));
        assertThrows(IllegalArgumentException.class, () -> ProductTypes.addProductType("  "));
    }
}
