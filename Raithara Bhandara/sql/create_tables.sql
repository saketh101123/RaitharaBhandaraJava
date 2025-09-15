-- Create DB (if not exists)
CREATE DATABASE IF NOT EXISTS raithara_bhandara
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;
USE raithara_bhandara;

-- Users (for login) - optional but used here for HttpServlet login
CREATE TABLE IF NOT EXISTS users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  password_hash CHAR(64) NOT NULL,
  role ENUM('FARMER','ADMIN') NOT NULL DEFAULT 'FARMER',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Farmer table
CREATE TABLE IF NOT EXISTS farmers (
  farmer_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  phone VARCHAR(20),
  location VARCHAR(100),
  email VARCHAR(100) UNIQUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Crop table
CREATE TABLE IF NOT EXISTS crops (
  crop_id INT AUTO_INCREMENT PRIMARY KEY,
  farmer_id INT NOT NULL,
  crop_name VARCHAR(100) NOT NULL,
  quantity INT NOT NULL,
  harvest_date DATE,
  expiry_date DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_crops_farmer FOREIGN KEY (farmer_id) REFERENCES farmers(farmer_id)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

-- Cold storage table
CREATE TABLE IF NOT EXISTS cold_storage (
  storage_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  location VARCHAR(100) NOT NULL,
  capacity INT NOT NULL,
  contact_info VARCHAR(200),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Booking/Allocation table
CREATE TABLE IF NOT EXISTS bookings (
  booking_id INT AUTO_INCREMENT PRIMARY KEY,
  farmer_id INT NOT NULL,
  crop_id INT NOT NULL,
  storage_id INT NOT NULL,
  booking_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status ENUM('PENDING','CONFIRMED','CANCELLED') NOT NULL DEFAULT 'CONFIRMED',
  CONSTRAINT fk_bkg_farmer FOREIGN KEY (farmer_id) REFERENCES farmers(farmer_id)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_bkg_crop FOREIGN KEY (crop_id) REFERENCES crops(crop_id)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_bkg_storage FOREIGN KEY (storage_id) REFERENCES cold_storage(storage_id)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

-- Indexes
CREATE INDEX idx_crops_farmer ON crops(farmer_id);
CREATE INDEX idx_storage_loc ON cold_storage(location);
CREATE INDEX idx_bkg_storage ON bookings(storage_id);
CREATE INDEX idx_bkg_status ON bookings(status);
