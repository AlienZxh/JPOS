package com.j1j2.jposmvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 兴昊 on 2016/1/5.
 */
public class Product implements Parcelable {


    /**
     * StockId : 1
     * ShopId : 2
     * ProductId : 1
     * BarCode : sample string 3
     * Name : sample string 4
     * Spec : sample string 5
     * Unit : sample string 6
     * RetailPrice : 1.0
     * MemberPrice : 1.0
     * Quantity : 7.0
     * SmallImgUrl : sample string 8
     * ThumbImgUrl : sample string 9
     * LastCost : 1.0
     */

    private int StockId;
    private int ShopId;
    private int ProductId;
    private String BarCode;
    private String Name;
    private String Spec;
    private String Unit;
    private double RetailPrice;
    private double MemberPrice;
    private double Quantity;
    private String SmallImgUrl;
    private String ThumbImgUrl;
    private double LastCost;

    public int getStockId() {
        return StockId;
    }

    public void setStockId(int StockId) {
        this.StockId = StockId;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int ShopId) {
        this.ShopId = ShopId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int ProductId) {
        this.ProductId = ProductId;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String BarCode) {
        this.BarCode = BarCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSpec() {
        return Spec;
    }

    public void setSpec(String Spec) {
        this.Spec = Spec;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public double getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(double RetailPrice) {
        this.RetailPrice = RetailPrice;
    }

    public double getMemberPrice() {
        return MemberPrice;
    }

    public void setMemberPrice(double MemberPrice) {
        this.MemberPrice = MemberPrice;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double Quantity) {
        this.Quantity = Quantity;
    }

    public String getSmallImgUrl() {
        return SmallImgUrl;
    }

    public void setSmallImgUrl(String SmallImgUrl) {
        this.SmallImgUrl = SmallImgUrl;
    }

    public String getThumbImgUrl() {
        return ThumbImgUrl;
    }

    public void setThumbImgUrl(String ThumbImgUrl) {
        this.ThumbImgUrl = ThumbImgUrl;
    }

    public double getLastCost() {
        return LastCost;
    }

    public void setLastCost(double LastCost) {
        this.LastCost = LastCost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "StockId=" + StockId +
                ", ShopId=" + ShopId +
                ", ProductId=" + ProductId +
                ", BarCode='" + BarCode + '\'' +
                ", Name='" + Name + '\'' +
                ", Spec='" + Spec + '\'' +
                ", Unit='" + Unit + '\'' +
                ", RetailPrice=" + RetailPrice +
                ", MemberPrice=" + MemberPrice +
                ", Quantity=" + Quantity +
                ", SmallImgUrl='" + SmallImgUrl + '\'' +
                ", ThumbImgUrl='" + ThumbImgUrl + '\'' +
                ", LastCost=" + LastCost +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.StockId);
        dest.writeInt(this.ShopId);
        dest.writeInt(this.ProductId);
        dest.writeString(this.BarCode);
        dest.writeString(this.Name);
        dest.writeString(this.Spec);
        dest.writeString(this.Unit);
        dest.writeDouble(this.RetailPrice);
        dest.writeDouble(this.MemberPrice);
        dest.writeDouble(this.Quantity);
        dest.writeString(this.SmallImgUrl);
        dest.writeString(this.ThumbImgUrl);
        dest.writeDouble(this.LastCost);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.StockId = in.readInt();
        this.ShopId = in.readInt();
        this.ProductId = in.readInt();
        this.BarCode = in.readString();
        this.Name = in.readString();
        this.Spec = in.readString();
        this.Unit = in.readString();
        this.RetailPrice = in.readDouble();
        this.MemberPrice = in.readDouble();
        this.Quantity = in.readDouble();
        this.SmallImgUrl = in.readString();
        this.ThumbImgUrl = in.readString();
        this.LastCost = in.readDouble();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
