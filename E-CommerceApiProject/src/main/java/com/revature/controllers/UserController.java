package com.revature.controllers;

import com.revature.models.User;
import com.revature.response.ErrorMessage;
import com.revature.services.UserService;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.Objects;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    public void getByUsernameHandler (Context ctx){

        String username = ctx.bodyAsClass(User.class).getUsername();
        ctx.json(userService.getByUsername(username));
    }

    public void getAllUsersHandler(Context ctx){
        // TO DO LOGIC TO VALIDATE TYPE OF USER.

        //Retrieving AllUsers
        ctx.json(userService.getAllUsers());
    }

    public void registerUserHandler (Context ctx){
        ArrayList<ErrorMessage> errorMessages = new ArrayList<>();

        // Validate that the user is not logged in
        if (ctx.sessionAttribute("user_id") != null) {
            ctx.status(401);
            errorMessages.add(new ErrorMessage("You have already logged in"));
            ctx.json(errorMessages);
            return;
        }

        //Take in the user object
        User requestUser = ctx.bodyAsClass(User.class);

        //Validate the email
        if (!userService.validateEmail(requestUser.getEmail())){
            ctx.status(400);
            // TO DO Add Error Message
            errorMessages.add(new ErrorMessage("Email is not valid"));
        }

        if (!userService.isEmailAvailable(requestUser.getEmail())){
            ctx.status(400);
            // TO DO Add Error Message
            errorMessages.add(new ErrorMessage("An account with that email already exists"));
        }
        //Validate password
        if (!userService.validatePassword(requestUser.getPassword())){
            ctx.status(400);
            //TO DO Add error message
            errorMessages.add(new ErrorMessage("Password must have at least one Uppercase/Lowercase character and minimum 8 characters"));
        }
        //Validate username
        if (!userService.validateUsername(requestUser.getUsername())){
            ctx.status(400);
            //TO DO Add error message
            errorMessages.add(new ErrorMessage("Username must have at least 8 characters"));

        }
        //Check availability of the username
        if (!userService.isUsernameAvailable(requestUser.getUsername())){
            ctx.status(400);
            //TO DO Add error message
            errorMessages.add(new ErrorMessage("Username is already taken"));
        }

        if (!errorMessages.isEmpty()){
            ctx.json(errorMessages);
            return;
        }

        //Hash password
        String hashPass = userService.hashPass(requestUser.getPassword());

        //Register user
        User registeredUser = userService.registerUser(
                requestUser.getFirst_name(),
                requestUser.getLast_name(),
                requestUser.getEmail(),
                hashPass,
                requestUser.getUsername()
        );


        //If something fails we'll report a server side error
        if (registeredUser == null){
            ctx.status(500);
            //ctx.json(new ErrorMessage("Something went wrong!"));
            return;
        }
        ctx.status(201);
        ctx.json(registeredUser);
    }

    public void loginHandler (Context ctx){
        ArrayList<ErrorMessage> errorMessages = new ArrayList<>();

        //Take in the user object
        User requestUser = ctx.bodyAsClass(User.class);

        //Validate the email
        if (!userService.validateEmail(requestUser.getEmail())){
            ctx.status(400);
            // TO DO Add Error Message
            errorMessages.add(new ErrorMessage("Email is not valid please write a correct one"));
        }

        //Attempt to log in
        User returnedUser = userService.loginUser(requestUser.getEmail(), requestUser.getPassword());

        if (returnedUser == null){
            ctx.status(400);
            errorMessages.add(new ErrorMessage("Email or Password Incorrect"));
        }

        if (!errorMessages.isEmpty()){
            ctx.json(errorMessages);
            return;
        }

        //If everything goes correct return user and add him to the session
        ctx.status(200);
        ctx.json(returnedUser);

        //Adding user to session
        ctx.sessionAttribute("user_id", returnedUser.getUser_id());
        ctx.sessionAttribute("role", requestUser.getRole());

    }

    public void updateHandler (Context ctx){
        ArrayList<ErrorMessage> errorMessages = new ArrayList<>();

        // Validate that the user is logged in
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null) {
            ctx.status(401);
            errorMessages.add(new ErrorMessage("You must be logged in to view this endpoint"));
            ctx.json(errorMessages);
            return;
        }

        User user = userService.getById(userId);

        //Validate that the user exists
        if (user == null){
            ctx.status(401);
            errorMessages.add(new ErrorMessage("Invalid ID"));
            ctx.json(errorMessages);
            return;
        }

        //Take in the user object
        User requestUser = ctx.bodyAsClass(User.class);

        //Validate the email
        if (!requestUser.getEmail().isEmpty() && !userService.validateEmail(requestUser.getEmail())){
            ctx.status(400);
            // TO DO Add Error Message
            errorMessages.add(new ErrorMessage("Email is not valid"));
        }

        if (!requestUser.getEmail().isEmpty() && !userService.isEmailAvailable(requestUser.getEmail())){
            ctx.status(400);
            // TO DO Add Error Message
            errorMessages.add(new ErrorMessage("An account with that email already exists"));
        }

        //Validate username
        if (!requestUser.getUsername().isEmpty() && !userService.validateUsername(requestUser.getUsername())){
            ctx.status(400);
            //TO DO Add error message
            errorMessages.add(new ErrorMessage("Username must have at least 8 characters"));

        }
        //Check availability of the username
        if (!requestUser.getUsername().isEmpty() && !userService.isUsernameAvailable(requestUser.getUsername())){
            ctx.status(400);
            //TO DO Add error message
            errorMessages.add(new ErrorMessage("Username is already taken"));
        }

        if (!errorMessages.isEmpty()){
            ctx.json(errorMessages);
            return;
        }


        //Set the new values if exists
        user.setFirst_name(requestUser.getFirst_name().isEmpty() ? user.getFirst_name() : requestUser.getFirst_name());
        user.setLast_name(requestUser.getLast_name().isEmpty() ? user.getLast_name() : requestUser.getLast_name());
        user.setEmail(requestUser.getEmail().isEmpty() ? user.getEmail() : requestUser.getEmail());
        user.setUsername(requestUser.getUsername().isEmpty() ? user.getUsername() : requestUser.getUsername());

        //Update user
        User updatedUser = userService.updateUser(user);

        //If something fails we'll report a server side error
        if (updatedUser == null){
            ctx.status(500);
            errorMessages.add(new ErrorMessage("Something went wrong!"));
            ctx.json(errorMessages);
            return;
        }

        ctx.status(201);
        ctx.json(updatedUser);

    }

    public void resetPasswordHandler (Context ctx){

        ArrayList<ErrorMessage> errorMessages = new ArrayList<>();

        // Validate that the user is logged in
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null) {
            ctx.status(401);
            errorMessages.add(new ErrorMessage("You must be logged in to view this endpoint"));
            ctx.json(errorMessages);
            return;
        }

        User user = userService.getById(userId);

        //Validate that the user exists
        if (user == null){
            ctx.status(401);
            errorMessages.add(new ErrorMessage("Invalid ID"));
            ctx.json(errorMessages);
            return;
        }

        //Validate password
        if (!userService.validatePassword(Objects.requireNonNull(ctx.formParam("password")))){
            ctx.status(400);
            //TO DO Add error message
            errorMessages.add(new ErrorMessage("Password must have at least one Uppercase/Lowercase character and minimum 8 characters"));
        }

        //Validate that both passwords matches;
        String password = ctx.formParam("password");
        String password2 = ctx.formParam("password2");

        assert password2 != null;
        assert password != null;
        if (!password.equals(password2)){
            ctx.status(400);
            //TO DO Add error message
            errorMessages.add(new ErrorMessage("Passwords are not equal"));
            ctx.json(errorMessages);
            return;
        }

        //Hash password
        String hashPass = userService.hashPass(password);
        boolean result = userService.resetPassword(hashPass, userId);


        if (!result){
            ctx.status(500);
            errorMessages.add(new ErrorMessage("Something went wrong!"));
            ctx.json(errorMessages);
            return;
        }

        ctx.status(201);
        ctx.json(result);

    }
}
