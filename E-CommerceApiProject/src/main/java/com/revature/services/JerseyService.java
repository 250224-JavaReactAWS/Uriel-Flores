package com.revature.services;

import com.revature.models.Jersey;
import com.revature.repos.JerseyDAO;


import java.util.List;

public class JerseyService {

    private JerseyDAO jerseyDAO;

    public JerseyService(JerseyDAO jerseyDAO) {
        this.jerseyDAO = jerseyDAO;
    }

    //Method to get Jerseys by team name
    public List<Jersey> getByTeamName (String teamName){
        return jerseyDAO.getByTeamName(teamName);
    }

    //Method to get Jersey by ID
    public Jersey getById (int id){
        return jerseyDAO.getById(id);
    }

    //Method to get all Jerseys
    public List<Jersey> getAllJerseys (){
        return jerseyDAO.getAll();
    }

    //Method to create Jersey
    public Jersey addNewJersey (String size, double price, int stock, int team_id, int jersey_type_id){
        Jersey newJersey = new Jersey(size,price,stock, team_id, jersey_type_id);

        //Save jersey into Database
        return jerseyDAO.create(newJersey);
    }

    //Method to update Jersey
    public Jersey updateJersey (Jersey updatedJersey){
        return jerseyDAO.update(updatedJersey);
    }

    //Method to delete Jersey
    public boolean deleteJerseyById(int id){
        return jerseyDAO.deleteById(id);
    }

}
