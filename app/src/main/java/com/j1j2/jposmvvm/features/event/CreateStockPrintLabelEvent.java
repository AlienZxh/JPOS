package com.j1j2.jposmvvm.features.event;

import com.j1j2.jposmvvm.data.model.ProductDetail;

/**
 * Created by alienzxh on 16-10-23.
 */

public class CreateStockPrintLabelEvent {
    private final ProductDetail productDetail;

    public CreateStockPrintLabelEvent(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }
}
