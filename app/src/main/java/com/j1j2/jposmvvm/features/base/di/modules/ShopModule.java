package com.j1j2.jposmvvm.features.base.di.modules;

import com.j1j2.jposmvvm.data.model.ShopInfo;
import com.j1j2.jposmvvm.features.base.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alienzxh on 16-3-11.
 */
@Module
public class ShopModule {

    private ShopInfo shopInfo;

    public ShopModule(ShopInfo shopInfo) {
        this.shopInfo = shopInfo;
    }

    @Provides
    @ActivityScope
    ShopInfo userInfo() {
        return this.shopInfo;
    }
}
