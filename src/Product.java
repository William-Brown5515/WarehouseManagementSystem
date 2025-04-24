public class Product {

    private String name;
    private String productID;
    private int quantity;
    private double price;

    public Product(String name, String productID, int quantity, double price) {
        this.name = name;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
