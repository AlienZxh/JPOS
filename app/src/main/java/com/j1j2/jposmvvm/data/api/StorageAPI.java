package com.j1j2.jposmvvm.data.api;

import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.Product;
import com.j1j2.jposmvvm.data.model.StorageOrder;
import com.j1j2.jposmvvm.data.model.StorageOrderItem;
import com.j1j2.jposmvvm.data.model.StorageStock;
import com.j1j2.jposmvvm.data.model.StorageStockDetail;
import com.j1j2.jposmvvm.data.model.WebReturn;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-7-15.
 */
public interface StorageAPI {

    @POST("client/v1/Storage/QueryStorageOrders")
    Observable<WebReturn<PageManager<List<StorageOrder>>>> queryStorageOrders(@Query("pageIndex") int pageIndex);

    @POST("client/v1/Storage/CreateUpdateStorageOrder")
    Observable<WebReturn<String>> createUpdateStorageOrder(@Body StorageOrder storageOrder);

    @POST("client/v1/Storage/QueryStocks")
    Observable<WebReturn<PageManager<List<StorageStock>>>> queryStocks(@Query("pageIndex") int pageIndex, @Query("key") String key, @Query("orderId") int orderId);

    @POST("client/v1/Storage/CreateOrUpdateStoreageOrderDetailItem")
    Observable<WebReturn<String>> createOrUpdateStoreageOrderDetailItem(@Body StorageOrderItem storageOrderItem);

    @POST("client/v1/Storage/RemoveStoreageOrderDetailItem")
    Observable<WebReturn<String>> removeStoreageOrderDetailItem(@Query("detailId") int detailId, @Query("orderId") int orderId);

    @POST("client/v1/Storage/QueryStoreOrderDetails")
    Observable<WebReturn<List<StorageStockDetail>>> queryStoreOrderDetails(@Query("storageOrderId") int storageOrderId);

    @POST("client/v1/Storage/AuditStorageOrder")
    Observable<WebReturn<String>> auditStorageOrder(@Query("orderId") int orderId);
}
