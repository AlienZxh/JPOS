package com.j1j2.jposmvvm.features.di.components;

import com.j1j2.jposmvvm.features.base.di.ActivityScope;
import com.j1j2.jposmvvm.features.di.modules.SaleStatisticModule;
import com.j1j2.jposmvvm.features.ui.SaleOrderActivity;
import com.j1j2.jposmvvm.features.ui.SaleOrderDetailActivity;
import com.j1j2.jposmvvm.features.ui.SaleStatisticActivity;
import com.j1j2.jposmvvm.features.ui.SaleStatisticInMonthActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-8-15.
 */
@ActivityScope
@Subcomponent(modules = SaleStatisticModule.class)
public interface SaleStatisticComponent {
    void inject(SaleStatisticActivity saleStatisticActivity);

    void inject(SaleOrderActivity saleOrderActivity);

    void inject(SaleOrderDetailActivity saleOrderDetailActivity);

    void inject(SaleStatisticInMonthActivity saleStatisticInMonthActivity);
}
