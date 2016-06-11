package com.j1j2.jposmvvm.features.di.components;

import com.j1j2.jposmvvm.features.base.di.ActivityScope;
import com.j1j2.jposmvvm.features.di.modules.StockTakePicturesModule;
import com.j1j2.jposmvvm.features.ui.StockTakePicturesActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-6-8.
 */
@ActivityScope
@Subcomponent(modules = StockTakePicturesModule.class)
public interface StockTakePicturesComponent {
    void inject(StockTakePicturesActivity stockTakePicturesActivity);
}
