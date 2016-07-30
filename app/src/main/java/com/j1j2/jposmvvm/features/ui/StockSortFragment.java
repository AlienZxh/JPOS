package com.j1j2.jposmvvm.features.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.j1j2.jposmvvm.common.widgets.RecyclerItemClickListener;
import com.j1j2.jposmvvm.common.widgets.recyclerviewchoicemode.SingleSelector;
import com.j1j2.jposmvvm.data.model.Category;
import com.j1j2.jposmvvm.data.model.TopCategory;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.FragmentStockSortBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StockActions;
import com.j1j2.jposmvvm.features.adapter.StockNoPicturesSortTopAdapter;
import com.j1j2.jposmvvm.features.adapter.StockSubCategoryAdapter;
import com.j1j2.jposmvvm.features.base.BaseFragment;
import com.j1j2.jposmvvm.features.stores.StockStore;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alienzxh on 16-6-13.
 */
public class StockSortFragment extends BaseFragment implements RxViewDispatch, RecyclerItemClickListener.OnItemClickListener {

    public interface StockSortFragmentListener {

        void navigateToSearch(String topCategory, String subCategory);
    }

    FragmentStockSortBinding binding;


    private StockSortFragmentListener listener;

    private SingleSelector singleSelector;
    private int selectPosition;
    private ArrayList<Category> topCategorys;
    private List<TopCategory> categorys;

    private StockSubCategoryAdapter stockSubCategoryAdapter;

    @Override
    protected void setupActivityComponent() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (StockSortFragmentListener) context;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_sort, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.sortList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.sortList.addOnItemTouchListener(new RecyclerItemClickListener(binding.sortList, this));
        singleSelector = new SingleSelector();


        binding.subSortList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.subSortList.addOnItemTouchListener(new RecyclerItemClickListener(binding.subSortList, this));

    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, int id) {
        if (parent == binding.sortList) {
            singleSelector.setSelected(position, id, true);
            selectPosition = position;
            stockSubCategoryAdapter = new StockSubCategoryAdapter(categorys.get(position).getSubCategories());
            binding.subSortList.setAdapter(stockSubCategoryAdapter);
            binding.subSortList.addItemDecoration(new DividerDecoration(0xffd4d4d4, 1, AutoUtils.getPercentWidthSize(20), 0));
        }
        if (parent == binding.subSortList) {
            listener.navigateToSearch(categorys.get(selectPosition).getTopCategory(),categorys.get(selectPosition).getSubCategories().get(position).getCategoryName());
        }
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
                            topCategorys = new ArrayList<>();
                            this.categorys = categoryWebReturn.getDetail();
                            Category category;
                            for (int i = 0; i < topSize; i++) {
                                int subSize = categoryWebReturn.getDetail().get(i).getSubCategories().size();
                                category = new Category();
                                category.setCategoryName(categoryWebReturn.getDetail().get(i).getTopCategory());
                                category.setProductCount(categoryWebReturn.getDetail().get(i).getSubCategories().size());
                                topCategorys.add(category);

                            }

                            StockNoPicturesSortTopAdapter stockNoPicturesSortTopAdapter = new StockNoPicturesSortTopAdapter(topCategorys, singleSelector);
                            binding.sortList.setAdapter(stockNoPicturesSortTopAdapter);
                            if (topCategorys.size() > 0) {
                                singleSelector.setSelected(0, stockNoPicturesSortTopAdapter.getItemId(0), true);
                                selectPosition = 0;
                                stockSubCategoryAdapter = new StockSubCategoryAdapter(categorys.get(0).getSubCategories());
                                binding.subSortList.setAdapter(stockSubCategoryAdapter);
                                binding.subSortList.addItemDecoration(new DividerDecoration(0xffd4d4d4, AutoUtils.getPercentHeightSize(1)));
                            }

                        } else {

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
}
