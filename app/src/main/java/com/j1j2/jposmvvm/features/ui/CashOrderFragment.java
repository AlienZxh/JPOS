package com.j1j2.jposmvvm.features.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.constants.Constants;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.data.model.CashOrder;
import com.j1j2.jposmvvm.data.model.CashPuzzyQueryStock;
import com.j1j2.jposmvvm.data.model.CashPuzzyQueryUser;
import com.j1j2.jposmvvm.data.model.ProductDetail;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.FragmentCashOrderBinding;
import com.j1j2.jposmvvm.features.actions.CashActionCreator;
import com.j1j2.jposmvvm.features.actions.CashActions;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.adapter.CashOrderAdapter;
import com.j1j2.jposmvvm.features.base.BaseFragment;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.base.di.HasComponent;
import com.j1j2.jposmvvm.features.di.components.CashComponent;
import com.j1j2.jposmvvm.features.stores.CashStore;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by alienzxh on 16-8-1.
 */
public class CashOrderFragment extends BaseFragment implements RxViewDispatch, CashOrderAdapter.CashOrderAdapterListener, RealmChangeListener<CashOrder> {

    public interface CashOrderFragmentListener extends HasComponent<CashComponent> {

        void setStockIdList(List<Integer> stockIdList);
    }

    private Realm realm;

    private CashOrderFragmentListener listener;

    FragmentCashOrderBinding binding;


    @Inject
    CashActionCreator cashActionCreator;
    @Inject
    CashStore cashStore;
    @Inject
    Toastor toastor;
    @Inject
    Navigate navigate;

    CashOrderAdapter adapter;
    CashOrder cashOrder;


    double productQuantity = 0;
    double productAmout = 0.0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (CashOrderFragmentListener) context;
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
    public void onDestroy() {
        super.onDestroy();
//        realm.removeAllChangeListeners();
        cashOrder.removeChangeListener(this);
        realm.close();
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cash_order, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.productsList.setRefreshingColorResources(R.color.colorPrimary);
        binding.productsList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.productsList.setAdapterWithProgress(adapter = new CashOrderAdapter(getContext()));
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
        adapter.setCashOrderAdapterListener(this);
        //________________________________________________________________________
        RealmResults<CashOrder> cashOrders = realm.where(CashOrder.class).findAllSorted("UpdateTime", Sort.DESCENDING);
        if (cashOrders == null || cashOrders.size() <= 0) {
            cashActionCreator.querySaleOrderNO();
        } else {
            cashOrder = cashOrders.get(0);
            if (cashOrder.isComplete())
                cashActionCreator.querySaleOrderNO();
            else if (cashOrder.getCashOrderStocks().size() <= 0 && cashOrder.getCashPuzzyQueryUser() == null) {
                cashActionCreator.querySaleOrderNO();
            } else
                showReloadDialog();
        }

    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case CashStore.ID:
                switch (change.getRxAction().getType()) {
                    case CashActions.QUERYSALEORDERNO:
                        WebReturn<String> stringWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.QUERYSALEORDERNO_WEBRETURN);

                        if (stringWebReturn.isValue()) {
                            cashOrder = realm.where(CashOrder.class).equalTo("OrderNO", stringWebReturn.getDetail()).findFirst();
                            cashOrder.addChangeListener(this);
                            loadCashOrder(cashOrder);

                        } else {
                            toastor.showSingletonToast(stringWebReturn.getErrorMessage());
                        }

                        break;
                    case CashActions.REFRESHLISTITEM:
                        int fromType = change.getRxAction().get(Keys.REFRESHLISTFROMTYPE);
                        if (fromType == Constants.FROM_CASH_ORDER) {
                            int position = change.getRxAction().get(Keys.REFRESHLISTPOSITION);
                            ProductDetail productDetail = change.getRxAction().get(Keys.REFRESHLISTPRODUCTDETAIL);
                            CashPuzzyQueryStock cashPuzzyQueryStock = adapter.getItem(position);
                            adapter.setExpandPosition(position);
                            realm.beginTransaction();
                            cashPuzzyQueryStock.setUnit(productDetail.getUnit());
                            cashPuzzyQueryStock.setBarCode(productDetail.getBarCode());
                            cashPuzzyQueryStock.setSmallImgUrl(productDetail.getThumbImgUrl());
                            cashPuzzyQueryStock.setSpec(productDetail.getSpec());
                            cashPuzzyQueryStock.setRetailPrice(productDetail.getRetailPrice());
                            cashPuzzyQueryStock.setMemberPrice(productDetail.getMemberPrice());
                            realm.commitTransaction();
                        }
                        break;
                    case CashActions.SETTLESALEORDER:
                        WebReturn<String> saleOrderWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.SETTLESALEORDER_WEBRETURN);

                        if (saleOrderWebReturn.isValue()) {
                            realm.beginTransaction();
                            cashOrder.setComplete(true);
                            cashOrder.setUpdateTime(new Date());
                            realm.commitTransaction();

                        } else {
                            toastor.showSingletonToast(saleOrderWebReturn.getErrorMessage());
                        }

                        break;
                }
                break;
        }
    }

    public void loadCashOrder(CashOrder cashOrder) {
        adapter.clear();

        RealmList<CashPuzzyQueryStock> cashPuzzyQueryStocks = cashOrder.getCashOrderStocks();

        if (cashPuzzyQueryStocks.size() <= 0)
            binding.productsList.showEmpty();
        else
            adapter.addAll(cashPuzzyQueryStocks);

        List<Integer> stockIdList = new ArrayList<>();
        productQuantity = 0.0;
        productAmout = 0.0;
        for (int i = 0; i < cashPuzzyQueryStocks.size(); i++) {
            if (cashPuzzyQueryStocks.get(i).getStockId() > 0)
                stockIdList.add(cashPuzzyQueryStocks.get(i).getStockId());
            productQuantity += cashPuzzyQueryStocks.get(i).getQuantity();
            productAmout += cashPuzzyQueryStocks.get(i).getRetailPrice() * cashPuzzyQueryStocks.get(i).getQuantity();
        }
        listener.setStockIdList(stockIdList);
        binding.productQuantity.setText("共" + productQuantity + "件商品");
        binding.productAmout.setText("￥" + productAmout);
        //__________________________
        if (cashOrder.getCashPuzzyQueryUser() == null) {
            binding.memberImg.setImageURI("");
            binding.memberName.setText("");
            binding.memberBalance.setText("");
            binding.userLayout.setVisibility(View.GONE);
            return;
        }
        Uri uri = Uri.parse("res://com.j1j2.pifalao/" + R.drawable.img_loading);
        ResizeOptions resizeOptions;
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(resizeOptions = new ResizeOptions(AutoUtils.getPercentHeightSize(160), AutoUtils.getPercentHeightSize(160)))
                .build();
        //Logger.d("StockNoPictureProductAdapter ImageRequest resizeOptions" + resizeOptions.toString());
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(binding.memberImg.getController())
                .setImageRequest(request)
                .build();
        binding.memberImg.setController(controller);

        binding.memberName.setText(cashOrder.getCashPuzzyQueryUser().getName());
        binding.memberBalance.setText("余额：￥" + cashOrder.getCashPuzzyQueryUser().getBalance());
        binding.userLayout.setVisibility(View.VISIBLE);
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
    public void imgClickAction(int position, CashPuzzyQueryStock cashOrderStock) {
        navigate.navigateToStockProductDetailActivity(getActivity(), null, false, cashOrderStock.getStockId(), false, Constants.FROM_CASH_ORDER, position, 0, "");
    }

    @Override
    public void deleteAction(int position, final CashPuzzyQueryStock cashOrderStock) {

        new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setTitle("提示")
                .setMessage("是否删除该商品？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realm.beginTransaction();
                        cashOrder.setUpdateTime(new Date());
                        cashOrder.getCashOrderStocks().remove(cashOrderStock);
                        realm.commitTransaction();
                    }
                })
                .create().show();

    }

    @Override
    public void quantityChangeAction(int position, CashPuzzyQueryStock cashOrderStock, double newQuqntity) {
        if (newQuqntity == cashOrderStock.getQuantity())
            return;
        adapter.setExpandPosition(position);
        realm.beginTransaction();
        cashOrder.setUpdateTime(new Date());
        cashOrderStock.setQuantity(newQuqntity);
        realm.commitTransaction();
    }

    @Override
    public void onChange(CashOrder element) {
        if (element.isComplete()) {
            cashOrder.removeChangeListener(this);
            cashActionCreator.querySaleOrderNO();
            return;
        }
        loadCashOrder(element);
    }

    public void showReloadDialog() {

        new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setTitle("提示")
                .setMessage("是否继续上次未完成交易？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cashActionCreator.querySaleOrderNO();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cashOrder.addChangeListener(CashOrderFragment.this);
                        loadCashOrder(cashOrder);
                    }
                })
                .create().show();
    }

    public void addStock(CashPuzzyQueryStock cashPuzzyQueryStock) {
        CashPuzzyQueryStock adapterStock;
        int size = cashOrder.getCashOrderStocks().size();

        for (int i = 0; i < size; i++) {
            adapterStock = cashOrder.getCashOrderStocks().get(i);
            if (adapterStock.getStockId() == cashPuzzyQueryStock.getStockId()) {
                adapter.setExpandPosition(i);
                realm.beginTransaction();
                adapterStock.setQuantity(adapterStock.getQuantity() + 1);
                cashOrder.setUpdateTime(new Date());
                realm.commitTransaction();
                return;
            }
        }

        adapter.setExpandPosition(0);
        realm.beginTransaction();
        cashPuzzyQueryStock.setQuantity(1);
        cashOrder.getCashOrderStocks().add(0, cashPuzzyQueryStock);
        cashOrder.setUpdateTime(new Date());
        realm.commitTransaction();
    }

    public void refreshMember(CashPuzzyQueryUser cashPuzzyQueryUser) {
        if (cashPuzzyQueryUser == null) {
            toastor.showSingletonToast("未搜索到会员");
            return;
        }
        toastor.showSingletonToast("会员用户");
        realm.beginTransaction();
        cashOrder.setCashPuzzyQueryUser(cashPuzzyQueryUser);
        cashOrder.setUpdateTime(new Date());
        realm.commitTransaction();
    }


    public void deleteMember() {
        realm.beginTransaction();
        cashOrder.setCashPuzzyQueryUser(null);
        realm.commitTransaction();
    }

    public CashOrder getCashOrder() {
        return cashOrder;
    }


}
