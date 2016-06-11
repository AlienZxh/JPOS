package com.j1j2.jposmvvm.data.api;


import com.j1j2.jposmvvm.data.api.body.LoginBody;
import com.j1j2.jposmvvm.data.model.ShopInfo;
import com.j1j2.jposmvvm.data.model.WebReturn;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 兴昊 on 2015/10/26.
 */
public interface ShopAPI {

    @POST("client/v1/shop/Login")
    Observable<WebReturn<ShopInfo>> login(@Body LoginBody loginBody);

}
