package main.suppliers;

import java.util.ArrayList;
import java.util.List;

// A class to manage the suppliers
public class SupplierManager {
    List<Supplier> suppliers;

    // The constructor, to initialise the supplier list
    public SupplierManager() {
        this.suppliers = new ArrayList<Supplier>();
    }

    // A method to add a new supplier
    public void addSupplier(Supplier supplier) {
        this.suppliers.add(supplier);
    }

    // A method to remove a supplier by their ID
    public boolean removeSupplier(String supplierID) {
        for (Supplier supplier : this.suppliers) {
            if (supplier.getSupplierID().equals(supplierID)) {
                this.suppliers.remove(supplier);
                return true;
            }
        }
        return false;
    }

    // A method to return a supplier that matches the given ID
    public Supplier getSupplier(String supplierID) {
        for (Supplier supplier : this.suppliers) {
            if (supplier.getSupplierID().equals(supplierID)) {
                return supplier;
            }
        }
        return null;
    }

    // A method to print all suppliers
    public void displaySuppliers() {
        for (Supplier supplier : this.suppliers) {
            System.out.println(supplier.toString());
        }
    }

    public boolean updateSupplierDetails(String supplierID, String name, String phone, String email) {
        Supplier supplier = getSupplier(supplierID);
        if (supplier != null) {
            supplier.setName(name);
            supplier.setPhone(phone);
            supplier.setEmail(email);
            return true;
        }
        return false;
    }
}
