-- Script tao co so du lieu cho ung dung AppHocTu
CREATE DATABASE IF NOT EXISTS apphoctu DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE apphoctu;

-- 1. Bang tai_khoan
CREATE TABLE IF NOT EXISTS tai_khoan (
    ma_tai_khoan INT AUTO_INCREMENT PRIMARY KEY,
    ten_dang_nhap VARCHAR(50) NOT NULL UNIQUE,
    mat_khau VARCHAR(256) NOT NULL, -- Mat khau da bam SHA-256
    vai_tro VARCHAR(20) NOT NULL DEFAULT 'User'
) ENGINE=InnoDB;

-- 2. Bang chu_de
CREATE TABLE IF NOT EXISTS chu_de (
    ma_chu_de INT AUTO_INCREMENT PRIMARY KEY,
    ten_chu_de VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- 3. Bang tu_vung
CREATE TABLE IF NOT EXISTS tu_vung (
    ma_tu INT AUTO_INCREMENT PRIMARY KEY,
    tu_tieng_anh VARCHAR(100) NOT NULL,
    nghia_tieng_viet VARCHAR(200) NOT NULL,
    ma_chu_de INT NOT NULL,
    FOREIGN KEY (ma_chu_de) REFERENCES chu_de(ma_chu_de) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 4. Bang ket_qua_kiem_tra
CREATE TABLE IF NOT EXISTS ket_qua_kiem_tra (
    ma_ket_qua INT AUTO_INCREMENT PRIMARY KEY,
    ma_tai_khoan INT NOT NULL,
    ma_chu_de INT NOT NULL,
    diem_so INT NOT NULL, -- So cau tra loi dung
    tong_so_cau INT NOT NULL, -- Tong so cau hoi
    thoi_diem_lam_bai TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ma_tai_khoan) REFERENCES tai_khoan(ma_tai_khoan) ON DELETE CASCADE,
    FOREIGN KEY (ma_chu_de) REFERENCES chu_de(ma_chu_de) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Chen du lieu khoi tao mac dinh (Seeding)

-- Mat khau 'admin123' -> SHA-256: 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9
-- Mat khau 'user123'  -> SHA-256: e606e38b0d8c19b24cf0ee3808183162ea7cd63ff7912dbb22b5e803286b4446
INSERT INTO tai_khoan (ten_dang_nhap, mat_khau, vai_tro) VALUES
('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Admin'),
('user', 'e606e38b0d8c19b24cf0ee3808183162ea7cd63ff7912dbb22b5e803286b4446', 'User')
ON DUPLICATE KEY UPDATE ten_dang_nhap=ten_dang_nhap;

-- Chen cac chu de mac dinh
INSERT INTO chu_de (ma_chu_de, ten_chu_de) VALUES
(1, 'Family'),
(2, 'Animals'),
(3, 'Foods')
ON DUPLICATE KEY UPDATE ten_chu_de=ten_chu_de;

-- Chen cac tu vung mac dinh
INSERT INTO tu_vung (tu_tieng_anh, nghia_tieng_viet, ma_chu_de) VALUES
-- Chu de Family (ID 1)
('Father', 'Cha, Bo', 1),
('Mother', 'Me, Ma', 1),
('Brother', 'Anh, Em trai', 1),
('Sister', 'Chi, Em gai', 1),
('Grandfather', 'Ong noi/ngoai', 1),
-- Chu de Animals (ID 2)
('Dog', 'Con cho', 2),
('Cat', 'Con meo', 2),
('Elephant', 'Con voi', 2),
('Lion', 'Su tu', 2),
('Monkey', 'Con khi', 2),
-- Chu de Foods (ID 3)
('Bread', 'Banh mi', 3),
('Rice', 'Com, Gao', 3),
('Apple', 'Qua tao', 3),
('Orange', 'Qua cam', 3),
('Milk', 'Sua', 3)
ON DUPLICATE KEY UPDATE tu_tieng_anh=tu_tieng_anh;
