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
import android.widget.EditText;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.JPOSApplicationLike;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.ProductPriceLabelOrder;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.ActivityPrintlabelorderCreateBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.PrintProductPriceLabelActionCreator;
import com.j1j2.jposmvvm.features.actions.PrintProductPriceLabelActions;
import com.j1j2.jposmvvm.features.actions.StorageActionCreator;
import com.j1j2.jposmvvm.features.adapter.PrintLabelOrderAdapter;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.modules.PrintProductPriceLabelModule;
import com.j1j2.jposmvvm.features.stores.PrintProductPriceLabelStore;
import com.j1j2.jposmvvm.features.stores.StorageStore;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-10-23.
 */

public class PrintLabelOrderCreateActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {

    ActivityPrintlabelorderCreateBinding binding;

    @Inject
    StorageActionCreator storageActionCreator;
    @Inject
    StorageStore storageStore;
    @Inject
    PrintProductPriceLabelActionCreator printProductPriceLabelActionCreator;
    @Inject
    PrintProductPriceLabelStore printProductPriceLabelStore;
    @Inject
    Navigate navigate;
    @Inject
    Toastor toastor;
    @Inject
    Dispatcher dispatcher;


    private int pageIndex = 1;
    private int pageCount = 0;

    PrintLabelOrderAdapter adapter;

    @Override
    protected void setupActivityComponent() {
        JPOSApplicationLike.get().getShopComponent().plus(new PrintProductPriceLabelModule(this)).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_printlabelorder_create);
    }

    @Override
    protected void initViews() {
        binding.ordersList.setRefreshingColorResources(R.color.colorPrimary);
        binding.ordersList.setLayoutManager(new LinearLayoutManager(this));
        binding.ordersList.setAdapterWithProgress(adapter = new PrintLabelOrderAdapter(this));
        binding.ordersList.getRecyclerView().setPadding(AutoUtils.getPercentHeightSize(20), 0,
                AutoUtils.getPercentHeightSize(20), AutoUtils.getPercentHeightSize(20));
        binding.ordersList.getRecyclerView().setClipToPadding(false);
        binding.ordersList.addItemDecoration(new DividerDecoration(Color.TRANSPARENT, AutoUtils.getPercentHeightSize(20)));
        adapter.setMore(R.layout.view_more_footer, this);
        adapter.setNoMore(R.layout.view_nomore_footer);
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        binding.ordersList.setRefreshListener(this);
        adapter.setOnItemClickListener(this);
        onRefresh();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case PrintProductPriceLabelStore.ID:
                switch (change.getRxAction().getType()) {
                    case PrintProductPriceLabelActions.QUERYPRODUCTPRICELABELORDERS:
                        WebReturn<PageManager<List<ProductPriceLabelOrder>>> pageManagerWebReturn =
                                (WebReturn<PageManager<List<ProductPriceLabelOrder>>>) change.getRxAction().get(Keys.QUERYPRODUCTPRICELABELORDERS_WEBRETURN);
                        if (pageManagerWebReturn.isValue()) {
                            pageCount = pageManagerWebReturn.getDetail().getPageCount();
                            if (pageManagerWebReturn.getDetail().getTotalCount() <= 0) {
                                binding.ordersList.showEmpty();
                            } else if (pageIndex == pageCount) {
                                adapter.addAll(pageManagerWebReturn.getDetail().getList());
                                adapter.stopMore();
                            } else {
                                adapter.addAll(pageManagerWebReturn.getDetail().getList());
                                pageIndex++;
                            }
                        } else {
                            adapter.pauseMore();
                            toastor.showSingletonToast(pageManagerWebReturn.getErrorMessage());
                        }
                        break;
                    case PrintProductPriceLabelActions.CREATEPRODUCTPRICELABELORDER:
                        onRefresh();

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
        return Arrays.asList(storageStore, printProductPriceLabelStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Arrays.asList(storageStore, printProductPriceLabelStore);
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        adapter.clear();
        printProductPriceLabelActionCreator.queryProductPriceLabelOrders(pageIndex, 15);
    }

    @Override
    public void onItemClick(int position) {
        navigate.navigateToPrintLabelOrderDetailsActivity(this, null, false, adapter.getItem(position).getOrderId());
    }

    @Override
    public void onLoadMore() {
        printProductPriceLabelActionCreator.queryProductPriceLabelOrders(pageIndex, 15);
    }

    public void creatPrintLabelOrder(View view) {
        final View rootView = getLayoutInflater().inflate(R.layout.view_pricelabel_createdialog, null, false);
        new AlertDialog.Builder(this)
                .setTitle("确认创建云打印单吗?")
                .setView(rootView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText noteEdit = (EditText) rootView.findViewById(R.id.noteEdit);
                        printProductPriceLabelActionCreator.createProductPriceLabelOrder(noteEdit.getText().toString());
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }
}
