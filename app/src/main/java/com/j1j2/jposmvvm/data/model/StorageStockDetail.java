package com.j1j2.jposmvvm.data.model;

/**
 * Created by alienzxh on 16-7-15.
 */
public class StorageStockDetail {


    /**
     * ProductName : sample string 1
     * Unit : sample string 2
     * BarCode : sample string 3
     * ItemId : 4
     * StockId : 5
     * OrderId : 6
     * Quantity : 7.0
     * Price : 8.0
     * Spec : sample string 9
     * Img : sample string 10
     */

    private String ProductName;
    private String Unit;
    private String BarCode;
    private int ItemId;
    private int StockId;
    private int OrderId;
    private double Quantity;
    private double Price;
    private String Spec;
    private String Img;

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String BarCode) {
        this.BarCode = BarCode;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int ItemId) {
        this.ItemId = ItemId;
    }

    public int getStockId() {
        return StockId;
    }

    public void setStockId(int StockId) {
        this.StockId = StockId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int OrderId) {
        this.OrderId = OrderId;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double Quantity) {
        this.Quantity = Quantity;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public String getSpec() {
        return Spec;
    }

    public void setSpec(String Spec) {
        this.Spec = Spec;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String Img) {
        this.Img = Img;
    }


}
