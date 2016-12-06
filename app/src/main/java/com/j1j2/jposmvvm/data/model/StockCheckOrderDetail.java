package com.j1j2.jposmvvm.data.model;

/**
 * Created by alienzxh on 16-2-15.
 */
public class StockCheckOrderDetail {


    /**
     * CheckDetailID : 1
     * StockCheckOrderId : 1
     * BarCode : sample string 2
     * Quantity : 3.0
     * StockId : 4
     * DeltaCount : 5.0
     * Spec : sample string 6
     * ProductId : 1
     * StockName : sample string 7
     * Unit : sample string 8
     * CostPrice : 1.0
     * CheckTime : 2016-02-15T17:11:46.2392957+08:00
     */

    private int CheckDetailID;
    private int StockCheckOrderId;
    private String BarCode;
    private double Quantity;
    private int StockId;
    private double DeltaCount;
    private String Spec;
    private int ProductId;
    private String StockName;
    private String Unit;
    private double CostPrice;
    private String CheckTime;

    public void setCheckDetailID(int CheckDetailID) {
        this.CheckDetailID = CheckDetailID;
    }

    public void setStockCheckOrderId(int StockCheckOrderId) {
        this.StockCheckOrderId = StockCheckOrderId;
    }

    public void setBarCode(String BarCode) {
        this.BarCode = BarCode;
    }

    public void setQuantity(double Quantity) {
        this.Quantity = Quantity;
    }

    public void setStockId(int StockId) {
        this.StockId = StockId;
    }

    public void setDeltaCount(double DeltaCount) {
        this.DeltaCount = DeltaCount;
    }

    public void setSpec(String Spec) {
        this.Spec = Spec;
    }

    public void setProductId(int ProductId) {
        this.ProductId = ProductId;
    }

    public void setStockName(String StockName) {
        this.StockName = StockName;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public void setCostPrice(double CostPrice) {
        this.CostPrice = CostPrice;
    }

    public void setCheckTime(String CheckTime) {
        this.CheckTime = CheckTime;
    }

    public int getCheckDetailID() {
        return CheckDetailID;
    }

    public int getStockCheckOrderId() {
        return StockCheckOrderId;
    }

    public String getBarCode() {
        return BarCode;
    }

    public double getQuantity() {
        return Quantity;
    }

    public int getStockId() {
        return StockId;
    }

    public double getDeltaCount() {
        return DeltaCount;
    }

    public String getSpec() {
        return Spec;
    }

    public int getProductId() {
        return ProductId;
    }

    public String getStockName() {
        return StockName;
    }

    public String getUnit() {
        return Unit;
    }

    public double getCostPrice() {
        return CostPrice;
    }

    public String getCheckTime() {
        return CheckTime;
    }
}
