package com.j1j2.jposmvvm.features.ui;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.constants.UIState;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.StockCheckOrder;
import com.j1j2.jposmvvm.data.model.StorageOrder;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.ActivityStockcheckOrderBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StockCheckActionCreator;
import com.j1j2.jposmvvm.features.actions.StockCheckActions;
import com.j1j2.jposmvvm.features.actions.StorageActions;
import com.j1j2.jposmvvm.features.adapter.StockCheckOrderAdapter;
import com.j1j2.jposmvvm.features.adapter.StorageOrderAdapter;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.modules.StockCheckModule;
import com.j1j2.jposmvvm.features.stores.StockCheckStore;
import com.j1j2.jposmvvm.features.stores.StockStore;
import com.j1j2.jposmvvm.features.stores.StorageStore;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-9-19.
 */
public class StockCheckOrderActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {

    ActivityStockcheckOrderBinding binding;

    @Inject
    StockCheckActionCreator stockCheckActionCreator;
    @Inject
    StockCheckStore stockCheckStore;
    @Inject
    StockStore stockStore;
    @Inject
    Navigate navigate;
    @Inject
    Dispatcher dispatcher;
    @Inject
    Toastor toastor;

    private int whichItem;

    private int pageIndex = 1;
    private int pageCount = 0;

    private StockCheckOrderAdapter adapter;

    @Override
    protected void setupActivityComponent() {
        JPOSApplication.get(this).getShopComponent().plus(new StockCheckModule(this)).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stockcheck_order);
    }

    @Override
    protected void initViews() {
        //_____________________________________________________________________
        binding.storageOrderList.setRefreshingColorResources(R.color.colorPrimary);
        binding.storageOrderList.setLayoutManager(new LinearLayoutManager(this));
        binding.storageOrderList.setAdapterWithProgress(adapter = new StockCheckOrderAdapter(this));
        binding.storageOrderList.getRecyclerView().setPadding(AutoUtils.getPercentHeightSize(20), 0,
                AutoUtils.getPercentHeightSize(20), AutoUtils.getPercentHeightSize(20));
        binding.storageOrderList.addItemDecoration(new DividerDecoration(Color.TRANSPARENT, AutoUtils.getPercentHeightSize(20)));
        adapter.setMore(R.layout.view_more_footer, this);
        adapter.setNoMore(R.layout.view_nomore_footer);
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        binding.storageOrderList.setRefreshListener(this);
        adapter.setOnItemClickListener(this);
        onRefresh();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case StockCheckStore.ID:
                switch (change.getRxAction().getType()) {
                    case StockCheckActions.QUERYSTOCKCHECKORDERS:
                        WebReturn<PageManager<List<StockCheckOrder>>> stockCheckOrderWebReturn =
                                (WebReturn<PageManager<List<StockCheckOrder>>>) change.getRxAction().get(Keys.QUERYSTOCKCHECKORDERS_WEBRETURN);
                        if (stockCheckOrderWebReturn.isValue()) {
                            pageCount = stockCheckOrderWebReturn.getDetail().getPageCount();
                            if (stockCheckOrderWebReturn.getDetail().getTotalCount() <= 0) {
                                binding.storageOrderList.showEmpty();
                            } else if (pageIndex == pageCount) {
                                adapter.addAll(stockCheckOrderWebReturn.getDetail().getList());
                                adapter.stopMore();
                            } else {
                                adapter.addAll(stockCheckOrderWebReturn.getDetail().getList());
                                pageIndex++;
                            }
                        } else {
                            adapter.pauseMore();
                            toastor.showSingletonToast(stockCheckOrderWebReturn.getErrorMessage());
                        }
                        break;
                    case StockCheckActions.CREATESTOCKCHECKORDER:
                        WebReturn<String> stringWebReturn = (WebReturn<String>) change.getRxAction().get(Keys.CREATESTOCKCHECKORDER_WEBRETURN);
                        if (stringWebReturn.isValue())
                            onRefresh();
                        else
                            toastor.showSingletonToast(stringWebReturn.getErrorMessage());
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

    }

    @Override
    public void onRxViewUnRegistered() {

    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Arrays.asList(stockStore, stockCheckStore);
//        return Collections.<RxStore>singletonList(stockCheckStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Arrays.asList(stockStore, stockCheckStore);
//        return Collections.<RxStore>singletonList(stockCheckStore);
    }

    public void createStockCheckOrder(View v) {
        whichItem = 0;
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("请选择新开盘点单类型")
                .setSingleChoiceItems(new String[]{"全部盘点单", "部分盘点单"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        whichItem = which;
                    }
                })
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        stockCheckActionCreator.createStockCheckOrder(whichItem == 0 ? 1 : 2);
                    }
                })
                .create().show();
    }

    @Override
    public void onItemClick(int position) {
        if (adapter.getItem(position).getState() != 2)
            navigate.navigateToStockCheckOrderDetailActivity(this, null, false, adapter.getItem(position).getStockCheckOrderId());
        else
            toastor.showSingletonToast("已完成盘点单无法修改");
    }

    @Override
    public void onLoadMore() {
        stockCheckActionCreator.queryStockCheckOrders(pageIndex, 10);
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        adapter.clear();
        stockCheckActionCreator.queryStockCheckOrders(pageIndex, 10);
    }
}
