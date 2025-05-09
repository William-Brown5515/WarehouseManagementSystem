public class Machinery extends Product {

    private String enginePower;
    private String operatingVoltage;
    private String machineType;
    private String manufacturer;
    private final int warrantyPeriod;

    public Machinery(String name, String productID, int quantity, double customerPrice, double supplierPrice, String enginePower, String operatingVoltage, String machineType, String manufacturer, int warrantyPeriod) {
        super(name, productID, quantity, customerPrice, supplierPrice);
        this.enginePower = enginePower;
        this.operatingVoltage = operatingVoltage;
        this.machineType = machineType;
        this.manufacturer = manufacturer;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getEnginePower() { return enginePower; }
    public String getOperatingVoltage() { return operatingVoltage; }
    public String getMachineType() { return machineType; }
    public String getManufacturer() { return manufacturer; }
    public int getWarrantyPeriod() { return warrantyPeriod; }

    public void setEnginePower(String enginePower) { this.enginePower = enginePower; }
    public void setOperatingVoltage(String operatingVoltage) { this.operatingVoltage = operatingVoltage; }
    public void setMachineType(String machineType) { this.machineType = machineType; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    @Override
    public String toString() {
        return "Product Name: " + getName() + ", Product ID: " + getProductID() + ", Quantity: " + getQuantity() + ", Consumer Price: " + getCustomerPrice() + ", Supplier Price: " + getSupplierPrice() + ", Engine Power: " + getEnginePower() + ", Operating Voltage: " + getOperatingVoltage() + ", machineType: " + getMachineType() + ", Manufacturer: " + getManufacturer() + ", Warranty Period: " + getWarrantyPeriod();
    }
}
