package com.j1j2.jposmvvm.features.stores;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.data.model.CashOrder;
import com.j1j2.jposmvvm.data.model.StorageOrder;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.features.actions.CashActions;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StorageActions;

import java.util.Date;

import io.realm.Realm;

/**
 * Created by alienzxh on 16-8-1.
 */
public class CashStore extends RxStore implements CashStoreInterface {
    public static final String ID = "CashStore";
    private static volatile CashStore instance;

    public CashStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static synchronized CashStore get(Dispatcher dispatcher) {
        CashStore insttemp = instance;  // <<< 在这里创建临时变量
        if (insttemp == null) {
            synchronized (CashStore.class) {
                insttemp = instance;
                if (insttemp == null) {
                    insttemp = new CashStore(dispatcher);
                    instance = insttemp;
                }
            }
        }
        return insttemp;  // <<< 注意这里返回的是临时变量
    }


    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case CashActions.PUZZYQUERYSCAN:
                break;
            case CashActions.QUERYSALEORDERNO:

                final WebReturn<String> stringWebReturn = (WebReturn<String>) action.get(Keys.QUERYSALEORDERNO_WEBRETURN);
                if (stringWebReturn.isValue()) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            CashOrder cashOrder = new CashOrder();
                            Date date = new Date();
                            cashOrder.setUpdateTime(date);
                            cashOrder.setCreateTime(date);
                            cashOrder.setComplete(false);
                            cashOrder.setOrderNO(stringWebReturn.getDetail());
                            realm.copyToRealmOrUpdate(cashOrder);
                        }
                    });
                    realm.close();
                }

                break;
            case CashActions.SETTLESALEORDER:
                break;
            case CashActions.REFRESHLISTITEM:

                break;
            case CashActions.SCANQUERY:

                break;
            default: // IMPORTANT if we don't modify the store just ignore
                return;
        }
        postChange(new RxStoreChange(ID, action));
    }
}
