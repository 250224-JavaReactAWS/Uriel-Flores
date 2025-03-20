package com.revature.services;

import com.revature.models.Jersey;
import com.revature.repos.JerseyDAO;
import com.revature.repos.JerseyDAOImpl;

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

    //Method to update Jersey
    public Jersey updateJersey (Jersey updatedJersey){
        return jerseyDAO.update(updatedJersey);
    }

}
