package UI;

import main.suppliers.Supplier;
import main.suppliers.SupplierManager;
import main.UI.SupplierManagementService;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.io.ByteArrayInputStream;

public class SupplierManagementServiceTest {

    @Test
    public void testCreateSupplier() {
        System.out.println("Running testCreateSupplier...");

        SupplierManager sm = new SupplierManager();

        // Simulate input for createSupplier: name, email, phone
        String input = "Test Supplier\nsupplier@example.com\n1234567890\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        SupplierManagementService.createSupplier(sm, scanner);

        Supplier created = sm.getSupplier(sm.getSuppliers().getFirst().getSupplierID());
        if (created != null && created.getName().equals("Test Supplier")) {
            System.out.println("testCreateSupplier passed.");
        } else {
            System.out.println("testCreateSupplier failed.");
        }
    }

    @Test
    public void testModifySupplier() {
        System.out.println("Running testModifySupplier...");

        SupplierManager sm = new SupplierManager();
        Supplier s = new Supplier("Old Name", "old@example.com", "11111111");
        sm.addSupplier(s);

        // Simulate inputs: supplierID, choice to modify name, new name, choice to exit
        String input = s.getSupplierID() + "\n1\nNew Name\n4\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        SupplierManagementService.modifySupplier(sm, scanner);

        Supplier modified = sm.getSupplier(s.getSupplierID());
        if (modified != null && modified.getName().equals("New Name")) {
            System.out.println("testModifySupplier passed.");
        } else {
            System.out.println("testModifySupplier failed.");
        }
    }

    @Test
    public void testDeleteSupplier() {
        System.out.println("Running testDeleteSupplier...");

        SupplierManager sm = new SupplierManager();
        Supplier s = new Supplier("Delete Me", "delete@example.com", "22222222");
        sm.addSupplier(s);

        // Simulate inputs: supplierID to delete
        String input = s.getSupplierID() + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        SupplierManagementService.deleteSupplier(sm, scanner);

        if (sm.getSupplier(s.getSupplierID()) == null) {
            System.out.println("testDeleteSupplier passed.");
        } else {
            System.out.println("testDeleteSupplier failed.");
        }
    }
}
