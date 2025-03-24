package com.revature.models;


import java.sql.Timestamp;

public class Order {
    private int order_id;
    private int user_id;
    private double total_price;
    private int status_id;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Order() {
    }

    public Order(int order_id, int user_id, double total_price,Timestamp created_at, Timestamp updated_at, int statusId) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.total_price = total_price;
        this.status_id = statusId;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getStatus() {
        return status_id;
    }

    public void setStatus(int status) {
        this.status_id = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
