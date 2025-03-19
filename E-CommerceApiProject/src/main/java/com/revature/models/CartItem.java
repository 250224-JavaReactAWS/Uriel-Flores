package com.revature.models;

public class CartItem {

    private int cart_item_id;
    private int user_id;
    private int jersey_id;
    private int quantity;

    public CartItem() {
    }

    public CartItem(int cart_item_id, int user_id, int jersey_id, int quantity) {
        this.cart_item_id = cart_item_id;
        this.user_id = user_id;
        this.jersey_id = jersey_id;
        this.quantity = quantity;
    }

    public int getCart_item_id() {
        return cart_item_id;
    }

    public void setCart_item_id(int cart_item_id) {
        this.cart_item_id = cart_item_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
}
