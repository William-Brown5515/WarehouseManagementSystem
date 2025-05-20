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
        String input = String.join("\n",
                "Test Product",      // Product name
                "10",                // Stock level
                "20.5",              // Customer price
                "15.0",              // Supplier price
                supplier.getSupplierID(), // Supplier ID
                "1"                  // Product type (first in list)
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
        String input = String.join("\n",
                "invalid",            // Invalid ID
                supplier.getSupplierID()  // Valid ID
        );
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        Supplier selected = ProductManagementService.selectSupplier(supplierManager, scanner);
        assertEquals(supplier, selected);
    }

    @Test
    void selectSupplier_cancelReturnsNull() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("cancel\n".getBytes()));
        Supplier selected = ProductManagementService.selectSupplier(supplierManager, scanner);
        assertNull(selected);
    }

    @Test
    void selectProductType_validAndInvalidInputs() {
        String input = String.join("\n",
                "0",          // invalid (below range)
                "100",        // invalid (above range)
                "abc",        // invalid (non-integer)
                "1"           // valid
        );
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        String selectedType = ProductManagementService.selectProductType(scanner);
        assertEquals(ProductTypes.TYPES.get(0), selectedType);
    }

    @Test
    void deleteProduct_existingProduct_deletesProduct() {
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
        Product product = new Product("KeepMe", 5, 10, 7, supplier, ProductTypes.TYPES.get(0));
        inventory.addProduct(product);

        String input = String.join("\n",
                "invalidID",
                "cancel"
        );
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ProductManagementService.deleteProduct(inventory, scanner);

        // Product still exists since deletion cancelled
        assertEquals(1, inventory.getProducts().size());
    }

    @Test
    void modifyProduct_modifyNameAndStockAndPrices() {
        Product product = new Product("OldName", 5, 10, 7, supplier, ProductTypes.TYPES.get(0));
        inventory.addProduct(product);

        String input = String.join("\n",
                product.getProductID(),
                "1",            // Modify name
                "NewName",
                "2",            // Modify stock
                "20",
                "3",            // Modify customer price
                "30.5",
                "4",            // Modify supplier price
                "25.5",
                "5",            // Return to menu
                "cancel"        // Exit outer loop
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
        String input = String.join("\n",
                "invalidID",
                "cancel"
        );
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ProductManagementService.modifyProduct(inventory, scanner);
        // No exception means test passed
    }
}
