package com.j1j2.jposmvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alienzxh on 16-10-22.
 */

public class IndexModule implements Parcelable {

    private String parentTitle;
    private String title;
    private String iconStr;
    private int colorInt;

    public IndexModule(String parentTitle, String title, String iconStr, int colorInt) {
        this.parentTitle = parentTitle;
        this.title = title;
        this.iconStr = iconStr;
        this.colorInt = colorInt;
    }

    public String getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconStr() {
        return iconStr;
    }

    public void setIconStr(String iconStr) {
        this.iconStr = iconStr;
    }

    public int getColorInt() {
        return colorInt;
    }

    public void setColorInt(int colorInt) {
        this.colorInt = colorInt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.parentTitle);
        dest.writeString(this.title);
        dest.writeString(this.iconStr);
        dest.writeInt(this.colorInt);
    }

    protected IndexModule(Parcel in) {
        this.parentTitle = in.readString();
        this.title = in.readString();
        this.iconStr = in.readString();
        this.colorInt = in.readInt();
    }

    public static final Creator<IndexModule> CREATOR = new Creator<IndexModule>() {
        @Override
        public IndexModule createFromParcel(Parcel source) {
            return new IndexModule(source);
        }

        @Override
        public IndexModule[] newArray(int size) {
            return new IndexModule[size];
        }
    };
}
