package com.j1j2.jposmvvm.features.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.JPOSApplicationLike;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.constants.Constants;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.data.model.CashPuzzyQuery;
import com.j1j2.jposmvvm.data.model.CashPuzzyQueryStock;
import com.j1j2.jposmvvm.data.model.CashPuzzyQueryUser;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.ActivityCashBinding;
import com.j1j2.jposmvvm.features.actions.CashActionCreator;
import com.j1j2.jposmvvm.features.actions.CashActions;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.components.CashComponent;
import com.j1j2.jposmvvm.features.di.modules.CashModule;
import com.j1j2.jposmvvm.features.event.BarCodeEvent;
import com.j1j2.jposmvvm.features.scanner.camera.activity.CaptureActivity;
import com.j1j2.jposmvvm.features.stores.CashStore;
import com.j1j2.jposmvvm.features.stores.StockStore;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by alienzxh on 16-8-1.
 */
public class CashActivity extends BaseActivity implements CashSearchFragment.CashSearchFragmentListener, CashOrderFragment.CashOrderFragmentListener, TextWatcher, View.OnFocusChangeListener {

    ActivityCashBinding binding;

    CashComponent cashComponent;

    @Inject
    CashActionCreator cashActionCreator;
    @Inject
    CashStore cashStore;
    @Inject
    Dispatcher dispatcher;
    @Inject
    Navigate navigate;
    @Inject
    Toastor toastor;

    @Inject
    StockStore stockStore;

    private Realm realm;

    CashOrderFragment cashOrderFragment;
    CashSearchFragment cashSearchFragment;

    List<Integer> stockIdList;

    @Override
    protected void setupActivityComponent() {
        cashComponent = JPOSApplicationLike.get().getShopComponent().plus(new CashModule(this));
        cashComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        realm = Realm.getDefaultInstance();
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cash);
        binding.setActivity(this);
    }

    @Override
    protected void initViews() {
        binding.editSearch.clearFocus();
        binding.editSearch.addTextChangedListener(this);
        binding.editSearch.setOnFocusChangeListener(this);
        //__________________________________________________________________
        cashOrderFragment = new CashOrderFragment();
        loadRootFragment(R.id.fragmentLayout, cashOrderFragment);
        cashSearchFragment = new CashSearchFragment();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case CashStore.ID:
                switch (change.getRxAction().getType()) {
                    case CashActions.SCANQUERY:
                        WebReturn<CashPuzzyQuery> cashPuzzyQueryWebReturn =
                                (WebReturn<CashPuzzyQuery>) change.getRxAction().get(Keys.SCANQUERY_WEBRETURN);
                        if (cashPuzzyQueryWebReturn.isValue()) {
                            if (cashPuzzyQueryWebReturn.getDetail().getReturnType() == 1) {
                                if (cashPuzzyQueryWebReturn.getDetail().getQueryStockResult() == null || cashPuzzyQueryWebReturn.getDetail().getQueryStockResult().size() <= 0)
                                    toastor.showSingletonToast("未找到该商品");
                                else {
                                    cashOrderFragment.addStock(cashPuzzyQueryWebReturn.getDetail().getQueryStockResult().get(0));
                                }
                            } else if (cashPuzzyQueryWebReturn.getDetail().getReturnType() == 2) {
                                realm.beginTransaction();
                                CashPuzzyQueryUser cashPuzzyQueryUser = realm.copyToRealmOrUpdate(cashPuzzyQueryWebReturn.getDetail().getQueryUserResult());
                                realm.commitTransaction();
                                cashOrderFragment.refreshMember(cashPuzzyQueryUser);
                            }
                        } else {
                            toastor.showSingletonToast(cashPuzzyQueryWebReturn.getErrorMessage());
                        }
                        break;
                }
                break;
        }
    }

    @Override
    public void onRxError(@NonNull RxError error) {
        Snackbar.make(binding.rootLayout, error.getThrowable().getMessage(), Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onRxViewRegistered() {
        if (cashOrderFragment != null)
            dispatcher.subscribeRxView(cashOrderFragment);
        if (cashSearchFragment != null)
            dispatcher.subscribeRxView(cashSearchFragment);
    }

    @Override
    public void onRxViewUnRegistered() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        if (cashOrderFragment != null)
            dispatcher.unsubscribeRxView(cashOrderFragment);
        if (cashSearchFragment != null)
            dispatcher.unsubscribeRxView(cashSearchFragment);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Arrays.asList(cashStore, stockStore);

    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Arrays.asList(cashStore, stockStore);

    }


    @Override
    public String getKey() {
        return binding.editSearch.getText().toString();
    }

    @Override
    public void onSearchFinish() {
        binding.editSearch.setText("");
        binding.editSearch.clearFocus();
        binding.createProductBtn.setVisibility(View.GONE);
    }

    @Override
    public CashComponent getComponent() {
        return cashComponent;
    }

    @Override
    public List<Integer> getStockIdList() {
        return this.stockIdList == null ? new ArrayList<Integer>() : stockIdList;
    }

    @Override
    public void selectStock(CashPuzzyQueryStock cashPuzzyQueryStock) {
        cashOrderFragment.addStock(cashPuzzyQueryStock);
    }

    @Override
    public void selectMember(CashPuzzyQueryUser cashPuzzyQueryUser) {
        cashOrderFragment.refreshMember(cashPuzzyQueryUser);
    }

    @Override
    public void setStockIdList(List<Integer> stockIdList) {
        this.stockIdList = stockIdList;
    }


    @Override
    public void showSearchActionBar() {
        binding.createProductBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {

        } else {
            cashSearchFragment.onRefresh();
        }


    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (!getTopFragment().equals(cashSearchFragment)) {
                start(cashSearchFragment);
            }

        }
    }

    public void navigateToCaptureActivityForResult(View v) {
//        binding.editSearch.requestFocus();
        navigate.navigateToCaptureActivityForResult(this, null, false, CaptureActivity.SCANNER_REQUESTCODE);
    }

    public void navigateToMemberDetail(View v) {
        if (cashOrderFragment.getCashOrder() == null)
            return;
        if (cashOrderFragment.getCashOrder().getCashPuzzyQueryUser() == null)
            return;
        navigate.navigateToMemberDetailActivity(this, null, false, cashOrderFragment.getCashOrder().getCashPuzzyQueryUser().getUserId());
    }

    public void navigateToCashOrderPayWithCash(View v) {
        if (cashOrderFragment.getCashOrder().getCashOrderStocks() == null || cashOrderFragment.getCashOrder().getCashOrderStocks().size() <= 0) {
            toastor.showSingletonToast("请添加结账商品");
            return;
        }
        navigate.navigateToCashOrderPayActivity(this, null, false, cashOrderFragment.getCashOrder().getOrderNO(), Constants.CASHPAY);
    }

    public void navigateToCashOrderPayWithAliPay(View v) {
        if (cashOrderFragment.getCashOrder().getCashOrderStocks() == null || cashOrderFragment.getCashOrder().getCashOrderStocks().size() <= 0) {
            toastor.showSingletonToast("请添加结账商品");
            return;
        }
        navigate.navigateToCashOrderPayActivity(this, null, false, cashOrderFragment.getCashOrder().getOrderNO(), Constants.ALIPAY);
    }

    public void navigateToCashOrderPayWithWeXinPay(View v) {
        if (cashOrderFragment.getCashOrder().getCashOrderStocks() == null || cashOrderFragment.getCashOrder().getCashOrderStocks().size() <= 0) {
            toastor.showSingletonToast("请添加结账商品");
            return;
        }
        navigate.navigateToCashOrderPayActivity(this, null, false, cashOrderFragment.getCashOrder().getOrderNO(), Constants.WEXINPAY);
    }

    public void deleteMember(View v) {
        cashOrderFragment.deleteMember();
    }

    public void createProduct(View v) {
        cashSearchFragment.onBackPressedSupport();
        navigate.navigateToStockProductDetailActivity(this, null, false, 0, true, Constants.FROM_CASH_ORDER, 0, 0, cashOrderFragment.getCashOrder().getOrderNO());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CaptureActivity.SCANNER_REQUESTCODE && resultCode == RESULT_OK) {
//            binding.editSearch.setText(data.getStringExtra("result"));
            cashActionCreator.scanQuery(data.getStringExtra("result"));
        } else {
//            binding.editSearch.clearFocus();
        }
    }

    @Override
    public void onBackPressedSupport() {

        if (getTopFragment() == cashSearchFragment) {
            cashSearchFragment.onBackPressedSupport();
        } else
            super.onBackPressedSupport();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onBarCodeEvent(final BarCodeEvent event) {
//        if (!getTopFragment().equals(cashSearchFragment)) {
//            start(cashSearchFragment);
//        }
//        binding.editSearch.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                binding.editSearch.setText(event.getBarCode());
//            }
//        }, 300);
        cashActionCreator.scanQuery(event.getBarCode());
    }
}
