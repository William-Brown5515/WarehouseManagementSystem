package main;

import java.util.List;
import java.util.Scanner;

import main.UI.CustomerOrderService;
import main.UI.ProductManagementService;
import main.UI.SupplierManagementService;
import main.UI.SupplierOrderService;
import main.order.*;
import main.products.*;
import main.suppliers.*;
import main.financial.*;

public class Main {
    // Shared scanner and core system managers
    private static final Scanner scanner = new Scanner(System.in);
    private static final SupplierManager supplierManager = new SupplierManager();
    private static final InventoryManager inventory = new InventoryManager();
    private static final FinancialReport report = new FinancialReport();
    private static final OrderManager orderManager = new OrderManager();

    public static void main(String[] args) {
        // Seed initial test data
        TestData.initialise(inventory, supplierManager);

        boolean running = true;
        while (running) {
            System.out.println("\n=== Warehouse Management System ===");
            System.out.println("1. Owner Mode");
            System.out.println("2. Customer Mode");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            switch (scanner.nextLine().trim()) {
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

    // Owner mode: full system management access
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
            System.out.println("6. View Order History");

            // Contextual options if low stock or incoming deliveries exist
            if (!lowStockItems.isEmpty()) {
                System.out.println("7. View Low Stock Levels: " + lowStockItems.size() + " Products");
            }
            if (!arrivedOrders.isEmpty()) {
                System.out.println("8. Accept Incoming Order Arrivals: " + arrivedOrders.size() + " Deliveries");
            }

            System.out.print("Enter choice: ");

            switch (scanner.nextLine().trim()) {
                case "0":
                    ownerRunning = false;
                    break;
                case "1":
                    inventory.getStockLevels();
                    break;
                case "2":
                    SupplierOrderService.orderFromSuppliers(orderManager, inventory, report, supplierManager, scanner);
                    break;
                case "3":
                    report.printReport();
                    break;
                case "4":
                    productMenu();
                    break;
                case "5":
                    supplierMenu();
                    break;
                case "6":
                    orderManager.displayBusinessOrders();
                    break;
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

    // Customer menu: placing/viewing personal orders
    private static void customerMenu() {
        boolean customerRunning = true;

        while (customerRunning) {
            System.out.println("\n--- Customer Mode ---");
            System.out.println("1. Place New Order");
            System.out.println("2. View Order History");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter choice: ");

            switch (scanner.nextLine().trim()) {
                case "1":
                    CustomerOrderService.orderFromWarehouse(orderManager, inventory, report, supplierManager, scanner);
                    break;
                case "2":
                    orderManager.displayCustomerOrders();
                    break;
                case "3":
                    customerRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Supplier management options
    private static void supplierMenu() {
        boolean supplierRunning = true;

        while (supplierRunning) {
            System.out.println("\n--- Supplier Menu ---");
            System.out.println("1. View Suppliers");
            System.out.println("2. Add a Supplier");
            System.out.println("3. Delete a Supplier");
            System.out.println("4. Modify Supplier Details");
            System.out.println("5. View Orders from a Supplier");
            System.out.println("6. Back to Owner Menu");
            System.out.print("Enter choice: ");

            switch (scanner.nextLine().trim()) {
                case "1":
                    supplierManager.displaySuppliers();
                    break;
                case "2":
                    SupplierManagementService.createSupplier(supplierManager, scanner);
                    break;
                case "3":
                    SupplierManagementService.deleteSupplier(supplierManager, scanner);
                    break;
                case "4":
                    SupplierManagementService.modifySupplier(supplierManager, scanner);
                    break;
                case "5":
                    SupplierManagementService.getSupplierOrders(supplierManager, scanner, orderManager);
                    break;
                case "6":
                    supplierRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Product management options
    private static void productMenu() {
        boolean productRunning = true;

        while (productRunning) {
            System.out.println("\n--- Product Menu ---");
            System.out.println("1. View All Products");
            System.out.println("2. Add a Product");
            System.out.println("3. Delete a Product");
            System.out.println("4. Modify Product Details");
            System.out.println("5. Back to Owner Menu");
            System.out.print("Enter choice: ");

            switch (scanner.nextLine().trim()) {
                case "1":
                    inventory.listProducts();
                    break;
                case "2":
                    ProductManagementService.createProduct(scanner, inventory, supplierManager);
                    break;
                case "3":
                    ProductManagementService.deleteProduct(inventory, scanner);
                    break;
                case "4":
                    ProductManagementService.modifyProduct(inventory, scanner);
                    break;
                case "5":
                    productRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
