package com.j1j2.jposmvvm.data.model;

import com.google.gson.annotations.Expose;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by 兴昊 on 2015/12/17.
 */
public class ShopInfo extends RealmObject {

    /**
     * ShopName : 内部测试
     * ClerkAccount : downleaves
     * ClerkName : 刘小林
     * IsAdmin : true
     * ShopId : 1
     * ClerkId : 1
     */
    @PrimaryKey
    private int ShopId;

    @Required
    private String ShopName;

    @Required
    private String ClerkAccount;

    @Required
    private String ClerkName;

    private int ClerkId;

    private boolean IsAdmin;

    @Required
    @Expose
    private Date UpdateTime;


    public int getShopId() {
        return ShopId;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getClerkAccount() {
        return ClerkAccount;
    }

    public void setClerkAccount(String clerkAccount) {
        ClerkAccount = clerkAccount;
    }

    public String getClerkName() {
        return ClerkName;
    }

    public void setClerkName(String clerkName) {
        ClerkName = clerkName;
    }

    public int getClerkId() {
        return ClerkId;
    }

    public void setClerkId(int clerkId) {
        ClerkId = clerkId;
    }

    public boolean isAdmin() {
        return IsAdmin;
    }

    public void setAdmin(boolean admin) {
        IsAdmin = admin;
    }

    public Date getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Date updateTime) {
        UpdateTime = updateTime;
    }
}
