package com.j1j2.jposmvvm.features.di.components;

import com.j1j2.jposmvvm.features.base.di.ActivityScope;
import com.j1j2.jposmvvm.features.di.modules.LoginModule;
import com.j1j2.jposmvvm.features.ui.LoginActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-5-5.
 */
@ActivityScope
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
