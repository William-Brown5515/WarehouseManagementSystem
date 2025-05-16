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

    public void setName(String newName) { name = newName; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setCustomerPrice(double customerPrice) { this.customerPrice = customerPrice; }
    public void setSupplierPrice(double supplierPrice) { this.supplierPrice = supplierPrice; }

    // Overrides the default toString() method to provide readable summary of product details
    @Override
    public String toString() {
        return "Product Name: " + name + ", Product ID: " + productID + ", Quantity: " + quantity + ", Customer Price: " + customerPrice + ", Supplier Price: " + supplierPrice;
    }
}
