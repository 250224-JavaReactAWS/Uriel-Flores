package com.revature.repos;

import com.revature.models.Role;
import com.revature.models.Team;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAOImpl implements TeamDAO{

    @Override
    public Team create(Team obj) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "INSERT INTO teams (name) VALUES (?) RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setString(1,obj.getName());

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new Team(rs.getInt("team_id"), rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not create new Team");
        }

        return null;
    }

    @Override
    public List<Team> getAll() {
        //Create new ArrayList to store all teams
        List<Team> allTeams = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()){

            //Creating the query
            String sql = "SELECT * FROM teams;";

            //Creating the statement
            Statement st = conn.createStatement();

            //Executing the sql query
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                Team t = new Team(rs.getInt("team_id"), rs.getString("name"));

                //Adding user u to our ArrayList
                allTeams.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get all Teams");
        }
        return allTeams;
    }
    @Override
    public Team getById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM teams WHERE team_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new Team(rs.getInt("team_id"), rs.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve Teams by ID");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Team update(Team obj) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "UPDATE teams SET name=? WHERE team_id=? RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setString(1,obj.getName());
            ps.setInt(2,obj.getTeam_id());

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new Team(rs.getInt("team_id"), rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not update the Team");
        }

        return null;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "DELETE FROM teams WHERE team_id=?;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,id);

            //Execute
            int rowsAffected = ps.executeUpdate();
            return rowsAffected>0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not delete the Team");
        }
        return false;
    }

    @Override
    public Team getByTeamName(String teamName) {
        try (Connection conn = ConnectionUtil.getConnection()) {

            //Declaring SQL query
            String sql = "SELECT * FROM teams WHERE name ILIKE ?;";

            //Creating a prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setString(1,teamName);

            //Execute the query
            ResultSet rs = ps.executeQuery();

            //Return the Team if execution was correct
            if (rs.next()){
                return new Team(rs.getInt("team_id"), rs.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve user by Team Name");
            e.printStackTrace();
        }

        return null;

    }
}
