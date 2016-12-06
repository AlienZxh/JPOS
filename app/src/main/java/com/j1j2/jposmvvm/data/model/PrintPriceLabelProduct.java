package com.j1j2.jposmvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-6-28.
 */
public class PrintPriceLabelProduct implements Parcelable {
    private int DetailId;
    private int OrderId;
    private int StockId;
    private String ThumbImg;
    private String Name;
    private String BarCode;
    private Double RetialPrice;
    private Double MemberPrice;
    private Double PrimeCost;
    private String Unit;
    private String Spec;


    public int getDetailId() {
        return DetailId;
    }

    public void setDetailId(int detailId) {
        DetailId = detailId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getStockId() {
        return StockId;
    }

    public void setStockId(int stockId) {
        StockId = stockId;
    }

    public String getThumbImg() {
        return ThumbImg;
    }

    public void setThumbImg(String thumbImg) {
        ThumbImg = thumbImg;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public Double getRetialPrice() {
        return RetialPrice;
    }

    public void setRetialPrice(Double retialPrice) {
        RetialPrice = retialPrice;
    }

    public Double getMemberPrice() {
        return MemberPrice;
    }

    public void setMemberPrice(Double memberPrice) {
        MemberPrice = memberPrice;
    }

    public Double getPrimeCost() {
        return PrimeCost;
    }

    public void setPrimeCost(Double primeCost) {
        PrimeCost = primeCost;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getSpec() {
        return Spec;
    }

    public void setSpec(String spec) {
        Spec = spec;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.DetailId);
        dest.writeInt(this.OrderId);
        dest.writeInt(this.StockId);
        dest.writeString(this.ThumbImg);
        dest.writeString(this.Name);
        dest.writeString(this.BarCode);
        dest.writeValue(this.RetialPrice);
        dest.writeValue(this.MemberPrice);
        dest.writeValue(this.PrimeCost);
        dest.writeString(this.Unit);
        dest.writeString(this.Spec);
    }

    public PrintPriceLabelProduct() {
    }

    protected PrintPriceLabelProduct(Parcel in) {
        this.DetailId = in.readInt();
        this.OrderId = in.readInt();
        this.StockId = in.readInt();
        this.ThumbImg = in.readString();
        this.Name = in.readString();
        this.BarCode = in.readString();
        this.RetialPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.MemberPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.PrimeCost = (Double) in.readValue(Double.class.getClassLoader());
        this.Unit = in.readString();
        this.Spec = in.readString();
    }

    public static final Creator<PrintPriceLabelProduct> CREATOR = new Creator<PrintPriceLabelProduct>() {
        @Override
        public PrintPriceLabelProduct createFromParcel(Parcel source) {
            return new PrintPriceLabelProduct(source);
        }

        @Override
        public PrintPriceLabelProduct[] newArray(int size) {
            return new PrintPriceLabelProduct[size];
        }
    };
}
