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
        // Verify that a new OrderManager initializes with an empty orders list
        orderManager = new OrderManager();
        assertTrue(orderManager.getOrders().isEmpty());
    }

    @Test
    void testAddOrder() {
        // Ensure addOrder properly adds a BusinessOrder to the orders list
        BusinessOrder order = new BusinessOrder(new FinancialReport(), new InventoryManager());

        OrderManager manager = new OrderManager();
        manager.addOrder(order);

        assertTrue(manager.getOrders().contains(order));
    }

    @Test
    void testAddOrder_nullThrows() {
        // Verify that adding a null order throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new OrderManager().addOrder(null));
    }

    @Test
    void testDisplayBusinessOrders() {
        // Check that displayBusinessOrders prints details of business orders correctly
        OrderManager manager = getOrderManagerForBusiness();

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
        // Ensure no output is printed when there are no business orders
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
        // Check that displayCustomerOrders prints details of customer orders correctly
        OrderManager manager = getOrderManagerForCustomer();

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
        // Ensure no output is printed when there are no customer orders
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        orderManager.displayCustomerOrders();

        System.setOut(originalOut);

        String output = outputStream.toString();
        assertTrue(output.isEmpty(), "Expected no output when no customer orders");
    }

    private static OrderManager getOrderManagerForBusiness() {
        // Create an OrderManager with one BusinessOrder containing one product with quantity 5
        OrderManager manager = new OrderManager();
        Supplier supplier = new Supplier("Test Supplier", "test@email.com", "00000000000");
        InventoryManager inventory = new InventoryManager();
        BusinessOrder order = new BusinessOrder(new FinancialReport(), inventory);

        manager.addOrder(order);
        Product product = new Product("TestProduct", 10, 6.0, 10.0, supplier, ProductTypes.CONSTRUCTION_MATERIAL);
        inventory.addProduct(product);
        order.addItem(product.getProductID(), 5);

        return manager;
    }

    private static OrderManager getOrderManagerForCustomer() {
        // Create an OrderManager with one CustomerOrder containing one product with quantity 5
        OrderManager manager = new OrderManager();
        Supplier supplier = new Supplier("Test Supplier", "test@email.com", "00000000000");
        InventoryManager inventory = new InventoryManager();
        CustomerOrder order = new CustomerOrder(new FinancialReport(), inventory);

        manager.addOrder(order);
        Product product = new Product("TestProduct", 10, 6.0, 10.0, supplier, ProductTypes.CONSTRUCTION_MATERIAL);
        inventory.addProduct(product);
        order.addItem(product.getProductID(), 5);

        return manager;
    }

    @Test
    void testUpdateArrivalStatus() {
        // Verify that updateArrivalStatus returns only arrived and not delivered orders
        OrderManager manager = new OrderManager();
        InventoryManager inventory = new InventoryManager();

        BusinessOrder order1 = new BusinessOrder(new FinancialReport(), inventory) {
            @Override
            public Boolean isArrived() { return true; }
            @Override
            public boolean isDelivered() { return false; }
        };

        BusinessOrder order2 = new BusinessOrder(new FinancialReport(), inventory) {
            @Override
            public Boolean isArrived() { return true; }
            @Override
            public boolean isDelivered() { return true; }
        };

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
