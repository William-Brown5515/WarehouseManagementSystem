package main.order;

import main.financial.FinancialReport;
import main.products.InventoryManager;
import main.suppliers.Supplier;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    private List<BaseOrder> orders;

    public OrderManager() {
        this.orders = new ArrayList<BaseOrder>();
    }

    public void createOrder(Supplier supplier, FinancialReport report, InventoryManager inventory) {
        BusinessOrder order = new BusinessOrder(supplier, report, inventory);
        orders.add(order);
    }

    public BaseOrder getOrderById(String id) {
        for (BaseOrder order : orders) {
            if (order.getOrderID().equals(id)) {
                return order;
            }
        }
        return null;
    }

    public void displayCustomerOrders() {
        for (BaseOrder order : orders) {
            if (order instanceof CustomerOrder) {
                printOrders((CustomerOrder) order);
            }
        }
    }

    public void displayBusinessOrders() {
        for (BaseOrder order : orders) {
            if (order instanceof BusinessOrder) {
                printOrders((BusinessOrder) order);
            }
        }
    }

    private void printOrders(BaseOrder order) {
        System.out.println("Order ID: " + order.getOrderID() + "Order Status: " + order.getOrderStatus() + "Order Date: " + order.getOrderDate());
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
                    arrivalOrders.add((BusinessOrder) order);
                }
            }
        }
        return arrivalOrders;
    }

}
