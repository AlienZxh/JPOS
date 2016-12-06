package com.j1j2.jposmvvm.features.di.components;

import com.j1j2.jposmvvm.features.base.di.ActivityScope;
import com.j1j2.jposmvvm.features.di.modules.PrintProductPriceLabelModule;
import com.j1j2.jposmvvm.features.ui.PrintLabelOrderCreateActivity;
import com.j1j2.jposmvvm.features.ui.PrintLabelOrderDetailsActivity;
import com.j1j2.jposmvvm.features.ui.PrintLabelProductsFragment;
import com.j1j2.jposmvvm.features.ui.PrintLabelSearchFragment;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-10-22.
 */
@ActivityScope
@Subcomponent(modules = PrintProductPriceLabelModule.class)
public interface PrintProductPriceLabelComponent {
    void inject(PrintLabelOrderCreateActivity printLabelOrderCreateActivity);

    void inject(PrintLabelOrderDetailsActivity printLabelOrderDetailsActivity);

    void inject(PrintLabelProductsFragment printLabelProductsFragment);

    void inject(PrintLabelSearchFragment printLabelSearchFragment);
}
