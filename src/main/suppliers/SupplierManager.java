package main.suppliers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// A class to manage the suppliers
public class SupplierManager {
    List<Supplier> suppliers;

    // The constructor, to initialise the supplier list
    public SupplierManager() {
        suppliers = new ArrayList<>();
    }

    // A method to add a new supplier, features null verification
    public void addSupplier(Supplier supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier cannot be null.");
        }
        suppliers.add(supplier);
    }

    // A method to remove a supplier by their ID
    public void removeSupplier(String supplierID) {
        if (supplierID == null || supplierID.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier ID cannot be null or empty.");
        }
        suppliers.removeIf(supplier -> supplier.getSupplierID().equals(supplierID));
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
}
