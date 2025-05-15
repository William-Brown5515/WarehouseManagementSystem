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
                new Product("Cordless Drill", "PROD123", 50, 120.00, 80.00, suppliers.get(0), main.products.ProductTypes.TOOL),
                new Product("Hammer", "PROD124", 150, 15.00, 10.00, suppliers.get(0), ProductTypes.TOOL),
                new Product("Screwdriver Set", "PROD125", 200, 25.00, 15.00, suppliers.get(0), ProductTypes.TOOL),
                new Product("Power Saw", "PROD126", 75, 250.00, 150.00, suppliers.get(1), ProductTypes.TOOL),
                new Product("Angle Grinder", "PROD127", 120, 80.00, 60.00, suppliers.get(1), ProductTypes.TOOL),

                // Safety Equipment products
                new Product("Safety Gloves", "PROD128", 200, 30.00, 20.00, suppliers.get(0), ProductTypes.SAFETY_EQUIPMENT),
                new Product("Safety Glasses", "PROD129", 100, 50.00, 35.00, suppliers.get(0), ProductTypes.SAFETY_EQUIPMENT),
                new Product("Ear Protection", "PROD130", 80, 45.00, 35.00, suppliers.get(1), ProductTypes.SAFETY_EQUIPMENT),
                new Product("Knee Pads", "PROD131", 150, 35.00, 25.00, suppliers.get(1), ProductTypes.SAFETY_EQUIPMENT),
                new Product("Respirator Mask", "PROD132", 50, 100.00, 75.00, suppliers.get(0), ProductTypes.SAFETY_EQUIPMENT),

                // Construction Material products
                new Product("Cement", "PROD133", 300, 5.00, 3.00, suppliers.get(1), ProductTypes.CONSTRUCTION_MATERIAL),
                new Product("Steel Bars", "PROD134", 100, 50.00, 40.00, suppliers.get(1), ProductTypes.CONSTRUCTION_MATERIAL),
                new Product("Sand", "PROD135", 500, 10.00, 7.00, suppliers.get(0), ProductTypes.CONSTRUCTION_MATERIAL),
                new Product("Bricks", "PROD136", 200, 50.00, 30.00, suppliers.get(2), ProductTypes.CONSTRUCTION_MATERIAL),
                new Product("Gravel", "PROD137", 150, 15.00, 10.00, suppliers.get(3), ProductTypes.CONSTRUCTION_MATERIAL),

                // Machinery products
                new Product("Excavator", "PROD138", 10, 25000.00, 18000.00, suppliers.get(0), ProductTypes.MACHINERY),
                new Product("Forklift", "PROD139", 5, 15000.00, 11000.00, suppliers.get(2), ProductTypes.MACHINERY),
                new Product("Bulldozer", "PROD140", 3, 45000.00, 35000.00, suppliers.get(4), ProductTypes.MACHINERY),
                new Product("Crane", "PROD141", 2, 100000.00, 75000.00, suppliers.get(3), ProductTypes.MACHINERY),
                new Product("Backhoe Loader", "PROD142", 8, 30000.00, 22000.00, suppliers.get(1), ProductTypes.MACHINERY)
        );

        // Add products to inventory
        for (Product product : products) {
            inventory.addProduct(product);
        }
    }

    private static CustomerOrder createOrder(Object[][] items, main.financial.FinancialReport report, InventoryManager inventory) {
        CustomerOrder order = new CustomerOrder(report, inventory);
        for (Object[] item : items) {
            String productId = (String) item[0];
            int quantity = (int) item[1];
            order.addItem(productId, quantity);
        }
        return order;
    }

    public static void customerOrders(FinancialReport report, InventoryManager inventory) {

        // Sample Orders (Creating order and adding products one by one)
        List<CustomerOrder> orders = Arrays.asList(
                createOrder(new Object[][] {
                        {"PROD123", 1}, {"PROD126", 2}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD124", 3}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD123", 10}, {"PROD128", 50}, {"PROD127", 100}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD125", 5}, {"PROD129", 2}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD124", 2}, {"PROD127", 10}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD129", 5}, {"PROD130", 3}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD136", 8}, {"PROD131", 9}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD129", 3}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD140", 2}, {"PROD123", 2}, {"PROD132", 4}, {"PROD130", 9}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD136", 3}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD142", 6}, {"PROD126", 2}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD125", 7}, {"PROD132", 10}, {"PROD129", 6}, {"PROD124", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD127", 10}, {"PROD135", 10}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD132", 6}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD139", 9}, {"PROD125", 10}, {"PROD126", 6}, {"PROD141", 2}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD132", 4}, {"PROD142", 5}, {"PROD129", 6}, {"PROD127", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD138", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD130", 4}, {"PROD124", 4}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD142", 2}, {"PROD127", 2}, {"PROD136", 6}, {"PROD135", 7}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD134", 6}, {"PROD133", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD125", 3}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD138", 4}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD126", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD129", 8}, {"PROD123", 1}, {"PROD137", 4}, {"PROD131", 4}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD139", 10}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD123", 10}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD131", 9}, {"PROD137", 2}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD123", 10}, {"PROD126", 10}, {"PROD132", 8}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD136", 7}, {"PROD129", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD135", 2}, {"PROD133", 3}, {"PROD136", 1}, {"PROD126", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD124", 8}, {"PROD141", 6}, {"PROD136", 1}, {"PROD131", 4}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD129", 8}, {"PROD135", 2}, {"PROD140", 5}, {"PROD126", 5}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD138", 1}, {"PROD125", 6}, {"PROD130", 6}, {"PROD135", 4}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD126", 6}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD132", 8}, {"PROD131", 8}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD129", 6}, {"PROD134", 7}, {"PROD125", 6}, {"PROD137", 2}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD142", 9}, {"PROD123", 7}, {"PROD129", 3}, {"PROD128", 2}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD135", 6}, {"PROD140", 3}, {"PROD134", 3}, {"PROD137", 9}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD142", 5}, {"PROD131", 8}, {"PROD129", 2}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD134", 8}, {"PROD135", 9}, {"PROD128", 4}, {"PROD130", 6}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD135", 9}, {"PROD140", 4}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD141", 4}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD126", 1}, {"PROD123", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD140", 2}, {"PROD130", 4}, {"PROD129", 8}, {"PROD133", 8}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD142", 4}, {"PROD129", 1}, {"PROD136", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD131", 3}, {"PROD139", 10}, {"PROD126", 5}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD125", 4}, {"PROD128", 10}, {"PROD131", 10}, {"PROD133", 6}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD124", 3}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD129", 5}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD135", 3}, {"PROD137", 9}, {"PROD136", 2}, {"PROD140", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD142", 6}, {"PROD133", 10}, {"PROD123", 7}, {"PROD139", 7}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD131", 9}, {"PROD133", 9}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD129", 7}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD133", 4}, {"PROD134", 10}, {"PROD135", 7}, {"PROD138", 8}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD132", 1}, {"PROD134", 4}, {"PROD126", 5}, {"PROD140", 6}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD136", 8}, {"PROD129", 10}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD134", 6}, {"PROD135", 2}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD131", 5}, {"PROD137", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD127", 7}, {"PROD124", 7}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD129", 9}, {"PROD128", 2}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD129", 5}, {"PROD132", 2}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD136", 6}, {"PROD127", 2}, {"PROD126", 9}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD139", 3}, {"PROD129", 8}, {"PROD126", 2}, {"PROD136", 5}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD124", 3}, {"PROD132", 3}, {"PROD130", 4}, {"PROD140", 10}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD138", 8}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD132", 6}, {"PROD129", 7}, {"PROD124", 6}, {"PROD141", 9}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD123", 7}, {"PROD136", 8}, {"PROD140", 9}, {"PROD135", 4}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD132", 7}, {"PROD123", 6}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD130", 5}, {"PROD139", 10}, {"PROD141", 6}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD125", 5}, {"PROD130", 3}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD123", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD123", 2}, {"PROD129", 10}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD139", 1}, {"PROD131", 8}, {"PROD127", 4}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD142", 9}, {"PROD140", 2}, {"PROD127", 5}, {"PROD124", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD132", 3}, {"PROD123", 10}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD123", 9}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD129", 6}, {"PROD133", 3}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD141", 5}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD133", 1}, {"PROD131", 9}, {"PROD124", 3}, {"PROD141", 3}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD140", 8}, {"PROD138", 10}, {"PROD131", 4}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD142", 2}, {"PROD127", 6}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD132", 7}, {"PROD135", 5}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD137", 5}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD137", 10}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD126", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD127", 4}, {"PROD129", 10}, {"PROD123", 5}, {"PROD135", 10}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD142", 10}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD131", 1}, {"PROD141", 3}, {"PROD123", 2}, {"PROD135", 10}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD136", 2}, {"PROD123", 8}, {"PROD132", 7}, {"PROD127", 9}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD127", 2}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD130", 7}, {"PROD142", 6}, {"PROD139", 3}, {"PROD133", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD132", 10}, {"PROD136", 10}, {"PROD133", 2}, {"PROD124", 7}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD137", 6}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD126", 4}, {"PROD123", 9}, {"PROD138", 1}, {"PROD124", 7}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD137", 3}, {"PROD135", 4}, {"PROD130", 8}, {"PROD132", 6}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD124", 4}, {"PROD133", 10}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD127", 2}, {"PROD139", 5}, {"PROD136", 7}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD129", 2}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD123", 7}, {"PROD134", 9}, {"PROD128", 1}, {"PROD132", 3}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD132", 8}, {"PROD138", 7}, {"PROD131", 2}, {"PROD125", 6}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD127", 3}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD126", 3}, {"PROD134", 1}, {"PROD133", 9}, {"PROD123", 1}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD124", 8}, {"PROD133", 2}, {"PROD125", 2}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD130", 7}, {"PROD125", 8}, {"PROD133", 4}, {"PROD123", 7}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD131", 3}, {"PROD141", 8}
                }, report, inventory),
                createOrder(new Object[][] {
                        {"PROD127", 5}, {"PROD141", 5}
                }, report, inventory)

        );

        // Process orders
        for (CustomerOrder order : orders) {
            order.completeOrder();  // Mark the order as complete
            order.deliverOrder();   // Mark the order as delivered
        }
    }

    public static void businessOrders(SupplierManager supplierManager, FinancialReport report, InventoryManager inventory) {
        System.out.println("WITHIN BUSINESS ORDERS");

        Object[][] businessOrderData = {
                { "ID-745", new Object[][] {
                        { "PROD123", 100 }, { "PROD124", 200 }, { "PROD128", 150 }, { "PROD142", 100 }
                }},
                { "ID-324", new Object[][] {
                        { "PROD137", 500 }, { "PROD130", 200 }
                }},
                { "ID-567", new Object[][] {
                        { "PROD136", 100 }, { "PROD139", 60 }, { "PROD127", 150 }
                }},
                { "ID-890", new Object[][] {
                        { "PROD141", 50 }, { "PROD133", 200 }
                }},
                { "ID-101", new Object[][] {
                        { "PROD140", 60 }, { "PROD125", 100 }, { "PROD132", 200 }
                }},
                { "ID-745", new Object[][] {
                        { "PROD138", 100 }, { "PROD129", 50 }
                }}
        };

        // Create all business orders
        for (Object[] data : businessOrderData) {
            String supplierId = (String) data[0];
            Object[][] items = (Object[][]) data[1];

            createBusinessOrder(supplierManager.getSupplier(supplierId), items, report, inventory);
        }
    }

    // Helper method to reduce repetition
    private static BusinessOrder createBusinessOrder(Supplier supplier, Object[][] items, FinancialReport report, InventoryManager inventory) {
        BusinessOrder order = new BusinessOrder(supplier, report, inventory);
        for (Object[] item : items) {
            order.addItem((String) item[0], (int) item[1]);
        }
        order.completeOrder();
        order.deliverOrder();
        return order;
    }
}
