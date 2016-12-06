package com.j1j2.jposmvvm.features.base.di.components;

import com.j1j2.jposmvvm.features.base.di.ShopScope;
import com.j1j2.jposmvvm.features.base.di.modules.ShopModule;
import com.j1j2.jposmvvm.features.di.components.CashComponent;
import com.j1j2.jposmvvm.features.di.components.PrintProductPriceLabelComponent;
import com.j1j2.jposmvvm.features.di.components.SaleStatisticComponent;
import com.j1j2.jposmvvm.features.di.components.StockCheckComponent;
import com.j1j2.jposmvvm.features.di.components.StockNoPicturesComponent;
import com.j1j2.jposmvvm.features.di.components.StockTakePicturesComponent;
import com.j1j2.jposmvvm.features.di.components.StorageComponent;
import com.j1j2.jposmvvm.features.di.modules.CashModule;
import com.j1j2.jposmvvm.features.di.modules.PrintProductPriceLabelModule;
import com.j1j2.jposmvvm.features.di.modules.SaleStatisticModule;
import com.j1j2.jposmvvm.features.di.modules.StockCheckModule;
import com.j1j2.jposmvvm.features.di.modules.StockNoPicturesModule;
import com.j1j2.jposmvvm.features.di.modules.StockTakePicturesModule;
import com.j1j2.jposmvvm.features.di.modules.StorageModule;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-11.
 */
@ShopScope
@Subcomponent(modules = {ShopModule.class})
public interface ShopComponent {

    StockNoPicturesComponent plus(StockNoPicturesModule stockNoPicturesModule);

    StockTakePicturesComponent plus(StockTakePicturesModule stockTakePicturesModule);

    StorageComponent plus(StorageModule storageModule);

    CashComponent plus(CashModule cashModule);

    SaleStatisticComponent plus(SaleStatisticModule saleStatisticModule);

    StockCheckComponent plus(StockCheckModule stockCheckModule);

    PrintProductPriceLabelComponent plus(PrintProductPriceLabelModule printProductPriceLabelModule);
}
