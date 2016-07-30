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
import com.j1j2.jposmvvm.data.model.StorageStock;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by alienzxh on 16-7-27.
 */
public class StorageSearchAdapter extends RecyclerArrayAdapter<StorageStock> {

    private List<Integer> stockIds;

    public interface StorageSearchAdapterListener {
        void onSelectStockAction(int position, StorageStock storageStock);
    }

    private StorageSearchAdapterListener storageSearchAdapterListener;


    public StorageSearchAdapter(Context context, List<Integer> stockIds) {
        super(context);
        this.stockIds = stockIds;
    }

    public StorageSearchAdapterListener getStorageSearchAdapterListener() {
        return storageSearchAdapterListener;
    }

    public void setStorageSearchAdapterListener(StorageSearchAdapterListener storageSearchAdapterListener) {
        this.storageSearchAdapterListener = storageSearchAdapterListener;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new StorageSearchViewHolder(parent);
    }

    public class StorageSearchViewHolder extends BaseViewHolder<StorageStock> {


        private SimpleDraweeView productImg;
        private TextView productName;
        private TextView productUnit;
        private TextView productSpec;
        private TextView productBarCode;
        private TextView select;

        public StorageSearchViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_storage_search);


            productImg = $(R.id.productImg);
            productName = $(R.id.productName);
            productUnit = $(R.id.productUnit);
            productSpec = $(R.id.productSpec);
            productBarCode = $(R.id.productBarCode);
            select = $(R.id.select);
        }

        @Override
        public void setData(final StorageStock data) {
            super.setData(data);
            Uri uri = Uri.parse(data.getImg() == null ? "" : data.getImg());
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
            if (stockIds != null && stockIds.contains(data.getStockId())) {
                select.setText("已选择");
                select.setTextColor(0xff999999);
            } else {
                select.setText("选择");
                select.setTextColor(0xff194ff1);
                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (storageSearchAdapterListener != null)
                            storageSearchAdapterListener.onSelectStockAction(getAdapterPosition(), data);
                    }
                });
            }
        }
    }
}
