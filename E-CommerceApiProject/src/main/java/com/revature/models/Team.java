package com.revature.models;

public class Team {

    private int team_id;
    private String name;

    public Team (){

    }

    public Team(int team_id, String name) {
        this.team_id = team_id;
        this.name = name;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
