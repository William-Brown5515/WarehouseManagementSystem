public class BusinessOrder extends BaseOrder {
    private Supplier supplier;

    public BusinessOrder(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getSupplier() { return supplier; }

    @Override
    protected void recalculateTotalPrice() {
        double newTotal = 0.0;
        for (OrderedProduct orderedProduct : getOrderedProducts()) {
            newTotal += orderedProduct.getProduct().getSupplierPrice() * orderedProduct.getQuantity();
        }
        updateTotalPrice(newTotal);
    }

    public void completeOrder() {
        updatePayment(true);
        updateOrderStatus("Paid, being Delivered");
        updateOrderDate();
    }

    // A method to run when the order is delivered
    @Override
    public void deliverOrder() {
        // Cycle through the ordered products and adjust the stock accordingly
        for (OrderedProduct orderedProduct : getOrderedProducts()) {
            inventory.addStock(orderedProduct.getProduct().getProductID(), orderedProduct.getQuantity());
        }
        updateDelivered(true);
        updateOrderStatus("Delivered");
    }
}
