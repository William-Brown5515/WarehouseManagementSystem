package main.UI;

import main.financial.FinancialReport;
import main.products.*;
import main.suppliers.*;
import main.order.*;

import java.util.Scanner;

public class CustomerOrderService {

    public static void orderFromWarehouse(OrderManager orderManager, InventoryManager inventory, FinancialReport report, SupplierManager supplierManager, Scanner scanner) {
        // Print the menu header
        System.out.println("\n--- Product Order Menu ---");
        // Create new customer order with access to report and inventory
        CustomerOrder order = new CustomerOrder(report, inventory);

        // Main input loop for adding/modifying products or completing/cancelling order
        while (true) {
            // Display all products in inventory
            inventory.listProducts();
            // Show the current products in the order
            System.out.println("Current Order: ");
            order.currentOrder();

            // Prompt user for product ID or command
            System.out.println("Enter Product ID to add a product (or type 'cancel' to exit, 'complete' to complete the order): ");
            String productID = scanner.nextLine().trim();

            // If user types 'cancel', exit the method
            if (productID.equalsIgnoreCase("cancel")) {
                System.out.println("Order cancelled.");
                return;
            }

            // If user types 'complete', check order is not empty then finalize
            if (productID.equalsIgnoreCase("complete")) {
                if (order.getOrderedProducts().isEmpty()) {
                    System.out.println("Your order is empty. Add products before completing.");
                    continue;
                }
                // Complete and add order to manager
                order.completeOrder();
                orderManager.addOrder(order);
                System.out.println("The order has been completed.");
                break;
            }

            // Try to find product by ID in inventory
            Product product = inventory.getProductById(productID);
            // If no such product, inform and continue loop
            if (product == null) {
                System.out.println("Product does not exist with ID: " + productID);
                continue;
            }

            // Check if product is already in current order
            int currentQuantity = order.currentQuantity(product);
            if (currentQuantity > 0) {
                System.out.println("This product is already in the basket with quantity: " + currentQuantity);
                System.out.println("Enter the TOTAL quantity you want to have in the basket (enter 0 to remove): ");
                int quantity = readPositiveInt(scanner);

                // Remove product if quantity set to 0
                if (quantity == 0) {
                    order.removeItem(productID);
                    System.out.println("Product removed from the order.");
                } else {
                    // Otherwise update quantity to new value
                    order.changeProductQuantity(product, quantity);
                    System.out.println("Product quantity updated.");
                }
                continue;
            }

            // Display product details before adding
            System.out.println("Product: " + product.getName());
            System.out.println("Your current stock level is: " + product.getQuantity());
            System.out.println("How many would you like to add? (Enter 0 to cancel ordering this product): ");
            int quantityToAdd = readPositiveInt(scanner);

            // Cancel adding if quantity is 0
            if (quantityToAdd == 0) {
                System.out.println("Cancelled adding product.");
                continue;
            }

            // Add the specified quantity of product to the order
            order.addItem(productID, quantityToAdd);
        }
    }

    // Helper method to safely read a non-negative integer from the user
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
