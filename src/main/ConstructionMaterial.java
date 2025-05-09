package main;

public class ConstructionMaterial extends Product {

    private String materialType;
    private String weightKg;
    private String dimensions;

    public ConstructionMaterial(String name, String productID, int quantity, double customerPrice, double supplierPrice, String materialType, String weightKg, String dimensions, Supplier supplier) {
        super(name, productID, quantity, customerPrice, supplierPrice, supplier);
        this.materialType = materialType;
        this.weightKg = weightKg;
        this.dimensions = dimensions;
    }

    public String getMaterialType() { return materialType; }
    public String getWeightKg() { return weightKg; }
    public String getDimensions() { return dimensions; }

    public void setMaterialType(String materialType) { this.materialType = materialType; }
    public void setWeightKg(String weightKg) { this.weightKg = weightKg; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }

    @Override
    public String toString() {
        return "Product Name: " + getName() + ", Product ID: " + getProductID() + ", Quantity: " + getQuantity() + ", Consumer Price: " + getCustomerPrice() + ", Supplier Price: " + getSupplierPrice() + ", Material Type: " + getMaterialType() + ", Weight: " + getWeightKg() + ", Dimensions: " + getDimensions();
    }
}