import java.time.LocalDate;

public class OrderManager {
    private Order order;
    private InventoryManager inventory;

    public OrderManager( Customer customer, InventoryManager inventory ) {
        this.order = new Order(customer);
        this.inventory = inventory;
    }

    public boolean addItem( String ProductID, int Quantity ) {

        // Check whether the product exists
        Product product = inventory.findProductById(ProductID);

        if (product != null) {

            // Iterate through the products to check if the product has already been added to basket
            for (OrderedProduct orderedProduct : order.getOrderedProducts()) {
                if (orderedProduct.getProduct().getProductID().equals(ProductID)) {
                    // Update the ordered quantity and total price
                    orderedProduct.setQuantity(orderedProduct.getQuantity() + Quantity);
                    order.updateTotalPrice(order.getTotalPrice() + (product.getPrice() * Quantity));
                    return true;
                }
            }

            // Create a new orderedProduct object, update total price
            OrderedProduct orderedProduct = new OrderedProduct(product, Quantity);
            order.getOrderedProducts().add(orderedProduct);
            order.updateTotalPrice(order.getTotalPrice() + (product.getPrice() * Quantity));
            return true;
        }
        return false;
    }

    public boolean removeItem( String ProductID, Integer Quantity ) {

        // Check whether the product exists
        Product product = inventory.findProductById(ProductID);

        if (product != null) {

            // Iterate through the products to check if the product has already been added to basket
            for (OrderedProduct orderedProduct : order.getOrderedProducts()) {
                if (orderedProduct.getProduct().getProductID().equals(ProductID)) {
                    if (orderedProduct.getQuantity() <= Quantity) {
                        // Update the ordered quantity and total price
                        order.getOrderedProducts().remove(orderedProduct);
                        order.updateTotalPrice(order.getTotalPrice() - (product.getPrice() * Quantity));
                    } else {
                        // Update the ordered quantity and total price
                        orderedProduct.setQuantity(orderedProduct.getQuantity() - Quantity);
                        order.updateTotalPrice(order.getTotalPrice() - (product.getPrice() * Quantity));
                    }
                    order.updateTotalPrice(order.getTotalPrice() + (product.getPrice() * Quantity));
                    return true;
                }
            }
        }
        return false;
    }

    // A method to run when a payment has been made
    public void completeOrder() {
        // Cycle through the ordered products and adjust the stock accordingly
        for (OrderedProduct orderedProduct : order.getOrderedProducts()) {
            inventory.reduceStock(orderedProduct.getProduct().getProductID(), orderedProduct.getQuantity());
        }
        order.updatePayment(true);
        order.updateOrderStatus("Paid, being Delivered");
        order.updateOrderDate(LocalDate.ofEpochDay(System.currentTimeMillis()));
    }

    // A method to run when the order is delivered
    public void deliverOrder() {
        order.updateDelivered(true);
        order.updateOrderStatus("Delivered");
    }

}
