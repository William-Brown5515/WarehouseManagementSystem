package main.order;

import main.products.Product;

public class OrderedProduct {

    private Product product;
    private int quantity;

    public OrderedProduct(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.product = product;
        this.quantity = quantity;
    }

    // Class getters and setters
    public Product getProduct() { return product; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product Name: " + product.getName() + " Product ID: " + product.getProductID() + " Quantity: " + quantity;
    }}
