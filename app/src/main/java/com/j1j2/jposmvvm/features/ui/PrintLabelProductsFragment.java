package com.j1j2.jposmvvm.features.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
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
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.PrintPriceLabelProduct;
import com.j1j2.jposmvvm.data.model.ProductDetail;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.FragmentPrintlabelProductsBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.PrintProductPriceLabelActionCreator;
import com.j1j2.jposmvvm.features.actions.PrintProductPriceLabelActions;
import com.j1j2.jposmvvm.features.adapter.PrintLabelProductAdapter;
import com.j1j2.jposmvvm.features.base.BaseFragment;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.base.di.HasComponent;
import com.j1j2.jposmvvm.features.di.components.PrintProductPriceLabelComponent;
import com.j1j2.jposmvvm.features.event.CreateStockPrintLabelEvent;
import com.j1j2.jposmvvm.features.event.RefreshStockPrintLabelEvent;
import com.j1j2.jposmvvm.features.stores.PrintProductPriceLabelStore;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-10-23.
 */

public class PrintLabelProductsFragment extends BaseFragment implements RxViewDispatch, RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, PrintLabelProductAdapter.PrintLabelProductAdapterListener {


    public interface PrintLabelProductsFragmentListener extends HasComponent<PrintProductPriceLabelComponent> {
        int getOrderId();


    }

    PrintLabelProductsFragmentListener listener;

    FragmentPrintlabelProductsBinding binding;

    @Inject
    PrintProductPriceLabelActionCreator printProductPriceLabelActionCreator;
    @Inject
    PrintProductPriceLabelStore printProductPriceLabelStore;
    @Inject
    Toastor toastor;
    @Inject
    Navigate navigate;

    private int pageIndex = 1;
    private int pageCount = 0;

    PrintLabelProductAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (PrintLabelProductsFragmentListener) activity;
    }

    @Override
    protected void setupActivityComponent() {
        listener.getComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_printlabel_products, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.productsList.setRefreshingColorResources(R.color.colorPrimary);
        binding.productsList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.productsList.setAdapterWithProgress(adapter = new PrintLabelProductAdapter(getContext()));
        binding.productsList.getRecyclerView().setPadding(AutoUtils.getPercentHeightSize(20), 0,
                AutoUtils.getPercentHeightSize(20), AutoUtils.getPercentHeightSize(180));
        binding.productsList.addItemDecoration(new DividerDecoration(Color.TRANSPARENT, AutoUtils.getPercentHeightSize(10)));
        adapter.setMore(R.layout.view_more_footer, this);
        adapter.setNoMore(R.layout.view_nomore_footer);
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        binding.productsList.setRefreshListener(this);
        adapter.setListener(this);
        onRefresh();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case PrintProductPriceLabelStore.ID:
                switch (change.getRxAction().getType()) {
                    case PrintProductPriceLabelActions.QUERYPRINTPRICELABELORDERDETAILS:
                        WebReturn<PageManager<List<PrintPriceLabelProduct>>> pageManagerWebReturn =
                                (WebReturn<PageManager<List<PrintPriceLabelProduct>>>) change.getRxAction().get(Keys.QUERYPRINTPRICELABELORDERDETAILS_WEBRETURN);
                        if (pageManagerWebReturn.isValue()) {
                            pageCount = pageManagerWebReturn.getDetail().getPageCount();
                            if (pageManagerWebReturn.getDetail().getTotalCount() <= 0) {
                                binding.productsList.showEmpty();
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
                    case PrintProductPriceLabelActions.SCANSTOCK:
                        WebReturn<List<PrintPriceLabelProduct>> listWebReturn =
                                (WebReturn<List<PrintPriceLabelProduct>>) change.getRxAction().get(Keys.PRINTPRICELABELSCANSTOCK_WEBRETURN);
                        if (listWebReturn.isValue()) {
                            if (listWebReturn.getDetail() != null && listWebReturn.getDetail().size() > 0)
                                onSelectStockAction(listWebReturn.getDetail().get(0));
                            else
                                toastor.showSingletonToast("未搜索到商品");
                        } else {
                            toastor.showSingletonToast(listWebReturn.getErrorMessage());
                        }
                        break;
                    case PrintProductPriceLabelActions.REMOVEPRINTPRICELABELPRDOUCT:
                        WebReturn<String> stringWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.REMOVEPRINTPRICELABELPRDOUCT_WEBRETURN);
                        if (stringWebReturn.isValue())
                            onRefresh();
                        else
                            toastor.showSingletonToast(stringWebReturn.getErrorMessage());
                        break;

                    case PrintProductPriceLabelActions.CREATEPRINTPRICELABELPRODUCT:
                        WebReturn<String> createWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.CREATEPRINTPRICELABELPRODUCT_WEBRETURN);
                        if (createWebReturn.isValue())
                            onRefresh();
                        else
                            toastor.showSingletonToast(createWebReturn.getErrorMessage());
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
        pageIndex = 1;
        adapter.clear();
        printProductPriceLabelActionCreator.queryPrintPriceLabelOrderDetails(pageIndex, 15, listener.getOrderId());
    }

    @Override
    public void onLoadMore() {
        printProductPriceLabelActionCreator.queryPrintPriceLabelOrderDetails(pageIndex, 15, listener.getOrderId());
    }

    @Override
    public void imgClickAction(int position, PrintPriceLabelProduct data) {
        navigate.navigateToStockProductDetailActivity(getActivity(), null, false, data.getStockId(), false, Constants.FROM_PRINTLABEL_PRODUCTS, position, 0, "");
    }

    @Override
    public void deleteAction(int position, final PrintPriceLabelProduct data) {
        new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .setTitle("提示")
                .setMessage("确认删除该商品吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        printProductPriceLabelActionCreator.removePrintPriceLabelPrdouct(data.getDetailId());
                    }
                })
                .create().show();
    }

    public void onSelectStockAction(PrintPriceLabelProduct printPriceLabelProduct) {
        printProductPriceLabelActionCreator.createPrintPriceLabelProduct(listener.getOrderId(), printPriceLabelProduct.getStockId());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onCreateStockPrintLabelEvent(final CreateStockPrintLabelEvent event) {
        if (event.getProductDetail() != null)
            printProductPriceLabelActionCreator.createPrintPriceLabelProduct(listener.getOrderId(), event.getProductDetail().getStockId());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onRefreshStockPrintLabelEvent(final RefreshStockPrintLabelEvent event) {
        ProductDetail productDetail = event.getProductDetail();
        if (productDetail != null) {
            PrintPriceLabelProduct printPriceLabelProduct = adapter.getItem(event.getPosition());
            printPriceLabelProduct.setBarCode(productDetail.getBarCode());
            printPriceLabelProduct.setMemberPrice(productDetail.getMemberPrice());
            printPriceLabelProduct.setName(productDetail.getName());
            printPriceLabelProduct.setPrimeCost(productDetail.getLastCost());
            printPriceLabelProduct.setRetialPrice(productDetail.getRetailPrice());
            printPriceLabelProduct.setSpec(productDetail.getSpec());
            printPriceLabelProduct.setThumbImg(productDetail.getThumbImgUrl());
            printPriceLabelProduct.setUnit(productDetail.getUnit());

            adapter.notifyItemChanged(event.getPosition());
        }

    }
}
