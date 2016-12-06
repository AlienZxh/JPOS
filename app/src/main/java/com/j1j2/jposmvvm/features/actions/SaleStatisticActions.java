package com.j1j2.jposmvvm.features.actions;

/**
 * Created by alienzxh on 16-8-15.
 */
public interface SaleStatisticActions {
    String LOADSALESTATICBYMONTH = "loadSaleStaticByMonth";
    String LOADSALESTATICINMONTHBYCATEGORY = "loadSaleStaticInMonthByCategory";
    String LOADSALEORDERS = "loadSaleOrders";
    String QUERYSALEORDERDETAILS = "querySaleOrderDetails";

    void loadSaleStaticByMonth(int year, int month);

    void loadSaleStaticInMonthByCategory(int year, int month);

    void loadSaleOrders(int pageIndex, int pageSize, String beginTime, String endTime);

    void querySaleOrderDetails(int orderId);
}
