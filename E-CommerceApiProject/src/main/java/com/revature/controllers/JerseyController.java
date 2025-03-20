package com.revature.controllers;

import com.revature.models.Jersey;
import com.revature.models.Role;
import com.revature.models.Team;
import com.revature.response.ErrorMessage;
import com.revature.services.JerseyService;
import io.javalin.http.Context;

import java.util.ArrayList;

public class JerseyController {

    private JerseyService jerseyService;

    public JerseyController(JerseyService jerseyService) {
        this.jerseyService = jerseyService;
    }


    //Get Jerseys by team name;
    public void getByTeamNameHandler (Context ctx){
        String teamName = ctx.formParam("name");
        ctx.json(jerseyService.getByTeamName(teamName));
    }

    //Update Jersey
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
        Role role = Role.valueOf(ctx.sessionAttribute("role"));
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
            ctx.status(400);
            errorMessages.add(new ErrorMessage("Could not found the Jersey"));
            ctx.json(errorMessages);
            return;
        }

        

        Jersey updatedJersey = ctx.bodyAsClass(Jersey.class);



    }


}
