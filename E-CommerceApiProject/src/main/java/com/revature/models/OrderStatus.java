package com.revature.models;

public class OrderStatus {
    private int status_id;
    private Status status;

    public OrderStatus() {
    }

    public OrderStatus(int status_id, Status status) {
        this.status_id = status_id;
        this.status = status;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
