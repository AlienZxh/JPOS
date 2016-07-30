package com.j1j2.jposmvvm.features.actions;

import com.j1j2.jposmvvm.data.api.body.AddShopSupplierBody;

/**
 * Created by alienzxh on 16-7-26.
 */
public interface SupplierActions {

    String QUERYSHOPSUPPLIERS = "queryShopSuppliers";
    String ADDSHOPSUPPLIER = "addShopSupplier";

    void queryShopSuppliers();

    void addShopSupplier(AddShopSupplierBody addShopSupplierBody);
}
