package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.order.BusinessOrder;
import main.order.OrderManager;
import main.products.*;
import main.suppliers.*;
import main.financial.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SupplierManager supplierManager = new SupplierManager();
    private static final InventoryManager inventory = new InventoryManager();
    private static final FinancialReport report = new FinancialReport();
    private static final OrderManager orderManager = new OrderManager();

    public static void main(String[] args) {
        TestData.initialise(inventory, supplierManager);
        boolean running = true;
        while (running) {
            System.out.println("\n=== Warehouse Management System ===");
            System.out.println("1. Owner Mode");
            System.out.println("2. Customer Mode");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            switch (scanner.nextLine()) {
                case "1":
                    ownerMenu();
                    break;
                case "2":
                    customerMenu();
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void ownerMenu() {
        boolean ownerRunning = true;
        while (ownerRunning) {
            List<Product> lowStockItems = inventory.lowStock();
            List<BusinessOrder> arrivedOrders = orderManager.updateArrivalStatus();
            System.out.println("\n--- Owner Mode ---");
            System.out.println("0. Back to Main Menu");
            System.out.println("1. View Stock Levels");
            System.out.println("2. Order from Suppliers");
            System.out.println("3. Generate Financial Reports");
            System.out.println("4. View/Manage Products");
            System.out.println("5. Manage Suppliers");
            System.out.println("6. Launch Test Orders to Simulate System");
            if (!lowStockItems.isEmpty()) {
                System.out.println("7. View Low Stock Levels: " + lowStockItems.size() + " Products");
            }
            if (!arrivedOrders.isEmpty()) {
                System.out.println("8. Accept Incoming Order Arrivals: " + arrivedOrders.size() + " Deliveries");
            }
            System.out.print("Enter choice: ");

            switch (scanner.nextLine()) {
                case "0":
                    ownerRunning = false;
                    break;
                case "1":
                    inventory.getStockLevels();
                    break;
                case "2":
                    orderFromSuppliers();
                    break;
                case "3":
                    report.printReport();
                    break;
                case "4":
                    manageProducts();
                    break;
                case "5":
                    supplierMenu();
                case "6":
                    TestData.businessOrders(supplierManager, report, inventory);
                    TestData.customerOrders(report, inventory);
                case "7":
                    for (Product product : lowStockItems) {
                        System.out.println("Product Name: " + product.getName() + " Stock: " + product.getQuantity());
                    }
                    break;
                case "8":
                    for (BusinessOrder businessOrder : arrivedOrders) {
                        businessOrder.deliverOrder();
                    }
                    System.out.println("The orders have been delivered.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void customerMenu() {
        boolean customerRunning = true;
        while (customerRunning) {
            System.out.println("\n--- Customer Mode ---");
            System.out.println("1. Place New Order");
            System.out.println("2. View/Edit My Details");
            System.out.println("3. View My Order History");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter choice: ");

            switch (scanner.nextLine()) {
                case "1":
                    placeCustomerOrder();
                    break;
                case "2":
                    editCustomerDetails();
                    break;
                case "3":
                    viewCustomerOrderHistory();
                    break;
                case "4":
                    customerRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void supplierMenu() {
        boolean supplierRunning = true;
        while (supplierRunning) {
            System.out.println("\n--- Supplier Menu ---");
            System.out.println("1. View Suppliers");
            System.out.println("2. Add a Supplier");
            System.out.println("3. Delete a Supplier");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter choice: ");

            switch (scanner.nextLine()) {
                case "1":
                    supplierManager.displaySuppliers();
                    break;
                case "2":
                    createSupplier();
                    break;
                case "3":
                    viewCustomerOrderHistory();
                    break;
                case "4":
                    supplierRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // === Owner Actions ===
    private static void orderFromSuppliers() {
        supplierManager.displaySuppliers();
        while (true) {
            System.out.println("Enter Supplier ID (or type 'cancel' to exit): ");
            String supplierID = scanner.nextLine().trim();
            if (supplierID.equalsIgnoreCase("cancel")) return;
            Supplier supplier = supplierManager.getSupplier(supplierID);
            if (supplier != null) {
                BusinessOrder order = new BusinessOrder(supplier, report, inventory);
                while (true) {
                    List<Product> products = inventory.getProductsBySupplier(supplier);
                    System.out.println("The available products:");
                    for (Product product : products) {
                        System.out.println(product.toString());
                    }
                    // What if order = null?
                    System.out.println("Current Order: ");
                    order.currentOrder();
                    System.out.println("Enter Product ID  to add a product (or type 'cancel' to exit, or 'complete' to complete the order): ");
                    String productID = scanner.nextLine().trim();
                    if (productID.equalsIgnoreCase("cancel")) return;
                    if (productID.equalsIgnoreCase("complete")) {
                        order.completeOrder();
                        System.out.println("The order has been completed.");
                        break;
                    }
                    Product product = inventory.getProductById(productID);
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
            } else {
                System.out.println("No supplier with ID: " + supplierID);
            }

        }
    }

    private static void manageProducts() {
        System.out.println("[Product management options]");
        // TODO: Hook into Product/InventoryManager
    }

    private static void manageSuppliers() {
        supplierManager.displaySuppliers();
    }

    // === Customer Actions ===
    private static void placeCustomerOrder() {
        System.out.println("[Placing new customer order]");
        // TODO: Hook into CustomerOrder
    }

    private static void editCustomerDetails() {
        System.out.println("[Editing customer details]");
        // TODO: Modify Customer fields
    }

    private static void viewCustomerOrderHistory() {
        System.out.println("[Displaying customer order history]");
        // TODO: Show past orders
    }

    private static void createSupplier() {
        System.out.println("Creating new supplier");
        System.out.print("Enter the name of the supplier:  ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter the email address of the supplier:  ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter the phone number of the supplier:  ");
        String phone = scanner.nextLine().trim();
        supplierManager.addSupplier(new Supplier(name, email, phone));
    }
} 