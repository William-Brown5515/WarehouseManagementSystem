package main;

import java.util.List;
import java.util.Scanner;

import main.order.BusinessOrder;
import main.order.CustomerOrder;
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
            System.out.println("7. View Order History");
            if (!lowStockItems.isEmpty()) {
                System.out.println("8. View Low Stock Levels: " + lowStockItems.size() + " Products");
            }
            if (!arrivedOrders.isEmpty()) {
                System.out.println("9. Accept Incoming Order Arrivals: " + arrivedOrders.size() + " Deliveries");
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
                    productMenu();
                    break;
                case "5":
                    supplierMenu();
                    break;
                case "6":
                    TestData.businessOrders(supplierManager, report, inventory);
                    TestData.customerOrders(report, inventory);
                    break;
                case "7":
                    orderManager.displayBusinessOrders();
                    break;
                case "8":
                    for (Product product : lowStockItems) {
                        System.out.println("Product Name: " + product.getName() + " Stock: " + product.getQuantity());
                    }
                    break;
                case "9":
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
            System.out.println("2. View Order History");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter choice: ");

            switch (scanner.nextLine()) {
                case "1":
                    orderFromWarehouse();
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

    private static void supplierMenu() {
        boolean supplierRunning = true;
        while (supplierRunning) {
            System.out.println("\n--- Supplier Menu ---");
            System.out.println("1. View Suppliers");
            System.out.println("2. Add a Supplier");
            System.out.println("3. Delete a Supplier");
            System.out.println("4. Modify Supplier Details");
            System.out.println("5. Back to Owner Menu");
            System.out.print("Enter choice: ");

            switch (scanner.nextLine()) {
                case "1":
                    supplierManager.displaySuppliers();
                    break;
                case "2":
                    createSupplier();
                    break;
                case "3":
                    deleteSupplier();
                    break;
                case "4":
                    modifySupplier();
                    break;
                case "5":
                    supplierRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void productMenu() {
        boolean productRunning = true;
        while (productRunning) {
            System.out.println("\n--- Product Menu ---");
            System.out.println("1. View All Product");
            System.out.println("2. Add a Product");
            System.out.println("3. Delete a Product");
            System.out.println("4. Modify Product Details");
            System.out.println("5. Back to Owner Menu");
            System.out.print("Enter choice: ");

            switch (scanner.nextLine()) {
                case "1":
                    inventory.listProducts();
                    break;
                case "2":
                    createProduct();
                    break;
                case "3":
                    deleteProduct();
                    break;
                case "4":
                    modifyProduct();
                    break;
                case "5":
                    productRunning = false;
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
            System.out.println("\n--- Supplier Order Menu ---");
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
                    int currentQuantity = order.currentQuantity(product);
                    if (currentQuantity != 0) {
                        System.out.println("This product is already in the basket. The current order quantity is: " + order.getOrderedProducts());
                        System.out.println("How many would you like to order in TOTAL? If there are currently 4 products, and you enter 2. The basket will contain only 2 of that item");
                        System.out.println("Enter '0' to remove the product from the order");
                        System.out.print("Enter choice: ");
                        int quantity = Integer.parseInt(scanner.nextLine().trim());;
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
            } else {
                System.out.println("No supplier with ID: " + supplierID);
            }

        }
    }

    // === Customer Actions ===
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

    private static void deleteSupplier() {
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

    private static void modifySupplier() {
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

    private static void createProduct() {
        System.out.println("Creating new product");
        System.out.print("Enter the name of the product:  ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter the current stock level of the product:  ");
        int stock = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter the selling price for the product:  ");
        double customerPrice = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Enter the supplier price for the product:  ");
        double supplierPrice = Double.parseDouble(scanner.nextLine().trim());
        Supplier supplier = selectSupplier();
        String type = selectProductType();
        inventory.addProduct(new Product(name, stock, customerPrice, supplierPrice, supplier, type));

    }

    private static Supplier selectSupplier() {
        while (true) {
            supplierManager.displaySuppliers();
            System.out.println("Enter the Supplier ID for this product: ");
            String supplierID = scanner.nextLine().trim();
            if (supplierID.equalsIgnoreCase("cancel")) return null;
            Supplier supplier = supplierManager.getSupplier(supplierID);
            if (supplier != null) {
                return supplierManager.getSupplier(supplierID);
            } else {
                System.out.println("The supplier ID does not match a supplier.");
            }
        }
    }

    private static String selectProductType() {
        System.out.println("Enter the product type:  ");
        List<String> types = ProductTypes.TYPES;
        for (int type = 0; type < types.size(); type++) {
            System.out.println((type + 1) + ". " + types.get(type));
        }
        System.out.print("Enter choice: ");
        int productType = Integer.parseInt(scanner.nextLine().trim());
        return types.get(productType - 1);
    }

    private static void deleteProduct() {
        inventory.listProducts();
        while (true) {
            System.out.println("Enter Product ID (or type 'cancel' to exit): ");
            String productID = scanner.nextLine().trim();
            if (productID.equalsIgnoreCase("cancel")) return;
            Product product = inventory.getProductById(productID);
            if (product != null) {
                inventory.removeProductById(productID);
                System.out.println("The product has been deleted.");
            }
        }
    }

    private static void modifyProduct() {
        inventory.listProducts();
        while (true) {
            System.out.println("\n--- Product Modifying Menu ---");
            System.out.println("Enter Product ID (or type 'cancel' to exit): ");
            String productID = scanner.nextLine().trim();
            if (productID.equalsIgnoreCase("cancel")) return;
            Product product = inventory.getProductById(productID);
            if (product != null) {
                boolean productRunning = true;
                while (productRunning) {
                    System.out.println(product.toString());
                    System.out.println("1. Modify Product Name");
                    System.out.println("2. Modify Product Stock Level");
                    System.out.println("3. Modify Product Customer Price");
                    System.out.println("4. Modify Product Supplier Price");
                    System.out.println("5. Return to Supplier Menu");
                    System.out.print("Enter choice: ");

                    switch (scanner.nextLine().trim()) {
                        case "1":
                            System.out.println("Enter new product name: ");
                            String newName = scanner.nextLine().trim();
                            product.setName(newName);
                            break;
                        case "2":
                            System.out.println("Enter new product stock level: ");
                            int newQuantity = Integer.parseInt(scanner.nextLine().trim());
                            product.setQuantity(newQuantity);
                            break;
                        case "3":
                            System.out.println("Enter new product customer price: ");
                            double newCustomerPrice = Double.parseDouble(scanner.nextLine().trim());
                            product.setCustomerPrice(newCustomerPrice);
                            break;
                        case "4":
                            System.out.println("Enter new product supplier price: ");
                            double newSupplierPrice = Double.parseDouble(scanner.nextLine().trim());
                            product.setSupplierPrice(newSupplierPrice);
                            break;
                        case "5":
                            productRunning = false;
                            break;
                    }
                }
            }
        }
    }

    private static void orderFromWarehouse() {
        while (true) {
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
                    int quantity = Integer.parseInt(scanner.nextLine().trim());;
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
}
