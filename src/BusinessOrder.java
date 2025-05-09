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

            // Debugging line
            System.out.println("Product: " + product.getName() + ", Quantity: " + quantity + ", Supplier Price: " + supplierPrice);

            newTotal += supplierPrice * quantity;
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
        getReport().orderCost(getTotalPrice());
        System.out.println("TOTAL PRICE: "+ getTotalPrice());
        // Cycle through the ordered products and adjust the stock accordingly
        for (OrderedProduct orderedProduct : getOrderedProducts()) {
            inventory.addStock(orderedProduct.getProduct().getProductID(), orderedProduct.getQuantity());
        }
        updateDelivered(true);
        updateOrderStatus("Delivered");
    }
}
