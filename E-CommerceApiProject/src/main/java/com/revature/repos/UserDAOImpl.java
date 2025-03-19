package com.revature.repos;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public User getByUsername(String username) {
        try (Connection conn = ConnectionUtil.getConnection()) {

            //Declaring SQL query
            String sql = "SELECT * FROM users WHERE username = ?";

            //Creating a prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setString(1,username);

            //Execute the query
            ResultSet rs = ps.executeQuery();

            //Return the user if execution was correct

            if (rs.next()){
                User u = new User(rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("email"), rs.getString("password"), rs.getString("username"));

                u.setUser_id(rs.getInt("user_id"));
                u.setRole(Role.valueOf(rs.getString("role")));

                return u;
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve user by username");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getByEmail(String email) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                User u = new User(rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("email"), rs.getString("password"), rs.getString("username"));

                u.setUser_id(rs.getInt("user_id"));
                u.setRole(Role.valueOf(rs.getString("role")));

                return u;
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve user by email");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User create(User obj) {
        try (Connection conn = ConnectionUtil.getConnection()) {

            //Declaring sql Query
            String sql = "INSERT INTO users (first_name, last_name, email, password, username) VALUES (?, ?, ?, ?, ?) RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setString(1,obj.getFirst_name());
            ps.setString(2,obj.getLast_name());
            ps.setString(3,obj.getEmail());
            ps.setString(4,obj.getPassword());
            ps.setString(5,obj.getUsername());

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                User u = new User(rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("email"), rs.getString("password") , rs.getString("username"));

                u.setUser_id(rs.getInt("user_id"));
                u.setRole(Role.valueOf(rs.getString("role")));

                return u;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not register  user");
        }


        return null;
    }

    @Override
    public List<User> getAll() {

        //Create new ArrayList to store all users
        List<User> allUsers = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()){

            //Creating the query
            String sql = "SELECT * FROM users;";

            //Creating the statement
            Statement st = conn.createStatement();

            //Executing the sql query
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                User u = new User(rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("email"), rs.getString("password"), rs.getString("username"));
                u.setUser_id(rs.getInt("user_id"));
                u.setRole(Role.valueOf(rs.getString("role")));

                //Adding user u to our ArrayList
                allUsers.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get all users");
        }

        return allUsers;
    }

    @Override
    public User getById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM users WHERE user_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                User u = new User(rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("email"), rs.getString("password"), rs.getString("username"));

                u.setUser_id(rs.getInt("user_id"));
                u.setRole(Role.valueOf(rs.getString("role")));

                return u;
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve user by email");
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public User update(User obj) {
        try (Connection conn = ConnectionUtil.getConnection()) {

            //Declaring sql Query
            String sql = "UPDATE users SET first_name=?, last_name=?, email=?, password=?, username=? WHERE user_id=? RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setString(1,obj.getFirst_name());
            ps.setString(2,obj.getLast_name());
            ps.setString(3,obj.getEmail());
            ps.setString(4,obj.getPassword());
            ps.setString(5,obj.getUsername());
            ps.setInt(6,obj.getUser_id());

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                User u = new User(rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("email"), rs.getString("password") , rs.getString("username"));

                u.setUser_id(rs.getInt("user_id"));
                u.setRole(Role.valueOf(rs.getString("role")));

                return u;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not update user");
        }


        return null;

    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
