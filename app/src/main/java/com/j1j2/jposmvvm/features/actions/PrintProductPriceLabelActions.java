package com.j1j2.jposmvvm.features.actions;

import retrofit2.http.Query;

/**
 * Created by alienzxh on 16-10-22.
 */

public interface PrintProductPriceLabelActions {
    String CREATEPRODUCTPRICELABELORDER = "createProductPriceLabelOrder";
    String QUERYPRODUCTPRICELABELORDERS = "queryProductPriceLabelOrders";
    String QUERYPRODUCTINPRINTPRICELABEL = "queryProductInPrintPriceLabel";
    String QUERYPRINTPRICELABELORDERDETAILS = "queryPrintPriceLabelOrderDetails";
    String CREATEPRINTPRICELABELPRODUCT = "createPrintPriceLabelProduct";
    String REMOVEPRINTPRICELABELPRDOUCT = "removePrintPriceLabelPrdouct";
    String SCANSTOCK = "scanStock";

    void createProductPriceLabelOrder(String note);

    void queryProductPriceLabelOrders(int pageIndex, int pageSize);

    void queryProductInPrintPriceLabel( String key);

    void queryPrintPriceLabelOrderDetails(int pageIndex, int pageSize, int orderId);

    void createPrintPriceLabelProduct(int orderId, int stockId);

    void removePrintPriceLabelPrdouct(int orderDetailId);

    void scanStock(String barCode);
}
