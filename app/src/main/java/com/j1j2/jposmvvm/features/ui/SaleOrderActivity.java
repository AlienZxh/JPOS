package com.j1j2.jposmvvm.features.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.JPOSApplicationLike;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.SaleOrder;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.ActivitySaleOrderBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.SaleStatisticActionCreator;
import com.j1j2.jposmvvm.features.actions.SaleStatisticActions;
import com.j1j2.jposmvvm.features.adapter.SaleOrderAdapter;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.modules.SaleStatisticModule;
import com.j1j2.jposmvvm.features.stores.SaleStatisticStore;
import com.j1j2.jposmvvm.features.viewmodel.UIViewModel;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.yatatsu.autobundle.AutoBundleField;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-8-15.
 */
public class SaleOrderActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {

    ActivitySaleOrderBinding binding;

    @Inject
    SaleStatisticStore saleStatisticStore;
    @Inject
    SaleStatisticActionCreator saleStatisticActionCreator;
    @Inject
    Toastor toastor;
    @Inject
    Navigate navigate;
    @Inject
    UIViewModel uiViewModel;
    @Inject
    Dispatcher dispatcher;


    @AutoBundleField
    String beginTime;
    @AutoBundleField
    String endTime;

    SaleOrderAdapter adapter;

    int pageIndex = 1;
    int pageSize = 15;
    int pageCount = 0;

    @Override
    protected void setupActivityComponent() {
        SaleOrderActivityAutoBundle.bind(this, getIntent());
        JPOSApplicationLike.get().getShopComponent().plus(new SaleStatisticModule(this)).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sale_order);
    }

    @Override
    protected void initViews() {
        binding.actionBarTitle.setText(beginTime.substring(0, 10) + "销售记录");
        //_______________________________________________________________________
        binding.ordersList.setRefreshingColorResources(R.color.colorPrimary);
        binding.ordersList.setLayoutManager(new LinearLayoutManager(this));
        binding.ordersList.setAdapterWithProgress(adapter = new SaleOrderAdapter(this));
//        binding.ordersList.getRecyclerView().setPadding(AutoUtils.getPercentHeightSize(20), 0,
//                AutoUtils.getPercentHeightSize(20), AutoUtils.getPercentHeightSize(20));
        binding.ordersList.addItemDecoration(new DividerDecoration(0xffd4d4d4, AutoUtils.getPercentHeightSize(1)));
        adapter.setMore(R.layout.view_more_footer, this);
        adapter.setNoMore(R.layout.view_nomore_footer);
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        adapter.setOnItemClickListener(this);
        binding.ordersList.setRefreshListener(this);
        onRefresh();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case SaleStatisticStore.ID:
                switch (change.getRxAction().getType()) {
                    case SaleStatisticActions.LOADSALEORDERS:
                        WebReturn<PageManager<List<SaleOrder>>> pageManagerWebReturnWebReturn =
                                (WebReturn<PageManager<List<SaleOrder>>>) change.getRxAction().get(Keys.LOADSALEORDERS_WEBRETURN);
                        if (pageManagerWebReturnWebReturn.isValue()) {
                            pageCount = pageManagerWebReturnWebReturn.getDetail().getPageCount();
                            if (pageManagerWebReturnWebReturn.getDetail().getTotalCount() <= 0) {
                                binding.ordersList.showEmpty();
                            } else if (pageIndex == pageCount) {
                                adapter.addAll(pageManagerWebReturnWebReturn.getDetail().getList());
                                adapter.stopMore();
                            } else {
                                adapter.addAll(pageManagerWebReturnWebReturn.getDetail().getList());
                                pageIndex++;
                            }
                        } else {
                            adapter.pauseMore();
                            toastor.showSingletonToast(pageManagerWebReturnWebReturn.getErrorMessage());
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
    public void onLoadMore() {
        saleStatisticActionCreator.loadSaleOrders(pageIndex, pageSize, beginTime, endTime);
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        pageIndex = 1;
        saleStatisticActionCreator.loadSaleOrders(pageIndex, pageSize, beginTime, endTime);
    }

    @Override
    public void onItemClick(int position) {
        navigate.navigateToSaleOrderDetailActivity(this, null, false, adapter.getItem(position).getOrderID());
    }
}
