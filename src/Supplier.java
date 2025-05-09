public class Supplier {

    private String name;
    private String supplierID;
    private String email;
    private String phone;

    public Supplier(String name, String supplierID, String email, String phone) {
        this.name = name;
        this.supplierID = supplierID;
        this.email = email;
        this.phone = phone;
    }

    // Getter and setters for the object
    public String getName() { return name; }

    public String getSupplierID() { return supplierID; }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public void setName(String name) { this.name = name; }

    public void setSupplierID(String supplierID) { this.supplierID = supplierID; }

    public void setEmail(String email) { this.email = email; }

    public void setPhone(String phone) { this.phone = phone; }

    // Overrides the default toString() method to provide readable summary of supplier details
    @Override
    public String toString() {
        return "Supplier ID: " + supplierID + ", Name: " + name + ", Email: " + email;
    }
}
