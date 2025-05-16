package main.order;

import main.products.InventoryManager;
import main.financial.FinancialReport;
import main.products.Product;

public class CustomerOrder extends BaseOrder {

    public CustomerOrder(FinancialReport report, InventoryManager inventory) {
        super(report, inventory);
    }

    @Override
    protected void recalculateTotalPrice() {
        // Cycle through each ordered product and add the prices to the total
        double newTotal = 0.0;
        for (OrderedProduct orderedProduct : getOrderedProducts()) {
            newTotal += orderedProduct.getProduct().getCustomerPrice() * orderedProduct.getQuantity();
        }
        // Set the new total price
        updateTotalPrice(newTotal);
    }

    @Override
    public void addItem(String productId, int quantity) {
        // Ensure the product exists
        Product product = inventory.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        // Ensure the quantity requested is possible in terms of stock level
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product " + product.getName());
        }
        // Ensure the quantity is above 0
        if (quantity <= 0) {
            throw new IllegalArgumentException("Negative quantity for product " + product.getName());
        }

        // Add product to the order basket
        addOrderedProduct(new OrderedProduct(product, quantity));
        inventory.reduceStock(productId, quantity);
        recalculateTotalPrice();
    }

    @Override
    public void completeOrder() {
        getReport().orderRevenue(getTotalPrice());
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
