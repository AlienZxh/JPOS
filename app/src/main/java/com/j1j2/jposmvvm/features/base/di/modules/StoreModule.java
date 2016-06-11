package com.j1j2.jposmvvm.features.base.di.modules;

import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.j1j2.jposmvvm.features.base.JPOSApplication;
import com.j1j2.jposmvvm.features.stores.ShopStore;
import com.j1j2.jposmvvm.features.stores.StockStore;

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
}
