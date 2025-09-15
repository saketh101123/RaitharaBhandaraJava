package com.rb.dao;

import com.rb.model.Crop;
import com.rb.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CropDAO {

    public int create(Crop crop) {
        String sql = "INSERT INTO crops (farmer_id, crop_name, quantity, harvest_date, expiry_date) VALUES (?,?,?,?,?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, crop.getFarmerId());
            ps.setString(2, crop.getCropName());
            ps.setInt(3, crop.getQuantity());
            ps.setDate(4, crop.getHarvestDate());
            ps.setDate(5, crop.getExpiryDate());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public List<Crop> findByFarmer(int farmerId) {
        String sql = "SELECT * FROM crops WHERE farmer_id=? ORDER BY created_at DESC";
        List<Crop> list = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, farmerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Crop c = new Crop();
                    c.setCropId(rs.getInt("crop_id"));
                    c.setFarmerId(rs.getInt("farmer_id"));
                    c.setCropName(rs.getString("crop_name"));
                    c.setQuantity(rs.getInt("quantity"));
                    c.setHarvestDate(rs.getDate("harvest_date"));
                    c.setExpiryDate(rs.getDate("expiry_date"));
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Crop findById(int cropId) {
        String sql = "SELECT * FROM crops WHERE crop_id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cropId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Crop c = new Crop();
                    c.setCropId(rs.getInt("crop_id"));
                    c.setFarmerId(rs.getInt("farmer_id"));
                    c.setCropName(rs.getString("crop_name"));
                    c.setQuantity(rs.getInt("quantity"));
                    c.setHarvestDate(rs.getDate("harvest_date"));
                    c.setExpiryDate(rs.getDate("expiry_date"));
                    return c;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
