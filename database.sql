DROP database IF EXISTS TEST1;

create DATABASE TEST1;
use TEST1;
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

alter table product add constraint FK_product_warehouse foreign key (warehouse_id) references ware_house(id);


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
    province_id INT(11),
    province TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    district_id INT(11),
    district TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    ward_id INT(11),
    ward TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    address_more TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
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
    payment_method TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    payment_status TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    ship_method TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
    ship_method_id INT(11),
    -- Phí ship
    delivery_charges INT(11),
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
	sale_name TEXT CHARACTER SET utf8 COLLATE utf8_general_ci,
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

CREATE TABLE `calendar` (
  `report_day` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

insert into cart (is_deleted)
values (0),
(0),
(0),
(0),
(0),
(0),
(0);

INSERT INTO  account (username,password,email,date_of_birth,full_name,phone,gender,cart_id,is_deleted) values
('anhhv','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','hoangvietanh02112002@gmail.com','2002-11-02','Hoàng Việt Anh','0346135361',N'Nam',1,0),
('cuongdm','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','damdinhcuong@gmail.com','2002-07-05','Đàm Đình Cường','0346135362',N'Nam',2,0),
('trietnpm','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','nguyenphamminhtriet@gmail.com','1999-08-22','Nguyễn Phạm Minh Triết','0346135363',N'Nam',3,0),
('longpp','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','phamphilong@gmail.com','2002-07-05','Phạm Phi Long','0346135364',N'Nam',4,0),
('hainh','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','nguyenhoanghai0507@gmail.com','2002-07-05','Nguyễn Hoàng Hải','0346135365',N'Nam',5,0),
('thuatlh','$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO','lehoangthuat@gmail.com','2002-11-02','Lê Hoàng Thuật','0346135366',N'Nam',6,0),
('admin','$2a$10$0ijqrFYFWglp4DLQ5dJu9.2SWDhchnoW.09gkiW7iUBtX7Vu4xKfe','admin@gmail.com','1999-11-02','Admin','0345837123',N'Nam',7,0);

INSERT INTO roles (role_name) values('OWNER'),('CUSTOMER'),('CUSTOMER_VIP'),('EMPLOYEE');

INSERT INTO roles_detail (account_id,role_id,is_deleted) values (1,2,0),(2,2,0),(3,2,0),(4,2,0),(5,2,0),(6,2,0),(7,1,0),(7,2,0),(7,3,0),(7,4,0);

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
(N'G-sock',0);

insert into categories(category_name,is_deleted)
values 
(N'Nam',0),
(N'Nữ',0);


insert into product(product_name,price,discount,images,note,number_of_sale,category_id,brand_id,is_deleted)
values (N'CASIO AE1200WHD',2000000,0,'Casio World Time AE1200WHD.png',
N'Đồng hồ Casio AE1200WHD có mặt đồng hồ vuông to với phong cách thể thao, mặt số điện tử với những tính năng hiện đại tiện dụng, kết hợp với dây đeo bằng kim loại đem lại vẻ mạnh mẽ cá tính dành cho phái nam.',
20,1,1,0),
(N'ORIENT AHE356',3000000,0,'Orient ABC356.png',
N'Đồng hồ nam Orient, mặt số điện tử với những tính năng hiện đại tiện dụng, kết hợp với dây đeo bằng kim loại đem lại vẻ mạnh mẽ cá tính dành cho phái nam.',
20,1,2,0),
(N'CITIZEN HM456',4000000,0,'Citizen HM456.png',
N'Đồng hồ nữ Citizen có mặt đồng hồ vuông to với phong cách thể thao, mặt số điện tử với những tính năng hiện đại tiện dụng, kết hợp với dây đeo bằng kim loại đem lại vẻ mạnh mẽ cá tính dành cho phái nam.',
20,2,3,0),
(N'MOVADO SYLVIA',2399000,0,'HAMILTON SYLVIA.png',
N'Đồng hồ nữ Curnon Hamilton Sylvia phong cách vintage, nhẹ nhàng nhưng vẫn rất cuốn hút, thanh lịch; Dây kim loại dạng lưới, Chống nước 3ATM, Kính Sapphire chống xước...',
20,2,4,0),
(N'CASIO MTP-1381D-1AVDF',1710000,0,'CASIO MTP-1381D-1AVDF.png',
N'Đồng hồ Casio MTP-1381D-1AVDF có vỏ và dây đeo kim loại phủ bạc sáng bóng, nền số màu đen mạnh mẽ với kim chỉ và vạch số được phủ phản quang nổi bật, lịch thứ vị trí 12h và lịch ngày vị trí 6h.',
20,1,1,0),
(N'CASIO MTP-B305D-1EVDF',3058000,0,'CASIO MTP-B305D-1EVDF.png',
N'Mẫu Casio MTP-B305D-1EVDF phiên bản các chức năng lịch được phân chia ra các ô số riêng biệt tạo nên kiểu dáng độc đáo đồng hồ 6 kim hiện thị trên nền mặt số với kích thước 41mm..',
20,1,1,0),
(N'CASIO A100WEG-9ADF',2591000,0,'CASIO A100WEG-9ADF.png',
N'Mẫu Casio A100WEG-9ADF mặt số điện tử với nhiều chức năng mang lại nhiều tiện ích cho người dùng, thiết kế thời trang sang trọng với bộ dây đeo bằng kim loại màu vàng nhạt.',
20,1,1,0),
(N'CASIO MTP-B300M-2AVDF',3161000,0,'CASIO A100WEG-9ADF.png',
N'Mẫu Casio MTP-B300M-2AVDF các tính năng lịch được sắp xếp thành 3 ô số riêng biệt tạo nên kiểu dáng độc đáo của đồng hồ 6 kim hiện thị trên nền mặt số kích thước 40mm.',
20,1,1,0),
(N'CASIO MTP-VD02D-7EUDF',1322000,0,'CASIO MTP-VD02D-7EUDF.png',
N'Mẫu Casio MTP-B300M-2AVDF các tính năng lịch được sắp xếp thành 3 ô số riêng biệt tạo nên kiểu dáng độc đáo của đồng hồ 6 kim hiện thị trên nền mặt số kích thước 40mm.',
20,1,1,0),
(N'CASIO W-218H-4BVDF',740000 ,0,'CASIO W-218H-4BVDF.png',
N'Mẫu Casio W-218H-4BVDF mặt số kiểu dáng vuông điện tử hiện thị đa chức năng, thiết kế tổng thể chất liệu nhựa cùng với mẫu dây đeo được phối tone màu đỏ cá tính thời trang dành các bạn trẻ năng động.',
20,1,1,0),
(N'CASIO AQ-S810W-1BVDF' ,2203000,0,'CASIO AQ-S810W-1BVDF.png',
N'Mẫu đồng hồ Casio AQ-S810W-1A4VDF ấn tượng với công nghệ Tough Solar (Năng Lượng Ánh Sáng), vỏ máy được thiết kế tạo hình các góc cạnh phủ tông màu xám phối cùng bộ dây đeo bằng cao su đen tạo nên vẻ cá tính năng động.',
20,1,1,0),
(N'CASIO MW-610H-2AVDF' ,1347000,0,'CASIO MW-610H-2AVDF.png',
N'Mẫu Casio MW-610H-2AVDF phiên bản dây vỏ nhựa phối tone màu đen nam tính, nổi bật khả năng chịu nước lên đến 10atm.',
20,1,1,0),
(N'CASIO MTD-1060D-1A3VDF' ,3109000 ,0,'CASIO MTD-1060D-1A3VDF.png',
N'Mẫu Casio MTD-1060D-1A3VDF mang trên mình một vẻ ngoài kiểu dáng đồng hồ 6 kim thể thao nam tính với các chi tiết vạch số tạo hình dày dặn, điểm nhấn nổi bật với khả năng chịu nước lên đến 10ATM.',
20,1,1,0),
(N'CASIO MTP-VD02L-7EUDF' ,1086000 ,0,'CASIO MTP-VD02L-7EUDF.png',
N'Mẫu Casio MTP-VD02L-7EUDF dây da nâu với phiên bản da trơn phong cách thời trang, các cọc vạch số cùng kim chỉ được tạo hình dày dặn phủ dạ quang nổi bật trong điều kiện thiếu sáng.',
20,1,1,0),
(N'CASIO MTP-B305D-1EVDF' ,3058000 ,0,'CASIO MTP-B305D-1EVDF.png',
N'Mẫu Casio MTD-1060D-1A3VDF mang trên mình một vẻ ngoài kiểu dáng đồng hồ 6 kim thể thao nam tính với các chi tiết vạch số tạo hình dày dặn, điểm nhấn nổi bật với khả năng chịu nước lên đến 10ATM..',
20,1,1,0),
(N'CASIO A171WEMB-1ADF' ,2980000  ,0,'CASIO A171WEMB-1ADF.png',
N'Mẫu Casio A171WEMB-1ADF mặt số điện tử với nhiều chức năng mang lại nhiều tiện ích cho người dùng, tạo nên phong cách thời trang cá tính phối với mẫu dây đeo phiên bản lưới đen.',
20,1,1,0),
(N'CASIO AE-1400WHD-1AVDF' ,1529000  ,0,'CASIO AE-1400WHD-1AVDF.png',
N'Mẫu Casio AE-1400WHD-1AVDF thiết kế theo phong cách trẻ trung năng với nền mặt số điện tử tích hợp đa chức năng, vẻ ngoài thể thao nhưng cũng không kém phần lịch lãm với mẫu dây đeo kim loại đầy nam tính.',
20,1,1,0),
(N'CASIO MTP-E600M-9BDF' ,3109000 ,0,'CASIO MTP-E600M-9BDF.png',
N'Mẫu Casio MTD-1060D-1A3VDF mang trên mình một vẻ ngoài kiểu dáng đồng hồ 6 kim thể thao nam tính với các chi tiết vạch số tạo hình dày dặn, điểm nhấn nổi bật với khả năng chịu nước lên đến 10ATM..',
20,1,1,0),
(N'CASIO MQ-1000D-1A2DF' ,1813000  ,0,'CASIO MQ-1000D-1A2DF.png',
N'Mẫu Casio MQ-1000D-1A2DF phiên bản đính viên kim cương tạo nên vẻ thời trang sang trọng trên nền mặt số với kích thước trung tính 34mm.',
20,1,1,0),
/*nu*/
(N'CASIO A168WG-9WDF' ,1813000  ,0,'CASIO A168WG-9WDF.png',
N'Đồng hồ Casio A168WG-9WDF với hình dáng truyền thống của hãng, phù hợp cho cả nam lẫn nữ, tông màu vàng chủ đạo từng chi tiết vỏ, mặt số và dây đeo tạo nên thời trang sang trọng, quý phái và thanh lịch',
20,2,1,0),
(N'CASIO LTP-1183A-7ADF' ,1140000   ,0,'CASIO LTP-1183A-7ADF.png',
N'Đồng hồ nữ Casio LTP-1183A-7ADF thanh lịch tinh tế, mặt đồng hồ có nền trắng cùng chữ số lớn, kiểu dáng 3 kim đơn giản cùng 1 lịch ngày.',
20,2,1,0),
(N'CASIO B640WCG-5DF' ,2177000,0,'CASIO B640WCG-5DF.png',
N'Mẫu Casio B640WCG-5DF phiên bản dây đeo cùng vỏ máy được phối cùng tone màu vàng hồng thời trang, kết hợp cùng nền mặt số điện tử hiện thị đa chức năng.',
20,2,1,0),
(N'CASIO SHE-4055PGL-7BUDF' ,4319000 ,0,'CASIO SHE-4055PGL-7BUDF.png',
N'Mẫu Casio SHE-4055PGL-7BUDF phiên bản đính pha lê tương ứng với các múi giờ trên mặt số trắng size 30mm tạo nên vẻ đẹp sang trọng trẻ trung khi kết hợp cùng vỏ máy vàng hồng.',
20,2,1,0),
(N'CASIO A159WGED-1DF' ,2721000  ,0,'CASIO A159WGED-1DF.png',
N'Mẫu đồng hồ Casio A159WGED-1DF vẻ ngoài giản dị nhưng không kém phần cuốn hút cùng thiết kế tinh xảo được đính viên kim cương tạo nên vẻ thời trang quyến rũ cho phái đẹp trên nền mặt số vuông điện tử.',
20,2,1,0),
(N'CASIO LW-200-2BVDF' ,2177000   ,0,'CASIO LW-200-2BVDF.png',
N'Mẫu đồng hồ Casio LTP-V300G-9AUDF với phong cách thời thượng, mặt đồng hồ tròn nhỏ nữ tính kẻm ô phụ với 3 chức năng khác nhau, đồng hồ  với tổng thể bằng kim loại mạ vàng đem lại cho phái nữ vẻ sang trọng.',
20,2,1,0),
(N'CASIO LTP-1274G-7ADF' ,1426000   ,0,'CASIO LTP-1274G-7ADF.png',
N'Mẫu đồng hồ nữ Casio LW-200-2BVDF kiểu dáng theo phong cách thể thao, mặt số điện tử với những tính năng hiện đại tiện dụng, kết hợp với dây đeo cao su tạo vẻ thời trang cá tính cho các bạn nữ..',
20,2,1,0),
(N'CASIO LTP-V005G-7AUDF' ,1192000,0,'CASIO LTP-V005G-7AUDF.png',
N'Đồng hồ nữ cao cấp Casio LTP-V005G-7AUDF với mặt nền trắng thanh lịch, cùng thiết kế tổng thể phủ vàng sang trọng cuốn hút mọi ánh nhìn, kiểu dáng 3 kim tinh tế.',
20,2,1,0),
(N'CASIO W-218HC-4A2VDF' ,803000 ,0,'CASIO W-218HC-4A2VDF.png',
N'Mẫu Casio W-218HC-4A2VDF phiên bản mới mặt số vuông điện tử hiện thị đa chức năng giúp người đeo dễ quan sát, thiết kế dây vỏ nhựa tạo nên vẻ năng động.',
20,2,1,0),
(N'CASIO LTP-VT02BL-1AUDF' ,1322000  ,0,'CASIO LTP-VT02BL-1AUDF.png',
N'Mẫu Casio LTP-VT02BL-1AUDF dây da nâu phiên bản da trơn trẻ trung cùng với lối thiết kế đơn giản chức năng 3 kim trên nền mặt số kích thước 30mm.',
20,2,1,0),
/*Orient*/
(N'ORIENT FGW0100AW0' ,4160000,0,'ORIENT FGW0100AW0.png',
N'Đồng hồ dây da Orient FGW0100AW0 dành cho nam giới với mặt đồng hồ nền trắng 2 tầng, điểm nhấn nổi bật với phiên bản mặt kính Sapphire size 38mm',
20,1,2,0),
(N'ORIENT CABALLERO FAG00004D0' ,8050000,0,'ORIENT CABALLERO FAG00004D0.png',
N'Mẫu Orient FAG00004D0 mang lại cho phái mạnh một phong cách lịch lãm nổi bật trên nền mặt số xanh tông màu trẻ trung với ô chân kính phô ra 1 phần trải nghiệm của bộ máy cơ với kiểu thiết kế độc đáo đầy nam tính.',
20,1,2,0),
(N'ORIENT MAKO FAA02002D9' ,8600000,0,'ORIENT MAKO FAA02002D9.png',
N'Mẫu Orient Mako 2 FAA02002D9 án tượng phần thiết kế vỏ viền ngoài tạo hình viền khía mang lại vẻ mạnh mẽ, mặt số được phối tông màu xanh mang đến một phong cách thời trang cho các phái nam.',
20,1,2,0),
(N'ORIENT RA-AC0H01L10B' ,7690000,0,'ORIENT RA-AC0H01L10B.png',
N'Mẫu Orient RA-AC0H01L10B mặt số xanh size 43mm với các chi tiết kim chỉ cùng nền cọc số học trò tạo hình dày dặn nổi bật sự trẻ trung năng động.',
20,1,2,0),
(N'ORIENT RE-AV0006Y00B' ,24280000,0,'ORIENT RE-AV0006Y00B.png',
N'Mẫu Orient RE-AV0006Y00B phiên bản lịch lãm với bộ dây da được tạo hình vân cá sấu, điểm nhấn nổi bật máy cơ lộ tim độc đáo trên mặt số size 42mm.',
20,1,2,0),
(N'ORIENT RA-AC0F12S10B' ,7480000,0,'ORIENT RA-AC0F12S10B.png',
N'Mẫu Orient RA-AC0F12S10B phiên bản da dây nâu tạo hình vân lịch lãm, chi tiết kim chỉ cùng nền cọc số mạ bạc trên nền mặt số trắng kích thước 41mm.',
20,1,2,0),
(N'ORIENT FAB00004D9' ,4890000,0,'ORIENT FAB00004D9.png',
N'Mẫu đồng hồ Orient 3 sao automatic FAB00004D9 phiên bản mạ vàng sang trọng trên chi tiết dây vỏ đồng hồ, các cọc vạch số tạo hình mỏng trẻ trung trên nền mặt số xanh size 38mm.',
20,1,2,0),
(N'ORIENT RA-AC0J03L10B' ,8230000 ,0,'ORIENT RA-AC0J03L10B.png',
N'Mẫu Orient RA-AC0J03L10B chi tiết kim chỉ cùng nền cọc số ma lã tạo hình mỏng cách tân trên nền mặt số size 42mm phối tone màu xanh thời trang.',
20,1,2,0),
(N'ORIENT BAMBINO RA-AC0005S10B' ,8230000 ,0,'ORIENT BAMBINO RA-AC0005S10B.png',
N'Mẫu Orient RA-AC0005S10B phiên bản kim chỉ xanh thời trang hiện thị trên nền mặt số trắng size 41mm, thiết kế đơn giản 3 kim cùng với cọc vạch số tạo hình mỏng trẻ trung',
20,1,2,0),
(N'ORIENT RA-AC0F10S10B' ,7850000  ,0,'ORIENT RA-AC0F10S10B.png',
N'Mẫu Orient RA-AC0F10S10B phiên bản mạ bạc chi tiết kim chỉ cùng nền cọc số trên nền mặt số kích thước 41mm, thiết kế đơn giản trẻ trung chức năng 3 kim 1 lịch.',
20,1,2,0),
/*CIyizen*/
(N'CITIZEN NH8350-83A',5785000,0,'CITIZEN NH8350-83A.png',
N'Đồng hồ nam Citizen NH8350-83A với kiểu dáng trẻ trung, vỏ máy bằng thép không gỉ tạo vẻ chắc chắn nam tính, kim chỉ vạch số được làm mỏng tinh tế thời trang, kết hợp cùng dây đeo kim loại với phong cách mạnh mẻ..',
20,1,3,0),
(N'CITIZEN BM7370-11A' ,6950000,0,'CITIZEN BM7370-11A.png',
N'Mẫu Citizen BM7370-11A mang lại vẻ tinh tế lịch lãm cho phái mạnh với kiểu thiết kế vạch số mỏng trẻ trung với nền mặt số tông trắng phối cùng mẫu dây da trơn hợp thời trang.',
20,1,3,0),
(N'CITIZEN AW1211-12A' ,4785000 ,0,'CITIZEN AW1211-12A.png',
N'Mẫu Citizen AW1211-12A kiểu dáng đơn giản 3 kim trên nền mặt trắng size 42mm, nổi bật với phiên bản trang bị công nghệ Eco-Drive (Năng Lượng Ánh Sáng).',
20,1,3,0),
(N'CITIZEN CA4458-88E' ,9860000  ,0,'CITIZEN CA4458-88E.png',
N'Mẫu Citizen CA4458-88E được thiết kế 3 núm vặn điều chỉnh các tính năng Chronograph (đo thời gian) hiện thị trên nền mặt số đen với kích thước lớn 43mm',
20,1,3,0),
(N'CITIZEN NH8360-80E' ,5785000,0,'CITIZEN NH8360-80E.png',
N'Đồng hồ nam Citizen NH8360-80E thiết kế từ máy cơ cho đến chữ số la mã theo phong cách cổ điển, với nền mặt số màu đen nam tính, vỏ máy cùng dây đeo kim loại màu bạc mạng lại sự sang trọng lịch lãm',
20,1,3,0),
(N'CITIZEN BE9170-13H' ,3400000,0,'CITIZEN BE9170-13H.png',
N'Mẫu Citizen BE9170-13H dây da nâu tạo hình vân cá sấu phong cách lịch lãm không kém cạnh trẻ trung với cọc vạch số thiết kế mỏng trên nền mặt số size 39mm.',
20,1,3,0),
(N'CITIZEN BI5000-87E' ,3285000 ,0,'CITIZEN BI5000-87E.png',
N'Mẫu Citizen BI5000-87E nam tính và lịch lãm là yếu tố tạo nên khí chất đàn ông với thiết kế các chi tiết vạch số tạo nét dày dặn mạ bạc chứa đựng vẻ sang trọng đầy cuốn hút.',
20,1,3,0),
(N'CITIZEN C7 NH8390-20L' ,7577000 ,0,'CITIZEN C7 NH8390-20L.png',
N'Mẫu Citizen C7 NH8390-20L phiên bản dây da tạo hình vân lịch lãm không kém cạnh trẻ trung kết hợp cùng nền mặt số xanh size 40mm với họa tiết trải tia nhẹ.',
20,1,3,0),
(N'CITIZEN NJ0150-81E' ,8180000 ,0,'CITIZEN NJ0150-81E.png',
N'Mẫu Citizen NJ0150-81E phiên bản mặt kính chất liệu kính Sapphire với kích thước nam tính 40mm, kết hợp cùng mẫu dây đeo kim loại mạ bạc phong cách thời trang sang trọng.',
20,1,3,0),
(N'CITIZEN NH8350-83E' ,5520000  ,0,'CITIZEN NH8350-83E.png',
N'Đồng hồ nam Citizen NH8350-83E với kiểu dáng đơn giản thời trang, vỏ máy bằng thép không gỉ tạo vẻ chắc chắn nam tính, ô lịch ngày và lịch thứ ở vị trị 3 giờ dễ quan sát, phối cùng dây đeo kim loại với phong cách mạnh mẻ',
20,1,3,0),
/*Movado*/
(N'MOVADO 0606916' ,4500000,0,'MOVADO 0606916.png',
N'Đồng hồ nam Movado 0606916 có mặt số tròn truyền thống với viền mỏng tinh tế, kim chỉ và logo dấu chấm đặc trưng được phủ vàng nổi bật trên nền số màu đen mạnh mẽ, dây đeo demi đem đến nét thời trang sang trọng.',
20,1,4,0),
(N'MOVADO 0606114' ,4800000,0,'MOVADO 0606114.png',
N'Mẫu Movado 0606114 mặt đồng hồ kích thước to tròn với trải nghiệm bô máy cơ dành cho nam, thiết kế theo phong cách giản dị chức năng lịch ngày sắp ở vị trí múi 6 giờ tinh tế trên nền mặt số tone màu đen nam tính',
20,1,4,0),
(N'MOVADO 0606115' ,5500000,0,'MOVADO 0606115.png',
N'Đồng hồ Movado 0606115 với thiết kế mặt số trơn màu đen, 2 kim chỉ được mạ bạc, logo hình tượng mặt trời vị trí 12h nổi bật trên nền mặt số, niềng và dây đeo kim loại bằng thép không gỉ.',
20,1,4,0),
/*Fossil*/
(N'FOSSIL HARRY POTTER LIMITED EDITION LE1159',5550000,0,'FOSSIL HARRY POTTER LIMITED EDITION LE1159.png',
N'Mẫu Fossil LE1159 phiên bản giới hạn dành tặng các fan chân chính nhà Hufflepuff với mẫu đeo chất liệu bằng dây vải thời trang, tông vàng đậm chất Hogwarts, kết hợp cùng vỏ máy kim loại mạ tone đen đầy tinh tế.',
20,1,5,0),
(N'FOSSIL FS5306' ,3790000,0,'FOSSIL FS5306.png',
N'Lối thiết kế đồng hồ kiểu dáng mỏng của sự tinh tế dành cho phái nam với mẫu Fossil FS5306, kim chỉ giây được phối tông màu xanh thể hiện sự nổi bật trên nền mặt số trắng kem là 2 yếu tố tạo nên vẻ thời trang trẻ trung.',
20,1,5,0),
(N'FOSSIL FS5946' ,3940000,0,'FOSSIL FS5946.png',
N'Mẫu Fossil FS5946 phiên bản dây da trơn tone màu nâu sẫm, chi tiết kim chỉ cùng các cọc chấm tròn được tạo hình dày dặn phủ dạ quang nổi bật trên nền mặt số xanh tone màu thời trang với kích thước lớn 42mm.',
20,1,5,0),
(N'FOSSIL FS5473' ,3740000,0,'FOSSIL FS5473.png',
N'Mẫu Fossil FS5473 kiểu dáng đơn giản 3 kim trên nền mặt xanh size 42mm tone màu thời trang phối cùng mẫu dây da trơn lịch lãm cho phái mạnh.',
20,1,5,0),
(N'FOSSIL FS5963' ,4440000,0,'FOSSIL FS5963.png',
N'Mẫu Fossil FS5963 thiết kế 3 núm vặn điều chỉnh các tính năng Chronograp (đo thời gian) tạo nên kiểu dáng độc đáo đồng hồ 6 kim hiện thị trên nền mặt số xanh kích thước lớn 44mm..',
20,1,5,0),
(N'FOSSIL FS5668' ,3650000,0,'FOSSIL FS5668.png',
N'Mẫu Fossil FS5668 phiên bản đồng hồ lặn nổi bật với khả năng chịu nước lên đến 10atm, các chi tiết kim chỉ cùng cọc vạch số được phủ dạ quang và tạo hình dày dặn dễ nhìn trong điều kiện thiếu sáng.',
20,1,5,0),
(N'FOSSIL FS5948' ,4510000,0,'FOSSIL FS5948.png',
N'Mẫu Fossil FS5948 chi tiết kim chỉ cùng các cọc chấm tròn được tạo hình dày dặn phủ dạ quang nổi bật trên nền mặt số kích thước lớn nam tính 42mm.',
20,1,5,0),
(N'FOSSIL FS5905' ,4710000,0,'FOSSIL FS5905.png',
N'Mẫu Fossil FS5905 các tính năng lịch ngày lịch thứ cùng chức năng Moon phase (lịch trăng) được thiết kế các ô số riêng biệt tạo nên vẻ độc đáo trên nền mặt số kích thước lớn 42mm.',
20,1,5,0),
(N'FOSSIL FS5843' ,2800000,0,'FOSSIL FS5843.png',
N'Mẫu Fossil FS5843 phiên bản mặt số vuông điện tử với phong cách hoài cổ, mặt số vuông lớn kích thước 40mm x 22mm kết hợp phần dây vỏ phối tone màu vàng nhạt thời trang.',
20,1,5,0),
(N'FOSSIL FS5952' ,4510000,0,'FOSSIL FS5952.png',
N'Mẫu Fossil FS5952 chi tiết kim chỉ cùng các cọc chấm tròn được tạo hình dày dặn phủ dạ quang nổi bật trên nền mặt số kích thước lớn nam tính 42mm.',
20,1,5,0),
/*Doxa*/
(N'DOXA D201RSV' ,4638000,0,'DOXA D201RSV.png',
N'Mẫu Doxa D201RSV vẻ ngoài sang trọng với mẫu vạch số tạo hình mỏng mang lại sự tinh tế dành cho phái mạnh đầy nổi bật khi các chi tiết kim chỉ được phủ tông vàng hồng trẻ trung đầy cuốn hút.',
20,1,6,0),
(N'DOXA EXECUTIVE D221RSV' ,3470000,0,'DOXA EXECUTIVE D221RSV.png',
N'Đồng hồ Nữ DOXA EXECUTIVE D221RSV
 Ẩn bên dưới mặt kính Sapphire với nền mặt số được gia công tinh xảo đính các viên kim cương tạo nên phụ kiện thời trang sang trọng dành cho phái đẹp với phiên bản Doxa D221RSV..',
20,1,6,0),
(N'DOXA EXECUTIVE D221RSV' ,3970000,0,'DOXA EXECUTIVE D221RSV.png',
N'Đồng hồ Nữ DOXA EXECUTIVE D221RSV
 Ẩn bên dưới mặt kính Sapphire với nền mặt số được gia công tinh xảo đính các viên kim cương tạo nên phụ kiện thời trang sang trọng dành cho phái đẹp với phiên bản Doxa D221RSV..',
20,1,6,0),
(N'DOXA D147SWHW',3090000,0,'DOXA D147SWHW.png',
N'Trẻ trung đầy quyền lực với mẫu D147SWHW mang trên mình một vẻ ngoài giản dị của chiếc đồng hồ 3 kim, ẩn chứa bên dưới mặt kính Sapphire các chi tiết đồng hồ tạo nét mỏng tinh tế được mạ bạc đầy sang trọng đến từ thương hiệu Doxa.',
20,1,6,0),
(N'DOXA D194SGD' ,2374000,0,'DOXA D194SGD.png',
N'Mẫu Doxa D194SGD phiên bản mặt vuông mang nét hoài cổ sang trọng với 8 viên kim cương đính trên mặt đồng hồ cùng với họa tiết trải tia.',
20,1,6,0),
(N'DOXA D185TSD' ,2202000,0,'DOXA D185TSD.png',
N'Mẫu Doxa D185TSD phiên bản mặt số được gia công tinh xảo đính kèm 8 viên kim cương nổi bật trên nền mặt số kiểu dáng đon giản 3 kim size 41mm.',
20,1,6,0),
(N'DOXA D167RWH' ,4123000,0,'DOXA D167RWH.png',
N'Đồng hồ Doxa D167RWH với thiết kế bộ máy cơ trải nghiệm dành cho nam, mặt đồng kiểu tròn kích thước to nam tính, nổi bật với phần viền ngoài được mạ màu vàng hồng đem lại vẻ trẻ trung cho phái nam.',
20,1,6,0),
(N'DOXA D194SSD' ,2374000,0,'DOXA D194SSD.png',
N'Doxa D194SSD mang trên mình vẻ hoài cổ với mẫu thiết kế mặt số vuông, dưới mặt kính Sapphire đính kèm 8 viên kim cương chứa đựng vẻ tinh tế đầy sang trọng với đồng hồ phiên bản kim loại bạc.',
20,1,6,0),
(N'DOXA D218SSV' ,3286000,0,'DOXA D218SSV.png',
N'Mẫu đồng hồ D218SSV mang trên mình một vẻ giản dị với phiên bản kim loại bạc đầy sang trọng, bên dưới mặt kính Sapphire các chi tiết đồng hồ được tạo nét mỏng chứa đựng sự tinh tế trẻ trung đến từ thương hiệu Doxa.',
20,1,6,0),
(N'DOXA D105SMW' ,2337000,0,'DOXA D105SMW.png',
N'Mẫu Doxa D105SMW phiên bản đính kim cương sang trọng, mặt số size 37mm thiết kế đơn giản 3 kim cùng với nền cọc số la mã tạo hình thời trang.',
20,1,6,0),
/*SeiKo*/
(N'SEIKO SGEH89P1' ,4890000,0,'SEIKO SGEH89P1.png',
N'Mẫu Seiko SGEH89P1 phiên bản mặt số size 40mm tone xanh thời trang cho phái mạnh đi kèm thiết  kế đơn giản 3 kim cùng chi tiết vạch số mỏng mạ bạc.',
20,1,7,0),
(N'SEIKO SRPG25J1' ,6525000,0,'SSRPG25J11.png',
N'Mẫu Seiko SRPG25J1 kim giây đỏ tone màu nổi bật cùng nền cọc số la mã được tạo hình mỏng thời trang trên nền mặt số trắng kích thước 41mm với thiết kế họa tiết.',
20,1,7,0),
(N'SEIKO SRK035P1' ,9510000,0,'SEIKO SRK035P1.png',
N'Đồng hồ nam Seiko SRK035P1 mặt đồng hồ kiểu to tròn với kim chỉ xanh trẻ trung nổi bật với chữ số la mã theo phong cách hoài cổ, vỏ máy bằng kim loại phối cùng dây đeo bằng da lịch lãm.',
20,1,7,0),
(N'SEIKO SUP863P1' ,4090000,0,'SEIKO SUP863P1.png',
N'Vẻ ngoài lịch lãm đến từ mẫu dây da nâu có vân ẩn chứa với vẻ giản dị của chiếc đồng hồ Seiko SUP863P1 dành cho phái mạnh mang trên mình một vẻ hiện đại khi được trang bị công nghệ Solar (Năng Lượng Ánh Sáng).',
20,1,7,0),
(N'SEIKO SSB343P1' ,6120000,0,'SEIKO SSB343P1.png',
N'Mẫu Seiko SSB343P1 thiết kế 3 núm điều chỉnh tính năng Chronograph (đo thời gian) hiện thị trên nền mặt số trắng size lớn 43mm phiên bản đồng hồ 6 kim kiểu dáng nam tính.',
20,1,7,0),
(N'SEIKO 5 FIELD SPORTS STYLE SNZG11K1' ,6640000,0,'SEIKO 5 FIELD SPORTS STYLE SNZG11K1.png',
N'Mẫu Seiko SNZG11K1 phiên bản Seiko 5 dây vải cùng với phần vỏ máy cơ thiết kế dày dặn khả năng chịu nước lên đến 10ATM, kim chỉ cùng cọc vạch số được phủ dạ quang trên mặt số kích thước 42mm.',
20,1,7,0),
(N'SEIKO PRESAGE SSA443J1' ,15325000 ,0,'SEIKO PRESAGE SSA443J1.png',
N'Mẫu Seiko SSA443J1 thiết kế open heart (máy cơ lộ tim) tạo nên vẻ độc đáo nổi bật trên nền mặt số trắng kích thước 41.8mm.',
20,1,7,0),
(N'SEIKO SSB317P1' ,5670000 ,0,'SEIKO SSB317P1.png',
N'Mẫu Seiko SSB317P1 thiết kế trẻ trung nam tính với kiểu dáng 6 kim đi kèm chức năng Chronograph trên nền mặt trắng size 44mm, vẻ ngoài sang trọng với phần dây vỏ kim loại mạ bạc.',
20,1,7,0),
(N'SEIKO SUR369P1' ,8040000 ,0,'SEIKO SUR369P1.png',
N'Mẫu Seiko SUR369P1 phiên bản chất liệu siêu nhẹ Titanium trên phần vỏ máy, thiết kế đơn giản trẻ trung với chức năng 3 kim trên nền mặt số size 40mm.',
20,1,7,0),
(N'SEIKO SSB361P1' ,6625000 ,0,'SEIKO SSB361P1.png',
N'Mẫu Seiko SSB361P1 thiết kế 3 núm vặn điều chỉnh các tính năng Chronograph (đo thời gian) tạo nên kiểu dáng đồng hồ 6 kim trên nền mặt số lớn kích thước 43.9mm.',
20,1,7,0),
/*TISSOT*/
(N'TISSOT TRADITION HTC276',1406000 ,0,'TISSOT TRADITION T063.637.36.037.00.png',
N'Đồng hồ Tissot T063.637.36.037.00 với thiết kế mặt số màu trắng, kim chỉ và gạch số được mạ tone màu vàng hồng, mang cảm giác cổ điển, dây đeo da màu nâu tạo nét lịch lãm, sang trọng.',
20,1,8,0),
(N'TISSOT TRADITION BTHA23',1168000 ,0,'TISSOT TRADITION T063.610.22.037.00.png',
N'Với niềng đồng hồ Tissot T063.610.22.037.00 bằng kim loại thép không gỉ mạ bạc, kim chỉ và gạch số được phủ vàng tinh tế trên nền trắng mặt số tròn cổ điển.',
20,1,8,0),
(N'TISSOT TRADITION KBN92',2060000 ,0,'TISSOT TRADITION T063.617.36.037.00.png',
N'Đồng hồ Tissot T063.617.36.037.00 có niềng kim loại bo tròn tinh tế quanh nền số màu trắng trang nhã, kim chỉ và vạch số được dát mỏng tinh tế, dây đeo da màu nâu có vân đem lại phong cách thời trang mang vẻ lịch lãm, nam tính.',
20,1,8,0),
/*G-SHOCK*/
(N'G-SHOCK NAM GA110' ,4612000,0,'G-SHOCK NAM.png',
N'Đồng hồ G-Shock GA-110-1BDR với chất liệu nhựa cao cấp siêu bền, vỏ và dây đeo có màu đen, chống va chạm mạnh, mặt kính khoáng chịu lực, khả năng chống nước lên đến 20ATM.',
20,1,9,0),
(N'G-SHOCK GA1A2DR' ,4638000,0,'G-SHOCK GA-2000-1A2DR.png',
N'Mẫu G-Shock GA-2000-1A2DR phần vỏ viền ngoài tạo hình nền cọc số mang lại vẻ thể thao năng động cùng các ô số điện tử hiện thị chức năng lịch và đo thời gian.',
20,1,9,0),
(N'G-SHOCK GA1A4DR' ,4638000,0,'G-SHOCK GA-500-1A4DR.png',
N'Mẫu G-Shock GA-500-1A4DR kích thước mặt đồng hồ to dày dặn nam tính, với chất liệu tổng thể bằng cao su, mặt đồng hồ chức năng kim chỉ cùng điện tử hiện thị trên nền đen mạnh mẽ mang đậm phong cách thể thao.',
20,1,9,0);

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
('Orient FUB9B005W0_3.png',15,0),
('CASIO MTP-B305D-1EVDF_1.png',16,0),
('CASIO A100WEG-9ADF_1.png',17,0),
('CASIO A100WEG-9ADF_2.png',17,0),
('CASIO MTP-B300M-2AVDF_1.png',18,0),
('CASIO MTP-B300M-2AVDF_2.png',18,0),
('CASIO W-218H-4BVDF_1.png',19,0),
('CASIO W-218H-4BVDF_2.png',19,0),
('CASIO W-218H-4BVDF_3.png',19,0),
('CASIO MTD-1060D-1A3VDF_1.png',20,0),
('CASIO MTD-1060D-1A3VDF_2.png',20,0),
('CASIO MTD-1060D-1A3VDF_3.png',20,0),
('CASIO MW-240-7EVDF_1.png',21,0),
('CASIO MW-240-7EVDF_2.png',21,0),
('CASIO MW-240-7EVDF_3.png',21,0),
('CASIO MTP-VD02L-7EUDF_1.png',22,0),
('CASIO A171WEMB-1ADF_1.png',23,0),
('CASIO A171WEMB-1ADF_2.png',23,0),
('CASIO A171WEMB-1ADF_3.png',23,0),
('CASIO AE-1400WHD-1AVDF_1.png',24,0),
('CASIO AE-1400WHD-1AVDF_2.png',24,0),
('CASIO AE-1400WHD-1AVDF_3.png',24,0),
('CASIO MTP-E600M-9BDF_1.png',25,0),
('CASIO MTP-E600M-9BDF_2.png',25,0),
('CASIO MTP-E600M-9BDF_3.png',25,0),
('CASIO A168WG-9WDF_1.png',26,0),
('CASIO A168WG-9WDF_2.png',26,0),
('CASIO A168WG-9WDF_3.png',26,0),
('CASIO LTP-1183A-7ADF_1.png',27,0),
('CASIO LTP-1183A-7ADF_2.png',27,0),
('CASIO B640WCG-5DF_1.png',28,0),
('CASIO SHE-4055PGL-7BUDF_1.png',29,0),
('CCASIO SHE-4055PGL-7BUDF_2.png',29,0),
('CASIO SHE-4055PGL-7BUDF_3.png',29,0),
('CASIO A159WGED-1DF_1.png',30,0),
('CASIO A159WGED-1DF_2.png',30,0),
('CASIO A159WGED-1DF_3.png',30,0),
('CASIO LW-200-2BVDF_1.png',31,0),
('CASIO LW-200-2BVDF_2.png',31,0),
('CASIO LW-200-2BVDF_3.png',31,0),
('CASIO LTP-V005G-7AUDF_1.png',32,0),
('CASIO LTP-V005G-7AUDF_2.png',32,0),
('CASIO LTP-V005G-7AUDF_3.png',32,0),
('CASIO W-218HC-4A2VDF_1.png',33,0),
('CASIO LTP-VT02BL-1AUDF_1.png',34,0),
('ORIENT FGW0100AW0_1.png',35,0),
('ORIENT FGW0100AW0_2.png',35,0),
('ORIENT FGW0100AW0_3.png',35,0),
('ORIENT CABALLERO FAG00004D0_1.png',36,0),
('ORIENT CABALLERO FAG00004D0_2.png',36,0),
('ORIENT CABALLERO FAG00004D0_3.png',36,0),
('ORIENT MAKO FAA02002D9_1.png',36,0),
('ORIENT RA-AC0H01L10B_1.png',37,0),
('ORIENT RA-AC0H01L10B_2.png',37,0),
('ORIENT RA-AC0H01L10B_3.png',37,0),
('ORIENT RE-AV0006Y00B_1.png',38,0),
('ORIENT RE-AV0006Y00B_2.png',38,0),
('ORIENT RE-AV0006Y00B_3.png',38,0),
('ORIENT FAB00004D9_1.png',39,0),
('ORIENT FAB00004D9_2.png',39,0),
('ORIENT FAB00004D9_3.png',39,0),
('ORIENT RA-AC0J03L10B_1.png',40,0),
('ORIENT RA-AC0J03L10B_2.png',40,0),
('ORIENT RA-AC0J03L10B_3.png',40,0),
('ORIENT RA-AC0J03L10B_1.png',41,0),
('ORIENT RA-AC0F10S10B_1.png',42,0),
('ORIENT RA-AC0F10S10B_2.png',42,0),
('ORIENT RA-AC0F10S10B_3.png',42,0),
('CITIZEN NH8350-83A_1.png',43,0),
('CITIZEN NH8350-83A_2.png',43,0),
('CITIZEN NH8350-83A_3.png',43,0),
('CITIZEN BM7370-11A_1.png',44,0),
('CITIZEN BM7370-11A_2.png',44,0),
('CITIZEN BM7370-11A_3.png',44,0),
('CITIZEN AW1211-12A_1.png',45,0),
('CITIZEN CA4458-88E_1.png',46,0),
('CITIZEN NH8360-80E_1.png',47,0),
('CITIZEN NH8360-80E_2.png',47,0),
('CITIZEN NH8360-80E_3.png',47,0),
('CITIZEN BE9170-13H_1.png',48,0),
('CITIZEN BE9170-13H_2.png',48,0),
('CITIZEN BE9170-13H_3.png',48,0),
('CITIZEN BI5000-87E_1.png',49,0),
('CITIZEN C7 NH8390-20L_1.png',50,0),
('CITIZEN C7 NH8390-20L_2.png',50,0),
('CITIZEN C7 NH8390-20L_3.png',50,0),
('CITIZEN NJ0150-81E_1.png',51,0),
('CITIZEN NJ0150-81E_2.png',51,0),
('CITIZEN NJ0150-81E_3.png',51,0),
('MOVADO 0606916_1.png',52,0),
('MOVADO 0606916_2.png',52,0),
('MOVADO 0606916_3.png',52,0),
('MOVADO 0606114_1.png',53,0),
('MOVADO 0606114_2.png',53,0),
('MOVADO 0606114_3.png',53,0),
('MOVADO 0606115_1.png',54,0),
('MOVADO 0606115_2.png',54,0),
('MOVADO 0606115_3.png',54,0),
('FOSSIL HARRY POTTER LIMITED EDITION LE1159_1.png',55,0),
('FOSSIL HARRY POTTER LIMITED EDITION LE1159_2.png',55,0),
('FOSSIL HARRY POTTER LIMITED EDITION LE1159_3.png',55,0),
('FOSSIL FS5306_1.png',56,0),
('FOSSIL FS5306_2.png',56,0),
('FOSSIL FS5306_3.png',56,0),
('FOSSIL FS5946_1.png',57,0),
('FOSSIL FS5946_2.png',57,0),
('FOSSIL FS5946_3.png',57,0),
('FOSSIL FS5473_1.png',58,0),
('FOSSIL FS5473_2.png',58,0),
('FOSSIL FS5473_3.png',58,0),
('FOSSIL FS5963_1.png',59,0),
('FOSSIL FS5963_2.png',59,0),
('FOSSIL FS5963_3.png',59,0),
('FOSSIL FS5668_1.png',60,0),
('FOSSIL FS5668_2.png',60,0),
('FOSSIL FS5668_3.png',60,0),
('FOSSIL FS5948_1.png',61,0),
('FOSSIL FS5948_2.png',61,0),
('FOSSIL FS5948_3.png',61,0),
('FOSSIL FS5905_1.png',62,0),
('FOSSIL FS5905_2.png',62,0),
('FOSSIL FS5905_3.png',62,0),
('FOSSIL FS5843_1.png',63,0),
('FOSSIL FS5843_2.png',63,0),
('FOSSIL FS5843_3.png',63,0),
('FOSSIL FS5952_1.png',64,0),
('FOSSIL FS5952_2.png',64,0),
('FOSSIL FS5952_3.png',64,0),
('DOXA D147SWHW_1.png',65,0),
('DOXA D147SWHW_2.png',65,0),
('DOXA D147SWHW_3.png',65,0),
('DOXA D194SGD_1.png',66,0),
('DOXA D185TSD_1.png',67,0),
('DOXA D167RWH_1.png',68,0),
('DOXA D167RWH_2.png',68,0),
('DOXA D194SSD_1.png',69,0),
('DOXA D194SSD_2.png',69,0),
('DOXA D194SSD_3.png',69,0),
('DOXA D218SSV_1.png',70,0),
('DOXA D218SSV_2.png',70,0),
('DOXA D218SSV_3.png',70,0),
('DOXA D105SMW_1.png',71,0),
('DOXA D105SMW_2.png',71,0),
('DOXA D105SMW_3.png',71,0),
('SEIKO SGEH89P1_1.png',72,0),
('SEIKO SGEH89P1_2.png',72,0),
('SEIKO SGEH89P1_3.png',72,0),
('SEIKO PRESAGE SRPG25J1_1.png',73,0),
('SEIKO SRK035P1_1.png',74,0),
('SEIKO SRK035P1_2.png',74,0),
('SEIKO SUP863P1_1.png',75,0),
('SEIKO SUP863P1_2.png',75,0),
('SEIKO SUP863P1_3.png',75,0),
('SEIKO SSB343P1_1.png',76,0),
('SEIKO SSB343P1_2.png',76,0),
('SEIKO SSB343P1_3.png',76,0),
('SEIKO 5 FIELD SPORTS STYLE SNZG11K1_1.png',77,0),
('SEIKO PRESAGE SSA443J1_1.png',78,0),
('SEIKO SSB317P1_1.png',79,0),
('SEIKO SSB317P1_2.png',79,0),
('SEIKO SSB317P1_3.png',79,0),
('SEIKO SUR369P1_1.png',80,0),
('SEIKO SUR369P1_2.png',80,0),
('SEIKO SUR369P1_3.png',80,0),
('SEIKO SSB361P1_1.png',81,0),
('TISSOT TRADITION T063.637.36.037.00_1.png',82,0),
('TISSOT TRADITION T063.637.36.037.00_2.png',82,0),
('TISSOT TRADITION T063.637.36.037.00_3.png',82,0),
('TISSOT TRADITION T063.610.22.037.00_1.png',83,0),
('TISSOT TRADITION T063.610.22.037.00_2.png',83,0),
('TISSOT TRADITION T063.610.22.037.00_3.png',83,0),
('TISSOT TRADITION T063.617.36.037.00_1.png',84,0),
('TISSOT TRADITION T063.617.36.037.00_2.png',84,0),
('TISSOT TRADITION T063.617.36.037.00_3.png',84,0),
('G-SHOCK NAM (GA-110-1BDR)_1.png',85,0),
('G-SHOCK NAM (GA-110-1BDR)_2.png',85,0),
('G-SHOCK NAM (GA-110-1BDR)_3.png',85,0),
('G-SHOCK GA-2000-1A2DR_1.png',86,0),
('G-SHOCK GA-2000-1A2DR_2.png',86,0),
('G-SHOCK GA-2000-1A2DR_3.png',86,0),
('G-SHOCK GA-500-1A4DR_1.png',87,0),
('G-SHOCK GA-500-1A4DR_2.png',87,0),
('G-SHOCK GA-500-1A4DR_3.png',87,0);

insert into ware_house(amount,product_id,is_deleted)
values 
(100,1,0),(100,2,0),(100,3,0),(100,4,0),(100,5,0),(100,6,0),(100,7,0),(100,8,0),(100,9,0),
(100,10,0),(100,11,0),(100,12,0),(100,13,0),(100,14,0),(100,15,0),(100,16,0),(100,17,0),(100,18,0),(100,19,0),
(100,20,0),(100,21,0),(100,22,0),(100,23,0),(100,24,0),(100,25,0),(100,26,0),(100,27,0),(100,28,0),(100,29,0),
(100,30,0),(100,31,0),(100,32,0),(100,33,0),(100,34,0),(100,35,0),(100,36,0),(100,37,0),(100,38,0),(100,39,0),
(100,40,0),(100,41,0),(100,42,0),(100,43,0),(100,44,0),(100,45,0),(100,46,0),(100,47,0),(100,48,0),(100,49,0),
(100,50,0),(100,51,0),(100,52,0),(100,53,0),(100,54,0),(100,55,0),(100,56,0),(100,57,0),(100,58,0),(100,59,0),
(100,60,0),(100,61,0),(100,62,0),(100,63,0),(100,64,0),(100,65,0),(100,66,0),(100,67,0),(100,68,0),(100,69,0),
(100,70,0),(100,71,0),(100,72,0),(100,73,0),(100,74,0),(100,75,0),(100,76,0),(100,77,0),(100,78,0),(100,79,0),
(100,80,0),(100,81,0),(100,82,0),(100,83,0),(100,84,0),(100,85,0),(100,86,0),(100,87,0),(100,88,0);

update product set warehouse_id=1 where id=1;
update product set warehouse_id=2 where id=2;
update product set warehouse_id=3 where id=3;
update product set warehouse_id=4 where id=4;
update product set warehouse_id=5 where id=5;
update product set warehouse_id=6 where id=6;
update product set warehouse_id=7 where id=7;
update product set warehouse_id=8 where id=8;
update product set warehouse_id=9 where id=9;
update product set warehouse_id=10 where id=10;
update product set warehouse_id=11 where id=11;
update product set warehouse_id=12 where id=12;
update product set warehouse_id=13 where id=13;
update product set warehouse_id=14 where id=14;
update product set warehouse_id=15 where id=15;
update product set warehouse_id=16 where id=16;
update product set warehouse_id=17 where id=17;
update product set warehouse_id=18 where id=18;
update product set warehouse_id=19 where id=19;
update product set warehouse_id=20 where id=20;
update product set warehouse_id=21 where id=21;
update product set warehouse_id=22 where id=22;
update product set warehouse_id=23 where id=23;
update product set warehouse_id=24 where id=24;
update product set warehouse_id=25 where id=25;
update product set warehouse_id=26 where id=26;
update product set warehouse_id=27 where id=27;
update product set warehouse_id=28 where id=28;
update product set warehouse_id=29 where id=29;
update product set warehouse_id=30 where id=30;
update product set warehouse_id=31 where id=31;
update product set warehouse_id=32 where id=32;
update product set warehouse_id=33 where id=33;
update product set warehouse_id=34 where id=34;
update product set warehouse_id=35 where id=35;
update product set warehouse_id=36 where id=36;
update product set warehouse_id=37 where id=37;
update product set warehouse_id=38 where id=38;
update product set warehouse_id=39 where id=39;
update product set warehouse_id=40 where id=40;
update product set warehouse_id=41 where id=41;
update product set warehouse_id=42 where id=42;
update product set warehouse_id=43 where id=43;
update product set warehouse_id=44 where id=44;
update product set warehouse_id=45 where id=45;
update product set warehouse_id=46 where id=46;
update product set warehouse_id=47 where id=47;
update product set warehouse_id=48 where id=48;
update product set warehouse_id=49 where id=49;
update product set warehouse_id=50 where id=50;
update product set warehouse_id=51 where id=51;
update product set warehouse_id=52 where id=52;
update product set warehouse_id=53 where id=53;
update product set warehouse_id=54 where id=54;
update product set warehouse_id=55 where id=55;
update product set warehouse_id=56 where id=56;
update product set warehouse_id=57 where id=57;
update product set warehouse_id=58 where id=58;
update product set warehouse_id=59 where id=59;
update product set warehouse_id=60 where id=60;
update product set warehouse_id=61 where id=61;
update product set warehouse_id=62 where id=62;
update product set warehouse_id=63 where id=63;
update product set warehouse_id=64 where id=64;
update product set warehouse_id=65 where id=65;
update product set warehouse_id=66 where id=66;
update product set warehouse_id=67 where id=67;
update product set warehouse_id=68 where id=68;
update product set warehouse_id=69 where id=69;
update product set warehouse_id=70 where id=70;
update product set warehouse_id=71 where id=71;
update product set warehouse_id=72 where id=72;
update product set warehouse_id=73 where id=73;
update product set warehouse_id=74 where id=74;
update product set warehouse_id=75 where id=75;
update product set warehouse_id=76 where id=76;
update product set warehouse_id=77 where id=77;
update product set warehouse_id=78 where id=78;
update product set warehouse_id=79 where id=79;
update product set warehouse_id=80 where id=80;
update product set warehouse_id=81 where id=81;
update product set warehouse_id=82 where id=82;
update product set warehouse_id=83 where id=83;
update product set warehouse_id=84 where id=84;
update product set warehouse_id=85 where id=85;
update product set warehouse_id=86 where id=86;
update product set warehouse_id=87 where id=87;
update product set warehouse_id=88 where id=88;

