package main.order;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    private List<BaseOrder> orders;

    public OrderManager() {
        this.orders = new ArrayList<BaseOrder>();
    }

    public void addOrder(BaseOrder order) {
        orders.add(order);
    }

    public void displayCustomerOrders() {
        for (BaseOrder order : orders) {
            if (order instanceof CustomerOrder) {
                printOrders(order);
            }
        }
    }

    public void displayBusinessOrders() {
        for (BaseOrder order : orders) {
            if (order instanceof BusinessOrder) {
                printOrders(order);
            }
        }
    }

    private void printOrders(BaseOrder order) {
        System.out.println("Order ID: " + order.getOrderID() + ", Order Status: " + order.getOrderStatus() + ", Order Date: " + order.getOrderDate());
        if (order.getOrderedProducts().isEmpty()) {
            System.out.println("This order is empty");
        } else {
            for (OrderedProduct product : order.getOrderedProducts()) {
                System.out.println("Product name: " + product.getProduct().getName() + ", Quantity: " + product.getQuantity());
            }
        }
    }

    public List<BusinessOrder> updateArrivalStatus() {
        List<BusinessOrder> arrivalOrders = new ArrayList<>();
        for (BaseOrder order : orders) {
            if (order instanceof BusinessOrder) {
                if (((BusinessOrder) order).isArrived()) {
                    if (!order.isDelivered()) {
                        arrivalOrders.add((BusinessOrder) order);
                    }
                }
            }
        }
        return arrivalOrders;
    }
}
