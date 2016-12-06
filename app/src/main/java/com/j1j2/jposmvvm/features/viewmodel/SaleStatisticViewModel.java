package com.j1j2.jposmvvm.features.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.j1j2.jposmvvm.BR;
import com.j1j2.jposmvvm.common.utils.DoubleUtils;
import com.j1j2.jposmvvm.data.model.SaleStatic;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by alienzxh on 16-8-16.
 */
public class SaleStatisticViewModel extends BaseObservable {
    private List<SaleStatic> saleStatics;
    private double saleAmount;
    private double perTicketSales;
    private double orderCount;
    private double memberRatio;
    private double grossProfit;
    private double grossProfitRate;

    public void setSaleStatics(List<SaleStatic> saleStatics) {
        this.saleStatics = saleStatics;
        saleAmount = 0;
        perTicketSales = 0;
        orderCount = 0;
        memberRatio = 0;
        grossProfit = 0;
        grossProfitRate = 0;
        int userCount = 0;

        for (int i = 0; i < saleStatics.size(); i++) {
            saleAmount += saleStatics.get(i).getOrderAmount();
            userCount += saleStatics.get(i).getUserCount();
            orderCount += saleStatics.get(i).getOrderCount();
            grossProfit += saleStatics.get(i).getProfit();
        }
        perTicketSales = saleAmount / orderCount;
        memberRatio = userCount / orderCount;
        grossProfitRate = grossProfit / saleAmount;

        notifyPropertyChanged(BR.saleAmount);
        notifyPropertyChanged(BR.perTicketSales);
        notifyPropertyChanged(BR.orderCount);
        notifyPropertyChanged(BR.memberRatio);
        notifyPropertyChanged(BR.grossProfit);
        notifyPropertyChanged(BR.grossProfitRate);
    }

    public void clear() {
        saleStatics.clear();
        saleAmount = 0;
        perTicketSales = 0;
        orderCount = 0;
        memberRatio = 0;
        grossProfit = 0;
        grossProfitRate = 0;
        notifyPropertyChanged(BR.saleAmount);
        notifyPropertyChanged(BR.perTicketSales);
        notifyPropertyChanged(BR.orderCount);
        notifyPropertyChanged(BR.memberRatio);
        notifyPropertyChanged(BR.grossProfit);
        notifyPropertyChanged(BR.grossProfitRate);
    }

    @Bindable
    public double getSaleAmount() {
        return DoubleUtils.formatDouble(saleAmount);
    }

    @Bindable
    public double getPerTicketSales() {
        return DoubleUtils.formatDouble(perTicketSales);
    }

    @Bindable
    public double getOrderCount() {
        return DoubleUtils.formatDouble(orderCount);
    }

    @Bindable
    public double getMemberRatio() {
        return DoubleUtils.formatDouble(memberRatio);
    }

    @Bindable
    public double getGrossProfit() {
        return DoubleUtils.formatDouble(grossProfit);
    }

    @Bindable
    public double getGrossProfitRate() {

        return DoubleUtils.formatDouble(grossProfitRate);
    }
}
