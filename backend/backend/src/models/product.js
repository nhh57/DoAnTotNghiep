class Product {
    constructor(ProductID, ProductName, Description, Price, Quantity, Discount, CategoryID, SupplierID) {
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Description = Description;
        this.Price = Price;
        this.Quantity = Quantity;
        this.Discount = Discount;
        this.CategoryID = CategoryID;
        this.SupplierID = SupplierID;
    }
}

module.exports = Product;
