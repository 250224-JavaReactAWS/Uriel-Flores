package com.revature.services;

import com.revature.models.Team;
import com.revature.repos.TeamDAO;

public class TeamService {

    private TeamDAO teamDAO;

    public TeamService(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    //Method to add a new Team
    public Team addNewTeam (Team newTeam){
        return teamDAO.create(newTeam);
    }

    //Method to validate if a Team already exists in DB
    public boolean isTeamNameAvailable (String teamName){
        Team team = teamDAO.getByTeamName(teamName);
        //Returns true if there is not any team with that name
        return team == null;
    }

    //Method to update a Team
    public Team updateTeam (Team updatedTeam){
        return teamDAO.update(updatedTeam);
    }

    //Method to get Team by ID
    public Team getTeamById(int id){
        return teamDAO.getById(id);
    }

    //Method to delete a Team
    public boolean deleteTeambyId (int id){
        return teamDAO.deleteById(id);
    }


}
