package com.j1j2.jposmvvm.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alienzxh on 16-8-1.
 */
public class CashPuzzyQueryUser extends RealmObject {
    /**
     * UserId : 1
     * Name : sample string 2
     * Mobile : sample string 3
     * Points : 4
     * Balance : 5.0
     * CostSum : 1.0
     * SaveSum : 1.0
     */
    @PrimaryKey
    private int UserId;
    private String Name;
    private String Mobile;
    private int Points;
    private double Balance;
    private double CostSum;
    private double SaveSum;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int Points) {
        this.Points = Points;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double Balance) {
        this.Balance = Balance;
    }

    public double getCostSum() {
        return CostSum;
    }

    public void setCostSum(double CostSum) {
        this.CostSum = CostSum;
    }

    public double getSaveSum() {
        return SaveSum;
    }

    public void setSaveSum(double SaveSum) {
        this.SaveSum = SaveSum;
    }

    @Override
    public String toString() {
        return "CashPuzzyQueryUser{" +
                "UserId=" + UserId +
                ", Name='" + Name + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Points=" + Points +
                ", Balance=" + Balance +
                ", CostSum=" + CostSum +
                ", SaveSum=" + SaveSum +
                '}';
    }
}
