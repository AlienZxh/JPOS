package com.j1j2.jposmvvm.data.api;


import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.Product;
import com.j1j2.jposmvvm.data.model.ProductDetail;
import com.j1j2.jposmvvm.data.model.TopCategory;
import com.j1j2.jposmvvm.data.model.WebReturn;

import java.util.List;


import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 兴昊 on 2016/1/6.
 */
public interface StockAPI {

    @POST("client/v1/Stock/QueryStockCategory")
    Observable<WebReturn<List<TopCategory>>> queryStockCategory(@Query("filterNoImgProduct") String filterNoImgProduct);

    //suggestPriceType   1:低价     2:常规
    //filterNoImgProduct true:无图　　　　false:有图　　　　null:不过滤
    @POST("client/v1/Stock/QueryStocksIfNotFoundThenCreate")
    Observable<WebReturn<PageManager<List<Product>>>> queryStocksIfNotFoundThenCreate(@Query("pageIndex") int pageIndex, @Query("keyWords") String keyWords, @Query("suggestPriceType") int suggestPriceType, @Query("category") String categoryName, @Query("subCategoryName") String subCategoryName, @Query("filterNoImgProduct") String filterNoImgProduct);

    @POST("client/v1/Stock/QueryStockDetail")
    Observable<WebReturn<ProductDetail>> queryStockDetail(@Query("stockId") int stockId);

    @POST("client/v1/Stock/CreateOrUpdateStock")
    Observable<WebReturn<String>> createOrUpdateStock(@Body ProductDetail productDetail);

    //imgSource  :1  jpos  2  android   3   apple    4   未知
    @Multipart
    @POST("client/v1/Stock/UploadStockImg")
    Observable<WebReturn<String>> uploadStockImg(@Query("stockId") int stockId, @Query("imgId") String imgId, @Query("isDefaultImg") boolean isDefaultImg, @Query("imgSource") int imgSource, @Part MultipartBody.Part file);

    @POST("client/v1/Stock/RemoveStockImg")
    Observable<WebReturn<String>> removeStockImg(@Query("stockId") int stockId, @Query("imgId") int imgId);

}
