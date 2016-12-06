package com.j1j2.jposmvvm.data.model;

/**
 * Created by alienzxh on 16-6-27.
 */
public class ProductPriceLabelOrder {

    private int OrderId;
    private int ShopId;
    private int OrderType;
    private String OrderTypeStr;//case 1:temp = "采集终端";case 2:temp = "JPOS电脑终端";case 4:temp = "JPOS手机终端";
    private String SubmitTimeStr;
    private int SubmitClerkId;
    private int State;
    private int LabelCount;
    private String Note;

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public int getOrderType() {
        return OrderType;
    }

    public void setOrderType(int orderType) {
        OrderType = orderType;
    }

    public String getOrderTypeStr() {
        return OrderTypeStr;
    }

    public void setOrderTypeStr(String orderTypeStr) {
        OrderTypeStr = orderTypeStr;
    }

    public String getSubmitTimeStr() {
        return SubmitTimeStr;
    }

    public void setSubmitTimeStr(String submitTimeStr) {
        SubmitTimeStr = submitTimeStr;
    }

    public int getSubmitClerkId() {
        return SubmitClerkId;
    }

    public void setSubmitClerkId(int submitClerkId) {
        SubmitClerkId = submitClerkId;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public int getLabelCount() {
        return LabelCount;
    }

    public void setLabelCount(int labelCount) {
        LabelCount = labelCount;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
