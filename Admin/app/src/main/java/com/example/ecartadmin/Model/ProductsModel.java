package com.example.ecartadmin.Model;

public class ProductsModel {

    private String ProductUId,ProductName,ProductDiscountPrice,ProductPrice,ProductDiscount,ProductCategories,ProductQuantity,ProductDescription,ProductImage;

    public ProductsModel(String productUId, String productName, String productDiscountPrice, String productPrice, String productDiscount, String productCategories, String productQuantity, String productDescription, String productImage) {
        ProductUId = productUId;
        ProductName = productName;
        ProductDiscountPrice = productDiscountPrice;
        ProductPrice = productPrice;
        ProductDiscount = productDiscount;
        ProductCategories = productCategories;
        ProductQuantity = productQuantity;
        ProductDescription = productDescription;
        ProductImage = productImage;
    }

    public ProductsModel() {
    }

    public String getProductUId() {
        return ProductUId;
    }

    public void setProductUId(String productUId) {
        ProductUId = productUId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductDiscountPrice() {
        return ProductDiscountPrice;
    }

    public void setProductDiscountPrice(String productDiscountPrice) {
        ProductDiscountPrice = productDiscountPrice;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductDiscount() {
        return ProductDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        ProductDiscount = productDiscount;
    }

    public String getProductCategories() {
        return ProductCategories;
    }

    public void setProductCategories(String productCategories) {
        ProductCategories = productCategories;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    @Override
    public String toString() {
        return "ProductsModel{" +
                "ProductUId='" + ProductUId + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", ProductDiscountPrice='" + ProductDiscountPrice + '\'' +
                ", ProductPrice='" + ProductPrice + '\'' +
                ", ProductDiscount='" + ProductDiscount + '\'' +
                ", ProductCategories='" + ProductCategories + '\'' +
                ", ProductQuantity='" + ProductQuantity + '\'' +
                ", ProductDescription='" + ProductDescription + '\'' +
                ", ProductImage='" + ProductImage + '\'' +
                '}';
    }
}
