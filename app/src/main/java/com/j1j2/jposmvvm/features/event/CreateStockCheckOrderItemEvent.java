package com.j1j2.jposmvvm.features.event;

import com.j1j2.jposmvvm.data.model.ProductDetail;

/**
 * Created by alienzxh on 16-9-19.
 */
public class CreateStockCheckOrderItemEvent {
    private final ProductDetail productDetail;

    public CreateStockCheckOrderItemEvent(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }
}
