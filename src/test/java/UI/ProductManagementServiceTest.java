package UI;

import main.UI.ProductManagementService;
import main.products.InventoryManager;
import main.products.Product;
import main.products.ProductTypes;
import main.suppliers.Supplier;
import main.suppliers.SupplierManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ProductManagementServiceTest {

    private InventoryManager inventory;
    private SupplierManager supplierManager;
    private Supplier supplier;

    @BeforeEach
    void setup() {
        inventory = new InventoryManager();
        supplierManager = new SupplierManager();
        supplier = new Supplier("Supplier A", "supplier@example.com", "1234567890");
        supplierManager.addSupplier(supplier);
    }

    @Test
    void createProduct_successfulFlow_addsProduct() {
        // Test creating a product successfully adds it to inventory
        String input = String.join("\n",
                "Test Product",
                "10",
                "20.5",
                "15.0",
                supplier.getSupplierID(),
                "1"
        );
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ProductManagementService.createProduct(scanner, inventory, supplierManager);

        assertEquals(1, inventory.getProducts().size());
        Product product = inventory.getProducts().get(0);
        assertEquals("Test Product", product.getName());
        assertEquals(10, product.getQuantity());
        assertEquals(20.5, product.getCustomerPrice());
        assertEquals(15.0, product.getSupplierPrice());
        assertEquals(supplier, product.getSupplier());
        assertEquals(ProductTypes.TYPES.get(0), product.getType());
    }

    @Test
    void createProduct_cancelSupplierSelection_doesNotAddProduct() {
        // Test cancelling supplier selection prevents product creation
        String input = String.join("\n",
                "Test Product",
                "10",
                "20.5",
                "15.0",
                "cancel"
        );
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ProductManagementService.createProduct(scanner, inventory, supplierManager);

        assertTrue(inventory.getProducts().isEmpty());
    }

    @Test
    void selectSupplier_validAndInvalidInputs() {
        // Test supplier selection retries on invalid input then accepts valid input
        String input = String.join("\n",
                "invalid",
                supplier.getSupplierID()
        );
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Supplier selected = ProductManagementService.selectSupplier(supplierManager, scanner);
        assertEquals(supplier, selected);
    }

    @Test
    void selectSupplier_cancelReturnsNull() {
        // Test supplier selection returns null when cancelled
        Scanner scanner = new Scanner(new ByteArrayInputStream("cancel\n".getBytes()));
        Supplier selected = ProductManagementService.selectSupplier(supplierManager, scanner);
        assertNull(selected);
    }

    @Test
    void selectProductType_validAndInvalidInputs() {
        // Test product type selection handles invalid inputs then selects valid type
        String input = String.join("\n",
                "0",
                "100",
                "abc",
                "1"
        );
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        String selectedType = ProductManagementService.selectProductType(scanner);
        assertEquals(ProductTypes.TYPES.get(0), selectedType);
    }

    @Test
    void deleteProduct_existingProduct_deletesProduct() {
        // Test deleting an existing product removes it from inventory
        Product product = new Product("DeleteMe", 5, 10, 7, supplier, ProductTypes.TYPES.get(0));
        inventory.addProduct(product);

        String input = String.join("\n",
                product.getProductID(),
                "cancel"
        );
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ProductManagementService.deleteProduct(inventory, scanner);

        assertTrue(inventory.getProducts().isEmpty());
    }

    @Test
    void deleteProduct_nonExistingProduct_retriesUntilCancel() {
        // Test deleting a non-existing product retries until cancelled and does not delete
        Product product = new Product("KeepMe", 5, 10, 7, supplier, ProductTypes.TYPES.get(0));
        inventory.addProduct(product);

        String input = String.join("\n",
                "invalidID",
                "cancel"
        );
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ProductManagementService.deleteProduct(inventory, scanner);

        assertEquals(1, inventory.getProducts().size());
    }

    @Test
    void modifyProduct_modifyNameAndStockAndPrices() {
        // Test modifying product details updates product attributes correctly
        Product product = new Product("OldName", 5, 10, 7, supplier, ProductTypes.TYPES.get(0));
        inventory.addProduct(product);

        String input = String.join("\n",
                product.getProductID(),
                "1",
                "NewName",
                "2",
                "20",
                "3",
                "30.5",
                "4",
                "25.5",
                "5",
                "cancel"
        );
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ProductManagementService.modifyProduct(inventory, scanner);

        assertEquals("NewName", product.getName());
        assertEquals(20, product.getQuantity());
        assertEquals(30.5, product.getCustomerPrice());
        assertEquals(25.5, product.getSupplierPrice());
    }

    @Test
    void modifyProduct_invalidProductID_andCancel() {
        // Test modifying product with invalid ID then cancelling does not throw exceptions
        String input = String.join("\n",
                "invalidID",
                "cancel"
        );
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ProductManagementService.modifyProduct(inventory, scanner);
        // No exception means test passed
    }
}
