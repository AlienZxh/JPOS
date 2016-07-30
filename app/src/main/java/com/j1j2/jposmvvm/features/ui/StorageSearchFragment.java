package com.j1j2.jposmvvm.features.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.common.widgets.RecyclerItemClickListener;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.StorageOrderItem;
import com.j1j2.jposmvvm.data.model.StorageStock;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.FragmentStorageSearchBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StorageActionCreator;
import com.j1j2.jposmvvm.features.actions.StorageActions;
import com.j1j2.jposmvvm.features.adapter.StorageSearchAdapter;
import com.j1j2.jposmvvm.features.base.BaseFragment;
import com.j1j2.jposmvvm.features.base.di.HasComponent;
import com.j1j2.jposmvvm.features.di.components.StorageComponent;
import com.j1j2.jposmvvm.features.stores.StorageStore;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-7-27.
 */
public class StorageSearchFragment extends BaseFragment implements RxViewDispatch, RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, StorageSearchAdapter.StorageSearchAdapterListener {

    public interface StorageSearchFragmentListener extends HasComponent<StorageComponent> {
        String getKey();

        int getOrderId();

        void showSearchActionBar();

        void onSearchFinish();

        List<Integer> getStockIdList();
    }

    private StorageSearchFragmentListener listener;

    FragmentStorageSearchBinding binding;

    @Inject
    StorageActionCreator storageActionCreator;
    @Inject
    StorageStore storageStore;
    @Inject
    Toastor toastor;

    StorageSearchAdapter adapter;

    private int pageIndex = 1;
    private int pageCount = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (StorageSearchFragmentListener) context;
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
    public void onResume() {
        super.onResume();
        listener.showSearchActionBar();
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_storage_search, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.stockList.setRefreshingColorResources(R.color.colorPrimary);
        binding.stockList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.stockList.setAdapterWithProgress(adapter = new StorageSearchAdapter(getContext(), listener.getStockIdList()));
        adapter.setMore(R.layout.view_more_footer, this);
        adapter.setNoMore(R.layout.view_nomore_footer);
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        binding.stockList.setRefreshListener(this);
        adapter.setStorageSearchAdapterListener(this);
        onRefresh();
    }


    @Override
    public void onLoadMore() {
        storageActionCreator.queryStocks(pageIndex, listener.getKey(), listener.getOrderId());
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        adapter.clear();
        storageActionCreator.queryStocks(pageIndex, listener.getKey(), listener.getOrderId());

    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case StorageStore.ID:
                switch (change.getRxAction().getType()) {
                    case StorageActions.QUERYSTOCKS:
                        WebReturn<PageManager<List<StorageStock>>> productsWebReturn =
                                (WebReturn<PageManager<List<StorageStock>>>) change.getRxAction().get(Keys.QUERYSTOCKS_WEBRETURN);
                        if (productsWebReturn.isValue()) {
                            pageCount = productsWebReturn.getDetail().getPageCount();
                            if (productsWebReturn.getDetail().getTotalCount() <= 0) {
                                binding.stockList.showEmpty();
                            } else if (pageIndex == pageCount) {
                                adapter.addAll(productsWebReturn.getDetail().getList());
                                adapter.stopMore();
                            } else {
                                adapter.addAll(productsWebReturn.getDetail().getList());
                                pageIndex++;
                            }
                        } else {
                            adapter.pauseMore();
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
    public void onSelectStockAction(int position, StorageStock storageStock) {
        StorageOrderItem storageOrderItem = new StorageOrderItem();
        storageOrderItem.setOrderId(listener.getOrderId());
        storageOrderItem.setStockId(storageStock.getStockId());
        storageOrderItem.setQuantity(0);
        storageActionCreator.createOrUpdateStoreageOrderDetailItem(storageOrderItem, true);
        onBackPressedSupport();
    }

    @Override
    public boolean onBackPressedSupport() {
        listener.onSearchFinish();
        hideSoftInput();
        pop();
        return true;
    }
}
