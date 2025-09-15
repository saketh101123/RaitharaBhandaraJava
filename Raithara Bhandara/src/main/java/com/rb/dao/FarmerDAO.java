package com.rb.dao;

import com.rb.model.Farmer;
import com.rb.util.DBUtil;

import java.sql.*;

public class FarmerDAO {

    public int create(Farmer farmer) {
        String sql = "INSERT INTO farmers (name, phone, location, email) VALUES (?,?,?,?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, farmer.getName());
            ps.setString(2, farmer.getPhone());
            ps.setString(3, farmer.getLocation());
            ps.setString(4, farmer.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public Farmer findByEmail(String email) {
        String sql = "SELECT farmer_id, name, phone, location, email FROM farmers WHERE email=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Farmer f = new Farmer();
                    f.setFarmerId(rs.getInt("farmer_id"));
                    f.setName(rs.getString("name"));
                    f.setPhone(rs.getString("phone"));
                    f.setLocation(rs.getString("location"));
                    f.setEmail(rs.getString("email"));
                    return f;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Farmer findById(int id) {
        String sql = "SELECT farmer_id, name, phone, location, email FROM farmers WHERE farmer_id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Farmer f = new Farmer();
                    f.setFarmerId(rs.getInt("farmer_id"));
                    f.setName(rs.getString("name"));
                    f.setPhone(rs.getString("phone"));
                    f.setLocation(rs.getString("location"));
                    f.setEmail(rs.getString("email"));
                    return f;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
