package com.revature.repos;

import com.revature.models.OrderStatus;
import com.revature.models.Status;
import com.revature.models.Team;
import com.revature.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusDAOImpl implements OrderStatusDAO {
    @Override
    public OrderStatus create(OrderStatus obj) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "INSERT INTO order_status (status) VALUES (?) RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setString(1,obj.getStatus().toString());

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new OrderStatus(rs.getInt("status_id"), Status.valueOf(rs.getString("status")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not create new Order Status");
        }
        return null;
    }

    @Override
    public List<OrderStatus> getAll() {
        //Create new ArrayList to store all teams
        List<OrderStatus> allStatus = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()){

            //Creating the query
            String sql = "SELECT * FROM order_status;";

            //Creating the statement
            Statement st = conn.createStatement();

            //Executing the sql query
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                OrderStatus os = new OrderStatus(rs.getInt("status_id"), Status.valueOf(rs.getString("status")));

                //Adding to our ArrayList
                allStatus.add(os);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get all Orders Status");
        }
        return allStatus;
    }

    @Override
    public OrderStatus getById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM order_status WHERE status_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new OrderStatus(rs.getInt("status_id"), Status.valueOf(rs.getString("status")));
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve Status by ID");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderStatus update(OrderStatus obj) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "UPDATE order_status SET status=? WHERE status_id=? RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setString(1,obj.getStatus().toString());
            ps.setInt(2,obj.getStatus_id());

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new OrderStatus(rs.getInt("status_id"), Status.valueOf(rs.getString("status")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not update the Status");
        }

        return null;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "DELETE FROM order_status WHERE status_id=?;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,id);

            //Execute
            int rowsAffected = ps.executeUpdate();
            return rowsAffected>0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not delete the Status");
        }
        return false;
    }
}
