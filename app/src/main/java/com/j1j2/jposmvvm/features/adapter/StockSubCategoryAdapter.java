package com.j1j2.jposmvvm.features.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.AutoBindingViewHolder;
import com.j1j2.jposmvvm.data.model.SubCategory;
import com.j1j2.jposmvvm.databinding.ItemStockSubcategoryBinding;

import java.util.List;

/**
 * Created by alienzxh on 16-6-13.
 */
public class StockSubCategoryAdapter extends RecyclerView.Adapter<StockSubCategoryAdapter.StockSubCategoryViewHolder> {

    private List<SubCategory> subCategories;

    public StockSubCategoryAdapter(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public StockSubCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stock_subcategory, parent, false);
        return new StockSubCategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StockSubCategoryViewHolder holder, int position) {
        holder.bind(subCategories.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.subCategories == null ? 0 : this.subCategories.size();
    }

    public class StockSubCategoryViewHolder extends AutoBindingViewHolder<ItemStockSubcategoryBinding, SubCategory> {
        public StockSubCategoryViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemStockSubcategoryBinding getBinding(View itemView) {
            return ItemStockSubcategoryBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull SubCategory data, int position) {
            binding.setCategory(data);
        }
    }
}
