-- PROCEDURE
-- Procedure 1
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
END ;

-- Procedure 2
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_report`(
IN years INT(11)
)
BEGIN
	select
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='1' and order_status = 'Đã giao') 
		as 'Thang1',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='2' and order_status = N'Đã giao')
		as 'Thang2',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='3' and order_status = N'Đã giao')
		as 'Thang3',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='4' and order_status = N'Đã giao')
		as 'Thang4',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='5' and order_status = N'Đã giao')
		as 'Thang5',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='6' and order_status = N'Đã giao')
		as 'Thang6',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='7' and order_status = N'Đã giao')
		as 'Thang7',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='8' and order_status = N'Đã giao')
		as 'Thang8',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='9' and order_status = N'Đã giao')
		as 'Thang9',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='10' and order_status = N'Đã giao')
		as 'Thang10',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='11' and order_status = N'Đã giao')
		as 'Thang11',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='12' and order_status = N'Đã giao')
		as 'Thang12';
END ;

-- Procedure 3
CREATE DEFINER=`admin`@`%` PROCEDURE `FillCalendar`(start_date DATE, end_date DATE)
BEGIN
    DECLARE crt_date DATE;
    SET crt_date = start_date;
    WHILE crt_date <= end_date DO
        INSERT IGNORE INTO calendar VALUES(crt_date);
        SET crt_date = ADDDATE(crt_date, INTERVAL 1 DAY);
    END WHILE;
END ;

-- Procedure 4
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
END ;

-- Procedure 5
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
END ;

-- Procedure 6
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

END ;

-- Procedure 7
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
END ;

-- Procedure 8
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
END ;

-- Procedure 9
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
END ;

-- Procedure 10
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
END ;

-- Procedure 11
CREATE DEFINER=`admin`@`%` PROCEDURE `sp_statistical_by_year`(IN years INT(11))
BEGIN
	select
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='1' and order_status = 'Đã giao') 
		as 'Thang1',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='2' and order_status = N'Đã giao')
		as 'Thang2',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='3' and order_status = N'Đã giao')
		as 'Thang3',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='4' and order_status = N'Đã giao')
		as 'Thang4',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='5' and order_status = N'Đã giao')
		as 'Thang5',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='6' and order_status = N'Đã giao')
		as 'Thang6',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='7' and order_status = N'Đã giao')
		as 'Thang7',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='8' and order_status = N'Đã giao')
		as 'Thang8',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='9' and order_status = N'Đã giao')
		as 'Thang9',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='10' and order_status = N'Đã giao')
		as 'Thang10',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='11' and order_status = N'Đã giao')
		as 'Thang11',
		(select sum(total_money) from orders o where year(order_date)=years and month(order_date)='12' and order_status = N'Đã giao')
		as 'Thang12';
END ;

-- Procedure 12
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
END ;

-- Procedure 13
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
END ;