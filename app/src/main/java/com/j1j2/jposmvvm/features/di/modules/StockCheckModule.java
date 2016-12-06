package com.j1j2.jposmvvm.features.di.modules;

import android.app.Activity;

import com.j1j2.jposmvvm.common.constants.UIState;
import com.j1j2.jposmvvm.features.base.di.ActivityScope;
import com.j1j2.jposmvvm.features.viewmodel.UIViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alienzxh on 16-9-19.
 */
@Module
public class StockCheckModule {
    private Activity activity;

    public StockCheckModule(Activity activity) {
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
