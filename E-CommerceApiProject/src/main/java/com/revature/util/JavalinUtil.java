package com.revature.util;

import com.revature.controllers.UserController;
import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;
import com.revature.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class JavalinUtil {

    public static Javalin create (int port) {

        UserDAO userDAO = new UserDAOImpl();
        UserService userService = new UserService(userDAO);
        UserController userController = new UserController(userService);

        return Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                path("/user", () -> {
                    post("/register", userController::registerUserHandler);
                    post("/login", userController::loginHandler);
                    post("/update", userController::updateHandler);
                });
//                path("/jerseys", () -> {
//                    get("/", userController::getAllUsersHandler);
//                });
//                path("/admin", () -> {
//                    get("/users", userController::getAllUsersHandler);
//                });
            });
        }).start(port);
    }


}
