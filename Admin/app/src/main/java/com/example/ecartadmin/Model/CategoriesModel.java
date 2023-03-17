package com.example.ecartadmin.Model;

public class CategoriesModel {
    private String CategoryName,CategoryImage,CategoresUID;

    public CategoriesModel(String categoryName, String categoryImage, String categoresUID) {
        CategoryName = categoryName;
        CategoryImage = categoryImage;
        CategoresUID = categoresUID;
    }

    public CategoriesModel() {
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryImage() {
        return CategoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        CategoryImage = categoryImage;
    }

    public String getCategoresUID() {
        return CategoresUID;
    }

    public void setCategoresUID(String categoresUID) {
        CategoresUID = categoresUID;
    }

    @Override
    public String toString() {
        return "CategoriesModel{" +
                "CategoryName='" + CategoryName + '\'' +
                ", CategoryImage='" + CategoryImage + '\'' +
                ", CategoresUID='" + CategoresUID + '\'' +
                '}';
    }
}
