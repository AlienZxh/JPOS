package com.j1j2.jposmvvm.features.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.JPOSApplicationLike;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.databinding.ActivityStorageOrderBinding;
import com.j1j2.jposmvvm.features.actions.StorageActionCreator;
import com.j1j2.jposmvvm.features.actions.SupplierActionCreator;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.components.StorageComponent;
import com.j1j2.jposmvvm.features.di.modules.StorageModule;
import com.j1j2.jposmvvm.features.stores.StorageStore;
import com.j1j2.jposmvvm.features.stores.SupplierStore;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-7-18.
 */
public class StorageOrderActivity extends BaseActivity implements StorageOrderFragment.StorageOrderFragmentListener, StorageOrderCreateFragment.StorageOrderCreateFragmentListener, SuppliersFragment.SuppliersFragmentListener {

    ActivityStorageOrderBinding binding;

    StorageComponent storageComponent;

    @Inject
    StorageActionCreator storageActionCreator;
    @Inject
    StorageStore storageStore;
    @Inject
    SupplierActionCreator supplierActionCreator;
    @Inject
    SupplierStore supplierStore;
    @Inject
    Navigate navigate;

    @Inject
    Dispatcher dispatcher;

    private StorageOrderFragment storageOrderFragment;
    private StorageOrderCreateFragment storageOrderCreateFragment;
    private SuppliersFragment suppliersFragment;

    @Override
    public void setActionBarTitle(String title) {
        binding.actionBarTitle.setText(title);
    }

    @Override
    public void showCreateStorageOrderActionBarBtn() {
        binding.createStorageOrderBtn.setVisibility(View.VISIBLE);
        binding.createSupplierBtn.setVisibility(View.GONE);
    }

    @Override
    public void showNoActionBarBtn() {
        binding.createStorageOrderBtn.setVisibility(View.GONE);
        binding.createSupplierBtn.setVisibility(View.GONE);
    }

    @Override
    public void showCreateSupplierActionBarBtn() {
        binding.createStorageOrderBtn.setVisibility(View.GONE);
        binding.createSupplierBtn.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setupActivityComponent() {
        storageComponent = JPOSApplicationLike.get().getShopComponent().plus(new StorageModule(this));
        storageComponent.inject(this);
    }

    @Override
    public StorageComponent getComponent() {
        return storageComponent;
    }

    @Override
    public StorageOrderCreateFragment getStorageOrderCreateFragment() {
        return storageOrderCreateFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_storage_order);
    }

    @Override
    protected void initViews() {
        storageOrderFragment = new StorageOrderFragment();
        loadRootFragment(R.id.fragmentLayout, storageOrderFragment);
        storageOrderCreateFragment = new StorageOrderCreateFragment();
        suppliersFragment = new SuppliersFragment();

    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

    }

    @Override
    public void onRxError(@NonNull RxError error) {
        Snackbar.make(binding.rootLayout, error.getThrowable().getMessage(), Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onRxViewRegistered() {
        if (storageOrderFragment != null)
            dispatcher.subscribeRxView(storageOrderFragment);
        if (suppliersFragment != null)
            dispatcher.subscribeRxView(suppliersFragment);
    }

    @Override
    public void onRxViewUnRegistered() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (storageOrderFragment != null)
            dispatcher.unsubscribeRxView(storageOrderFragment);
        if (suppliersFragment != null)
            dispatcher.unsubscribeRxView(suppliersFragment);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Arrays.asList(storageStore, supplierStore);

//    return null;
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Arrays.asList(storageStore, supplierStore);
//    return null;
    }

    public void navigateToCreateStorageOrder(View v) {
        if (storageOrderCreateFragment == null)
            storageOrderCreateFragment = new StorageOrderCreateFragment();
        startForResult(storageOrderCreateFragment, StorageOrderFragment.REQ_CODE);
    }

    public void navigateToSuppliers(View v) {
        if (suppliersFragment == null)
            suppliersFragment = new SuppliersFragment();
        startForResult(suppliersFragment, StorageOrderCreateFragment.REQ_CODE);
    }


    public void createStorageOrder(View v) {
        storageOrderCreateFragment.createStorageOrder();
    }

    public void showSelectTimeDialog(View v) {
        storageOrderCreateFragment.showSelectTimeDialog();
    }

    public void createSupplier(View v) {
        suppliersFragment.showAddSupplierDialog();
    }

    @Override
    public void onBackPressedSupport() {
        if (getTopFragment() == storageOrderCreateFragment) {
            storageOrderCreateFragment.onBackPressedSupport();
        } else {
            super.onBackPressedSupport();
        }
    }
}
