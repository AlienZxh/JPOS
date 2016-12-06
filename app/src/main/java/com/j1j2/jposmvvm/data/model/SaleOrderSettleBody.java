package com.j1j2.jposmvvm.data.model;

import java.util.List;

/**
 * Created by alienzxh on 16-8-1.
 */
public class SaleOrderSettleBody {


    /**
     * UserId : 1
     * OrderNO : sample string 1
     * PayType : 64
     * Details : [{"StockId":1,"Quantity":1,"Price":1},{"StockId":1,"Quantity":1,"Price":1}]
     * PayAuthCode : sample string 3
     * CashAmount : 4.0
     */

    private Integer UserId;
    private String OrderNO;
    private int PayType;
    private String PayAuthCode;
    private double CashAmount;
    private double UseBalance;
    private double SaveBalance;
    private double Change;
    /**
     * StockId : 1
     * Quantity : 1
     * Price : 1.0
     */

    private List<SaleOrderSettleStock> Details;

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getOrderNO() {
        return OrderNO;
    }

    public void setOrderNO(String OrderNO) {
        this.OrderNO = OrderNO;
    }

    public int getPayType() {
        return PayType;
    }

    public void setPayType(int PayType) {
        this.PayType = PayType;
    }

    public String getPayAuthCode() {
        return PayAuthCode;
    }

    public void setPayAuthCode(String PayAuthCode) {
        this.PayAuthCode = PayAuthCode;
    }

    public double getCashAmount() {
        return CashAmount;
    }

    public void setCashAmount(double CashAmount) {
        this.CashAmount = CashAmount;
    }

    public double getUseBalance() {
        return UseBalance;
    }

    public void setUseBalance(double useBalance) {
        UseBalance = useBalance;
    }

    public double getSaveBalance() {
        return SaveBalance;
    }

    public void setSaveBalance(double saveBalance) {
        SaveBalance = saveBalance;
    }

    public double getChange() {
        return Change;
    }

    public void setChange(double change) {
        Change = change;
    }

    public List<SaleOrderSettleStock> getDetails() {
        return Details;
    }

    public void setDetails(List<SaleOrderSettleStock> Details) {
        this.Details = Details;
    }

    public static class SaleOrderSettleStock {
        private Integer StockId;
        private double Quantity;
        private double Price;

        public Integer getStockId() {
            return StockId;
        }

        public void setStockId(Integer stockId) {
            StockId = stockId;
        }

        public double getQuantity() {
            return Quantity;
        }

        public void setQuantity(double quantity) {
            Quantity = quantity;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }
    }
}
