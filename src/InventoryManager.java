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
}
