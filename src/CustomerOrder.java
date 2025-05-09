import java.util.Iterator;

public class CustomerOrder extends BaseOrder {
    private Customer customer;

    public CustomerOrder(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() { return customer; }

    public boolean addItem( String ProductId, int Quantity ) {
        Product product = inventory.findProductById(ProductId);
        if (product != null) {
            for (OrderedProduct orderedProduct : getOrderedProducts()) {
                if (orderedProduct.getProduct().getProductID().equals(ProductId)) {
                    changeProductQuantity(orderedProduct, Quantity);
                    recalculateTotalPrice();
                    return true;
                }
            }
            addOrderedProduct(new OrderedProduct(product, Quantity));
            recalculateTotalPrice();
            return true;
        }
        return false;
    }

    public boolean removeItem( String ProductID, Integer Quantity ) {

        // Check whether the product exists
        Product product = inventory.findProductById(ProductID);

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

    // A method to run when the order is delivered
    public void deliverOrder() {
        updateDelivered(true);
        updateOrderStatus("Delivered");
    }
}
