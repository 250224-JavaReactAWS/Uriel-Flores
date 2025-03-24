package com.revature.controllers;

import com.revature.services.OrderItemService;
import io.javalin.http.Context;

public class OrderItemController {
    private OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    //Method to create an order item according to the cart items
    public void createOrderItemHandler (Context ctx){


    }

}
