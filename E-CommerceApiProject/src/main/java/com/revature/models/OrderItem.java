package com.revature.models;

public class OrderItem {
    private int order_item_id;
    private int order_id;
    private int jersey_id;
    private int quantity;
    private double price;

    public OrderItem() {
    }

    public OrderItem(int order_item_id, int order_id, int jersey_id, int quantity, double price) {
        this.order_item_id = order_item_id;
        this.order_id = order_id;
        this.jersey_id = jersey_id;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(int order_item_id) {
        this.order_item_id = order_item_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getJersey_id() {
        return jersey_id;
    }

    public void setJersey_id(int jersey_id) {
        this.jersey_id = jersey_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
