package com.j1j2.jposmvvm.features.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.BaseViewHolder;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.SaleStatic;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by alienzxh on 16-8-15.
 */
public class SaleStatisticAdapter extends RecyclerArrayAdapter<SaleStatic> implements StickyRecyclerHeadersAdapter<SaleStatisticAdapter.SaleStatisticHeadViewHolder> {

    public SaleStatisticAdapter(Context context) {
        super(context);
        setHasStableIds(true);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SaleStatisticViewHolder(parent);
    }

    @Override
    public long getHeaderId(int position) {
        if (getAllData().size() <= 0 || getAllData().size() <= position)
            return -1;
        else
            return getItem(position).getSortDate().getTime();
    }

    @Override
    public SaleStatisticHeadViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sale_statistic_header, parent, false);
        return new SaleStatisticHeadViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(SaleStatisticHeadViewHolder holder, int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getHeaderId(position));

        Calendar now = Calendar.getInstance();
        now.setTime(new Date());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
        if (now.get(Calendar.MONTH) == calendar.get(Calendar.MONTH))
            holder.time.setText("本月");
        else
            holder.time.setText(simpleDateFormat.format(calendar.getTime()));
    }

    public class SaleStatisticViewHolder extends BaseViewHolder<SaleStatic> {

        private TextView time;
        private TextView amount;
        private TextView orderSum;
        private TextView orderGrossProfit;

        public SaleStatisticViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_sale_statistic);

            time = $(R.id.time);
            amount = $(R.id.amount);
            orderSum = $(R.id.orderSum);
            orderGrossProfit = $(R.id.orderGrossProfit);
        }

        @Override
        public void setData(SaleStatic data) {
            super.setData(data);
            time.setText("" + data.getDay() + "号");
            amount.setText("￥" + data.getOrderAmount());
            orderSum.setText("单数：" + data.getOrderCount());
            orderGrossProfit.setText("毛利：" + new BigDecimal(data.getProfitRate()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

        }
    }

    public class SaleStatisticHeadViewHolder extends RecyclerView.ViewHolder {

        public TextView time;

        public SaleStatisticHeadViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }

}
