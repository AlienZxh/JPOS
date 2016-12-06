package com.j1j2.jposmvvm.features.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.BaseViewHolder;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.ProductPriceLabelOrder;

/**
 * Created by alienzxh on 16-10-23.
 */

public class PrintLabelOrderAdapter extends RecyclerArrayAdapter<ProductPriceLabelOrder> {

    public PrintLabelOrderAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PrintLabelOrderViewHolder(parent);
    }

    public class PrintLabelOrderViewHolder extends BaseViewHolder<ProductPriceLabelOrder> {

        private TextView createTime;
        private TextView orderId;
        private TextView productNum;
        private TextView note;
        private TextView completeTag;

        public PrintLabelOrderViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_printlabel_order);
            createTime = $(R.id.createTime);
            orderId = $(R.id.orderId);
            productNum = $(R.id.productNum);
            note = $(R.id.note);
            completeTag = $(R.id.completeTag);

        }

        @Override
        public void setData(ProductPriceLabelOrder data) {
            super.setData(data);
            createTime.setText("开单时间：" + data.getSubmitTimeStr());
            orderId.setText("开单编号：" + data.getOrderId());
            productNum.setText("商品数量：" + data.getLabelCount());
            note.setText("备注：" + data.getNote());
            completeTag.setVisibility(View.INVISIBLE);
        }
    }
}
