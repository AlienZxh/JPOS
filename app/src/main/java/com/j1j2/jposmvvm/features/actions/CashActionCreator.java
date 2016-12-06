package com.j1j2.jposmvvm.features.actions;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.action.RxActionCreator;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;
import com.j1j2.jposmvvm.data.api.CashAPI;
import com.j1j2.jposmvvm.data.model.CashPuzzyQuery;
import com.j1j2.jposmvvm.data.model.ProductDetail;
import com.j1j2.jposmvvm.data.model.SaleOrderSettleBody;
import com.j1j2.jposmvvm.data.model.TopCategory;
import com.j1j2.jposmvvm.data.model.WebReturn;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-8-1.
 */
public class CashActionCreator extends RxActionCreator implements CashActions {

    private CashAPI cashAPI;

    public CashActionCreator(Dispatcher dispatcher, SubscriptionManager manager, CashAPI cashAPI) {
        super(dispatcher, manager);
        this.cashAPI = cashAPI;
    }

    @Override
    public void puzzyQueryScan(String key) {
        final RxAction action = newRxAction(PUZZYQUERYSCAN);
        if (hasRxAction(action)) return;
        addRxAction(action, cashAPI.puzzyQueryScan(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<CashPuzzyQuery>>() {
                    @Override
                    public void call(WebReturn<CashPuzzyQuery> cashPuzzyQueryWebReturn) {
                        action.getData().put(Keys.PUZZYQUERYSCAN_WEBRETURN, cashPuzzyQueryWebReturn);
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
    public void scanQuery(String key) {
        final RxAction action = newRxAction(SCANQUERY);
        if (hasRxAction(action)) return;
        addRxAction(action, cashAPI.puzzyQueryScan(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<CashPuzzyQuery>>() {
                    @Override
                    public void call(WebReturn<CashPuzzyQuery> cashPuzzyQueryWebReturn) {
                        action.getData().put(Keys.SCANQUERY_WEBRETURN, cashPuzzyQueryWebReturn);
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
    public void querySaleOrderNO() {
        final RxAction action = newRxAction(QUERYSALEORDERNO);
        if (hasRxAction(action)) return;
        addRxAction(action, cashAPI.querySaleOrderNO()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.QUERYSALEORDERNO_WEBRETURN, stringWebReturn);
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
    public void settleSaleOrder(SaleOrderSettleBody saleOrderSettleBody) {
        final RxAction action = newRxAction(SETTLESALEORDER);
        if (hasRxAction(action)) return;
        addRxAction(action, cashAPI.settleSaleOrder(saleOrderSettleBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.SETTLESALEORDER_WEBRETURN, stringWebReturn);
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
    public void refreshListItem(int fromType, int position, ProductDetail productDetail) {
        final RxAction action = newRxAction(REFRESHLISTITEM);
        action.getData().put(Keys.REFRESHLISTFROMTYPE, fromType);
        action.getData().put(Keys.REFRESHLISTPOSITION, position);
        action.getData().put(Keys.REFRESHLISTPRODUCTDETAIL, productDetail);
        postRxAction(action);
    }
}
