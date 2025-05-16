package main.order;

import main.products.InventoryManager;
import main.financial.FinancialReport;
import main.products.Product;

import java.time.LocalDateTime;

public class BusinessOrder extends BaseOrder {

    public BusinessOrder(FinancialReport report, InventoryManager inventory) {
        super(report, inventory);
    }

    @Override
    protected void recalculateTotalPrice() {
        double newTotal = 0;
        // Cycle through each ordered product and add the prices to the total
        for (OrderedProduct orderedProduct : getOrderedProducts()) {
            Product product = orderedProduct.getProduct();
            int quantity = orderedProduct.getQuantity();
            double supplierPrice = product.getSupplierPrice();
            newTotal += supplierPrice * quantity;
        }
        // Set the new total price
        updateTotalPrice(newTotal);
    }

    public void addItem(String ProductId, int Quantity) {
        // Check if quantity is greater than 0
        if (Quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        // Ensure the product exists
        Product product = inventory.getProductById(ProductId);
        if (product == null) {
            throw new IllegalArgumentException("Product with id " + ProductId + " already exists");
        }

        // Add the product to the order and recalculate the price
        addOrderedProduct(new OrderedProduct(product, Quantity));
        recalculateTotalPrice();
    }

    public void completeOrder() {
        // Update the status when the order is confirmed
        updateOrderStatus("Paid, being Delivered");
        updateOrderDate();
    }

    // A method to run when the order is delivered
    @Override
    public void deliverOrder() {
        getReport().orderCost(getTotalPrice());
        // Cycle through the ordered products and adjust the stock accordingly
        for (OrderedProduct orderedProduct : getOrderedProducts()) {
            inventory.addStock(orderedProduct.getProduct().getProductID(), orderedProduct.getQuantity());
        }

        // Update relevant fields for future queries
        updateDelivered(true);
        updateOrderStatus("Delivered");
    }

    public Boolean isArrived() {
        // Collect the order date, and ensure it has been defined
        LocalDateTime orderDate = getOrderDate();
        if (orderDate == null) {
            return false;
        }

        // Confirm whether the order has arrived and is ready for delivery to be completed
        LocalDateTime now = LocalDateTime.now();
        return java.time.Duration.between(getOrderDate(), now).toMinutes() >= 1;
    }
}
