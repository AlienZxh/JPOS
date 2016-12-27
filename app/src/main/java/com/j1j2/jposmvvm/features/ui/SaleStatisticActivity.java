package com.j1j2.jposmvvm.features.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.JPOSApplicationLike;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.SaleStatic;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.ActivitySaleStatisticBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.SaleStatisticActionCreator;
import com.j1j2.jposmvvm.features.actions.SaleStatisticActions;
import com.j1j2.jposmvvm.features.adapter.SaleStatisticAdapter;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.modules.SaleStatisticModule;
import com.j1j2.jposmvvm.features.stores.SaleStatisticStore;
import com.j1j2.jposmvvm.features.viewmodel.UIViewModel;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-8-15.
 */
public class SaleStatisticActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {

    ActivitySaleStatisticBinding binding;

    @Inject
    SaleStatisticStore saleStatisticStore;
    @Inject
    SaleStatisticActionCreator saleStatisticActionCreator;
    @Inject
    Toastor toastor;
    @Inject
    Navigate navigate;
    @Inject
    UIViewModel uiViewModel;
    @Inject
    Dispatcher dispatcher;

    SaleStatisticAdapter adapter;

    int year = 1;
    int month = 1;

    @Override
    protected void setupActivityComponent() {

        JPOSApplicationLike.get().getShopComponent().plus(new SaleStatisticModule(this)).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sale_statistic);

    }

    @Override
    protected void initViews() {
        binding.reportList.setRefreshingColorResources(R.color.colorPrimary);
        binding.reportList.setLayoutManager(new LinearLayoutManager(this));
        binding.reportList.setAdapterWithProgress(adapter = new SaleStatisticAdapter(this));
//        binding.reportList.getRecyclerView().setPadding(AutoUtils.getPercentHeightSize(20), 0,
//                AutoUtils.getPercentHeightSize(20), AutoUtils.getPercentHeightSize(20));
        adapter.setMore(R.layout.view_more_footer, this);
        adapter.setNoMore(R.layout.view_nomore_footer);
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        adapter.setOnItemClickListener(this);
        binding.reportList.setRefreshListener(this);
        //__________________________________________________
        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        binding.reportList.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
//         binding.reportList.addItemDecoration(new DividerDecoration(0xffd4d4d4, AutoUtils.getPercentHeightSize(2)));

        // Add touch listeners
        StickyRecyclerHeadersTouchListener touchListener =
                new StickyRecyclerHeadersTouchListener(binding.reportList.getRecyclerView(), headersDecor);
        touchListener.setOnHeaderClickListener(
                new StickyRecyclerHeadersTouchListener.OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(View header, int position, long headerId) {

//                        toastor.showSingletonToast("Header position: " + position + ", id: " + headerId);
                        navigate.navigateToSaleStatisticInMonthActivity(SaleStatisticActivity.this, null, false, headerId);
                    }
                });
        binding.reportList.addOnItemTouchListener(touchListener);

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });

        onRefresh();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case SaleStatisticStore.ID:
                switch (change.getRxAction().getType()) {
                    case SaleStatisticActions.LOADSALESTATICBYMONTH:
                        WebReturn<List<SaleStatic>> saleStaticWebReturn =
                                (WebReturn<List<SaleStatic>>) change.getRxAction().get(Keys.LOADSALESTATICBYMONTH_WEBRETURN);
                        if (saleStaticWebReturn.isValue()) {
                            List<SaleStatic> saleStatics = saleStaticWebReturn.getDetail();
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month - 1, 1);
                            for (int i = 0; i < saleStatics.size(); i++) {
                                saleStatics.get(i).setSortDate(calendar.getTime());
                            }
                            Collections.reverse(saleStatics);
                            adapter.addAll(saleStatics);
                            if (month == 1) {
                                year--;
                                month = 12;
                            } else
                                month--;
                        } else {
                            adapter.pauseMore();
                            toastor.showSingletonToast(saleStaticWebReturn.getErrorMessage());
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
        return Collections.<RxStore>singletonList(saleStatisticStore);

    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.<RxStore>singletonList(saleStatisticStore);

    }

    @Override
    public void onLoadMore() {
        saleStatisticActionCreator.loadSaleStaticByMonth(year, month);
    }

    @Override
    public void onRefresh() {
        Date now = new Date();
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(now);
        month = nowCalendar.get(Calendar.MONTH) + 1;
        year = nowCalendar.get(Calendar.YEAR);
        adapter.clear();
        saleStatisticActionCreator.loadSaleStaticByMonth(year, month);
    }

    @Override
    public void onItemClick(int position) {
        SaleStatic saleStatic = adapter.getItem(position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(saleStatic.getSortDate());
        calendar.set(Calendar.DAY_OF_MONTH, saleStatic.getDay());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String beginTime = simpleDateFormat.format(calendar.getTime()) + " 00:00:00";
        String endTime = simpleDateFormat.format(calendar.getTime()) + " 23:59:59";

//        Logger.d("zxh " + beginTime + "\n" + endTime);
//        toastor.showSingletonToast("zxh " + beginTime + "\n" + endTime);
        navigate.navigateToSaleOrderActivity(this, null, false, beginTime, endTime);
    }
}
