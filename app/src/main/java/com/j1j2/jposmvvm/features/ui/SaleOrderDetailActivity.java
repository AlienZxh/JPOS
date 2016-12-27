package com.j1j2.jposmvvm.features.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.JPOSApplicationLike;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.data.model.QuerySaleOrderDetail;
import com.j1j2.jposmvvm.data.model.SaleStatic;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.ActivitySaleOrderDetailBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.SaleStatisticActionCreator;
import com.j1j2.jposmvvm.features.actions.SaleStatisticActions;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.modules.SaleStatisticModule;
import com.j1j2.jposmvvm.features.stores.SaleStatisticStore;
import com.j1j2.jposmvvm.features.viewmodel.UIViewModel;
import com.yatatsu.autobundle.AutoBundleField;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-8-19.
 */
public class SaleOrderDetailActivity extends BaseActivity {

    ActivitySaleOrderDetailBinding binding;

    @AutoBundleField
    int orderId;

    @Inject
    SaleStatisticStore saleStatisticStore;
    @Inject
    SaleStatisticActionCreator saleStatisticActionCreator;
    @Inject
    Toastor toastor;
    @Inject
    Navigate navigate;
    @Inject
    UIViewModel uiViewModel;
    @Inject
    Dispatcher dispatcher;

    @Override
    protected void setupActivityComponent() {
        SaleOrderDetailActivityAutoBundle.bind(this, getIntent());
        JPOSApplicationLike.get().getShopComponent().plus(new SaleStatisticModule(this)).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBinding() {


        binding = DataBindingUtil.setContentView(this, R.layout.activity_sale_order_detail);
    }

    @Override
    protected void initViews() {
        binding.detail.setLayoutManager(new LinearLayoutManager(this));

        saleStatisticActionCreator.querySaleOrderDetails(orderId);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case SaleStatisticStore.ID:
                switch (change.getRxAction().getType()) {
                    case SaleStatisticActions.QUERYSALEORDERDETAILS:
                        WebReturn<QuerySaleOrderDetail> querySaleOrderDetailWebReturn =
                                (WebReturn<QuerySaleOrderDetail>) change.getRxAction().get(Keys.QUERYSALEORDERDETAILS_WEBRETURN);
                        if (querySaleOrderDetailWebReturn.isValue()) {

                            QuerySaleOrderDetail querySaleOrderDetail = querySaleOrderDetailWebReturn.getDetail();

                            List<ItemDetail> itemDetailList = new ArrayList<>();
                            itemDetailList.add(new ItemDetail(1, "销售单详情", "", null));
                            itemDetailList.add(new ItemDetail(0, "客户", querySaleOrderDetail.getUserName(), null));
                            itemDetailList.add(new ItemDetail(0, "收银员", querySaleOrderDetail.getClerkName(), null));
                            itemDetailList.add(new ItemDetail(0, "收银时间", querySaleOrderDetail.getSettleTimeStr(), null));
                            itemDetailList.add(new ItemDetail(0, "本单金额", "" + querySaleOrderDetail.getAmount(), null));
                            if (querySaleOrderDetail.getSettleInfo().isIsSettle()) {
                                if (querySaleOrderDetail.getSettleInfo().getPayType() == 1)
                                    itemDetailList.add(new ItemDetail(0, "支付明细", "现金支付　" + querySaleOrderDetail.getSettleInfo().getPayAmount() + "\n现金找零　" + querySaleOrderDetail.getSettleInfo().getChange(), null));
                                if (querySaleOrderDetail.getSettleInfo().getPayType() == 2)
                                    itemDetailList.add(new ItemDetail(0, "支付明细", "支付宝支付　" + querySaleOrderDetail.getSettleInfo().getPayAmount(), null));
                                if (querySaleOrderDetail.getSettleInfo().getPayType() == 3)
                                    itemDetailList.add(new ItemDetail(0, "支付明细", "微信支付　" + querySaleOrderDetail.getSettleInfo().getPayAmount(), null));
                            } else {
                                itemDetailList.add(new ItemDetail(0, "支付明细", "未支付", null));
                            }
                            itemDetailList.add(new ItemDetail(0, "积分", "" + querySaleOrderDetail.getPoint(), null));
                            itemDetailList.add(new ItemDetail(1, "商品明细", "", null));
                            for (int i = 0; i < querySaleOrderDetail.getDetails().size(); i++) {
                                itemDetailList.add(new ItemDetail(2, "商品" + i, "", querySaleOrderDetail.getDetails().get(i)));
                            }

                            MultiItemTypeAdapter<ItemDetail> multiItemTypeAdapter = new MultiItemTypeAdapter<>(SaleOrderDetailActivity.this, itemDetailList);
                            multiItemTypeAdapter.addItemViewDelegate(new ItemViewDelegate<ItemDetail>() {
                                @Override
                                public int getItemViewLayoutId() {
                                    return R.layout.item_saleorder_detail_type0;
                                }

                                @Override
                                public boolean isForViewType(ItemDetail item, int position) {
                                    return item.getType() == 0;
                                }

                                @Override
                                public void convert(ViewHolder holder, ItemDetail itemDetail, int position) {
                                    holder.setText(R.id.title, itemDetail.getTitle() + "：");
                                    holder.setText(R.id.content, itemDetail.getContent());
                                }
                            });
                            multiItemTypeAdapter.addItemViewDelegate(new ItemViewDelegate<ItemDetail>() {
                                @Override
                                public int getItemViewLayoutId() {
                                    return R.layout.item_saleorder_detail_type1;
                                }

                                @Override
                                public boolean isForViewType(ItemDetail item, int position) {
                                    return item.getType() == 1;
                                }

                                @Override
                                public void convert(ViewHolder holder, ItemDetail itemDetail, int position) {
                                    holder.setText(R.id.title, itemDetail.getTitle());
                                }
                            });
                            multiItemTypeAdapter.addItemViewDelegate(new ItemViewDelegate<ItemDetail>() {
                                @Override
                                public int getItemViewLayoutId() {
                                    return R.layout.item_saleorder_detail_type2;
                                }

                                @Override
                                public boolean isForViewType(ItemDetail item, int position) {
                                    return item.getType() == 2;
                                }

                                @Override
                                public void convert(ViewHolder holder, ItemDetail itemDetail, int position) {
                                    holder.setText(R.id.name, itemDetail.getSalrOrderProduct().getName());
                                    holder.setText(R.id.price, "价格：￥" + itemDetail.getSalrOrderProduct().getPrice());
                                    holder.setText(R.id.quantity, "X" + itemDetail.getSalrOrderProduct().getQuantity());
                                }
                            });

                            binding.detail.setAdapter(multiItemTypeAdapter);
                        } else {

                            toastor.showSingletonToast(querySaleOrderDetailWebReturn.getErrorMessage());
                        }
                        break;
                }
                break;
        }
    }

    @Override
    public void onRxError(@NonNull RxError error) {

    }

    @Override
    public void onRxViewRegistered() {

    }

    @Override
    public void onRxViewUnRegistered() {

    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return null;
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return null;
    }

    class ItemDetail {
        private int type; //0:普通内容　　1:标题　　2:商品列表内容
        private String title;
        private String content;
        private QuerySaleOrderDetail.SaleOrderProduct salrOrderProduct;

        public ItemDetail(int type, String title, String content, QuerySaleOrderDetail.SaleOrderProduct salrOrderProduct) {
            this.type = type;
            this.title = title;
            this.content = content;
            this.salrOrderProduct = salrOrderProduct;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public QuerySaleOrderDetail.SaleOrderProduct getSalrOrderProduct() {
            return salrOrderProduct;
        }

        public void setSalrOrderProduct(QuerySaleOrderDetail.SaleOrderProduct salrOrderProduct) {
            this.salrOrderProduct = salrOrderProduct;
        }
    }
}
