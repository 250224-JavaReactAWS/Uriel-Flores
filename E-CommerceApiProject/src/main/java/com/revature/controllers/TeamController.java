package com.revature.controllers;

import com.revature.models.Role;
import com.revature.models.Team;
import com.revature.response.ErrorMessage;
import com.revature.services.TeamService;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Objects;

public class TeamController {
    private final TeamService teamService;
    private final Logger logger = LoggerFactory.getLogger(TeamController.class);

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }
    //Method to add a new Team (ADMIN only)
    public void addNewTeamHandler (Context ctx){
        ArrayList<ErrorMessage> errorMessages = new ArrayList<>();

        // Validate that the user is logged in
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null) {
            ctx.status(401);
            errorMessages.add(new ErrorMessage("You must be logged in to view this endpoint"));
            ctx.json(errorMessages);
            return;
        }
        //Validate that the user is ADMIN
        Role role = ctx.sessionAttribute("role");
        if (role != Role.ADMIN){
            ctx.status(403);
            errorMessages.add(new ErrorMessage("You don't have permission to view this endpoint"));
            ctx.json(errorMessages);
            return;
        }

        //Take in the requested team
        Team requestTeam = ctx.bodyAsClass(Team.class);

        //Take the new team name
        String teamName = requestTeam.getName();

        //Validate the availability of team name
        if(!teamService.isTeamNameAvailable(teamName)){
            ctx.status(409);
            errorMessages.add(new ErrorMessage("The club already exists"));
            ctx.json(errorMessages);
            return;
        }

        //Add new team
        Team registeredTeam = teamService.addNewTeam(requestTeam);

        //If something fails we'll report a server side error
        if (registeredTeam == null){
            ctx.status(500);
            errorMessages.add(new ErrorMessage("Something went wrong!"));
            ctx.json(errorMessages);
            return;
        }
        ctx.status(201);
        ctx.json(registeredTeam);
        logger.info("New team registered with name: " + registeredTeam.getName());

    }

    //Method to update a Team (ADMIN only)
    public void updateTeamHandler (Context ctx){
        ArrayList<ErrorMessage> errorMessages = new ArrayList<>();

        // Validate that the user is logged in
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null) {
            ctx.status(401);
            errorMessages.add(new ErrorMessage("You must be logged in to view this endpoint"));
            ctx.json(errorMessages);
            return;
        }
        //Validate that the user is ADMIN
        Role role = ctx.sessionAttribute("role");
        if (role != Role.ADMIN){
            ctx.status(403);
            errorMessages.add(new ErrorMessage("You don't have permission to view this endpoint"));
            ctx.json(errorMessages);
            return;
        }

        //Take in the team id
        int teamId = Integer.parseInt(ctx.pathParam("id"));

        //Validate that the team exists or not
        if (teamService.getTeamById(teamId) == null){
            ctx.status(404);
            errorMessages.add(new ErrorMessage("Page Not Found"));
            ctx.json(errorMessages);
            return;
        }

        String oldName = teamService.getTeamById(teamId).getName();

        //Take in the new team
        Team requestedTeam = ctx.bodyAsClass(Team.class);
        //Set missing value
        requestedTeam.setTeam_id(teamId);

        //Update the team
        Team updatedTeam = teamService.updateTeam(requestedTeam);

        //If something fails we'll report a server side error
        if (updatedTeam == null){
            ctx.status(500);
            errorMessages.add(new ErrorMessage("Something went wrong!"));
            ctx.json(errorMessages);
            return;
        }

        ctx.status(200);
        ctx.json(updatedTeam);
        logger.info("Team name updated from "+ oldName + " to: " + updatedTeam.getName());
    }

    //Method to delete a Team
    public void deleteTeamByIdHandler (Context ctx){
        ArrayList<ErrorMessage> errorMessages = new ArrayList<>();

        // Validate that the user is logged in
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null) {
            ctx.status(401);
            errorMessages.add(new ErrorMessage("You must be logged in to view this endpoint"));
            ctx.json(errorMessages);
            return;
        }
        //Validate that the user is ADMIN
        Role role = ctx.sessionAttribute("role");
        if (role != Role.ADMIN){
            ctx.status(403);
            errorMessages.add(new ErrorMessage("You don't have permission to view this endpoint"));
            ctx.json(errorMessages);
            return;
        }

        //Get ID from query string parameter
        int id = Integer.parseInt(Objects.requireNonNull(ctx.queryParam("id")));

        //Validate that the team exists or not
        if (teamService.getTeamById(id) == null){
            ctx.status(404);
            errorMessages.add(new ErrorMessage("Page Not Found"));
            ctx.json(errorMessages);
            return;
        }
        //Get old name
        String deletedTeamName = teamService.getTeamById(id).getName();

        //Delete the Team and if something fails we'll report a server side error
        if(!teamService.deleteTeambyId(id)){
            ctx.status(500);
            errorMessages.add(new ErrorMessage("Something went wrong!"));
            ctx.json(errorMessages);
            return;
        }

        ctx.status(200);
        ctx.json("Operation completed");
        logger.info("Deleted team with name: " + deletedTeamName);

    }
}
