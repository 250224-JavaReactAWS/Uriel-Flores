package com.revature.models;

public class Jersey {
    private int jersey_id;
    private char size;
    private String specification;
    private double price;
    private int stock;
    private int review_id;
    private int team_id;

    public Jersey (){

    }

    public Jersey(char size, String specification, double price, int stock, int review_id, int team_id) {
        this.size = size;
        this.specification = specification;
        this.price = price;
        this.stock = stock;
        this.review_id = review_id;
        this.team_id = team_id;
    }

    public int getJersey_id() {
        return jersey_id;
    }

    public void setJersey_id(int jersey_id) {
        this.jersey_id = jersey_id;
    }

    public char getSize() {
        return size;
    }

    public void setSize(char size) {
        this.size = size;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
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

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }
}
