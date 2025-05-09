public class Main {

    public static void main(String[] args) {
        System.out.println("Starting the application!");
        InventoryManager inventory = new InventoryManager();
        SupplierManager supplierManager = new SupplierManager();
        TestData.initialise(inventory, supplierManager);
        CustomerManager customerManager = new CustomerManager();
        FinancialReport report = new FinancialReport();
        TestData.businessOrders(supplierManager, report, inventory);
        TestData.customerOrders(customerManager, report, inventory);
        report.printReport();
    }
}