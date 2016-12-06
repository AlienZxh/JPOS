package com.j1j2.jposmvvm.features.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.BaseViewHolder;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.StockCheckOrder;
import com.j1j2.jposmvvm.data.model.StorageOrder;

/**
 * Created by alienzxh on 16-9-19.
 */
public class StockCheckOrderAdapter extends RecyclerArrayAdapter<StockCheckOrder> {


    public StockCheckOrderAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new StockCheckOrderViewHolder(parent);
    }

    public class StockCheckOrderViewHolder extends BaseViewHolder<StockCheckOrder> {
        private TextView orderId;
        private TextView orderType;
        private TextView submitTime;
        private TextView stockCheckedCount;
        private TextView deltaCount;
        private TextView deltaAmount;
        private TextView completeTag;

        public StockCheckOrderViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_stockcheck_order);

            orderId = $(R.id.orderId);
            orderType = $(R.id.orderType);
            submitTime = $(R.id.submitTime);
            stockCheckedCount = $(R.id.StockCheckedCount);
            deltaCount = $(R.id.DeltaCount);
            deltaAmount = $(R.id.DeltaAmount);
            completeTag = $(R.id.completeTag);
        }

        @Override
        public void setData(StockCheckOrder data) {
            super.setData(data);
            orderId.setText("　　单号：" + data.getStockCheckOrderId());
            orderType.setText("　　类型：" + (data.getStockOrderType() == 1 ? "全部盘点单" : "部分盘点单"));
            stockCheckedCount.setText("已盘数量：" + data.getStockCheckedCount());
            submitTime.setText("开单时间：" + data.getSubmitTimeStr());
            deltaCount.setText("差异数量：" + data.getDeltaCount());
            deltaAmount.setText("差异金额：" + data.getDeltaAmount());
            completeTag.setVisibility(data.getState() == 2 ? View.VISIBLE : View.INVISIBLE);

        }
    }
}
