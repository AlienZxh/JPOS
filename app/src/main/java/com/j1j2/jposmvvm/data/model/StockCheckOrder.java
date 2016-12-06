package com.j1j2.jposmvvm.data.model;

/**
 * Created by 兴昊 on 2016/2/2.
 */
public class StockCheckOrder {

    /**
     * StockCheckOrderId : 15
     * ShopId : 1
     * SubmitClerkID : 1
     * SubmitTime : 2015-08-21T20:32:01.203
     * SubmitTimeStr : 2015-08-21 20:32:01
     * DeltaCount : 0.0
     * DeltaAmount : 2.0909
     * AuditClerkID : 1
     * AuditTime : 2015-08-21T20:32:01.203
     * State : 2   1是未审核  2是已审核
     * StockCheckedCount :
     * StockOrderType : 1是全部   2是部分
     */

    private int StockCheckOrderId;
    private int ShopId;
    private int SubmitClerkID;
    private String SubmitTime;
    private String SubmitTimeStr;
    private double DeltaCount;
    private double DeltaAmount;
    private int AuditClerkID;
    private String AuditTime;
    private int State;
    private int StockCheckedCount;
    private int StockOrderType;

    public void setStockCheckOrderId(int StockCheckOrderId) {
        this.StockCheckOrderId = StockCheckOrderId;
    }

    public void setShopId(int ShopId) {
        this.ShopId = ShopId;
    }

    public void setSubmitClerkID(int SubmitClerkID) {
        this.SubmitClerkID = SubmitClerkID;
    }

    public void setSubmitTime(String SubmitTime) {
        this.SubmitTime = SubmitTime;
    }

    public void setSubmitTimeStr(String SubmitTimeStr) {
        this.SubmitTimeStr = SubmitTimeStr;
    }

    public void setDeltaCount(double DeltaCount) {
        this.DeltaCount = DeltaCount;
    }

    public void setDeltaAmount(double DeltaAmount) {
        this.DeltaAmount = DeltaAmount;
    }

    public void setAuditClerkID(int AuditClerkID) {
        this.AuditClerkID = AuditClerkID;
    }

    public void setAuditTime(String AuditTime) {
        this.AuditTime = AuditTime;
    }

    public void setState(int State) {
        this.State = State;
    }

    public void setStockCheckedCount(int StockCheckedCount) {
        this.StockCheckedCount = StockCheckedCount;
    }

    public void setStockOrderType(int StockOrderType) {
        this.StockOrderType = StockOrderType;
    }

    public int getStockCheckOrderId() {
        return StockCheckOrderId;
    }

    public int getShopId() {
        return ShopId;
    }

    public int getSubmitClerkID() {
        return SubmitClerkID;
    }

    public String getSubmitTime() {
        return SubmitTime;
    }

    public String getSubmitTimeStr() {
        return SubmitTimeStr;
    }

    public double getDeltaCount() {
        return DeltaCount;
    }

    public double getDeltaAmount() {
        return DeltaAmount;
    }

    public int getAuditClerkID() {
        return AuditClerkID;
    }

    public String getAuditTime() {
        return AuditTime;
    }

    public int getState() {
        return State;
    }

    public int getStockCheckedCount() {
        return StockCheckedCount;
    }

    public int getStockOrderType() {
        return StockOrderType;
    }
}
