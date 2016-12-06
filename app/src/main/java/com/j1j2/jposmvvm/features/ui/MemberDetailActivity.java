package com.j1j2.jposmvvm.features.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.data.model.CashPuzzyQueryUser;
import com.j1j2.jposmvvm.databinding.ActivityMemberDetailBinding;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.yatatsu.autobundle.AutoBundleField;

import java.util.List;

import io.realm.Realm;

/**
 * Created by alienzxh on 16-8-2.
 */
public class MemberDetailActivity extends BaseActivity {

    ActivityMemberDetailBinding binding;

    @AutoBundleField
    int userId;

    Realm realm;

    @Override
    protected void setupActivityComponent() {
        MemberDetailActivityAutoBundle.bind(this, getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        realm = Realm.getDefaultInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_member_detail);
    }

    @Override
    protected void initViews() {
        CashPuzzyQueryUser cashPuzzyQueryUser = realm.where(CashPuzzyQueryUser.class).equalTo("UserId", userId).findFirst();
        binding.setMember(cashPuzzyQueryUser);
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
}
