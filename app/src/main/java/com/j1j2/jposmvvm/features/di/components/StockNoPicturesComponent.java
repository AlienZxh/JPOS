package com.j1j2.jposmvvm.features.di.components;

import com.j1j2.jposmvvm.features.base.di.ActivityScope;
import com.j1j2.jposmvvm.features.di.modules.StockNoPicturesModule;
import com.j1j2.jposmvvm.features.ui.StockNoPicturesActivity;
import com.j1j2.jposmvvm.features.ui.StockNoPicturesSearchFragment;
import com.j1j2.jposmvvm.features.ui.StockNoPicturesSortFragment;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-6-7.
 */
@ActivityScope
@Subcomponent(modules = StockNoPicturesModule.class)
public interface StockNoPicturesComponent {
    void inject(StockNoPicturesActivity stockNoPicturesActivity);

    void inject(StockNoPicturesSortFragment stockNoPicturesSortFragment);

    void inject(StockNoPicturesSearchFragment stockNoPicturesSearchFragment);
}
