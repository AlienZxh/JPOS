package com.j1j2.jposmvvm.features.adapter;

import android.content.Context;
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
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.AutoExpandableLinearLayout;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.BaseViewHolder;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.StorageStockDetail;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by alienzxh on 16-7-27.
 */
public class StorageProductAdapter extends RecyclerArrayAdapter<StorageStockDetail> {

    public interface StorageProductAdapterListener {
        void deleteAction(int position, StorageStockDetail storageStockDetail);

        void priceChangeAction(int position, StorageStockDetail storageStockDetail, double newPrice);

        void quantityChangeAction(int position, StorageStockDetail storageStockDetail, double newQuqntity);
    }

    private StorageProductAdapterListener storageProductAdapterListener;

    public StorageProductAdapter(Context context) {
        super(context);
    }

    public StorageProductAdapterListener getStorageProductAdapterListener() {
        return storageProductAdapterListener;
    }

    public void setStorageProductAdapterListener(StorageProductAdapterListener storageProductAdapterListener) {
        this.storageProductAdapterListener = storageProductAdapterListener;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new StorageProductViewHolder(parent);
    }

    public class StorageProductViewHolder extends BaseViewHolder<StorageStockDetail> implements View.OnClickListener, View.OnFocusChangeListener {
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
        private EditText productPrice;

        private TextView addBtn;
        private EditText productQuantity;
        private TextView minusBtn;

        public StorageProductViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_storage_product);
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
            productPrice = $(R.id.productPrice);

            addBtn = $(R.id.addBtn);
            productQuantity = $(R.id.productQuantity);
            minusBtn = $(R.id.minusBtn);
        }


        @Override
        public void setData(final StorageStockDetail data) {
            super.setData(data);
            expandableLayout.setOnClickListener(this);
            expandableLayout.setOnFocusChangeListener(this);
            productPrice.setOnFocusChangeListener(this);
            productQuantity.setOnFocusChangeListener(this);

            num.setText("" + (getCount() - getAdapterPosition()));
            storageStockName.setText(data.getProductName());
            storageStockCount.setText("数量：" + data.getQuantity() + data.getUnit());
            storageStockPrice.setText("进货价：" + data.getPrice());
            delete.setOnClickListener(this);
            //______________________________________________________________________________________
            Uri uri = Uri.parse(data.getImg() == null ? "" : data.getImg());
            ResizeOptions resizeOptions;
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(resizeOptions = new ResizeOptions(AutoUtils.getPercentHeightSize(160), AutoUtils.getPercentHeightSize(160)))
                    .build();
            //Logger.d("StockNoPictureProductAdapter ImageRequest resizeOptions" + resizeOptions.toString());
            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(productImg.getController())
                    .setImageRequest(request)
                    .build();
            productImg.setController(controller);
            productName.setText(data.getProductName());
            productUnit.setText("单位：" + data.getUnit());
            productSpec.setText("规格：" + data.getSpec());
            productBarCode.setText("条码：" + data.getBarCode());
            productPrice.setText("" + data.getPrice());
            productPrice.addTextChangedListener(new TextWatcher() {
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
                    double newPrice = 0;

                    try {
                        newPrice = Double.parseDouble(s.toString());
                    } catch (NumberFormatException e) {

                    }
                    if (newPrice < 0 || newPrice == data.getPrice())
                        return;
                    if (storageProductAdapterListener != null)
                        storageProductAdapterListener.priceChangeAction(getAdapterPosition(), getItem(getAdapterPosition()), newPrice);

                }
            });
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
                    if (storageProductAdapterListener != null)
                        storageProductAdapterListener.quantityChangeAction(getAdapterPosition(), getItem(getAdapterPosition()), newQuantity);

                }
            });
            addBtn.setOnClickListener(this);
            minusBtn.setOnClickListener(this);
            //______________________________________________________________________________________
            if (getAdapterPosition() == 0) {
                expandableLayout.requestFocus();
            }
        }

        @Override
        public void onClick(View v) {
            double newQuantity;

            switch (v.getId()) {
                case R.id.expandableLayout:
                    if (expandableLayout.isFocused() || productPrice.isFocused() || productQuantity.isFocused()) {
                        expandableLayout.clearFocus();
                        productPrice.clearFocus();
                        productQuantity.clearFocus();
                    } else {
                        expandableLayout.requestFocus();
                    }
                    break;

                case R.id.delete:
                    if (storageProductAdapterListener != null)
                        storageProductAdapterListener.deleteAction(getAdapterPosition(), getItem(getAdapterPosition()));
                    break;
                case R.id.expandContent:
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

            if (expandableLayout.isFocused() || productPrice.isFocused() || productQuantity.isFocused()) {
                expandableTitle.setVisibility(View.GONE);
                expandableLayout.expand(false);
            } else {
                expandableTitle.setVisibility(View.VISIBLE);
                expandableLayout.collapse(false);
            }


        }


    }

}
