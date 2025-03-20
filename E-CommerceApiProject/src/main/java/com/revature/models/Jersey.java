package com.revature.models;

public class Jersey {
    private int jersey_id;
    private String size;
    private double price;
    private int stock;
    private int team_id;
    private int jersey_type_id;

    public Jersey (){

    }

    public Jersey(int jersey_id, String size, double price, int stock, int team_id, int jersey_type_id) {
        this.jersey_id = jersey_id;
        this.size = size;
        this.price = price;
        this.stock = stock;
        this.team_id = team_id;
        this.jersey_type_id = jersey_type_id;
    }

    public int getJersey_id() {
        return jersey_id;
    }

    public void setJersey_id(int jersey_id) {
        this.jersey_id = jersey_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getJersey_type_id() {
        return jersey_type_id;
    }

    public void setJersey_type_id(int jersey_type_id) {
        this.jersey_type_id = jersey_type_id;
    }
}
