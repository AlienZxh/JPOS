package com.j1j2.jposmvvm.features.di.components;

import com.j1j2.jposmvvm.features.base.di.ActivityScope;
import com.j1j2.jposmvvm.features.di.modules.StorageModule;
import com.j1j2.jposmvvm.features.ui.StorageOrderActivity;
import com.j1j2.jposmvvm.features.ui.StorageOrderCreateFragment;
import com.j1j2.jposmvvm.features.ui.StorageOrderFragment;
import com.j1j2.jposmvvm.features.ui.StorageProductsActivity;
import com.j1j2.jposmvvm.features.ui.StorageProductsFragment;
import com.j1j2.jposmvvm.features.ui.StorageSearchFragment;
import com.j1j2.jposmvvm.features.ui.SuppliersFragment;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-7-25.
 */
@ActivityScope
@Subcomponent(modules = StorageModule.class)
public interface StorageComponent {
    void inject(StorageOrderActivity storageOrderActivity);

    void inject(StorageOrderFragment storageOrderFragment);

    void inject(StorageOrderCreateFragment storageOrderCreateFragment);

    void inject(SuppliersFragment suppliersFragment);

    void inject(StorageProductsActivity storageProductsActivity);

    void inject(StorageProductsFragment storageProductsFragment);

    void inject(StorageSearchFragment storageSearchFragment);
}
