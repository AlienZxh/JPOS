package com.j1j2.jposmvvm.data.model;

import java.util.List;

/**
 * Created by alienzxh on 16-8-1.
 */
public class CashPuzzyQuery {

    /**
     * ReturnType : 1
     * QueryStockResult : [{"StockId":1,"Name":"sample string 1","BarCode":"sample string 2","Spec":"sample string 3","Unit":"sample string 4","MemberPrice":1,"RetailPrice":1,"SmallImgUrl":"sample string 5"},{"StockId":1,"Name":"sample string 1","BarCode":"sample string 2","Spec":"sample string 3","Unit":"sample string 4","MemberPrice":1,"RetailPrice":1,"SmallImgUrl":"sample string 5"}]
     * QueryUserResult : {"UserId":1,"Name":"sample string 2","Mobile":"sample string 3","Points":4,"Balance":5,"CostSum":1,"SaveSum":1}
     */

    private int ReturnType;//返回的类型 1:返回的是商品数据 2:返回的是用户信息


    private CashPuzzyQueryUser QueryUserResult;


    private List<CashPuzzyQueryStock> QueryStockResult;

    public int getReturnType() {
        return ReturnType;
    }

    public void setReturnType(int ReturnType) {
        this.ReturnType = ReturnType;
    }

    public CashPuzzyQueryUser getQueryUserResult() {
        return QueryUserResult;
    }

    public void setQueryUserResult(CashPuzzyQueryUser QueryUserResult) {
        this.QueryUserResult = QueryUserResult;
    }

    public List<CashPuzzyQueryStock> getQueryStockResult() {
        return QueryStockResult;
    }

    public void setQueryStockResult(List<CashPuzzyQueryStock> QueryStockResult) {
        this.QueryStockResult = QueryStockResult;
    }




}
