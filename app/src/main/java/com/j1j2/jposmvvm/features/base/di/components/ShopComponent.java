package com.j1j2.jposmvvm.features.base.di.components;

import com.j1j2.jposmvvm.features.base.di.ShopScope;
import com.j1j2.jposmvvm.features.base.di.modules.ShopModule;
import com.j1j2.jposmvvm.features.di.components.LoginComponent;
import com.j1j2.jposmvvm.features.di.components.StockNoPicturesComponent;
import com.j1j2.jposmvvm.features.di.components.StockTakePicturesComponent;
import com.j1j2.jposmvvm.features.di.modules.LoginModule;
import com.j1j2.jposmvvm.features.di.modules.StockNoPicturesModule;
import com.j1j2.jposmvvm.features.di.modules.StockTakePicturesModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-11.
 */
@ShopScope
@Subcomponent(modules = {ShopModule.class})
public interface ShopComponent {

    StockNoPicturesComponent plus(StockNoPicturesModule stockNoPicturesModule);

    StockTakePicturesComponent plus(StockTakePicturesModule stockTakePicturesModule);
}
