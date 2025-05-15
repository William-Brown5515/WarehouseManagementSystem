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

    public List<CustomerOrder> getCustomerOrders() {
        List<CustomerOrder> customerOrders = new ArrayList<>();
        for (BaseOrder order : orders) {
            if (order instanceof CustomerOrder) {
                customerOrders.add((CustomerOrder) order);
            }
        }
        return customerOrders;
    }

    public List<BusinessOrder> getBusinessOrders() {
        List<BusinessOrder> businessOrders = new ArrayList<>();
        for (BaseOrder order : orders) {
            if (order instanceof BusinessOrder) {
                businessOrders.add((BusinessOrder) order);
            }
        }
        return businessOrders;
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
