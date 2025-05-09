package main;

import java.util.ArrayList;
import java.util.List;

// A Class to manage the inventory
public class InventoryManager {
    private List<Product> products;

    // The constructor, which creates an empty ArrayList on object creation
    public InventoryManager() {
        this.products = new ArrayList<Product>();
    }

    // A method to add a new product to the ArrayList
    public void addProduct(Product product) {
        products.add(product);
    }

    // A method which lists all the products
    public void listProducts() {
        for (Product product : products) {
            System.out.println(product);
        }
    }

    // A method to find a product by its ID
    public Product findProductById(String id) {
        // Iterating through the products
        for (Product product : products) {
            // If the product exists, remove and confirm true
            if (product.getProductID().equals(id)) {
                return product;
            }
        }
        return null;
    }

    // A method to remove a product by its ID
    public boolean removeProductById(String id) {
        // Iterating through the products
        for (Product product : products) {
            // If the product exists, remove and confirm true
            if (product.getProductID().equals(id)) {
                products.remove(product);
                return true;
            }
        }
        return false;
    }

    public boolean addStock(String productID, int quantity) {
        // Find the product by ID
        Product product = findProductById(productID);
        // If the product exists, add the stock as requested
        if (product != null) {
            product.setQuantity(product.getQuantity() + quantity);
            return true;
        } else {
            return false;
        }
    }

    public List<Product> lowStock() {
        // Create a List, then iterate through the products and check if stock is low
        List<Product> lowStockItems = new ArrayList<>();
        for (Product product : products) {
            if (product.getQuantity() <= 50) {
                lowStockItems.add(product);
            }
        }
        return lowStockItems;
    }

    public void reduceStock(String productID, int quantity) {
        Product product = findProductById(productID);
        if (product != null) {
            product.setQuantity(product.getQuantity() - quantity);
        }
    }
}
