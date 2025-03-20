package com.revature.models;

public class JerseyTypes {
    private int jersey_type_id;
    private JerseyType jerseyType;

    public JerseyTypes() {
    }

    public JerseyTypes(JerseyType jerseyType, int jersey_type_id) {
        this.jerseyType = jerseyType;
        this.jersey_type_id = jersey_type_id;
    }

    public int getJersey_type_id() {
        return jersey_type_id;
    }

    public void setJersey_type_id(int jersey_type_id) {
        this.jersey_type_id = jersey_type_id;
    }

    public JerseyType getJerseyType() {
        return jerseyType;
    }

    public void setJerseyType(JerseyType jerseyType) {
        this.jerseyType = jerseyType;
    }
}
