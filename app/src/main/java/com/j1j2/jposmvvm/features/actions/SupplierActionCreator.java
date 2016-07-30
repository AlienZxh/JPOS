package com.j1j2.jposmvvm.features.actions;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.action.RxActionCreator;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;
import com.j1j2.jposmvvm.data.api.SupplierAPI;
import com.j1j2.jposmvvm.data.api.body.AddShopSupplierBody;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.StorageOrder;
import com.j1j2.jposmvvm.data.model.Supplier;
import com.j1j2.jposmvvm.data.model.WebReturn;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-7-26.
 */
public class SupplierActionCreator extends RxActionCreator implements SupplierActions {

    private SupplierAPI supplierAPI;

    public SupplierActionCreator(Dispatcher dispatcher, SubscriptionManager manager, SupplierAPI supplierAPI) {
        super(dispatcher, manager);
        this.supplierAPI = supplierAPI;
    }

    @Override
    public void queryShopSuppliers() {
        final RxAction action = newRxAction(QUERYSHOPSUPPLIERS);
        if (hasRxAction(action)) return;
        addRxAction(action, supplierAPI.queryShopSuppliers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<List<Supplier>>>() {
                    @Override
                    public void call(WebReturn<List<Supplier>> pageManagerWebReturn) {
                        action.getData().put(Keys.SUPPLIERS_WEBRETURN, pageManagerWebReturn);
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
    public void addShopSupplier(AddShopSupplierBody addShopSupplierBody) {
        final RxAction action = newRxAction(ADDSHOPSUPPLIER);
        if (hasRxAction(action)) return;
        addRxAction(action, supplierAPI.addShopSupplier(addShopSupplierBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.ADDSUPPLIER_WEBRETURN, stringWebReturn);
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
