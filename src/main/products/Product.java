package main.products;

import main.suppliers.Supplier;

import java.util.UUID;

public class Product {

    private String name;
    private String productID;
    private int quantity;
    private double customerPrice;
    private double supplierPrice;
    private Supplier supplier;
    private String type;

    public Product(String name, int quantity, double customerPrice, double supplierPrice, Supplier supplier, String type) {

        // Ensure all parameters are as expected
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (customerPrice < 0 || supplierPrice < 0) {
            throw new IllegalArgumentException("Prices cannot be negative");
        }
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier cannot be null");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or blank");
        }

        this.name = name;
        this.productID = UUID.randomUUID().toString().substring(0, 8).toUpperCase();;
        this.quantity = quantity;
        this.customerPrice = customerPrice;
        this.supplierPrice = supplierPrice;
        this.supplier = supplier;
        this.type = type;
    }

    // Getter and setters for the object
    public String getName() { return name; }
    public String getProductID() { return productID; }
    public int getQuantity() { return quantity; }
    public double getCustomerPrice() { return customerPrice; }
    public double getSupplierPrice() { return supplierPrice; }
    public Supplier getSupplier() { return supplier; }

    // Class setters, with exceptions to ensure the parameters match the requirements
    public void setName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        name = newName; }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public void setCustomerPrice(double customerPrice) {
        if (customerPrice < 0) {
            throw new IllegalArgumentException("Customer price cannot be negative");
        }
        this.customerPrice = customerPrice;
    }

    public void setSupplierPrice(double supplierPrice) {
        if (supplierPrice < 0) {
            throw new IllegalArgumentException("Supplier price cannot be negative");
        }
        this.supplierPrice = supplierPrice;
    }
    // Overrides the default toString() method to provide readable summary of product details
    @Override
    public String toString() {
        return "Product Name: " + name + ", Product ID: " + productID + ", Quantity: " + quantity + ", Customer Price: " + customerPrice + ", Supplier Price: " + supplierPrice + ", Type: " + type;
    }
}
