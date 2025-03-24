package com.revature.repos;


import com.revature.models.OrderItem;
import com.revature.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {
    @Override
    public OrderItem create(OrderItem obj) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "INSERT INTO order_item (order_id, jersey_id, quantity, price) VALUES (?,?,?,?) RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,obj.getOrder_id());
            ps.setInt(2,obj.getJersey_id());
            ps.setInt(3,obj.getQuantity());
            ps.setDouble(4, obj.getPrice());


            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new OrderItem(rs.getInt("order_item_id"), rs.getInt("order_id"),
                        rs.getInt("jersey_id"), rs.getInt("quantity"), rs.getDouble("price"));
            }

        } catch (
        SQLException e) {
            e.printStackTrace();
            System.out.println("Could not create new Order Item");
        }

        return null;
    }

    @Override
    public List<OrderItem> getAll() {
        //Create new ArrayList to store all users
        List<OrderItem> allOrderItems = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()){

            //Creating the query
            String sql = "SELECT * FROM order_item;";

            //Creating the statement
            Statement st = conn.createStatement();

            //Executing the sql query
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                OrderItem o = new OrderItem(rs.getInt("order_item_id"), rs.getInt("order_id"),
                        rs.getInt("jersey_id"), rs.getInt("quantity"), rs.getDouble("price"));

                //Adding to our ArrayList
                allOrderItems.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get all Order Items");
        }

        return allOrderItems;
    }

    @Override
    public OrderItem getById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM order_item WHERE order_item_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new OrderItem(rs.getInt("order_item_id"), rs.getInt("order_id"),
                        rs.getInt("jersey_id"), rs.getInt("quantity"), rs.getDouble("price"));
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve Order Item by ID");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderItem update(OrderItem obj) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "UPDATE order_item SET order_id=?, jersey_id=?, quantity=?, price=? WHERE order_item_id=? RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,obj.getOrder_id());
            ps.setInt(2,obj.getJersey_id());
            ps.setInt(3,obj.getQuantity());
            ps.setDouble(4,obj.getPrice());
            ps.setInt(5, obj.getOrder_item_id());

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new OrderItem(rs.getInt("order_item_id"), rs.getInt("order_id"),
                        rs.getInt("jersey_id"), rs.getInt("quantity"), rs.getDouble("price"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not update the Order Item");
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "DELETE FROM order_item WHERE order_item_id=?;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,id);

            //Execute
            int rowsAffected = ps.executeUpdate();
            return rowsAffected>0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not delete the Order Item");
        }
        return false;
    }
}
