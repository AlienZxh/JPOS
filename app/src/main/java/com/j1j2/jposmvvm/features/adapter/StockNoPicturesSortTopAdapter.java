package com.j1j2.jposmvvm.features.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.AutoBindingViewHolder;
import com.j1j2.jposmvvm.common.widgets.recyclerviewchoicemode.SelectableHolder;
import com.j1j2.jposmvvm.common.widgets.recyclerviewchoicemode.SingleSelector;
import com.j1j2.jposmvvm.data.model.Category;
import com.j1j2.jposmvvm.databinding.ItemStockNopicturesSortTopBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by alienzxh on 16-6-7.
 */
public class StockNoPicturesSortTopAdapter extends RecyclerView.Adapter<StockNoPicturesSortTopAdapter.StockNoPicturesSortTopViewHolder> {

    private ArrayList<Category> categoryArrayList;
    private SingleSelector singleSelector;

    public StockNoPicturesSortTopAdapter(ArrayList<Category> categoryArrayList, SingleSelector singleSelector) {
        this.categoryArrayList = categoryArrayList;
        this.singleSelector = singleSelector;
    }

    @Override
    public StockNoPicturesSortTopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stock_nopictures_sort_top, parent, false);
        return new StockNoPicturesSortTopViewHolder(itemView, singleSelector);
    }

    @Override
    public void onBindViewHolder(StockNoPicturesSortTopViewHolder holder, int position) {
        holder.bind(categoryArrayList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.categoryArrayList == null ? 0 : categoryArrayList.size();
    }

    public class StockNoPicturesSortTopViewHolder extends AutoBindingViewHolder<ItemStockNopicturesSortTopBinding, Category> implements SelectableHolder {

        private SingleSelector singleSelector;

        private boolean mIsSelectable = false;


        public StockNoPicturesSortTopViewHolder(View itemView, SingleSelector singleSelector) {
            super(itemView);
            this.singleSelector = singleSelector;
        }

        @Override
        protected ItemStockNopicturesSortTopBinding getBinding(View itemView) {
            return ItemStockNopicturesSortTopBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull Category data, final int position) {
            singleSelector.bindHolder(this, position, getItemId());
            binding.setCategory(data);
            binding.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleSelector.setSelected(position, getItemId(), true);
                }
            });
        }

        @Override
        public void setSelectable(boolean isSelectable) {
            boolean changed = isSelectable != mIsSelectable;
            mIsSelectable = isSelectable;

        }

        @Override
        public boolean isSelectable() {
            return mIsSelectable;
        }

        @Override
        public void setActivated(boolean activated) {
            itemView.setActivated(activated);
        }

        @Override
        public boolean isActivated() {
            return itemView.isActivated();
        }

    }
}
