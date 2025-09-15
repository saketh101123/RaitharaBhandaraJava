package com.rb.model;

import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int farmerId;
    private int cropId;
    private int storageId;
    private Timestamp bookingDate;
    private String status;

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getFarmerId() { return farmerId; }
    public void setFarmerId(int farmerId) { this.farmerId = farmerId; }

    public int getCropId() { return cropId; }
    public void setCropId(int cropId) { this.cropId = cropId; }

    public int getStorageId() { return storageId; }
    public void setStorageId(int storageId) { this.storageId = storageId; }

    public Timestamp getBookingDate() { return bookingDate; }
    public void setBookingDate(Timestamp bookingDate) { this.bookingDate = bookingDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
