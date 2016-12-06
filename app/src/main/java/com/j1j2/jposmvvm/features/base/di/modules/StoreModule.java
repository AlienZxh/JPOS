package com.j1j2.jposmvvm.features.base.di.modules;

import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.features.stores.CashStore;
import com.j1j2.jposmvvm.features.stores.PrintProductPriceLabelStore;
import com.j1j2.jposmvvm.features.stores.SaleStatisticStore;
import com.j1j2.jposmvvm.features.stores.ShopStore;
import com.j1j2.jposmvvm.features.stores.StockCheckStore;
import com.j1j2.jposmvvm.features.stores.StockStore;
import com.j1j2.jposmvvm.features.stores.StorageStore;
import com.j1j2.jposmvvm.features.stores.SupplierStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alienzxh on 16-5-17.
 */
@Module
public class StoreModule {

    @Provides
    @Singleton
    ShopStore shopStore(Dispatcher dispatcher, JPOSApplication jposApplication) {
        return ShopStore.get(dispatcher, jposApplication);
    }

    @Provides
    @Singleton
    StockStore stockStore(Dispatcher dispatcher) {
        return StockStore.get(dispatcher);
    }

    @Provides
    @Singleton
    StockCheckStore stockCheckStore(Dispatcher dispatcher) {
        return StockCheckStore.get(dispatcher);
    }

    @Provides
    @Singleton
    StorageStore storageStore(Dispatcher dispatcher) {
        return StorageStore.get(dispatcher);
    }

    @Provides
    @Singleton
    SupplierStore supplierStore(Dispatcher dispatcher) {
        return SupplierStore.get(dispatcher);
    }

    @Provides
    @Singleton
    CashStore cashStore(Dispatcher dispatcher) {
        return CashStore.get(dispatcher);
    }

    @Provides
    @Singleton
    SaleStatisticStore saleStaticStore(Dispatcher dispatcher) {
        return SaleStatisticStore.get(dispatcher);
    }

    @Provides
    @Singleton
    PrintProductPriceLabelStore printProductPriceLabelStore(Dispatcher dispatcher) {
        return PrintProductPriceLabelStore.get(dispatcher);
    }
}
