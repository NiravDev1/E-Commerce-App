package com.example.ecart.ModelClass;

public class CartModel {
    private String ProductUId,ProductName,CustomerUID,ProductDiscountPrice,ProductPrice,ProductSprice,ProductQuantity,ProductImage;

    public CartModel(String productUId, String productName, String customerUID, String productDiscountPrice, String productPrice, String productSprice, String productQuantity, String productImage) {
        ProductUId = productUId;
        ProductName = productName;
        CustomerUID = customerUID;
        ProductDiscountPrice = productDiscountPrice;
        ProductPrice = productPrice;
        ProductSprice = productSprice;
        ProductQuantity = productQuantity;
        ProductImage = productImage;
    }

    public CartModel() {
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

    public String getCustomerUID() {
        return CustomerUID;
    }

    public void setCustomerUID(String customerUID) {
        CustomerUID = customerUID;
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

    public String getProductSprice() {
        return ProductSprice;
    }

    public void setProductSprice(String productSprice) {
        ProductSprice = productSprice;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }
}
