package main.suppliers;

import java.util.UUID;

public class Supplier {

    private String name;
    private String supplierID;
    private String email;
    private String phone;

    public Supplier(String name, String email, String phone) {

        // Ensure all parameters are as expected
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier name cannot be null or empty.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier email cannot be null or empty.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier phone cannot be null or empty.");
        }
        this.name = name;
        this.supplierID = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.email = email;
        this.phone = phone;
    }

    // Getter and setters for the object
    public String getName() { return name; }

    public String getSupplierID() { return supplierID; }

    // Class setters, with exceptions to ensure the parameters match the requirements
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier name cannot be null or empty.");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier email cannot be null or empty.");
        }
        this.email = email;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier phone cannot be null or empty.");
        }
        this.phone = phone;
    }

    // Overrides the default toString() method to provide readable summary of supplier details
    @Override
    public String toString() {
        return "Supplier ID: " + supplierID + ", Name: " + name + ", Email: " + email + ", Phone: " + phone ;
    }
}
