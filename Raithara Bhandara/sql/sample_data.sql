USE raithara_bhandara;

-- Passwords hashed with SHA-256:
-- 'password123' -> ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f
-- 'admin123'    -> 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9

INSERT INTO users (username, password_hash, role) VALUES
('farmer1', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'FARMER'),
('admin',   '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'ADMIN')
ON DUPLICATE KEY UPDATE username=VALUES(username);

INSERT INTO farmers (name, phone, location, email) VALUES
('Suresh Kumar', '9876543210', 'Bangalore', 'farmer1@example.com')
ON DUPLICATE KEY UPDATE email=email;

INSERT INTO cold_storage (name, location, capacity, contact_info) VALUES
('CoolKeep Yeshwanthpur', 'Bangalore', 1000, '080-1234567'),
('FrostBox Whitefield',   'Bangalore',  600, '080-2223334'),
('ChillHub Tumkur',       'Tumkur',     800, '0816-445566');

-- Attach a crop to the existing farmer (id lookup)
SET @fid = (SELECT farmer_id FROM farmers WHERE email='farmer1@example.com' LIMIT 1);
INSERT INTO crops (farmer_id, crop_name, quantity, harvest_date, expiry_date) VALUES
(@fid, 'Tomatoes', 200, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY)),
(@fid, 'Mangoes', 150, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY));
