public class OrderManager {
    private Order order;
    private InventoryManager inventory;

    public OrderManager( Customer customer, InventoryManager inventory ) {
        this.order = new Order(customer);
        this.inventory = inventory;
    }

    public boolean addItem( String ProductID) {

        Product product = inventory.findProductById(ProductID);

        if (product != null) {

            for (OrderedProduct orderedProduct : order.getOrderedProducts()) {
                if (orderedProduct.getProduct().getProductID().equals(ProductID)) {
                    orderedProduct.setQuantity(orderedProduct.getQuantity() + 1);
                    order.updateTotalPrice(order.getTotalPrice() + product.getPrice());
                    return true;
                }
            }

            OrderedProduct orderedProduct = new OrderedProduct(product, 1);
            order.getOrderedProducts().add(orderedProduct);
            order.updateTotalPrice(order.getTotalPrice() + product.getPrice());
            return true;
        }
        return false;
    }

    public boolean removeItem( String ProductID) {
        Product product = inventory.findProductById(ProductID);

        if (product != null) {
            for (OrderedProduct orderedProduct : order.getOrderedProducts()) {
                if (orderedProduct.getProduct().getProductID().equals(ProductID)) {
                    if (orderedProduct.getQuantity() == 1) {
                        order.getOrderedProducts().remove(orderedProduct);
                        order.updateTotalPrice(order.getTotalPrice() - product.getPrice());
                    } else {
                        orderedProduct.setQuantity(orderedProduct.getQuantity() - 1);
                        order.updateTotalPrice(order.getTotalPrice() - product.getPrice());
                    }
                    order.updateTotalPrice(order.getTotalPrice() + product.getPrice());
                    return true;
                }
            }
        }
        return false;
    }

    public void payOrder() {
        order.updatePayment(true);
        order.updateOrderStatus("Paid, being Delivered");
    }

    public void deliverOrder() {
        order.updateDelivered(true);
        order.updateOrderStatus("Delivered");
    }

}
