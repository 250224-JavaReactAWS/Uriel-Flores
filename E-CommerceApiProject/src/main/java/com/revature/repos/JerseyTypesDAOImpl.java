package com.revature.repos;

import com.revature.models.JerseyTypes;
import com.revature.models.JerseyType;
import com.revature.models.OrderStatus;
import com.revature.models.Status;
import com.revature.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JerseyTypesDAOImpl implements JerseyTypesDAO {
    @Override
    public JerseyType create(JerseyType obj) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "INSERT INTO jersey_types (type) VALUES (?) RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setString(1,obj.getJerseyType().toString());

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new JerseyType(rs.getInt("jersey_type_id"), JerseyTypes.valueOf(rs.getString("type")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not create new Jersey Type");
        }
        return null;
    }

    @Override
    public List<JerseyType> getAll() {
        //Create new ArrayList to store all teams
        List<JerseyType> allJerseyTypes = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection()){

            //Creating the query
            String sql = "SELECT * FROM order_status;";

            //Creating the statement
            Statement st = conn.createStatement();

            //Executing the sql query
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                JerseyType jt = new JerseyType(rs.getInt("jersey_type_id"), JerseyTypes.valueOf(rs.getString("type")));

                //Adding to our ArrayList
                allJerseyTypes.add(jt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get all Jersey Types");
        }
        return allJerseyTypes;
    }

    @Override
    public JerseyType getById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM jersey_types WHERE jersey_type_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new JerseyType(rs.getInt("jersey_type_id"), JerseyTypes.valueOf(rs.getString("type")));
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve Jersey Type by ID");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JerseyType update(JerseyType obj) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "UPDATE jersey_types SET type=? WHERE jersey_type_id=? RETURNING *;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setString(1,obj.getJerseyType().toString());
            ps.setInt(2,obj.getJersey_type_id());

            //Execute
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new JerseyType(rs.getInt("jersey_type_id"), JerseyTypes.valueOf(rs.getString("type")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not update the Jersey Type");
        }

        return null;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection conn = ConnectionUtil.getConnection()){
            //Declaring sql Query
            String sql = "DELETE FROM jersey_types WHERE jersey_type_id=?;";

            //Creating prepare statement
            PreparedStatement ps = conn.prepareStatement(sql);

            //Set values
            ps.setInt(1,id);

            //Execute
            int rowsAffected = ps.executeUpdate();
            return rowsAffected>0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not delete the Jersey Type");
        }
        return false;
    }
}
