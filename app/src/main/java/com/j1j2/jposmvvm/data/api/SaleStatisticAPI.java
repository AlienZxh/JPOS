package com.j1j2.jposmvvm.data.api;

import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.QuerySaleOrderDetail;
import com.j1j2.jposmvvm.data.model.SaleOrder;
import com.j1j2.jposmvvm.data.model.SaleStatic;
import com.j1j2.jposmvvm.data.model.SaleStaticByCategory;
import com.j1j2.jposmvvm.data.model.WebReturn;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-8-15.
 */
public interface SaleStatisticAPI {

    @POST("client/v1/SaleStatistic/LoadSaleStaticByMonth")
    Observable<WebReturn<List<SaleStatic>>> loadSaleStaticByMonth(@Query("year") int year, @Query("month") int month);


    @POST("client/v1/SaleStatistic/LoadSaleStaticInMonthByCategory")
    Observable<WebReturn<List<SaleStaticByCategory>>> loadSaleStaticInMonthByCategory(@Query("year") int year, @Query("month") int month);


    @POST("client/v1/SaleStatistic/LoadSaleOrders")//beginTime：2016-05-01 00:00:00 endTime：2016-05-01 23:59:59
    Observable<WebReturn<PageManager<List<SaleOrder>>>> loadSaleOrders(@Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize, @Query("beginTime") String beginTime, @Query("endTime") String endTime);


    @POST("client/v1/SaleStatistic/QuerySaleOrderDetails")
    Observable<WebReturn<QuerySaleOrderDetail>> querySaleOrderDetails(@Query("orderId") int orderId);
}
