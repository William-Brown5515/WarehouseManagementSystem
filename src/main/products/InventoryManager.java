package main.products;

import main.suppliers.Supplier;

import java.util.ArrayList;
import java.util.List;

// A Class to manage the inventory
public class InventoryManager {
    private List<Product> products;

    // The constructor, which creates an empty ArrayList on object creation
    public InventoryManager() {
        this.products = new ArrayList<>();
    }

    public List <Product> getProducts() { return products; }

    // A method to add a new product to the List
    public void addProduct(Product product) {
        // Ensure the product exists
        if (product == null) {
            throw new IllegalArgumentException("Cannot add null product to inventory.");
        }
        products.add(product);
    }

    // A method which lists all the products
    public void listProducts() {
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }

    // A method to find a product by its ID
    public Product getProductById(String id) {
        // Ensure the parameter is as expected
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }

        // Iterating through the products
        for (Product product : products) {
            // If the product exists, return the product
            if (product.getProductID().equals(id)) {
                return product;
            }
        }
        return null;
    }

    // A method to remove a product by its ID
    public void removeProductById(String id) {
        // Ensure the parameter is as expected
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }

        // Iterating through the products
        products.removeIf(product -> product.getProductID().equals(id));
    }

    public void addStock(String productID, int quantity) {

        // Ensure the parameter is as expected
        if (productID == null || productID.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }
        // Ensure the quantity is valid
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to add must be positive.");
        }
        // Find the product by ID
        Product product = getProductById(productID);
        // If the product exists, add the stock as requested
        if (product != null) {
            product.setQuantity(product.getQuantity() + quantity);
        }
    }

    public void reduceStock(String productID, int quantity) {

        // Ensure the parameters are as expected
        if (productID == null || productID.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to reduce must be positive.");
        }
        Product product = getProductById(productID);
        if (product != null) {
            if (product.getQuantity() < quantity) {
                throw new IllegalArgumentException("Insufficient stock to reduce by " + quantity + ".");
            }
            // Set the new stock levels
            product.setQuantity(product.getQuantity() - quantity);
        } else {
            throw new IllegalArgumentException("Product with ID " + productID + " not found.");
        }
    }

    public List<Product> lowStock() {
        // Create a List, then iterate through the products and check if stock is low
        List<Product> lowStockItems = new ArrayList<>();
        for (Product product : products) {
            if (product.getQuantity() <= 20) {
                lowStockItems.add(product);
            }
        }
        return lowStockItems;
    }

    public void getStockLevels() {
        // Cycle through the products and retrieve the stock levels
        for (Product product : products) {
            System.out.println("Product Name: " + product.getName() + " Product ID: " + product.getProductID() + " Quantity: " + product.getQuantity());
        }
    }

    public List<Product> getProductsBySupplier(Supplier supplier) {
        List<Product> productsBySupplier = new ArrayList<>();
        // Cycle through the products, and add to the list if the supplier matches
        for (Product product : products) {
            if (product.getSupplier().equals(supplier)) {
                productsBySupplier.add(product);
            }
        }
        return productsBySupplier;
    }
}
