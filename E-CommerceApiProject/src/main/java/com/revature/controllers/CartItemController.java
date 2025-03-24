package com.revature.controllers;

import com.revature.models.CartItem;
import com.revature.models.Jersey;
import com.revature.response.ErrorMessage;
import com.revature.services.CartItemService;
import com.revature.services.JerseyService;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartItemController {

    private final CartItemService cartItemService;
    private final JerseyService jerseyService;
    private final Logger logger = LoggerFactory.getLogger(CartItemController.class);

    public CartItemController(CartItemService cartItemService, JerseyService jerseyService) {
        this.cartItemService = cartItemService;
        this.jerseyService = jerseyService;
    }

    //Method to add a Cart Item
    public void addCartItemHandler (Context ctx){
        ArrayList<ErrorMessage> errorMessages = new ArrayList<>();

        // Validate that the user is logged in
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null) {
            ctx.status(401);
            errorMessages.add(new ErrorMessage("You must be logged in to view this endpoint"));
            ctx.json(errorMessages);
            return;
        }

        //Get the Jersey id from the path
        int jerseyId = Integer.parseInt(Objects.requireNonNull(ctx.queryParam("id")));

        //Validate that the Jersey exists
        if (jerseyService.getById(jerseyId) == null){
            ctx.status(404);
            errorMessages.add(new ErrorMessage("Page Not Found!"));
            ctx.json(errorMessages);
            return;
        }

        //Get the quantity
        int quantity = Integer.parseInt(Objects.requireNonNull(ctx.queryParam("quantity")));

        //Creating new Cart Item
        CartItem cartItem = new CartItem();

        //Setting values
        cartItem.setUser_id(userId);
        cartItem.setJersey_id(jerseyId);
        cartItem.setQuantity(quantity);


        //TODO Validate that a cart item with same values does not exist into the DB


        //Add new cart item into DB and verify if it works
        cartItem = cartItemService.addCartItem(cartItem);
        if ( cartItem == null){
            ctx.status(500);
            errorMessages.add(new ErrorMessage("Something went wrong!"));
            ctx.json(errorMessages);
            return;
        }

        ctx.status(201);
        ctx.json(cartItem);
        logger.info("New car item registered for a User with id: " + userId);

    }

    //Method to delete a specific Cart Item
    public void deleteCartItemHandler(Context ctx){
        ArrayList<ErrorMessage> errorMessages = new ArrayList<>();

        // Validate that the user is logged in
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null) {
            ctx.status(401);
            errorMessages.add(new ErrorMessage("You must be logged in to view this endpoint"));
            ctx.json(errorMessages);
            return;
        }
        //Validate the Cart Item Exists
        int cartItemId = Integer.parseInt(Objects.requireNonNull(ctx.queryParam("id")));
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);
        if (cartItem == null){
            ctx.status(404);
            errorMessages.add(new ErrorMessage("Page Not Found!"));
            ctx.json(errorMessages);
            return;
        }

        //Validate that te user logged corresponds to the Cart Item's user_id
        if (cartItem.getUser_id() != userId){
            ctx.status(403);
            errorMessages.add(new ErrorMessage("Forbidden Access"));
            ctx.json(errorMessages);
            return;
        }

        //If pass all validations delete the cart item
        if (!cartItemService.deleteCartItem(cartItemId)){
            ctx.status(500);
            errorMessages.add(new ErrorMessage("Something went wrong!"));
            ctx.json(errorMessages);
            return;
        }

        ctx.status(200);
        ctx.json("Operation Completed");
        logger.info("Delete item for a User with id: " + userId);

    }

    //Method to retrieve a specific User Cart
    public void getUserCartHandler (Context ctx){
        ArrayList<ErrorMessage> errorMessages = new ArrayList<>();

        // Validate that the user is logged in
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null) {
            ctx.status(401);
            errorMessages.add(new ErrorMessage("You must be logged in to view this endpoint"));
            ctx.json(errorMessages);
            return;
        }

        //Get the cart
        List<CartItem> cartItems = cartItemService.getAllCartItemsByUserId(userId);

        if (cartItems.isEmpty()){
            ctx.status(204);
            errorMessages.add(new ErrorMessage("You don't have any jerseys in your cart"));
            ctx.json(errorMessages);
            return;
        }

        ctx.status(200);
        ctx.json(cartItems);
    }

}
