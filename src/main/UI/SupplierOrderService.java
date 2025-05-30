package main.UI;

import main.financial.FinancialReport;
import main.products.*;
import main.suppliers.*;
import main.order.*;

import java.util.List;
import java.util.Scanner;

public class SupplierOrderService {

    public static void orderFromSuppliers(OrderManager orderManager, InventoryManager inventory, FinancialReport report, SupplierManager supplierManager, Scanner scanner) {
        // Display all available suppliers
        supplierManager.displaySuppliers();

        System.out.println("\n--- Supplier Order Menu ---");
        System.out.println("Enter Supplier ID (or type 'cancel' to exit): ");
        String supplierID = scanner.nextLine().trim();
        if (supplierID.equalsIgnoreCase("cancel")) return;

        Supplier supplier = supplierManager.getSupplier(supplierID);
        if (supplier == null) {
            System.out.println("No supplier with ID: " + supplierID);
            return;
        }

        BusinessOrder order = new BusinessOrder(report, inventory, supplier);

        while (true) {
            // Display products from selected supplier
            List<Product> products = inventory.getProductsBySupplier(supplier);
            System.out.println("The available products:");
            for (Product product : products) {
                System.out.println(product.toString());
            }

            // Display current order
            System.out.println("Current Order: ");
            order.currentOrder();

            System.out.println("Enter Product ID to add a product (or type 'cancel' to exit, or 'complete' to complete the order): ");
            String productID = scanner.nextLine().trim();

            if (productID.equalsIgnoreCase("cancel")) return;

            if (productID.equalsIgnoreCase("complete")) {
                order.completeOrder();
                orderManager.addOrder(order);
                System.out.println("The order has been completed.");
                break;
            }

            Product product = inventory.getProductById(productID);
            if (product == null) {
                System.out.println("Product does not exist with ID: " + productID);
                continue;
            }

            int currentQuantity = order.currentQuantity(product);

            if (currentQuantity > 0) {
                // Product already in order - update or remove
                System.out.println("This product is already in the basket.");
                System.out.println("Current order quantity: " + currentQuantity);
                System.out.println("Enter TOTAL quantity (e.g. 2 to keep 2 total, 0 to remove):");

                int quantity = readPositiveInt(scanner);
                if (quantity == 0) {
                    order.removeItem(productID);
                    System.out.println("Product removed from the order. Current order price: " + order.getTotalPrice());
                } else {
                    order.changeProductQuantity(product, quantity);
                    System.out.println("Product quantity updated. Current order price: " + order.getTotalPrice());
                }

            } else {
                // Product not yet in order - add new quantity
                System.out.println("Product: " + product.getName());
                System.out.println("Your current stock level is: " + product.getQuantity());
                System.out.println("How many would you like to add? (type '0' to cancel ordering this product): ");

                int quantityToAdd = readPositiveInt(scanner);
                if (quantityToAdd == 0) {
                    System.out.println("Cancelled adding product.");
                    continue;
                }

                order.addItem(productID, quantityToAdd);
                System.out.println("Product added to order. Current order price: " + order.getTotalPrice());
            }
        }
    }

    // Reusable input helper for safely reading non-negative integers
    private static int readPositiveInt(Scanner scanner) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input < 0) {
                    System.out.println("Please enter a non-negative integer.");
                    continue;
                }
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }
}
