package com.j1j2.jposmvvm.features.actions;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.action.RxActionCreator;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;
import com.j1j2.jposmvvm.data.api.ShopAPI;
import com.j1j2.jposmvvm.data.api.body.LoginBody;
import com.j1j2.jposmvvm.data.model.ShopInfo;
import com.j1j2.jposmvvm.data.model.WebReturn;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by alienzxh on 16-5-5.
 */
public class ShopActionCreator extends RxActionCreator implements ShopActions {

    private ShopAPI shopAPI;

    public ShopActionCreator(Dispatcher dispatcher, SubscriptionManager manager, ShopAPI shopAPI) {
        super(dispatcher, manager);
        this.shopAPI = shopAPI;
    }

    @Override
    public void login(final LoginBody loginBody) {
        final RxAction action = newRxAction(LOGIN, Keys.LOGINBODY, loginBody);
        if (hasRxAction(action)) return;
        addRxAction(action, shopAPI.login(loginBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WebReturn<ShopInfo>>() {
                    @Override
                    public void call(WebReturn<ShopInfo> shopInfoWebReturn) {
                        action.getData().put(Keys.SHOPINFO_WEBRETURN, shopInfoWebReturn);
                        postRxAction(action);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        postError(action, throwable);
                    }
                }));
    }
}
