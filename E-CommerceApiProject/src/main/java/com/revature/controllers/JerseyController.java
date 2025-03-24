package com.revature.controllers;

import com.revature.models.Jersey;
import com.revature.models.Role;
import com.revature.response.ErrorMessage;
import com.revature.services.JerseyService;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JerseyController {

    private JerseyService jerseyService;

    public JerseyController(JerseyService jerseyService) {
        this.jerseyService = jerseyService;
    }


    //Get Jerseys by team name;
    public void getByTeamNameHandler (Context ctx){
        String teamName = ctx.formParam("name");

        //Validate that the list is not empty
        if (jerseyService.getByTeamName(teamName).isEmpty()){
            ctx.status(204);
            ctx.json("There is not any team with that name");
            return;
        }

        ctx.json(jerseyService.getByTeamName(teamName));
    }

    //Get all jerseys
    public void getAllJerseysHandler (Context ctx){

        List<Jersey> allJerseys = jerseyService.getAllJerseys();
        ArrayList<ErrorMessage> errorMessages = new ArrayList<>();

        if (allJerseys.isEmpty()){
            ctx.status(204);
            return;
        }

        ctx.status(200);
        ctx.json(allJerseys);

    }

    //Get details of a Jersey
    public void getJerseyDetailsHandler (Context ctx){
        ArrayList<ErrorMessage> errorMessages = new ArrayList<>();

        // Validate that the user is logged in
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null) {
            ctx.status(401);
            errorMessages.add(new ErrorMessage("You must be logged in to view this endpoint"));
            ctx.json(errorMessages);
            return;
        }

        //Get Jersey by Id
        Jersey jersey = jerseyService.getById(Integer.parseInt(ctx.pathParam("id")));

        //Validate that the jerseys exists
        if (jersey == null){
            ctx.status(404);
            errorMessages.add(new ErrorMessage("Page Not Found!"));
            ctx.json(errorMessages);
            return;
        }

        ctx.status(200);
        ctx.json(jersey);


    }

    //Add new Jersey (ADMIN function)
    public void addNewJerseyHandler (Context ctx){
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

        //Take in the user object
        Jersey requestJersey = ctx.bodyAsClass(Jersey.class);

        //TODO Validation process of the request Jersey


        //Add Jersey
        Jersey registeredJersey = jerseyService.addNewJersey(
                requestJersey.getSize(),
                requestJersey.getPrice(),
                requestJersey.getStock(),
                requestJersey.getTeam_id(),
                requestJersey.getJersey_type_id()
        );

        //If something fails we'll report a server side error
        if (registeredJersey == null){
            ctx.status(500);
            errorMessages.add(new ErrorMessage("Something went wrong!"));
            ctx.json(errorMessages);
            return;
        }

        ctx.status(201);
        ctx.json(registeredJersey);


    }

    //Update Jersey (ADMIN function)
    public void updateJersey (Context ctx){
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
        //Get the ID from the path
        int id = Integer.parseInt(ctx.pathParam("id"));

        //Get the Jersey stored in database
        Jersey storedJersey = jerseyService.getById(id);

        //Validate that the ID exists
        if (storedJersey == null){
            ctx.status(404);
            errorMessages.add(new ErrorMessage("Could not found the Jersey"));
            ctx.json(errorMessages);
            return;
        }

        //Take in the user object
        Jersey requestJersey = ctx.bodyAsClass(Jersey.class);

        //Update Jersey with synchronized Jersey
        Jersey updatedJersey = jerseyService.updateJersey(storedJersey.synchronize(requestJersey));

        //If something fails we'll report a server side error
        if (updatedJersey == null){
            ctx.status(500);
            errorMessages.add(new ErrorMessage("Something went wrong!"));
            ctx.json(errorMessages);
            return;
        }

        ctx.status(201);
        ctx.json(updatedJersey);

    }

    // Delete a specific jersey (ADMIN function)
    public void deleteJerseyByIdHandler (Context ctx){
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

        //Validate that the jersey exists
        if (jerseyService.getById(id) == null){
            ctx.status(404);
            errorMessages.add(new ErrorMessage("Page Not Found"));
            ctx.json(errorMessages);
            return;
        }

        //Delete the Jersey and if something fails we'll report a server side error
        if(!jerseyService.deleteJerseyById(id)){
            ctx.status(500);
            errorMessages.add(new ErrorMessage("Something went wrong!"));
            ctx.json(errorMessages);
            return;
        }

        ctx.status(200);
        ctx.json("Operation completed");

    }



}
