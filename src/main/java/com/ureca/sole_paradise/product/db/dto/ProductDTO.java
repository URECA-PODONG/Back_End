package com.ureca.sole_paradise.product.db.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;

public class ProductDTO {

    private Integer productId;

    @NotNull
    @Size(max = 255)
    private String productTitle;

    @Size(max = 255)
    private String productLink;

    @Size(max = 255)
    private String productImage;

    @NotNull
    private Integer productLprice;

    private Integer productHprice;

    @Size(max = 255)
    private String productMallName;

    private Integer productType;

    @Size(max = 255)
    private String productBrand;

    @Size(max = 255)
    private String productMaker;

    @Size(max = 50)
    private String productCategory1;

    @Size(max = 50)
    private String productCategory2;

    @Size(max = 50)
    private String productCategory3;

    @Size(max = 50)
    private String productCategory4;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(final Integer productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(final String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductLink() {
        return productLink;
    }

    public void setProductLink(final String productLink) {
        this.productLink = productLink;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(final String productImage) {
        this.productImage = productImage;
    }

    public Integer getProductLprice() {
        return productLprice;
    }

    public void setProductLprice(final Integer productLprice) {
        this.productLprice = productLprice;
    }

    public Integer getProductHprice() {
        return productHprice;
    }

    public void setProductHprice(final Integer productHprice) {
        this.productHprice = productHprice;
    }

    public String getProductMallName() {
        return productMallName;
    }

    public void setProductMallName(final String productMallName) {
        this.productMallName = productMallName;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(final Integer productType) {
        this.productType = productType;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(final String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductMaker() {
        return productMaker;
    }

    public void setProductMaker(final String productMaker) {
        this.productMaker = productMaker;
    }

    public String getProductCategory1() {
        return productCategory1;
    }

    public void setProductCategory1(final String productCategory1) {
        this.productCategory1 = productCategory1;
    }

    public String getProductCategory2() {
        return productCategory2;
    }

    public void setProductCategory2(final String productCategory2) {
        this.productCategory2 = productCategory2;
    }

    public String getProductCategory3() {
        return productCategory3;
    }

    public void setProductCategory3(final String productCategory3) {
        this.productCategory3 = productCategory3;
    }

    public String getProductCategory4() {
        return productCategory4;
    }

    public void setProductCategory4(final String productCategory4) {
        this.productCategory4 = productCategory4;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
