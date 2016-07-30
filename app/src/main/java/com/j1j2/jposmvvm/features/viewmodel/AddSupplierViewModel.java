package com.j1j2.jposmvvm.features.viewmodel;

import com.j1j2.jposmvvm.data.api.body.AddShopSupplierBody;

/**
 * Created by alienzxh on 16-7-26.
 */
public class AddSupplierViewModel {
    private AddShopSupplierBody mAddShopSupplierBody;


    public AddShopSupplierBody getAddShopSupplierBody() {
        return this.mAddShopSupplierBody;
    }

    public void setAddShopSupplierBody(AddShopSupplierBody mAddShopSupplierBody) {
        this.mAddShopSupplierBody = mAddShopSupplierBody;
    }

    public String getName() {
        return this.mAddShopSupplierBody.getName();
    }

    public void setName(String name) {
        this.mAddShopSupplierBody.setName(name);
    }

    public String getContacter() {
        return this.mAddShopSupplierBody.getContacter();
    }

    public void setContacter(String contacter) {
        this.mAddShopSupplierBody.setContacter(contacter);
    }

    public String getContact() {
        return this.mAddShopSupplierBody.getContact();
    }

    public void setContact(String contact) {
        this.mAddShopSupplierBody.setContact(contact);
    }

    public String getRemark() {
        return this.mAddShopSupplierBody.getRemark();
    }

    public void setRemark(String remark) {
        this.mAddShopSupplierBody.setRemark(remark);
    }
}
