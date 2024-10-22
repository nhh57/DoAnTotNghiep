const db = require('../config/db');

exports.getAllProducts = (req, res) => {
    const sql = 'SELECT * FROM products';
    db.query(sql, (err, results) => {
        if (err) {
            return res.status(500).json({ message: 'Error retrieving products', error: err });
        }
        res.json({ 
            message: 'Products retrieved successfully', 
            products: results 
        });
    });
};

exports.getProductById = (req, res) => {
    const { id } = req.params;
    const sql = 'SELECT * FROM products WHERE ProductID = ?';
    
    db.query(sql, [id], (err, results) => {
        if (err) {
            return res.status(500).json({ message: 'Error retrieving product', error: err });
        }
        if (results.length === 0) {
            return res.status(404).json({ message: 'Product not found' });
        }
        res.json({ 
            message: 'Product by id successfully', 
            product: results[0] 
        });
    });
};

exports.createProduct = (req, res) => {
    const { ProductName, Description, Price, Quantity, Discount, CategoryID, SupplierID } = req.body;
    const sql = 'INSERT INTO products (ProductName, Description, Price, Quantity, Discount, CategoryID, SupplierID) VALUES (?, ?, ?, ?, ?, ?, ?)';
    
    db.query(sql, [ProductName, Description, Price, Quantity, Discount, CategoryID, SupplierID], (err, results) => {
        if (err) {
            return res.status(500).json({ message: 'Error creating product', error: err });
        }
        res.status(201).json({ 
            message: 'Product created successfully', 
            ProductID: results.insertId, 
            ProductName, 
            Description, 
            Price, 
            Quantity, 
            Discount, 
            CategoryID, 
            SupplierID 
        });
    });
};

exports.updateProduct = (req, res) => {
    const { id } = req.params;
    const { ProductName, Description, Price, Quantity, Discount, CategoryID, SupplierID } = req.body;
    const sql = 'UPDATE products SET ProductName = ?, Description = ?, Price = ?, Quantity = ?, Discount = ?, CategoryID = ?, SupplierID = ? WHERE ProductID = ?';
    
    db.query(sql, [ProductName, Description, Price, Quantity, Discount, CategoryID, SupplierID, id], (err) => {
        if (err) {
            return res.status(500).json({ message: 'Error updating product', error: err });
        }
        res.json({ 
            message: 'Product updated successfully', 
            ProductID: id, 
            ProductName, 
            Description, 
            Price, 
            Quantity, 
            Discount, 
            CategoryID, 
            SupplierID 
        });
    });
};

exports.deleteProduct = (req, res) => {
    const { id } = req.params;
    const sql = 'DELETE FROM products WHERE ProductID = ?';
    
    db.query(sql, [id], (err) => {
        if (err) {
            return res.status(500).json({ message: 'Error deleting product', error: err });
        }
        res.status(200).json({ message: 'Product deleted successfully' });
    });
};
