package main.order;

import main.products.InventoryManager;
import main.suppliers.Supplier;
import main.financial.FinancialReport;
import main.products.Product;

import java.time.LocalDateTime;

public class BusinessOrder extends BaseOrder {
    private Supplier supplier;
    private Boolean arrived;

    public BusinessOrder(Supplier supplier, FinancialReport report, InventoryManager inventory) {
        super(report, inventory);
        this.supplier = supplier;
        this.arrived = false;
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
        Product product = inventory.getProductById(ProductId);
        if (product != null) {
            addOrderedProduct(new OrderedProduct(product, Quantity));
            recalculateTotalPrice();
        }
    }

    public void completeOrder() {
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

    public Boolean isArrived() {
        LocalDateTime now = LocalDateTime.now();
        if (java.time.Duration.between(getOrderDate(), now).toMinutes() >= 1) {
            arrived = true;
            return true;
        }
        return false;
    }
}
