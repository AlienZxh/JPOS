package com.j1j2.jposmvvm.features.actions;

import com.j1j2.jposmvvm.data.model.ProductDetail;

/**
 * Created by alienzxh on 16-6-7.
 */
public interface StockActions {
    String QUERYSTOCKCATEGORY = "queryStockCategory";
    String QUERYSTOCKSIFNOTFOUNDTHENCREATE = "queryStocksIfNotFoundThenCreate";
    String SEARCHSTOCKS = "searchStocks";
    String QUERYSTOCKDETAIL = "queryStockDetail";
    String CREATEORUPDATESTOCK = "createOrUpdateStock";
    String REMOVESTOCKIMG = "removeStockImg";
    String UPLOADIMG = "uploadImg";
    String REFRESHLIST = "refreshList";

    void queryStockCategory(String filterNoImgProduct);

    void queryStocksIfNotFoundThenCreate(int pageIndex, String keyWords, int suggestPriceType, String categoryName, String subCategoryName, String filterNoImgProduct);

    void searchStocks(int pageIndex, String keyWords, int suggestPriceType, String categoryName, String subCategoryName, String filterNoImgProduct);

    void queryStockDetail(int stockId);

    void createOrUpdateStock(ProductDetail productDetail);

    void removeStockImg(int stockId, int imgId);

    void upLoadPhoto(String path, int stockId, int imgId, boolean isDefaultImg, int imgSource);

    void refreshList(int fromType,int position,ProductDetail productDetail);
}
