package main.UI;

import main.suppliers.Supplier;
import main.suppliers.SupplierManager;

import java.util.Scanner;

public class SupplierManagementService {

    // Handles creating a new supplier
    public static void createSupplier(SupplierManager supplierManager, Scanner scanner) {
        System.out.println("Creating new supplier");

        // Prompt for supplier details
        System.out.print("Enter the name of the supplier:  ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter the email address of the supplier:  ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter the phone number of the supplier:  ");
        String phone = scanner.nextLine().trim();

        // Create and add supplier
        supplierManager.addSupplier(new Supplier(name, email, phone));
        System.out.println("Supplier created successfully.");
    }

    // Handles deleting a supplier by ID
    public static void deleteSupplier(SupplierManager supplierManager, Scanner scanner) {
        supplierManager.displaySuppliers();

        while (true) {
            System.out.println("Enter Supplier ID (or type 'cancel' to exit): ");
            String supplierID = scanner.nextLine().trim();

            if (supplierID.equalsIgnoreCase("cancel")) return;

            // Attempt to fetch supplier
            Supplier supplier = supplierManager.getSupplier(supplierID);
            if (supplier != null) {
                supplierManager.removeSupplier(supplierID);
                System.out.println("The supplier has been deleted.");
                return; // Exit after successful deletion
            } else {
                System.out.println("No supplier found with that ID. Please try again.");
            }
        }
    }

    // Handles modifying supplier attributes
    public static void modifySupplier(SupplierManager supplierManager, Scanner scanner) {
        supplierManager.displaySuppliers();

        while (true) {
            System.out.println("Enter Supplier ID (or type 'cancel' to exit): ");
            String supplierID = scanner.nextLine().trim();

            if (supplierID.equalsIgnoreCase("cancel")) return;

            Supplier supplier = supplierManager.getSupplier(supplierID);
            if (supplier != null) {
                boolean supplierRunning = true;

                // Modification loop
                while (supplierRunning) {
                    System.out.println(supplier);
                    System.out.println("1. Modify Supplier Name");
                    System.out.println("2. Modify Supplier Email");
                    System.out.println("3. Modify Supplier Phone");
                    System.out.println("4. Return to Supplier Menu");
                    System.out.print("Enter choice: ");

                    String choice = scanner.nextLine().trim();
                    switch (choice) {
                        case "1":
                            System.out.println("Enter new supplier name: ");
                            String newName = scanner.nextLine().trim();
                            supplier.setName(newName);
                            System.out.println("Supplier name updated.");
                            break;
                        case "2":
                            System.out.println("Enter new supplier email: ");
                            String newEmail = scanner.nextLine().trim();
                            supplier.setEmail(newEmail);
                            System.out.println("Supplier email updated.");
                            break;
                        case "3":
                            System.out.println("Enter new supplier phone number: ");
                            String newPhone = scanner.nextLine().trim();
                            supplier.setPhone(newPhone);
                            System.out.println("Supplier phone number updated.");
                            break;
                        case "4":
                            supplierRunning = false; // Exit modification loop
                            break;
                        default:
                            System.out.println("Invalid choice. Please select an option between 1 and 4.");
                    }
                }
                return; // Exit after modifying one supplier
            } else {
                System.out.println("No supplier found with that ID. Please try again.");
            }
        }
    }
}
