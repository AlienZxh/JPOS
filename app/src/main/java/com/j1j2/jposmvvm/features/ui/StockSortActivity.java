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
import android.widget.TextView;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.JPOSApplicationLike;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.constants.Constants;
import com.j1j2.jposmvvm.databinding.ActivityStockSortBinding;
import com.j1j2.jposmvvm.features.actions.StockActionCreator;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.components.StockNoPicturesComponent;
import com.j1j2.jposmvvm.features.di.modules.StockNoPicturesModule;
import com.j1j2.jposmvvm.features.scanner.camera.activity.CaptureActivity;
import com.j1j2.jposmvvm.features.stores.StockStore;
import com.j1j2.jposmvvm.features.viewmodel.UIViewModel;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-6-13.
 */
public class StockSortActivity extends BaseActivity implements TextWatcher, View.OnFocusChangeListener, StockSortFragment.StockSortFragmentListener, StockProductSearchFragment.StockNoPicturesSearchFragmentListener {

    ActivityStockSortBinding binding;

    private StockNoPicturesComponent stockNoPicturesComponent;

    @Inject
    StockActionCreator stockActionCreator;
    @Inject
    StockStore stockStore;
    @Inject
    Navigate navigate;
    @Inject
    UIViewModel uiViewModel;
    @Inject
    Dispatcher dispatcher;

    private StockSortFragment stockSortFragment;
    private StockProductSearchFragment stockProductSearchFragment;
    private boolean showNoPicture = false;

    @Override
    protected void setupActivityComponent() {
        stockNoPicturesComponent = JPOSApplicationLike.get().getShopComponent().plus(new StockNoPicturesModule(this));
        stockNoPicturesComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stock_sort);
    }

    @Override
    protected void initViews() {

        binding.editSearch.clearFocus();
        binding.editSearch.addTextChangedListener(this);
        binding.editSearch.setOnFocusChangeListener(this);

        stockProductSearchFragment = StockProductSearchFragmentAutoBundle.createFragmentBuilder(StockProductSearchFragment.FROM_SORT).build();
        stockSortFragment = new StockSortFragment();
        loadRootFragment(R.id.fragmentLayout, stockSortFragment);


        stockActionCreator.queryStockCategory(String.valueOf(showNoPicture));
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

    }

    @Override
    public void onRxError(@NonNull RxError error) {

    }

    @Override
    public void onRxViewRegistered() {
        if (stockSortFragment != null)
            dispatcher.subscribeRxView(stockSortFragment);
        if (stockProductSearchFragment != null)
            dispatcher.subscribeRxView(stockProductSearchFragment);
    }

    @Override
    public void onRxViewUnRegistered() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (stockSortFragment != null)
            dispatcher.unsubscribeRxView(stockSortFragment);
        if (stockProductSearchFragment != null)
            dispatcher.unsubscribeRxView(stockProductSearchFragment);
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
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (!getTopFragment().equals(stockProductSearchFragment)) {
                navigateToSearch("", "");
            }

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {

        } else {
            stockProductSearchFragment.setKeyWord(s.toString());
            stockProductSearchFragment.onRefresh();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CaptureActivity.SCANNER_REQUESTCODE && resultCode == RESULT_OK) {
            binding.editSearch.setText(data.getStringExtra("result"));
        } else {
            binding.editSearch.clearFocus();
        }
    }

    public void navigateToCaptureActivityForResult(View v) {
        binding.editSearch.requestFocus();
        navigate.navigateToCaptureActivityForResult(this, null, false, CaptureActivity.SCANNER_REQUESTCODE);
    }

    public void switchState(View v) {
        TextView textView = (TextView) v;
        if (showNoPicture) {
            textView.setText("无图");
            showNoPicture = false;
        } else {
            textView.setText("全部");
            showNoPicture = true;
        }
        stockActionCreator.queryStockCategory(String.valueOf(showNoPicture));
        if (getTopFragment().equals(stockProductSearchFragment)) {
            stockProductSearchFragment.onRefresh();
        }
    }


    @Override
    public void onSearchFinish() {
        showNormalActionBar();
        binding.editSearch.setText("");
        binding.editSearch.clearFocus();
    }

    @Override
    public StockNoPicturesComponent getComponent() {
        return stockNoPicturesComponent;
    }

    @Override
    public void navigateToSearch(String topCategory, String subCategory) {
        stockProductSearchFragment.setShowNoPicture(showNoPicture);
        stockProductSearchFragment.setTopCategory(topCategory);
        stockProductSearchFragment.setSubCategory(subCategory);

        start(stockProductSearchFragment);
    }

    @Override
    public void showSearchActionBar() {
        binding.actionbarNewProductBtn.setVisibility(View.VISIBLE);
        binding.actionbarNoPicBtn.setVisibility(View.GONE);
    }


    public void showNormalActionBar() {
        binding.actionbarNewProductBtn.setVisibility(View.GONE);
        binding.actionbarNoPicBtn.setVisibility(View.VISIBLE);
    }

    public void createProduct(View v) {
        navigate.navigateToStockProductDetailActivity(this, null, false, 0, true, Constants.FROM_STOCK_SORT, 0,0,"");
    }
}
