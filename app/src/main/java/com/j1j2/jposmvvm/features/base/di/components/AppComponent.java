package com.j1j2.jposmvvm.features.base.di.components;

import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.features.base.di.modules.ActionModule;
import com.j1j2.jposmvvm.features.base.di.modules.ActivityModule;
import com.j1j2.jposmvvm.features.base.di.modules.AppModule;
import com.j1j2.jposmvvm.features.base.di.modules.ShopModule;
import com.j1j2.jposmvvm.features.base.di.modules.StoreModule;
import com.j1j2.jposmvvm.features.di.components.LoginComponent;
import com.j1j2.jposmvvm.features.di.modules.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alienzxh on 16-3-5.
 */
@Singleton
@Component(modules = {AppModule.class, ActionModule.class, StoreModule.class})
public interface AppComponent {

    JPOSApplication application();

    ActivityComponent plus(ActivityModule activityModule);

    ShopComponent plus(ShopModule shopModule);

    LoginComponent plus(LoginModule loginModule);
}
