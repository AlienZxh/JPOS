package com.j1j2.jposmvvm.data.api;

import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.PrintPriceLabelProduct;
import com.j1j2.jposmvvm.data.model.ProductPriceLabelOrder;
import com.j1j2.jposmvvm.data.model.WebReturn;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alienzxh on 16-6-27.
 */
public interface PrintProductPriceLabelAPI {

    @POST("client/v1/PrintProductPriceLabel/CreateProductPriceLabelOrder")
    Observable<WebReturn<String>> createProductPriceLabelOrder( @Query("note") String note);

    @POST("client/v1/PrintProductPriceLabel/QueryProductPriceLabelOrders")
    Observable<WebReturn<PageManager<List<ProductPriceLabelOrder>>>> queryProductPriceLabelOrders(@Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);



    @POST("client/v1/PrintProductPriceLabel/QueryProductInPrintPriceLabel")
    Observable<WebReturn<List<PrintPriceLabelProduct>>> queryProductInPrintPriceLabel( @Query("key") String key);

    @POST("client/v1/PrintProductPriceLabel/QueryPrintPriceLabelOrderDetails")
    Observable<WebReturn<PageManager<List<PrintPriceLabelProduct>>>> queryPrintPriceLabelOrderDetails(@Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize, @Query("orderId") int orderId);

    @POST("client/v1/PrintProductPriceLabel/CreatePrintPriceLabelProduct")
    Observable<WebReturn<String>> createPrintPriceLabelProduct(@Query("orderId") int orderId, @Query("stockId") int stockId);

    @POST("client/v1/PrintProductPriceLabel/RemovePrintPriceLabelPrdouct")
    Observable<WebReturn<String>> removePrintPriceLabelPrdouct(@Query("orderDetailId") int orderDetailId);
}
