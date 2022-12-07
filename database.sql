DROP DATABASE WatchShop2

create DATABASE WatchShop2
use WatchShop2
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
    price INT,
    discount INT,
    note TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    images TEXT,
    category_id INT(11),
    number_of_sale INT(11), -- Số lần bán
    brand_id INT(11),
    warehouse_id INT(11),
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
    import_price decimal(20,0),
    product_id INT(11),
    note TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    total_price decimal(20,0),
    status INT(11),
    is_deleted TINYINT(1) DEFAULT 0 ,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

alter table product add constraint FK_product_warehouse foreign key (warehouse_id) references ware_house(id)


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
    full_name VARCHAR(50),
    phone VARCHAR(20) unique,
    gender VARCHAR(10),
    date_of_birth DATE,  
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
    province_id INT(11)
    province TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    district_id INT(11)
    district TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    ward_id INT(11)
    ward TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    address_more TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    full_name VARCHAR(50),
    address TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    full_name VARCHAR(50),
    is_default TINYINT(1) DEFAULT 0 ,
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
    order_status TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    -- Tổng tiền
    total_money DECIMAL(20,0),
    -- Ngày giao dự kiến (mặc định + lên 3 ngày)
    delivery_date DATETIME,
    -- Người đặt hàng
    account_id INT(11),
    payment_method TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    payment_status TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
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

-- đánh giá
create table reviews
(
	id INT(11) AUTO_INCREMENT PRIMARY KEY,
	comment TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
	rate INT,
	product_id int(11),
	account_id int(11),
	order_id int(11),
	is_deleted TINYINT(1) DEFAULT 0,
	created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (account_id) REFERENCES account(id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- Flash sale
create table sale
(
	id INT AUTO_INCREMENT PRIMARY KEY,
	sale_date_start date,
	sale_time_start time,
	sale_date_end date,
	sale_time_end time,
	is_deleted TINYINT(1) DEFAULT 0,
	created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW()
);

create table sale_detail
(
	id INT(11) AUTO_INCREMENT PRIMARY KEY,
	sale_id INT(11),
	product_id INT(11),
	sale_name TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
	discount_old int,
	discount_sale int,
	created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW(),
    FOREIGN KEY (sale_id) REFERENCES sale(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

create table calendar
(
	report_day date not null,
);

insert into cart (is_deleted)
values (0),
(0),
(0),
(0),
(0),
(0),
(0)

INSERT INTO  account (username,password,email,date_of_birth,full_name,phone,gender,cart_id,is_deleted) values
('anhhv','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','hoangvietanh02112002@gmail.com','2002-11-02','Hoàng Việt Anh','0346135361',N'Nam',1,0),
('cuongdm','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','damdinhcuong@gmail.com','2002-07-05','Đàm Đình Cường','0346135362',N'Nam',2,0),
('trietnpm','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','nguyenphamminhtriet@gmail.com','1999-08-22','Nguyễn Phạm Minh Triết','0346135363',N'Nam',3,0),
('longpp','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','phamphilong@gmail.com','2002-07-05','Phạm Phi Long','0346135364',N'Nam',4,0),
('hainh','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','nguyenhoanghai0507@gmail.com','2002-07-05','Nguyễn Hoàng Hải','0346135365',N'Nam',5,0),
('thuatlh','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','lehoangthuat@gmail.com','2002-11-02','Lê Hoàng Thuật','0346135366',N'Nam',6,0),
('admin','$2a$10$0ijqrFYFWglp4DLQ5dJu9.2SWDhchnoW.09gkiW7iUBtX7Vu4xKfe','admin@gmail.com','1999-11-02','Admin','0345837123',N'Nam',7,0);

INSERT INTO roles (role_name) values('OWNER'),('CUSTOMER'),('CUSTOMER_VIP'),('EMPLOYEE');

INSERT INTO roles_detail (account_id,role_id,is_deleted) values (1,1,0),(2,2,0),(2,4,0),(5,1,0),(5,2,0),(7,1,0),(7,2,0),(7,3,0),(7,4,0);

insert into brand(brand_name,is_deleted)
values 
(N'Casio',0),
(N'Orient',0),
(N'Citizen',0),
(N'Movado',0),
(N'Fossil',0),
(N'Doxa',0),
(N'SeiKo',0),
(N'Tissot',0),
(N'G-sock',0)


insert into categories(category_name,is_deleted)
values 
(N'Nam',0),
(N'Nữ',0),
(N'Cặp đôi',0),
(N'Trẻ em',0)

insert into product(product_name,price,discount,images,note,number_of_sale,category_id,brand_id,is_deleted)
values (N'Casio AE1200WHD',2000000,10,'Casio World Time AE1200WHD.png',
N'Đồng hồ nam Casio AE1200WHD có mặt đồng hồ vuông to với phong cách thể thao, mặt số điện tử với những tính năng hiện đại tiện dụng, kết hợp với dây đeo bằng kim loại đem lại vẻ mạnh mẽ cá tính dành cho phái nam.',
20,1,1,0),
(N'Orient ABC356',3000000,15,'Orient ABC356.png',
N'Đồng hồ nam Orient, mặt số điện tử với những tính năng hiện đại tiện dụng, kết hợp với dây đeo bằng kim loại đem lại vẻ mạnh mẽ cá tính dành cho phái nam.',
20,1,2,0),
(N'Citizen HM456',4000000,10,'Citizen HM456.png',
N'Đồng hồ nữ Citizen có mặt đồng hồ vuông to với phong cách thể thao, mặt số điện tử với những tính năng hiện đại tiện dụng, kết hợp với dây đeo bằng kim loại đem lại vẻ mạnh mẽ cá tính dành cho phái nam.',
20,2,3,0),
(N'HAMILTON SYLVIA',2399000,10,'HAMILTON SYLVIA.png',
N'Đồng hồ nữ Curnon Hamilton Sylvia phong cách vintage, nhẹ nhàng nhưng vẫn rất cuốn hút, thanh lịch; Dây kim loại dạng lưới, Chống nước 3ATM, Kính Sapphire chống xước...',
20,2,3,0),
(N'ORIENT NAM KÍNH SAPPHIRE' ,4160000,10,'ORIENT.png',
N'Đồng hồ dây da Orient FGW0100AW0 dành cho nam giới với mặt đồng hồ nền trắng 2 tầng, điểm nhấn nổi bật với phiên bản mặt kính Sapphire size 38mm',
20,1,2,0),
(N'CASIO MTP-1381D-1AVDF' ,1710000,10,'CASIO MTP-1381D-1AVDF.png',
N'Đồng hồ Casio MTP-1381D-1AVDF có vỏ và dây đeo kim loại phủ bạc sáng bóng, nền số màu đen mạnh mẽ với kim chỉ và vạch số được phủ phản quang nổi bật, lịch thứ vị trí 12h và lịch ngày vị trí 6h.',
20,1,1,0),
(N'ORIENT CABALLERO' ,8690000,20,'ORIENT CABALLERO.png',
N'Mẫu đồng hồ nam Orient FAG00002W0 với phong cách cổ điển khi kết hợp giữa vỏ máy bằng kim loại mạ vàng ánh lên sự sang trọng phối cùng bộ dây đeo bằng da tông màu nâu lịch lãm.',
20,1,1,0),
(N'G-SHOCK NAM (GA-110-1BDR)' ,4612000,15,'G-SHOCK NAM.png',
N'Đồng hồ G-Shock GA-110-1BDR với chất liệu nhựa cao cấp siêu bền, vỏ và dây đeo có màu đen, chống va chạm mạnh, mặt kính khoáng chịu lực, khả năng chống nước lên đến 20ATM.',
20,1,9,0),
(N'TISSOT TRADITION T063',14060000 ,10,'TISSOT TRADITION.png',
N'Đồng hồ Tissot T063 với thiết kế mặt số màu trắng, kim chỉ và gạch số được mạ tone màu vàng hồng, mang cảm giác cổ điển, dây đeo da màu nâu tạo nét lịch lãm, sang trọng.',
20,1,8,0),
(N'ORIENT STAR OPEN HEART RE' ,22300000,10,'ORIENT STAR OPEN HEART RE-AV0001S00B.png',
N'Mẫu Orient Star Open Heart RE-AV0001S00B nổi bật với ô chân kính lộ tim phô diễn ra 1 phần hoạt động bộ máy cơ bên trong tạo nên vẻ độc đáo sang trọng với phần vỏ máy kim loại mạ vàng hồng.',
20,1,2,0),
(N'DOXA EXECUTIVE D221RSV' ,39470000,10,'DOXA EXECUTIVE D221RSV.png',
N'Đồng hồ Nữ DOXA EXECUTIVE D221RSV
 Ẩn bên dưới mặt kính Sapphire với nền mặt số được gia công tinh xảo đính các viên kim cương tạo nên phụ kiện thời trang sang trọng dành cho phái đẹp với phiên bản Doxa D221RSV..',
20,2,6,0),
(N'CASIO AQ-S810W-1BVDF' ,2203000,10,'CASIO AQ-S810W-1BVDF.png',
N'Mẫu đồng hồ Casio AQ-S810W-1A4VDF ấn tượng với công nghệ Tough Solar (Năng Lượng Ánh Sáng), vỏ máy được thiết kế tạo hình các góc cạnh phủ tông màu xám phối cùng bộ dây đeo bằng cao su đen tạo nên vẻ cá tính năng động.',
20,1,1,0),
(N'G-SHOCK GA-2000-1A2DR' ,4638000,10,'G-SHOCK GA-2000-1A2DR.png',
N'Mẫu G-Shock GA-2000-1A2DR phần vỏ viền ngoài tạo hình nền cọc số mang lại vẻ thể thao năng động cùng các ô số điện tử hiện thị chức năng lịch và đo thời gian.',
20,1,9,0),
(N'DOXA D201RSV' ,4638000,10,'DOXA D201RSV.png',
N'Mẫu Doxa D201RSV vẻ ngoài sang trọng với mẫu vạch số tạo hình mỏng mang lại sự tinh tế dành cho phái mạnh đầy nổi bật khi các chi tiết kim chỉ được phủ tông vàng hồng trẻ trung đầy cuốn hút.',
20,1,6,0),
(N'Orient FUB9B005W0' ,4070000,10 ,'Orient FUB9B005W0.png',
N'Đồng hồ dây da cao cấp dành cho nữ giới Orient FUB9B005W0 với kiểu dáng thời trang sang trọng ở mặt đồng hồ nền trắng có chữ số hạt, mặt kính Sapphire chống trầy, còn có dây da trắng tinh tế.',
20,2,2,0)


insert into product_image (product_image_name,product_id,is_deleted)
VALUES
('Casio World Time AE1200WHD_1.png',1,0),
('Casio World Time AE1200WHD_2.png',1,0),
('Casio World Time AE1200WHD_3.png',1,0),
('Orient ABC356_1.png',2,0),
('Orient ABC356_2.png',2,0),
('Orient ABC356_3.png',2,0),
('Citizen HM456_1.png',3,0),
('Citizen HM456_2.png',3,0),
('Citizen HM456_3.png',3,0),
('HAMILTON SYLVIA_1.png',4,0),
('HAMILTON SYLVIA_2.png',4,0),
('HAMILTON SYLVIA_3.png',4,0),
('ORIENT_1.png',5,0),
('ORIENT_2.png',5,0),
('ORIENT_3.png',5,0),
('CASIO MTP-1381D-1AVDF_1.png',6,0),
('CASIO MTP-1381D-1AVDF_2.png',6,0),
('CASIO MTP-1381D-1AVDF_3.png',6,0),
('ORIENT CABALLERO.png_1',7,0),
('ORIENT CABALLERO.png_2',7,0),
('ORIENT CABALLERO.png_3',7,0),
('G-SHOCK NAM_1.png',8,0),
('G-SHOCK NAM_2.png',8,0),
('G-SHOCK NAM_3.png',8,0),
('TISSOT TRADITION_1.png',9,0),
('TISSOT TRADITION_2.png',9,0),
('TISSOT TRADITION_3.png',9,0),
('ORIENT STAR OPEN HEART RE-AV0001S00B_1.png',10,0),
('ORIENT STAR OPEN HEART RE-AV0001S00B_2.png',10,0),
('ORIENT STAR OPEN HEART RE-AV0001S00B_3.png',10,0),
('DOXA EXECUTIVE D221RSV_1.png',11,0),
('DOXA EXECUTIVE D221RSV_2.png',11,0),
('DOXA EXECUTIVE D221RSV_3.png',11,0),
('CASIO AQ-S810W-1BVDF_1.png',12,0),
('G-SHOCK GA-2000-1A2DR_1.png',13,0),
('G-SHOCK GA-2000-1A2DR_2.png',13,0),
('G-SHOCK GA-2000-1A2DR_3.png',13,0),
('DOXA D201RSV_1.png',14,0),
('DOXA D201RSV_2.png',14,0),
('DOXA D201RSV_3.png',14,0),
('Orient FUB9B005W0_1.png',15,0),
('Orient FUB9B005W0_2.png',15,0),
('Orient FUB9B005W0_3.png',15,0)

insert into ware_house(amount,product_id,is_deleted)
values (100,1,'0'),
(100,2,'0'),
(100,3,'0'),
(100,4,'0'),
(100,5,'0'),
(100,6,'0'),
(100,7,'0'),
(100,8,'0'),
(100,9,'0'),
(100,10,'0'),
(100,11,'0'),
(100,12,'0'),
(100,13,'0'),
(100,14,'0'),
(100,15,'0')


-- PROCEDURE
-- 1
CREATE DEFINER=`admin`@`%` PROCEDURE `aaa_sss`(
	IN `userName` VARCHAR(50)
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
		WHERE 	((`userName` <> '' AND (a.username LIKE CONCAT('%',`userName`) 
		OR a.phone LIKE CONCAT('%',`userName`) 
		OR a.email LIKE CONCAT('%',`userName`))) OR `userName` = '')
		
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
	WHERE 	((`userName` <> '' AND (a.username LIKE CONCAT('%',`userName`) 
		OR a.phone LIKE CONCAT('%',`userName`) 
		OR a.email LIKE CONCAT('%',`userName`))) OR `userName` = '');

	DROP TEMPORARY TABLE IF EXISTS tbl_roles;
END ;;

-- 2

CREATE DEFINER=`admin`@`%` PROCEDURE `FillCalendar`(start_date DATE, end_date DATE)
BEGIN
    DECLARE crt_date DATE;
    SET crt_date = start_date;
    WHILE crt_date <= end_date DO
        INSERT IGNORE INTO calendar VALUES(crt_date);
        SET crt_date = ADDDATE(crt_date, INTERVAL 1 DAY);
    END WHILE;
END ;;


-- 3

CREATE DEFINER=`admin`@`%` PROCEDURE `sp_c_create_account`(
	IN `userName` VARCHAR(50),
	IN `passWord` TEXT,
	IN `Email` VARCHAR(100),
	IN `dateOfBirth` VARCHAR(50),
	IN `fullName` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci,
	IN `Phone` VARCHAR(20)
)
BEGIN
	DECLARE `accountId` INT(11) DEFAULT 0;
	DECLARE `cartId` INT(11) DEFAULT 0;
	DECLARE errno INT;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
    GET CURRENT DIAGNOSTICS CONDITION 1 errno = MYSQL_ERRNO;
--     SELECT errno AS MYSQL_ERROR;
    ROLLBACK;
    END;
	START TRANSACTION;
		INSERT INTO cart  (card_status) values(1);
		
		SET `cartId` = (SELECT c.id FROM cart c ORDER BY c.id DESC  LIMIT 1 );
	
		INSERT INTO account (username,password,email,date_of_birth,full_name,phone,cart_id) 
		values(`userName`,`passWord`,`Email`, `dateOfBirth`,`fullName`,`Phone`,`cartId`);
	
	
		SET `accountId` = (SELECT a.id  FROM account a WHERE a.username = `userName` AND a.email = `Email`);
	
		
		INSERT INTO roles_detail (account_id,role_id)values(`accountId`,2);
	COMMIT;
END ;;

-- 4

CREATE DEFINER=`admin`@`%` PROCEDURE `sp_c_create_import_export_warehouse`(
	IN `nameProduct` text, 
	IN `statusType` int,
	IN `importPirce` decimal(20),
	IN `totalPrice` decimal(20), 
	IN `note` text,
	IN `amount` int)
BEGIN
	DECLARE `productId` INT(11);
	DECLARE errno INT;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
    GET CURRENT DIAGNOSTICS CONDITION 1 errno = MYSQL_ERRNO;
--     SELECT errno AS MYSQL_ERROR;
    ROLLBACK;
    END;
	START TRANSACTION;
		IF (`statusType` = 1) THEN
			INSERT INTO product (product_name) values(`nameProduct`);
           		SET productId = (SELECT  p.id FROM product p ORDER BY p.id DESC LIMIT 1);
           		INSERT INTO ware_house (amount,product_id,import_price,note,total_price,status) VALUES(`amount`,`productId`,`importPirce`,`note`,totalPrice,`statusType`);
       	ELSE IF (`statusType` = 0) THEN
       			SELECT * FROM product p;
   		END IF;
   	END IF;
	COMMIT;
END ;;

-- 5
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_c_create_product`(
	IN `productName` VARCHAR(50),
	IN `price` DECIMAL(20,0),
	IN `discount` DECIMAL(20,0),
	IN `note` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
	IN `images` TEXT,
	IN `numberOfSale` INT(11),
	IN `category` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
	IN `brand` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci
)
BEGIN
	
	DECLARE `categoryCount` INT(11) DEFAULT 0;
	DECLARE `index` INT(11) DEFAULT 0;
	DECLARE `brandCount` INT(11) DEFAULT 0;
	DECLARE `categoryId` INT(11) DEFAULT 0;
	DECLARE `brandId` INT(11) DEFAULT 0;


	DROP TEMPORARY TABLE IF EXISTS tbl_category;
	CREATE TEMPORARY TABLE tbl_category(
		id INT(11)
	) ENGINE=MEMORY;

	SET `categoryCount` = JSON_LENGTH(`category`, '$.id');
   	SET `index` = 0;
   
   	WHILE `index` < `categoryCount` DO
        INSERT INTO tbl_category
		SELECT 	JSON_EXTRACT(`category`, CONCAT( '$.id')) AS id;
	
		SET `index` = `index` + 1;
   END WHILE;
  
  	DROP TEMPORARY TABLE IF EXISTS tbl_brand;
	CREATE TEMPORARY TABLE tbl_brand(
		id INT(11)
	) ENGINE=MEMORY;

	SET `brandCount` = JSON_LENGTH(`brand`, '$.id');
   	SET `index` = 0;
   
   	WHILE `index` < `brandCount` DO
        INSERT INTO tbl_brand
		SELECT 	JSON_EXTRACT(`brand`, CONCAT( '$.id')) AS id;
	
		SET `index` = `index` + 1;
   	END WHILE;
  	SET `categoryId` = (SELECT c.id FROM tbl_category c);
  	SET `brandId` = (SELECT b.id FROM tbl_brand b) ;
  
  	INSERT INTO product (product_name, price, discount, note, images, number_of_sale, category_id, brand_id)  values(`productName`,`price`,`discount`,`note`,`images`,`numberOfSale`,`categoryId`,`brandId`);
  
 	DROP TEMPORARY TABLE IF EXISTS tbl_category;
	DROP TEMPORARY TABLE IF EXISTS tbl_brand;

END ;;

-- 6
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_c_create_reivews`(
 IN `reviews` LONGTEXT
 )
BEGIN
	DECLARE `reviewsCount` INT(11) DEFAULT 0;
	DECLARE `index` INT(11) DEFAULT 0;

	DROP TEMPORARY TABLE IF EXISTS tbl_food_ids;
	CREATE TEMPORARY TABLE tbl_food_ids(
		comment VARCHAR(255),
		rate INT(11),
		product_id INT(11),
		account_id INT(11)
	) ENGINE=MEMORY;
   
   	SET `reviewsCount` = JSON_LENGTH(`reviews`, '$');
   	SET `index` = 0;
    WHILE `index` < `reviewsCount` DO
        INSERT INTO tbl_food_ids
		SELECT 	JSON_EXTRACT(`reviews`, CONCAT( '$[', `index`, '].comment')) AS comment,
				JSON_EXTRACT(`reviews`, CONCAT( '$[', `index`, '].rate')) AS rate,
				JSON_EXTRACT(`reviews`, CONCAT( '$[', `index`, '].product_id')) AS product_id,
				JSON_EXTRACT(`reviews`, CONCAT( '$[', `index`, '].account_id')) AS account_id;
	
		SET `index` = `index` + 1;
    END WHILE;
   
   INSERT INTO reviews (comment,rate,product_id,account_id) SELECT t.comment,t.rate,t.product_id,t.account_id FROM tbl_food_ids t;
END ;;

-- 7
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_g_list_order_by_account`(IN `orderDetaiId` INT(11),
	IN `orderId` INT(11),
	IN `accountId` INT(11),
	OUT `status_code` TINYINT(1),
	OUT `message_error` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci
    )
    READS SQL DATA
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
	BEGIN
		SET `status_code` = 1;
		GET DIAGNOSTICS CONDITION 1 `message_error` = MESSAGE_TEXT;
		SELECT `status_code`, `message_error`;
	END;

	SET `status_code` = 0;
	SET `message_error` = 'Success';
   
   DROP TEMPORARY TABLE IF EXISTS tbl_orders;
	CREATE TEMPORARY TABLE tbl_orders AS (
		SELECT 	o.id AS order_id,
				JSON_UNQUOTE(CONCAT('[',GROUP_CONCAT(
					JSON_OBJECT(
					'ma_don_hang', o.id ,
					'order_date',o.order_date,
					'delivery_date', o.delivery_date,
					'order_status',o.order_status 
		 			)SEPARATOR ','),']')) AS orders
		FROM 	orders o
		WHERE 	o.id = `orderId`
		GROUP BY o.id 
	);

 DROP TEMPORARY TABLE IF EXISTS tbl_info_account;
	CREATE TEMPORARY TABLE tbl_info_account AS (
		SELECT 	o.id AS order_id,
				JSON_UNQUOTE(CONCAT('[',GROUP_CONCAT(
					JSON_OBJECT(
					'full_name',a.full_name ,
					'phone',sd.phone ,
					'address',sd.address 
		 			)SEPARATOR ','),']')) AS info
		FROM 	account a
				LEFT JOIN orders o ON a.id = o.account_id 
				LEFT JOIN ship_detail sd ON o.ship_detail_id = sd.id 
		WHERE 	o.id = `orderId`
		GROUP BY o.id 
	);

 DROP TEMPORARY TABLE IF EXISTS tbl_product;
	CREATE TEMPORARY TABLE tbl_product AS (
		SELECT 	o.id AS order_id,
				JSON_UNQUOTE(CONCAT('[',GROUP_CONCAT(
					JSON_OBJECT(
					'product_name',p.product_name,
					'price',p.price,
					'category_name',c.category_name ,
					'amount', od.amount 
		 			)SEPARATOR ','),']')) AS product
		FROM 	product p 
				LEFT JOIN categories c ON p.category_id  = c.id 
				LEFT JOIN oders_detail od ON p.id = od.product_id 
				LEFT JOIN orders o ON od.order_id = o.id 
		WHERE 	o.id = `orderId`
		GROUP BY o.id 
	);


	SELECT 	o.id ,
			tod.orders,
			tia.info,
			tp.product,
			o.total_money
	FROM 	orders o  
			LEFT JOIN tbl_orders tod ON o.id =tod.order_id
			LEFT JOIN tbl_info_account tia ON o.id =tia.order_id
			LEFT JOIN tbl_product tp ON o.id =tp.order_id
	WHERE 	o.id =`orderId`;

 DROP TEMPORARY TABLE IF EXISTS tbl_orders;
 DROP TEMPORARY TABLE IF EXISTS tbl_info_account;
 DROP TEMPORARY TABLE IF EXISTS tbl_product;
END ;;

-- 8
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_g_list_user_roles`(
	IN `userName` VARCHAR(50)
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
		WHERE 	((`userName` <> '' AND (a.username LIKE CONCAT('%',`userName`) 
		OR a.phone LIKE CONCAT('%',`userName`) 
		OR a.email LIKE CONCAT('%',`userName`))) OR `userName` = '')
		
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
	WHERE 	((`userName` <> '' AND (a.username LIKE CONCAT('%',`userName`) 
		OR a.phone LIKE CONCAT('%',`userName`) 
		OR a.email LIKE CONCAT('%',`userName`))) OR `userName` = '');

	DROP TEMPORARY TABLE IF EXISTS tbl_roles;
END ;;

-- 9

CREATE DEFINER=`admin`@`%` PROCEDURE `sp_g_list_user_statistical`(
IN `accountId` INT(11),
	OUT `status_code` TINYINT(1),
	OUT `message_error` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci
	)
    READS SQL DATA
store_procedure:BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
	BEGIN
		SET `status_code` = 1;
		GET DIAGNOSTICS CONDITION 1 `message_error` = MESSAGE_TEXT;
		SELECT `status_code`, `message_error`;
	END;

	SET `status_code` = 0;
	SET `message_error` = 'Success';

	IF (SELECT IFNULL(COUNT(*), 0) FROM account a WHERE a.id = `accountId`) = 0  THEN
		SET `status_code` = 2;
   		SET `message_error` = 'Id người dùng không tồn tại';
   		LEAVE store_procedure;
   	END IF;
   
   	SELECT 	a.id,
   			a.username ,
 			c.id AS category_id,
 			c.card_status,
 			a.updated_at,
 			a.created_at
   	FROM 	account a 
  			LEFT JOIN cart c ON a.cart_id = c.id 
	WHERE 	a.id = `accountId`;
END ;;

-- 10
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_u_assign_role_for_user`(
	IN `accountId` INT(11),
	IN `roleId` INT(11),
	IN `type` INT(11)
)
store_procedure:
BEGIN
	START TRANSACTION;
		IF(SELECT IFNULL(COUNT(*), 0) FROM account a WHERE a.id = `accountId` ) = 0 THEN 
			LEAVE store_procedure;
		ELSEIF (SELECT IFNULL(COUNT(*), 0) FROM roles r  WHERE r.id = `roleId` ) = 0 THEN
			LEAVE store_procedure;
		ELSEIF (SELECT IFNULL(COUNT(*), 0) FROM roles_detail rd WHERE rd.role_id  = `roleId` AND rd .account_id = `accountId`) > 0  THEN
			LEAVE store_procedure;
		ELSE
			IF(`type` = 1) THEN
				INSERT INTO roles_detail (account_id,role_id) VALUES(`accountId`,`roleId`);
-- 			ELSEIF(`type` = 0) THEN
-- 				UPDATE roles_detail SET roles_detail.account_id  = `accountId`, roles_detail.role_id = `roleId` WHERE roles_detail.account_id  = `accountId` AND roles_detail.role_id = `roleId`;
			END IF;	
		END IF;
	COMMIT;
END ;;


-- 12
CREATE DEFINER=`admin`@`%` PROCEDURE `thongketien`(
	IN `fromDateString` VARCHAR(100),
	IN `toDateString` VARCHAR(100),
	IN `reportType` INT(11)
	)
    READS SQL DATA
BEGIN
	
	DECLARE `fromDate` DATETIME DEFAULT NULL;
	DECLARE `toDate` DATETIME DEFAULT NULL;

	SET `fromDate` = CASE WHEN `fromDateString` IS NOT NULL AND `fromDateString` <> '' THEN DATE_FORMAT(`fromDateString`, '%Y-%m-%d') ELSE NULL END;
	SET `toDate` = CASE WHEN `toDateString` IS NOT NULL AND `toDateString` <> '' THEN DATE_FORMAT(`toDateString`, '%Y-%m-%d') ELSE NULL END;
	
DROP TEMPORARY TABLE IF EXISTS tbl_day_report_time;
    CREATE TEMPORARY TABLE tbl_day_report_time AS(
					    SELECT 	drt.report_day,
								CASE 
										WHEN `reportType` = 2 THEN
												 WEEKDAY(drt.report_day)
										WHEN `reportType` = 3 THEN
												 DATE_FORMAT(drt.report_day,'%d')	
										WHEN `reportType` IN (4,5) THEN
												 DATE_FORMAT(drt.report_day,'%m')
										WHEN `reportType` IN (6,8) THEN
												 DATE_FORMAT(drt.report_day,'%Y')
								END AS time_formats 
						FROM 	calendar drt 
						WHERE 	((`fromDate` IS NOT NULL AND DATE_FORMAT(report_day , '%Y-%m-%d') >= `fromDate`) OR `fromDate` IS NULL)
							AND ((`toDate` IS NOT NULL AND DATE_FORMAT(report_day , '%Y-%m-%d') <= `toDate`) OR `toDate` IS NULL)
						GROUP BY CASE 
										WHEN `reportType` IN (2,3) THEN
											DATE_FORMAT(drt.report_day,'%d')
										WHEN `reportType` IN (4,5) THEN
											DATE_FORMAT(drt.report_day,'%m')
										WHEN `reportType` IN (6,8) THEN
											DATE_FORMAT(drt.report_day,'%Y')
										ELSE
											DATE_FORMAT(drt.report_day,'%Y-%m-%d')
							 END						 
    );

 DROP TEMPORARY TABLE IF EXISTS tbl_revenue;
    CREATE TEMPORARY TABLE tbl_revenue AS(
			SELECT 	  	SUM( amount * price) AS total_price,
					CASE 
						WHEN `reportType` = 2 THEN
								 WEEKDAY(created_at)
						WHEN `reportType` = 3 THEN
								 DATE_FORMAT(created_at,'%d')	
						WHEN `reportType` IN (4,5) THEN
								 DATE_FORMAT(created_at,'%m')
						WHEN `reportType` IN (6,8) THEN
								 DATE_FORMAT(created_at,'%Y')
					END AS report_time
			FROM 	oders_detail od 
			WHERE 	((`fromDate` IS NOT NULL AND DATE_FORMAT(created_at , '%Y-%m-%d') >= `fromDate`) OR `fromDate` IS NULL)
					AND ((`toDate` IS NOT NULL AND DATE_FORMAT(created_at , '%Y-%m-%d') <= `toDate`) OR `toDate` IS NULL)
			GROUP BY CASE 
						WHEN `reportType` IN (2,3) THEN
							DATE_FORMAT(created_at,'%d')
						WHEN `reportType` IN (4,5) THEN
							DATE_FORMAT(created_at,'%m')
						WHEN `reportType` IN (6,8) THEN
							DATE_FORMAT(created_at,'%Y')
						ELSE
							DATE_FORMAT(created_at,'%Y-%m-%d')
			 END
					);	

	SELECT 	tdrt.report_day,
			IFNULL(tr.total_price,0) AS total_price
	FROM 	tbl_day_report_time tdrt
			LEFT JOIN tbl_revenue tr ON tdrt.time_formats = tr.report_time
	GROUP BY tdrt.report_day
	ORDER BY tdrt.report_day ASC;
END ;;

select * from sale_detail sd 
insert into sale(sale_date_start,sale_time_start,sale_date_end,sale_time_end,sale_name)
values ('2022/12/08','00:00:00','2022/12/08','23:59:59',N'Flashsale'),

insert into sale(sale_date_start,sale_time_start,sale_date_end,sale_time_end)
values ('2022/12/04','01:00:00','2022/12/04','23:59:59')

update sale set sale_time_start = '05:12:00' where id=1

update sale set sale_time_end = '05:54:00' where id=1

insert into sale_detail(sale_id,product_id,discount_old,discount_sale)
values (2,1,0,10),
(2,2,0,15),
(2,3,0,20),
(2,4,0,10),
(2,5,0,15),
(2,6,0,20),
(2,7,0,10),
(2,8,0,20)

select * from orders o 

select * from ship_detail sd 






