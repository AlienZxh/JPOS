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
import com.j1j2.jposmvvm.common.widgets.recyclerviewchoicemode.SingleSelector;
import com.j1j2.jposmvvm.data.model.Category;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.Product;
import com.j1j2.jposmvvm.data.model.ProductDetail;
import com.j1j2.jposmvvm.data.model.TopCategory;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.FragmentStockNopicturesSortBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StockActionCreator;
import com.j1j2.jposmvvm.features.actions.StockActions;
import com.j1j2.jposmvvm.features.adapter.StockNoPictureProductAdapter;
import com.j1j2.jposmvvm.features.adapter.StockNoPicturesSortTopAdapter;
import com.j1j2.jposmvvm.features.base.BaseFragment;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.base.di.HasComponent;
import com.j1j2.jposmvvm.features.di.components.StockNoPicturesComponent;
import com.j1j2.jposmvvm.features.stores.StockStore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-6-7.
 */
public class StockNoPicturesSortFragment extends BaseFragment implements RxViewDispatch, RecyclerItemClickListener.OnItemClickListener, RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    public interface StockNoPicturesSortFragmentListener extends HasComponent<StockNoPicturesComponent> {

    }

    FragmentStockNopicturesSortBinding binding;

    private StockNoPicturesSortFragmentListener listener;

    private SingleSelector singleSelector;
    private StockNoPictureProductAdapter adapter;
    private ArrayList<Category> categories;
    private int selectPosition;

    @Inject
    StockActionCreator stockActionCreator;
    @Inject
    Navigate navigate;

    private int pageIndex = 1;
    private int pageCount = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (StockNoPicturesSortFragmentListener) context;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_nopictures_sort, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.sortList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.sortList.addOnItemTouchListener(new RecyclerItemClickListener(binding.sortList, this));
        singleSelector = new SingleSelector();

        binding.stockList.setRefreshingColorResources(R.color.colorPrimary);
        binding.stockList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.stockList.setAdapterWithProgress(adapter = new StockNoPictureProductAdapter(getContext()));
        adapter.setMore(R.layout.view_more_footer, this);
        adapter.setNoMore(R.layout.view_nomore_footer);
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        binding.stockList.setRefreshListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                navigate.navigateToStockTakePicturesActivity(getActivity(), null, false, adapter.getItem(position).getStockId(), StockTakePicturesActivity.From_SORT, position);
            }
        });

    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case StockStore.ID:
                switch (change.getRxAction().getType()) {
                    case StockActions.QUERYSTOCKCATEGORY:
                        WebReturn<List<TopCategory>> categoryWebReturn =
                                (WebReturn<List<TopCategory>>) change.getRxAction().get(Keys.CATEGORY_WEBRETURN);
                        if (categoryWebReturn.isValue()) {

                            int topSize = categoryWebReturn.getDetail().size();
                            categories = new ArrayList<>();
                            for (int i = 0; i < topSize; i++) {
                                int subSize = categoryWebReturn.getDetail().get(i).getSubCategories().size();
                                for (int j = 0; j < subSize; j++) {
                                    Category category = new Category();
                                    category.setParentCategoryName(categoryWebReturn.getDetail().get(i).getTopCategory());
                                    category.setCategoryName(categoryWebReturn.getDetail().get(i).getSubCategories().get(j).getCategoryName());
                                    category.setProductCount(categoryWebReturn.getDetail().get(i).getSubCategories().get(j).getProductCount());
                                    categories.add(category);
                                }
                            }

                            StockNoPicturesSortTopAdapter stockNoPicturesSortTopAdapter = new StockNoPicturesSortTopAdapter(categories, singleSelector);
                            binding.sortList.setAdapter(stockNoPicturesSortTopAdapter);
                            if (categories.size() > 0) {
                                singleSelector.setSelected(0, stockNoPicturesSortTopAdapter.getItemId(0), true);
                                selectPosition = 0;
                                onRefresh();
                            } else {
                                binding.stockList.setRefreshing(false);
                                binding.stockList.showEmpty();
                            }

                        } else {

                        }
                        break;
                    case StockActions.QUERYSTOCKSIFNOTFOUNDTHENCREATE:
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
                        if (fromType == StockTakePicturesActivity.From_SORT) {
                            int position = change.getRxAction().get(Keys.REFRESHLISTPOSITION);
                            ProductDetail productDetail = change.getRxAction().get(Keys.REFRESHLISTPRODUCTDETAIL);
                            if (!TextUtils.isEmpty(productDetail.getThumbImgUrl())) {
                                Product product = adapter.getItem(position);
                                product.setThumbImgUrl(productDetail.getThumbImgUrl());
                                adapter.notifyItemChanged(position);
                            }
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
    public void onItemClick(RecyclerView parent, View view, int position, int id) {
        if (parent == binding.sortList) {
            singleSelector.setSelected(position, id, true);
            selectPosition = position;
            onRefresh();
        }

    }

    @Override
    public void onLoadMore() {
        stockActionCreator.queryStocksIfNotFoundThenCreate(pageIndex, "", 2
                , categories.get(selectPosition).getParentCategoryName() == null ? "未知分类" : categories.get(selectPosition).getParentCategoryName()
                , categories.get(selectPosition).getCategoryName() == null ? "未知分类" : categories.get(selectPosition).getCategoryName()
                , String.valueOf(true));
    }

    @Override
    public void onRefresh() {
        if (categories == null || categories.size() <= 0) {
            binding.stockList.setRefreshing(false);
            return;
        }
        pageIndex = 1;
        adapter.clear();

        stockActionCreator.queryStocksIfNotFoundThenCreate(pageIndex, "", 2
                , categories.get(selectPosition).getParentCategoryName() == null ? "未知分类" : categories.get(selectPosition).getParentCategoryName()
                , categories.get(selectPosition).getCategoryName() == null ? "未知分类" : categories.get(selectPosition).getCategoryName()
                , String.valueOf(true));
    }
}
