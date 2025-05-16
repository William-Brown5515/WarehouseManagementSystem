package main.order;

import main.financial.FinancialReport;
import main.products.InventoryManager;
import main.products.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public abstract class BaseOrder {

    private String orderID;
    private double totalPrice;
    private List<OrderedProduct> orderedProducts;
    private LocalDateTime orderDate;
    private String orderStatus;
    private boolean delivered;
    protected InventoryManager inventory;
    private FinancialReport report;

    public BaseOrder(FinancialReport report, InventoryManager inventory) {
        this.orderedProducts = new ArrayList<OrderedProduct>();
        this.orderID = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.totalPrice = 0.0;
        this.orderDate = null;
        this.orderStatus = "Order in Progress";
        this.delivered = false;
        this.report = report;
        this.inventory = inventory;
    }

    // Class getters
    public double getTotalPrice() { return totalPrice; }

    public String getOrderID() { return orderID; }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public FinancialReport getReport() { return report; }

    // Class setters
    public void updateTotalPrice(double price) {
        totalPrice = price;
    }

    public void updateOrderDate() {
        orderDate = LocalDateTime.now();
    }

    public void updateOrderStatus(String newOrderStatus) {
        orderStatus = newOrderStatus;
    }

    public void updateDelivered(boolean deliveredStatus) {
        delivered = deliveredStatus;
    }

    protected void addOrderedProduct(OrderedProduct newProduct) {
        orderedProducts.add(newProduct);
        recalculateTotalPrice();
    }

    public void changeProductQuantity(Product product, int quantity) {
        for (OrderedProduct orderedProduct : orderedProducts) {
            if (orderedProduct.getProduct().equals(product)) {
                orderedProduct.setQuantity(quantity);
            }
        }
    }

    public abstract void addItem(String ProductId, int Quantity);

    public boolean removeItem(String ProductID) {

        // Check whether the product exists
        Product product = inventory.getProductById(ProductID);
        for (OrderedProduct orderedProduct : orderedProducts) {
            if (orderedProduct.getProduct().equals(product)) {
                orderedProducts.remove(orderedProduct);
                recalculateTotalPrice();
                return true;
            }
        }
        return false;
    }

    public void currentOrder() {
        if (orderedProducts.isEmpty()) {
            System.out.println("Your order is empty");
        } else {
            for (OrderedProduct product : orderedProducts) {
                System.out.println("Product name: " + product.getProduct().getName() + ", Quantity: " + product.getQuantity());
            }
        }
    }

    public int currentQuantity (Product product) {
        for (OrderedProduct orderedProduct : orderedProducts) {
            if (orderedProduct.getProduct().getProductID().equals(product.getProductID())) {
                return orderedProduct.getQuantity();
            }
        }
        return 0;
    }

    // Placeholders to utilise polymorphism
    protected void recalculateTotalPrice() {}
    protected void completeOrder() {}
    protected void deliverOrder() {}
}
