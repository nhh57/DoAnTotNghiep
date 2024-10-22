const express = require('express');
const router = express.Router();
const productController = require('../controllers/productController');
const asyncHandler = require("../helpers/asyncHandler");
const accessController = require("../controllers/authController");
const  {authenticationV2}= require("../auth/auth.Utils");
// Lấy danh sách sản phẩm
router.get('/products', productController.getAllProducts);

// Lấy sản phẩm theo ID
router.get('/products/:id', productController.getProductById);

// Thêm sản phẩm mới
router.post('/products', productController.createProduct);

// Cập nhật sản phẩm
router.put('/products/:id', productController.updateProduct);

// Xóa sản phẩm
router.delete('/products/:id', productController.deleteProduct);


router.post('/signup', asyncHandler(accessController.signUp))

router.post('/login', asyncHandler(accessController.login))
router.use(authenticationV2)
router.post('/handler-refresh-token', asyncHandler(accessController.handlerRefreshToken))

module.exports = router;
