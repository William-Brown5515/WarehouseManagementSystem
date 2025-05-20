package UI;

import main.UI.SupplierOrderService;
import main.products.*;
import main.suppliers.*;
import main.order.*;
import main.financial.FinancialReport;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.io.ByteArrayInputStream;

public class SupplierOrderServiceTest {

    @Test
    public void testOrderFromSuppliers_AddAndCompleteOrder() {
        // Test adding multiple products to an order from a supplier and completing the order
        System.out.println("Running testOrderFromSuppliers_AddAndCompleteOrder...");

        // Setup managers and report
        SupplierManager supplierManager = new SupplierManager();
        InventoryManager inventory = new InventoryManager();
        OrderManager orderManager = new OrderManager();
        FinancialReport report = new FinancialReport();

        // Create supplier and add
        Supplier supplier = new Supplier("Supplier1", "supplier1@example.com", "123456");
        supplierManager.addSupplier(supplier);

        // Create products linked to supplier
        Product p1 = new Product("Product1", 10, 15.0, 10.0, supplier, "Type1");
        Product p2 = new Product("Product2", 5, 20.0, 12.0, supplier, "Type2");
        inventory.addProduct(p1);
        inventory.addProduct(p2);

        /*
         Simulate user input sequence:
         - Enter supplier ID
         - Add product p1 with quantity 2
         - Add product p2 with quantity 3
         - Complete order
        */

        String input = supplier.getSupplierID() + "\n" +  // supplier ID
                p1.getProductID() + "\n" +        // select p1 to add
                "2\n" +                          // quantity 2
                p2.getProductID() + "\n" +       // select p2 to add
                "3\n" +                          // quantity 3
                "complete\n";                    // complete order

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        SupplierOrderService.orderFromSuppliers(orderManager, inventory, report, supplierManager, scanner);

        // Check if order was added
        if (orderManager.getOrders().size() == 1) {
            BusinessOrder order = (BusinessOrder) orderManager.getOrders().get(0);
            boolean p1InOrder = order.currentQuantity(p1) == 2;
            boolean p2InOrder = order.currentQuantity(p2) == 3;
            if (p1InOrder && p2InOrder) {
                System.out.println("testOrderFromSuppliers_AddAndCompleteOrder passed.");
                return;
            }
        }

        System.out.println("testOrderFromSuppliers_AddAndCompleteOrder failed.");
    }
}