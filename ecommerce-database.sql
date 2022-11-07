DROP DATABASE WatchShop

create DATABASE WatchShop
use WatchShop
-- Danh mục sản phẩm (Điện thoại, phụ kiện)
CREATE TABLE categories
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci,
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW()
);

CREATE TABLE brand
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    brand_name VARCHAR(70) CHARACTER SET utf8 COLLATE utf8_general_ci,
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW()

);

-- Bảng sản phẩm
CREATE TABLE product
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
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (brand_id) REFERENCES brand(id)
);

-- Bảng hình ảnh của sản phẩm
CREATE TABLE product_image
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    product_image_name VARCHAR(50),
    product_id INT(11),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE ware_house
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    amount INT(11),
    product_id INT(11),
    status INT(11),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (product_id) REFERENCES product(id)
);



-- Giỏ hàng
CREATE TABLE cart
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    card_status TINYINT(1),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW()
);

-- Chi tiết giỏ hàng
CREATE TABLE cart_detail
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
    FOREIGN KEY (cart_id) REFERENCES cart(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- Quyền(Người bán, khách thường)
CREATE TABLE roles
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW()
);

-- Đây là Bảngg tài khoản
CREATE TABLE account
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20),
    password TEXT,
    email VARCHAR(255) unique,
    date_of_birth DATETIME DEFAULT NOW(),
    full_name VARCHAR(50),
    phone VARCHAR(20) unique,
    cart_id INT(11),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (cart_id) REFERENCES cart(id)
);

-- Bảng chứa thông tin giao hàng
CREATE TABLE ship_detail
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    phone VARCHAR(20),
    address TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    account_id INT(11),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

--  Đây là Bảngg chi tiết quyền
CREATE TABLE roles_detail
(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    account_id INT(11),
    role_id INT(11),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (account_id) REFERENCES account(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Hóa đơn
CREATE TABLE orders
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
    FOREIGN KEY (ship_detail_id) REFERENCES ship_detail(id),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

--  Chi tiết hóa đơn
CREATE TABLE oders_detail
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
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);


INSERT INTO  account (username,password,email,date_of_birth,full_name,phone) values
('anhhv','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','hoangvietanh02112002@gmail.com','2002-11-02','Hoàng Việt Anh','0346135361'),
('cuongdm','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','damdinhcuong@gmail.com','2002-07-05','Đàm Đình Cường','0346135362'),
('trietnpm','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','nguyenphamminhtriet@gmail.com','2002-07-05','Nguyễn Phạm Minh Triết','0346135363'),
('longpp','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','phamphilong@gmail.com','2002-07-05','Phạm Phi Long','0346135364'),
('hainh','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','nguyenhoanghai0507@gmail.com','2002-07-05','Nguyễn Hoàng Hải','0346135365'),
('thuatlh','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','lehoangthuat@gmail.com','2002-11-02','Lê Hoàng Thuật','0346135366');


INSERT INTO roles (role_name) values('OWNER'),('CUSTOMER'),('CUSTOMER_VIP'),('EMPLOYEE');

INSERT INTO roles_detail (account_id,role_id) values(1,1),(2,2),(2,4);

CREATE PROCEDURE WatchShop.sp_g_list_user_roles(
	IN userName VARCHAR(50)
)
    READS SQL DATA
BEGIN
	
	DROP TEMPORARY TABLE IF EXISTS tbl_roles;
	CREATE TEMPORARY TABLE tbl_roles AS (
		SELECT 	a.id AS account_id,
				JSON_UNQUOTE(CONCAT('[',GROUP_CONCAT(
					JSON_OBJECT(
					'name', r.role_name,
					'id',r.id 
		 			)SEPARATOR ','),']')) AS roles
		FROM 	roles r
				LEFT JOIN roles_detail rd ON r.id = rd.role_id 
				LEFT JOIN account a ON a.id = rd.account_id 
		WHERE 	a.username = userName
		GROUP BY a.id 
	);
	
	
	SELECT 	a.id,
			a.username ,
			a.password ,
			a.email ,
			a.full_name ,
			a.phone ,
			tr.roles
	FROM 	account a
			LEFT JOIN tbl_roles tr ON a.id = tr.account_id
	WHERE 	a.username = userName;

	DROP TEMPORARY TABLE IF EXISTS tbl_roles;
END




insert into brand(brand_name,is_deleted)
values (N'Casio',0),
(N'Orient',0),
(N'Citizen',0),
(N'Movado',0),
(N'Fossil',0)

insert into categories(category_name,is_deleted)
values (N'Nam',0),
(N'Nữ',0),
(N'Cặp đôi',0),
(N'Trẻ em',0)

insert into product(product_name,price,discount,images,note,number_of_sale,category_id,brand_id,is_deleted)
values (N'Casio World Time AE1200WHD',2000000,10,'none.png',
N'Đồng hồ nam Casio AE1200WHD có mặt đồng hồ vuông to với phong cách thể thao, mặt số điện tử với những tính năng hiện đại tiện dụng, kết hợp với dây đeo bằng kim loại đem lại vẻ mạnh mẽ cá tính dành cho phái nam.',
20,1,1,0),
(N'Orient ABC356',3000000,15,'none.png',
N'Đồng hồ nam Orient, mặt số điện tử với những tính năng hiện đại tiện dụng, kết hợp với dây đeo bằng kim loại đem lại vẻ mạnh mẽ cá tính dành cho phái nam.',
20,1,2,0),
(N'Citizen HM456',4000000,10,'none.png',
N'Đồng hồ nữ Citizen có mặt đồng hồ vuông to với phong cách thể thao, mặt số điện tử với những tính năng hiện đại tiện dụng, kết hợp với dây đeo bằng kim loại đem lại vẻ mạnh mẽ cá tính dành cho phái nam.',
20,2,3,0)

select * from ship_detail sd 


select * from orders o 

