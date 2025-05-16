package main;

import main.financial.FinancialReport;
import main.order.BusinessOrder;
import main.order.CustomerOrder;
import main.products.InventoryManager;
import main.products.Product;
import main.products.ProductTypes;
import main.suppliers.Supplier;
import main.suppliers.SupplierManager;

import java.util.Arrays;
import java.util.List;

public class TestData {

    public static void initialise(InventoryManager inventory, main.suppliers.SupplierManager supplierManager) {
        // Add sample suppliers
        List<main.suppliers.Supplier> suppliers = Arrays.asList(
                new Supplier("Tools 4 U", "manager@tools4u.com", "01237856823"),
                new Supplier("XYZ Safety Gear", "xyz@safety.com", "01252528496"),
                new Supplier("Builders Supply Co.", "sales@buildersupply.com", "01254321123"),
                new Supplier("GreenTech Equip", "contact@greentechequip.com", "01251234567"),
                new Supplier("Safety First", "info@safetyfirst.com", "01250000000")
        );

        // Add the suppliers to the SupplierManager
        for (Supplier supplier : suppliers) {
            supplierManager.addSupplier(supplier);
        }

        // Create list of products for each subclass and assign them to the appropriate supplier
        List<main.products.Product> products = Arrays.asList(
                // Tool products
                new Product("Cordless Drill", 50, 120.00, 80.00, suppliers.get(0), main.products.ProductTypes.TOOL),
                new Product("Hammer", 150, 15.00, 10.00, suppliers.get(0), ProductTypes.TOOL),
                new Product("Screwdriver Set", 200, 25.00, 15.00, suppliers.get(0), ProductTypes.TOOL),
                new Product("Power Saw", 75, 250.00, 150.00, suppliers.get(1), ProductTypes.TOOL),
                new Product("Angle Grinder", 120, 80.00, 60.00, suppliers.get(1), ProductTypes.TOOL),

                // Safety Equipment products
                new Product("Safety Gloves", 200, 30.00, 20.00, suppliers.get(0), ProductTypes.SAFETY_EQUIPMENT),
                new Product("Safety Glasses", 100, 50.00, 35.00, suppliers.get(0), ProductTypes.SAFETY_EQUIPMENT),
                new Product("Ear Protection", 80, 45.00, 35.00, suppliers.get(1), ProductTypes.SAFETY_EQUIPMENT),
                new Product("Knee Pads", 150, 35.00, 25.00, suppliers.get(1), ProductTypes.SAFETY_EQUIPMENT),
                new Product("Respirator Mask", 50, 100.00, 75.00, suppliers.get(0), ProductTypes.SAFETY_EQUIPMENT),

                // Construction Material products
                new Product("Cement", 300, 5.00, 3.00, suppliers.get(1), ProductTypes.CONSTRUCTION_MATERIAL),
                new Product("Steel Bars", 100, 50.00, 40.00, suppliers.get(1), ProductTypes.CONSTRUCTION_MATERIAL),
                new Product("Sand", 500, 10.00, 7.00, suppliers.get(0), ProductTypes.CONSTRUCTION_MATERIAL),
                new Product("Bricks", 200, 50.00, 30.00, suppliers.get(2), ProductTypes.CONSTRUCTION_MATERIAL),
                new Product("Gravel", 150, 15.00, 10.00, suppliers.get(3), ProductTypes.CONSTRUCTION_MATERIAL),

                // Machinery products
                new Product("Excavator", 10, 25000.00, 18000.00, suppliers.get(0), ProductTypes.MACHINERY),
                new Product("Forklift", 5, 15000.00, 11000.00, suppliers.get(2), ProductTypes.MACHINERY),
                new Product("Bulldozer", 3, 45000.00, 35000.00, suppliers.get(4), ProductTypes.MACHINERY),
                new Product("Crane", 2, 100000.00, 75000.00, suppliers.get(3), ProductTypes.MACHINERY),
                new Product("Backhoe Loader", 8, 30000.00, 22000.00, suppliers.get(1), ProductTypes.MACHINERY)
        );

        // Add products to inventory
        for (Product product : products) {
            inventory.addProduct(product);
        }
    }
}
