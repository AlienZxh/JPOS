package com.j1j2.jposmvvm.features.event;

import com.j1j2.jposmvvm.data.model.ProductDetail;

/**
 * Created by alienzxh on 16-10-24.
 */

public class RefreshStockPrintLabelEvent {
    private final int position;
    private final ProductDetail productDetail;

    public RefreshStockPrintLabelEvent(int position, ProductDetail productDetail) {
        this.position = position;
        this.productDetail = productDetail;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public int getPosition() {
        return position;
    }
}
