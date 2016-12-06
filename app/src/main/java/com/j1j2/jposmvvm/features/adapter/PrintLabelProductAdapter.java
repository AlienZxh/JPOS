package com.j1j2.jposmvvm.features.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
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
import com.j1j2.jposmvvm.data.model.PrintPriceLabelProduct;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by alienzxh on 16-10-23.
 */

public class PrintLabelProductAdapter extends RecyclerArrayAdapter<PrintPriceLabelProduct> {

    public interface PrintLabelProductAdapterListener {
        void imgClickAction(int position, PrintPriceLabelProduct data);

        void deleteAction(int position, PrintPriceLabelProduct data);
    }

    private int expandPosition = 0;

    private PrintLabelProductAdapterListener listener;

    public PrintLabelProductAdapter(Context context) {
        super(context);
    }

    public int getExpandPosition() {
        return expandPosition;
    }

    public void setExpandPosition(int expandPosition) {
        this.expandPosition = expandPosition;
    }

    public void setListener(PrintLabelProductAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PrintLabelProductViewHolder(parent);
    }


    public class PrintLabelProductViewHolder extends BaseViewHolder<PrintPriceLabelProduct> implements View.OnClickListener, View.OnFocusChangeListener {
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
        private TextView retialPrice;
        private TextView memberPrice;


        public PrintLabelProductViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_printlabel_product);

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
            retialPrice = $(R.id.retialPrice);
            memberPrice = $(R.id.memberPrice);
        }

        @Override
        public void setData(final PrintPriceLabelProduct data) {
            super.setData(data);
            expandableLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableLayout.isFocused()) {
                        expandableLayout.clearFocus();

                    } else {
                        expandableLayout.requestFocus();
                    }
                }
            });
            expandableLayout.setOnFocusChangeListener(this);


            num.setText("" + (getCount() - getAdapterPosition()));
            storageStockName.setText(data.getName());
            storageStockCount.setText("单位：" + data.getUnit());
            storageStockPrice.setText("成本价：" + data.getPrimeCost());
            delete.setOnClickListener(this);
            //______________________________________________________________________________________
            Uri uri = Uri.parse(data.getThumbImg() == null ? "" : data.getThumbImg());
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

            productImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.imgClickAction(getAdapterPosition(), data);
                }
            });
            productName.setText(data.getName());
            productUnit.setText("单位：" + data.getUnit());
            productSpec.setText("规格：" + data.getSpec());
            productBarCode.setText("条码：" + data.getBarCode());
            retialPrice.setText("零售价：" + data.getRetialPrice());
            memberPrice.setText("会员价：" + data.getMemberPrice());
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
                    if (expandableLayout.isFocused()) {
                        expandableLayout.clearFocus();
                    } else {
                        expandableLayout.requestFocus();
                    }
                    break;

                case R.id.delete:
                    if (listener != null)
                        listener.deleteAction(getAdapterPosition(), getItem(getAdapterPosition()));
                    break;
            }

        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (expandableLayout.isFocused()) {
                expandableTitle.setVisibility(View.GONE);
                expandableLayout.expand(false);
            } else {
                expandableTitle.setVisibility(View.VISIBLE);
                expandableLayout.collapse(false);
            }


        }


    }
}
