import java.util.Arrays;
import java.util.List;

public class TestData {

    public static void initialize(InventoryManager inventory, SupplierManager supplierManager) {
        // Add sample suppliers
        Supplier supplier1 = new Supplier("Tools 4 U", "ID-745", "manager@tools4u.com", "01237856823");
        Supplier supplier2 = new Supplier("XYZ Safety Gear", "ID-324", "xyz@safety.com", "01252528496");
        supplierManager.addSupplier(supplier1);
        supplierManager.addSupplier(supplier2);

        // Create list of products for each subclass and assign them to the appropriate supplier
        List<Product> products = Arrays.asList(
                // Tool products
                new Tool("Cordless Drill", "PROD123", 50, 120.00, 80.00, "Battery", "1.5", supplier1),
                new Tool("Hammer", "PROD124", 150, 15.00, 10.00, "Manual", "0.7", supplier1),
                new Tool("Screwdriver Set", "PROD125", 200, 25.00, 15.00, "Manual", "0.5", supplier1),
                new Tool("Power Saw", "PROD126", 75, 250.00, 150.00, "Electric", "3.0", supplier2),
                new Tool("Angle Grinder", "PROD127", 120, 80.00, 60.00, "Electric", "1.8", supplier2),

                // Safety Equipment products
                new SafetyEquipment("Safety Gloves", "PROD128", 200, 30.00, 20.00, "5-star", "Leather", "Medium", true, supplier1),
                new SafetyEquipment("Safety Glasses", "PROD129", 100, 50.00, 35.00, "3-star", "Polycarbonate", "Standard", false, supplier1),
                new SafetyEquipment("Ear Protection", "PROD130", 80, 45.00, 35.00, "4-star", "Foam", "Standard", true, supplier2),
                new SafetyEquipment("Knee Pads", "PROD131", 150, 35.00, 25.00, "5-star", "Rubber", "One Size", true, supplier2),
                new SafetyEquipment("Respirator Mask", "PROD132", 50, 100.00, 75.00, "5-star", "Silicone", "Medium", true, supplier1),

                // Construction Material products
                new ConstructionMaterial("Cement", "PROD133", 300, 5.00, 3.00, "Cement", "25", "50kg bag", supplier2),
                new ConstructionMaterial("Steel Bars", "PROD134", 100, 50.00, 40.00, "Steel", "200", "10 meters", supplier2),
                new ConstructionMaterial("Sand", "PROD135", 500, 10.00, 7.00, "Sand", "100", "25kg bag", supplier1),
                new ConstructionMaterial("Bricks", "PROD136", 200, 50.00, 30.00, "Clay", "50", "1000 pcs", supplier1),
                new ConstructionMaterial("Gravel", "PROD137", 150, 15.00, 10.00, "Gravel", "150", "25kg bag", supplier2),

                // Machinery products
                new Machinery("Excavator", "PROD138", 10, 25000.00, 18000.00, "200HP", "380V", "Excavator", "Caterpillar", 12, supplier1),
                new Machinery("Forklift", "PROD139", 5, 15000.00, 11000.00, "150HP", "220V", "Forklift", "Toyota", 24, supplier1),
                new Machinery("Bulldozer", "PROD140", 3, 45000.00, 35000.00, "300HP", "480V", "Bulldozer", "Komatsu", 18, supplier2),
                new Machinery("Crane", "PROD141", 2, 100000.00, 75000.00, "500HP", "400V", "Crane", "Liebherr", 36, supplier2),
                new Machinery("Backhoe Loader", "PROD142", 8, 30000.00, 22000.00, "250HP", "380V", "Loader", "Caterpillar", 24, supplier1)
        );

        // Add products to inventory
        for (Product product : products) {
            inventory.addProduct(product);
        }
    }
}
