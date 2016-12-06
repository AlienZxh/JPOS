package com.j1j2.jposmvvm.data.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;

/**
 * Created by alienzxh on 16-8-1.
 */
public class CashPuzzyQueryStock extends RealmObject {
    /**
     * StockId : 1
     * Name : sample string 1
     * BarCode : sample string 2
     * Spec : sample string 3
     * Unit : sample string 4
     * MemberPrice : 1.0
     * RetailPrice : 1.0
     * SmallImgUrl : sample string 5
     */
    private int StockId;
    private String Name;
    private String BarCode;
    private String Spec;
    private String Unit;
    private double MemberPrice;
    private double RetailPrice;
    private String SmallImgUrl;
    @Expose
    private double Quantity;

    public int getStockId() {
        return StockId;
    }

    public void setStockId(int StockId) {
        this.StockId = StockId;
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

    public String getSmallImgUrl() {
        return SmallImgUrl;
    }

    public void setSmallImgUrl(String SmallImgUrl) {
        this.SmallImgUrl = SmallImgUrl;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }


}
