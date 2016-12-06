package com.j1j2.jposmvvm.features.base.di.modules;

import com.google.gson.Gson;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;
import com.j1j2.jposmvvm.data.api.CashAPI;
import com.j1j2.jposmvvm.data.api.PrintProductPriceLabelAPI;
import com.j1j2.jposmvvm.data.api.SaleStatisticAPI;
import com.j1j2.jposmvvm.data.api.ShopAPI;
import com.j1j2.jposmvvm.data.api.StockAPI;
import com.j1j2.jposmvvm.data.api.StockCheckAPI;
import com.j1j2.jposmvvm.data.api.StorageAPI;
import com.j1j2.jposmvvm.data.api.SupplierAPI;
import com.j1j2.jposmvvm.features.actions.CashActionCreator;
import com.j1j2.jposmvvm.features.actions.PrintProductPriceLabelActionCreator;
import com.j1j2.jposmvvm.features.actions.SaleStatisticActionCreator;
import com.j1j2.jposmvvm.features.actions.ShopActionCreator;
import com.j1j2.jposmvvm.features.actions.StockActionCreator;
import com.j1j2.jposmvvm.features.actions.StockCheckActionCreator;
import com.j1j2.jposmvvm.features.actions.StorageActionCreator;
import com.j1j2.jposmvvm.features.actions.SupplierActionCreator;

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
    StockCheckAPI stockCheckAPI(Retrofit retrofit) {
        return retrofit.create(StockCheckAPI.class);
    }


    @Provides
    @Singleton
    StorageAPI storageAPI(Retrofit retrofit) {
        return retrofit.create(StorageAPI.class);
    }

    @Provides
    @Singleton
    SupplierAPI supplierAPI(Retrofit retrofit) {
        return retrofit.create(SupplierAPI.class);
    }

    @Provides
    @Singleton
    CashAPI cashAPI(Retrofit retrofit) {
        return retrofit.create(CashAPI.class);
    }

    @Provides
    @Singleton
    SaleStatisticAPI saleStatisticAPI(Retrofit retrofit) {
        return retrofit.create(SaleStatisticAPI.class);
    }

    @Provides
    @Singleton
    PrintProductPriceLabelAPI printProductPriceLabelAPI(Retrofit retrofit) {
        return retrofit.create(PrintProductPriceLabelAPI.class);
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

    @Provides
    @Singleton
    StockCheckActionCreator stockCheckActionCreator(Dispatcher dispatcher, SubscriptionManager subscriptionManager, StockCheckAPI stockCheckAPI) {
        return new StockCheckActionCreator(dispatcher, subscriptionManager, stockCheckAPI);
    }


    @Provides
    @Singleton
    StorageActionCreator storageActionCreator(Dispatcher dispatcher, SubscriptionManager subscriptionManager, StorageAPI storageAPI) {
        return new StorageActionCreator(dispatcher, subscriptionManager, storageAPI);
    }

    @Provides
    @Singleton
    SupplierActionCreator supplierActionCreator(Dispatcher dispatcher, SubscriptionManager subscriptionManager, SupplierAPI supplierAPI) {
        return new SupplierActionCreator(dispatcher, subscriptionManager, supplierAPI);
    }

    @Provides
    @Singleton
    CashActionCreator cashActionCreator(Dispatcher dispatcher, SubscriptionManager subscriptionManager, CashAPI cashAPI) {
        return new CashActionCreator(dispatcher, subscriptionManager, cashAPI);
    }

    @Provides
    @Singleton
    SaleStatisticActionCreator saleStaticActionCreator(Dispatcher dispatcher, SubscriptionManager subscriptionManager, SaleStatisticAPI saleStatisticAPI) {
        return new SaleStatisticActionCreator(dispatcher, subscriptionManager, saleStatisticAPI);
    }

    @Provides
    @Singleton
    PrintProductPriceLabelActionCreator printProductPriceLabelActionCreator(Dispatcher dispatcher, SubscriptionManager subscriptionManager, PrintProductPriceLabelAPI printProductPriceLabelAPI) {
        return new PrintProductPriceLabelActionCreator(dispatcher, subscriptionManager, printProductPriceLabelAPI);
    }
}
