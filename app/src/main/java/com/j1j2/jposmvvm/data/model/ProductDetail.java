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
    private double LastCost;
    private String Brand;
    private double MemberPrice;
    private double RetailPrice;
    private int ShopId;
    private String SmallImgUrl;
    private String ThumbImgUrl;
    private ArrayList<StockImg> StockImgs;

    public int getStockId() {
        return StockId;
    }

    public void setStockId(int StockId) {
        this.StockId = StockId;
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

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String BarCode) {
        this.BarCode = BarCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSpec() {
        return Spec;
    }

    public void setSpec(String Spec) {
        this.Spec = Spec;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public String getMnemonic() {
        return Mnemonic;
    }

    public void setMnemonic(String Mnemonic) {
        this.Mnemonic = Mnemonic;
    }

    public double getLastCost() {
        return LastCost;
    }

    public void setLastCost(double LastCost) {
        this.LastCost = LastCost;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String Brand) {
        this.Brand = Brand;
    }

    public double getMemberPrice() {
        return MemberPrice;
    }

    public void setMemberPrice(double MemberPrice) {
        this.MemberPrice = MemberPrice;
    }

    public double getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(double RetailPrice) {
        this.RetailPrice = RetailPrice;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int ShopId) {
        this.ShopId = ShopId;
    }

    public String getSmallImgUrl() {
        return SmallImgUrl;
    }

    public void setSmallImgUrl(String SmallImgUrl) {
        this.SmallImgUrl = SmallImgUrl;
    }

    public String getThumbImgUrl() {
        return ThumbImgUrl;
    }

    public void setThumbImgUrl(String ThumbImgUrl) {
        this.ThumbImgUrl = ThumbImgUrl;
    }

    public ArrayList<StockImg> getStockImgs() {
        return StockImgs;
    }

    public void setStockImgs(ArrayList<StockImg> stockImgs) {
        StockImgs = stockImgs;
    }

    public String getProductRetailPriceRate() {
        if (LastCost <= 0) {
            return "";
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            return decimalFormat.format(((RetailPrice - LastCost) / RetailPrice) * 100) + "%";
        }
    }

    public String getProductMemberPriceRate() {
        if (LastCost <= 0) {
            return "";
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            return decimalFormat.format(((MemberPrice - LastCost) / MemberPrice) * 100) + "%";
        }
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
