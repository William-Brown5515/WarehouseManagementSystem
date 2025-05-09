import java.util.Arrays;
import java.util.List;

public class TestData {

    public static void initialise(InventoryManager inventory, SupplierManager supplierManager) {
        // Add sample suppliers
        List<Supplier> suppliers = Arrays.asList(
            new Supplier("Tools 4 U", "ID-745", "manager@tools4u.com", "01237856823"),
            new Supplier("XYZ Safety Gear", "ID-324", "xyz@safety.com", "01252528496"),
            new Supplier("Builders Supply Co.", "ID-567", "sales@buildersupply.com", "01254321123"),
            new Supplier("GreenTech Equip", "ID-890", "contact@greentechequip.com", "01251234567"),
            new Supplier("Safety First", "ID-101", "info@safetyfirst.com", "01250000000")
        );

        // Add the suppliers to the SupplierManager
        for (Supplier supplier : suppliers) {
            supplierManager.addSupplier(supplier);
        }

        // Create list of products for each subclass and assign them to the appropriate supplier
        List<Product> products = Arrays.asList(
                // Tool products
                new Tool("Cordless Drill", "PROD123", 50, 120.00, 80.00, "Battery", "1.5", suppliers.getFirst()),
                new Tool("Hammer", "PROD124", 150, 15.00, 10.00, "Manual", "0.7", suppliers.getFirst()),
                new Tool("Screwdriver Set", "PROD125", 200, 25.00, 15.00, "Manual", "0.5", suppliers.getFirst()),
                new Tool("Power Saw", "PROD126", 75, 250.00, 150.00, "Electric", "3.0", suppliers.get(1)),
                new Tool("Angle Grinder", "PROD127", 120, 80.00, 60.00, "Electric", "1.8", suppliers.get(1)),

                // Safety Equipment products
                new SafetyEquipment("Safety Gloves", "PROD128", 200, 30.00, 20.00, "5-star", "Leather", "Medium", true, suppliers.getFirst()),
                new SafetyEquipment("Safety Glasses", "PROD129", 100, 50.00, 35.00, "3-star", "Polycarbonate", "Standard", false, suppliers.getFirst()),
                new SafetyEquipment("Ear Protection", "PROD130", 80, 45.00, 35.00, "4-star", "Foam", "Standard", true, suppliers.get(1)),
                new SafetyEquipment("Knee Pads", "PROD131", 150, 35.00, 25.00, "5-star", "Rubber", "One Size", true, suppliers.get(1)),
                new SafetyEquipment("Respirator Mask", "PROD132", 50, 100.00, 75.00, "5-star", "Silicone", "Medium", true, suppliers.getFirst()),

                // Construction Material products
                new ConstructionMaterial("Cement", "PROD133", 300, 5.00, 3.00, "Cement", "25", "50kg bag", suppliers.get(1)),
                new ConstructionMaterial("Steel Bars", "PROD134", 100, 50.00, 40.00, "Steel", "200", "10 meters", suppliers.get(1)),
                new ConstructionMaterial("Sand", "PROD135", 500, 10.00, 7.00, "Sand", "100", "25kg bag", suppliers.getFirst()),
                new ConstructionMaterial("Bricks", "PROD136", 200, 50.00, 30.00, "Clay", "50", "1000 pcs", suppliers.get(2)),
                new ConstructionMaterial("Gravel", "PROD137", 150, 15.00, 10.00, "Gravel", "150", "25kg bag", suppliers.get(3)),

                // Machinery products
                new Machinery("Excavator", "PROD138", 10, 25000.00, 18000.00, "200HP", "380V", "Excavator", "Caterpillar", 12, suppliers.getFirst()),
                new Machinery("Forklift", "PROD139", 5, 15000.00, 11000.00, "150HP", "220V", "Forklift", "Toyota", 24, suppliers.get(2)),
                new Machinery("Bulldozer", "PROD140", 3, 45000.00, 35000.00, "300HP", "480V", "Bulldozer", "Komatsu", 18, suppliers.get(4)),
                new Machinery("Crane", "PROD141", 2, 100000.00, 75000.00, "500HP", "400V", "Crane", "Liebherr", 36, suppliers.get(3)),
                new Machinery("Backhoe Loader", "PROD142", 8, 30000.00, 22000.00, "250HP", "380V", "Loader", "Caterpillar", 24, suppliers.get(1))
        );

        // Add products to inventory
        for (Product product : products) {
            inventory.addProduct(product);
        }
    }

    public static void customerOrders(CustomerManager customerManager) {
        // Sample Customers
        List<Customer> customers = Arrays.asList(
            new Customer("John Doe", "CUST123", "johndoe@example.com", "555-1234"),
            new Customer("Jane Smith", "CUST124", "janesmith@example.com", "555-5678"),
            new Customer("ACME Corp", "CUST125", "acme@example.com", "555-0000"),
            new Customer("Sophie Brown", "CUST126", "sophie@example.com", "555-1122"),
            new Customer("Liam Green", "CUST127", "liamgreen@example.com", "555-3344")
        );

        for (Customer customer : customers) {
            customerManager.addCustomer(customer);
        }

        // Sample Orders (Creating order and adding products one by one)

        // Order 1: Customer John Doe, 1 Tool + 1 Safety Equipment
        CustomerOrder order1 = new CustomerOrder(customers.getFirst(), "ORD123");
        order1.addItem("PROD123", 1);  // 1 Cordless Drill
        order1.addItem("PROD126", 2);  // 2 Safety Glasses

        // Order 2: Customer Jane Smith, 3 Tools
        CustomerOrder order2 = new CustomerOrder(customers.get(1), "ORD124");
        order2.addItem("PROD124", 3);   // 3 Hammers

        // Order 3: ACME Corp, Large order of Tools and Construction Materials
        CustomerOrder order3 = new CustomerOrder(customers.get(2), "ORD125");
        order3.addItem("PROD123", 10);  // 10 Cordless Drills
        order3.addItem("PROD128", 50);  // 50 Steel Bars
        order3.addItem("PROD127", 100); // 100 Bags of Cement

        // Order 4: Customer Sophie Brown, Mixed order of Safety Equipment and Machinery
        CustomerOrder order4 = new CustomerOrder(customers.get(3), "ORD126");
        order4.addItem("PROD125", 5);   // 5 Safety Gloves
        order4.addItem("PROD129", 2);   // 2 Excavators

        // Order 5: Customer Liam Green, Small order of Tools and Materials
        CustomerOrder order5 = new CustomerOrder(customers.get(4), "ORD127");
        order5.addItem("PROD124", 2);   // 2 Hammers
        order5.addItem("PROD127", 10);  // 10 Bags of Cement

        // Order 6: ACME Corp, Expensive order with Machinery
        CustomerOrder order6 = new CustomerOrder(customers.get(2), "ORD128");
        order6.addItem("PROD129", 5);   // 5 Excavators
        order6.addItem("PROD130", 3);   // 3 Forklifts

        // List of orders
        List<CustomerOrder> orders = Arrays.asList(order1, order2, order3, order4, order5, order6);

        // Process orders
        for (CustomerOrder order : orders) {
            order.completeOrder();  // Mark the order as complete
            order.deliverOrder();   // Mark the order as delivered
        }
    }

    public static void businessOrders(SupplierManager supplierManager) {
        // Create a business order for Supplier 1
        Supplier supplier1 = supplierManager.getSupplier("ID-745");
        BusinessOrder businessOrder1 = new BusinessOrder(supplier1, "BUSINESS-ORD001");

        // Add items to the business order
        businessOrder1.addItem("PROD123", 100);  // 100 Cordless Drills
        businessOrder1.addItem("PROD124", 200);  // 200 Hammers
        businessOrder1.addItem("PROD128", 150);  // 150 Safety Gloves

        // Create another business order for Supplier 2
        Supplier supplier2 = supplierManager.getSupplier("ID-324");
        BusinessOrder businessOrder2 = new BusinessOrder(supplier2, "BUSINESS-ORD002");

        // Add items to the business order
        businessOrder2.addItem("PROD137", 500);  // 500 Bags of Gravel
        businessOrder2.addItem("PROD130", 200);  // 200 Ear Protection

        // Create another business order for Supplier 3
        Supplier supplier3 = supplierManager.getSupplier("ID-567");
        BusinessOrder businessOrder3 = new BusinessOrder(supplier3, "BUSINESS-ORD003");

        // Add items to the business order
        businessOrder3.addItem("PROD136", 100);  // 100 Bricks
        businessOrder3.addItem("PROD139", 50);   // 50 Forklifts

        // Create another business order for Supplier 4
        Supplier supplier4 = supplierManager.getSupplier("ID-890");
        BusinessOrder businessOrder4 = new BusinessOrder(supplier4, "BUSINESS-ORD004");

        // Add items to the business order
        businessOrder4.addItem("PROD141", 10);   // 10 Cranes
        businessOrder4.addItem("PROD133", 200);  // 200 Bags of Cement

        // Create another business order for Supplier 5
        Supplier supplier5 = supplierManager.getSupplier("ID-101");
        BusinessOrder businessOrder5 = new BusinessOrder(supplier5, "BUSINESS-ORD005");

        // Add items to the business order
        businessOrder5.addItem("PROD140", 50);  // 50 Bulldozers
        businessOrder5.addItem("PROD125", 100); // 100 Screwdriver Sets

        // List of orders
        List<BusinessOrder> businessOrders = Arrays.asList(businessOrder1, businessOrder2, businessOrder3, businessOrder4, businessOrder5);

        for (BusinessOrder businessOrder : businessOrders) {
            businessOrder.completeOrder();
            businessOrder.deliverOrder();
        }
    }
}
