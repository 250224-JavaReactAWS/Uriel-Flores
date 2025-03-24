package com.revature.repos;

import com.revature.models.Jersey;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JerseyDAOImpl implements JerseyDAO {
    @Override
    public List<Jersey> getByTeamName(String teamName) {

        //List of Jerseys
        List<Jersey> allJerseys = new ArrayList<>();

        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM jerseys j JOIN teams t  ON j.team_id = t.team_id WHERE t.name ILIKE ?;";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "%"+ teamName + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Jersey j = new Jersey(rs.getInt("jersey_id"),rs.getString("size"), rs.getDouble("price"),
                        rs.getInt("stock"), rs.getInt("team_id"), rs.getInt("jersey_type_id"));

                allJerseys.add(j);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not retrieve Jersey by Team Name");
        }

        return allJerseys;
    }

    @Override
    public Jersey create(Jersey obj) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "INSERT INTO jerseys (size, price, stock, team_id, jersey_type_id) VALUES (?,?,?,?,?) RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setString(1,obj.getSize());
            ps.setDouble(2,obj.getPrice());
            ps.setInt(3,obj.getStock());
            ps.setInt(4,obj.getTeam_id());
            ps.setInt(5,obj.getJersey_type_id());

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                Jersey j = new Jersey(rs.getInt("jersey_id"),rs.getString("size"), rs.getDouble("price"),
                        rs.getInt("stock"), rs.getInt("team_id"), rs.getInt("jersey_type_id"));
                return j;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not create new jersey");
        }

        return null;

    }

    @Override
    public List<Jersey> getAll() {
        //Create new ArrayList to store all users
        List<Jersey> allJerseys = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()){

            //Creating the query
            String sql = "SELECT * FROM jerseys;";

            //Creating the statement
            Statement st = conn.createStatement();

            //Executing the sql query
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                Jersey j = new Jersey(rs.getInt("jersey_id"),rs.getString("size"), rs.getDouble("price"),
                        rs.getInt("stock"), rs.getInt("team_id"), rs.getInt("jersey_type_id"));

                //Adding user u to our ArrayList
                allJerseys.add(j);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get all jerseys");
        }

        return allJerseys;
    }

    @Override
    public Jersey getById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM jerseys WHERE jersey_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new Jersey(rs.getInt("jersey_id"),rs.getString("size"), rs.getDouble("price"),
                        rs.getInt("stock"), rs.getInt("team_id"), rs.getInt("jersey_type_id"));
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve jersey by ID");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Jersey update(Jersey obj) { //Admin method

        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "UPDATE jerseys SET size=?, price=?, stock=?, team_id=?, jersey_type_id=?  WHERE jersey_id=? RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setString(1,obj.getSize());
            ps.setDouble(2,obj.getPrice());
            ps.setInt(3,obj.getStock());
            ps.setInt(4,obj.getTeam_id());
            ps.setInt(5,obj.getJersey_type_id());
            ps.setInt(6,obj.getJersey_id());

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new Jersey(rs.getInt("jersey_id"),rs.getString("size"), rs.getDouble("price"),
                        rs.getInt("stock"), rs.getInt("team_id"), rs.getInt("jersey_type_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not update the jersey");
        }

        return null;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "DELETE FROM jerseys WHERE jersey_id=?;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,id);

            //Execute
            int rowsAffected = ps.executeUpdate();
            return rowsAffected>0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not delete the jersey");
        }

        return false;
    }
}
