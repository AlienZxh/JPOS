package com.j1j2.jposmvvm.features.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.constants.Constants;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.common.utils.Validate;
import com.j1j2.jposmvvm.data.model.CashPuzzyQuery;
import com.j1j2.jposmvvm.data.model.CashPuzzyQueryStock;
import com.j1j2.jposmvvm.data.model.CashPuzzyQueryUser;
import com.j1j2.jposmvvm.data.model.ProductDetail;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.FragmentCashSearchBinding;
import com.j1j2.jposmvvm.features.actions.CashActionCreator;
import com.j1j2.jposmvvm.features.actions.CashActions;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.adapter.CashSearchAdapter;
import com.j1j2.jposmvvm.features.base.BaseFragment;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.base.di.HasComponent;
import com.j1j2.jposmvvm.features.di.components.CashComponent;
import com.j1j2.jposmvvm.features.stores.CashStore;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by alienzxh on 16-8-1.
 */
public class CashSearchFragment extends BaseFragment implements RxViewDispatch, SwipeRefreshLayout.OnRefreshListener, CashSearchAdapter.CashSearchAdapterListener {

    public interface CashSearchFragmentListener extends HasComponent<CashComponent> {

        String getKey();

        void showSearchActionBar();

        void onSearchFinish();

        List<Integer> getStockIdList();

        void selectStock(CashPuzzyQueryStock cashPuzzyQueryStock);

        void selectMember(CashPuzzyQueryUser cashPuzzyQueryUser);
    }

    private CashSearchFragmentListener listener;

    FragmentCashSearchBinding binding;

    private Realm realm;

    @Inject
    CashActionCreator cashActionCreator;
    @Inject
    CashStore cashStore;
    @Inject
    Toastor toastor;
    @Inject
    Navigate navigate;

    CashSearchAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (CashSearchFragmentListener) context;
    }


    @Override
    protected void setupActivityComponent() {
        listener.getComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        realm = Realm.getDefaultInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.showSearchActionBar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cash_search, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.stockList.setRefreshingColorResources(R.color.colorPrimary);
        binding.stockList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.stockList.setAdapterWithProgress(adapter = new CashSearchAdapter(getContext(), listener.getStockIdList()));
        adapter.setNoMore(R.layout.view_nomore_footer);
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        binding.stockList.setRefreshListener(this);
        adapter.setCashSearchAdapterListener(this);
        onRefresh();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case CashStore.ID:
                switch (change.getRxAction().getType()) {
                    case CashActions.PUZZYQUERYSCAN:
                        WebReturn<CashPuzzyQuery> cashPuzzyQueryWebReturn =
                                (WebReturn<CashPuzzyQuery>) change.getRxAction().get(Keys.PUZZYQUERYSCAN_WEBRETURN);
                        if (cashPuzzyQueryWebReturn.isValue()) {
                            if (cashPuzzyQueryWebReturn.getDetail().getReturnType() == 1) {
                                if (cashPuzzyQueryWebReturn.getDetail().getQueryStockResult() == null || cashPuzzyQueryWebReturn.getDetail().getQueryStockResult().size() <= 0)
                                    binding.stockList.showEmpty();
                                else if (cashPuzzyQueryWebReturn.getDetail().getQueryStockResult().size() == 1) {
                                    listener.selectStock(cashPuzzyQueryWebReturn.getDetail().getQueryStockResult().get(0));
                                    onBackPressedSupport();
                                } else
                                    adapter.addAll(cashPuzzyQueryWebReturn.getDetail().getQueryStockResult());
                            } else if (cashPuzzyQueryWebReturn.getDetail().getReturnType() == 2) {
                                realm.beginTransaction();
                                CashPuzzyQueryUser cashPuzzyQueryUser = realm.copyToRealmOrUpdate(cashPuzzyQueryWebReturn.getDetail().getQueryUserResult());
                                realm.commitTransaction();
                                listener.selectMember(cashPuzzyQueryUser);
                                onBackPressedSupport();
                            }
                        } else {
                            adapter.pauseMore();
                            toastor.showSingletonToast(cashPuzzyQueryWebReturn.getErrorMessage());
                        }
                        break;
                    case CashActions.REFRESHLISTITEM:
                        int fromType = change.getRxAction().get(Keys.REFRESHLISTFROMTYPE);
                        if (fromType == Constants.FROM_CASH_SEARCH) {
                            int position = change.getRxAction().get(Keys.REFRESHLISTPOSITION);
                            ProductDetail productDetail = change.getRxAction().get(Keys.REFRESHLISTPRODUCTDETAIL);
                            CashPuzzyQueryStock cashPuzzyQueryStock = adapter.getItem(position);
                            cashPuzzyQueryStock.setUnit(productDetail.getUnit());
                            cashPuzzyQueryStock.setBarCode(productDetail.getBarCode());
                            cashPuzzyQueryStock.setSmallImgUrl(productDetail.getThumbImgUrl());
                            cashPuzzyQueryStock.setSpec(productDetail.getSpec());
                            cashPuzzyQueryStock.setRetailPrice(productDetail.getRetailPrice());
                            cashPuzzyQueryStock.setMemberPrice(productDetail.getMemberPrice());

                            adapter.notifyItemChanged(position);
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
    public void onRefresh() {
        String key = listener.getKey();

        if (TextUtils.isEmpty(key)) {
            binding.stockList.showEmpty();
        } else {
            adapter.clear();
            cashActionCreator.puzzyQueryScan(key);
        }

    }

    @Override
    public void onSelectStockAction(int position, CashPuzzyQueryStock cashPuzzyQueryStock) {
        listener.selectStock(cashPuzzyQueryStock);
        onBackPressedSupport();
    }

    @Override
    public void onItemClickAction(int position, CashPuzzyQueryStock cashPuzzyQueryStock) {
        navigate.navigateToStockProductDetailActivity(getActivity(), null, false, cashPuzzyQueryStock.getStockId(), false, Constants.FROM_CASH_SEARCH, position, 0, "");
    }

    @Override
    public boolean onBackPressedSupport() {
        listener.onSearchFinish();
        hideSoftInput();
        pop();
        return true;
    }
}
