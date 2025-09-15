package com.rb.model;

public class ColdStorage {
    private int storageId;
    private String name;
    private String location;
    private int capacity;
    private String contactInfo;

    public int getStorageId() { return storageId; }
    public void setStorageId(int storageId) { this.storageId = storageId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
}
