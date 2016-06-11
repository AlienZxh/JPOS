package com.j1j2.jposmvvm.features.ui;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.databinding.ActivityLanuchBinding;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.JPOSApplication;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.base.di.modules.ActivityModule;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;

import java.util.List;

import javax.inject.Inject;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LanuchActivity extends BaseActivity {
    ActivityLanuchBinding binding;

    @Inject
    Navigate navigate;

    @Override
    protected void setupActivityComponent() {
        JPOSApplication.get(this).getAppComponent().plus(new ActivityModule(this)).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);

//        UpdateBuilder.create()
//                .strategy(new UpdateStrategy() {
//                    @Override
//                    public boolean isShowUpdateDialog(Update update) {
//                        return true;
//                    }
//
//                    @Override
//                    public boolean isAutoInstall() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isShowInstallDialog() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isShowDownloadDialog() {
//                        return false;
//                    }
//                })
//                .checkCB(new UpdateCheckCB() {
//                    @Override
//                    public void hasUpdate(Update update) {
//
//                    }
//
//                    @Override
//                    public void noUpdate() {
//                        navigate.navigateToLoginActivity(LanuchActivity.this, null, true);
//                    }
//
//                    @Override
//                    public void onCheckError(int code, String errorMsg) {
//                        navigate.navigateToLoginActivity(LanuchActivity.this, null, true);
//                    }
//
//                    @Override
//                    public void onUserCancel() {
//                        navigate.navigateToLoginActivity(LanuchActivity.this, null, true);
//                    }
//                })
//                .check(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lanuch);
    }

    @Override
    protected void initViews() {
        navigate.navigateToLoginActivity(LanuchActivity.this, null, true);
    }

    @SuppressLint("InlinedApi")
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

    }

    @Override
    public void onRxError(@NonNull RxError error) {

    }

    @Override
    public void onRxViewRegistered() {

    }

    @Override
    public void onRxViewUnRegistered() {

    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return null;
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return null;
    }
}
