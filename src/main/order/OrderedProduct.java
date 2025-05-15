package main.order;

import main.products.Product;

public class OrderedProduct {

    private Product product;
    private int quantity;

    public OrderedProduct(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "Product Name: " + product.getName() + " Product ID: " + product.getProductID() + " Quantity: " + quantity;
    }}
