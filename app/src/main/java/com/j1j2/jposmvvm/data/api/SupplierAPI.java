package com.j1j2.jposmvvm.data.api;

import com.j1j2.jposmvvm.data.api.body.AddShopSupplierBody;
import com.j1j2.jposmvvm.data.model.Supplier;
import com.j1j2.jposmvvm.data.model.WebReturn;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by alienzxh on 16-7-26.
 */
public interface SupplierAPI {

    @POST("client/v1/Supplier/QueryShopSuppliers")
    Observable<WebReturn<List<Supplier>>> queryShopSuppliers();

    @POST("client/v1/Supplier/AddShopSupplier")
    Observable<WebReturn<String>> addShopSupplier(@Body AddShopSupplierBody addShopSupplierBody);

}
