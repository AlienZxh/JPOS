package com.j1j2.jposmvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-7-26.
 */
public class Supplier implements Parcelable {

    /**
     * SupplierId : 1
     * Name : sample string 2
     * ShopId : 1
     * Contacter : sample string 3
     * Contact : sample string 4
     * Remark : sample string 5
     * SubmitClerkId : 6
     */

    private int SupplierId;
    private String Name;
    private int ShopId;
    private String Contacter;
    private String Contact;
    private String Remark;
    private int SubmitClerkId;

    public int getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(int SupplierId) {
        this.SupplierId = SupplierId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int ShopId) {
        this.ShopId = ShopId;
    }

    public String getContacter() {
        return Contacter;
    }

    public void setContacter(String Contacter) {
        this.Contacter = Contacter;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public int getSubmitClerkId() {
        return SubmitClerkId;
    }

    public void setSubmitClerkId(int SubmitClerkId) {
        this.SubmitClerkId = SubmitClerkId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.SupplierId);
        dest.writeString(this.Name);
        dest.writeInt(this.ShopId);
        dest.writeString(this.Contacter);
        dest.writeString(this.Contact);
        dest.writeString(this.Remark);
        dest.writeInt(this.SubmitClerkId);
    }

    public Supplier() {
    }

    protected Supplier(Parcel in) {
        this.SupplierId = in.readInt();
        this.Name = in.readString();
        this.ShopId = in.readInt();
        this.Contacter = in.readString();
        this.Contact = in.readString();
        this.Remark = in.readString();
        this.SubmitClerkId = in.readInt();
    }

    public static final Parcelable.Creator<Supplier> CREATOR = new Parcelable.Creator<Supplier>() {
        @Override
        public Supplier createFromParcel(Parcel source) {
            return new Supplier(source);
        }

        @Override
        public Supplier[] newArray(int size) {
            return new Supplier[size];
        }
    };
}
