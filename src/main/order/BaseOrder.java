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

    private final String orderID;
    private double totalPrice;
    private List<OrderedProduct> orderedProducts;
    private LocalDateTime orderDate;
    private String orderStatus;
    private boolean delivered;
    protected InventoryManager inventory;
    protected FinancialReport report;

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

    // A method to add a product to an order
    protected void addOrderedProduct(OrderedProduct newProduct) {
        // Ensure the product exists
        if (newProduct == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        // Add the product to the order and update the price
        orderedProducts.add(newProduct);
        recalculateTotalPrice();
    }

    // A method to change the quantity of a product already in the basket
    public void changeProductQuantity(Product product, int quantity) {
        for (OrderedProduct orderedProduct : orderedProducts) {
            if (orderedProduct.getProduct().equals(product)) {
                int oldQuantity = orderedProduct.getQuantity();
                if (oldQuantity < quantity) {
                    inventory.reduceStock(orderedProduct.getProduct().getProductID(), quantity - oldQuantity);
                } else if (oldQuantity > quantity) {
                    inventory.addStock(orderedProduct.getProduct().getProductID(), oldQuantity - quantity);
                }
                orderedProduct.setQuantity(quantity);
                return;
            }
        }
        // Throw error if the product does not exist
        throw new IllegalArgumentException("Product not found in the order");
    }

    // Abstract class as this is different for different order types
    public abstract void addItem(String ProductId, int Quantity);

    public void removeItem(String ProductID) {
        // Check whether the product exists
        Product product = inventory.getProductById(ProductID);

        // Use an iterator, rather than a for loop, to avoid concurrentModificationException
        Iterator<OrderedProduct> iterator = orderedProducts.iterator();
        while (iterator.hasNext()) {
            OrderedProduct orderedProduct = iterator.next();
            if (orderedProduct.getProduct().equals(product)) {
                // Remove the product from the order, update stock level and update the price
                inventory.addStock(orderedProduct.getProduct().getProductID(), orderedProduct.getQuantity());
                iterator.remove();
                recalculateTotalPrice();
                return;
            }
        }
    }

    // A method which displays the current order information
    public void currentOrder() {
        // Checks if the order is empty
        if (orderedProducts.isEmpty()) {
            System.out.println("Your order is empty");
        } else {
            // Loops through products and displays the information
            for (OrderedProduct product : orderedProducts) {
                System.out.println("Product name: " + product.getProduct().getName() + ", Quantity: " + product.getQuantity());
            }
        }
    }

    // A method to check the current quantity of a product in the order
    public int currentQuantity (Product product) {
        if (product == null) return 0;
        for (OrderedProduct orderedProduct : orderedProducts) {
            if (orderedProduct.getProduct().getProductID().equals(product.getProductID())) {
                return orderedProduct.getQuantity();
            }
        }
        // Returns 0 if product not found
        return 0;
    }

    // Placeholders to utilise polymorphism
    protected void recalculateTotalPrice() {}
    protected void completeOrder() {}
    protected void deliverOrder() {}
}
