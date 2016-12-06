package com.j1j2.jposmvvm.features.actions;

import com.j1j2.jposmvvm.data.model.ProductDetail;
import com.j1j2.jposmvvm.data.model.StorageOrder;
import com.j1j2.jposmvvm.data.model.StorageOrderItem;

/**
 * Created by alienzxh on 16-7-18.
 */
public interface StorageActions {
    String QUERYSTORAGEORDERS = "queryStockCategory";
    String CREATEUPDATESTORAGEORDER = "createUpdateStorageOrder";
    String QUERYSTOCKS = "queryStocks";
    String CREATEORUPDATESTOREAGEORDERDETAILITEM = "createOrUpdateStoreageOrderDetailItem";
    String REMOVESTOREAGEORDERDETAILITEM = "removeStoreageOrderDetailItem";
    String QUERYSTOREORDERDETAILS = "queryStoreOrderDetails";
    String AUDITSTORAGEORDER = "auditStorageOrder";
    String REFRESHSTORAGEORDERS = "refreshStorageOrders";
    String REFRESHLISTITEM = "refreshListItem";
    String SCANSTOCKS = "scanStocks";

    void queryStorageOrders(int pageIndex);

    void createUpdateStorageOrder(StorageOrder storageOrder);

    void queryStocks(int pageIndex, String key, int orderId);

    void createOrUpdateStoreageOrderDetailItem(StorageOrderItem storageOrderItem, boolean needRefresh);

    void removeStoreageOrderDetailItem(int detailId, int orderId);

    void queryStoreOrderDetails(int storageOrderId);

    void auditStorageOrder(int orderId);

    void refreshStorageOrders();

    void refreshListItem(int fromType, int position, ProductDetail productDetail);

    void scanStocks(int pageIndex, String key, int orderId);
}


