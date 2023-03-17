package com.example.ecart.ModelClass;

public class CategoriesModel {

    private String CategoryName,CategoryImage;

    public CategoriesModel(String categoryName, String categoryImage) {
        CategoryName = categoryName;
        CategoryImage = categoryImage;
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

    @Override
    public String toString() {
        return "CategoriesModel{" +
                "CategoryName='" + CategoryName + '\'' +
                ", CategoryImage='" + CategoryImage + '\'' +
                '}';
    }
}
