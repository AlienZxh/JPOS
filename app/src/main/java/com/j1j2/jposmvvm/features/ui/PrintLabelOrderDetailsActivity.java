package com.j1j2.jposmvvm.features.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.constants.Constants;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.data.model.PrintPriceLabelProduct;
import com.j1j2.jposmvvm.databinding.ActivityPrintlabelDetailsBinding;
import com.j1j2.jposmvvm.features.actions.PrintProductPriceLabelActionCreator;
import com.j1j2.jposmvvm.features.actions.StockActionCreator;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.components.PrintProductPriceLabelComponent;
import com.j1j2.jposmvvm.features.di.modules.PrintProductPriceLabelModule;
import com.j1j2.jposmvvm.features.event.BarCodeEvent;
import com.j1j2.jposmvvm.features.event.CreateStockPrintLabelEvent;
import com.j1j2.jposmvvm.features.scanner.camera.activity.CaptureActivity;
import com.j1j2.jposmvvm.features.stores.PrintProductPriceLabelStore;
import com.j1j2.jposmvvm.features.stores.StockStore;
import com.yatatsu.autobundle.AutoBundleField;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-10-23.
 */

public class PrintLabelOrderDetailsActivity extends BaseActivity implements TextWatcher, View.OnFocusChangeListener, PrintLabelProductsFragment.PrintLabelProductsFragmentListener, PrintLabelSearchFragment.PrintLabelSearchFragmentListener {

    ActivityPrintlabelDetailsBinding binding;

    @AutoBundleField
    int orderId;

    @Inject
    PrintProductPriceLabelActionCreator printProductPriceLabelActionCreator;
    @Inject
    PrintProductPriceLabelStore printProductPriceLabelStore;
    @Inject
    StockActionCreator stockActionCreator;
    @Inject
    StockStore stockStore;
    @Inject
    Dispatcher dispatcher;
    @Inject
    Navigate navigate;
    @Inject
    Toastor toastor;

    PrintLabelProductsFragment printLabelProductsFragment;
    PrintLabelSearchFragment printLabelSearchFragment;

    PrintProductPriceLabelComponent printProductPriceLabelComponent;

    @Override
    protected void setupActivityComponent() {
        PrintLabelOrderDetailsActivityAutoBundle.bind(this, getIntent());
        printProductPriceLabelComponent = JPOSApplication.get(this).getShopComponent().plus(new PrintProductPriceLabelModule(this));
        printProductPriceLabelComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_printlabel_details);
    }

    @Override
    protected void initViews() {
        binding.editSearch.clearFocus();
        binding.editSearch.addTextChangedListener(this);
        binding.editSearch.setOnFocusChangeListener(this);
        //___________________________________
        printLabelProductsFragment = new PrintLabelProductsFragment();
        loadRootFragment(R.id.fragmentLayout, printLabelProductsFragment);
        printLabelSearchFragment = new PrintLabelSearchFragment();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

    }

    @Override
    public void onRxError(@NonNull RxError error) {

    }

    @Override
    public void onRxViewRegistered() {
        if (printLabelProductsFragment != null)
            dispatcher.subscribeRxView(printLabelProductsFragment);
        if (printLabelSearchFragment != null)
            dispatcher.subscribeRxView(printLabelSearchFragment);
    }

    @Override
    public void onRxViewUnRegistered() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (printLabelProductsFragment != null)
            dispatcher.unsubscribeRxView(printLabelProductsFragment);
        if (printLabelSearchFragment != null)
            dispatcher.unsubscribeRxView(printLabelSearchFragment);
    }

    @Override
    public void onSearchFinish() {
        binding.editSearch.setText("");
        binding.editSearch.clearFocus();
    }

    @Override
    public String getKey() {
        return binding.editSearch.getText().toString();
    }

    @Override
    public void onSelectStockAction(PrintPriceLabelProduct printPriceLabelProduct) {
        if (printLabelProductsFragment != null)
            printLabelProductsFragment.onSelectStockAction(printPriceLabelProduct);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.<RxStore>singletonList(stockStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.<RxStore>singletonList(stockStore);
    }

    @Override
    public int getOrderId() {
        return orderId;
    }

    @Override
    public PrintProductPriceLabelComponent getComponent() {
        return printProductPriceLabelComponent;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {

        } else {
            printLabelSearchFragment.onRefresh();
        }


    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (!getTopFragment().equals(printLabelSearchFragment)) {
                start(printLabelSearchFragment);
            }

        }
    }

    public void navigateToCaptureActivityForResult(View v) {
//        binding.editSearch.requestFocus();
        navigate.navigateToCaptureActivityForResult(this, null, false, CaptureActivity.SCANNER_REQUESTCODE);
    }


    @Override
    public void onBackPressedSupport() {
        if (getTopFragment() == printLabelSearchFragment)
            printLabelSearchFragment.onBackPressedSupport();
        else
            super.onBackPressedSupport();
    }

    public void createProduct(View v) {
        if (getTopFragment().equals(printLabelSearchFragment) && printLabelProductsFragment != null) {
            printLabelSearchFragment.onBackPressedSupport();
        }

        navigate.navigateToStockProductDetailActivity(this, null, false, 0, true, Constants.FROM_PRINTLABEL_PRODUCTS, 0, orderId, "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CaptureActivity.SCANNER_REQUESTCODE && resultCode == RESULT_OK) {

            printProductPriceLabelActionCreator.scanStock(data.getStringExtra("result"));
        } else {
//            binding.editSearch.clearFocus();
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onBarCodeEvent(final BarCodeEvent event) {

        printProductPriceLabelActionCreator.scanStock(event.getBarCode());
    }


}
