package com.j1j2.jposmvvm.data.model;

/**
 * Created by alienzxh on 16-8-15.
 */
public class SaleStaticByCategory {


    /**
     * Category : sample string 1
     * OrderAmount : 2.0
     * OrderCount : 3
     * UserCount : 4
     * Profit : 1.0
     * OrderPrice : 0.6666666666666666
     * ProfitRate : 0.5
     */

    private String Category;
    private double OrderAmount;
    private int OrderCount;
    private int UserCount;
    private double Profit;
    private double OrderPrice;
    private double ProfitRate;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public double getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(double OrderAmount) {
        this.OrderAmount = OrderAmount;
    }

    public int getOrderCount() {
        return OrderCount;
    }

    public void setOrderCount(int OrderCount) {
        this.OrderCount = OrderCount;
    }

    public int getUserCount() {
        return UserCount;
    }

    public void setUserCount(int UserCount) {
        this.UserCount = UserCount;
    }

    public double getProfit() {
        return Profit;
    }

    public void setProfit(double Profit) {
        this.Profit = Profit;
    }

    public double getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(double OrderPrice) {
        this.OrderPrice = OrderPrice;
    }

    public double getProfitRate() {
        return ProfitRate;
    }

    public void setProfitRate(double ProfitRate) {
        this.ProfitRate = ProfitRate;
    }
}
