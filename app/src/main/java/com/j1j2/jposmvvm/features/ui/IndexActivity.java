package com.j1j2.jposmvvm.features.ui;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AlertDialog;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.JPOSApplicationLike;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.data.model.IndexModule;
import com.j1j2.jposmvvm.data.model.ShopInfo;
import com.j1j2.jposmvvm.databinding.ActivityIndexBinding;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.base.di.modules.ActivityModule;
import com.yatatsu.autobundle.AutoBundleField;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

import static java.util.Arrays.asList;

/**
 * Created by alienzxh on 16-5-23.
 */
public class IndexActivity extends BaseActivity implements IndexFragment.IndexFragmentListener {

    ActivityIndexBinding binding;

    private Realm realm;

    @AutoBundleField
    int shopId;

    @Inject
    Navigate navigate;

    @Override
    protected void setupActivityComponent() {
        IndexActivityAutoBundle.bind(this, getIntent());
        JPOSApplicationLike.get().getAppComponent().plus(new ActivityModule(this)).inject(this);
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
        binding.viewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return IndexFragmentAutoBundle
                                .createFragmentBuilder(new ArrayList<>(asList(
                                        new IndexModule("商品管理", "库存管理", getString(R.string.ic_stock), 0xff62c7ff),
                                        new IndexModule("商品管理", "商品拍照", getString(R.string.ic_camera), 0xff67be91),
                                        new IndexModule("商品管理", "商品入库", getString(R.string.ic_storage), 0xffffc233),
                                        new IndexModule("商品管理", "库存盘点", getString(R.string.ic_stockcheck), 0xfff57162),
                                        new IndexModule("商品管理", "标签打印", getString(R.string.ic_label), 0xff9788c1))))
                                .build();
                    case 1:
                        return IndexFragmentAutoBundle
                                .createFragmentBuilder(new ArrayList<>(asList(
                                        new IndexModule("收银管理", "商品收银", getString(R.string.ic_cash), 0xff62c7ff))))
                                .build();
                    case 2:
                        return IndexFragmentAutoBundle
                                .createFragmentBuilder(new ArrayList<>(asList(
                                        new IndexModule("报表管理", "销售报表", getString(R.string.ic_report), 0xff62c7ff))))
                                .build();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "商品管理";
                    case 1:
                        return "收银管理";
                    case 2:
                        return "报表管理";
                }
                return null;
            }
        });
        binding.tablayout.setupWithViewPager(binding.viewpager);
    }

    @Override
    protected void initActivityAnimations() {
        super.initActivityAnimations();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            //________________________________________________________________
            Slide slideTransition = new Slide();
            slideTransition.setSlideEdge(Gravity.BOTTOM);
            slideTransition.setDuration(500);
            getWindow().setEnterTransition(slideTransition);
            getWindow().getEnterTransition().setDuration(500);
        }
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
    public void onBackPressedSupport() {
//        super.onBackPressedSupport();
        showExitDialog();
    }

    public void switchAccount(View v) {
        showSwitchAccountDialog();
    }

    public void exitApp(View v) {
        onBackPressedSupport();
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

    @Override
    public void navigateToStock() {
        navigate.navigateToStockSortActivity(this, null, false);
    }

    @Override
    public void navigateToPhoto() {
        navigate.navigateToStockNoPicturesActivity(this, null, false);
    }

    @Override
    public void navigateToStorage() {
        navigate.navigateToStorageOrderActivity(this, null, false);
    }

    @Override
    public void navigateToCash() {
        navigate.navigateToCashActivity(this, null, false);
    }

    @Override
    public void navigateToReport() {
        navigate.navigateToSaleStatisticActivity(this, null, false);
    }

    @Override
    public void navigateToStockCheck() {
        navigate.navigateToStockCheckOrderActivity(this, null, false);
    }

    @Override
    public void navigateToPrintLabel() {
        navigate.navigateToPrintLabelOrderCreateActivity(this, null, false);
    }
}
