package com.j1j2.jposmvvm.features.actions;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.action.RxActionCreator;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;
import com.j1j2.jposmvvm.data.api.StorageAPI;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.Product;
import com.j1j2.jposmvvm.data.model.StorageOrder;
import com.j1j2.jposmvvm.data.model.StorageOrderItem;
import com.j1j2.jposmvvm.data.model.StorageStock;
import com.j1j2.jposmvvm.data.model.StorageStockDetail;
import com.j1j2.jposmvvm.data.model.WebReturn;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-7-18.
 */
public class StorageActionCreator extends RxActionCreator implements StorageActions {

    private StorageAPI storageAPI;

    public StorageActionCreator(Dispatcher dispatcher, SubscriptionManager manager, StorageAPI storageAPI) {
        super(dispatcher, manager);
        this.storageAPI = storageAPI;
    }

    @Override
    public void queryStorageOrders(int pageIndex) {
        final RxAction action = newRxAction(QUERYSTORAGEORDERS);
        if (hasRxAction(action)) return;
        addRxAction(action, storageAPI.queryStorageOrders(pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<PageManager<List<StorageOrder>>>>() {
                    @Override
                    public void call(WebReturn<PageManager<List<StorageOrder>>> pageManagerWebReturn) {
                        action.getData().put(Keys.STORAGEORDERS_WEBRETURN, pageManagerWebReturn);
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
    public void createUpdateStorageOrder(StorageOrder storageOrder) {
        final RxAction action = newRxAction(CREATEUPDATESTORAGEORDER);
        if (hasRxAction(action)) return;
        addRxAction(action, storageAPI.createUpdateStorageOrder(storageOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.CREATEUPDATESTORAGEORDER_WEBRETURN, stringWebReturn);
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
    public void queryStocks(int pageIndex, String key, int orderId) {
        final RxAction action = newRxAction(QUERYSTOCKS);
        if (hasRxAction(action)) return;
        addRxAction(action, storageAPI.queryStocks(pageIndex, key, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<PageManager<List<StorageStock>>>>() {
                    @Override
                    public void call(WebReturn<PageManager<List<StorageStock>>> pageManagerWebReturn) {
                        action.getData().put(Keys.QUERYSTOCKS_WEBRETURN, pageManagerWebReturn);
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
    public void createOrUpdateStoreageOrderDetailItem(StorageOrderItem storageOrderItem, final boolean needRefresh) {
        final RxAction action = newRxAction(CREATEORUPDATESTOREAGEORDERDETAILITEM);
        if (hasRxAction(action)) return;
        addRxAction(action, storageAPI.createOrUpdateStoreageOrderDetailItem(storageOrderItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.CREATEORUPDATESTOREAGEORDERDETAILITEM_ISREFRESH, needRefresh);
                        action.getData().put(Keys.CREATEORUPDATESTOREAGEORDERDETAILITEM_WEBRETURN, stringWebReturn);
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
    public void removeStoreageOrderDetailItem(int detailId, int orderId) {
        final RxAction action = newRxAction(REMOVESTOREAGEORDERDETAILITEM);
        if (hasRxAction(action)) return;
        addRxAction(action, storageAPI.removeStoreageOrderDetailItem(detailId, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.REMOVESTOREAGEORDERDETAILITEM_WEBRETURN, stringWebReturn);
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
    public void queryStoreOrderDetails(int storageOrderId) {
        final RxAction action = newRxAction(QUERYSTOREORDERDETAILS);
        if (hasRxAction(action)) return;
        addRxAction(action, storageAPI.queryStoreOrderDetails(storageOrderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<List<StorageStockDetail>>>() {
                    @Override
                    public void call(WebReturn<List<StorageStockDetail>> listWebReturn) {
                        action.getData().put(Keys.QUERYSTOREORDERDETAILS_WEBRETURN, listWebReturn);
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
    public void auditStorageOrder(int orderId) {
        final RxAction action = newRxAction(AUDITSTORAGEORDER);
        if (hasRxAction(action)) return;
        addRxAction(action, storageAPI.auditStorageOrder(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.AUDITSTORAGEORDER_WEBRETURN, stringWebReturn);
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
    public void refreshStorageOrders() {
        final RxAction action = newRxAction(REFRESHSTORAGEORDERS);
        postRxAction(action);
    }
}
