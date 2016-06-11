package com.j1j2.jposmvvm.features.stores;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.data.model.ShopInfo;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.ShopActions;
import com.j1j2.jposmvvm.features.base.JPOSApplication;

import java.util.Date;

import io.realm.Realm;

/**
 * Created by alienzxh on 16-5-5.
 */
public class ShopStore extends RxStore implements ShopStoreInterface {

    public static final String ID = "ShopStore";
    private static volatile ShopStore instance;
    private ShopInfo shopInfo;
    private JPOSApplication jposApplication;

    private ShopStore(Dispatcher dispatcher, JPOSApplication jposApplication) {
        super(dispatcher);
        this.jposApplication = jposApplication;
    }

    public static synchronized ShopStore get(Dispatcher dispatcher, JPOSApplication jposApplication) {
        ShopStore insttemp = instance;  // <<< 在这里创建临时变量
        if (insttemp == null) {
            synchronized (ShopStore.class) {
                insttemp = instance;
                if (insttemp == null) {
                    insttemp = new ShopStore(dispatcher, jposApplication);
                    instance = insttemp;
                }
            }
        }
        return insttemp;  // <<< 注意这里返回的是临时变量
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case ShopActions.LOGIN:
                WebReturn<ShopInfo> userInfoWebReturn = (WebReturn<ShopInfo>) action.get(Keys.SHOPINFO_WEBRETURN);
                if (userInfoWebReturn.isValue()) {
                    shopInfo = userInfoWebReturn.getDetail();
                    shopInfo.setUpdateTime(new Date(System.currentTimeMillis()));
                    jposApplication.login(shopInfo);
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealmOrUpdate(shopInfo);
                        }
                    });
                    realm.close();
                } else{
                    jposApplication.logOut();
                }

                break;
            default: // IMPORTANT if we don't modify the store just ignore
                return;
        }
        postChange(new RxStoreChange(ID, action));
    }

    @Override
    public ShopInfo getShopInfo(String id) {
        return shopInfo;
    }
}
