package com.j1j2.jposmvvm.data.model;

/**
 * Created by alienzxh on 16-6-7.
 */
public class Category {
    private String ParentCategoryName;
    private String CategoryName;
    private int ProductCount;

    public String getParentCategoryName() {
        return ParentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        ParentCategoryName = parentCategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    public void setProductCount(int ProductCount) {
        this.ProductCount = ProductCount;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public int getProductCount() {
        return ProductCount;
    }
}
