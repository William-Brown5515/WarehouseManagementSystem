import java.time.LocalDate;

public class CustomerOrder extends BaseOrder {
    private Customer customer;

    public CustomerOrder(Customer customer, String orderID) {
        super(orderID);
        this.customer = customer;
    }

    public Customer getCustomer() { return customer; }

    @Override
    protected void recalculateTotalPrice() {
        double newTotal = 0.0;
        for (OrderedProduct orderedProduct : getOrderedProducts()) {
            newTotal += orderedProduct.getProduct().getCustomerPrice() * orderedProduct.getQuantity();
        }
        updateTotalPrice(newTotal);
    }

    @Override
    public void completeOrder() {
        // Cycle through the ordered products and adjust the stock accordingly
        for (OrderedProduct orderedProduct : getOrderedProducts()) {
            inventory.reduceStock(orderedProduct.getProduct().getProductID(), orderedProduct.getQuantity());
        }
        updatePayment(true);
        updateOrderStatus("Paid, being Delivered");
        updateOrderDate();
    }

    // A method to run when the order is delivered
    @Override
    public void deliverOrder() {
        updateDelivered(true);
        updateOrderStatus("Delivered");
    }
}
