-- Danh mục sản phẩm (Điện thoại, phụ kiện)
CREATE TABLE Categories
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci,
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW()
);

CREATE TABLE Brand
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    brand_name VARCHAR(70) CHARACTER SET utf8 COLLATE utf8_general_ci,
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW()

);

-- Bảng sản phẩm
CREATE TABLE Product
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    product_name TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    price DECIMAL(20,0),
    discount DECIMAL(20,0),
    note TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    images TEXT,
    category_id INT(11),
    number_of_sale INT(11), -- Số lần bán
    brand_id INT(11),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (category_id) REFERENCES Categories(id),
    FOREIGN KEY (brand_id) REFERENCES Brand(id)
);

-- Bảng hình ảnh của sản phẩm
CREATE TABLE ProductImage
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    product_image_name VARCHAR(50),
    product_id INT(11),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (product_id) REFERENCES Product(id)
);

CREATE TABLE Warehouse
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    amount INT(11),
    product_id INT(11),
    status INT(11),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (product_id) REFERENCES Product(id)
);



-- Giỏ hàng
CREATE TABLE Cart
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    card_status TINYINT(1),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW()
);

-- Chi tiết giỏ hàng
CREATE TABLE CartDetail
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    cart_id INT(11),
    product_id INT(11),
    -- Số lượng sp
    amount INT(11),
    -- Đơn giá sp
    price DECIMAL(20,0),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (cart_id) REFERENCES Cart(id),
    FOREIGN KEY (product_id) REFERENCES Product(id)
);

-- Bảng chứa thông tin giao hàng
CREATE TABLE ShipDetail
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    phone VARCHAR(20),
    address TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW()
);

-- Quyền(Người bán, khách thường)
CREATE TABLE Roles
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW()
);

-- Đây là Bảngg tài khoản
CREATE TABLE Account
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20),
    password VARCHAR(20),
    email VARCHAR(255) unique,
    date_of_birth DATETIME DEFAULT NOW(),
    full_name VARCHAR(50),
    phone VARCHAR(20) unique,
    ship_detail_id INT(11),
    cart_id INT(11),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (ship_detail_id) REFERENCES ShipDetail(id),
    FOREIGN KEY (cart_id) REFERENCES Cart(id)
);

--  Đây là Bảngg chi tiết quyền
CREATE TABLE RolesDetail
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    account_id INT(11),
    role_id INT(11),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (account_id) REFERENCES Account(id),
    FOREIGN KEY (role_id) REFERENCES Roles(id)
);

-- Hóa đơn
CREATE TABLE Orders
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    -- Ngày đặt hàng
    order_date DATETIME DEFAULT NOW(),
    --  Ghi chú của khách hàng
    note TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    -- Trạng thái đơn hàng
    order_status INT(11),
    -- Tổng tiền
    total_money DECIMAL(20,0),
    -- Ngày giao dự kiến (mặc định + lên 3 ngày)
    delivery_date DATETIME  DEFAULT NOW() ,
    -- Người đặt hàng
    account_id INT(11),
    ship_detail_id INT(11),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (ship_detail_id) REFERENCES ShipDetail(id),
    FOREIGN KEY (account_id) REFERENCES Account(id)
);

--  Chi tiết hóa đơn
CREATE TABLE OdersDetail
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    order_id INT(11),
    product_id INT(11),
    -- Số lượng sp
    amount INT(11),
    -- Đơn giá sp
    price DECIMAL(20,0),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (order_id) REFERENCES Orders(id),
    FOREIGN KEY (product_id) REFERENCES Product(id)
);


INSERT INTO  Account (username,password,email,date_of_birth,full_name,phone) values
('anhhv','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','hoangvietanh02112002@gmail.com','2002-11-02','Hoàng Việt Anh','0346135361'),
('cuongdm','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','damdinhcuong@gmail.com','2002-07-05','Đàm Đình Cường','0346135362'),
('trietnpm','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','nguyenphamminhtriet@gmail.com','2002-07-05','Nguyễn Phạm Minh Triết','0346135363'),
('longpp','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','phamphilong@gmail.com','2002-07-05','Phạm Phi Long','0346135364'),
('hainh','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','nguyenhoanghai0507@gmail.com','2002-07-05','Nguyễn Hoàng Hải','0346135365'),
('thuatlh','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','lehoangthuat@gmail.com','2002-11-02','Lê Hoàng Thuật','0346135366');

INSERT INTO Roles (role_name) values('OWNER'),('CUSTOMER'),('CUSTOMER_VIP'),('EMPLOYEE');

INSERT INTO RolesDetail (account_id,role_id) values(5,1),(2,2),(2,4);