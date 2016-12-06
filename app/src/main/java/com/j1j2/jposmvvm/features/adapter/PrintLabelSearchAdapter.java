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
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.BaseViewHolder;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.PrintPriceLabelProduct;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by alienzxh on 16-10-23.
 */

public class PrintLabelSearchAdapter extends RecyclerArrayAdapter<PrintPriceLabelProduct> {


    public PrintLabelSearchAdapterListener getPrintLabelSearchAdapterListener() {
        return printLabelSearchAdapterListener;
    }

    public void setPrintLabelSearchAdapterListener(PrintLabelSearchAdapterListener printLabelSearchAdapterListener) {
        this.printLabelSearchAdapterListener = printLabelSearchAdapterListener;
    }

    public interface PrintLabelSearchAdapterListener {
        void onSelectStockAction(PrintPriceLabelProduct printPriceLabelProduct);

        void onItemClickAction(int position, PrintPriceLabelProduct printPriceLabelProduct);
    }

    private PrintLabelSearchAdapterListener printLabelSearchAdapterListener;


    public PrintLabelSearchAdapter(Context context) {
        super(context);

    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PrintLabelSearchViewHolder(parent);
    }

    public class PrintLabelSearchViewHolder extends BaseViewHolder<PrintPriceLabelProduct> {

        private AutoLinearLayout layout;

        private SimpleDraweeView productImg;
        private TextView productName;
        private TextView productUnit;
        private TextView productSpec;
        private TextView productBarCode;
        private TextView select;

        public PrintLabelSearchViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_storage_search);

            layout = $(R.id.layout);
            productImg = $(R.id.productImg);
            productName = $(R.id.productName);
            productUnit = $(R.id.productUnit);
            productSpec = $(R.id.productSpec);
            productBarCode = $(R.id.productBarCode);
            select = $(R.id.select);
        }

        @Override
        public void setData(final PrintPriceLabelProduct data) {
            super.setData(data);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (printLabelSearchAdapterListener != null)
                        printLabelSearchAdapterListener.onItemClickAction(getAdapterPosition(), data);
                }
            });
            //_________________________________________
            Uri uri = Uri.parse(data.getThumbImg() == null ? "" : data.getThumbImg());
            ResizeOptions resizeOptions;
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(resizeOptions = new ResizeOptions(AutoUtils.getPercentHeightSize(160), AutoUtils.getPercentHeightSize(160)))
                    .build();
//            Logger.d("StockNoPictureProductAdapter ImageRequest resizeOptions" + resizeOptions.toString());
            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(productImg.getController())
                    .setImageRequest(request)
                    .build();
            productImg.setController(controller);
            //__________________________
            productName.setText(data.getName());
            productUnit.setText("单位：" + data.getUnit());
            productSpec.setText("规格：" + data.getSpec());
            productBarCode.setText("条码：" + data.getBarCode());
            select.setText("选择");
            select.setTextColor(0xff194ff1);
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (printLabelSearchAdapterListener != null)
                        printLabelSearchAdapterListener.onSelectStockAction(data);
                }
            });
        }
    }
}