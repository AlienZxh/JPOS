package com.j1j2.jposmvvm.features.base.di.modules;

import com.google.gson.Gson;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;
import com.j1j2.jposmvvm.data.api.ShopAPI;
import com.j1j2.jposmvvm.data.api.StockAPI;
import com.j1j2.jposmvvm.features.actions.ShopActionCreator;
import com.j1j2.jposmvvm.features.actions.StockActionCreator;
import com.j1j2.jposmvvm.features.actions.StockActions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alienzxh on 16-5-17.
 */
@Module
public class ActionModule {

    @Provides
    @Singleton
    ShopAPI shopAPI(Retrofit retrofit) {
        return retrofit.create(ShopAPI.class);
    }

    @Provides
    @Singleton
    StockAPI stockAPI(Retrofit retrofit) {
        return retrofit.create(StockAPI.class);
    }

    @Provides
    @Singleton
    ShopActionCreator shopActionCreator(Dispatcher dispatcher, SubscriptionManager subscriptionManager, ShopAPI shopAPI) {
        return new ShopActionCreator(dispatcher, subscriptionManager, shopAPI);
    }

    @Provides
    @Singleton
    StockActionCreator stockActionCreator(Dispatcher dispatcher, SubscriptionManager subscriptionManager, StockAPI stockAPI, Gson gson) {
        return new StockActionCreator(dispatcher, subscriptionManager, stockAPI, gson);
    }
}
