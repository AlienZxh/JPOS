package com.j1j2.jposmvvm.features.actions;

import com.j1j2.jposmvvm.data.api.body.LoginBody;

/**
 * Created by alienzxh on 16-5-5.
 */
public interface ShopActions {
    String LOGIN = "login";


    void login(LoginBody loginBody);
}
