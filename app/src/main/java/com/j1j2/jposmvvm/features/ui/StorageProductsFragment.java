package com.j1j2.jposmvvm.features.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.data.model.StorageOrder;
import com.j1j2.jposmvvm.data.model.StorageOrderItem;
import com.j1j2.jposmvvm.data.model.StorageStockDetail;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.FragmentStorageProductsBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StorageActionCreator;
import com.j1j2.jposmvvm.features.actions.StorageActions;
import com.j1j2.jposmvvm.features.adapter.StorageProductAdapter;
import com.j1j2.jposmvvm.features.base.BaseFragment;
import com.j1j2.jposmvvm.features.base.di.HasComponent;
import com.j1j2.jposmvvm.features.di.components.StorageComponent;
import com.j1j2.jposmvvm.features.stores.StorageStore;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.orhanobut.logger.Logger;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * Created by alienzxh on 16-7-27.
 */
public class StorageProductsFragment extends BaseFragment implements RxViewDispatch, SwipeRefreshLayout.OnRefreshListener, StorageProductAdapter.StorageProductAdapterListener {

    public interface StorageProductsFragmentListener extends HasComponent<StorageComponent> {
        int getOrderId();

        void showProductActionBar();

        void setStockIdList(List<Integer> stockIdList);
    }

    private Realm realm;

    private StorageProductsFragmentListener listener;

    FragmentStorageProductsBinding binding;

    @Inject
    StorageActionCreator storageActionCreator;
    @Inject
    StorageStore storageStore;
    @Inject
    Toastor toastor;

    StorageOrder storageOrder;

    StorageProductAdapter adapter;
    List<StorageStockDetail> storageStockDetails;

    double productAmout = 0.0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (StorageProductsFragmentListener) context;
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
        realm.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.showProductActionBar();
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_storage_products, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
//        realm.addChangeListener(new RealmChangeListener<Realm>() {
//            @Override
//            public void onChange(Realm element) {
//                storageOrder = element.where(StorageOrder.class).equalTo("OrderId", listener.getOrderId()).findFirst();
//                binding.otherAmout.setText("" + storageOrder.getOtherAmount());
//            }
//        });
        storageOrder = realm.where(StorageOrder.class).equalTo("OrderId", listener.getOrderId()).findFirst();
        binding.otherAmout.setText("" + storageOrder.getOtherAmount());
        binding.otherAmout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()))
                    return;
                double newAmount = 0;

                try {
                    newAmount = Double.parseDouble(s.toString());
                } catch (NumberFormatException e) {

                }
                if (newAmount < 0 || newAmount == storageOrder.getOtherAmount())
                    return;
                otherAmoutChangeAction(newAmount);
            }
        });
        //________________________________________________________________________
        binding.productsList.setRefreshingColorResources(R.color.colorPrimary);
        binding.productsList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.productsList.setAdapterWithProgress(adapter = new StorageProductAdapter(getContext()));
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
        binding.productsList.setRefreshListener(this);
        adapter.setStorageProductAdapterListener(this);
        onRefresh();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case StorageStore.ID:
                switch (change.getRxAction().getType()) {
                    case StorageActions.QUERYSTOREORDERDETAILS:
                        WebReturn<List<StorageStockDetail>> listWebReturn =
                                (WebReturn<List<StorageStockDetail>>) change.getRxAction().get(Keys.QUERYSTOREORDERDETAILS_WEBRETURN);
                        if (listWebReturn.isValue()) {
                            if (listWebReturn.getDetail().size() > 0) {
                                storageStockDetails = listWebReturn.getDetail();
                                adapter.addAll(storageStockDetails);
                                int length = storageStockDetails.size();
                                List<Integer> stockIdList = new ArrayList<>();

                                for (int i = 0; i < length; i++) {
                                    stockIdList.add(storageStockDetails.get(i).getStockId());
                                    productAmout += (storageStockDetails.get(i).getPrice() * storageStockDetails.get(i).getQuantity());
                                }
                                listener.setStockIdList(stockIdList);

                            } else
                                binding.productsList.showEmpty();

                            binding.productAmout.setText("" + productAmout);
                            binding.amout.setText("" + (productAmout + storageOrder.getOtherAmount()));
                        } else {
                            adapter.pauseMore();
                        }
                        break;
                    case StorageActions.CREATEORUPDATESTOREAGEORDERDETAILITEM:
                        WebReturn<String> createItemWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.CREATEORUPDATESTOREAGEORDERDETAILITEM_WEBRETURN);
                        boolean needRefresh = change.getRxAction().get(Keys.CREATEORUPDATESTOREAGEORDERDETAILITEM_ISREFRESH);
                        if (createItemWebReturn.isValue()) {
                            if (needRefresh)
                                onRefresh();
                        } else {
                            toastor.showSingletonToast(createItemWebReturn.getErrorMessage());
                            adapter.clear();
                            adapter.addAll(storageStockDetails);
                        }
                        break;
                    case StorageActions.REMOVESTOREAGEORDERDETAILITEM:
                        WebReturn<String> removeItemWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.REMOVESTOREAGEORDERDETAILITEM_WEBRETURN);
                        if (removeItemWebReturn.isValue()) {

                            onRefresh();

                        } else {
                            toastor.showSingletonToast(removeItemWebReturn.getErrorMessage());
                        }
                        break;
                    case StorageActions.CREATEUPDATESTORAGEORDER:
                        WebReturn<String> storageOrderWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.CREATEUPDATESTORAGEORDER_WEBRETURN);
                        if (storageOrderWebReturn.isValue()) {
                            toastor.showSingletonToast("更新入库单成功");
                        } else {
                            toastor.showSingletonToast(storageOrderWebReturn.getErrorMessage());
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
        adapter.clear();
        storageActionCreator.queryStoreOrderDetails(listener.getOrderId());
    }

    @Override
    public void deleteAction(int position, StorageStockDetail storageStockDetail) {
        storageActionCreator.removeStoreageOrderDetailItem(storageStockDetail.getItemId(), storageStockDetail.getOrderId());
    }


    @Override
    public void priceChangeAction(int position, StorageStockDetail storageStockDetail, double newPrice) {
        StorageOrderItem storageOrderItem = new StorageOrderItem();
        storageOrderItem.setItemId(storageStockDetail.getItemId());
        storageOrderItem.setOrderId(listener.getOrderId());
        storageOrderItem.setStockId(storageStockDetail.getStockId());
        storageOrderItem.setQuantity(storageStockDetail.getQuantity());
        storageOrderItem.setPrice(newPrice);
        storageActionCreator.createOrUpdateStoreageOrderDetailItem(storageOrderItem, false);

        productAmout += storageStockDetails.get(position).getQuantity() * (newPrice - storageStockDetails.get(position).getPrice());
        binding.productAmout.setText("" + productAmout);
        binding.amout.setText("" + (productAmout + storageOrder.getOtherAmount()));
    }

    @Override
    public void quantityChangeAction(int position, StorageStockDetail storageStockDetail, double newQuqntity) {
        StorageOrderItem storageOrderItem = new StorageOrderItem();
        storageOrderItem.setItemId(storageStockDetail.getItemId());
        storageOrderItem.setOrderId(listener.getOrderId());
        storageOrderItem.setStockId(storageStockDetail.getStockId());
        storageOrderItem.setQuantity(newQuqntity);
        storageOrderItem.setPrice(storageStockDetail.getPrice());
        storageActionCreator.createOrUpdateStoreageOrderDetailItem(storageOrderItem, false);

        productAmout += storageStockDetails.get(position).getPrice() * (newQuqntity - storageStockDetails.get(position).getQuantity());
        binding.productAmout.setText("" + productAmout);
        binding.amout.setText("" + (productAmout + storageOrder.getOtherAmount()));
    }

    public void otherAmoutChangeAction(double otherAmout) {
        realm.beginTransaction();
        storageOrder.setOtherAmount(otherAmout);
        realm.commitTransaction();
        StorageOrder storageOrderBody = new StorageOrder();
        storageOrderBody.setShopId(storageOrder.getShopId());
        storageOrderBody.setSupplerId(storageOrder.getSupplerId());
        storageOrderBody.setClerkId(storageOrder.getClerkId());
        storageOrderBody.setOrderId(storageOrder.getOrderId());
        storageOrderBody.setOtherAmount(otherAmout);
        storageActionCreator.createUpdateStorageOrder(storageOrderBody);
    }
}
