package com.j1j2.jposmvvm.features.base.di.modules;

import android.app.Activity;


import com.j1j2.jposmvvm.features.base.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alienzxh on 16-3-5.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity activity() {
        return this.activity;
    }
}
