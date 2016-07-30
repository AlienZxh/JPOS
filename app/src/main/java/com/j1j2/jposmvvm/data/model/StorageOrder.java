package com.j1j2.jposmvvm.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alienzxh on 16-7-15.
 */
public class StorageOrder extends RealmObject{


    /**
     * OrderId : 1
     * OrderNO : sample string 2
     * ShopId : 3
     * ClerkId : 4
     * SubmitTime : 2016-07-15T09:41:49.7051594+08:00
     * SubmitTimeStr : 2016-07-15
     * Amount : 6.0
     * OtherAmount : 1.0
     * Invalid : true
     * Remark : sample string 8
     * State : 64
     * SupplerId : 10
     * SupplerName : sample string 11
     */
    @PrimaryKey
    private int OrderId;

    private String OrderNO;
    private int ShopId;
    private int ClerkId;
    private String SubmitTime;
    private String SubmitTimeStr;
    private double Amount;
    private double OtherAmount;
    private boolean Invalid;
    private String Remark;
    private int State; // 1:未审核　２：已审合　　３：作废
    private int SupplerId;
    private String SupplerName;

    private String CreateOrderSubmitTimeStr;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int OrderId) {
        this.OrderId = OrderId;
    }

    public String getOrderNO() {
        return OrderNO;
    }

    public void setOrderNO(String OrderNO) {
        this.OrderNO = OrderNO;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int ShopId) {
        this.ShopId = ShopId;
    }

    public int getClerkId() {
        return ClerkId;
    }

    public void setClerkId(int ClerkId) {
        this.ClerkId = ClerkId;
    }

    public String getSubmitTime() {
        return SubmitTime;
    }

    public void setSubmitTime(String SubmitTime) {
        this.SubmitTime = SubmitTime;
    }

    public String getSubmitTimeStr() {
        return SubmitTimeStr;
    }

    public void setSubmitTimeStr(String SubmitTimeStr) {
        this.SubmitTimeStr = SubmitTimeStr;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public double getOtherAmount() {
        return OtherAmount;
    }

    public void setOtherAmount(double OtherAmount) {
        this.OtherAmount = OtherAmount;
    }

    public boolean isInvalid() {
        return Invalid;
    }

    public void setInvalid(boolean Invalid) {
        this.Invalid = Invalid;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public int getSupplerId() {
        return SupplerId;
    }

    public void setSupplerId(int SupplerId) {
        this.SupplerId = SupplerId;
    }

    public String getSupplerName() {
        return SupplerName;
    }

    public void setSupplerName(String SupplerName) {
        this.SupplerName = SupplerName;
    }

    public String getCreateOrderSubmitTimeStr() {
        return CreateOrderSubmitTimeStr;
    }

    public void setCreateOrderSubmitTimeStr(String createOrderSubmitTimeStr) {
        CreateOrderSubmitTimeStr = createOrderSubmitTimeStr;
    }
}
