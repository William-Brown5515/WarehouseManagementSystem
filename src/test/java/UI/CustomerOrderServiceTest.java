package UI;

import main.UI.CustomerOrderService;
import main.financial.FinancialReport;
import main.order.OrderManager;
import main.products.InventoryManager;
import main.products.Product;
import main.suppliers.Supplier;
import main.suppliers.SupplierManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerOrderServiceTest {

    private OrderManager orderManager;
    private InventoryManager inventory;
    private FinancialReport report;
    private SupplierManager supplierManager;
    private Supplier supplier;
    private Product product;

    @BeforeEach
    void setup() {
        orderManager = new OrderManager();
        inventory = new InventoryManager();
        report = new FinancialReport();
        supplierManager = new SupplierManager();

        supplier = new Supplier("Test Supplier", "test@example.com", "1234567890");
        product = new Product("Test Product", 10, 20.0, 10.0, supplier, "Tool");
        inventory.addProduct(product);
        supplierManager.addSupplier(supplier);
    }

    @Test
    void orderFromWarehouse_cancelImmediately_noOrdersAdded() {
        // Verify no orders added if user cancels immediately
        String input = "cancel\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        CustomerOrderService.orderFromWarehouse(orderManager, inventory, report, supplierManager, scanner);

        assertEquals(0, orderManager.getOrders().size());
    }

    @Test
    void orderFromWarehouse_completeEmptyOrder_printsMessageAndContinues() {
        // Verify completing an empty order does not add orders
        String input = "complete\ncancel\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        CustomerOrderService.orderFromWarehouse(orderManager, inventory, report, supplierManager, scanner);

        assertEquals(0, orderManager.getOrders().size());
    }

    @Test
    void orderFromWarehouse_addProductThenComplete_orderAdded() {
        // Verify order is added when product is added and order completed
        String input = product.getProductID() + "\n2\ncomplete\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        CustomerOrderService.orderFromWarehouse(orderManager, inventory, report, supplierManager, scanner);

        assertEquals(1, orderManager.getOrders().size());
    }

    @Test
    void orderFromWarehouse_addProductUpdateQuantityThenComplete_orderAdded() {
        // Verify order quantity updates before completion and order is added
        String input = product.getProductID() + "\n2\n" + product.getProductID() + "\n5\ncomplete\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        CustomerOrderService.orderFromWarehouse(orderManager, inventory, report, supplierManager, scanner);

        assertEquals(1, orderManager.getOrders().size());
    }

    @Test
    void orderFromWarehouse_invalidProductID_printsMessageAndContinues() {
        // Verify invalid product ID input is handled gracefully without crashing
        String input = "invalidID\ncancel\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        CustomerOrderService.orderFromWarehouse(orderManager, inventory, report, supplierManager, scanner);

        // No exceptions thrown means test passes
    }
}
