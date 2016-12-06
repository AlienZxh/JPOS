package com.j1j2.jposmvvm.features.actions;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.action.RxActionCreator;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;
import com.j1j2.jposmvvm.data.api.SaleStatisticAPI;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.QuerySaleOrderDetail;
import com.j1j2.jposmvvm.data.model.SaleOrder;
import com.j1j2.jposmvvm.data.model.SaleStatic;
import com.j1j2.jposmvvm.data.model.SaleStaticByCategory;
import com.j1j2.jposmvvm.data.model.WebReturn;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-8-15.
 */
public class SaleStatisticActionCreator extends RxActionCreator implements SaleStatisticActions {

    private SaleStatisticAPI saleStatisticAPI;

    public SaleStatisticActionCreator(Dispatcher dispatcher, SubscriptionManager manager, SaleStatisticAPI saleStatisticAPI) {
        super(dispatcher, manager);
        this.saleStatisticAPI = saleStatisticAPI;
    }

    @Override
    public void loadSaleStaticByMonth(int year, int month) {
        final RxAction action = newRxAction(LOADSALESTATICBYMONTH);
        if (hasRxAction(action)) return;
        addRxAction(action, saleStatisticAPI.loadSaleStaticByMonth(year, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<List<SaleStatic>>>() {
                    @Override
                    public void call(WebReturn<List<SaleStatic>> listWebReturn) {
                        action.getData().put(Keys.LOADSALESTATICBYMONTH_WEBRETURN, listWebReturn);
                        postRxAction(action);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        postError(action, throwable);
                    }
                }));
    }

    @Override
    public void loadSaleStaticInMonthByCategory(int year, int month) {
        final RxAction action = newRxAction(LOADSALESTATICINMONTHBYCATEGORY);
        if (hasRxAction(action)) return;
        addRxAction(action, saleStatisticAPI.loadSaleStaticInMonthByCategory(year, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<List<SaleStaticByCategory>>>() {
                    @Override
                    public void call(WebReturn<List<SaleStaticByCategory>> listWebReturn) {
                        action.getData().put(Keys.LOADSALESTATICINMONTHBYCATEGORY_WEBRETURN, listWebReturn);
                        postRxAction(action);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        postError(action, throwable);
                    }
                }));
    }

    @Override
    public void loadSaleOrders(int pageIndex, int pageSize, String beginTime, String endTime) {
        final RxAction action = newRxAction(LOADSALEORDERS);
        if (hasRxAction(action)) return;
        addRxAction(action, saleStatisticAPI.loadSaleOrders(pageIndex, pageSize, beginTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<PageManager<List<SaleOrder>>>>() {
                    @Override
                    public void call(WebReturn<PageManager<List<SaleOrder>>> listWebReturn) {
                        action.getData().put(Keys.LOADSALEORDERS_WEBRETURN, listWebReturn);
                        postRxAction(action);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        postError(action, throwable);
                    }
                }));
    }

    @Override
    public void querySaleOrderDetails(int orderId) {
        final RxAction action = newRxAction(QUERYSALEORDERDETAILS);
        if (hasRxAction(action)) return;
        addRxAction(action, saleStatisticAPI.querySaleOrderDetails(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<QuerySaleOrderDetail>>() {
                    @Override
                    public void call(WebReturn<QuerySaleOrderDetail> webReturn) {
                        action.getData().put(Keys.QUERYSALEORDERDETAILS_WEBRETURN, webReturn);
                        postRxAction(action);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        postError(action, throwable);
                    }
                }));
    }
}
