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
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.common.widgets.AutoExpandableLinearLayout;
import com.j1j2.jposmvvm.common.widgets.DelayChangeEditText;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.BaseViewHolder;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.StockCheckOrderDetail;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by alienzxh on 16-9-19.
 */
public class StockCheckProductAdapter extends RecyclerArrayAdapter<StockCheckOrderDetail> {

    public interface StockCheckProductAdapterListener {
//        void deleteAction(int position, StockCheckOrderDetail data);

        void modifyAction(int position, StockCheckOrderDetail data);

        void quantityChangeAction(int position, StockCheckOrderDetail data, double newQuqntity);
    }

    private Toastor toastor;

    public StockCheckProductAdapter(Context context, Toastor toastor) {
        super(context);
        this.toastor = toastor;
    }

    private StockCheckProductAdapterListener listener;

    private int expandPosition = 0;

    public StockCheckProductAdapterListener getListener() {
        return listener;
    }

    public void setListener(StockCheckProductAdapterListener listener) {
        this.listener = listener;
    }

    public int getExpandPosition() {
        return expandPosition;
    }

    public void setExpandPosition(int expandPosition) {
        this.expandPosition = expandPosition;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new StockCheckProductViewHolder(parent);
    }

    public class StockCheckProductViewHolder extends BaseViewHolder<StockCheckOrderDetail> implements View.OnClickListener, View.OnFocusChangeListener {

        private AutoExpandableLinearLayout expandableLayout;
        private AutoLinearLayout expandableTitle;
        private AutoLinearLayout expandableContent;

        private TextView num;
        private TextView stockCheckName;
        private TextView stockCheckCount;
        private TextView differCount;
        private TextView stockCheckAmount;
        private TextView differAmount;
        private TextView delete;

        //        private SimpleDraweeView productImg;
        private TextView productName;
        private TextView productUnit;
        private TextView productSpec;
        private TextView productBarCode;
        private TextView price;
        private TextView deltaCount;

        private TextView addBtn;
        private DelayChangeEditText productQuantity;
        private TextView minusBtn;


        public StockCheckProductViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_stockcheck_product);
            expandableLayout = $(R.id.expandableLayout);
            expandableTitle = $(R.id.expandTitle);
            expandableContent = $(R.id.expandContent);

            num = $(R.id.num);
            stockCheckName = $(R.id.stockCheckName);
            stockCheckCount = $(R.id.stockCheckCount);
            differCount = $(R.id.differCount);
            stockCheckAmount = $(R.id.stockCheckAmount);
            differAmount = $(R.id.differAmount);
            delete = $(R.id.delete);

//            productImg = $(R.id.productImg);
            productName = $(R.id.productName);
            productUnit = $(R.id.productUnit);
            productSpec = $(R.id.productSpec);
            productBarCode = $(R.id.productBarCode);
            price = $(R.id.price);
            deltaCount = $(R.id.deltaCount);

            addBtn = $(R.id.addBtn);
            productQuantity = $(R.id.productQuantity);
            minusBtn = $(R.id.minusBtn);
        }


        @Override
        public void setData(final StockCheckOrderDetail data) {
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
            stockCheckName.setText(data.getStockName());
            stockCheckCount.setText("数量：" + data.getQuantity() + data.getUnit());
            differCount.setText("差异：" + data.getDeltaCount() + data.getUnit());
            stockCheckAmount.setText("金额：" + (data.getQuantity() * data.getCostPrice()));
            differAmount.setText("差异：" + data.getDeltaCount() * data.getCostPrice());
            delete.setOnClickListener(this);
            //______________________________________________________________________________________
//            Uri uri = Uri.parse("");
//            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
//                    .setResizeOptions(new ResizeOptions(AutoUtils.getPercentHeightSize(160), AutoUtils.getPercentHeightSize(160)))
//                    .build();
//            //Logger.d("StockNoPictureProductAdapter ImageRequest resizeOptions" + resizeOptions.toString());
//            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
//                    .setOldController(productImg.getController())
//                    .setImageRequest(request)
//                    .build();
//            productImg.setController(controller);

            productName.setText(data.getStockName());
            productUnit.setText("单位：" + data.getUnit());
            productSpec.setText("规格：" + data.getSpec());
            productBarCode.setText("条码：" + data.getBarCode());
            price.setText("价格：" + data.getCostPrice());
            deltaCount.setText("数量差异：" + data.getDeltaCount());
            //_________________________________________________________________
            productQuantity.setText("" + data.getQuantity());
            productQuantity.setOnTextChangerListener(new DelayChangeEditText.onTextChangerListener() {
                @Override
                public void onTextChanger(String s) {
                    if (TextUtils.isEmpty(s.toString()))
                        return;
                    double newQuantity = 0;
                    try {
                        newQuantity = Double.parseDouble(s.toString());
                    } catch (NumberFormatException e) {

                    }
                    if (newQuantity < 0) {
                        newQuantity = 0;
                        toastor.showSingletonToast("数量不能少于０");
                    }
                    if (newQuantity == data.getQuantity()) {
                        return;
                    }
                    if (listener != null)
                        listener.quantityChangeAction(getAdapterPosition(), getItem(getAdapterPosition()), newQuantity);

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
                    if (listener != null)
                        listener.modifyAction(getAdapterPosition(), getItem(getAdapterPosition()));
                    break;
                case R.id.addBtn:
                    newQuantity = Double.parseDouble(productQuantity.getText().toString());
                    newQuantity++;
                    productQuantity.setText("" + newQuantity);
                    break;
                case R.id.minusBtn:
                    newQuantity = Double.parseDouble(productQuantity.getText().toString());
                    if (newQuantity == 0) {
                        toastor.showSingletonToast("数量不能少于０");
                        break;
                    }
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
