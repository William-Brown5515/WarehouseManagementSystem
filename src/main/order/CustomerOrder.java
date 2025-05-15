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
        double newTotal = 0.0;
        for (OrderedProduct orderedProduct : getOrderedProducts()) {
            newTotal += orderedProduct.getProduct().getCustomerPrice() * orderedProduct.getQuantity();
        }
        updateTotalPrice(newTotal);
    }

    @Override
    public void addItem(String ProductId, int Quantity) {
        Product product = inventory.getProductById(ProductId);
        if (product != null) {
            for (OrderedProduct orderedProduct : getOrderedProducts()) {
                if (orderedProduct.getProduct().getProductID().equals(ProductId)) {
                    if (product.getQuantity() >= (Quantity + orderedProduct.getQuantity())) {
                        changeProductQuantity(orderedProduct, Quantity);
                        recalculateTotalPrice();
                        inventory.reduceStock(ProductId, Quantity);
                        return;
                    }
                }
            }
            if (product.getQuantity() >= Quantity) {
                System.out.println("Ordered. Product: " + product + ", Quantity: " + Quantity + ", Amount in stock: " + product.getQuantity());
                addOrderedProduct(new OrderedProduct(product, Quantity));
                recalculateTotalPrice();
                inventory.reduceStock(ProductId, Quantity);
            }
        } else {
            System.out.println("Product not found");
        }
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
