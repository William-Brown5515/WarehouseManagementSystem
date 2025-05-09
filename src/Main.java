public class Main {

    public Main() {
        System.out.println("Starting the application!");
        InventoryManager inventory = new InventoryManager();
        SupplierManager supplierManager = new SupplierManager();
        TestData.initialise(inventory, supplierManager);
        CustomerManager customerManager = new CustomerManager();
    }
}