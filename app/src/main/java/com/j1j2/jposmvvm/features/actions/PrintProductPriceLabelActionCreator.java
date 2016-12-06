package com.j1j2.jposmvvm.features.actions;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.action.RxActionCreator;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;
import com.j1j2.jposmvvm.data.api.PrintProductPriceLabelAPI;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.PrintPriceLabelProduct;
import com.j1j2.jposmvvm.data.model.ProductPriceLabelOrder;
import com.j1j2.jposmvvm.data.model.WebReturn;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-10-22.
 */

public class PrintProductPriceLabelActionCreator extends RxActionCreator implements PrintProductPriceLabelActions {
    private PrintProductPriceLabelAPI printProductPriceLabelAPI;

    public PrintProductPriceLabelActionCreator(Dispatcher dispatcher, SubscriptionManager manager, PrintProductPriceLabelAPI printProductPriceLabelAPI) {
        super(dispatcher, manager);
        this.printProductPriceLabelAPI = printProductPriceLabelAPI;
    }

    @Override
    public void createProductPriceLabelOrder(String note) {
        final RxAction action = newRxAction(CREATEPRODUCTPRICELABELORDER);
        if (hasRxAction(action)) return;
        addRxAction(action, printProductPriceLabelAPI.createProductPriceLabelOrder(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.CREATEPRODUCTPRICELABELORDER_WEBRETUN, stringWebReturn);
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
    public void queryProductPriceLabelOrders(int pageIndex, int pageSize) {
        final RxAction action = newRxAction(QUERYPRODUCTPRICELABELORDERS);
        if (hasRxAction(action)) return;
        addRxAction(action, printProductPriceLabelAPI.queryProductPriceLabelOrders(pageIndex, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<PageManager<List<ProductPriceLabelOrder>>>>() {
                    @Override
                    public void call(WebReturn<PageManager<List<ProductPriceLabelOrder>>> listWebReturn) {
                        action.getData().put(Keys.QUERYPRODUCTPRICELABELORDERS_WEBRETURN, listWebReturn);
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
    public void queryProductInPrintPriceLabel(String key) {
        final RxAction action = newRxAction(QUERYPRODUCTINPRINTPRICELABEL);
        if (hasRxAction(action)) return;
        addRxAction(action, printProductPriceLabelAPI.queryProductInPrintPriceLabel( key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<List<PrintPriceLabelProduct>>>() {
                    @Override
                    public void call(WebReturn<List<PrintPriceLabelProduct>> listWebReturn) {
                        action.getData().put(Keys.QUERYPRODUCTINPRINTPRICELABEL_WEBRETURN, listWebReturn);
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
    public void queryPrintPriceLabelOrderDetails(int pageIndex, int pageSize, int orderId) {
        final RxAction action = newRxAction(QUERYPRINTPRICELABELORDERDETAILS);
        if (hasRxAction(action)) return;
        addRxAction(action, printProductPriceLabelAPI.queryPrintPriceLabelOrderDetails(pageIndex, pageSize,orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<PageManager<List<PrintPriceLabelProduct>>>>() {
                    @Override
                    public void call(WebReturn<PageManager<List<PrintPriceLabelProduct>>> pageManagerWebReturn) {
                        action.getData().put(Keys.QUERYPRINTPRICELABELORDERDETAILS_WEBRETURN, pageManagerWebReturn);
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
    public void createPrintPriceLabelProduct(int orderId, int stockId) {
        final RxAction action = newRxAction(CREATEPRINTPRICELABELPRODUCT);
        if (hasRxAction(action)) return;
        addRxAction(action, printProductPriceLabelAPI.createPrintPriceLabelProduct(orderId, stockId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.CREATEPRINTPRICELABELPRODUCT_WEBRETURN, stringWebReturn);
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
    public void removePrintPriceLabelPrdouct(int orderDetailId) {
        final RxAction action = newRxAction(REMOVEPRINTPRICELABELPRDOUCT);
        if (hasRxAction(action)) return;
        addRxAction(action, printProductPriceLabelAPI.removePrintPriceLabelPrdouct(orderDetailId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.REMOVEPRINTPRICELABELPRDOUCT_WEBRETURN, stringWebReturn);
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
    public void scanStock(String barcode) {
        final RxAction action = newRxAction(SCANSTOCK);
        if (hasRxAction(action)) return;
        addRxAction(action, printProductPriceLabelAPI.queryProductInPrintPriceLabel(barcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<List<PrintPriceLabelProduct>>>() {
                    @Override
                    public void call(WebReturn<List<PrintPriceLabelProduct>> listWebReturn) {
                        action.getData().put(Keys.PRINTPRICELABELSCANSTOCK_WEBRETURN, listWebReturn);
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
