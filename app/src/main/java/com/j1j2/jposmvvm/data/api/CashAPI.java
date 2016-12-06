package com.j1j2.jposmvvm.data.api;

import com.j1j2.jposmvvm.data.model.CashPuzzyQuery;
import com.j1j2.jposmvvm.data.model.SaleOrderSettleBody;
import com.j1j2.jposmvvm.data.model.WebReturn;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-8-1.
 */
public interface CashAPI {

    @POST("client/v1/Cash/PuzzyQueryScan")
    Observable<WebReturn<CashPuzzyQuery>> puzzyQueryScan(@Query("key") String key);


    @POST("client/v1/Cash/QuerySaleOrderNO")
    Observable<WebReturn<String>> querySaleOrderNO();

    @POST("client/v1/Cash/SettleSaleOrder")
    Observable<WebReturn<String>> settleSaleOrder(@Body SaleOrderSettleBody saleOrderSettleBody);
}
