public class SafetyEquipment extends Product {

    private String safetyRating;
    private String material;
    private String size;
    private boolean isReusable;

    public SafetyEquipment(String name, String productID, int quantity, double customerPrice, double supplierPrice, String safetyRating, String material, String size, boolean isReusable) {
        super(name, productID, quantity, customerPrice, supplierPrice);
        this.safetyRating = safetyRating;
        this.material = material;
        this.size = size;
        this.isReusable = isReusable;
    }

    public String getSafetyRating() { return safetyRating; }
    public String getMaterial() { return material; }
    public String getSize() { return size; }
    public boolean isReusable() { return isReusable; }

    public void setSafetyRating(String safetyRating) { this.safetyRating = safetyRating; }
    public void setMaterial(String material) { this.material = material; }
    public void setSize(String size) { this.size = size; }
    public void setReusable(boolean isReusable) { this.isReusable = isReusable; }

    @Override
    public String toString() {
        return "Product Name: " + getName() + ", Product ID: " + getProductID() + ", Quantity: " + getQuantity() + ", Consumer Price: " + getCustomerPrice() + ", Supplier Price: " + getSupplierPrice() + ", Safety Rating: " + safetyRating + ", Material : " + getMaterial() + ", Size : " + getSize() + ", Reusable : " + isReusable();
    }
}
