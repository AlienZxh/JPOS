package com.j1j2.jposmvvm.features.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.j1j2.jposmvvm.BR;
import com.j1j2.jposmvvm.common.constants.Constants;
import com.j1j2.jposmvvm.common.utils.DoubleUtils;
import com.j1j2.jposmvvm.data.model.CashOrder;
import com.j1j2.jposmvvm.data.model.CashPuzzyQueryStock;
import com.j1j2.jposmvvm.data.model.SaleOrderSettleBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alienzxh on 16-8-2.
 */
public class SaleOrderViewModel extends BaseObservable {
    private SaleOrderSettleBody saleOrderSettleBody;

    //    private String payType;
//    private String payAuthCode;//付款码
//    private String cashAmount;//收款金额
    private double productAmount;
    private double saveAmount;
    private double receivableAmount;//实际应收

    private boolean isMember;
    private String userName;

    private double memberBalance;//用户余额
//    private double UseBalance;//使用余额
//    private double SaveBalance;//存入余额
//    private double change;//找零


    public SaleOrderViewModel(CashOrder cashOrder, int payType) {
        saleOrderSettleBody = new SaleOrderSettleBody();
        saleOrderSettleBody.setOrderNO(cashOrder.getOrderNO());
        saleOrderSettleBody.setPayType(payType);
        if (cashOrder.getCashPuzzyQueryUser() != null) {
            saleOrderSettleBody.setUserId(cashOrder.getCashPuzzyQueryUser().getUserId());
            userName = cashOrder.getCashPuzzyQueryUser().getName();
            memberBalance = cashOrder.getCashPuzzyQueryUser().getBalance();
            isMember = true;
        } else {
            userName = null;
            memberBalance = 0;
            isMember = false;
        }

        List<SaleOrderSettleBody.SaleOrderSettleStock> saleOrderSettleStocks = new ArrayList<>();
        int size = cashOrder.getCashOrderStocks().size();
        CashPuzzyQueryStock cashPuzzyQueryStock;
        SaleOrderSettleBody.SaleOrderSettleStock saleOrderSettleStock;
        for (int i = 0; i < size; i++) {
            cashPuzzyQueryStock = cashOrder.getCashOrderStocks().get(i);

            productAmount += cashPuzzyQueryStock.getQuantity() * cashPuzzyQueryStock.getRetailPrice();

            saleOrderSettleStock = new SaleOrderSettleBody.SaleOrderSettleStock();
            if (cashPuzzyQueryStock.getStockId() > 0)
                saleOrderSettleStock.setStockId(cashPuzzyQueryStock.getStockId());
            saleOrderSettleStock.setQuantity(cashPuzzyQueryStock.getQuantity());

            if (cashOrder.getCashPuzzyQueryUser() != null) {
                saleOrderSettleStock.setPrice(cashPuzzyQueryStock.getMemberPrice());
                receivableAmount += cashPuzzyQueryStock.getQuantity() * cashPuzzyQueryStock.getMemberPrice();
            } else {
                saleOrderSettleStock.setPrice(cashPuzzyQueryStock.getRetailPrice());
                receivableAmount += cashPuzzyQueryStock.getQuantity() * cashPuzzyQueryStock.getRetailPrice();
            }

            saleOrderSettleStocks.add(saleOrderSettleStock);
        }

        BigDecimal save = new BigDecimal(productAmount - receivableAmount);
        saveAmount = save.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        saleOrderSettleBody.setDetails(saleOrderSettleStocks);

//        saleOrderSettleBody.setUseBalance(memberBalance >= productAmount ? productAmount : memberBalance);
        saleOrderSettleBody.setUseBalance(0);
        saleOrderSettleBody.setCashAmount(0);
        saleOrderSettleBody.setSaveBalance(0);
        saleOrderSettleBody.setChange(DoubleUtils.formatDouble(saleOrderSettleBody.getUseBalance() + saleOrderSettleBody.getCashAmount() - saleOrderSettleBody.getSaveBalance() - receivableAmount));

    }

    public SaleOrderSettleBody getSaleOrderSettleBody() {
        return saleOrderSettleBody;
    }


    public int getPayType() {
        return this.saleOrderSettleBody.getPayType();
    }

    public String getPayTitle() {
        String payType = "";
        switch (saleOrderSettleBody.getPayType()) {
            case Constants.CASHPAY:
                payType = "现金支付";
                break;
            case Constants.ALIPAY:
                payType = "支付宝支付";
                break;
            case Constants.WEXINPAY:
                payType = "微信支付";
                break;
            default:
                payType = "现金支付";
                break;
        }
        return payType;
    }


    public String getPayAuthCode() {
        return this.saleOrderSettleBody.getPayAuthCode();
    }

    public void setPayAuthCode(String payAuthCode) {
        this.saleOrderSettleBody.setPayAuthCode(payAuthCode);

    }

    public String getCashAmount() {
        if (this.saleOrderSettleBody.getCashAmount() > 0)
            return "" + this.saleOrderSettleBody.getCashAmount();
        else
            return "";
    }

    public void setCashAmount(String cashAmount) {
        double cash = 0;

        try {
            cash = Double.parseDouble(cashAmount);
        } catch (NumberFormatException e) {

        }

        this.saleOrderSettleBody.setCashAmount(cash);

        double change = DoubleUtils.formatDouble(saleOrderSettleBody.getUseBalance() + saleOrderSettleBody.getCashAmount() - saleOrderSettleBody.getSaveBalance() - receivableAmount);
        if (this.isMember) {
            saleOrderSettleBody.setChange((int) change);
            saleOrderSettleBody.setSaveBalance(DoubleUtils.formatDouble(change - (int) change));
            notifyPropertyChanged(BR.change);
            notifyPropertyChanged(BR.saveBalance);
        } else {
            saleOrderSettleBody.setChange(change);
            saleOrderSettleBody.setSaveBalance(0);
            notifyPropertyChanged(BR.change);
            notifyPropertyChanged(BR.saveBalance);
        }

    }


    public void setReceivableAmount(double receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public void setSaveAmount(double saveAmount) {
        this.saveAmount = saveAmount;
    }

    public void setProductAmount(double productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductAmount() {
        return "" + productAmount;
    }

    public String getSaveAmount() {
        return "" + saveAmount;
    }

    public String getReceivableAmount() {
        return "" + receivableAmount;
    }

    public boolean isMember() {
        return isMember;
    }

    public String getUserName() {
        return userName;
    }

    public double getMemberBalance() {
        return memberBalance;
    }


    @Bindable
    public String getChange() {
        return "￥" + this.saleOrderSettleBody.getChange();
    }


    public String getUseBalance() {
        return "￥" + this.saleOrderSettleBody.getUseBalance();
    }

    @Bindable
    public String getSaveBalance() {
        return "" + this.saleOrderSettleBody.getSaveBalance();
    }

    public void setSaveBalance(String saveBalance) {
        double balance = 0;

        try {
            balance = Double.parseDouble(saveBalance);
        } catch (NumberFormatException e) {

        }

        this.saleOrderSettleBody.setSaveBalance(balance);
        saleOrderSettleBody.setChange(DoubleUtils.formatDouble(saleOrderSettleBody.getUseBalance() + saleOrderSettleBody.getCashAmount() - saleOrderSettleBody.getSaveBalance() - receivableAmount));
        notifyPropertyChanged(BR.change);
    }
}
