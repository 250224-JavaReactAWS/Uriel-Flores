package com.revature.models;

public class JerseyType {
    private int jersey_type_id;
    private JerseyTypes jerseyType;

    public JerseyType() {
    }

    public JerseyType(int jersey_type_id, JerseyTypes jerseyType) {
        this.jerseyType = jerseyType;
        this.jersey_type_id = jersey_type_id;
    }

    public int getJersey_type_id() {
        return jersey_type_id;
    }

    public void setJersey_type_id(int jersey_type_id) {
        this.jersey_type_id = jersey_type_id;
    }

    public JerseyTypes getJerseyType() {
        return jerseyType;
    }

    public void setJerseyType(JerseyTypes jerseyType) {
        this.jerseyType = jerseyType;
    }
}
