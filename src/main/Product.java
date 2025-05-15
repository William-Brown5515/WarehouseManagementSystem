package main;

public class Product {

    private String name;
    private String productID;
    private int quantity;
    private double customerPrice;
    private double supplierPrice;
    private Supplier supplier;
    private String type;

    public Product(String name, String productID, int quantity, double customerPrice, double supplierPrice, Supplier supplier, String type) {
        this.name = name;
        this.productID = productID;
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

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setCustomerPrice(double customerPrice) { this.customerPrice = customerPrice; }
    public void setSupplierPrice(double supplierPrice) { this.supplierPrice = supplierPrice; }

    // Overrides the default toString() method to provide readable summary of product details
    @Override
    public String toString() {
        return "Product Name: " + name + ", Product ID: " + productID + ", Quantity: " + quantity + ", Consumer Price: " + customerPrice + ", Supplier Price: " + supplierPrice;
    }
}
