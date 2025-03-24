package com.revature.controllers;

import com.revature.models.*;
import com.revature.response.ErrorMessage;
import com.revature.services.CartItemService;
import com.revature.services.JerseyService;
import com.revature.services.OrderItemService;
import com.revature.services.OrderService;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderController {

    private final OrderService orderService;
    private final CartItemService cartItemService;
    private final OrderItemService orderItemService;
    private final JerseyService jerseyService;

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService, CartItemService cartItemService, OrderItemService orderItemService, JerseyService jerseyService) {
        this.orderService = orderService;
        this.cartItemService = cartItemService;
        this.orderItemService = orderItemService;
        this.jerseyService = jerseyService;
    }

    //Method to create new Order
    public void createNewOrderHandler (Context ctx){
        ArrayList<ErrorMessage> errorMessages = new ArrayList<>();

        // Validate that the user is logged in
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null) {
            ctx.status(401);
            errorMessages.add(new ErrorMessage("You must be logged in to view this endpoint"));
            ctx.json(errorMessages);
            return;
        }

        // Validate that the cart is not empty
        if (cartItemService.getAllCartItemsByUserId(userId).isEmpty()){
            ctx.status(404);
            errorMessages.add(new ErrorMessage("You don't have any jerseys in your cart"));
            ctx.json(errorMessages);
            logger.warn("Buy items attempt made for an empty Cart for the user with id: " + userId);
            return;
        }

        //If validation is passed then create new order
        Order newOrder = new Order();

        //Set values
        newOrder.setUser_id(userId);
        newOrder.setTotal_price(cartItemService.getCartTotal(userId));
        newOrder.setCreated_at(Timestamp.from(Instant.now()));
        newOrder.setUpdated_at(Timestamp.from(Instant.now()));
        newOrder.setStatus(1);

        //Save the new order
        Order savedOrder = orderService.createNewOrder(newOrder);

        //If something fails we'll report a server side error
        if (savedOrder == null){
            ctx.status(500);
            errorMessages.add(new ErrorMessage("Something went wrong!"));
            ctx.json(errorMessages);
            return;
        }

        //Now create order items according to cart items
        List<CartItem> cartItems = cartItemService.getAllCartItemsByUserId(userId);
        List<OrderItem> orderItems = new ArrayList<>();

        //Create an order item for each cart item
        for (CartItem ci : cartItems){
            orderItems.add(orderItemService.createOrderItem(
                    savedOrder.getOrder_id(),
                    ci.getJersey_id(),
                    ci.getQuantity(),
                    jerseyService.getById(ci.getJersey_id()).getPrice()
            ));
        }

        //Validate that order items list is not empty
        if (orderItems.isEmpty()){
            ctx.status(500);
            errorMessages.add(new ErrorMessage("Something went wrong!"));
            ctx.json(errorMessages);
            return;
        }

        //If the purchase goes correct then delete the Customer Cart
        cartItemService.deleteAllItems(userId);

        //If everything its correct
        ctx.status(201);
        ctx.json(savedOrder);
        logger.info("An Order was created for User with Id: " + userId);
    }

    //Method to update status order
    public void updateStatusHandler (Context ctx){
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

        //Search for the order by ID
        Order order = orderService.getOrderById(Integer.parseInt(ctx.pathParam("id")));

        //Validate that the order exists
        if (order == null){
            ctx.status(404);
            errorMessages.add(new ErrorMessage("Page Not Found"));
            ctx.json(errorMessages);
            logger.warn("Update status order attempt made for an invalid order id ");
            return;
        }

        //Get the new status
        int statusId = Integer.parseInt(Objects.requireNonNull(ctx.queryParam("status")));

        //update the order
        Order updatedOrder = orderService.updateStatus(order.getOrder_id(), statusId);

        //If something fails we'll report a server side error
        if (updatedOrder == null){
            ctx.status(500);
            errorMessages.add(new ErrorMessage("Something went wrong!"));
            ctx.json(errorMessages);
            return;
        }

        ctx.status(200);
        ctx.json(updatedOrder);
        logger.info("A Status Order was updated for order with Id: " + order.getOrder_id());



    }

    //Method to view all orders that correspond to a specific user
    public void getAllOrdersByUserIdHandler(Context ctx){
        ArrayList<ErrorMessage> errorMessages = new ArrayList<>();

        // Validate that the user is logged in
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null) {
            ctx.status(401);
            errorMessages.add(new ErrorMessage("You must be logged in to view this endpoint"));
            ctx.json(errorMessages);
            return;
        }

        //Validate that the customer has at least one order
        List<Order> orders = orderService.getAllByUserId(userId);

        if (orders.isEmpty()){
            ctx.status(404);
            errorMessages.add(new ErrorMessage("You don't have any order yet"));
            ctx.json(errorMessages);
            return;
        }

        //Show all orders
        ctx.status(200);
        ctx.json(orders);

    }

    //Method to view All orders (ADMIN only)
    public void getAllOrdersHandler (Context ctx){
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
        List<Order> orders = orderService.getAll();

        if (orders.isEmpty()){
            ctx.status(204);
            errorMessages.add(new ErrorMessage("The list is empty"));
            ctx.json(errorMessages);
            return;
        }     
        ctx.status(200);
        ctx.json(orders);

    }

    //Method to view All pending orders (ADMIN only)
    public void getAllPendingOrdersHandler (Context ctx){
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
        List<Order> orders = orderService.getAllPendingOrders();

        if (orders.isEmpty()){
            ctx.status(204);
            errorMessages.add(new ErrorMessage("The list is empty"));
            ctx.json(errorMessages);
            return;
        }
        ctx.status(200);
        ctx.json(orders);
    }

    //Method to view All shipped orders (ADMIN only)
    public void getAllShippedOrdersHandler (Context ctx){
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
        List<Order> orders = orderService.getAllShippedOrders();

        if (orders.isEmpty()){
            ctx.status(204);
            errorMessages.add(new ErrorMessage("The list is empty"));
            ctx.json(errorMessages);
            return;
        }
        ctx.status(200);
        ctx.json(orders);

    }

    //Method to view All delivered orders (ADMIN only)
    public void getAllDeliveredOrdersHandler (Context ctx){
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
        List<Order> orders = orderService.getAllDeliveredOrders();

        if (orders.isEmpty()){
            ctx.status(204);
            errorMessages.add(new ErrorMessage("The list is empty"));
            ctx.json(errorMessages);
            return;
        }
        ctx.status(200);
        ctx.json(orders);

    }




}
