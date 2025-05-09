import java.util.Arrays;
import java.util.List;

public class CustomerOrderTestData {

    public static void initialize(CustomerOrder customerOrder, CustomerManager customerManager, InventoryManager inventory) {
        // Sample Customers
        Customer customer1 = new Customer("John Doe", "CUST123", "johndoe@example.com", "555-1234");
        Customer customer2 = new Customer("Jane Smith", "CUST124", "janesmith@example.com", "555-5678");
        Customer customer3 = new Customer("ACME Corp", "CUST125", "acme@example.com", "555-0000");
        Customer customer4 = new Customer("Sophie Brown", "CUST126", "sophie@example.com", "555-1122");
        Customer customer5 = new Customer("Liam Green", "CUST127", "liamgreen@example.com", "555-3344");

        customerManager.addCustomer(customer1);
        customerManager.addCustomer(customer2);
        customerManager.addCustomer(customer3);
        customerManager.addCustomer(customer4);
        customerManager.addCustomer(customer5);

        // Sample Orders (Mix of product types)
        // Order 1: Customer John Doe, 1 Tool + 1 Safety Equipment
        CustomerOrder order1 = new CustomerOrder("ORD123", customer1, Arrays.asList(
                new OrderedProduct(inventory.findProductById("PROD123"), 1),  // 1 Cordless Drill
                new OrderedProduct(inventory.findProductById("PROD126"), 2)   // 2 Safety Glasses
        ));

        // Order 2: Customer Jane Smith, 3 Tools
        Order order2 = new Order("ORD124", customer2, Arrays.asList(
                new OrderedProduct(inventory.getProductByID("PROD124"), 3)   // 3 Hammers
        ));

        // Order 3: ACME Corp, Large order of Tools and Construction Materials
        Order order3 = new Order("ORD125", customer3, Arrays.asList(
                new OrderedProduct(inventory.getProductByID("PROD123"), 10),  // 10 Cordless Drills
                new OrderedProduct(inventory.getProductByID("PROD128"), 50),  // 50 Steel Bars
                new OrderedProduct(inventory.getProductByID("PROD127"), 100)  // 100 Bags of Cement
        ));

        // Order 4: Customer Sophie Brown, Mixed order of Safety Equipment and Machinery
        Order order4 = new Order("ORD126", customer4, Arrays.asList(
                new OrderedProduct(inventory.getProductByID("PROD125"), 5),   // 5 Safety Gloves
                new OrderedProduct(inventory.getProductByID("PROD129"), 2)    // 2 Excavators
        ));

        // Order 5: Customer Liam Green, Small order of Tools and Materials
        Order order5 = new Order("ORD127", customer5, Arrays.asList(
                new OrderedProduct(inventory.getProductByID("PROD124"), 2),   // 2 Hammers
                new OrderedProduct(inventory.getProductByID("PROD127"), 10)   // 10 Bags of Cement
        ));

        // Order 6: ACME Corp, Expensive order with Machinery
        Order order6 = new Order("ORD128", customer3, Arrays.asList(
                new OrderedProduct(inventory.getProductByID("PROD129"), 5),   // 5 Excavators
                new OrderedProduct(inventory.getProductByID("PROD130"), 3)    // 3 Forklifts
        ));

        // Order 7: Customer Jane Smith, Safety Equipment Only
        Order order7 = new Order("ORD129", customer2, Arrays.asList(
                new OrderedProduct(inventory.getProductByID("PROD125"), 2),   // 2 Safety Gloves
                new OrderedProduct(inventory.getProductByID("PROD126"), 1)    // 1 Safety Glasses
        ));

        // Order 8: Customer Sophie Brown, Bulk order of Construction Materials
        Order order8 = new Order("ORD130", customer4, Arrays.asList(
                new OrderedProduct(inventory.getProductByID("PROD127"), 200), // 200 Bags of Cement
                new OrderedProduct(inventory.getProductByID("PROD128"), 100)  // 100 Steel Bars
        ));

        // Order 9: Customer Liam Green, Small order of Tools
        Order order9 = new Order("ORD131", customer5, Arrays.asList(
                new OrderedProduct(inventory.getProductByID("PROD123"), 1),   // 1 Cordless Drill
                new OrderedProduct(inventory.getProductByID("PROD124"), 1)    // 1 Hammer
        ));

        // Order 10: Customer John Doe, Miscellaneous order of Safety and Tools
        Order order10 = new Order("ORD132", customer1, Arrays.asList(
                new OrderedProduct(inventory.getProductByID("PROD125"), 10),  // 10 Safety Gloves
                new OrderedProduct(inventory.getProductByID("PROD124"), 5),   // 5 Hammers
                new OrderedProduct(inventory.getProductByID("PROD127"), 50)   // 50 Bags of Cement
        ));

        // Add Orders to OrderManager
        orderManager.addOrder(order1);
        orderManager.addOrder(order2);
        orderManager.addOrder(order3);
        orderManager.addOrder(order4);
        orderManager.addOrder(order5);
        orderManager.addOrder(order6);
        orderManager.addOrder(order7);
        orderManager.addOrder(order8);
        orderManager.addOrder(order9);
        orderManager.addOrder(order10);
    }
}
