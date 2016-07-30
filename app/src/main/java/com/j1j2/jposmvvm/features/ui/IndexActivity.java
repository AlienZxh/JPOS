package com.j1j2.jposmvvm.features.ui;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.data.model.ShopInfo;
import com.j1j2.jposmvvm.databinding.ActivityIndexBinding;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.JPOSApplication;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.base.di.modules.ActivityModule;
import com.yatatsu.autobundle.AutoBundleField;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by alienzxh on 16-5-23.
 */
public class IndexActivity extends BaseActivity {

    ActivityIndexBinding binding;

    private Realm realm;

    @AutoBundleField
    int shopId;

    @Inject
    Navigate navigate;

    @Override
    protected void setupActivityComponent() {
        IndexActivityAutoBundle.bind(this, getIntent());
        JPOSApplication.get(this).getAppComponent().plus(new ActivityModule(this)).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        realm = Realm.getDefaultInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_index);
    }

    @Override
    protected void initViews() {
        binding.setShopInfo(realm.where(ShopInfo.class).equalTo("ShopId", shopId).findFirst());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        showExitDialog();
    }

    public void switchAccount(View v) {
        showSwitchAccountDialog();
    }

    public void exitApp(View v) {
        onBackPressed();
    }

    private void showSwitchAccountDialog() {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setMessage("确定切换账号？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        navigate.navigateToLoginActivity(IndexActivity.this, null, true);
                    }
                })
                .create().show();
    }


    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setMessage("确定退出？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create().show();
    }

    public void navigateToStock(View v) {
        navigate.navigateToStockSortActivity(this, null, false);
    }

    public void navigateToPhoto(View v) {
        navigate.navigateToStockNoPicturesActivity(this, null, false);
    }

    public void navigateToStorage(View v) {
        navigate.navigateToStorageOrderActivity(this, null, false);
    }
}
