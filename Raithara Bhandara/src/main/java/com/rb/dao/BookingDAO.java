package com.rb.dao;

import com.rb.model.Booking;
import com.rb.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public int create(Booking b) {
        String sql = "INSERT INTO bookings (farmer_id, crop_id, storage_id, status) VALUES (?,?,?,?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, b.getFarmerId());
            ps.setInt(2, b.getCropId());
            ps.setInt(3, b.getStorageId());
            ps.setString(4, b.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public List<Booking> findByFarmer(int farmerId) {
        String sql = "SELECT * FROM bookings WHERE farmer_id=? ORDER BY booking_date DESC";
        List<Booking> list = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, farmerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking();
                    b.setBookingId(rs.getInt("booking_id"));
                    b.setFarmerId(rs.getInt("farmer_id"));
                    b.setCropId(rs.getInt("crop_id"));
                    b.setStorageId(rs.getInt("storage_id"));
                    b.setBookingDate(rs.getTimestamp("booking_date"));
                    b.setStatus(rs.getString("status"));
                    list.add(b);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
