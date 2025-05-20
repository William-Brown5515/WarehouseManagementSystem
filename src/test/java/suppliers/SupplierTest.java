package suppliers;

import main.suppliers.Supplier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SupplierTest {

    @Test
    void constructor_validInputs_createsSupplier() {
        // Verify constructor creates supplier with valid inputs
        Supplier supplier = new Supplier("Acme Ltd", "contact@acme.com", "0123456789");
        assertEquals("Acme Ltd", supplier.getName());
        assertNotNull(supplier.getSupplierID());
        assertFalse(supplier.getSupplierID().isBlank());
    }

    @Test
    void constructor_nullOrEmptyName_throwsException() {
        // Verify constructor throws exception for null or empty name
        assertThrows(IllegalArgumentException.class, () -> new Supplier(null, "email@test.com", "123"));
        assertThrows(IllegalArgumentException.class, () -> new Supplier("", "email@test.com", "123"));
        assertThrows(IllegalArgumentException.class, () -> new Supplier("   ", "email@test.com", "123"));
    }

    @Test
    void constructor_nullOrEmptyEmail_throwsException() {
        // Verify constructor throws exception for null or empty email
        assertThrows(IllegalArgumentException.class, () -> new Supplier("Name", null, "123"));
        assertThrows(IllegalArgumentException.class, () -> new Supplier("Name", "", "123"));
        assertThrows(IllegalArgumentException.class, () -> new Supplier("Name", "   ", "123"));
    }

    @Test
    void constructor_nullOrEmptyPhone_throwsException() {
        // Verify constructor throws exception for null or empty phone number
        assertThrows(IllegalArgumentException.class, () -> new Supplier("Name", "email@test.com", null));
        assertThrows(IllegalArgumentException.class, () -> new Supplier("Name", "email@test.com", ""));
        assertThrows(IllegalArgumentException.class, () -> new Supplier("Name", "email@test.com", "   "));
    }

    @Test
    void setName_valid_setsName() {
        // Verify setName updates supplier name when valid
        Supplier supplier = new Supplier("Name", "email@test.com", "123");
        supplier.setName("New Name");
        assertEquals("New Name", supplier.getName());
    }

    @Test
    void setName_nullOrEmpty_throwsException() {
        // Verify setName throws exception for null or empty name
        Supplier supplier = new Supplier("Name", "email@test.com", "123");
        assertThrows(IllegalArgumentException.class, () -> supplier.setName(null));
        assertThrows(IllegalArgumentException.class, () -> supplier.setName(""));
        assertThrows(IllegalArgumentException.class, () -> supplier.setName("  "));
    }

    @Test
    void setEmail_valid_setsEmail() {
        // Verify setEmail updates supplier email when valid
        Supplier supplier = new Supplier("Name", "email@test.com", "123");
        supplier.setEmail("newemail@test.com");
        assertTrue(supplier.toString().contains("newemail@test.com"));
    }

    @Test
    void setEmail_nullOrEmpty_throwsException() {
        // Verify setEmail throws exception for null or empty email
        Supplier supplier = new Supplier("Name", "email@test.com", "123");
        assertThrows(IllegalArgumentException.class, () -> supplier.setEmail(null));
        assertThrows(IllegalArgumentException.class, () -> supplier.setEmail(""));
        assertThrows(IllegalArgumentException.class, () -> supplier.setEmail("  "));
    }

    @Test
    void setPhone_valid_setsPhone() {
        // Verify setPhone updates supplier phone when valid
        Supplier supplier = new Supplier("Name", "email@test.com", "123");
        supplier.setPhone("9876543210");
        assertTrue(supplier.toString().contains("9876543210"));
    }

    @Test
    void setPhone_nullOrEmpty_throwsException() {
        // Verify setPhone throws exception for null or empty phone number
        Supplier supplier = new Supplier("Name", "email@test.com", "123");
        assertThrows(IllegalArgumentException.class, () -> supplier.setPhone(null));
        assertThrows(IllegalArgumentException.class, () -> supplier.setPhone(""));
        assertThrows(IllegalArgumentException.class, () -> supplier.setPhone("  "));
    }

    @Test
    void toString_containsAllDetails() {
        // Verify toString contains all supplier details
        Supplier supplier = new Supplier("Name", "email@test.com", "123");
        String str = supplier.toString();
        assertTrue(str.contains(supplier.getSupplierID()));
        assertTrue(str.contains("Name"));
        assertTrue(str.contains("email@test.com"));
        assertTrue(str.contains("123"));
    }
}
