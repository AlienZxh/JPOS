package com.j1j2.jposmvvm.features.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.BaseViewHolder;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.StorageOrder;

/**
 * Created by alienzxh on 16-7-26.
 */
public class StorageOrderAdapter extends RecyclerArrayAdapter<StorageOrder> {
    public StorageOrderAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new StorageOrderViewHolder(parent);
    }

    public class StorageOrderViewHolder extends BaseViewHolder<StorageOrder> {


        private TextView orderId;
        private TextView supplier;
        private TextView orderTime;
        private TextView orderAmout;
        private TextView completeTag;

        public StorageOrderViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_storage_order);

            orderId = $(R.id.orderId);
            supplier = $(R.id.supplier);
            orderTime = $(R.id.orderTime);
            orderAmout = $(R.id.orderAmout);
            completeTag = $(R.id.completeTag);
        }

        @Override
        public void setData(StorageOrder data) {
            super.setData(data);
            orderId.setText("　　单号：" + data.getOrderNO());
            supplier.setText("　供应商：" + data.getSupplerName());
            orderTime.setText("开单时间：" + data.getSubmitTimeStr());
            orderAmout.setText("总计金额：" + (data.getState() == 1 ? "待计算" : data.getAmount()));
            completeTag.setVisibility(data.getState() == 2 ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
