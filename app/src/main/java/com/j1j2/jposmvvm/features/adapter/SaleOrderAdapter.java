package com.j1j2.jposmvvm.features.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.BaseViewHolder;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.SaleOrder;

/**
 * Created by alienzxh on 16-8-16.
 */
public class SaleOrderAdapter extends RecyclerArrayAdapter<SaleOrder> {
    public SaleOrderAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SaleOrderViewHolder(parent);
    }

    public class SaleOrderViewHolder extends BaseViewHolder<SaleOrder> {

        private TextView orderId;
        private TextView orderMember;
        private TextView orderTime;
        private TextView orderAmout;

        public SaleOrderViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_sale_order);
            orderId = $(R.id.orderId);
            orderMember = $(R.id.orderMember);
            orderTime = $(R.id.orderTime);
            orderAmout = $(R.id.orderAmout);
        }

        @Override
        public void setData(SaleOrder data) {
            super.setData(data);

            if (data.getOrderState() == 4) {
                orderId.setTextColor(0xffaaaaaa);
                orderMember.setTextColor(0xffaaaaaa);
                orderTime.setTextColor(0xffaaaaaa);
                orderAmout.setTextColor(0xff999999);

                orderId.setText("单号：" + data.getOrderNo() + "（订单已取消）");
                orderMember.setText("客户：" + (data.getUserName() == null ? "散客" : data.getUserName()));
                orderTime.setText("时间：" + data.getSubmitTime());
                orderAmout.setText("￥" + data.getAmount());
            } else if (data.getOrderState() == 2) {
                orderId.setTextColor(0xff333333);
                orderMember.setTextColor(0xff333333);
                orderTime.setTextColor(0xff333333);
                orderAmout.setTextColor(0xffff0000);

                orderId.setText("单号：" + data.getOrderNo());
                orderMember.setText("客户：" + (data.getUserName() == null ? "散客" : data.getUserName()));
                orderTime.setText("时间：" + data.getSubmitTime());
                orderAmout.setText("￥" + data.getAmount());
            } else {
                orderId.setTextColor(0xffaaaaaa);
                orderMember.setTextColor(0xffaaaaaa);
                orderTime.setTextColor(0xffaaaaaa);
                orderAmout.setTextColor(0xff999999);

                orderId.setText("单号：" + data.getOrderNo() + "（订单未结账）");
                orderMember.setText("客户：" + (data.getUserName() == null ? "散客" : data.getUserName()));
                orderTime.setText("时间：" + data.getSubmitTime());
                orderAmout.setText("￥" + data.getAmount());
            }

        }
    }
}
