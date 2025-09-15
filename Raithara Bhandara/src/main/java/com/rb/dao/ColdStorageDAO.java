package com.rb.dao;

import com.rb.model.ColdStorage;
import com.rb.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColdStorageDAO {

    public List<ColdStorage> findByLocation(String location) {
        String sql = "SELECT * FROM cold_storage WHERE location=? ORDER BY name";
        List<ColdStorage> list = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, location);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ColdStorage cs = new ColdStorage();
                    cs.setStorageId(rs.getInt("storage_id"));
                    cs.setName(rs.getString("name"));
                    cs.setLocation(rs.getString("location"));
                    cs.setCapacity(rs.getInt("capacity"));
                    cs.setContactInfo(rs.getString("contact_info"));
                    list.add(cs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public ColdStorage findById(int id) {
        String sql = "SELECT * FROM cold_storage WHERE storage_id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ColdStorage cs = new ColdStorage();
                    cs.setStorageId(rs.getInt("storage_id"));
                    cs.setName(rs.getString("name"));
                    cs.setLocation(rs.getString("location"));
                    cs.setCapacity(rs.getInt("capacity"));
                    cs.setContactInfo(rs.getString("contact_info"));
                    return cs;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int currentUsedCapacity(int storageId) {
        String sql = "SELECT COALESCE(SUM(c.quantity),0) AS used FROM bookings b JOIN crops c ON b.crop_id=c.crop_id WHERE b.storage_id=? AND b.status IN ('PENDING','CONFIRMED')";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, storageId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("used");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
