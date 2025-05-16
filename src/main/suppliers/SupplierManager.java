package main.suppliers;

import java.util.ArrayList;
import java.util.List;

// A class to manage the suppliers
public class SupplierManager {
    static List<Supplier> suppliers;

    // The constructor, to initialise the supplier list
    public SupplierManager() {
        suppliers = new ArrayList<Supplier>();
    }

    // A method to add a new supplier
    public static void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
    }

    // A method to remove a supplier by their ID
    public boolean removeSupplier(String supplierID) {
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierID().equals(supplierID)) {
                suppliers.remove(supplier);
                return true;
            }
        }
        return false;
    }

    // A method to return a supplier that matches the given ID
    public Supplier getSupplier(String supplierID) {
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierID().equals(supplierID)) {
                return supplier;
            }
        }
        return null;
    }

    // A method to print all suppliers
    public void displaySuppliers() {
        for (Supplier supplier : suppliers) {
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
