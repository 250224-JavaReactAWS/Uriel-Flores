package com.revature.models;

import java.time.LocalDateTime;

public class Review {
    private int review_id;
    private String title;
    private String content;
    private int rate;
    private int user_id;
    private int jersey_id;
    private LocalDateTime created_at;


    public Review() {
    }

    public Review(int review_id, String title, String content, int rate, int user_id, int jersey_id, LocalDateTime created_at) {
        this.review_id = review_id;
        this.title = title;
        this.content = content;
        this.rate = rate;
        this.user_id = user_id;
        this.jersey_id = jersey_id;
        this.created_at = created_at;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
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

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
