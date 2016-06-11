package com.j1j2.jposmvvm.features.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.AutoBindingViewHolder;
import com.j1j2.jposmvvm.data.model.StockImg;
import com.j1j2.jposmvvm.databinding.ItemTakepictureProductAddBinding;
import com.j1j2.jposmvvm.databinding.ItemTakepictureProductImgBinding;

import java.util.ArrayList;

/**
 * Created by alienzxh on 16-6-9.
 */
public class StockTakePictureProductAdapter extends RecyclerView.Adapter<AutoBindingViewHolder> {


    public interface OnStockTakePictureActionClickListener {
        void onPictureClick(StockImg stockImg, int position);

        void onPictureDeleteClick(StockImg stockImg, int position);

        void onPictureAddClick(int position);
    }

    private OnStockTakePictureActionClickListener listener;

    public void setListener(OnStockTakePictureActionClickListener listener) {
        this.listener = listener;
    }

    public final static int IMGTYPE = 1;
    public final static int ADDTYPE = 2;

    private ArrayList<StockImg> stockImgs;

    public StockTakePictureProductAdapter(ArrayList<StockImg> stockImgs) {
        this.stockImgs = stockImgs;
    }

    @Override
    public AutoBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == IMGTYPE) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_takepicture_product_img, parent, false);
            return new StockTakePictureProductImgViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_takepicture_product_add, parent, false);
            return new StockTakePictureProductAddViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(AutoBindingViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            ((StockTakePictureProductAddViewHolder) holder).bind("", position);
        } else {
            ((StockTakePictureProductImgViewHolder) holder).bind(stockImgs.get(position), position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1)
            return ADDTYPE;
        else
            return IMGTYPE;
    }

    @Override
    public int getItemCount() {
        return stockImgs == null ? 1 : (stockImgs.size() + 1);
    }

    public class StockTakePictureProductImgViewHolder extends AutoBindingViewHolder<ItemTakepictureProductImgBinding, StockImg> {
        public StockTakePictureProductImgViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemTakepictureProductImgBinding getBinding(View itemView) {
            return ItemTakepictureProductImgBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull final StockImg data, final int position) {
            binding.setStockImg(data);
            binding.productImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onPictureClick(data, position);
                    }
                }
            });
            binding.productImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onPictureDeleteClick(data, position);
                    }
                }
            });
        }
    }

    public class StockTakePictureProductAddViewHolder extends AutoBindingViewHolder<ItemTakepictureProductAddBinding, String> {
        public StockTakePictureProductAddViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ItemTakepictureProductAddBinding getBinding(View itemView) {
            return ItemTakepictureProductAddBinding.bind(itemView);
        }

        @Override
        public void bind(@NonNull String data, final int position) {
            binding.pictureAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onPictureAddClick(position);
                    }
                }
            });
        }
    }
}
