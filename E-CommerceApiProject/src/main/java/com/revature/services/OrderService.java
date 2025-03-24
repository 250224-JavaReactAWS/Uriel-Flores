package com.revature.services;

import com.revature.models.Order;
import com.revature.models.OrderItem;
import com.revature.repos.OrderDAO;

import java.util.List;

public class OrderService {

    private OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    //Method to get an Order by Id
    public Order getOrderById (int id){
        return orderDAO.getById(id);
    }

    //Method to create an Order
    public Order createNewOrder (Order newOrder){
        return orderDAO.create(newOrder);
    }

    //Method to retrieve all orders that belong to a user
    public List<Order> getAllByUserId (int userId){
        return orderDAO.getAllByUserId(userId);
    }

    public List<Order> getAll (){
        return orderDAO.getAll();
    }

    public List<Order> getAllPendingOrders (){
        return orderDAO.getAllPendingOrders();
    }

    public List<Order> getAllShippedOrders (){
        return orderDAO.getAllShippedOrders();
    }

    public List<Order> getAllDeliveredOrders (){
        return orderDAO.getAllDeliveredOrders();
    }

    public Order updateStatus (int orderId, int statusId){
        return orderDAO.updateStatusOrder(orderId, statusId);

    }



}
