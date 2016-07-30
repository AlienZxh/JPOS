package com.j1j2.jposmvvm.features.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.RecyclerItemClickListener;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.Product;
import com.j1j2.jposmvvm.data.model.ProductDetail;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.FragmentStockProductSearchBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StockActionCreator;
import com.j1j2.jposmvvm.features.actions.StockActions;
import com.j1j2.jposmvvm.features.adapter.StockNoPictureProductAdapter;
import com.j1j2.jposmvvm.features.base.BaseFragment;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.base.di.HasComponent;
import com.j1j2.jposmvvm.features.di.components.StockNoPicturesComponent;
import com.j1j2.jposmvvm.features.stores.StockStore;
import com.yatatsu.autobundle.AutoBundleField;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-6-7.
 */
public class StockProductSearchFragment extends BaseFragment implements RxViewDispatch, RecyclerItemClickListener.OnItemClickListener, RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    public static final int FROM_SORT = 0;
    public static final int FROM_TAKEPICTURE = 1;

    public interface StockNoPicturesSearchFragmentListener extends HasComponent<StockNoPicturesComponent> {
        void onSearchFinish();
    }

    FragmentStockProductSearchBinding binding;

    private StockNoPictureProductAdapter adapter;

    private StockNoPicturesSearchFragmentListener listener;

    @Inject
    StockActionCreator stockActionCreator;
    @Inject
    Navigate navigate;

    @AutoBundleField
    int mFromType;

    private boolean showNoPicture = false;
    private String keyWord = "";
    private String topCategory = "";
    private String subCategory = "";
    private int pageIndex = 1;
    private int pageCount = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (StockNoPicturesSearchFragmentListener) context;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_product_search, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.stockList.setRefreshingColorResources(R.color.colorPrimary);
        binding.stockList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.stockList.setAdapterWithProgress(adapter = new StockNoPictureProductAdapter(getContext(), mFromType));
        adapter.setMore(R.layout.view_more_footer, this);
        adapter.setNoMore(R.layout.view_nomore_footer);
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        binding.stockList.setRefreshListener(this);
        binding.stockList.addOnItemTouchListener(new RecyclerItemClickListener(binding.stockList.getRecyclerView(), this));

        onRefresh();
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, int id) {
        if (mFromType == FROM_SORT)
            navigate.navigateToStockProductDetailActivity(getActivity(), null, false, adapter.getItem(position).getStockId(), StockTakePicturesActivity.From_SEARCH, position);
        else if (mFromType == FROM_TAKEPICTURE)
            navigate.navigateToStockTakePicturesActivity(getActivity(), null, false, adapter.getItem(position).getStockId(), StockTakePicturesActivity.From_SEARCH, position);
    }

    @Override
    public void onLoadMore() {
        stockActionCreator.searchStocks(pageIndex, keyWord, 2
                , TextUtils.isEmpty(topCategory) ? "未知分类" : topCategory
                , TextUtils.isEmpty(subCategory) ? "未知分类" : subCategory
                , String.valueOf(showNoPicture));
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        adapter.clear();
        stockActionCreator.searchStocks(pageIndex, keyWord, 2
                , TextUtils.isEmpty(topCategory) ? "未知分类" : topCategory
                , TextUtils.isEmpty(subCategory) ? "未知分类" : subCategory
                , String.valueOf(showNoPicture));
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case StockStore.ID:
                switch (change.getRxAction().getType()) {

                    case StockActions.SEARCHSTOCKS:
                        WebReturn<PageManager<List<Product>>> productsWebReturn =
                                (WebReturn<PageManager<List<Product>>>) change.getRxAction().get(Keys.PRODUCTS_WEBRETURN);
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
                    case StockActions.REFRESHLIST:
                        int fromType = change.getRxAction().get(Keys.REFRESHLISTFROMTYPE);

                        if (fromType == StockTakePicturesActivity.From_SEARCH) {
                            int position = change.getRxAction().get(Keys.REFRESHLISTPOSITION);
                            ProductDetail productDetail = change.getRxAction().get(Keys.REFRESHLISTPRODUCTDETAIL);
                            Product product = adapter.getItem(position);
                            if (!TextUtils.isEmpty(productDetail.getThumbImgUrl())) {
                                product.setThumbImgUrl(productDetail.getThumbImgUrl());
                            }
                            product.setName(productDetail.getName());
                            product.setUnit(productDetail.getUnit());
                            product.setSpec(productDetail.getSpec());
                            product.setBarCode(productDetail.getBarCode());
                            adapter.notifyItemChanged(position);
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

    public void setShowNoPicture(boolean showNoPicture) {
        this.showNoPicture = showNoPicture;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void setTopCategory(String topCategory) {
        this.topCategory = topCategory;
    }

    @Override
    public boolean onBackPressedSupport() {
        listener.onSearchFinish();
        hideSoftInput();
        pop();
        return true;
    }
}
