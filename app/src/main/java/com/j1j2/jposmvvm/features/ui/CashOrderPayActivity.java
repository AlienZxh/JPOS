package com.j1j2.jposmvvm.features.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.constants.Constants;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.common.widgets.viewstatemanager.LoadingAndRetryManager;
import com.j1j2.jposmvvm.common.widgets.viewstatemanager.OnLoadingAndRetryListener;
import com.j1j2.jposmvvm.data.model.CashOrder;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.ActivityCashorderPayBinding;
import com.j1j2.jposmvvm.features.actions.CashActionCreator;
import com.j1j2.jposmvvm.features.actions.CashActions;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.modules.CashModule;
import com.j1j2.jposmvvm.features.scanner.camera.activity.CaptureActivity;
import com.j1j2.jposmvvm.features.stores.CashStore;
import com.j1j2.jposmvvm.features.viewmodel.SaleOrderViewModel;
import com.yatatsu.autobundle.AutoBundleField;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by alienzxh on 16-8-2.
 */
public class CashOrderPayActivity extends BaseActivity {

    ActivityCashorderPayBinding binding;

    @AutoBundleField
    String orderNO;
    @AutoBundleField
    int payType;//支付方式： 1:现金支付 2：支付宝支付 3：微信支付

    @Inject
    CashActionCreator cashActionCreator;
    @Inject
    CashStore cashStore;
    @Inject
    Toastor toastor;
    @Inject
    Navigate navigate;

    LoadingAndRetryManager mLoadingAndRetryManager;

    private Realm realm;
    CashOrder cashOrder;

    @Override
    protected void setupActivityComponent() {
        CashOrderPayActivityAutoBundle.bind(this, getIntent());
        JPOSApplication.get(this).getShopComponent().plus(new CashModule(this)).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        realm = Realm.getDefaultInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cashorder_pay);
    }

    @Override
    protected void initViews() {

        cashOrder = realm.where(CashOrder.class).equalTo("OrderNO", orderNO).findFirst();
        binding.setOrderVM(new SaleOrderViewModel(cashOrder, payType));
        //___________________________________________________-
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(binding.contentLayout, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {

            }
        });
        mLoadingAndRetryManager.showContent();
    }


    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case CashStore.ID:
                switch (change.getRxAction().getType()) {
                    case CashActions.SETTLESALEORDER:
                        WebReturn<String> stringWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.SETTLESALEORDER_WEBRETURN);

                        if (stringWebReturn.isValue()) {
                            toastor.showSingleLongToast("结账成功");
                            finish();
                        } else {
                            toastor.showSingletonToast(stringWebReturn.getErrorMessage());
                        }
                        mLoadingAndRetryManager.showContent();
                        break;


                }
                break;
        }

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

    public void cancelPay(View v) {
        finish();
    }

    public void confirmPay(View v) {
        if (payType == Constants.CASHPAY && binding.getOrderVM().getSaleOrderSettleBody().getChange() < 0) {
            toastor.showSingletonToast("付款金额不足");
            return;
        }
        mLoadingAndRetryManager.showLoading();
        cashActionCreator.settleSaleOrder(binding.getOrderVM().getSaleOrderSettleBody());
    }

    public void navigateToCaptureActivityForResult(View v) {
        navigate.navigateToCaptureActivityForResult(this, null, false, CaptureActivity.SCANNER_REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CaptureActivity.SCANNER_REQUESTCODE && resultCode == RESULT_OK) {
            binding.payAuthCodeEdit.setText(data.getStringExtra("result"));
        }
    }
}
