package com.j1j2.jposmvvm.features.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
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
import com.j1j2.jposmvvm.common.constants.Constants;
import com.j1j2.jposmvvm.common.utils.EmptyUtils;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.ProductDetail;
import com.j1j2.jposmvvm.data.model.StockCheckOrderDetail;
import com.j1j2.jposmvvm.data.model.StockCheckOrderItem;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.ActivityStockcheckOrderdetailBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StockCheckActionCreator;
import com.j1j2.jposmvvm.features.actions.StockCheckActions;
import com.j1j2.jposmvvm.features.adapter.StockCheckProductAdapter;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.modules.StockCheckModule;
import com.j1j2.jposmvvm.features.event.BarCodeEvent;
import com.j1j2.jposmvvm.features.event.CreateStockCheckOrderItemEvent;
import com.j1j2.jposmvvm.features.event.RefreshStockCheckOrderDetailEvent;
import com.j1j2.jposmvvm.features.scanner.camera.activity.CaptureActivity;
import com.j1j2.jposmvvm.features.stores.StockCheckStore;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.yatatsu.autobundle.AutoBundleField;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-9-19.
 */
public class StockCheckOrderDetailActivity extends BaseActivity implements StockCheckProductAdapter.StockCheckProductAdapterListener, RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    ActivityStockcheckOrderdetailBinding binding;

    @Inject
    StockCheckActionCreator stockCheckActionCreator;
    @Inject
    StockCheckStore stockCheckStore;
    @Inject
    Navigate navigate;
    @Inject
    Dispatcher dispatcher;
    @Inject
    Toastor toastor;

    @AutoBundleField
    int stockDetailId;

    private int pageIndex = 1;
    private int pageCount = 0;

    StockCheckProductAdapter adapter;

    ProductDetail newProduct = null;

    @Override
    protected void setupActivityComponent() {
        StockCheckOrderDetailActivityAutoBundle.bind(this, getIntent());
        JPOSApplicationLike.get().getShopComponent().plus(new StockCheckModule(this)).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (newProduct == null)
            binding.productsList.setRefreshing(true, true);
        else {
            StockCheckOrderDetail stockCheckOrderDetail = new StockCheckOrderDetail();
            stockCheckOrderDetail.setStockCheckOrderId(stockDetailId);
            stockCheckOrderDetail.setBarCode(newProduct.getBarCode());
            stockCheckOrderDetail.setStockId(newProduct.getStockId());
            stockCheckOrderDetail.setQuantity(0);
            stockCheckActionCreator.createOrUpdateStockCheckItem(stockCheckOrderDetail);
            newProduct = null;
        }
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stockcheck_orderdetail);
    }

    @Override
    protected void initViews() {
        binding.productsList.setRefreshingColorResources(R.color.colorPrimary);
        binding.productsList.setLayoutManager(new LinearLayoutManager(this));
        binding.productsList.setAdapterWithProgress(adapter = new StockCheckProductAdapter(this, toastor));
        binding.productsList.getRecyclerView().setPadding(AutoUtils.getPercentHeightSize(20), 0,
                AutoUtils.getPercentHeightSize(20), AutoUtils.getPercentHeightSize(180));
        binding.productsList.addItemDecoration(new DividerDecoration(Color.TRANSPARENT, AutoUtils.getPercentHeightSize(10)));
        adapter.setNoMore(R.layout.view_nomore_footer);
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        adapter.setListener(this);
        binding.productsList.setRefreshListener(this);
        binding.productsList.setRefreshing(true, true);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case StockCheckStore.ID:
                switch (change.getRxAction().getType()) {
                    case StockCheckActions.QUERYSTOCKCHECKORDERDETAILS:
                        WebReturn<PageManager<List<StockCheckOrderDetail>>> stockCheckOrderDetailWebReturn =
                                (WebReturn<PageManager<List<StockCheckOrderDetail>>>) change.getRxAction().get(Keys.QUERYSTOCKCHECKORDERDETAILS_WEBRETURN);
                        if (stockCheckOrderDetailWebReturn.isValue()) {
                            pageCount = stockCheckOrderDetailWebReturn.getDetail().getPageCount();
                            if (stockCheckOrderDetailWebReturn.getDetail().getTotalCount() <= 0) {
                                binding.productsList.showEmpty();
                            } else if (pageIndex == pageCount) {
                                adapter.addAll(stockCheckOrderDetailWebReturn.getDetail().getList());
                                adapter.stopMore();
                            } else {
                                adapter.addAll(stockCheckOrderDetailWebReturn.getDetail().getList());
                                pageIndex++;
                            }
                        } else {
                            adapter.pauseMore();
                            toastor.showSingletonToast(stockCheckOrderDetailWebReturn.getErrorMessage());
                        }
                        break;
                    case StockCheckActions.QUERYSTOCKS:
                        WebReturn<List<StockCheckOrderItem>> listWebReturn =
                                (WebReturn<List<StockCheckOrderItem>>) change.getRxAction().get(Keys.QUERYCHECKSTOCKS_WEBRETURN);
                        if (listWebReturn.isValue()) {
                            if (!EmptyUtils.isEmpty(listWebReturn.getDetail())) {
                                StockCheckOrderItem stockCheckOrderItem = listWebReturn.getDetail().get(0);
                                StockCheckOrderDetail stockCheckOrderDetail = new StockCheckOrderDetail();
                                stockCheckOrderDetail.setStockCheckOrderId(stockDetailId);
                                stockCheckOrderDetail.setBarCode(stockCheckOrderItem.getBarCode());
                                stockCheckOrderDetail.setStockId(stockCheckOrderItem.getStockId());
                                stockCheckOrderDetail.setQuantity(stockCheckOrderItem.getCurrentCheckQuantity());
                                stockCheckActionCreator.createOrUpdateStockCheckItem(stockCheckOrderDetail);
                            } else {
                                navigate.navigateToStockProductDetailActivity(this, null, false, 0, true, Constants.FROM_STOCKCHECK_ORDER, 0, 0, "");
                                toastor.showSingletonToast("未搜索到该商品");
                            }

                        } else {
                            toastor.showSingletonToast(listWebReturn.getErrorMessage());
                        }
                        break;
                    case StockCheckActions.CREATEORUPDATESTOCKCHECKITEM:

                        WebReturn<String> creatItemWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.CREATEORUPDATESTOCKCHECKITEM_WEBRETURN);
                        if (creatItemWebReturn.isValue())

                            binding.productsList.setRefreshing(true, true);
                        else
                            toastor.showSingletonToast(creatItemWebReturn.getErrorMessage());
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
    public void modifyAction(int position, StockCheckOrderDetail data) {
        navigate.navigateToStockProductDetailActivity(this, null, false, data.getStockId(), false, Constants.FROM_STOCKCHECK_ORDER, position, 0, "");
    }

    @Override
    public void quantityChangeAction(int position, StockCheckOrderDetail data, double newQuqntity) {
        if (newQuqntity == data.getQuantity())
            return;
        data.setQuantity(newQuqntity);
        stockCheckActionCreator.createOrUpdateStockCheckItem(data);
    }

    @Override
    public void onLoadMore() {
        stockCheckActionCreator.queryStockCheckOrderDetails(stockDetailId, pageIndex, 10);
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        adapter.clear();
        stockCheckActionCreator.queryStockCheckOrderDetails(stockDetailId, pageIndex, 10);

    }

    public void createStock(View v) {
        navigate.navigateToStockProductDetailActivity(this, null, false, 0, true, Constants.FROM_STOCKCHECK_ORDER, 0, 0, "");
    }

    public void navigateToCaptureActivityForResult(View v) {
//        binding.editSearch.requestFocus();
        navigate.navigateToCaptureActivityForResult(this, null, false, CaptureActivity.SCANNER_REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CaptureActivity.SCANNER_REQUESTCODE && resultCode == RESULT_OK) {
//            binding.editSearch.setText(data.getStringExtra("result"));
            if (!binding.productsList.getSwipeToRefresh().isRefreshing())
                stockCheckActionCreator.queryStocks(stockDetailId, data.getStringExtra("result"));
        } else {
//            binding.editSearch.clearFocus();
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onBarCodeEvent(final BarCodeEvent event) {
//        if (!getTopFragment().equals(cashSearchFragment)) {
//            start(cashSearchFragment);
//        }
//        binding.editSearch.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                binding.editSearch.setText(event.getBarCode());
//            }
//        }, 300);
        if (!binding.productsList.getSwipeToRefresh().isRefreshing())
            stockCheckActionCreator.queryStocks(stockDetailId, event.getBarCode());
    }

//    @Subscribe(threadMode = ThreadMode.POSTING)
//    public void onRefreshStockCheckOrderDetailEvent(final RefreshStockCheckOrderDetailEvent event) {
//        binding.productsList.setRefreshing(true, true);
//    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onCreateStockCheckOrderItemEvent(final CreateStockCheckOrderItemEvent event) {
        newProduct = event.getProductDetail();
    }
}
