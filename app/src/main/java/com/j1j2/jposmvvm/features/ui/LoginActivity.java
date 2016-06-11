package com.j1j2.jposmvvm.features.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.constants.ProcessButtonState;
import com.j1j2.jposmvvm.common.constants.UIState;
import com.j1j2.jposmvvm.data.api.body.LoginBody;
import com.j1j2.jposmvvm.data.model.ShopInfo;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.ActivityLoginBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.ShopActionCreator;
import com.j1j2.jposmvvm.features.actions.ShopActions;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.JPOSApplication;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.modules.LoginModule;
import com.j1j2.jposmvvm.features.stores.ShopStore;
import com.j1j2.jposmvvm.features.viewmodel.UIViewModel;
import com.orhanobut.logger.Logger;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by alienzxh on 16-5-5.
 */

public class LoginActivity extends BaseActivity  {
    ActivityLoginBinding binding;

    private Realm realm;

    @Inject
    ShopActionCreator shopActionCreator;
    @Inject
    ShopStore shopStore;
    @Inject
    Navigate navigate;
    @Inject
    UIViewModel uiViewModel;

    @Override
    protected void setupActivityComponent() {
        JPOSApplication.get(this).getAppComponent().plus(new LoginModule(this)).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        realm = Realm.getDefaultInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setMUIViewModel(uiViewModel);
    }

    @Override
    protected void initViews() {
        RealmResults<ShopInfo> shopInfoRealmResults = realm.where(ShopInfo.class).findAllSorted("UpdateTime", Sort.DESCENDING);
        if (shopInfoRealmResults.size() > 0) {
            binding.loginEditStore.setText(shopInfoRealmResults.first().getShopName());
            binding.loginEditAccount.setText(shopInfoRealmResults.first().getClerkAccount());
        }

    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case ShopStore.ID:
                switch (change.getRxAction().getType()) {
                    case ShopActions.LOGIN:
                        WebReturn<ShopInfo> userInfoWebReturn =
                                (WebReturn<ShopInfo>) change.getRxAction().get(Keys.SHOPINFO_WEBRETURN);
                        if (userInfoWebReturn.isValue()) {
                            uiViewModel.setUiState(UIState.STATE_CONTENT);
                            navigate.navigateToIndexActivity(this, null, true, userInfoWebReturn.getDetail().getShopId());
                        } else {
                            uiViewModel.setErrorMessage(userInfoWebReturn.getErrorMessage());
                            uiViewModel.setUiState(UIState.STATE_ERROR);
                            Snackbar.make(binding.rootLayout, userInfoWebReturn.toString(),
                                    Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                }
                break;
        }
    }

    @Override
    public void onRxError(@NonNull RxError error) {
        uiViewModel.setErrorMessage(error.getThrowable().getMessage());
        uiViewModel.setUiState(UIState.STATE_ERROR);
        Logger.e(getLocalClassName() + " error:" + error.getThrowable().toString());
        Snackbar.make(binding.rootLayout, error.getThrowable().getMessage(), Snackbar.LENGTH_SHORT)
                .show();
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
        return Collections.<RxStore>singletonList(shopStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.<RxStore>singletonList(shopStore);
    }

    public void login(View v) {
        if (binding.loginBtn.getProgress() == ProcessButtonState.STATE_ERROR) {
            binding.loginBtn.setProgress(ProcessButtonState.STATE_NORMAL);
            return;
        }
        if (binding.loginEditStore.testValidity()
                && binding.loginEditAccount.testValidity()
                && binding.loginEditPassword.testValidity()) {
            uiViewModel.setLoadingMessage("登录中");
            uiViewModel.setUiState(UIState.STATE_LOADING);

            LoginBody loginBody = new LoginBody();
            loginBody.setAccount(binding.loginEditStore.getText().toString());
            loginBody.setUserName(binding.loginEditAccount.getText().toString());
            loginBody.setPassword(binding.loginEditPassword.getText().toString());
            shopActionCreator.login(loginBody);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
