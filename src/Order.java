import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    // Initialise class attributes
    private String orderID;
    private List<OrderedProduct> orderedProducts;
    private double totalPrice;
    private LocalDate orderDate;
    private Customer customer;
    private String orderStatus;
    private boolean payment;
    private boolean delivered;

    // Constructor
    public Order(Customer customer) {
        this.orderedProducts = new ArrayList<OrderedProduct>();
        this.orderID = ""; // Something random here
        this.totalPrice = 0.0;
        this.orderDate = null;
        this.customer = customer;
        this.orderStatus = "Order in Progress";
        this.payment = false;
        this.delivered = false;
    }

    // Class getters
    public double getTotalPrice() { return totalPrice; }
    public LocalDate getOrderDate() { return orderDate; }
    public Customer getCustomer() { return customer; }
    public String getOrderStatus() { return orderStatus; }
    public List<OrderedProduct> getOrderedProducts() { return orderedProducts; }
    public boolean isPayment() { return payment; }
    public boolean isDelivered() { return delivered; }

    // Class setters
    public void updateTotalPrice(double price) { this.totalPrice += price; }
    public void updateOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public void updateOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
    public void updatePayment(boolean payment) { this.payment = payment; }
    public void updateDelivered(boolean delivered) { this.delivered = delivered; }

}
