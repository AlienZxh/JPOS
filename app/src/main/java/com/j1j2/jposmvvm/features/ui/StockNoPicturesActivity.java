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
import com.j1j2.jposmvvm.JPOSApplicationLike;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.constants.UIState;
import com.j1j2.jposmvvm.databinding.ActivityStockNopicturesBinding;
import com.j1j2.jposmvvm.features.actions.StockActionCreator;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.components.StockNoPicturesComponent;
import com.j1j2.jposmvvm.features.di.modules.StockNoPicturesModule;
import com.j1j2.jposmvvm.features.scanner.camera.activity.CaptureActivity;
import com.j1j2.jposmvvm.features.stores.StockStore;
import com.j1j2.jposmvvm.features.viewmodel.UIViewModel;
import com.orhanobut.logger.Logger;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-6-7.
 */
public class StockNoPicturesActivity extends BaseActivity implements StockNoPicturesSortFragment.StockNoPicturesSortFragmentListener, StockProductSearchFragment.StockNoPicturesSearchFragmentListener, TextWatcher, View.OnFocusChangeListener {


    ActivityStockNopicturesBinding binding;

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

    private StockNoPicturesSortFragment stockNoPicturesSortFragment;
    private StockProductSearchFragment stockProductSearchFragment;

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stock_nopictures);
    }

    @Override
    protected void initViews() {
        binding.editSearch.clearFocus();
        binding.editSearch.addTextChangedListener(this);
        binding.editSearch.setOnFocusChangeListener(this);

        stockProductSearchFragment = StockProductSearchFragmentAutoBundle.createFragmentBuilder(StockProductSearchFragment.FROM_TAKEPICTURE).build();
        stockNoPicturesSortFragment = new StockNoPicturesSortFragment();
        loadRootFragment(R.id.fragmentLayout, stockNoPicturesSortFragment);

        stockActionCreator.queryStockCategory(String.valueOf(true));
    }

//    @Override
//    public void onAttachFragment(Fragment fragment) {
//        super.onAttachFragment(fragment);
////        Note that Fragment B C D should not be instantiated from one Fragment, if so the following C or D might not be registered.
////        Because the class name is used as a tag in subscribeRxView:
//        if (fragment instanceof RxViewDispatch) {
//            RxViewDispatch viewDispatch = (RxViewDispatch) fragment;
//            viewDispatch.onRxViewRegistered();
//            dispatcher.subscribeRxView(viewDispatch);
//        }
//    }


    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

    }

    @Override
    public void onRxError(@NonNull RxError error) {
        uiViewModel.setErrorMessage(error.getThrowable().getMessage());
        uiViewModel.setUiState(UIState.STATE_ERROR);
        Logger.e(getLocalClassName() + " error:" + error.getThrowable().toString());
        Snackbar.make(binding.rootLayout, error.getThrowable().getMessage(), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onRxViewRegistered() {
        if (stockNoPicturesSortFragment != null)
            dispatcher.subscribeRxView(stockNoPicturesSortFragment);
        if (stockProductSearchFragment != null)
            dispatcher.subscribeRxView(stockProductSearchFragment);

    }

    @Override
    public void onRxViewUnRegistered() {
//        if (stockNoPicturesSortFragment != null)
//            dispatcher.unsubscribeRxView(stockNoPicturesSortFragment);
//        if (stockProductSearchFragment != null)
//            dispatcher.unsubscribeRxView(stockProductSearchFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (stockNoPicturesSortFragment != null)
            dispatcher.unsubscribeRxView(stockNoPicturesSortFragment);
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

    public void refreshList(View v) {
        if (stockNoPicturesSortFragment.isVisible())
            stockNoPicturesSortFragment.onRefresh();
        if (stockProductSearchFragment.isVisible())
            stockProductSearchFragment.onRefresh();
    }

    public void navigateToCaptureActivityForResult(View v) {
        binding.editSearch.requestFocus();
        navigate.navigateToCaptureActivityForResult(this, null, false, CaptureActivity.SCANNER_REQUESTCODE);
    }

    @Override
    public StockNoPicturesComponent getComponent() {
        return stockNoPicturesComponent;
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
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (!getTopFragment().equals(stockProductSearchFragment)) {
                start(stockProductSearchFragment);
            }

        }
    }


    @Override
    public void onSearchFinish() {
        binding.editSearch.setText("");
        binding.editSearch.clearFocus();
    }

    @Override
    public void showSearchActionBar() {

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
}
