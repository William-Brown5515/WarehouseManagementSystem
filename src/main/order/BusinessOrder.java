package main.order;

import main.*;

import java.time.LocalDateTime;

public class BusinessOrder extends BaseOrder {
    private Supplier supplier;

    public BusinessOrder(Supplier supplier, String orderID, FinancialReport report, InventoryManager inventory) {
        super(orderID, report, inventory);
        this.supplier = supplier;
    }

    public Supplier getSupplier() { return supplier; }

    @Override
    protected void recalculateTotalPrice() {
        double newTotal = 0;
        for (OrderedProduct orderedProduct : getOrderedProducts()) {
            Product product = orderedProduct.getProduct();
            int quantity = orderedProduct.getQuantity();
            double supplierPrice = product.getSupplierPrice();
            newTotal += supplierPrice * quantity;
        }
        updateTotalPrice(newTotal);
    }

    public void addItem(String ProductId, int Quantity) {
        Product product = inventory.findProductById(ProductId);
        if (product != null) {
            for (OrderedProduct orderedProduct : getOrderedProducts()) {
                if (orderedProduct.getProduct().getProductID().equals(ProductId)) {
                    changeProductQuantity(orderedProduct, Quantity);
                    recalculateTotalPrice();
                    return;
                }
            }
            addOrderedProduct(new OrderedProduct(product, Quantity));
            recalculateTotalPrice();
        }
    }

    public void completeOrder() {
        updatePayment(true);
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
        updateDelivered(true);
        updateOrderStatus("Delivered");
    }

    public boolean deliveryTime() {
        LocalDateTime now = LocalDateTime.now();
        if (java.time.Duration.between(getOrderDate(), now).toMinutes() >= 1) {
            updateOrderStatus("Arrived");
            return true;
        }
        return false;
    }
}
