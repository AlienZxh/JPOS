package com.j1j2.jposmvvm.features.adapter;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.BaseViewHolder;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.Product;
import com.j1j2.jposmvvm.features.ui.StockProductSearchFragment;
import com.zhy.autolayout.utils.AutoUtils;

import javax.annotation.Nullable;

/**
 * Created by alienzxh on 16-6-7.
 */
public class StockNoPictureProductAdapter extends RecyclerArrayAdapter<Product> {
    private int fromType;

    public StockNoPictureProductAdapter(Context context, int fromType) {
        super(context);
        this.fromType = fromType;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new StockNoPictureProductViewHolder(parent, fromType);
    }

    public class StockNoPictureProductViewHolder extends BaseViewHolder<Product> {
        private int fromType;

        private SimpleDraweeView productImg;
        private TextView productName;
        private TextView productUnit;
        private TextView productSpec;
        private TextView productBarCode;
        private TextView productImgTag;

        public StockNoPictureProductViewHolder(ViewGroup parent, int fromType) {
            super(parent, R.layout.item_nopicture_product);

            this.fromType = fromType;

            productImg = $(R.id.productImg);
            productName = $(R.id.productName);
            productUnit = $(R.id.productUnit);
            productSpec = $(R.id.productSpec);
            productBarCode = $(R.id.productBarCode);
            productImgTag = $(R.id.productImgTag);
        }

        @Override
        public void setData(Product data) {
            super.setData(data);
            Uri uri = Uri.parse(data.getThumbImgUrl() == null ? "" : data.getThumbImgUrl());
            ResizeOptions resizeOptions;
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(resizeOptions = new ResizeOptions(AutoUtils.getPercentHeightSize(160), AutoUtils.getPercentHeightSize(160)))
                    .build();
//            Logger.d("StockNoPictureProductAdapter ImageRequest resizeOptions" + resizeOptions.toString());
            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setControllerListener(new BaseControllerListener<ImageInfo>() {
                        @Override
                        public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                            super.onFinalImageSet(id, imageInfo, animatable);
                            if (fromType == StockProductSearchFragment.FROM_TAKEPICTURE)
                                productImgTag.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailure(String id, Throwable throwable) {
                            super.onFailure(id, throwable);
                            productImgTag.setVisibility(View.INVISIBLE);
                        }
                    })
                    .setOldController(productImg.getController())
                    .setImageRequest(request)
                    .build();
            productImg.setController(controller);
            //__________________________
            productName.setText(data.getName());
            productUnit.setText("单位：" + data.getUnit());
            productSpec.setText("规格：" + data.getSpec());
            productBarCode.setText("条码：" + data.getBarCode());
        }
    }
}
