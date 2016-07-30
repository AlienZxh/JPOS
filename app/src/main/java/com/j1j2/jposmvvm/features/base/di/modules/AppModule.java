package com.j1j2.jposmvvm.features.base.di.modules;

import android.text.TextUtils;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.hardsoftstudio.rxflux.RxFlux;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.dispatcher.RxBus;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;
import com.j1j2.jposmvvm.BuildConfig;
import com.j1j2.jposmvvm.common.utils.TinyDB;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.data.model.StorageOrder;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.data.model.serializertypeadapter.ShopInfoSerializer;
import com.j1j2.jposmvvm.data.model.serializertypeadapter.StorageOrderSerializer;
import com.j1j2.jposmvvm.data.model.serializertypeadapter.UpdateDeserializer;
import com.j1j2.jposmvvm.data.model.serializertypeadapter.UpdateWebReturnDeserializer;
import com.j1j2.jposmvvm.features.base.JPOSApplication;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.orhanobut.logger.Logger;

import org.lzh.framework.updatepluginlib.model.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmObject;
import io.realm.ShopInfoRealmProxy;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alienzxh on 16-3-5.
 */
@Module
public class AppModule {
    private final JPOSApplication application;
    private final RxFlux rxFlux;

    public AppModule(JPOSApplication application, RxFlux rxFlux) {
        this.application = application;
        this.rxFlux = rxFlux;
    }

    @Provides
    @Singleton
    JPOSApplication application() {
        return application;
    }

    @Provides
    @Singleton
    Navigate navigate() {
        return new Navigate();
    }

    @Provides
    @Singleton
    Toastor toastor(JPOSApplication application) {
        return new Toastor(application.getApplicationContext());
    }

    @Provides
    @Singleton
    TinyDB tinyDB(JPOSApplication application) {
        return new TinyDB(application.getApplicationContext());
    }

    @Provides
    @Singleton
    Gson gson() {
        return new GsonBuilder().serializeNulls()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class) || f.getAnnotation(Expose.class) != null;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(StorageOrder.class,new StorageOrderSerializer())
                .registerTypeAdapter(ShopInfoRealmProxy.class, new ShopInfoSerializer())
                .registerTypeAdapter(Update.class, new UpdateDeserializer())
                .registerTypeAdapter(new TypeToken<WebReturn<Update>>() {
                }.getType(), new UpdateWebReturnDeserializer())
                .create();
    }

    @Provides
    @Singleton
    OkHttpClient okHttpClient(final Gson gson, final TinyDB tinyDB) {
        HttpLoggingInterceptor logging =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Logger.d(message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
//                .addInterceptor(logging)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        Response newResponse = new Response.Builder()
                                .cacheResponse(response.cacheResponse())
                                .networkResponse(response.networkResponse())
                                .priorResponse(response.priorResponse())
                                .body(response.body())
                                .headers(response.headers())
                                .handshake(response.handshake())
                                .protocol(response.protocol())
                                .request(response.request())
                                .message(response.message())
                                .code(200)
                                .build();
                        return newResponse;
                    }
                })
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        if (url.toString().equals(BuildConfig.API_URL + "client/v1/shop/Login")) {
                            tinyDB.putString("loginCookies", gson.toJson(cookies));
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        if (url.toString().equals(BuildConfig.API_URL + "client/v1/shop/Login") || TextUtils.isEmpty(tinyDB.getString("loginCookies"))) {
                            return new ArrayList<>();
                        } else {
                            return gson.fromJson(tinyDB.getString("loginCookies"), new TypeToken<List<Cookie>>() {
                            }.getType());
                        }

                    }
                })
                .build();
    }

    @Provides
    @Singleton
    Retrofit retrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    RxFlux rxFlux() {
        return rxFlux;
    }

    @Provides
    @Singleton
    Dispatcher dispatcher(RxFlux rxFlux) {
        return rxFlux.getDispatcher();
    }

    @Provides
    @Singleton
    RxBus rxBus(RxFlux rxFlux) {
        return rxFlux.getRxBus();
    }

    @Provides
    @Singleton
    SubscriptionManager subscriptionManager(RxFlux rxFlux) {
        return rxFlux.getSubscriptionManager();
    }
}
