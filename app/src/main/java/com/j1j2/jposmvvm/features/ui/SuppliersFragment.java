package com.j1j2.jposmvvm.features.ui;

import android.content.Context;
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
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.api.body.AddShopSupplierBody;
import com.j1j2.jposmvvm.data.model.Supplier;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.FragmentSuppliersBinding;
import com.j1j2.jposmvvm.databinding.ViewAddSupplierBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.SupplierActionCreator;
import com.j1j2.jposmvvm.features.actions.SupplierActions;
import com.j1j2.jposmvvm.features.adapter.SuppliersAdapter;
import com.j1j2.jposmvvm.features.base.BaseFragment;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.base.di.HasComponent;
import com.j1j2.jposmvvm.features.di.components.StorageComponent;
import com.j1j2.jposmvvm.features.stores.StorageStore;
import com.j1j2.jposmvvm.features.stores.SupplierStore;
import com.j1j2.jposmvvm.features.viewmodel.AddSupplierViewModel;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-7-26.
 */
public class SuppliersFragment extends BaseFragment implements RxViewDispatch, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {


    public interface SuppliersFragmentListener extends HasComponent<StorageComponent> {
        void setActionBarTitle(String title);

        void showCreateStorageOrderActionBarBtn();

        void showCreateSupplierActionBarBtn();
    }

    private SuppliersFragmentListener listener;

    FragmentSuppliersBinding binding;

    @Inject
    SupplierActionCreator supplierActionCreator;
    @Inject
    SupplierStore supplierStore;
    @Inject
    Toastor toastor;

    private SuppliersAdapter adapter;

    ViewAddSupplierBinding diaologBinding;
    AlertDialog addSupplierDialog;
    AddSupplierViewModel addSupplierBodyViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (SuppliersFragmentListener) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.setActionBarTitle("选择供应商");
        listener.showCreateSupplierActionBarBtn();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_suppliers, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.storageOrderList.setRefreshingColorResources(R.color.colorPrimary);
        binding.storageOrderList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.storageOrderList.setAdapterWithProgress(adapter = new SuppliersAdapter(getContext()));
        binding.storageOrderList.getRecyclerView().setPadding(AutoUtils.getPercentHeightSize(20), AutoUtils.getPercentHeightSize(20),
                AutoUtils.getPercentHeightSize(20), AutoUtils.getPercentHeightSize(20));
        binding.storageOrderList.addItemDecoration(new DividerDecoration(Color.TRANSPARENT, AutoUtils.getPercentWidthSize(20)));
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
        //_________________________
        diaologBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.view_add_supplier, null, false);
        addSupplierBodyViewModel = new AddSupplierViewModel();
        addSupplierBodyViewModel.setAddShopSupplierBody(new AddShopSupplierBody());
        diaologBinding.setMAddViewModel(addSupplierBodyViewModel);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case StorageStore.ID:
                switch (change.getRxAction().getType()) {
                    case SupplierActions.QUERYSHOPSUPPLIERS:
                        WebReturn<List<Supplier>> listWebReturn =
                                (WebReturn<List<Supplier>>) change.getRxAction().get(Keys.SUPPLIERS_WEBRETURN);
                        if (listWebReturn.isValue()) {
                            List<Supplier> suppliers = listWebReturn.getDetail();
                            Collections.reverse(suppliers);
                            adapter.addAll(suppliers);
                        } else {
                            adapter.pauseMore();
                        }
                        break;
                    case SupplierActions.ADDSHOPSUPPLIER:
                        WebReturn<String> stringWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.ADDSUPPLIER_WEBRETURN);
                        if (stringWebReturn.isValue()) {
                            onRefresh();
                        } else {
                            toastor.showSingletonToast(stringWebReturn.getErrorMessage());
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
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("selectSupplierName", adapter.getItem(position).getName());
        bundle.putInt("selectSupplierId", adapter.getItem(position).getSupplierId());
        setFramgentResult(RESULT_OK, bundle);
        onBackPressedSupport();
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        supplierActionCreator.queryShopSuppliers();
    }

    public void showAddSupplierDialog() {
        if (addSupplierDialog == null)
            addSupplierDialog = new AlertDialog.Builder(getContext())
                    .setCancelable(true)
                    .setTitle("新增供应商")
                    .setView(diaologBinding.getRoot())
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            supplierActionCreator.addShopSupplier(addSupplierBodyViewModel.getAddShopSupplierBody());
                        }
                    })
                    .create();
        addSupplierDialog.show();
    }

    @Override
    public boolean onBackPressedSupport() {
        pop();
        listener.setActionBarTitle("新增入库单");
        listener.showCreateStorageOrderActionBarBtn();
        return true;
    }


}
