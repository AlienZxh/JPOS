package com.j1j2.jposmvvm.data.model;

/**
 * Created by alienzxh on 16-5-23.
 */
public class SubCategory {
    private String CategoryName;
    private int ProductCount;

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
