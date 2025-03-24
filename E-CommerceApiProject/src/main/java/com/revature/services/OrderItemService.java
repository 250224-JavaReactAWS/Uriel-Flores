package com.revature.services;

import com.revature.models.OrderItem;
import com.revature.repos.OrderItemDAO;

public class OrderItemService {
    private OrderItemDAO orderItemDAO;

    public OrderItemService(OrderItemDAO orderItemDAO) {
        this.orderItemDAO = orderItemDAO;
    }

    //Method to create an Order Item
    public OrderItem createOrderItem (int order_id, int jersey_id, int quantity, double price) {
        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setOrder_id(order_id);
        newOrderItem.setJersey_id(jersey_id);
        newOrderItem.setQuantity(quantity);
        newOrderItem.setPrice(price);

        return orderItemDAO.create(newOrderItem);
    }

}
