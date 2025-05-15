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

    protected void changeProductQuantity(OrderedProduct orderedProduct, int quantity) {
        if (quantity != orderedProduct.getQuantity()) {
            orderedProduct.setQuantity(quantity + orderedProduct.getQuantity());
            recalculateTotalPrice();
        }
    }

    public abstract void addItem(String ProductId, int Quantity);

    public boolean removeItem(String ProductID, Integer Quantity) {

        // Check whether the product exists
        Product product = inventory.getProductById(ProductID);

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

    public void currentOrder() {
        for (OrderedProduct product: orderedProducts) {
            System.out.println("Product name: " + product.getProduct().getName() + ", Quantity: " + product.getQuantity());
        }
    }

    // Placeholders to utilise polymorphism
    protected void recalculateTotalPrice() {}
    protected void completeOrder() {}
    protected void deliverOrder() {}
}
