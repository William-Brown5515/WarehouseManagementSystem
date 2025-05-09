import java.time.LocalDate;
import java.util.ArrayList;
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
    public double getTotalPrice() { return totalPrice; }
    public LocalDate getOrderDate() { return orderDate; }
    public String getOrderStatus() { return orderStatus; }
    public List<OrderedProduct> getOrderedProducts() { return orderedProducts; }
    public boolean isPayment() { return payment; }
    public boolean isDelivered() { return delivered; }

    // Class setters
    public void updateTotalPrice(double price) { totalPrice += price; }
    public void updateOrderDate() { orderDate = LocalDate.now(); }
    public void updateOrderStatus(String newOrderStatus) { orderStatus = newOrderStatus; }
    public void updatePayment(boolean paymentStatus) { payment = paymentStatus; }
    public void updateDelivered(boolean deliveredStatus) { delivered = deliveredStatus; }

    protected void addOrderedProduct(OrderedProduct newProduct) {
        orderedProducts.add(newProduct);
        totalPrice += newProduct.getProduct().getPrice() * newProduct.getQuantity();
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

    protected void recalculateTotalPrice() {
        double newTotal = 0.0;
        for (OrderedProduct orderedProduct : orderedProducts) {
            newTotal += orderedProduct.getProduct().getPrice() * orderedProduct.getQuantity();
        }
        updateTotalPrice(newTotal);
    }

}
