package com.j1j2.jposmvvm.data.model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by alienzxh on 16-8-1.
 */
public class CashOrder extends RealmObject {
    @PrimaryKey
    private String OrderNO;
    @Required
    private Date UpdateTime;
    @Required
    private Date CreateTime;

    private boolean IsComplete;

    private RealmList<CashPuzzyQueryStock> CashOrderStocks;

    private CashPuzzyQueryUser cashPuzzyQueryUser;

    public String getOrderNO() {
        return OrderNO;
    }

    public void setOrderNO(String orderNO) {
        OrderNO = orderNO;
    }

    public Date getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Date updateTime) {
        UpdateTime = updateTime;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        this.CreateTime = createTime;
    }

    public boolean isComplete() {
        return IsComplete;
    }

    public void setComplete(boolean complete) {
        IsComplete = complete;
    }

    public RealmList<CashPuzzyQueryStock> getCashOrderStocks() {
        return CashOrderStocks;
    }

    public void setCashOrderStocks(RealmList<CashPuzzyQueryStock> cashOrderStocks) {
        CashOrderStocks = cashOrderStocks;
    }

    public CashPuzzyQueryUser getCashPuzzyQueryUser() {
        return cashPuzzyQueryUser;
    }

    public void setCashPuzzyQueryUser(CashPuzzyQueryUser cashPuzzyQueryUser) {
        this.cashPuzzyQueryUser = cashPuzzyQueryUser;
    }
}
