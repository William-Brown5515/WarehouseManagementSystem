package suppliers;

import main.suppliers.Supplier;
import main.suppliers.SupplierManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SupplierManagerTest {

    private SupplierManager manager;
    private Supplier supplier1;
    private Supplier supplier2;

    @BeforeEach
    void setup() {
        manager = new SupplierManager();
        supplier1 = new Supplier("Supplier One", "one@example.com", "1111111111");
        supplier2 = new Supplier("Supplier Two", "two@example.com", "2222222222");
    }

    @Test
    void addSupplier_valid_addsSupplier() {
        manager.addSupplier(supplier1);
        assertEquals(supplier1, manager.getSupplier(supplier1.getSupplierID()));
    }

    @Test
    void addSupplier_null_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> manager.addSupplier(null));
    }

    @Test
    void removeSupplier_valid_removesSupplier() {
        manager.addSupplier(supplier1);
        manager.addSupplier(supplier2);
        manager.removeSupplier(supplier1.getSupplierID());
        assertNull(manager.getSupplier(supplier1.getSupplierID()));
        assertNotNull(manager.getSupplier(supplier2.getSupplierID()));
    }

    @Test
    void removeSupplier_nullOrEmpty_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> manager.removeSupplier(null));
        assertThrows(IllegalArgumentException.class, () -> manager.removeSupplier(""));
        assertThrows(IllegalArgumentException.class, () -> manager.removeSupplier("  "));
    }

    @Test
    void getSupplier_existing_returnsSupplier() {
        manager.addSupplier(supplier1);
        Supplier found = manager.getSupplier(supplier1.getSupplierID());
        assertEquals(supplier1, found);
    }

    @Test
    void getSupplier_nonExisting_returnsNull() {
        manager.addSupplier(supplier1);
        assertNull(manager.getSupplier("NONEXIST"));
    }

    @Test
    void displaySuppliers_printsSuppliers() {
        // Since displaySuppliers prints to System.out, just test it runs without error
        manager.addSupplier(supplier1);
        manager.addSupplier(supplier2);
        manager.displaySuppliers();
    }
}
