package main.UI;

import main.products.InventoryManager;
import main.products.Product;
import main.products.ProductTypes;
import main.suppliers.Supplier;
import main.suppliers.SupplierManager;

import java.util.List;
import java.util.Scanner;

public class ProductManagementService {

    public static void createProduct(Scanner scanner, InventoryManager inventory, SupplierManager supplierManager) {
        // Notify user product creation started
        System.out.println("Creating new product");
        System.out.print("Enter the name of the product:  ");
        String name = scanner.nextLine().trim();

        // Read and validate stock level (non-negative integer)
        int stock = 0;
        while (true) {
            System.out.print("Enter the current stock level of the product:  ");
            try {
                stock = Integer.parseInt(scanner.nextLine().trim());
                if (stock < 0) {
                    System.out.println("Stock level cannot be negative. Please try again.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        // Read and validate customer price (non-negative double)
        double customerPrice = 0.0;
        while (true) {
            System.out.print("Enter the selling price for the product:  ");
            try {
                customerPrice = Double.parseDouble(scanner.nextLine().trim());
                if (customerPrice < 0) {
                    System.out.println("Price cannot be negative. Please try again.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Read and validate supplier price (non-negative double)
        double supplierPrice = 0.0;
        while (true) {
            System.out.print("Enter the supplier price for the product:  ");
            try {
                supplierPrice = Double.parseDouble(scanner.nextLine().trim());
                if (supplierPrice < 0) {
                    System.out.println("Price cannot be negative. Please try again.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        // Select supplier, allow cancelling creation by returning null
        Supplier supplier = selectSupplier(supplierManager, scanner);
        if (supplier == null) {
            System.out.println("Product creation cancelled.");
            return;
        }

        // Select product type from predefined list
        String type = selectProductType(scanner);

        // Add new product to inventory
        inventory.addProduct(new Product(name, stock, customerPrice, supplierPrice, supplier, type));
    }

    public static Supplier selectSupplier(SupplierManager supplierManager, Scanner scanner) {
        while (true) {
            // Display all suppliers
            supplierManager.displaySuppliers();
            System.out.println("Enter the Supplier ID for this product (or type 'cancel' to exit): ");
            String supplierID = scanner.nextLine().trim();

            // Allow canceling supplier selection
            if (supplierID.equalsIgnoreCase("cancel")) return null;

            // Lookup supplier by ID
            Supplier supplier = supplierManager.getSupplier(supplierID);
            if (supplier != null) {
                return supplier;
            } else {
                System.out.println("The supplier ID does not match a supplier. Please try again.");
            }
        }
    }

    public static String selectProductType(Scanner scanner) {
        List<String> types = ProductTypes.TYPES;

        while (true) {
            // Display all product types with numbers
            System.out.println("Enter the product type:  ");
            for (int i = 0; i < types.size(); i++) {
                System.out.println((i + 1) + ". " + types.get(i));
            }
            System.out.print("Enter choice: ");

            try {
                int productType = Integer.parseInt(scanner.nextLine().trim());

                // Validate input is within range
                if (productType < 1 || productType > types.size()) {
                    System.out.println("Invalid choice. Please select a valid option.");
                    continue;
                }
                return types.get(productType - 1);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number corresponding to the product type.");
            }
        }
    }

    public static void deleteProduct(InventoryManager inventory, Scanner scanner) {
        // Show all products before deletion
        inventory.listProducts();

        while (true) {
            System.out.println("Enter Product ID to delete (or type 'cancel' to exit): ");
            String productID = scanner.nextLine().trim();

            // Allow canceling deletion
            if (productID.equalsIgnoreCase("cancel")) return;

            // Find product by ID
            Product product = inventory.getProductById(productID);
            if (product != null) {
                // Remove product and notify
                inventory.removeProductById(productID);
                System.out.println("The product has been deleted.");
            } else {
                System.out.println("No product found with that ID. Please try again.");
            }
        }
    }

    public static void modifyProduct(InventoryManager inventory, Scanner scanner) {
        // List all products before modification
        inventory.listProducts();

        while (true) {
            System.out.println("\n--- Product Modifying Menu ---");
            System.out.println("Enter Product ID (or type 'cancel' to exit): ");
            String productID = scanner.nextLine().trim();

            // Allow canceling modification
            if (productID.equalsIgnoreCase("cancel")) return;

            // Find product to modify
            Product product = inventory.getProductById(productID);
            if (product != null) {
                boolean productRunning = true;

                // Loop for modifying product properties
                while (productRunning) {
                    System.out.println(product.toString());
                    System.out.println("1. Modify Product Name");
                    System.out.println("2. Modify Product Stock Level");
                    System.out.println("3. Modify Product Customer Price");
                    System.out.println("4. Modify Product Supplier Price");
                    System.out.println("5. Return to Product Menu");
                    System.out.print("Enter choice: ");

                    String choice = scanner.nextLine().trim();
                    switch (choice) {
                        case "1":
                            // Change product name
                            System.out.println("Enter new product name: ");
                            String newName = scanner.nextLine().trim();
                            product.setName(newName);
                            break;

                        case "2":
                            // Change stock level, validating non-negative integer
                            while (true) {
                                System.out.println("Enter new product stock level: ");
                                try {
                                    int newQuantity = Integer.parseInt(scanner.nextLine().trim());
                                    if (newQuantity < 0) {
                                        System.out.println("Stock level cannot be negative. Please try again.");
                                        continue;
                                    }
                                    product.setQuantity(newQuantity);
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid integer.");
                                }
                            }
                            break;

                        case "3":
                            // Change customer price, validating non-negative double
                            while (true) {
                                System.out.println("Enter new product customer price: ");
                                try {
                                    double newCustomerPrice = Double.parseDouble(scanner.nextLine().trim());
                                    if (newCustomerPrice < 0) {
                                        System.out.println("Price cannot be negative. Please try again.");
                                        continue;
                                    }
                                    product.setCustomerPrice(newCustomerPrice);
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid number.");
                                }
                            }
                            break;

                        case "4":
                            // Change supplier price, validating non-negative double
                            while (true) {
                                System.out.println("Enter new product supplier price: ");
                                try {
                                    double newSupplierPrice = Double.parseDouble(scanner.nextLine().trim());
                                    if (newSupplierPrice < 0) {
                                        System.out.println("Price cannot be negative. Please try again.");
                                        continue;
                                    }
                                    product.setSupplierPrice(newSupplierPrice);
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid number.");
                                }
                            }
                            break;

                        case "5":
                            // Exit product modification menu
                            productRunning = false;
                            break;

                        default:
                            System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    }
                }
            } else {
                System.out.println("No product found with that ID. Please try again.");
            }
        }
    }
}
