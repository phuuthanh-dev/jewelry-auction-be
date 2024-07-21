USE master;
GO

IF NOT EXISTS (SELECT [name]
               FROM sys.databases
               WHERE [name] = N'DB_AUCTION')
CREATE DATABASE DB_AUCTION;
GO

USE DB_AUCTION;
GO


CREATE TABLE bank
(
    id           INT PRIMARY KEY,
    bank_name    NVARCHAR(100),
    logo         NVARCHAR(255),
    trading_name NVARCHAR(100)
);

-- Insert data into the 'bank' table
INSERT INTO bank (id, bank_name, logo, trading_name)
VALUES (1, N'Ngân hàng Nông nghiệp và Phát triển Nông thôn Việt Nam',
        'https://inhoangkien.vn/wp-content/uploads/2020/04/Agribank-logo-01-e1676880269189-300x124.png', 'Agribank'),
       (2, N'Ngân hàng Xây dựng',
        'https://inhoangkien.vn/wp-content/uploads/2020/04/CBbank-logo-1-01-e1585971864974-300x221.png', 'CB'),
       (3, N'Ngân hàng Đại Dương', 'https://inhoangkien.vn/wp-content/uploads/2020/04/Oceanbank-logo-01-300x183.png',
        'Oceanbank'),
       (4, N'Ngân hàng Dầu Khí Toàn Cầu',
        'https://inhoangkien.vn/wp-content/uploads/2020/04/GPBANK-logo-01-300x183.png', 'GPBank'),
       (5, N'Ngân hàng Đầu tư và Phát triển Việt Nam',
        'https://inhoangkien.vn/wp-content/uploads/2020/04/BIDV-01-e1585972482948-300x126.png', 'BIDV'),
       (6, N'Ngân hàng Công Thương Việt Nam',
        'https://inhoangkien.vn/wp-content/uploads/2020/04/Vietinbank-01-e1585972297613-600x287.png', 'VietinBank'),
       (7, N'Ngân hàng Ngoại thương Việt Nam',
        'https://inhoangkien.vn/wp-content/uploads/2020/04/Vietcombank-01-e1585972386757-300x134.png', 'Vietcombank'),
       (8, N'Ngân hàng Quân đội',
        'https://inhoangkien.vn/wp-content/uploads/2020/04/logo-ngan-hang-MB-01-e1585972573949-300x193.png', 'MBBank'),
       (9, N'Ngân hàng Kỹ Thương Việt Nam',
        'https://inhoangkien.vn/wp-content/uploads/2020/04/Techcombank-01-e1585972434854-300x77.png', 'Techcombank'),
       (10, N'Ngân hàng Á Châu',
        'https://inhoangkien.vn/wp-content/uploads/2020/04/logo-ngan-hang-ACB-PNG-e1585972709842-300x148.png', 'ACB');

-- Create the 'jewelry_category' table if it does not exist

CREATE TABLE jewelry_category
(
    id   INT PRIMARY KEY IDENTITY,
    name NVARCHAR(100)
);

-- Insert data into the 'jewelry_category' table
INSERT INTO jewelry_category (name)
VALUES (N'Dây chuyền'),
       (N'Nhẫn'),
       (N'Vòng tay'),
       (N'Hoa tai'),
       (N'Mặt dây chuyền');

-- Create the user table if it does not exist

CREATE TABLE [dbo].[user]
(
    [id]                  [int] IDENTITY (1,1) NOT NULL,
    [cccd]                [varchar](20)        NOT NULL,
    [cccd_first]          [varchar](MAX)       NOT NULL,
    [cccd_last]           [varchar](MAX)       NOT NULL,
    [cccd_from]           [nvarchar](50)       NOT NULL,
    [address]             [nvarchar](50)       NOT NULL,
    [avatar]              [varchar](max)       NULL,
    [bank_account_name]   [varchar](30)        NOT NULL,
    [bank_account_number] [varchar](30)        NOT NULL,
    [city]                [nvarchar](50)       NOT NULL,
    [district]            [nvarchar](50)       NOT NULL,
    [email]               [varchar](50)        NOT NULL,
    [first_name]          [nvarchar](50)       NOT NULL,
    [last_name]           [nvarchar](50)       NOT NULL,
    [password]            [nvarchar](100)      NULL,
    [phone]               [varchar](15)        NOT NULL,
    [role]                [varchar](255)       NULL,
    [state]               [varchar](10)        NOT NULL,
    [username]            [varchar](50)        NOT NULL,
    [ward]                [nvarchar](50)       NOT NULL,
    [year_of_birth]       [varchar](4)         NOT NULL,
    [bank_id]             [int]                NULL,
    [register_date]       [datetime2](6)       NULL,
    [ban_reason]          [varchar](255)       NULL
)

-- Insert data into the user table
    INSERT INTO [dbo].[user]
([cccd], [address], [avatar], [city], [email], [first_name], [last_name], [password], [phone], [district], [ward],
    [state], [username], [year_of_birth], [role], [bank_id], [bank_account_number], [bank_account_name], [register_date], [cccd_first], [cccd_last], [cccd_from], [ban_reason])
    VALUES ('011234567890', N'1 Lý Thường Kiệt',
            'https://scontent.fsgn2-7.fna.fbcdn.net/v/t39.30808-6/438275285_1101091104487039_4035794765477072253_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=5f2048&_nc_ohc=7MHTOamLKXgQ7kNvgGdad4i&_nc_ht=scontent.fsgn2-7.fna&oh=00_AYDZ0L0Y3_1M_tK5YBX-b1PhjuTPFeLod8Jz1UhW3t_Gkg&oe=6665416E',
            N'Lâm Đồng', 'phuuthanh2003@gmail.com', N'Phùng', N'Thành',
            '$2a$12$j/1n5Pjv4JgzG76ZG0hyH.MD6ftohJNbjuZjRHQFt31Ta/jViwKQ2', '0912345670', N'Hà Nội', N'Hoang', 'ACTIVE',
            'phuuthanh2003', 1985, 'ADMIN', 7, '1030293193991', 'PHUNG HUU THANH', '2024-05-01 17:30:00', 'https://firebasestorage.googleapis.com/v0/b/auction-image-aecbe.appspot.com/o/images-cccd%2F49a92919-a92a-4d32-9f70-db2f641ccb51?alt=media&token=f50f52dc-8954-4a18-845e-16e855b076bb'
           , 'https://firebasestorage.googleapis.com/v0/b/auction-image-aecbe.appspot.com/o/images-cccd%2F79f370d3-d4ea-4690-903e-88a99ade8d38?alt=media&token=b728a278-36fd-4e96-bd64-f34a8b03e5a1',
            'CA MAU', '');


CREATE TABLE [dbo].[jewelry]
(
    [id]          [int] IDENTITY (1,1) NOT NULL,
    [brand]       [nvarchar](20)       NOT NULL,
    [description] [nvarchar](max)      NOT NULL,
    [material]    [nvarchar](20)       NOT NULL,
    [name]        [nvarchar](50)       NOT NULL,
    [buy_now_price]       [float]              NULL,
    [state]       [nvarchar](20)       NOT NULL,
    [weight]      [float]              NOT NULL,
    [category_id] [int]                NULL,
    [user_id]     [int]                NULL,
    [is_holding]  [bit]                NULL,
    [received_date] [datetime2](6)     NULL,
    [delivery_date] [datetime2](6)     NULL,
    [create_date] [datetime2](6)     NULL
)

    INSERT INTO [dbo].[jewelry]
([brand], [description], [material], [name], [buy_now_price], [state], [weight], [category_id], [user_id], [is_holding])
    VALUES (N'DOJI', N'Nhẫn đính hôn bằng kim cương.', 'SILVER', N'NHẪN ĐÍNH HÔN KIM CƯƠNG ENR3111W', 44500000, 'ACTIVE',
            15.5, 2, 1, 1),
            (N'DOJI', N'Nhẫn đính hôn bằng kim cương.', 'SILVER', N'NHẪN CƯỚI KIM CƯƠNG IWR163', 5000000, 'ACTIVE', 8.2, 2,
            1, 1),
            (N'DOJI', N'Nhẫn đính hôn bằng kim cương.', 'SILVER', N'NHẪN KIM CƯƠNG FDR0257', 41130000, 'ACTIVE', 20.1, 2, 1, 1),
            (N'DOJI', N'Nhẫn đính hôn bằng kim cương.', 'SILVER', N'NHẪN KIM CƯƠNG DJR397-22', 37000000, 'ACTIVE', 10.0, 2,
            1, 1),
            (N'Fine Jewelry', N'Dây chuyền kim cương', 'GOLD',  N'Dây chuyền kim cương Y', 18000000, 'ACTIVE', 6.9, 1, 15, 1);

CREATE TABLE [dbo].[auction]
(
    [id]                [int] IDENTITY (1,1) NOT NULL,
    [deposit]           [float]              NOT NULL,
    [description]       [nvarchar](max)      NOT NULL,
    [end_date]          [datetime2](6)       NOT NULL,
    [first_price]       [float]              NOT NULL,
    [last_price]        [float]              NULL,
    [name]              [nvarchar](50)       NOT NULL,
    [participation_fee] [float]              NOT NULL,
    [price_step]        [float]              NOT NULL,
    [start_date]        [datetime2](6)       NOT NULL,
    [state]             [varchar](255)       NULL,
    [jewelry_id]        [int]                NULL,
    [staff_id]          [int]                NULL,
    [create_date]       [datetime2](6)       NULL,
    [end_date_stored]   [datetime2](6)       NULL
)

    INSERT INTO [dbo].[auction]
([deposit], [description], [first_price], [end_date], [last_price], [name], [participation_fee], [price_step],
    [start_date], [state], [jewelry_id], [staff_id], [create_date])
    VALUES (10000000, N'NHẪN ĐÍNH HÔN KIM CƯƠNG ENR3111W', 44500000, '2023-05-19 14:00:00', 70500000,
            N'Đấu giá nhẫn kim cương ENR3111W', 500000, 1000000, '2023-05-19 10:00:00', 'FINISHED', 1, 1, '2023-05-19 01:00:00'),
            (10000000, N'NHẪN CƯỚI KIM CƯƠNG IWR163', 5000000, '2024-05-25 21:00:00', NULL,
            N'Đấu giá NHẪN CƯỚI KIM CƯƠNG IWR163', 100000, 2000000, '2024-05-25 22:00:00', 'ONGOING', 2, 1, '2024-05-25 20:00:00');


CREATE TABLE [dbo].[auction_history]
(
    [id]          [int] IDENTITY (1,1) NOT NULL,
    [price_given] [float]              NOT NULL,
    [time]        [datetime2](6)       NOT NULL,
    [auction_id]  [int]                NULL,
    [user_id]     [int]                NULL,
    [bid_code]    [varchar](20)        NOT NULL,
    [state]       [varchar](255)       NULL,
    );

INSERT INTO [dbo].[auction_history]
( [price_given]
    , [time]
    , [auction_id]
    , [user_id]
    , [bid_code]
    , [state])
VALUES (2550750, '2024-05-01 17:30:00', 1, 1, 'BAS12', 'ACTIVE'),
    (1000750, '2024-05-12 19:30:00', 2, 1, 'BAS22', 'ACTIVE');


CREATE TABLE [dbo].[token]
(
    [id]            [bigint] IDENTITY (1,1) NOT NULL,
    [created_time]  [datetime2](6)          NULL,
    [device_info]   [varchar](255)          NULL,
    [expired]       [bit]                   NOT NULL,
    [ip_address]    [varchar](255)          NULL,
    [refresh_token] [varchar](255)          NULL,
    [revoked]       [bit]                   NOT NULL,
    [token]         [varchar](255)          NULL,
    [token_type]    [varchar](255)          NULL,
    [user_id]       [int]                   NULL
    )

CREATE TABLE [dbo].[image]
(
    [id] [int] IDENTITY(1,1) NOT NULL,
    [data] [varchar](max) NULL,
    [icon] [bit] NULL,
    [link] [varchar](255) NULL,
    [jewelry_id] [int] NULL
)

    INSERT INTO [dbo].[image] ([data], [icon], [link], [jewelry_id])
    VALUES
('https://raw.githubusercontent.com/phuuthanh2003/FE_Deploy/main/public/assets/images/product/small-size/1.jpg', 1, '', 1),
('https://raw.githubusercontent.com/phuuthanh2003/FE_Deploy/main/public/assets/images/product/small-size/2.jpg', 0, '', 1),
('https://raw.githubusercontent.com/phuuthanh2003/FE_Deploy/main/public/assets/images/product/small-size/2-1.jpg', 1, '', 2),
('https://raw.githubusercontent.com/phuuthanh2003/FE_Deploy/main/public/assets/images/product/small-size/2-2.jpg', 0, '', 2),
('https://raw.githubusercontent.com/phuuthanh2003/FE_Deploy/main/public/assets/images/product/small-size/6-1.jpg', 1, '', 3),
('https://raw.githubusercontent.com/phuuthanh2003/FE_Deploy/main/public/assets/images/product/small-size/9-1.jpg', 1, '', 4);

CREATE TABLE [dbo].[transaction](
    [id] [int] IDENTITY(1,1) NOT NULL,
    [create_date] [datetime2](6) NOT NULL,
    [fees_incurred] [float] NOT NULL,
    [payment_method] [varchar](20) NULL,
    [payment_time] [datetime2](6) NULL,
    [transaction_state] [varchar](255) NOT NULL,
    [total_price] [float] NOT NULL,
    [transaction_type] [varchar](255) NOT NULL,
    [auction_id] [int] NULL,
    [user_id] [int] NULL,
    [transaction_code] [varchar](255) NULL,
    [bank_code] [varchar](255) NULL
)

    INSERT INTO [dbo].[transaction] ([create_date], [fees_incurred], [payment_method], [payment_time], [transaction_state], [total_price], [transaction_type], [auction_id], [user_id], [transaction_code], [bank_code])
    VALUES
('2024-05-01 17:30:00', 100000, 'BANKING', '2024-05-01 17:30:00', 'SUCCEED', 1000000, 'REGISTRATION', 1, 1, 'BAS12', 'AGB'),
('2024-05-12 19:30:00', 200000, 'BANKING', '2024-05-12 19:30:00', 'SUCCEED', 2000000, 'REGISTRATION', 2, 1, 'BAS22', 'NCB');



CREATE TABLE [dbo].[auction_registration]
(
    [id] [int] IDENTITY(1,1) NOT NULL,
    [auction_registration_state] [varchar](255) NOT NULL,
    [registration_date] [datetime2](6) NOT NULL,
    [registration_fee] [float] NOT NULL,
    [auction_id] [int] NULL,
    [transaction_id] [int] NULL,
    [user_id] [int] NULL,
    [kick_reason] [nvarchar](255) NULL
)

    INSERT INTO [dbo].[auction_registration] ([auction_registration_state], [registration_date], [registration_fee], [auction_id], [transaction_id], [user_id])
    VALUES
('VALID', '2024-05-01 17:30:00', 1000000, 1, 1, 1),
('VALID', '2024-05-12 19:30:00', 2000000, 2, 1, 1);

CREATE TABLE [dbo].[request_approval](
    [id] [int] IDENTITY(1,1) NOT NULL,
    [desired_price] [float] NOT NULL,
    [is_confirm] [bit] NULL,
    [note] [nvarchar](255) NULL,
    [request_time] [datetime2](6) NOT NULL,
    [response_time] [datetime2](6) NULL,
    [state] [varchar](255) NULL,
    [valuation] [float] NULL,
    [jewelry_id] [int] NULL,
    [user_id_respond] [int] NULL,
    [user_id_send] [int] NULL,
    [staff_id] [int] NULL
    )

    INSERT INTO [dbo].[request_approval] ([desired_price], [is_confirm], [note], [request_time], [response_time], [state], [valuation], [jewelry_id], [user_id_respond], [user_id_send], [staff_id])
    VALUES
(1000000, 1, 'Nhẫn kim cương', '2024-05-01 17:30:00', '2024-05-01 17:30:00', 'ACTIVE', 1000000, 1, 1, 2, 1);