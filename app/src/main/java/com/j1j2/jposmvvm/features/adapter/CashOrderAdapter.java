package com.j1j2.jposmvvm.features.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.AutoExpandableLinearLayout;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.BaseViewHolder;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.CashPuzzyQueryStock;
import com.orhanobut.logger.Logger;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by alienzxh on 16-8-1.
 */
public class CashOrderAdapter extends RecyclerArrayAdapter<CashPuzzyQueryStock> {
    public interface CashOrderAdapterListener {
        void imgClickAction(int position, CashPuzzyQueryStock cashOrderStock);

        void deleteAction(int position, CashPuzzyQueryStock cashOrderStock);

        void quantityChangeAction(int position, CashPuzzyQueryStock cashOrderStock, double newQuqntity);
    }

    private int expandPosition = 0;

    private CashOrderAdapterListener cashOrderAdapterListener;

    public CashOrderAdapter(Context context) {
        super(context);
    }

    public CashOrderAdapterListener getCashOrderAdapterListener() {
        return cashOrderAdapterListener;
    }

    public void setCashOrderAdapterListener(CashOrderAdapterListener cashOrderAdapterListener) {
        this.cashOrderAdapterListener = cashOrderAdapterListener;
    }

    public int getExpandPosition() {
        return expandPosition;
    }

    public void setExpandPosition(int expandPosition) {
        this.expandPosition = expandPosition;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new StorageProductViewHolder(parent);
    }

    public class StorageProductViewHolder extends BaseViewHolder<CashPuzzyQueryStock> implements View.OnClickListener, View.OnFocusChangeListener {
        private AutoExpandableLinearLayout expandableLayout;
        private AutoLinearLayout expandableTitle;
        private AutoLinearLayout expandableContent;

        private TextView num;
        private TextView storageStockName;
        private TextView storageStockCount;
        private TextView storageStockPrice;
        private TextView delete;

        private SimpleDraweeView productImg;
        private TextView productName;
        private TextView productUnit;
        private TextView productSpec;
        private TextView productBarCode;
        private TextView productRetailPrice;
        private TextView productMemberPrice;

        private TextView addBtn;
        private EditText productQuantity;
        private TextView minusBtn;

        public StorageProductViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_cash_order);
            expandableLayout = $(R.id.expandableLayout);
            expandableTitle = $(R.id.expandTitle);
            expandableContent = $(R.id.expandContent);

            num = $(R.id.num);
            storageStockName = $(R.id.storageStockName);
            storageStockCount = $(R.id.storageStockCount);
            storageStockPrice = $(R.id.storageStockPrice);
            delete = $(R.id.delete);

            productImg = $(R.id.productImg);
            productName = $(R.id.productName);
            productUnit = $(R.id.productUnit);
            productSpec = $(R.id.productSpec);
            productBarCode = $(R.id.productBarCode);
            productRetailPrice = $(R.id.productRetailPrice);
            productMemberPrice = $(R.id.productMemberPrice);

            addBtn = $(R.id.addBtn);
            productQuantity = $(R.id.productQuantity);
            minusBtn = $(R.id.minusBtn);
        }


        @Override
        public void setData(final CashPuzzyQueryStock data) {
            super.setData(data);
            expandableLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableLayout.isFocused() || productQuantity.isFocused()) {
                        productQuantity.clearFocus();
                        expandableLayout.clearFocus();
                    } else {
                        expandableLayout.requestFocus();
                    }
                }
            });
            expandableLayout.setOnFocusChangeListener(this);
            productQuantity.setOnFocusChangeListener(this);

            num.setText("" + (getCount() - getAdapterPosition()));
            storageStockName.setText(data.getName());
            storageStockCount.setText("数量：" + data.getQuantity() + data.getUnit());
            storageStockPrice.setText("单价：" + data.getRetailPrice());
            delete.setOnClickListener(this);
            //______________________________________________________________________________________
            Uri uri = Uri.parse(data.getSmallImgUrl() == null ?  ""  : data.getSmallImgUrl());
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(AutoUtils.getPercentHeightSize(160), AutoUtils.getPercentHeightSize(160)))
                    .build();
            //Logger.d("StockNoPictureProductAdapter ImageRequest resizeOptions" + resizeOptions.toString());
            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(productImg.getController())
                    .setImageRequest(request)
                    .build();
            productImg.setController(controller);
            productImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cashOrderAdapterListener != null)
                        cashOrderAdapterListener.imgClickAction(getAdapterPosition(), data);
                }
            });
            productName.setText(data.getName());
            productUnit.setText("单位：" + data.getUnit());
            productSpec.setText("规格：" + data.getSpec());
            productBarCode.setText("条码：" + data.getBarCode());
            productRetailPrice.setText("进货价：" + data.getRetailPrice());
            productMemberPrice.setText("会员价：" + data.getMemberPrice());
            //_________________________________________________________________
            productQuantity.setText("" + data.getQuantity());
            productQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s.toString()))
                        return;
                    double newQuantity = 0;
                    try {
                        newQuantity = Double.parseDouble(s.toString());
                    } catch (NumberFormatException e) {

                    }
                    if (newQuantity < 0 || newQuantity == data.getQuantity())
                        return;
                    if (cashOrderAdapterListener != null)
                        cashOrderAdapterListener.quantityChangeAction(getAdapterPosition(), getItem(getAdapterPosition()), newQuantity);

                }
            });
            addBtn.setOnClickListener(this);
            minusBtn.setOnClickListener(this);
            //______________________________________________________________________________________
            if (getAdapterPosition() == expandPosition) {
                expandableLayout.requestFocus();
            }
        }

        @Override
        public void onClick(View v) {
            double newQuantity;

            switch (v.getId()) {
                case R.id.expandableLayout:
                    if (expandableLayout.isFocused() || productQuantity.isFocused()) {
                        productQuantity.clearFocus();
                        expandableLayout.clearFocus();
                    } else {
                        expandableLayout.requestFocus();
                    }
                    break;
                case R.id.delete:
                    if (cashOrderAdapterListener != null)
                        cashOrderAdapterListener.deleteAction(getAdapterPosition(), getItem(getAdapterPosition()));
                    break;
                case R.id.addBtn:
                    newQuantity = Double.parseDouble(productQuantity.getText().toString());
                    newQuantity++;
                    productQuantity.setText("" + newQuantity);
                    break;
                case R.id.minusBtn:
                    newQuantity = Double.parseDouble(productQuantity.getText().toString());
                    if (newQuantity == 0)
                        break;
                    newQuantity--;
                    productQuantity.setText("" + newQuantity);
                    break;
            }

        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (expandableLayout.isFocused() || productQuantity.isFocused()) {
                expandableTitle.setVisibility(View.GONE);
                expandableLayout.expand(false);
            } else {
                expandableTitle.setVisibility(View.VISIBLE);
                expandableLayout.collapse(false);
            }
        }


    }
}
