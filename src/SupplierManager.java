import java.util.ArrayList;
import java.util.List;

public class SupplierManager {
    List<Supplier> suppliers;

    public SupplierManager() {
        this.suppliers = new ArrayList<Supplier>();
    }

    public void addSupplier(Supplier supplier) {
        this.suppliers.add(supplier);
    }

    public boolean removeSupplier(String supplierID) {
        for (Supplier supplier : this.suppliers) {
            if (supplier.getSupplierID().equals(supplierID)) {
                this.suppliers.remove(supplier);
                return true;
            }
        }
        return false;
    }

    public Supplier getSupplier(String supplierID) {
        for (Supplier supplier : this.suppliers) {
            if (supplier.getSupplierID().equals(supplierID)) {
                return supplier;
            }
        }
        return null;
    }

    public void displaySuppliers() {
        for (Supplier supplier : this.suppliers) {
            System.out.println(supplier.toString());
        }
    }
}
