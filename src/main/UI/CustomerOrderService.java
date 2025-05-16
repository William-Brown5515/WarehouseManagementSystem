package main.UI;

import main.financial.FinancialReport;
import main.products.*;
import main.suppliers.*;
import main.order.*;

import java.util.Scanner;

public class CustomerOrderService {

    public static void orderFromWarehouse(OrderManager orderManager, InventoryManager inventory, FinancialReport report, SupplierManager supplierManager, Scanner scanner) {
        System.out.println("\n--- Product Order Menu ---");
        CustomerOrder order = new CustomerOrder(report, inventory);
        while (true) {
            inventory.listProducts();
            System.out.println("Current Order: ");
            order.currentOrder();
            System.out.println("Enter Product ID  to add a product (or type 'cancel' to exit, or 'complete' to complete the order): ");
            String productID = scanner.nextLine().trim();
            if (productID.equalsIgnoreCase("cancel")) return;
            if (productID.equalsIgnoreCase("complete")) {
                order.completeOrder();
                orderManager.addOrder(order);
                System.out.println("The order has been completed.");
                break;
            }
            Product product = inventory.getProductById(productID);
            int currentQuantity = order.currentQuantity(product);
            if (currentQuantity != 0) {
                System.out.println("This product is already in the basket. The current order quantity is: " + order.getOrderedProducts());
                System.out.println("How many would you like to order in TOTAL? If there are currently 4 products, and you enter 2. The basket will contain only 2 of that item");
                System.out.println("Enter '0' to remove the product from the order");
                System.out.print("Enter choice: ");
                int quantity = Integer.parseInt(scanner.nextLine().trim());
                ;
                if (quantity == 0) {
                    order.removeItem(productID);
                } else {
                    order.changeProductQuantity(product, quantity);
                }
            }
            if (product != null) {
                System.out.println("Product: " + product.getName());
                System.out.println("Your current stock level is: " + product.getQuantity());
                System.out.println("How many would you like to add? (type '0' to cancel ordering this product): ");
                int quantity = Integer.parseInt(scanner.nextLine().trim());
                if (quantity == 0) return;
                order.addItem(productID, quantity);
            } else {
                System.out.println("Product does not exist with ID: " + productID);
            }
        }
    }
}
