package com.j1j2.jposmvvm.features.stores;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.features.actions.SupplierActions;

/**
 * Created by alienzxh on 16-7-26.
 */
public class SupplierStore extends RxStore implements SupplierStoreInterface {
    public static final String ID = "StorageStore";

    private static volatile SupplierStore instance;

    public static synchronized SupplierStore get(Dispatcher dispatcher) {
        SupplierStore insttemp = instance;  // <<< 在这里创建临时变量
        if (insttemp == null) {
            synchronized (SupplierStore.class) {
                insttemp = instance;
                if (insttemp == null) {
                    insttemp = new SupplierStore(dispatcher);
                    instance = insttemp;
                }
            }
        }
        return insttemp;  // <<< 注意这里返回的是临时变量
    }

    public SupplierStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case SupplierActions.QUERYSHOPSUPPLIERS:

                break;
            case SupplierActions.ADDSHOPSUPPLIER:

                break;
            default: // IMPORTANT if we don't modify the store just ignore
                return;
        }
        postChange(new RxStoreChange(ID, action));
    }
}
