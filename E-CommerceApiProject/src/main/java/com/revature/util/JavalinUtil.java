package com.revature.util;

import com.revature.controllers.JerseyController;
import com.revature.controllers.UserController;
import com.revature.repos.JerseyDAO;
import com.revature.repos.JerseyDAOImpl;
import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;
import com.revature.services.JerseyService;
import com.revature.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class JavalinUtil {

    public static Javalin create (int port) {

        UserDAO userDAO = new UserDAOImpl();
        UserService userService = new UserService(userDAO);
        UserController userController = new UserController(userService);

        JerseyDAO jerseyDAO = new JerseyDAOImpl();
        JerseyService jerseyService = new JerseyService(jerseyDAO);
        JerseyController jerseyController = new JerseyController(jerseyService);

        return Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                path("/user", () -> {
                    post("/register", userController::registerUserHandler);
                    post("/login", userController::loginHandler);
                    patch("/update", userController::updateHandler);
                    patch("/reset", userController::resetPasswordHandler);
                });
                path("/jerseys", () -> {
                    post("/club", jerseyController::getByTeamNameHandler);
                });
                path("/admin", () -> {
//                    get("/users", userController::getAllUsersHandler);
                    patch("/update/jersey/{id}", jerseyController::updateJersey);
                });
            });
        }).start(port);
    }


}
