package com.j1j2.jposmvvm.features.base.di.components;

import android.app.Activity;

import com.j1j2.jposmvvm.features.base.di.ActivityScope;
import com.j1j2.jposmvvm.features.base.di.modules.ActivityModule;
import com.j1j2.jposmvvm.features.ui.IndexActivity;
import com.j1j2.jposmvvm.features.ui.LanuchActivity;

import dagger.Subcomponent;

/**
 * Created by alienzxh on 16-3-5.
 */
@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();

    void inject(LanuchActivity lanuchActivity);

    void inject(IndexActivity indexActivity);
}
