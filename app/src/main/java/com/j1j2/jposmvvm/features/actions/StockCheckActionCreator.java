package com.j1j2.jposmvvm.features.actions;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.action.RxActionCreator;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;
import com.j1j2.jposmvvm.data.api.StockCheckAPI;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.StockCheckOrder;
import com.j1j2.jposmvvm.data.model.StockCheckOrderDetail;
import com.j1j2.jposmvvm.data.model.StockCheckOrderItem;
import com.j1j2.jposmvvm.data.model.WebReturn;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-9-19.
 */
public class StockCheckActionCreator extends RxActionCreator implements StockCheckActions {

    private StockCheckAPI stockCheckAPI;

    public StockCheckActionCreator(Dispatcher dispatcher, SubscriptionManager manager, StockCheckAPI stockCheckAPI) {
        super(dispatcher, manager);
        this.stockCheckAPI = stockCheckAPI;
    }

    @Override
    public void queryStockCheckOrders(int pageIndex, int pageSize) {
        final RxAction action = newRxAction(QUERYSTOCKCHECKORDERS);
        if (hasRxAction(action)) return;
        addRxAction(action, stockCheckAPI.queryStockCheckOrders(pageIndex, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<PageManager<List<StockCheckOrder>>>>() {
                    @Override
                    public void call(WebReturn<PageManager<List<StockCheckOrder>>> pageManagerWebReturn) {
                        action.getData().put(Keys.QUERYSTOCKCHECKORDERS_WEBRETURN, pageManagerWebReturn);
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
    public void createStockCheckOrder(int stockCheckOrderType) {
        final RxAction action = newRxAction(CREATESTOCKCHECKORDER);
        if (hasRxAction(action)) return;
        addRxAction(action, stockCheckAPI.createStockCheckOrder(stockCheckOrderType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.CREATESTOCKCHECKORDER_WEBRETURN, stringWebReturn);
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
    public void queryStockCheckOrderDetails(int stockCheckOrderId, int pageIndex, int pageSize) {
        final RxAction action = newRxAction(QUERYSTOCKCHECKORDERDETAILS);
        if (hasRxAction(action)) return;
        addRxAction(action, stockCheckAPI.queryStockCheckOrderDetails(stockCheckOrderId, pageIndex, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<PageManager<List<StockCheckOrderDetail>>>>() {
                    @Override
                    public void call(WebReturn<PageManager<List<StockCheckOrderDetail>>> pageManagerWebReturn) {
                        action.getData().put(Keys.QUERYSTOCKCHECKORDERDETAILS_WEBRETURN, pageManagerWebReturn);
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
    public void queryStocks(int stockCheckOrderId, String key) {
        final RxAction action = newRxAction(QUERYSTOCKS);
        if (hasRxAction(action)) return;
        addRxAction(action, stockCheckAPI.queryStocks(stockCheckOrderId, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<List<StockCheckOrderItem>>>() {
                    @Override
                    public void call(WebReturn<List<StockCheckOrderItem>> listWebReturn) {
                        action.getData().put(Keys.QUERYCHECKSTOCKS_WEBRETURN, listWebReturn);
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
    public void createOrUpdateStockCheckItem(StockCheckOrderDetail stockCheckOrderDetail) {
        final RxAction action = newRxAction(CREATEORUPDATESTOCKCHECKITEM);
        if (hasRxAction(action)) return;
        addRxAction(action, stockCheckAPI.createOrUpdateStockCheckItem(stockCheckOrderDetail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.CREATEORUPDATESTOCKCHECKITEM_WEBRETURN, stringWebReturn);
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
