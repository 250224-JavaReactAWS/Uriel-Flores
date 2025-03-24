package com.revature.util;

import com.revature.controllers.*;
import com.revature.repos.*;
import com.revature.services.*;
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

        TeamDAO teamDAO = new TeamDAOImpl();
        TeamService teamService = new TeamService(teamDAO);
        TeamController teamController = new TeamController(teamService);

        CartItemDAO cartItemDAO = new CartItemDAOImpl();
        CartItemService cartItemService = new CartItemService(cartItemDAO);
        CartItemController cartItemController = new CartItemController(cartItemService, jerseyService);

        OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
        OrderItemService orderItemService = new OrderItemService(orderItemDAO);
        OrderItemController orderItemController = new OrderItemController(orderItemService);

        OrderDAO orderDAO = new OrderDAOImpl();
        OrderService orderService = new OrderService(orderDAO);
        OrderController orderController = new OrderController(orderService, cartItemService, orderItemService, jerseyService);

        return Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                path("/user", () -> {
                    post("/register", userController::registerUserHandler);
                    post("/login", userController::loginHandler);
                    patch("/update", userController::updateHandler);
                    patch("/reset", userController::resetPasswordHandler);
                });
                path("/jersey", () -> {
                    post("/search", jerseyController::getByTeamNameHandler);
                    get("/{id}", jerseyController::getJerseyDetailsHandler);
                });
                path("/jerseys", () -> {
                    get("/", jerseyController::getAllJerseysHandler);
                });
                path("/admin", () -> {
//                    get("/users", userController::getAllUsersHandler);
                    post("/add/jersey", jerseyController::addNewJerseyHandler);
                    patch("/update/jersey/{id}", jerseyController::updateJersey);
                    delete("/delete/jersey", jerseyController::deleteJerseyByIdHandler);
                    post("/add/team", teamController::addNewTeamHandler);
                    patch("/update/team/{id}", teamController::updateTeamHandler);
                    delete("/delete/team", teamController::deleteTeamByIdHandler);
                    get("/orders/all", orderController::getAllOrdersHandler);
                    get("/orders/pending", orderController::getAllPendingOrdersHandler);
                    get("/orders/shipped", orderController::getAllShippedOrdersHandler);
                    get("/orders/delivered", orderController::getAllDeliveredOrdersHandler);
                    patch("/orders/update-status/{id}", orderController::updateStatusHandler);
                });
                path("/cart", () -> {
                    post("/add-item", cartItemController::addCartItemHandler);
                    get("/my-cart", cartItemController::getUserCartHandler);
                    delete("/delete-item", cartItemController::deleteCartItemHandler);
                    post("/buy", orderController::createNewOrderHandler);
                });
                path("/order", () -> {
                    get("/my-orders", orderController::getAllOrdersByUserIdHandler);

                });
            });
        }).start(port);
    }


}
