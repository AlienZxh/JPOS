package com.j1j2.jposmvvm.features.di.modules;

import android.app.Activity;

import com.j1j2.jposmvvm.common.constants.UIState;
import com.j1j2.jposmvvm.features.base.di.ActivityScope;
import com.j1j2.jposmvvm.features.viewmodel.UIViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alienzxh on 16-6-8.
 */
@Module
public class StockTakePicturesModule {

    private Activity activity;

    public StockTakePicturesModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity activity() {
        return this.activity;
    }

    @Provides
    @ActivityScope
    UIViewModel uiViewModel() {
        return new UIViewModel(UIState.STATE_NORMAL);
    }
}
