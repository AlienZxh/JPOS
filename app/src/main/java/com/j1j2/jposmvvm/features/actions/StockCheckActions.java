package com.j1j2.jposmvvm.features.actions;

import com.j1j2.jposmvvm.data.model.StockCheckOrderDetail;

import retrofit2.http.Body;
import retrofit2.http.Query;

/**
 * Created by alienzxh on 16-9-19.
 */
public interface StockCheckActions {
    String QUERYSTOCKCHECKORDERS = "queryStockCheckOrders";
    String CREATESTOCKCHECKORDER = "createStockCheckOrder";
    String QUERYSTOCKCHECKORDERDETAILS = "queryStockCheckOrderDetails";
    String QUERYSTOCKS = "queryStocks";
    String CREATEORUPDATESTOCKCHECKITEM = "createOrUpdateStockCheckItem";

    void queryStockCheckOrders(int pageIndex, int pageSize);

    void createStockCheckOrder(int stockCheckOrderType);

    void queryStockCheckOrderDetails(int stockCheckOrderId, int pageIndex, int pageSize);

    void queryStocks(int stockCheckOrderId, String key);

    void createOrUpdateStockCheckItem(StockCheckOrderDetail stockCheckOrderDetail);
}
