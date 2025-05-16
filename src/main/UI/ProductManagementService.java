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
        System.out.println("Creating new product");
        System.out.print("Enter the name of the product:  ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter the current stock level of the product:  ");
        int stock = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter the selling price for the product:  ");
        double customerPrice = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Enter the supplier price for the product:  ");
        double supplierPrice = Double.parseDouble(scanner.nextLine().trim());
        Supplier supplier = selectSupplier(supplierManager, scanner);
        String type = selectProductType(scanner);
        inventory.addProduct(new Product(name, stock, customerPrice, supplierPrice, supplier, type));
    }

    public static Supplier selectSupplier(SupplierManager supplierManager, Scanner scanner) {
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
    
    public static String selectProductType(Scanner scanner) {
        System.out.println("Enter the product type:  ");
        List<String> types = ProductTypes.TYPES;
        for (int type = 0; type < types.size(); type++) {
            System.out.println((type + 1) + ". " + types.get(type));
        }
        System.out.print("Enter choice: ");
        int productType = Integer.parseInt(scanner.nextLine().trim());
        return types.get(productType - 1);
    }

    public static void deleteProduct(InventoryManager inventory, Scanner scanner) {
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

    public static void modifyProduct(InventoryManager inventory, Scanner scanner) {
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
}
