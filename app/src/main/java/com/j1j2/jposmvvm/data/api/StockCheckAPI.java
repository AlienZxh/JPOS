package com.j1j2.jposmvvm.data.api;


import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.StockCheckOrder;
import com.j1j2.jposmvvm.data.model.StockCheckOrderDetail;
import com.j1j2.jposmvvm.data.model.StockCheckOrderItem;
import com.j1j2.jposmvvm.data.model.WebReturn;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 兴昊 on 2016/2/2.
 */
public interface StockCheckAPI {

    @POST("client/v1/StockCheck/QueryStockCheckOrders")
    Observable<WebReturn<PageManager<List<StockCheckOrder>>>> queryStockCheckOrders(@Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    @POST("client/v1/StockCheck/CreateStockCheckOrder")
    Observable<WebReturn<String>> createStockCheckOrder(@Query("stockCheckOrderType") int stockCheckOrderType);//1:全部　　2:部分

    @POST("client/v1/StockCheck/QueryStockCheckOrderDetails")
    Observable<WebReturn<PageManager<List<StockCheckOrderDetail>>>> queryStockCheckOrderDetails(@Query("stockCheckOrderId") int stockCheckOrderId, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

    @POST("client/v1/StockCheck/QueryStocks")
    Observable<WebReturn<List<StockCheckOrderItem>>> queryStocks(@Query("stockCheckOrderId") int stockCheckOrderId, @Query("key") String key);

    @POST("client/v1/StockCheck/CreateOrUpdateStockCheckItem")
    Observable<WebReturn<String>> createOrUpdateStockCheckItem(@Body StockCheckOrderDetail stockCheckOrderDetail);


}
