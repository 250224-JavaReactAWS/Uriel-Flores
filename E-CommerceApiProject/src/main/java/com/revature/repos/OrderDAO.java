package com.revature.repos;

import com.revature.models.Order;

import java.util.List;

public interface OrderDAO extends GeneralDAO<Order> {

    List<Order> getAllByUserId (int user_id);
    List<Order> getAllPendingOrders ();
    List<Order> getAllShippedOrders ();
    List<Order> getAllDeliveredOrders ();
    Order updateStatusOrder(int orderId, int statusId);
}
