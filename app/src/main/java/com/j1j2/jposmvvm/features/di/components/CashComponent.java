package com.j1j2.jposmvvm.features.di.components;

import com.j1j2.jposmvvm.features.base.di.ActivityScope;
import com.j1j2.jposmvvm.features.di.modules.CashModule;
import com.j1j2.jposmvvm.features.ui.CashActivity;
import com.j1j2.jposmvvm.features.ui.CashOrderFragment;
import com.j1j2.jposmvvm.features.ui.CashOrderPayActivity;
import com.j1j2.jposmvvm.features.ui.CashSearchFragment;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-8-1.
 */
@ActivityScope
@Subcomponent(modules = CashModule.class)
public interface CashComponent {
    void inject(CashActivity cashActivity);

    void inject(CashSearchFragment cashSearchFragment);

    void inject(CashOrderFragment cashOrderFragment);

    void inject(CashOrderPayActivity cashOrderPayActivity);
}
