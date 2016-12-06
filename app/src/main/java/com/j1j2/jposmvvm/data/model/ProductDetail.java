package com.j1j2.jposmvvm.data.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 兴昊 on 2016/1/12.
 */
public class ProductDetail {

    /**
     * StockId : 1
     * BarCode : sample string 2
     * Name : sample string 3
     * Spec : sample string 4
     * Unit : sample string 5
     * Mnemonic : sample string 6
     * LastCost : 1.0
     * Brand : sample string 7
     * MemberPrice : 1.0
     * RetailPrice : 1.0
     * ShopId : 8
     * SmallImgUrl : sample string 9
     * ThumbImgUrl : sample string 10
     * StockImgs : [{"ImgId":1,"ShopId":2,"StockId":3,"Name":"sample string 4","OriginalPath":"sample string 5","OptimizePath":"sample string 6","NormalUrl":"sample string 7","SmallUrl":"sample string 8","ThumbUrl":"sample string 9","Optimized":true,"SubmitTime":"2016-05-24T09:46:10.1024523+08:00","OptimizeTime":"2016-05-24T09:46:10.1024523+08:00"},{"ImgId":1,"ShopId":2,"StockId":3,"Name":"sample string 4","OriginalPath":"sample string 5","OptimizePath":"sample string 6","NormalUrl":"sample string 7","SmallUrl":"sample string 8","ThumbUrl":"sample string 9","Optimized":true,"SubmitTime":"2016-05-24T09:46:10.1024523+08:00","OptimizeTime":"2016-05-24T09:46:10.1024523+08:00"},{"ImgId":1,"ShopId":2,"StockId":3,"Name":"sample string 4","OriginalPath":"sample string 5","OptimizePath":"sample string 6","NormalUrl":"sample string 7","SmallUrl":"sample string 8","ThumbUrl":"sample string 9","Optimized":true,"SubmitTime":"2016-05-24T09:46:10.1024523+08:00","OptimizeTime":"2016-05-24T09:46:10.1024523+08:00"}]
     */

    private int StockId;
    private String BarCode;
    private String Category;
    private String SubCategory;
    private String Name;
    private String Spec;
    private String Unit;
    private String Mnemonic;
    private String Brand;
    private Double LastCost;
    private Double MemberPrice;
    private Double RetailPrice;
    private int ShopId;
    private String SmallImgUrl;
    private String ThumbImgUrl;
    private ArrayList<StockImg> StockImgs;

    public int getStockId() {
        return StockId;
    }

    public void setStockId(int stockId) {
        StockId = stockId;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSubCategory() {
        return SubCategory;
    }

    public void setSubCategory(String subCategory) {
        SubCategory = subCategory;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSpec() {
        return Spec;
    }

    public void setSpec(String spec) {
        Spec = spec;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getMnemonic() {
        return Mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        Mnemonic = mnemonic;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public Double getLastCost() {
        return LastCost;
    }

    public void setLastCost(Double lastCost) {
        LastCost = lastCost;
    }

    public Double getMemberPrice() {
        return MemberPrice;
    }

    public void setMemberPrice(Double memberPrice) {
        MemberPrice = memberPrice;
    }

    public Double getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        RetailPrice = retailPrice;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getSmallImgUrl() {
        return SmallImgUrl;
    }

    public void setSmallImgUrl(String smallImgUrl) {
        SmallImgUrl = smallImgUrl;
    }

    public String getThumbImgUrl() {
        return ThumbImgUrl;
    }

    public void setThumbImgUrl(String thumbImgUrl) {
        ThumbImgUrl = thumbImgUrl;
    }

    public ArrayList<StockImg> getStockImgs() {
        return StockImgs;
    }

    public void setStockImgs(ArrayList<StockImg> stockImgs) {
        StockImgs = stockImgs;
    }

    public String getProductRetailPriceRate() {
        if (LastCost != null && RetailPrice != null)
            if (LastCost <= 0) {
                return "";
            } else {
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                return decimalFormat.format(((RetailPrice - LastCost) / RetailPrice) * 100) + "%";
            }
        else
            return "";
    }

    public String getProductMemberPriceRate() {
        if (MemberPrice != null && LastCost != null)
            if (LastCost <= 0) {
                return "";
            } else {
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                return decimalFormat.format(((MemberPrice - LastCost) / MemberPrice) * 100) + "%";
            }
        else
            return "";
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "StockId=" + StockId +
                ", BarCode='" + BarCode + '\'' +
                ", Category='" + Category + '\'' +
                ", SubCategory='" + SubCategory + '\'' +
                ", Name='" + Name + '\'' +
                ", Spec='" + Spec + '\'' +
                ", Unit='" + Unit + '\'' +
                ", Mnemonic='" + Mnemonic + '\'' +
                ", LastCost=" + LastCost +
                ", Brand='" + Brand + '\'' +
                ", MemberPrice=" + MemberPrice +
                ", RetailPrice=" + RetailPrice +
                ", ShopId=" + ShopId +
                ", SmallImgUrl='" + SmallImgUrl + '\'' +
                ", ThumbImgUrl='" + ThumbImgUrl + '\'' +
                ", StockImgs=" + StockImgs +
                '}';
    }
}
