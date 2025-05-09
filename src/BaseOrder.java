import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class BaseOrder {

    private String orderID;
    private double totalPrice;
    private List<OrderedProduct> orderedProducts;
    private LocalDate orderDate;
    private String orderStatus;
    private boolean payment;
    private boolean delivered;
    protected InventoryManager inventory;

    public BaseOrder() {
        this.orderedProducts = new ArrayList<OrderedProduct>();
        this.orderID = UUID.randomUUID().toString();
        this.totalPrice = 0.0;
        this.orderDate = null;
        this.orderStatus = "Order in Progress";
        this.payment = false;
        this.delivered = false;
        this.inventory = new InventoryManager();
    }

    // Class getters
    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public boolean isPayment() {
        return payment;
    }

    public boolean isDelivered() {
        return delivered;
    }

    // Class setters
    public void updateTotalPrice(double price) {
        totalPrice += price;
    }

    public void updateOrderDate() {
        orderDate = LocalDate.now();
    }

    public void updateOrderStatus(String newOrderStatus) {
        orderStatus = newOrderStatus;
    }

    public void updatePayment(boolean paymentStatus) {
        payment = paymentStatus;
    }

    public void updateDelivered(boolean deliveredStatus) {
        delivered = deliveredStatus;
    }

    protected void addOrderedProduct(OrderedProduct newProduct) {
        orderedProducts.add(newProduct);
        recalculateTotalPrice();
    }

    protected void changeProductQuantity(OrderedProduct newProduct, int quantity) {
        for (OrderedProduct orderedProduct : orderedProducts) {
            if (orderedProduct.getProduct().equals(newProduct.getProduct())) {
                if (quantity != orderedProduct.getQuantity()) {
                    orderedProduct.getProduct().setQuantity(quantity);
                    recalculateTotalPrice();
                }
                return;
            }
        }
    }

    public boolean addItem(String ProductId, int Quantity) {
        Product product = inventory.findProductById(ProductId);
        if (product != null) {
            for (OrderedProduct orderedProduct : getOrderedProducts()) {
                if (orderedProduct.getProduct().getProductID().equals(ProductId)) {
                    changeProductQuantity(orderedProduct, Quantity);
                    recalculateTotalPrice();
                    return true;
                }
            }
            addOrderedProduct(new OrderedProduct(product, Quantity));
            recalculateTotalPrice();
            return true;
        }
        return false;
    }

    public boolean removeItem(String ProductID, Integer Quantity) {

        // Check whether the product exists
        Product product = inventory.findProductById(ProductID);

        if (product != null) {

            // Iterate through the products to check if the product has already been added to basket
            Iterator<OrderedProduct> iterator = getOrderedProducts().iterator();
            while (iterator.hasNext()) {
                OrderedProduct orderedProduct = iterator.next();
                if (orderedProduct.getProduct().getProductID().equals(ProductID)) {
                    if (orderedProduct.getQuantity() <= Quantity) {
                        // Update the ordered quantity
                        iterator.remove();
                    } else {
                        // Update the ordered quantity
                        orderedProduct.setQuantity(orderedProduct.getQuantity() - Quantity);
                    }
                    recalculateTotalPrice();
                    return true;
                }
            }
        }
        return false;
    }

    // Placeholders to utilise polymorphism
    protected void recalculateTotalPrice() {}
    protected void completeOrder() {}
    protected void deliverOrder() {}
}
