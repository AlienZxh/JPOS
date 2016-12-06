package com.j1j2.jposmvvm.features.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.constants.Constants;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.StorageStock;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.ActivityStorageProductsBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StorageActionCreator;
import com.j1j2.jposmvvm.features.actions.StorageActions;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.components.StorageComponent;
import com.j1j2.jposmvvm.features.di.modules.StorageModule;
import com.j1j2.jposmvvm.features.event.BarCodeEvent;
import com.j1j2.jposmvvm.features.scanner.camera.activity.CaptureActivity;
import com.j1j2.jposmvvm.features.stores.StockStore;
import com.j1j2.jposmvvm.features.stores.StorageStore;
import com.yatatsu.autobundle.AutoBundleField;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-7-27.
 */
public class StorageProductsActivity extends BaseActivity implements StorageProductsFragment.StorageProductsFragmentListener, StorageSearchFragment.StorageSearchFragmentListener, TextWatcher, View.OnFocusChangeListener {
    ActivityStorageProductsBinding binding;

    @AutoBundleField
    int orderId;

    StorageComponent storageComponent;

    @Inject
    StorageActionCreator storageActionCreator;
    @Inject
    StorageStore storageStore;
    @Inject
    Dispatcher dispatcher;
    @Inject
    Navigate navigate;
    @Inject
    Toastor toastor;

    @Inject
    StockStore stockStore;

    StorageProductsFragment storageProductsFragment;
    StorageSearchFragment storageSearchFragment;
    List<Integer> stockIdList;


    @Override
    protected void setupActivityComponent() {
        StorageProductsActivityAutoBundle.bind(this, getIntent());
        storageComponent = JPOSApplication.get(this).getShopComponent().plus(new StorageModule(this));
        storageComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_storage_products);
    }

    @Override
    protected void initViews() {
        binding.editSearch.clearFocus();
        binding.editSearch.addTextChangedListener(this);
        binding.editSearch.setOnFocusChangeListener(this);
        //___________________________________
        storageProductsFragment = new StorageProductsFragment();
        loadRootFragment(R.id.fragmentLayout, storageProductsFragment);
        storageSearchFragment = new StorageSearchFragment();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case StorageStore.ID:
                switch (change.getRxAction().getType()) {

                    case StorageActions.AUDITSTORAGEORDER:
                        WebReturn<String> auditItemWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.AUDITSTORAGEORDER_WEBRETURN);
                        if (auditItemWebReturn.isValue()) {
                            toastor.showSingletonToast("审核成功");
                            storageActionCreator.refreshStorageOrders();
                            finish();
                        } else {
                            toastor.showSingletonToast(auditItemWebReturn.getErrorMessage());
                        }
                        break;
                    case StorageActions.SCANSTOCKS:
                        WebReturn<PageManager<List<StorageStock>>> productsWebReturn =
                                (WebReturn<PageManager<List<StorageStock>>>) change.getRxAction().get(Keys.SCANSTOCKS_WEBRETURN);
                        if (productsWebReturn.isValue()) {

                            if (productsWebReturn.getDetail().getTotalCount() > 0) {
                                storageProductsFragment.onSelectStockAction(productsWebReturn.getDetail().getList().get(0));
                            } else {
                                toastor.showSingletonToast("未找到该商品");
                            }
                        } else {
                            toastor.showSingletonToast(productsWebReturn.getErrorMessage());
                        }
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
        if (storageProductsFragment != null)
            dispatcher.subscribeRxView(storageProductsFragment);
        if (storageSearchFragment != null)
            dispatcher.subscribeRxView(storageSearchFragment);
    }

    @Override
    public void onRxViewUnRegistered() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (storageProductsFragment != null)
            dispatcher.unsubscribeRxView(storageProductsFragment);
        if (storageSearchFragment != null)
            dispatcher.unsubscribeRxView(storageSearchFragment);
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
    public String getKey() {
        return binding.editSearch.getText().toString();
    }

    @Override
    public void showProductActionBar() {
        binding.actionBarTitle.setText("入库商品");
        binding.aduitBtn.setVisibility(View.VISIBLE);
        binding.createProductBtn.setVisibility(View.GONE);
    }

    @Override
    public void showSearchActionBar() {
        binding.actionBarTitle.setText("选择入库商品");
        binding.aduitBtn.setVisibility(View.GONE);
        binding.createProductBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSearchFinish() {
        showProductActionBar();
        binding.editSearch.setText("");
        binding.editSearch.clearFocus();
    }

    @Override
    public void setStockIdList(List<Integer> stockIdList) {
        this.stockIdList = stockIdList;
    }

    @Override
    public List<Integer> getStockIdList() {
        return this.stockIdList == null ? new ArrayList<Integer>() : this.stockIdList;
    }


    @Override
    public void onSelectStockAction(StorageStock storageStock) {
        storageProductsFragment.onSelectStockAction(storageStock);
    }

    @Override
    public StorageComponent getComponent() {
        return storageComponent;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {

        } else {
            storageSearchFragment.onRefresh();
        }


    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (!getTopFragment().equals(storageSearchFragment)) {
                start(storageSearchFragment);
            }

        }
    }

    public void createProduct(View v) {
        storageSearchFragment.onBackPressedSupport();
        navigate.navigateToStockProductDetailActivity(this, null, false, 0, true, Constants.FROM_STORAGE_PRODUCTS, 0, orderId, "");
    }

    public void auditStorageOrder(View v) {
        storageProductsFragment.auditStorageOrder();


    }


    public void navigateToCaptureActivityForResult(View v) {
//        binding.editSearch.requestFocus();
        navigate.navigateToCaptureActivityForResult(this, null, false, CaptureActivity.SCANNER_REQUESTCODE);
    }

    @Override
    public void onBackPressedSupport() {
        if (getTopFragment() == storageSearchFragment)
            storageSearchFragment.onBackPressedSupport();
        else
            super.onBackPressedSupport();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CaptureActivity.SCANNER_REQUESTCODE && resultCode == RESULT_OK) {
//            binding.editSearch.setText(data.getStringExtra("result"));
            storageActionCreator.scanStocks(1, data.getStringExtra("result"), orderId);
        } else {
//            binding.editSearch.clearFocus();
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onBarCodeEvent(final BarCodeEvent event) {
//        if (!getTopFragment().equals(storageSearchFragment)) {
//            start(storageSearchFragment);
//        }
//        binding.editSearch.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                binding.editSearch.setText(event.getBarCode());
//            }
//        }, 300);
        storageActionCreator.scanStocks(1, event.getBarCode(), orderId);
    }
}
