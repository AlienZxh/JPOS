package com.j1j2.jposmvvm.data.model;

import com.google.gson.annotations.Expose;

/**
 * Created by alienzxh on 16-5-24.
 */
public class StockImg {

    /**
     * ImgId : 1
     * ShopId : 2
     * StockId : 3
     * Name : sample string 4
     * OriginalPath : sample string 5
     * OptimizePath : sample string 6
     * NormalUrl : sample string 7
     * SmallUrl : sample string 8
     * ThumbUrl : sample string 9
     * Optimized : true
     * SubmitTime : 2016-05-24T09:46:10.1024523+08:00
     * OptimizeTime : 2016-05-24T09:46:10.1024523+08:00
     */

    private int ImgId;
    private int ShopId;
    private int StockId;
    private String Name;
    private String NormalUrl;
    private String SmallUrl;
    private String ThumbUrl;
    private String SubmitTime;

    @Expose
    private int StockImgId;
    @Expose
    private boolean IsUpload;
    @Expose
    private String UploadTime;
    @Expose
    private String LocalPath;


    public int getImgId() {
        return ImgId;
    }

    public void setImgId(int ImgId) {
        this.ImgId = ImgId;
    }

    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int ShopId) {
        this.ShopId = ShopId;
    }

    public int getStockId() {
        return StockId;
    }

    public void setStockId(int StockId) {
        this.StockId = StockId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }


    public String getNormalUrl() {
        return NormalUrl;
    }

    public void setNormalUrl(String NormalUrl) {
        this.NormalUrl = NormalUrl;
    }

    public String getSmallUrl() {
        return SmallUrl;
    }

    public void setSmallUrl(String SmallUrl) {
        this.SmallUrl = SmallUrl;
    }

    public String getThumbUrl() {
        return ThumbUrl;
    }

    public void setThumbUrl(String ThumbUrl) {
        this.ThumbUrl = ThumbUrl;
    }

    public String getSubmitTime() {
        return SubmitTime;
    }

    public void setSubmitTime(String SubmitTime) {
        this.SubmitTime = SubmitTime;
    }

    public int getStockImgId() {
        return StockImgId;
    }

    public void setStockImgId(int stockImgId) {
        StockImgId = stockImgId;
    }

    public boolean isUpload() {
        return IsUpload;
    }

    public void setUpload(boolean upload) {
        IsUpload = upload;
    }

    public String getUploadTime() {
        return UploadTime;
    }

    public void setUploadTime(String uploadTime) {
        UploadTime = uploadTime;
    }

    public String getLocalPath() {
        return LocalPath;
    }

    public void setLocalPath(String localPath) {
        LocalPath = localPath;
    }
}
