package com.example.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product extends BaseEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "product_name", nullable = true, length = -1)
    private String productName;
    @Basic
    @Column(name = "price", nullable = true, precision = 0)
    private Integer price;
    @Basic
    @Column(name = "discount", nullable = true, precision = 0)
    private Integer discount;
    @Basic
    @Column(name = "note", nullable = true, length = -1)
    private String note;
    @Basic
    @Column(name = "images", nullable = true, length = -1)
    private String images;
    @Basic
    @Column(name = "category_id", nullable = true)
    private Integer categoryId;
    @Basic
    @Column(name = "number_of_sale", nullable = true)
    private Integer numberOfSale;
    @Basic
    @Column(name = "brand_id", nullable = true)
    private Integer brandId;

    @Column(name = "warehouse_id", nullable = true)
    private Integer warehouseId;
    @Basic
    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Categories categoriesByCategoryId;
    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Brand brandByBrandId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Warehouse warehouse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getNumberOfSale() {
        return numberOfSale;
    }

    public void setNumberOfSale(Integer numberOfSale) {
        this.numberOfSale = numberOfSale;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Categories getCategoriesByCategoryId() {
        return categoriesByCategoryId;
    }

    public void setCategoriesByCategoryId(Categories categoriesByCategoryId) {
        this.categoriesByCategoryId = categoriesByCategoryId;
    }

    public Brand getBrandByBrandId() {
        return brandByBrandId;
    }

    public void setBrandByBrandId(Brand brandByBrandId) {
        this.brandByBrandId = brandByBrandId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
