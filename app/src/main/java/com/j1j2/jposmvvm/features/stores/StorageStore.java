package com.j1j2.jposmvvm.features.stores;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.ShopInfo;
import com.j1j2.jposmvvm.data.model.StorageOrder;
import com.j1j2.jposmvvm.data.model.StorageStockDetail;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StorageActions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

/**
 * Created by alienzxh on 16-7-18.
 */
public class StorageStore extends RxStore implements StorageStoreInterface {

    public static final String ID = "StorageStore";

    private static volatile StorageStore instance;

    public static synchronized StorageStore get(Dispatcher dispatcher) {
        StorageStore insttemp = instance;  // <<< 在这里创建临时变量
        if (insttemp == null) {
            synchronized (StorageStore.class) {
                insttemp = instance;
                if (insttemp == null) {
                    insttemp = new StorageStore(dispatcher);
                    instance = insttemp;
                }
            }
        }
        return insttemp;  // <<< 注意这里返回的是临时变量
    }

    public StorageStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case StorageActions.QUERYSTORAGEORDERS:
                final WebReturn<PageManager<List<StorageOrder>>> storageOrderWebReturn =
                        (WebReturn<PageManager<List<StorageOrder>>>) action.get(Keys.STORAGEORDERS_WEBRETURN);
                if (storageOrderWebReturn.isValue()) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.<StorageOrder>copyToRealmOrUpdate(storageOrderWebReturn.getDetail().getList());
                        }
                    });
                    realm.close();
                }
                break;
            case StorageActions.AUDITSTORAGEORDER:

                break;
            case StorageActions.CREATEORUPDATESTOREAGEORDERDETAILITEM:

                break;
            case StorageActions.CREATEUPDATESTORAGEORDER:

                break;
            case StorageActions.QUERYSTOCKS:

                break;
            case StorageActions.REMOVESTOREAGEORDERDETAILITEM:

                break;
            case StorageActions.QUERYSTOREORDERDETAILS:

                break;
            case StorageActions.REFRESHSTORAGEORDERS:

                break;
            default: // IMPORTANT if we don't modify the store just ignore
                return;
        }
        postChange(new RxStoreChange(ID, action));
    }
}
