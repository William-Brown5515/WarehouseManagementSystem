package main.UI;

import main.suppliers.Supplier;
import main.suppliers.SupplierManager;

import java.util.Scanner;

public class SupplierManagementService {

    public static void createSupplier(SupplierManager supplierManager, Scanner scanner) {
        System.out.println("Creating new supplier");
        System.out.print("Enter the name of the supplier:  ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter the email address of the supplier:  ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter the phone number of the supplier:  ");
        String phone = scanner.nextLine().trim();
        SupplierManager.addSupplier(new Supplier(name, email, phone));
    }

    public static void deleteSupplier(SupplierManager supplierManager, Scanner scanner) {
        supplierManager.displaySuppliers();
        while (true) {
            System.out.println("Enter Supplier ID (or type 'cancel' to exit): ");
            String supplierID = scanner.nextLine().trim();
            if (supplierID.equalsIgnoreCase("cancel")) return;
            Supplier supplier = supplierManager.getSupplier(supplierID);
            if (supplier != null) {
                supplierManager.removeSupplier(supplierID);
                System.out.println("The supplier has been deleted.");
            }
        }
    }

    public static void modifySupplier(SupplierManager supplierManager, Scanner scanner) {
        supplierManager.displaySuppliers();
        while (true) {
            System.out.println("Enter Supplier ID (or type 'cancel' to exit): ");
            String supplierID = scanner.nextLine().trim();
            if (supplierID.equalsIgnoreCase("cancel")) return;
            Supplier supplier = supplierManager.getSupplier(supplierID);
            if (supplier != null) {
                boolean supplierRunning = true;
                while (supplierRunning) {
                    System.out.println(supplier.toString());
                    System.out.println("1. Modify Supplier Name");
                    System.out.println("2. Modify Supplier Email");
                    System.out.println("3. Modify Supplier Phone");
                    System.out.println("4. Return to Supplier Menu");
                    System.out.print("Enter choice: ");

                    switch (scanner.nextLine().trim()) {
                        case "1":
                            System.out.println("Enter new supplier name: ");
                            String newName = scanner.nextLine().trim();
                            supplier.setName(newName);
                            break;
                        case "2":
                            System.out.println("Enter new supplier email: ");
                            String newEmail = scanner.nextLine().trim();
                            supplier.setEmail(newEmail);
                            break;
                        case "3":
                            System.out.println("Enter new supplier phone number: ");
                            String newPhone = scanner.nextLine().trim();
                            supplier.setPhone(newPhone);
                            break;
                        case "4":
                            supplierRunning = false;
                            break;
                    }
                }
            }
        }
    }
}
