package com.j1j2.jposmvvm.data.model;

import java.util.List;

/**
 * Created by alienzxh on 16-8-20.
 */
public class QuerySaleOrderDetail {


    /**
     * OrderNO : sample string 1
     * UserId : 1
     * Amount : 2.0
     * Discount : 3.0
     * Point : 4
     * Profit : 1.0
     * ClerkName : sample string 5
     * SubmitTime : 2016-08-20T11:04:57.9326595+08:00
     * SubmitTimeStr : 2016-08-20 11:04:57
     * SettleTime : 2016-08-20T11:04:57.9326595+08:00
     * SettleTimeStr : 2016-08-20 11:04:57
     * InvalidTime : 2016-08-20T11:04:57.9326595+08:00
     * InvalidTimeStr : 2016-08-20 11:04:57
     * OrderState : 1
     * StateString : 未结账
     * UserName : sample string 7
     * UserMobile : sample string 8
     * Details : [{"Name":"sample string 1","BarCode":"sample string 2","Spec":"sample string 3","Unit":"sample string 4","Price":5,"Quantity":6},{"Name":"sample string 1","BarCode":"sample string 2","Spec":"sample string 3","Unit":"sample string 4","Price":5,"Quantity":6}]
     * SettleInfo : {"OrderID":1,"PayType":1,"CashAmount":2,"AlipayAmount":3,"WepayAmount":4,"UseBalance":5,"SaveBalance":6,"Change":7,"IsSettle":true}
     */
    // CashPay 1 AliPay 2 WePay 3

    private String OrderNO;
    private int UserId;
    private double Amount;
    private double Discount;
    private int Point;
    private double Profit;
    private String ClerkName;
    private String SubmitTime;
    private String SubmitTimeStr;
    private String SettleTime;
    private String SettleTimeStr;
    private String InvalidTime;
    private String InvalidTimeStr;
    private int OrderState;
    private String StateString;
    private String UserName;
    private String UserMobile;
    /**
     * OrderID : 1
     * PayType : 1
     * CashAmount : 2.0
     * AlipayAmount : 3.0
     * WepayAmount : 4.0
     * UseBalance : 5.0
     * SaveBalance : 6.0
     * Change : 7.0
     * IsSettle : true
     */

    private SettleInfoEntity SettleInfo;
    /**
     * Name : sample string 1
     * BarCode : sample string 2
     * Spec : sample string 3
     * Unit : sample string 4
     * Price : 5.0
     * Quantity : 6.0
     */

    private List<SaleOrderProduct> Details;

    public String getOrderNO() {
        return OrderNO;
    }

    public void setOrderNO(String OrderNO) {
        this.OrderNO = OrderNO;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double Discount) {
        this.Discount = Discount;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int Point) {
        this.Point = Point;
    }

    public double getProfit() {
        return Profit;
    }

    public void setProfit(double Profit) {
        this.Profit = Profit;
    }

    public String getClerkName() {
        return ClerkName;
    }

    public void setClerkName(String ClerkName) {
        this.ClerkName = ClerkName;
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

    public String getSettleTime() {
        return SettleTime;
    }

    public void setSettleTime(String SettleTime) {
        this.SettleTime = SettleTime;
    }

    public String getSettleTimeStr() {
        return SettleTimeStr;
    }

    public void setSettleTimeStr(String SettleTimeStr) {
        this.SettleTimeStr = SettleTimeStr;
    }

    public String getInvalidTime() {
        return InvalidTime;
    }

    public void setInvalidTime(String InvalidTime) {
        this.InvalidTime = InvalidTime;
    }

    public String getInvalidTimeStr() {
        return InvalidTimeStr;
    }

    public void setInvalidTimeStr(String InvalidTimeStr) {
        this.InvalidTimeStr = InvalidTimeStr;
    }

    public int getOrderState() {
        return OrderState;
    }

    public void setOrderState(int OrderState) {
        this.OrderState = OrderState;
    }

    public String getStateString() {
        return StateString;
    }

    public void setStateString(String StateString) {
        this.StateString = StateString;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserMobile() {
        return UserMobile;
    }

    public void setUserMobile(String UserMobile) {
        this.UserMobile = UserMobile;
    }

    public SettleInfoEntity getSettleInfo() {
        return SettleInfo;
    }

    public void setSettleInfo(SettleInfoEntity SettleInfo) {
        this.SettleInfo = SettleInfo;
    }

    public List<SaleOrderProduct> getDetails() {
        return Details;
    }

    public void setDetails(List<SaleOrderProduct> Details) {
        this.Details = Details;
    }

    public static class SettleInfoEntity {
        private int OrderID;
        private int PayType;
        private double PayAmount;
        private double UseBalance;
        private double SaveBalance;
        private double Change;
        private boolean IsSettle;

        public int getOrderID() {
            return OrderID;
        }

        public void setOrderID(int OrderID) {
            this.OrderID = OrderID;
        }

        public int getPayType() {
            return PayType;
        }

        public void setPayType(int PayType) {
            this.PayType = PayType;
        }

        public double getPayAmount() {
            return PayAmount;
        }

        public void setPayAmount(double payAmount) {
            PayAmount = payAmount;
        }

        public double getUseBalance() {
            return UseBalance;
        }

        public void setUseBalance(double UseBalance) {
            this.UseBalance = UseBalance;
        }

        public double getSaveBalance() {
            return SaveBalance;
        }

        public void setSaveBalance(double SaveBalance) {
            this.SaveBalance = SaveBalance;
        }

        public double getChange() {
            return Change;
        }

        public void setChange(double Change) {
            this.Change = Change;
        }

        public boolean isIsSettle() {
            return IsSettle;
        }

        public void setIsSettle(boolean IsSettle) {
            this.IsSettle = IsSettle;
        }
    }

    public static class SaleOrderProduct {
        private String Name;
        private String BarCode;
        private String Spec;
        private String Unit;
        private double Price;
        private double Quantity;

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

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public double getQuantity() {
            return Quantity;
        }

        public void setQuantity(double Quantity) {
            this.Quantity = Quantity;
        }
    }
}
