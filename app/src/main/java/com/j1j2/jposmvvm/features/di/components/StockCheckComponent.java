package com.j1j2.jposmvvm.features.di.components;

import com.j1j2.jposmvvm.features.base.di.ActivityScope;
import com.j1j2.jposmvvm.features.di.modules.StockCheckModule;
import com.j1j2.jposmvvm.features.ui.StockCheckOrderActivity;
import com.j1j2.jposmvvm.features.ui.StockCheckOrderDetailActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-9-19.
 */
@ActivityScope
@Subcomponent(modules = StockCheckModule.class)
public interface StockCheckComponent {
    void inject(StockCheckOrderActivity stockCheckOrderActivity);

    void inject(StockCheckOrderDetailActivity stockCheckOrderActivity);
}
