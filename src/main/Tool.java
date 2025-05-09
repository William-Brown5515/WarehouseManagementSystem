package main;

public class Tool extends Product {

    private String powerSource;
    private String weightKg;

    public Tool(String name, String productID, int quantity, double customerPrice, double supplierPrice, String powerSource, String weightKg, Supplier supplier) {
        super(name, productID, quantity, customerPrice, supplierPrice, supplier);
        this.powerSource = powerSource;
        this.weightKg = weightKg;
    }

    public String getPowerSource() { return powerSource; }
    public String getWeightKg() { return weightKg; }

    public void setPowerSource(String powerSource) { this.powerSource = powerSource; }
    public void setWeightKg(String weightKg) { this.weightKg = weightKg; }

    @Override
    public String toString() {
        return "Product Name: " + getName() + ", Product ID: " + getProductID() + ", Quantity: " + getQuantity() + ", Consumer Price: " + getCustomerPrice() + ", Supplier Price: " + getSupplierPrice() + ", PowerSource: " + getPowerSource() + ", WeightKg: " + getWeightKg();
    }
}
