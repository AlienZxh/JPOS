package com.j1j2.jposmvvm.features.actions;

import com.google.gson.Gson;
import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.action.RxActionCreator;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;
import com.j1j2.jposmvvm.data.api.StockAPI;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.Product;
import com.j1j2.jposmvvm.data.model.ProductDetail;
import com.j1j2.jposmvvm.data.model.TopCategory;
import com.j1j2.jposmvvm.data.model.WebReturn;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-6-7.
 */
public class StockActionCreator extends RxActionCreator implements StockActions {


    private StockAPI stockAPI;
    private Gson gson;

    public StockActionCreator(Dispatcher dispatcher, SubscriptionManager manager, StockAPI stockAPI, Gson gson) {
        super(dispatcher, manager);
        this.stockAPI = stockAPI;
        this.gson = gson;
    }

    @Override
    public void queryStockCategory(String filterNoImgProduct) {
        final RxAction action = newRxAction(QUERYSTOCKCATEGORY);
        if (hasRxAction(action)) return;
        addRxAction(action, stockAPI.queryStockCategory(filterNoImgProduct)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<List<TopCategory>>>() {
                    @Override
                    public void call(WebReturn<List<TopCategory>> listWebReturn) {
                        action.getData().put(Keys.CATEGORY_WEBRETURN, listWebReturn);
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
    public void queryStocksIfNotFoundThenCreate(int pageIndex, String keyWords, int suggestPriceType, String categoryName, String subCategoryName, String filterNoImgProduct) {
        final RxAction action = newRxAction(QUERYSTOCKSIFNOTFOUNDTHENCREATE);
        if (hasRxAction(action)) return;
        addRxAction(action, stockAPI.queryStocksIfNotFoundThenCreate(pageIndex, keyWords, suggestPriceType, categoryName, subCategoryName, filterNoImgProduct)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<PageManager<List<Product>>>>() {
                    @Override
                    public void call(WebReturn<PageManager<List<Product>>> pageManagerWebReturn) {
                        action.getData().put(Keys.PRODUCTS_WEBRETURN, pageManagerWebReturn);
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
    public void searchStocks(int pageIndex, String keyWords, int suggestPriceType, String categoryName, String subCategoryName, String filterNoImgProduct) {
        final RxAction action = newRxAction(SEARCHSTOCKS);
        if (hasRxAction(action)) return;
        addRxAction(action, stockAPI.queryStocksIfNotFoundThenCreate(pageIndex, keyWords, suggestPriceType, categoryName, subCategoryName, filterNoImgProduct)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<PageManager<List<Product>>>>() {
                    @Override
                    public void call(WebReturn<PageManager<List<Product>>> pageManagerWebReturn) {
                        action.getData().put(Keys.PRODUCTS_WEBRETURN, pageManagerWebReturn);
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
    public void queryStockDetail(int stockId) {
        final RxAction action = newRxAction(QUERYSTOCKDETAIL);
        if (hasRxAction(action)) return;
        addRxAction(action, stockAPI.queryStockDetail(stockId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<ProductDetail>>() {
                    @Override
                    public void call(WebReturn<ProductDetail> pageManagerWebReturn) {
                        action.getData().put(Keys.PRODUCTDETAIL_WEBRETURN, pageManagerWebReturn);
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
    public void createOrUpdateStock(ProductDetail productDetail) {
        final RxAction action = newRxAction(CREATEORUPDATESTOCK);
        if (hasRxAction(action)) return;
        addRxAction(action, stockAPI.createOrUpdateStock(productDetail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.CREATEORUPDATESTOCK_WEBRETURN, stringWebReturn);
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
    public void removeStockImg(int stockId, int imgId) {
        final RxAction action = newRxAction(REMOVESTOCKIMG);
        if (hasRxAction(action)) return;
        addRxAction(action, stockAPI.removeStockImg(stockId, imgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.REMOVESTOCKIMG_WEBRETURN, stringWebReturn);
                        postRxAction(action);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        postError(action, throwable);
                    }
                }));
    }

    public void upLoadPhoto(String path, int stockId, int imgId, boolean isDefaultImg, int imgSource) {
//        String absolutelyPath = Environment.getExternalStorageDirectory() + "/" + path;
        File uploadFile = new File(path);
        MultipartBody.Part file = MultipartBody.Part.createFormData(
                "imgFile",
                uploadFile.getName(),
                RequestBody.create(MediaType.parse("image/*"), uploadFile));

        final RxAction action = newRxAction(UPLOADIMG);
        if (hasRxAction(action)) return;
        addRxAction(action, stockAPI.uploadStockImg(stockId, imgId == 0 ? "" : String.valueOf(imgId), isDefaultImg, imgSource, file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<String>>() {
                    @Override
                    public void call(WebReturn<String> stringWebReturn) {
                        action.getData().put(Keys.UPLOADSTOCKIMG_WEBRETURN, stringWebReturn);
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
    public void refreshList(int fromType, int position, ProductDetail productDetail) {
        final RxAction action = newRxAction(REFRESHLIST);
        action.getData().put(Keys.REFRESHLISTFROMTYPE, fromType);
        action.getData().put(Keys.REFRESHLISTPOSITION, position);
        action.getData().put(Keys.REFRESHLISTPRODUCTDETAIL, productDetail);
        postRxAction(action);
    }
}
