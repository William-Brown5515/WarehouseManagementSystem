import main.financial.FinancialReport;
import main.order.*;
import main.order.OrderedProduct;
import main.products.InventoryManager;
import main.products.Product;
import main.products.ProductTypes;
import main.suppliers.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderManagerTest {

    private OrderManager orderManager;

    @BeforeEach
    void setUp() {
        orderManager = new OrderManager();
    }

    @Test
    void testAddOrder() {
        BusinessOrder order = new BusinessOrder(new Supplier("Test Supplier", "test@email.com", "00000000000"), new FinancialReport(), new InventoryManager()) {
            @Override
            public Boolean isArrived() {
                return true;
            }

            @Override
            public boolean isDelivered() {
                return false;
            }
        };

        OrderManager manager = new OrderManager();
        manager.addOrder(order);

        List<BusinessOrder> arrivedOrders = manager.updateArrivalStatus();
        assertTrue(arrivedOrders.contains(order));
    }

    @Test
    void testDisplayBusinessOrders_printsCorrectly() {
        OrderManager manager = getOrderManagerForBusiness();

        // Capture the system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        manager.displayBusinessOrders();
        System.setOut(originalOut);
        String output = outputStream.toString();

        assertTrue(output.contains("Order ID:"));
        assertTrue(output.contains("Order Status:"));
        assertTrue(output.contains("Product name: TestProduct"));
        assertTrue(output.contains("Quantity: 5"));
    }

    private static OrderManager getOrderManagerForBusiness() {
        OrderManager manager = new OrderManager();
        Supplier supplier = new Supplier("Test Supplier", "test@email.com", "00000000000");
        InventoryManager inventory = new InventoryManager();
        BusinessOrder order = new BusinessOrder(
                supplier,
                new FinancialReport(),
                inventory
        );

        manager.addOrder(order);
        Product product = new Product("TestProduct", 10, 6.0, 10.0, supplier, ProductTypes.CONSTRUCTION_MATERIAL);
        inventory.addProduct(product);
        order.addItem(product.getProductID(), 5);

        return manager;
    }

    @Test
    void testDisplayCustomerOrders_printsCorrectly() {
        OrderManager manager = getOrderManagerForCustomer();

        // Capture the system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        manager.displayCustomerOrders();
        System.setOut(originalOut);
        String output = outputStream.toString();

        assertTrue(output.contains("Order ID:"));
        assertTrue(output.contains("Order Status:"));
        assertTrue(output.contains("Product name: TestProduct"));
        assertTrue(output.contains("Quantity: 5"));
    }

    private static OrderManager getOrderManagerForCustomer() {
        OrderManager manager = new OrderManager();
        Supplier supplier = new Supplier("Test Supplier", "test@email.com", "00000000000");
        InventoryManager inventory = new InventoryManager();
        CustomerOrder order = new CustomerOrder(
                new FinancialReport(),
                inventory
        );

        manager.addOrder(order);
        Product product = new Product("TestProduct", 10, 6.0, 10.0, supplier, ProductTypes.CONSTRUCTION_MATERIAL);
        inventory.addProduct(product);
        order.addItem(product.getProductID(), 5);

        return manager;
    }

    @Test
    void testUpdateArrivalStatus_returnsOnlyArrivedAndUndeliveredBusinessOrders() {
        OrderManager manager = new OrderManager();

        Supplier supplier = new Supplier("Test Supplier", "test@email.com", "00000000000");
        InventoryManager inventory = new InventoryManager();

        // Order 1: Arrived and not delivered - should be included
        BusinessOrder order1 = new BusinessOrder(supplier, new FinancialReport(), inventory) {
            @Override
            public Boolean isArrived() { return true; }
            @Override
            public boolean isDelivered() { return false; }
        };

        // Order 2: Arrived and delivered - should NOT be included
        BusinessOrder order2 = new BusinessOrder(supplier, new FinancialReport(), inventory) {
            @Override
            public Boolean isArrived() { return true; }
            @Override
            public boolean isDelivered() { return true; }
        };

        // Order 3: Not arrived - should NOT be included
        BusinessOrder order3 = new BusinessOrder(supplier, new FinancialReport(), inventory) {
            @Override
            public Boolean isArrived() { return false; }
            @Override
            public boolean isDelivered() { return false; }
        };

        manager.addOrder(order1);
        manager.addOrder(order2);
        manager.addOrder(order3);

        List<BusinessOrder> arrivedOrders = manager.updateArrivalStatus();

        assertTrue(arrivedOrders.contains(order1));
        assertFalse(arrivedOrders.contains(order2));
        assertFalse(arrivedOrders.contains(order3));
        assertEquals(1, arrivedOrders.size());
    }
}
