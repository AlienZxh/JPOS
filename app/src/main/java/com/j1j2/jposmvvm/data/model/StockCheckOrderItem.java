package com.j1j2.jposmvvm.data.model;

/**
 * Created by alienzxh on 16-2-16.
 */
public class StockCheckOrderItem {

    /**
     * StockId : 1
     * Name : sample string 2
     * Unit : sample string 3
     * Spec : sample string 4
     * BarCode : sample string 5
     * CheckDetailID : 1
     * SysQuantity : 6.0
     * CurrentCheckQuantity : 1.0
     */

    private int StockId;
    private String Name;
    private String Unit;
    private String Spec;
    private String BarCode;
    private int CheckDetailID;
    private double SysQuantity;
    private double CurrentCheckQuantity;

    public void setStockId(int StockId) {
        this.StockId = StockId;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public void setSpec(String Spec) {
        this.Spec = Spec;
    }

    public void setBarCode(String BarCode) {
        this.BarCode = BarCode;
    }

    public void setCheckDetailID(int CheckDetailID) {
        this.CheckDetailID = CheckDetailID;
    }

    public void setSysQuantity(double SysQuantity) {
        this.SysQuantity = SysQuantity;
    }

    public void setCurrentCheckQuantity(double CurrentCheckQuantity) {
        this.CurrentCheckQuantity = CurrentCheckQuantity;
    }

    public int getStockId() {
        return StockId;
    }

    public String getName() {
        return Name;
    }

    public String getUnit() {
        return Unit;
    }

    public String getSpec() {
        return Spec;
    }

    public String getBarCode() {
        return BarCode;
    }

    public int getCheckDetailID() {
        return CheckDetailID;
    }

    public double getSysQuantity() {
        return SysQuantity;
    }

    public double getCurrentCheckQuantity() {
        return CurrentCheckQuantity;
    }
}
