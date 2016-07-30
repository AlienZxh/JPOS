package com.j1j2.jposmvvm.data.model;

/**
 * Created by alienzxh on 16-7-27.
 */
public class StorageStock {


    /**
     * StockId : 1
     * ShopId : 2
     * ProductId : 1
     * Name : sample string 3
     * BarCode : sample string 4
     * Spec : sample string 5
     * Unit : sample string 6
     * LastCost : 1.0
     * Quantity : 7.0
     * ItemId : 1
     * Img : sample string 8
     * SmallImgUrl : sample string 9
     */

    private int StockId;
    private int ShopId;
    private int ProductId;
    private String Name;
    private String BarCode;
    private String Spec;
    private String Unit;
    private double LastCost;
    private double Quantity;
    private int ItemId;
    private String Img;
    private String SmallImgUrl;

    public int getStockId() {
        return StockId;
    }

    public void setStockId(int StockId) {
        this.StockId = StockId;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int ShopId) {
        this.ShopId = ShopId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int ProductId) {
        this.ProductId = ProductId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String BarCode) {
        this.BarCode = BarCode;
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

    public double getLastCost() {
        return LastCost;
    }

    public void setLastCost(double LastCost) {
        this.LastCost = LastCost;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double Quantity) {
        this.Quantity = Quantity;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int ItemId) {
        this.ItemId = ItemId;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String Img) {
        this.Img = Img;
    }

    public String getSmallImgUrl() {
        return SmallImgUrl;
    }

    public void setSmallImgUrl(String SmallImgUrl) {
        this.SmallImgUrl = SmallImgUrl;
    }
}
