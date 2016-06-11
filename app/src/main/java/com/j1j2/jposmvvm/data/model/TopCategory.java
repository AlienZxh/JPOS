package com.j1j2.jposmvvm.data.model;

import java.util.List;

/**
 * Created by 兴昊 on 2016/1/26.
 */
public class TopCategory {

    /**
     * TopCategory : sample string 1
     * SubCategories : [{"CategoryName":"sample string 1","ProductCount":2},{"CategoryName":"sample string 1","ProductCount":2},{"CategoryName":"sample string 1","ProductCount":2}]
     */

    private String TopCategory;
    /**
     * CategoryName : sample string 1
     * ProductCount : 2
     */

    private List<SubCategory> SubCategories;

    public void setTopCategory(String TopCategory) {
        this.TopCategory = TopCategory;
    }

    public void setSubCategories(List<SubCategory> SubCategories) {
        this.SubCategories = SubCategories;
    }

    public String getTopCategory() {
        return TopCategory;
    }

    public List<SubCategory> getSubCategories() {
        return SubCategories;
    }


}
