package com.revature.repos;

import com.revature.models.Jersey;
import com.revature.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JerseyDAOImpl implements JerseyDAO {
    @Override
    public Jersey getByTeamName(String name) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT ";


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not retrieve Jersey by Team Name");
        }
        return null;
    }

    @Override
    public Jersey create(Jersey obj) {
        return null;
    }

    @Override
    public List<Jersey> getAll() {
        return List.of();
    }

    @Override
    public Jersey getById(int id) {
        return null;
    }

    @Override
    public Jersey update(Jersey obj) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
