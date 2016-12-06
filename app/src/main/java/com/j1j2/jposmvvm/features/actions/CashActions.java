package com.j1j2.jposmvvm.features.actions;

import com.j1j2.jposmvvm.data.model.ProductDetail;
import com.j1j2.jposmvvm.data.model.SaleOrderSettleBody;

/**
 * Created by alienzxh on 16-8-1.
 */
public interface CashActions {
    String PUZZYQUERYSCAN = "puzzyQueryScan";
    String QUERYSALEORDERNO = "querySaleOrderNO";
    String SETTLESALEORDER = "settleSaleOrder";
    String REFRESHLISTITEM = "refreshListItem";
    String SCANQUERY = "scanQuery";


    void puzzyQueryScan(String key);

    void querySaleOrderNO();

    void settleSaleOrder(SaleOrderSettleBody saleOrderSettleBody);

    void refreshListItem(int fromType, int position, ProductDetail productDetail);

    void scanQuery(String key);
}
