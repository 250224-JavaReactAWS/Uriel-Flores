package com.revature.repos;

import com.revature.models.CartItem;
import com.revature.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAOImpl implements CartItemDAO{
    @Override
    public CartItem create(CartItem obj) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "INSERT INTO cart_items (user_id, jersey_id, quantity) VALUES (?,?,?) RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,obj.getUser_id());
            ps.setInt(2,obj.getJersey_id());
            ps.setInt(3,obj.getQuantity());


            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new CartItem(rs.getInt("cart_item_id"), rs.getInt("user_id"),
                        rs.getInt("jersey_id"), rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not create new Cart Item");
        }

        return null;
    }

    @Override
    public List<CartItem> getAll() {
        //Create new ArrayList to store all users
        List<CartItem> allCartItems = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()){

            //Creating the query
            String sql = "SELECT * FROM cart_items;";

            //Creating the statement
            Statement st = conn.createStatement();

            //Executing the sql query
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                CartItem c = new CartItem(rs.getInt("cart_item_id"), rs.getInt("user_id"),
                        rs.getInt("jersey_id"), rs.getInt("quantity"));

                //Adding to our ArrayList
                allCartItems.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get all Cart Items");
        }

        return allCartItems;
    }

    @Override
    public CartItem getById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM cart_items WHERE cart_item_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new CartItem(rs.getInt("cart_item_id"), rs.getInt("user_id"),
                        rs.getInt("jersey_id"), rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve Cart Item by ID");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CartItem update(CartItem obj) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "UPDATE cart_items SET user_id=?, jersey_id=?, quantity=? WHERE cart_item_id=? RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,obj.getUser_id());
            ps.setInt(2,obj.getJersey_id());
            ps.setInt(3,obj.getQuantity());
            ps.setInt(4,obj.getCart_item_id());

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new CartItem(rs.getInt("cart_item_id"), rs.getInt("user_id"),
                        rs.getInt("jersey_id"), rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not update the Cart Item");
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "DELETE FROM cart_items WHERE cart_item_id=?;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,id);

            //Execute
            int rowsAffected = ps.executeUpdate();
            return rowsAffected>0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not delete the Cart Item");
        }
        return false;
    }

    @Override
    public List<CartItem> getAllCartItemsByUserId(int id) {
        //Create new ArrayList to store all users
        List<CartItem> allCartItems = new ArrayList<>();

        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM cart_items WHERE user_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                CartItem cartItem = new CartItem(rs.getInt("cart_item_id"), rs.getInt("user_id"),
                        rs.getInt("jersey_id"), rs.getInt("quantity"));
                allCartItems.add(cartItem);
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve Cart Items by User ID");
            e.printStackTrace();
        }
        return allCartItems;
    }

    @Override
    public double getCartTotalByUserId(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT SUM(ci.quantity * j.price) AS total  FROM cart_items ci JOIN jerseys j ON ci.jersey_id = j.jersey_id WHERE ci.user_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve Cart Total by ID");
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double getTotalPerItem(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT (ci.quantity * j.price) AS total  FROM cart_items ci JOIN jerseys j ON ci.jersey_id = j.jersey_id WHERE ci.user_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve Cart Total by ID");
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean deleteAllItems(int user_id) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "DELETE FROM cart_items WHERE user_id=?;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,user_id);

            //Execute
            int rowsAffected = ps.executeUpdate();
            return rowsAffected>0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not delete all Cart Items");
        }
        return false;
    }
}
