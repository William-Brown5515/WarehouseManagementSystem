package main.order;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    private List<BaseOrder> orders;

    public OrderManager() {
        this.orders = new ArrayList<BaseOrder>();
    }

    public void addOrder(BaseOrder order) {
        // Ensure the parameter is not null, then add to the list
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        orders.add(order);
    }

    public void displayCustomerOrders() {
        // Collect all customer orders and display them
        for (BaseOrder order : orders) {
            if (order instanceof CustomerOrder) {
                printOrders(order);
            }
        }
    }

    public void displayBusinessOrders() {
        // Collect all business orders and display them
        for (BaseOrder order : orders) {
            if (order instanceof BusinessOrder) {
                printOrders(order);
            }
        }
    }

    private void printOrders(BaseOrder order) {
        // Display order information, and inform the user if the order is empty
        System.out.println("Order ID: " + order.getOrderID() + ", Order Status: " + order.getOrderStatus() + ", Order Date: " + order.getOrderDate());
        if (order.getOrderedProducts().isEmpty()) {
            System.out.println("This order is empty");
        } else {
            // Cycle through the items in the order and display relevant information
            for (OrderedProduct product : order.getOrderedProducts()) {
                System.out.println("Product name: " + product.getProduct().getName() + ", Quantity: " + product.getQuantity());
            }
        }
    }

    public List<BusinessOrder> updateArrivalStatus() {
        // Identify any orders that have arrived, and are ready to be accepted
        List<BusinessOrder> arrivalOrders = new ArrayList<>();
        for (BaseOrder order : orders) {
            // Check if the order matches the relevant requirements to be ready for acceptance
            if (order instanceof BusinessOrder businessOrder &&
                    businessOrder.isArrived() && !businessOrder.isDelivered()) {
                arrivalOrders.add(businessOrder);
            }
        }
        return arrivalOrders;
    }
}
