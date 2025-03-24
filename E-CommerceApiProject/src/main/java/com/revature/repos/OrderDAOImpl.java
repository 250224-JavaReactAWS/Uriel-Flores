package com.revature.repos;

import com.revature.models.Jersey;
import com.revature.models.Order;
import com.revature.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public Order create(Order obj) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "INSERT INTO orders (user_id, total_price, created_at, updated_at, status_id) VALUES (?,?,?,?,?) RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,obj.getUser_id());
            ps.setDouble(2,obj.getTotal_price());
            ps.setTimestamp(3,obj.getCreated_at());
            ps.setTimestamp(4,obj.getUpdated_at());
            ps.setInt(5,obj.getStatus());

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new Order(rs.getInt("order_id"), rs.getInt("user_id"),
                        rs.getDouble("total_price"), rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"), rs.getInt("status_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not create new Order");
        }

        return null;
    }

    @Override
    public List<Order> getAll() {
        //Create new ArrayList to store all users
        List<Order> allOrders = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()){

            //Creating the query
            String sql = "SELECT * FROM orders;";

            //Creating the statement
            Statement st = conn.createStatement();

            //Executing the sql query
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                Order o = new Order(rs.getInt("order_id"), rs.getInt("user_id"),
                        rs.getDouble("total_price"), rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"), rs.getInt("status_id"));

                //Adding to our ArrayList
                allOrders.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get all Orders");
        }

        return allOrders;
    }

    @Override
    public List<Order> getAllByUserId(int user_id) {
        //Create new ArrayList to store all users
        List<Order> allOrders = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()){

            //Creating the query
            String sql = "SELECT * FROM orders WHERE user_id= ?;";

            //Creating the statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set value
            ps.setInt(1,user_id);

            //Executing the sql query
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Order o = new Order(rs.getInt("order_id"), rs.getInt("user_id"),
                        rs.getDouble("total_price"), rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"), rs.getInt("status_id"));

                //Adding to our ArrayList
                allOrders.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get all Orders");
        }

        return allOrders;
    }

    @Override
    public List<Order> getAllPendingOrders() {
        //Create new ArrayList to store all users
        List<Order> allOrders = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()){

            //Creating the query
            String sql = "SELECT * FROM orders WHERE status_id = 1;";

            //Creating the statement
            Statement st = conn.createStatement();

            //Executing the sql query
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                Order o = new Order(rs.getInt("order_id"), rs.getInt("user_id"),
                        rs.getDouble("total_price"), rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"), rs.getInt("status_id"));

                //Adding to our ArrayList
                allOrders.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get all Orders");
        }

        return allOrders;
    }

    @Override
    public List<Order> getAllShippedOrders() {
        //Create new ArrayList to store all users
        List<Order> allOrders = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()){

            //Creating the query
            String sql = "SELECT * FROM orders WHERE status_id=2;";

            //Creating the statement
            Statement st = conn.createStatement();

            //Executing the sql query
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                Order o = new Order(rs.getInt("order_id"), rs.getInt("user_id"),
                        rs.getDouble("total_price"), rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"), rs.getInt("status_id"));

                //Adding to our ArrayList
                allOrders.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get all Orders");
        }

        return allOrders;
    }

    @Override
    public List<Order> getAllDeliveredOrders() {
        //Create new ArrayList to store all users
        List<Order> allOrders = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()){

            //Creating the query
            String sql = "SELECT * FROM orders WHERE status_id=3;";

            //Creating the statement
            Statement st = conn.createStatement();

            //Executing the sql query
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                Order o = new Order(rs.getInt("order_id"), rs.getInt("user_id"),
                        rs.getDouble("total_price"), rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"), rs.getInt("status_id"));

                //Adding to our ArrayList
                allOrders.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get all Orders");
        }

        return allOrders;
    }


    @Override
    public Order getById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM orders WHERE order_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new Order(rs.getInt("order_id"), rs.getInt("user_id"),
                        rs.getDouble("total_price"), rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"), rs.getInt("status_id"));
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve Order by ID");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order update(Order obj) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "UPDATE orders SET user_id=?, total_price=?, created_at=?, updated_at=?, status_id=?  WHERE order_id=? RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,obj.getUser_id());
            ps.setDouble(2,obj.getTotal_price());
            ps.setTimestamp(3,obj.getCreated_at());
            ps.setTimestamp(4,obj.getUpdated_at());
            ps.setInt(5,obj.getStatus());
            ps.setInt(6,obj.getOrder_id());

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new Order(rs.getInt("order_id"), rs.getInt("user_id"),
                        rs.getDouble("total_price"), rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"), rs.getInt("status_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not update the Order");
        }

        return null;
    }

    @Override
    public Order updateStatusOrder(int orderId, int statusId) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "UPDATE orders SET status_id=? WHERE order_id=? RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,statusId);
            ps.setInt(2,orderId);

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new Order(rs.getInt("order_id"), rs.getInt("user_id"),
                        rs.getDouble("total_price"), rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"), rs.getInt("status_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not update the Status Order");
        }

        return null;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "DELETE FROM orders WHERE order_id=?;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,id);

            //Execute
            int rowsAffected = ps.executeUpdate();
            return rowsAffected>0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not delete the Order");
        }
        return false;
    }


}
