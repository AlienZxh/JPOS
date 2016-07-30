package com.j1j2.jposmvvm.features.viewmodel;

import com.j1j2.jposmvvm.data.model.ProductDetail;

import java.text.DecimalFormat;

/**
 * Created by alienzxh on 16-6-27.
 */
public class ProductViewModel {

    private ProductDetail productDetail;

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }


    public String getName() {
        return this.productDetail.getName();
    }

    public void setName(String name) {
        this.productDetail.setName(name);
    }


    public String getSpec() {
        return this.productDetail.getSpec();
    }

    public void setSpec(String spec) {
        this.productDetail.setSpec(spec);

    }


    public String getUnit() {
        return this.productDetail.getUnit();
    }

    public void setUnit(String unit) {
        this.productDetail.setUnit(unit);
    }


    public String getBarCode() {
        return this.productDetail.getBarCode();
    }

    public void setBarCode(String barCode) {
        this.productDetail.setBarCode(barCode);

    }


    public String getMnemonic() {
        return this.productDetail.getMnemonic();
    }

    public void setMnemonic(String mnemonic) {
        this.productDetail.setMnemonic(mnemonic);
    }


    public String getBrand() {
        return this.productDetail.getBrand();
    }

    public void setBrand(String brand) {
        this.productDetail.setBrand(brand);
    }


    public String getLastCost() {
        return String.valueOf(this.productDetail.getLastCost());
    }

    public void setLastCost(String lastCost) {
        this.productDetail.setLastCost(Double.parseDouble(lastCost));
    }


    public String getRetailPrice() {
        return String.valueOf(this.productDetail.getRetailPrice());
    }

    public void setRetailPrice(String retailPrice) {
        this.productDetail.setRetailPrice(Double.parseDouble(retailPrice));
    }


    public String getMemberPrice() {
        return String.valueOf(this.productDetail.getMemberPrice());
    }

    public void setMemberPrice(String memberPrice) {
        this.productDetail.setMemberPrice(Double.parseDouble(memberPrice));
    }


    public String getRetailPriceGrossMargin() {
        if (this.productDetail.getLastCost() <= 0) {
            return "";
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            return decimalFormat.format(((this.productDetail.getRetailPrice() - this.productDetail.getLastCost()) / this.productDetail.getRetailPrice()) * 100) + "%";
        }
    }


    public String getMemberPriceGrossMargin() {
        if (this.productDetail.getLastCost() <= 0) {
            return "";
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            return decimalFormat.format(((this.productDetail.getMemberPrice() - this.productDetail.getLastCost()) / this.productDetail.getMemberPrice()) * 100) + "%";
        }
    }
}
