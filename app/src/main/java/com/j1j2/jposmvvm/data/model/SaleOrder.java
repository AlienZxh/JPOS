package com.j1j2.jposmvvm.data.model;

/**
 * Created by alienzxh on 16-8-15.
 */
public class SaleOrder {


    /**
     * OrderID : 1
     * OrderNo : sample string 2
     * Amount : 3.0
     * SubmitTime : 2016-08-15T14:38:47.1290748+08:00
     * UserMobile : sample string 5
     * UserName : sample string 6
     * ClerkName : sample string 7
     * OrderState : 1
     */


    private int OrderID;
    private String OrderNo;
    private double Amount;
    private String SubmitTime;
    private String UserMobile;
    private String UserName;
    private String ClerkName;
    private int OrderState;//提交：１　结账：２　取消：４

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String OrderNo) {
        this.OrderNo = OrderNo;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public String getSubmitTime() {
        return SubmitTime;
    }

    public void setSubmitTime(String SubmitTime) {
        this.SubmitTime = SubmitTime;
    }

    public String getUserMobile() {
        return UserMobile;
    }

    public void setUserMobile(String UserMobile) {
        this.UserMobile = UserMobile;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getClerkName() {
        return ClerkName;
    }

    public void setClerkName(String ClerkName) {
        this.ClerkName = ClerkName;
    }

    public int getOrderState() {
        return OrderState;
    }

    public void setOrderState(int OrderState) {
        this.OrderState = OrderState;
    }
}
