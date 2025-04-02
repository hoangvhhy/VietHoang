CREATE DATABASE FootballStore;
GO
USE FootballStore;
GO


USE master;
DROP TABLE FootballStore;


-- Bảng vai trò (Role)
CREATE TABLE role (
    role_id INT IDENTITY(1,1) PRIMARY KEY,
    role_name NVARCHAR(50) NOT NULL UNIQUE -- Tên vai trò (Khách hàng, Nhân viên, Quản trị viên)
);
GO

-- Bảng tài khoản chung (bao gồm khách hàng, nhân viên, admin)
CREATE TABLE accounts (
    account_id INT IDENTITY(1,1) PRIMARY KEY,
    full_name NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) UNIQUE NOT NULL,
    phone NVARCHAR(max),
    address NVARCHAR(max),
    password NVARCHAR(255) NOT NULL,
    role_id INT NOT NULL, -- Liên kết với bảng role
    FOREIGN KEY (role_id) REFERENCES role(role_id) ON DELETE CASCADE
);
GO

-- Bảng danh mục sản phẩm
CREATE TABLE categories (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    category_name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX)
);
GO

-- Bảng sản phẩm
CREATE TABLE products (
    product_id INT IDENTITY(1,1) PRIMARY KEY,
    category_id INT,
    product_name NVARCHAR(255) NOT NULL,
    brand NVARCHAR(255) NOT NULL, -- Hãng sản xuất
    description NVARCHAR(MAX),
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL,
    image NVARCHAR(255), -- Đường dẫn hình ảnh sản phẩm
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL
);
GO

CREATE TABLE product_imports (
    import_id INT IDENTITY(1,1) PRIMARY KEY,
    product_id INT NOT NULL,
    import_date DATETIME DEFAULT GETDATE(),
    quantity INT NOT NULL,
    import_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);


-- Bảng đơn hàng
CREATE TABLE orders (
    order_id INT IDENTITY(1,1) PRIMARY KEY,
    account_id INT, -- Liên kết với bảng accounts
    order_date DATETIME DEFAULT GETDATE(),
    total_price DECIMAL(10,2) NOT NULL,
    status NVARCHAR(50) DEFAULT 'Pending',
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    CONSTRAINT chk_status CHECK (status IN ('Pending', 'Processing', 'Shipped', 'Cancelled'))
);
GO

-- Bảng chi tiết đơn hàng
CREATE TABLE order_details (
    order_detail_id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);
GO

-- Bảng lưu giỏ hàng của khách hàng
CREATE TABLE cart (
    cart_id INT IDENTITY(1,1) PRIMARY KEY,
    account_id INT,
    product_id INT,
    quantity INT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);
GO

ALTER TABLE products
ADD status NVARCHAR(50) NOT NULL DEFAULT 'Active';

ALTER TABLE accounts
ADD status NVARCHAR(50) NOT NULL DEFAULT 'Active';

ALTER TABLE Orders 
ADD Address NVARCHAR(255);

ALTER TABLE Orders 
ADD PhoneNumber NVARCHAR(255);

INSERT INTO role (role_name) VALUES 
('admin'),
('manager'),
('staff'),
('customer');

INSERT INTO accounts (full_name, email, phone, address, password, role_id) VALUES 
(N'Nguyễn Văn A', N'nguyenvana@gmail.com', '0123456789', N'123 Đường Lê Lợi, Hà Nội', 'password123', 1),
(N'Nguyễn Hoàng S', N'nguyenhoangs@gmail.com', '0123456789', N'123 Đường Lê Lợi, Hà Nội', 'password123', 2),
(N'Trần Thị B', N'tranthib@gmail.com', '0987654321', N'456 Đường Hoàng Diệu, Đà Nẵng', 'password456', 3),
(N'Nguyễn Văn D', 'nguyenvana@example.com', '0987654321', N'Số 10, Đường Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', 'password123', 3),
(N'Trần Thị H', 'tranthib@example.com', '0978654321', N'Số 25, Đường Lạch Tray, Ngô Quyền, Hải Phòng', 'password123', 3),
(N'Lê Văn A', 'levanc@example.com', '0967654321', N'Số 8, Đường Nguyễn Văn Linh, Hải Châu, Đà Nẵng', 'password123', 3),
(N'Phạm Thị D', 'phamthid@example.com', '0957654321', N'Số 15, Đường Lê Lợi, Quận 1, TP.HCM', 'password123', 3),
(N'Hoàng Văn E', 'hoangvane@example.com', '0947654321', N'Số 20, Đường 30/4, Ninh Kiều, Cần Thơ', 'password123', 3),
(N'Đặng Văn F', 'dangvanf@example.com', '0937654321', N'Số 5, Đường Phạm Văn Đồng, Cầu Giấy, Hà Nội', 'password123', 4),
(N'Bùi Thị G', 'buithig@example.com', '0927654321', N'Số 12, Đường Trần Phú, Ngô Quyền, Hải Phòng', 'password123', 4),
(N'Ngô Văn H', 'ngovanh@example.com', '0917654321', N'Số 18, Đường Bạch Đằng, Hải Châu, Đà Nẵng', 'password123', 4),
(N'Đỗ Thị I', 'dothii@example.com', '0907654321', N'Số 22, Đường Võ Văn Kiệt, Quận 1, TP.HCM', 'password123', 4),
(N'Phạm Văn C', N'phamvanc@gmail.com', '0345678901', N'789 Đường Trần Phú, TP. HCM', 'password789', 4),
(N'Vũ Văn K', 'vuvank@example.com', '0897654321', N'Số 30, Đường Trần Quang Khải, Ninh Kiều, Cần Thơ', 'password123', 4);


INSERT INTO categories (category_name, description) VALUES 
(N'Quần áo đá bóng', N'Bộ quần áo thể thao chuyên dụng cho bóng đá'),
(N'Giày đá bóng', N'Giày chuyên dụng để chơi bóng trên sân cỏ nhân tạo và sân tự nhiên'),
(N'Bóng đá', N'Các loại bóng đạt chuẩn FIFA dành cho thi đấu và tập luyện'),
(N'Găng tay thủ môn', N'Găng tay chuyên dụng dành cho thủ môn'),
(N'Phụ kiện', N'Tất, băng keo thể thao, túi đựng đồ và các phụ kiện khác');


-- Quần áo đá bóng
INSERT INTO products (category_id, product_name, brand, description, price, stock_quantity, image) VALUES 
(1, N'Bộ quần áo CLB Real Madrid', N'Adidas', N'Bộ quần áo thi đấu chính thức của CLB Real Madrid mùa 2024', 500000, 100, N'imageKit/realkit.jpg'),
(1, N'Bộ quần áo CLB Barcelona', N'Nike', N'Bộ quần áo thi đấu chính thức của CLB Barcelona mùa 2024', 520000, 90, N'imageKit/barcakit.jpg'),
(1, N'Bộ quần áo CLB Manchester United', N'Adidas', N'Bộ quần áo thi đấu chính thức của MU mùa 2024', 510000, 95, N'imageKit/mukit.jpg'),
(1, N'Bộ quần áo đội tuyển Việt Nam', N'Grand Sport', N'Bộ quần áo thi đấu của đội tuyển Việt Nam', 450000, 200, N'imageKit/vnkit.jpg'),
(1, N'Bộ quần áo CLB Chelsea', N'Nike', N'Bộ quần áo thi đấu chính thức của CLB Chelsea mùa 2024', 530000, 85, N'imageKit/chelseakit.jpg'),
(1, N'Boy Alime siêu ngầu', N'Nike', N'Boy Alime', 530000, 85, N'imageAnother/shrek.jpg');


-- Giày đá bóng
INSERT INTO products (category_id, product_name, brand, description, price, stock_quantity, image) VALUES 
(2, N'Giày đá bóng Nike Mercurial', N'Nike', N'Giày đá bóng cao cấp Nike Mercurial dành cho sân cỏ tự nhiên', 3200000, 50, N'imageShoe/nikemercurial.jpg'),
(2, N'Giày đá bóng Adidas Predator', N'Adidas', N'Giày đá bóng Adidas Predator dành cho sân cỏ nhân tạo', 2800000, 60, N'imageShoe/adidaspedator.jpg'),
(2, N'Giày đá bóng Puma Future', N'Puma', N'Giày đá bóng Puma Future thế hệ mới', 2900000, 55, N'imageShoe/pumafuture.jpg'),
(2, N'Giày đá bóng Mizuno Rebula', N'Mizuno', N'Giày đá bóng Mizuno dành cho sân cỏ nhân tạo', 2700000, 65, N'imageShoe/mizunorebula.jpg'),
(2, N'Giày đá bóng Kamito TA11', N'Kamito', N'Giày đá bóng Kamito dành cho người mới chơi', 1500000, 80, N'imageShoe/kamitota11.jpg');

-- Bóng đá
INSERT INTO products (category_id, product_name, brand, description, price, stock_quantity, image) VALUES 
(3, N'Bóng đá Adidas World Cup', N'Adidas', N'Bóng thi đấu chính thức của World Cup', 1200000, 30, N'imageBall/bongworldcupadidas.jpg'),
(3, N'Bóng đá Nike Flight', N'Nike', N'Bóng đá Nike sử dụng cho các giải đấu chuyên nghiệp', 1100000, 40, N'imageBall/bongnikeflight.jpg'),
(3, N'Bóng đá Molten UEFA', N'Molten', N'Bóng thi đấu của UEFA Champions League', 1300000, 25, N'imageBall/bongmoltenuefa.jpg'),
(3, N'Bóng đá Động Lực UHV 2.07', N'Động Lực', N'Bóng đạt tiêu chuẩn FIFA sản xuất tại Việt Nam', 800000, 50, N'imageBall/bongdongluc.jpg'),
(3, N'Bóng đá Kamito Tango', N'Kamito', N'Bóng đá Kamito giá rẻ dành cho sân cỏ nhân tạo', 600000, 70, N'imageBall/bongkamitotango.jpg');

-- Găng tay thủ môn
INSERT INTO products (category_id, product_name, brand, description, price, stock_quantity, image) VALUES 
(4, N'Găng tay thủ môn Puma màu hồng', N'Puma', N'Găng tay thủ môn chuyên nghiệp của Puma', 750000, 40, 'imageGloves/puma_pink.jpg'),
(4, N'Găng tay thủ môn Adidas Predator màu đen', N'Adidas', N'Găng tay thủ môn Adidas dành cho thi đấu', 850000, 35, 'imageGloves/adidas_predator_black.jpg'),
(4, N'Găng tay thủ môn Nike Vapor Grip 3 màu đỏ ', N'Nike', N'Găng tay thủ môn Nike với công nghệ Grip3', 900000, 30, N'imageGloves/nike_grip3_red.jpg'),
(4, N'Găng tay thủ môn Uhlsport Hyperact SuperGrip màu xanh', N'Uhlsport', N'Găng tay thủ môn cao cấp Uhlsport', 950000, 25, N'imageGloves/uhlsport_hyperact_supergrip_blue.jpg'),
(4, N'Găng tay thủ môn Reusch Attrakt Freegel Gold Shine Bright màu trắng', N'Reusch', N'Găng tay thủ môn Reusch với lớp phủ cao cấp', 1000000, 20, N'imageGloves/reusch_attrakt_white.jpg');

-- Phụ kiện
INSERT INTO products (category_id, product_name, brand, description, price, stock_quantity, image) VALUES 
(5, N'Tất bóng đá dài Nike Classic II màu trắng', N'Nike', N'Tất bóng đá dài dành cho cầu thủ', 100000, 300, N'imageAnother/nike_classic_II_white.jpg'),
(5, N'Băng keo thể thao', N'Adidas', N'Băng keo bảo vệ mắt cá chân', 50000, 150, N'imageAnother/adidas_sports_tape.jpg'),
(5, N'Túi đựng giày Kamito Cool 2.0', N'Kamito', N'Túi đựng giày đá bóng tiện lợi', 120000, 100, N'imageAnother/kamito_cool_shoe_bag.jpg'),
(5, N'Bọc ống đồng Puma Ultra Flex màu hồng', N'Puma', N'Dụng cụ bảo vệ ống đồng khi thi đấu', 180000, 120, N'imageAnother/puma_ultra_flex_pink_shin_guards.jpg'),
(5, N'Găng tay giữ ấm Under Armour Storm màu đen', N'Under Armour', N'Găng tay chống lạnh dành cho cầu thủ mùa đông', 200000, 80, N'imageAnother/under_armour_storm_black_winter_gloves.jpg');


INSERT INTO orders (account_id, order_date, total_price, status) VALUES 
(12, '2025-03-24', 500000, N'Pending'),
(13, '2025-03-24', 2800000, N'Processing'),
(14, '2025-03-24', 1200000, N'Shipped'),
(12, '2025-03-24', 5200000, 'Processing'),
(12, GETDATE(), 2700000, 'Shipped'),
(13, GETDATE(), 3100000, 'Processing'),
(13, GETDATE(), 1500000, 'Pending'),
(14, GETDATE(), 6000000, 'Processing'),
(14, GETDATE(), 3200000, 'Shipped');

INSERT INTO order_details (order_id, product_id, quantity, price) VALUES 
(1, 1, 1, 500000),
(2, 4, 1, 2800000),
(3, 5, 1, 1200000),
(1, 1, 2, 500000),   -- Real Madrid Kit x2
(1, 6, 1, 3200000),  -- Nike Mercurial x1
(2, 4, 3, 450000),   -- Vietnam Kit x3
(3, 7, 2, 2800000),  -- Adidas Predator x2
(4, 15, 1, 1300000), -- Molten UEFA Ball x1
(5, 3, 4, 510000),   -- Manchester United Kit x4
(6, 9, 2, 2700000),  -- Mizuno Rebula Shoes x2
(7, 20, 5, 100000),  -- Nike Classic II Socks x5
(8, 23, 2, 180000),  -- Puma Ultra Flex Shin Guards x2
(9, 10, 1, 1500000); -- Kamito TA11 Shoes x1

INSERT INTO cart (account_id, product_id, quantity) VALUES 
(1, 2, 2),
(2, 3, 1),
(3, 6, 1);



INSERT INTO product_imports (product_id, import_date, quantity, import_price)
SELECT 
    product_id, 
    GETDATE(), -- Ngày nhập hàng là ngày hiện tại
    stock_quantity, -- Nhập hàng ban đầu bằng số lượng tồn kho
    price * 0.6 -- Giá nhập hàng bằng 60% giá bán (có thể chỉnh sửa nếu cần)
FROM products;

