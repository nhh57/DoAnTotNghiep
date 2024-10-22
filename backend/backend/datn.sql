-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 21, 2024 at 04:21 AM
-- Server version: 8.0.31
-- PHP Version: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `datn`
--

-- --------------------------------------------------------

--
-- Table structure for table `cartitems`
--

CREATE TABLE `cartitems` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cartitems`
--

INSERT INTO `cartitems` (`id`, `user_id`, `product_id`, `quantity`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 1, '2024-10-21 11:20:26', '2024-10-21 11:20:26'),
(2, 2, 2, 1, '2024-10-21 11:20:26', '2024-10-21 11:20:26'),
(3, 3, 3, 1, '2024-10-21 11:20:26', '2024-10-21 11:20:26'),
(4, 4, 4, 1, '2024-10-21 11:20:26', '2024-10-21 11:20:26'),
(5, 5, 5, 1, '2024-10-21 11:20:26', '2024-10-21 11:20:26');

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int NOT NULL,
  `category_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `images` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `parent_categoryID` int DEFAULT NULL,
  `status` enum('active','inactive') COLLATE utf8mb4_general_ci DEFAULT 'active',
  `description` text COLLATE utf8mb4_general_ci,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `category_name`, `images`, `parent_categoryID`, `status`, `description`, `created_at`, `updated_at`) VALUES
(1, 'Electronics', 'electronics.jpg', NULL, 'active', 'All kinds of electronics', '2024-10-21 11:15:16', '2024-10-21 11:15:16'),
(2, 'Fashion', 'fashion.jpg', NULL, 'active', 'Latest fashion trends', '2024-10-21 11:15:16', '2024-10-21 11:15:16'),
(3, 'Home Appliances', 'home_appliances.jpg', NULL, 'inactive', 'Appliances for home use', '2024-10-21 11:15:16', '2024-10-21 11:15:16'),
(4, 'Books', 'books.jpg', NULL, 'active', 'All genres of books', '2024-10-21 11:15:16', '2024-10-21 11:15:16'),
(5, 'Toys', 'toys.jpg', NULL, 'active', 'Toys for all ages', '2024-10-21 11:15:16', '2024-10-21 11:15:16');

-- --------------------------------------------------------

--
-- Table structure for table `contactmessages`
--

CREATE TABLE `contactmessages` (
  `id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `subject` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `message` text COLLATE utf8mb4_general_ci NOT NULL,
  `status` enum('pending','resolved') COLLATE utf8mb4_general_ci DEFAULT 'pending',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `contactmessages`
--

INSERT INTO `contactmessages` (`id`, `user_id`, `subject`, `message`, `status`, `created_at`, `updated_at`) VALUES
(1, 1, 'Shipping Inquiry', 'When will my order be shipped?', 'pending', '2024-10-21 11:19:08', '2024-10-21 11:19:08'),
(2, 2, 'Order Cancel', 'Please cancel my order.', 'resolved', '2024-10-21 11:19:08', '2024-10-21 11:19:08'),
(3, 3, 'Voucher Problem', 'My voucher code is not working.', 'pending', '2024-10-21 11:19:08', '2024-10-21 11:19:08'),
(4, 4, 'Product Review', 'How do I post a review?', 'resolved', '2024-10-21 11:19:08', '2024-10-21 11:19:08'),
(5, 5, 'Payment Issue', 'I was charged twice.', 'pending', '2024-10-21 11:19:08', '2024-10-21 11:19:08'),
(6, 1, 'Shipping Inquiry', 'When will my order be shipped?', 'pending', '2024-10-21 11:19:16', '2024-10-21 11:19:16'),
(7, 2, 'Order Cancel', 'Please cancel my order.', 'resolved', '2024-10-21 11:19:16', '2024-10-21 11:19:16'),
(8, 3, 'Voucher Problem', 'My voucher code is not working.', 'pending', '2024-10-21 11:19:16', '2024-10-21 11:19:16'),
(9, 4, 'Product Review', 'How do I post a review?', 'resolved', '2024-10-21 11:19:16', '2024-10-21 11:19:16'),
(10, 5, 'Payment Issue', 'I was charged twice.', 'pending', '2024-10-21 11:19:16', '2024-10-21 11:19:16');

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `message` text COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`id`, `user_id`, `message`, `created_at`, `updated_at`) VALUES
(1, 1, 'Your order has been shipped.', '2024-10-21 11:18:38', '2024-10-21 11:18:38'),
(2, 2, 'Your order has been delivered.', '2024-10-21 11:18:38', '2024-10-21 11:18:38'),
(3, 3, 'Your voucher has been applied.', '2024-10-21 11:18:38', '2024-10-21 11:18:38'),
(4, 4, 'Your order has been cancelled.', '2024-10-21 11:18:38', '2024-10-21 11:18:38'),
(5, 5, 'Your review has been posted.', '2024-10-21 11:18:38', '2024-10-21 11:18:38');

-- --------------------------------------------------------

--
-- Table structure for table `orderdetails`
--

CREATE TABLE `orderdetails` (
  `id` int NOT NULL,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `discount` int DEFAULT '0',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orderdetails`
--

INSERT INTO `orderdetails` (`id`, `order_id`, `product_id`, `quantity`, `price`, `discount`, `created_at`, `updated_at`) VALUES
(11, 1, 1, 1, 299.99, 10, '2024-10-21 11:17:12', '2024-10-21 11:17:12'),
(12, 2, 2, 1, 999.99, 15, '2024-10-21 11:17:12', '2024-10-21 11:17:12'),
(13, 3, 3, 1, 79.99, 5, '2024-10-21 11:17:12', '2024-10-21 11:17:12'),
(14, 4, 4, 1, 150.00, 20, '2024-10-21 11:17:12', '2024-10-21 11:17:12'),
(15, 5, 5, 1, 19.99, 0, '2024-10-21 11:17:12', '2024-10-21 11:17:12');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `payment_method` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `status` enum('shipped','delivered','cancelled') COLLATE utf8mb4_general_ci DEFAULT 'shipped',
  `payment_amount` decimal(10,2) NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `phone_number` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `voucher_id` int DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `user_id`, `total_amount`, `payment_method`, `status`, `payment_amount`, `address`, `phone_number`, `voucher_id`, `created_at`, `updated_at`) VALUES
(1, 1, 319.99, 'credit_card', 'shipped', 299.99, '123 Main St', '0123456789', NULL, '2024-10-21 11:15:42', '2024-10-21 11:15:42'),
(2, 2, 1019.99, 'paypal', 'delivered', 999.99, '456 Market St', '0987654321', NULL, '2024-10-21 11:15:42', '2024-10-21 11:15:42'),
(3, 3, 94.99, 'credit_card', 'shipped', 79.99, '789 Oak St', '0123123123', 1, '2024-10-21 11:15:42', '2024-10-21 11:15:42'),
(4, 4, 180.00, 'debit_card', 'cancelled', 150.00, '321 Maple St', '0912312312', 2, '2024-10-21 11:15:42', '2024-10-21 11:15:42'),
(5, 5, 19.99, 'cash', 'delivered', 19.99, '654 Pine St', '0987123123', NULL, '2024-10-21 11:15:42', '2024-10-21 11:15:42');

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE `posts` (
  `id` int NOT NULL,
  `auth_id` int NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `content` text COLLATE utf8mb4_general_ci NOT NULL,
  `post_category_id` int DEFAULT NULL,
  `status` enum('draft','published') COLLATE utf8mb4_general_ci DEFAULT 'draft',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`id`, `auth_id`, `title`, `content`, `post_category_id`, `status`, `created_at`, `updated_at`) VALUES
(1, 1, 'Latest Tech Trends', 'Content about latest tech trends.', 1, 'published', '2024-10-21 11:18:58', '2024-10-21 11:18:58'),
(2, 2, 'How to stay healthy', 'Content about staying healthy.', 3, 'published', '2024-10-21 11:18:58', '2024-10-21 11:18:58'),
(3, 3, 'Top 10 movies', 'Content about top 10 movies.', 4, 'published', '2024-10-21 11:18:58', '2024-10-21 11:18:58'),
(4, 4, 'Best study techniques', 'Content about study techniques.', 5, 'draft', '2024-10-21 11:18:58', '2024-10-21 11:18:58'),
(5, 5, 'Fashion trends 2024', 'Content about fashion trends.', 2, 'published', '2024-10-21 11:18:58', '2024-10-21 11:18:58');

-- --------------------------------------------------------

--
-- Table structure for table `post_categories`
--

CREATE TABLE `post_categories` (
  `id` int NOT NULL,
  `parentCategoryID` int DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `post_categories`
--

INSERT INTO `post_categories` (`id`, `parentCategoryID`, `name`, `created_at`, `updated_at`) VALUES
(1, NULL, 'Tech', '2024-10-21 11:18:48', '2024-10-21 11:18:48'),
(2, NULL, 'Lifestyle', '2024-10-21 11:18:48', '2024-10-21 11:18:48'),
(3, NULL, 'Health', '2024-10-21 11:18:48', '2024-10-21 11:18:48'),
(4, NULL, 'Entertainment', '2024-10-21 11:18:48', '2024-10-21 11:18:48'),
(5, NULL, 'Education', '2024-10-21 11:18:48', '2024-10-21 11:18:48');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `image` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_general_ci,
  `discount` decimal(5,2) DEFAULT '0.00',
  `quantity` int NOT NULL,
  `status` enum('active','inactive') COLLATE utf8mb4_general_ci DEFAULT 'active',
  `categories_id` int DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `price`, `image`, `description`, `discount`, `quantity`, `status`, `categories_id`, `created_at`, `updated_at`) VALUES
(1, 'Smartphone', 299.99, 'smartphone.jpg', 'Latest smartphone with great features', 10.00, 50, 'active', 1, '2024-10-21 11:16:52', '2024-10-21 11:16:52'),
(2, 'Laptop', 999.99, 'laptop.jpg', 'High-performance laptop', 15.00, 20, 'active', 1, '2024-10-21 11:16:52', '2024-10-21 11:16:52'),
(3, 'Jacket', 79.99, 'jacket.jpg', 'Stylish winter jacket', 5.00, 100, 'active', 2, '2024-10-21 11:16:52', '2024-10-21 11:16:52'),
(4, 'Microwave Oven', 150.00, 'microwave.jpg', 'High-efficiency microwave oven', 20.00, 30, 'inactive', 3, '2024-10-21 11:16:52', '2024-10-21 11:16:52'),
(5, 'Novel', 19.99, 'novel.jpg', 'Bestselling novel', 0.00, 200, 'active', 4, '2024-10-21 11:16:52', '2024-10-21 11:16:52'),
(26, 'Smartphone', 299.99, 'smartphone.jpg', 'Latest smartphone with great features', 10.00, 50, 'active', 1, '2024-10-21 11:15:33', '2024-10-21 11:15:33'),
(27, 'Laptop', 999.99, 'laptop.jpg', 'High-performance laptop', 15.00, 20, 'active', 1, '2024-10-21 11:15:33', '2024-10-21 11:15:33'),
(28, 'Jacket', 79.99, 'jacket.jpg', 'Stylish winter jacket', 5.00, 100, 'active', 2, '2024-10-21 11:15:33', '2024-10-21 11:15:33'),
(29, 'Microwave Oven', 150.00, 'microwave.jpg', 'High-efficiency microwave oven', 20.00, 30, 'inactive', 3, '2024-10-21 11:15:33', '2024-10-21 11:15:33'),
(30, 'Novel', 19.99, 'novel.jpg', 'Bestselling novel', 0.00, 200, 'active', 4, '2024-10-21 11:15:33', '2024-10-21 11:15:33');

-- --------------------------------------------------------

--
-- Table structure for table `productsubimages`
--

CREATE TABLE `productsubimages` (
  `id` int NOT NULL,
  `product_id` int NOT NULL,
  `image` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `productsubimages`
--

INSERT INTO `productsubimages` (`id`, `product_id`, `image`, `created_at`, `updated_at`) VALUES
(1, 1, 'smartphone_side.jpg', '2024-10-21 11:19:32', '2024-10-21 11:19:32'),
(2, 2, 'laptop_back.jpg', '2024-10-21 11:19:32', '2024-10-21 11:19:32'),
(3, 3, 'jacket_detail.jpg', '2024-10-21 11:19:32', '2024-10-21 11:19:32'),
(4, 4, 'microwave_inside.jpg', '2024-10-21 11:19:32', '2024-10-21 11:19:32'),
(5, 5, 'novel_cover.jpg', '2024-10-21 11:19:32', '2024-10-21 11:19:32');

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `id` int NOT NULL,
  `product_id` int NOT NULL,
  `user_id` int NOT NULL,
  `rating` tinyint DEFAULT NULL,
  `reviews_text` text COLLATE utf8mb4_general_ci,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ;

--
-- Dumping data for table `reviews`
--

INSERT INTO `reviews` (`id`, `product_id`, `user_id`, `rating`, `reviews_text`, `created_at`, `updated_at`) VALUES
(6, 1, 1, 5, 'Great product! Totally worth the price.', '2024-10-21 11:17:28', '2024-10-21 11:17:28'),
(7, 2, 2, 4, 'Very good performance but slightly overpriced.', '2024-10-21 11:17:28', '2024-10-21 11:17:28'),
(8, 3, 3, 3, 'Average product, not bad.', '2024-10-21 11:17:28', '2024-10-21 11:17:28'),
(9, 4, 4, 4, 'Good product but delivery was slow.', '2024-10-21 11:17:28', '2024-10-21 11:17:28'),
(10, 5, 5, 5, 'Loved this book, highly recommend it!', '2024-10-21 11:17:28', '2024-10-21 11:17:28');

-- --------------------------------------------------------

--
-- Table structure for table `shippings`
--

CREATE TABLE `shippings` (
  `id` int NOT NULL,
  `order_id` int NOT NULL,
  `shipping_method` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `shipping_cost` decimal(10,2) NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shippings`
--

INSERT INTO `shippings` (`id`, `order_id`, `shipping_method`, `shipping_cost`, `created_at`, `updated_at`) VALUES
(1, 1, 'Standard', 10.00, '2024-10-21 11:19:24', '2024-10-21 11:19:24'),
(2, 2, 'Express', 15.00, '2024-10-21 11:19:24', '2024-10-21 11:19:24'),
(3, 3, 'Standard', 5.00, '2024-10-21 11:19:24', '2024-10-21 11:19:24'),
(4, 4, 'Overnight', 20.00, '2024-10-21 11:19:24', '2024-10-21 11:19:24'),
(5, 5, 'Standard', 0.00, '2024-10-21 11:19:24', '2024-10-21 11:19:24');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `fullname` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `phone_number` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf8mb4_general_ci DEFAULT 'user',
  `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `status` enum('active','inactive') COLLATE utf8mb4_general_ci DEFAULT 'active',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `fullname`, `password`, `phone_number`, `role`, `email`, `status`, `created_at`, `updated_at`) VALUES
(1, 'Nguyen Van A', 'password123', '0123456789', 'customer', 'a@gmail.com', 'active', '2024-10-21 11:09:24', '2024-10-21 11:09:24'),
(2, 'Le Thi B', 'password456', '0987654321', 'admin', 'b@gmail.com', 'active', '2024-10-21 11:09:24', '2024-10-21 11:09:24'),
(3, 'Tran Van C', 'password789', '0111222333', 'customer', 'c@gmail.com', 'inactive', '2024-10-21 11:09:24', '2024-10-21 11:09:24'),
(4, 'Hoang Thi D', 'passwordabc', '0345566778', 'seller', 'd@gmail.com', 'active', '2024-10-21 11:09:24', '2024-10-21 11:09:24'),
(5, 'Pham Van E', 'passwordxyz', '0567890123', 'customer', 'e@gmail.com', 'active', '2024-10-21 11:09:24', '2024-10-21 11:09:24'),
(16, 'Nguyen Van A', 'password123', '0123456789', 'customer', 'nguyenvana@gmail.com', 'active', '2024-10-21 11:10:39', '2024-10-21 11:10:39'),
(17, 'Le Thi B', 'password456', '0987654321', 'admin', 'lethib@gmail.com', 'active', '2024-10-21 11:10:39', '2024-10-21 11:10:39'),
(18, 'Tran Van C', 'password789', '0111222333', 'customer', 'tranvanc@gmail.com', 'inactive', '2024-10-21 11:10:39', '2024-10-21 11:10:39'),
(19, 'Hoang Thi D', 'passwordabc', '0345566778', 'seller', 'hoangthid@gmail.com', 'active', '2024-10-21 11:10:39', '2024-10-21 11:10:39'),
(20, 'Pham Van E', 'passwordxyz', '0567890123', 'customer', 'phamvane@gmail.com', 'active', '2024-10-21 11:10:39', '2024-10-21 11:10:39');

-- --------------------------------------------------------



--
-- Table structure for table `vouchers`
--

CREATE TABLE `vouchers` (
  `id` int NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `discount_percent` decimal(5,2) DEFAULT NULL,
  `status` enum('active','inactive') COLLATE utf8mb4_general_ci DEFAULT 'active',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vouchers`
--

INSERT INTO `vouchers` (`id`, `price`, `discount_percent`, `status`, `created_at`, `updated_at`) VALUES
(1, 50.00, 10.00, 'active', '2024-10-21 11:18:11', '2024-10-21 11:18:11'),
(2, 30.00, 5.00, 'active', '2024-10-21 11:18:11', '2024-10-21 11:18:11'),
(3, 20.00, 15.00, 'inactive', '2024-10-21 11:18:11', '2024-10-21 11:18:11'),
(4, 100.00, 20.00, 'active', '2024-10-21 11:18:11', '2024-10-21 11:18:11'),
(5, 75.00, 25.00, 'active', '2024-10-21 11:18:11', '2024-10-21 11:18:11');

-- --------------------------------------------------------

--
-- Table structure for table `wishlistitems`
--

CREATE TABLE `wishlistitems` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `wishlistitems`
--

INSERT INTO `wishlistitems` (`id`, `user_id`, `product_id`, `created_at`, `updated_at`) VALUES
(1, 1, 1, '2024-10-21 11:18:22', '2024-10-21 11:18:22'),
(2, 2, 2, '2024-10-21 11:18:22', '2024-10-21 11:18:22'),
(3, 3, 3, '2024-10-21 11:18:22', '2024-10-21 11:18:22'),
(4, 4, 4, '2024-10-21 11:18:22', '2024-10-21 11:18:22'),
(5, 5, 5, '2024-10-21 11:18:22', '2024-10-21 11:18:22'),
(6, 1, 1, '2024-10-21 11:18:31', '2024-10-21 11:18:31'),
(7, 2, 2, '2024-10-21 11:18:31', '2024-10-21 11:18:31'),
(8, 3, 3, '2024-10-21 11:18:31', '2024-10-21 11:18:31'),
(9, 4, 4, '2024-10-21 11:18:31', '2024-10-21 11:18:31'),
(10, 5, 5, '2024-10-21 11:18:31', '2024-10-21 11:18:31');







--
-- Indexes for dumped tables
--

--
-- Indexes for table `cartitems`
--
ALTER TABLE `cartitems`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`),
  ADD KEY `parent_categoryID` (`parent_categoryID`);

--
-- Indexes for table `contactmessages`
--
ALTER TABLE `contactmessages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `orderdetails`
--
ALTER TABLE `orderdetails`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `auth_id` (`auth_id`),
  ADD KEY `post_category_id` (`post_category_id`);

--
-- Indexes for table `post_categories`
--
ALTER TABLE `post_categories`
  ADD PRIMARY KEY (`id`),
  ADD KEY `parentCategoryID` (`parentCategoryID`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `categories_id` (`categories_id`);

--
-- Indexes for table `productsubimages`
--
ALTER TABLE `productsubimages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `shippings`
--
ALTER TABLE `shippings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `vouchers`
--
ALTER TABLE `vouchers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `wishlistitems`
--
ALTER TABLE `wishlistitems`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cartitems`
--
ALTER TABLE `cartitems`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `contactmessages`
--
ALTER TABLE `contactmessages`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `orderdetails`
--
ALTER TABLE `orderdetails`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `posts`
--
ALTER TABLE `posts`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `post_categories`
--
ALTER TABLE `post_categories`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `productsubimages`
--
ALTER TABLE `productsubimages`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `shippings`
--
ALTER TABLE `shippings`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `vouchers`
--
ALTER TABLE `vouchers`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `wishlistitems`
--
ALTER TABLE `wishlistitems`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cartitems`
--
ALTER TABLE `cartitems`
  ADD CONSTRAINT `cartitems_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `cartitems_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `categories`
--
ALTER TABLE `categories`
  ADD CONSTRAINT `categories_ibfk_1` FOREIGN KEY (`parent_categoryID`) REFERENCES `categories` (`id`);

--
-- Constraints for table `contactmessages`
--
ALTER TABLE `contactmessages`
  ADD CONSTRAINT `contactmessages_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `orderdetails`
--
ALTER TABLE `orderdetails`
  ADD CONSTRAINT `orderdetails_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `orderdetails_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `posts`
--
ALTER TABLE `posts`
  ADD CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`auth_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `posts_ibfk_2` FOREIGN KEY (`post_category_id`) REFERENCES `post_categories` (`id`);

--
-- Constraints for table `post_categories`
--
ALTER TABLE `post_categories`
  ADD CONSTRAINT `post_categories_ibfk_1` FOREIGN KEY (`parentCategoryID`) REFERENCES `post_categories` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`categories_id`) REFERENCES `categories` (`id`);

--
-- Constraints for table `productsubimages`
--
ALTER TABLE `productsubimages`
  ADD CONSTRAINT `productsubimages_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `shippings`
--
ALTER TABLE `shippings`
  ADD CONSTRAINT `shippings_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

--
-- Constraints for table `wishlistitems`
--
ALTER TABLE `wishlistitems`
  ADD CONSTRAINT `wishlistitems_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `wishlistitems_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
