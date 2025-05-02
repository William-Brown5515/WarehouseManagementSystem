import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private List<Product> products;

    public InventoryManager() {
        this.products = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void listProducts() {
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public Product findProductById(String id) {
        for (Product product : products) {
            if (product.getProductID().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public boolean removeProductById(String id) {
        for (Product product : products) {
            if (product.getProductID().equals(id)) {
                products.remove(product);
                return true;
            }
        }
        return false;
    }

    public boolean addStock(String productID, int quantity) {
        Product product = findProductById(productID);
        if (product != null) {
            product.setQuantity(product.getQuantity() + quantity);
            return true;
        } else {
            return false;
        }
    }

    public void lowStock() {
        ArrayList<Product> items = new ArrayList<Product>();
        for (Product product : products) {
            if (product.getQuantity() <= 10) {
                items.add(product);
            }
        }
        if (!items.isEmpty()) {
            System.out.println("These are the items with low stock:");
            for (Product product : items) {
                System.out.println ("Name: " + product.getName() + "    Quantity: " + product.getQuantity());
            }
        }
    }
}
