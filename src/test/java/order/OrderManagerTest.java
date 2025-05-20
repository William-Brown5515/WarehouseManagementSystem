package order;

import main.financial.FinancialReport;
import main.order.*;
import main.products.InventoryManager;
import main.products.Product;
import main.products.ProductTypes;
import main.suppliers.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderManagerTest {

    private OrderManager orderManager;

    @BeforeEach
    void setUp() {
        orderManager = new OrderManager();
    }

    @Test
    void testOrderManager() {
        // Test that the order manager creates an empty array on instantiation
        orderManager = new OrderManager();
        assertTrue(orderManager.getOrders().isEmpty());
    }

    @Test
    void testAddOrder() {
        // Ensure an order is added to the order manager in addOrder
        BusinessOrder order = new BusinessOrder(new FinancialReport(), new InventoryManager());

        OrderManager manager = new OrderManager();
        manager.addOrder(order);

        assertTrue(manager.getOrders().contains(order));
    }

    @Test
    void testAddOrder_nullThrows() {
        // Ensure trying to add a null order result in a relevant exception
        assertThrows(IllegalArgumentException.class, () -> new OrderManager().addOrder(null));
    }

    @Test
    void testDisplayBusinessOrders() {
        // Ensure business orders are collected correctly
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

    @Test
    void testDisplayBusinessOrders_emptyOrder() {
        // Ensure output is blank when there are no orders
        OrderManager manager = new OrderManager();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        manager.displayBusinessOrders();
        System.setOut(originalOut);

        String output = outputStream.toString();
        assertTrue(output.isEmpty(), "Expected no output when no business orders");
    }

    @Test
    void testDisplayCustomerOrders() {
        // Ensure customer orders are collected correctly
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

    @Test
    void testDisplayCustomerOrders_emptyOrder() {
        // Ensure output is blank when there are no orders

        // Capture system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Run the method on an empty OrderManager (orders list is empty by default)
        orderManager.displayCustomerOrders();

        // Restore original System.out
        System.setOut(originalOut);

        // Check that nothing was printed
        String output = outputStream.toString();
        assertTrue(output.isEmpty(), "Expected no output when no customer orders");
    }

    private static OrderManager getOrderManagerForBusiness() {
        // Create an order manager and insert test values
        OrderManager manager = new OrderManager();
        Supplier supplier = new Supplier("Test Supplier", "test@email.com", "00000000000");
        InventoryManager inventory = new InventoryManager();
        BusinessOrder order = new BusinessOrder(
                new FinancialReport(),
                inventory
        );

        manager.addOrder(order);
        Product product = new Product("TestProduct", 10, 6.0, 10.0, supplier, ProductTypes.CONSTRUCTION_MATERIAL);
        inventory.addProduct(product);
        order.addItem(product.getProductID(), 5);

        return manager;
    }

    private static OrderManager getOrderManagerForCustomer() {
        // Create an order manager and insert test values
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
    void testUpdateArrivalStatus() {
        // Ensure that only orders that have arrived, and not delivered, are displayed
        OrderManager manager = new OrderManager();
        InventoryManager inventory = new InventoryManager();

        // Order 1: Arrived and not delivered - should be included
        BusinessOrder order1 = new BusinessOrder(new FinancialReport(), inventory) {
            @Override
            public Boolean isArrived() { return true; }
            @Override
            public boolean isDelivered() { return false; }
        };

        // Order 2: Arrived and delivered - should NOT be included
        BusinessOrder order2 = new BusinessOrder(new FinancialReport(), inventory) {
            @Override
            public Boolean isArrived() { return true; }
            @Override
            public boolean isDelivered() { return true; }
        };

        // Order 3: Not arrived - should NOT be included
        BusinessOrder order3 = new BusinessOrder(new FinancialReport(), inventory) {
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
