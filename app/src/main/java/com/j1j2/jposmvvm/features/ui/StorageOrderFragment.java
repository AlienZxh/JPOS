package com.j1j2.jposmvvm.features.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.ShopInfo;
import com.j1j2.jposmvvm.data.model.StorageOrder;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.FragmentStorageOrdersBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StorageActionCreator;
import com.j1j2.jposmvvm.features.actions.StorageActions;
import com.j1j2.jposmvvm.features.adapter.PrintLabelOrderAdapter;
import com.j1j2.jposmvvm.features.adapter.StorageOrderAdapter;
import com.j1j2.jposmvvm.features.base.BaseFragment;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.base.di.HasComponent;
import com.j1j2.jposmvvm.features.di.components.StockNoPicturesComponent;
import com.j1j2.jposmvvm.features.di.components.StorageComponent;
import com.j1j2.jposmvvm.features.stores.StorageStore;
import com.j1j2.jposmvvm.features.viewmodel.UIViewModel;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.orhanobut.logger.Logger;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by alienzxh on 16-7-26.
 */
public class StorageOrderFragment extends BaseFragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener, RxViewDispatch {

    public interface StorageOrderFragmentListener extends HasComponent<StorageComponent> {
        StorageOrderCreateFragment getStorageOrderCreateFragment();

        void setActionBarTitle(String title);

        void showNoActionBarBtn();
    }

    public static final int REQ_CODE = 0;

    private StorageOrderFragmentListener listener;

    FragmentStorageOrdersBinding binding;

    @Inject
    StorageActionCreator storageActionCreator;
    @Inject
    StorageStore storageStore;
    @Inject
    Toastor toastor;
    @Inject
    Navigate navigate;
    @Inject
    UIViewModel uiViewModel;
    @Inject
    Dispatcher dispatcher;


    private int pageIndex = 1;
    private int pageCount = 0;


    private StorageOrderAdapter adapter;
    private StorageOrderCreateFragment storageOrderCreateFragment;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (StorageOrderFragmentListener) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.setActionBarTitle("入库记录");
        listener.showNoActionBarBtn();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_storage_orders, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.setMUIViewModel(uiViewModel);

        //_____________________________________________________________________
        binding.storageOrderList.setRefreshingColorResources(R.color.colorPrimary);
        binding.storageOrderList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.storageOrderList.setAdapterWithProgress(adapter = new StorageOrderAdapter(getContext()));
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
    public void onItemClick(int position) {
        navigate.navigateToStorageProductsActivity(getActivity(), null, false, adapter.getItem(position).getOrderId());
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        adapter.clear();
        storageActionCreator.queryStorageOrders(pageIndex);
    }

    @Override
    public void onLoadMore() {
        storageActionCreator.queryStorageOrders(pageIndex);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case StorageStore.ID:
                switch (change.getRxAction().getType()) {
                    case StorageActions.QUERYSTORAGEORDERS:
                        WebReturn<PageManager<List<StorageOrder>>> storageOrderWebReturn =
                                (WebReturn<PageManager<List<StorageOrder>>>) change.getRxAction().get(Keys.STORAGEORDERS_WEBRETURN);
                        if (storageOrderWebReturn.isValue()) {
                            pageCount = storageOrderWebReturn.getDetail().getPageCount();
                            if (storageOrderWebReturn.getDetail().getTotalCount() <= 0) {
                                binding.storageOrderList.showEmpty();
                            } else if (pageIndex == pageCount) {
                                adapter.addAll(storageOrderWebReturn.getDetail().getList());
                                adapter.stopMore();
                            } else {
                                adapter.addAll(storageOrderWebReturn.getDetail().getList());
                                pageIndex++;
                            }
                        } else {
                            adapter.pauseMore();
                            toastor.showSingletonToast(storageOrderWebReturn.getErrorMessage());
                        }
                        break;
                    case StorageActions.REFRESHSTORAGEORDERS:
                        onRefresh();

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
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (data != null)
            Logger.d("onFragmentResult " + getClass().getSimpleName() + data.toString());
        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {

        }
    }


}
