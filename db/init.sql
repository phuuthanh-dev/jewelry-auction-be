CREATE DATABASE IF NOT EXISTS DB_AUCTION DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;;
USE DB_AUCTION;

-- Create the 'bank' table
CREATE TABLE bank (
      id INT PRIMARY KEY,
      bank_name VARCHAR(100),
      logo LONGTEXT,
      trading_name VARCHAR(100)
);

-- Insert data into the 'bank' table
INSERT INTO bank (id, bank_name, logo, trading_name)
VALUES
    (1, 'Ngân hàng Nông nghiệp và Phát triển Nông thôn Việt Nam',
     'https://inhoangkien.vn/wp-content/uploads/2020/04/Agribank-logo-01-e1676880269189-300x124.png', 'Agribank'),
    (2, 'Ngân hàng Xây dựng',
     'https://inhoangkien.vn/wp-content/uploads/2020/04/CBbank-logo-1-01-e1585971864974-300x221.png', 'CB'),
    (3, 'Ngân hàng Đại Dương', 'https://inhoangkien.vn/wp-content/uploads/2020/04/Oceanbank-logo-01-300x183.png',
     'Oceanbank'),
    (4, 'Ngân hàng Dầu Khí Toàn Cầu',
     'https://inhoangkien.vn/wp-content/uploads/2020/04/GPBANK-logo-01-300x183.png', 'GPBank'),
    (5, 'Ngân hàng Đầu tư và Phát triển Việt Nam',
     'https://inhoangkien.vn/wp-content/uploads/2020/04/BIDV-01-e1585972482948-300x126.png', 'BIDV'),
    (6, 'Ngân hàng Công Thương Việt Nam',
     'https://inhoangkien.vn/wp-content/uploads/2020/04/Vietinbank-01-e1585972297613-600x287.png', 'VietinBank'),
    (7, 'Ngân hàng Ngoại thương Việt Nam',
     'https://inhoangkien.vn/wp-content/uploads/2020/04/Vietcombank-01-e1585972386757-300x134.png', 'Vietcombank'),
    (8, 'Ngân hàng Quân đội',
     'https://inhoangkien.vn/wp-content/uploads/2020/04/logo-ngan-hang-MB-01-e1585972573949-300x193.png', 'MBBank'),
    (9, 'Ngân hàng Kỹ Thương Việt Nam',
     'https://inhoangkien.vn/wp-content/uploads/2020/04/Techcombank-01-e1585972434854-300x77.png', 'Techcombank'),
    (10, 'Ngân hàng Á Châu',
     'https://inhoangkien.vn/wp-content/uploads/2020/04/logo-ngan-hang-ACB-PNG-e1585972709842-300x148.png', 'ACB');

-- Create the 'jewelry_category' table
CREATE TABLE jewelry_category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

-- Insert data into the 'jewelry_category' table
INSERT INTO jewelry_category (name)
VALUES
    ('Dây chuyền'),
    ('Nhẫn'),
    ('Vòng tay'),
    ('Hoa tai'),
    ('Mặt dây chuyền');

-- Create the 'user' table
CREATE TABLE `user` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cccd VARCHAR(20) NOT NULL,
    cccd_first LONGTEXT NOT NULL,
    cccd_last LONGTEXT NOT NULL,
    cccd_from VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    avatar LONGTEXT,
    bank_account_name VARCHAR(30) NOT NULL,
    bank_account_number VARCHAR(30) NOT NULL,
    city VARCHAR(50) NOT NULL,
    district VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    password VARCHAR(100),
    phone VARCHAR(15) NOT NULL,
    role VARCHAR(255),
    state VARCHAR(10) NOT NULL,
    username VARCHAR(50) NOT NULL,
    ward VARCHAR(50) NOT NULL,
    year_of_birth VARCHAR(4) NOT NULL,
    bank_id INT,
    register_date DATETIME,
    ban_reason VARCHAR(255)
);

-- Insert data into the 'user' table
INSERT INTO `user`
(cccd, address, avatar, city, email, first_name, last_name, password, phone, district, ward, state, username, year_of_birth, role, bank_id, bank_account_number, bank_account_name, register_date, cccd_first, cccd_last, cccd_from, ban_reason)
VALUES
    ('011234567890', '1 Lý Thường Kiệt',
     'https://scontent.fsgn2-7.fna.fbcdn.net/v/t39.30808-6/438275285_1101091104487039_4035794765477072253_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=5f2048&_nc_ohc=7MHTOamLKXgQ7kNvgGdad4i&_nc_ht=scontent.fsgn2-7.fna&oh=00_AYDZ0L0Y3_1M_tK5YBX-b1PhjuTPFeLod8Jz1UhW3t_Gkg&oe=6665416E',
     'Lâm Đồng', 'phuuthanh2003@gmail.com', 'Phùng', 'Thành',
     '$2a$12$j/1n5Pjv4JgzG76ZG0hyH.MD6ftohJNbjuZjRHQFt31Ta/jViwKQ2', '0912345670', 'Hà Nội', 'Hoang', 'ACTIVE',
     'phuuthanh2003', '1985', 'ADMIN', 7, '1030293193991', 'PHUNG HUU THANH', '2024-05-01 17:30:00',
     'https://firebasestorage.googleapis.com/v0/b/auction-image-aecbe.appspot.com/o/images-cccd%2F49a92919-a92a-4d32-9f70-db2f641ccb51?alt=media&token=f50f52dc-8954-4a18-845e-16e855b076bb',
     'https://firebasestorage.googleapis.com/v0/b/auction-image-aecbe.appspot.com/o/images-cccd%2F79f370d3-d4ea-4690-903e-88a99ade8d38?alt=media&token=b728a278-36fd-4e96-bd64-f34a8b03e5a1',
     'CA MAU', '');

-- Create the 'jewelry' table
CREATE TABLE jewelry (
     id INT AUTO_INCREMENT PRIMARY KEY,
     brand VARCHAR(20) NOT NULL,
     description TEXT NOT NULL,
     material VARCHAR(20) NOT NULL,
     name VARCHAR(50) NOT NULL,
     buy_now_price FLOAT,
     state VARCHAR(20) NOT NULL,
     weight FLOAT NOT NULL,
     category_id INT,
     user_id INT,
     is_holding BOOLEAN,
     received_date DATETIME,
     delivery_date DATETIME,
     create_date DATETIME
);

-- Insert data into the 'jewelry' table
INSERT INTO jewelry
(brand, description, material, name, buy_now_price, state, weight, category_id, user_id, is_holding)
VALUES
    ('DOJI', 'Nhẫn đính hôn bằng kim cương.', 'SILVER', 'NHẪN ĐÍNH HÔN KIM CƯƠNG ENR3111W', 44500000, 'ACTIVE', 15.5, 2, 1, 1),
    ('DOJI', 'Nhẫn đính hôn bằng kim cương.', 'SILVER', 'NHẪN CƯỚI KIM CƯƠNG IWR163', 5000000, 'ACTIVE', 8.2, 2, 1, 1),
    ('DOJI', 'Nhẫn đính hôn bằng kim cương.', 'SILVER', 'NHẪN KIM CƯƠNG FDR0257', 41130000, 'ACTIVE', 20.1, 2, 1, 1),
    ('DOJI', 'Nhẫn đính hôn bằng kim cương.', 'SILVER', 'NHẪN KIM CƯƠNG DJR397-22', 37000000, 'ACTIVE', 10.0, 2, 1, 1),
    ('Fine Jewelry', 'Dây chuyền kim cương', 'GOLD', 'Dây chuyền kim cương Y', 18000000, 'ACTIVE', 6.9, 1, 15, 1);

-- Create table `auction`
CREATE TABLE `auction` (
   `id` INT AUTO_INCREMENT PRIMARY KEY,
   `deposit` FLOAT NOT NULL,
   `description` LONGTEXT NOT NULL,
   `end_date` DATETIME(6) NOT NULL,
   `first_price` FLOAT NOT NULL,
   `last_price` FLOAT NULL,
   `name` VARCHAR(50) NOT NULL,
   `participation_fee` FLOAT NOT NULL,
   `price_step` FLOAT NOT NULL,
   `start_date` DATETIME(6) NOT NULL,
   `state` VARCHAR(255) NULL,
   `jewelry_id` INT NULL,
   `staff_id` INT NULL,
   `create_date` DATETIME(6) NULL,
   `end_date_stored` DATETIME(6) NULL
);

-- Insert data into `auction`
INSERT INTO `auction` (`deposit`, `description`, `first_price`, `end_date`, `last_price`, `name`, `participation_fee`, `price_step`, `start_date`, `state`, `jewelry_id`, `staff_id`, `create_date`)
VALUES
    (10000000, 'NHẪN ĐÍNH HÔN KIM CƯƠNG ENR3111W', 44500000, '2023-05-19 14:00:00', 70500000, 'Đấu giá nhẫn kim cương ENR3111W', 500000, 1000000, '2023-05-19 10:00:00', 'FINISHED', 1, 1, '2023-05-19 01:00:00'),
    (10000000, 'NHẪN CƯỚI KIM CƯƠNG IWR163', 5000000, '2024-05-25 21:00:00', NULL, 'Đấu giá NHẪN CƯỚI KIM CƯƠNG IWR163', 100000, 2000000, '2024-05-25 22:00:00', 'ONGOING', 2, 1, '2024-05-25 20:00:00');

-- Create table `auction_history`
CREATE TABLE `auction_history` (
   `id` INT AUTO_INCREMENT PRIMARY KEY,
   `price_given` FLOAT NOT NULL,
   `time` DATETIME(6) NOT NULL,
   `auction_id` INT NULL,
   `user_id` INT NULL,
   `bid_code` VARCHAR(20) NOT NULL,
   `state` VARCHAR(255) NULL
);

-- Insert data into `auction_history`
INSERT INTO `auction_history` (`price_given`, `time`, `auction_id`, `user_id`, `bid_code`, `state`)
VALUES
    (2550750, '2024-05-01 17:30:00', 1, 1, 'BAS12', 'ACTIVE'),
    (1000750, '2024-05-12 19:30:00', 2, 1, 'BAS22', 'ACTIVE');

-- Create table `token`
CREATE TABLE `token` (
     `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
     `created_time` DATETIME(6) NULL,
     `device_info` VARCHAR(255) NULL,
     `expired` BOOLEAN NOT NULL,
     `ip_address` VARCHAR(255) NULL,
     `refresh_token` VARCHAR(255) NULL,
     `revoked` BOOLEAN NOT NULL,
     `token` VARCHAR(255) NULL,
     `token_type` VARCHAR(255) NULL,
     `user_id` INT NULL
);

-- Create table `image`
CREATE TABLE `image` (
     `id` INT AUTO_INCREMENT PRIMARY KEY,
     `data` LONGTEXT NULL,
     `icon` BOOLEAN NULL,
     `link` VARCHAR(255) NULL,
     `jewelry_id` INT NULL
);

-- Insert data into `image`
INSERT INTO `image` (`data`, `icon`, `link`, `jewelry_id`)
VALUES
    ('https://raw.githubusercontent.com/phuuthanh2003/FE_Deploy/main/public/assets/images/product/small-size/1.jpg', TRUE, '', 1),
    ('https://raw.githubusercontent.com/phuuthanh2003/FE_Deploy/main/public/assets/images/product/small-size/2.jpg', FALSE, '', 1),
    ('https://raw.githubusercontent.com/phuuthanh2003/FE_Deploy/main/public/assets/images/product/small-size/2-1.jpg', TRUE, '', 2),
    ('https://raw.githubusercontent.com/phuuthanh2003/FE_Deploy/main/public/assets/images/product/small-size/2-2.jpg', FALSE, '', 2),
    ('https://raw.githubusercontent.com/phuuthanh2003/FE_Deploy/main/public/assets/images/product/small-size/6-1.jpg', TRUE, '', 3),
    ('https://raw.githubusercontent.com/phuuthanh2003/FE_Deploy/main/public/assets/images/product/small-size/9-1.jpg', TRUE, '', 4);

-- Create table `transaction`
CREATE TABLE `transaction` (
   `id` INT AUTO_INCREMENT PRIMARY KEY,
   `create_date` DATETIME(6) NOT NULL,
   `fees_incurred` FLOAT NOT NULL,
   `payment_method` VARCHAR(20) NULL,
   `payment_time` DATETIME(6) NULL,
   `transaction_state` VARCHAR(255) NOT NULL,
   `total_price` FLOAT NOT NULL,
   `transaction_type` VARCHAR(255) NOT NULL,
   `auction_id` INT NULL,
   `user_id` INT NULL,
   `transaction_code` VARCHAR(255) NULL,
   `bank_code` VARCHAR(255) NULL
);

-- Insert data into `transaction`
INSERT INTO `transaction` (`create_date`, `fees_incurred`, `payment_method`, `payment_time`, `transaction_state`, `total_price`, `transaction_type`, `auction_id`, `user_id`, `transaction_code`, `bank_code`)
VALUES
    ('2024-05-01 17:30:00', 100000, 'BANKING', '2024-05-01 17:30:00', 'SUCCEED', 1000000, 'REGISTRATION', 1, 1, 'BAS12', 'AGB'),
    ('2024-05-12 19:30:00', 200000, 'BANKING', '2024-05-12 19:30:00', 'SUCCEED', 2000000, 'REGISTRATION', 2, 1, 'BAS22', 'NCB');

-- Create table `auction_registration`
CREATE TABLE `auction_registration` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `auction_registration_state` VARCHAR(255) NOT NULL,
    `registration_date` DATETIME(6) NOT NULL,
    `registration_fee` FLOAT NOT NULL,
    `auction_id` INT NULL,
    `transaction_id` INT NULL,
    `user_id` INT NULL,
    `kick_reason` LONGTEXT NULL
);

-- Insert data into `auction_registration`
INSERT INTO `auction_registration` (`auction_registration_state`, `registration_date`, `registration_fee`, `auction_id`, `transaction_id`, `user_id`)
VALUES
    ('VALID', '2024-05-01 17:30:00', 1000000, 1, 1, 1),
    ('VALID', '2024-05-12 19:30:00', 2000000, 2, 1, 1);

-- Create table `request_approval`
CREATE TABLE `request_approval` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `desired_price` FLOAT NOT NULL,
    `is_confirm` BOOLEAN NULL,
    `note` LONGTEXT NULL,
    `request_time` DATETIME(6) NOT NULL,
    `response_time` DATETIME(6) NULL,
    `state` VARCHAR(255) NULL,
    `valuation` FLOAT NULL,
    `jewelry_id` INT NULL,
    `user_id_respond` INT NULL,
    `user_id_send` INT NULL,
    `staff_id` INT NULL
);

-- Insert data into `request_approval`
INSERT INTO `request_approval` (`desired_price`, `is_confirm`, `note`, `request_time`, `response_time`, `state`, `valuation`, `jewelry_id`, `user_id_respond`, `user_id_send`, `staff_id`)
VALUES
    (1000000, TRUE, 'Nhẫn kim cương', '2024-05-01 17:30:00', '2024-05-01 17:30:00', 'ACTIVE', 1000000, 1, 1, 2, 1);
