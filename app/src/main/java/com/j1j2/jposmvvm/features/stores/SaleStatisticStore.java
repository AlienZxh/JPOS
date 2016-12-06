package com.j1j2.jposmvvm.features.stores;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.features.actions.SaleStatisticActions;

/**
 * Created by alienzxh on 16-8-15.
 */
public class SaleStatisticStore extends RxStore implements SaleStatisticStoreInterface {
    public static final String ID = "SaleStatisticStore";
    private static volatile SaleStatisticStore instance;

    public SaleStatisticStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static synchronized SaleStatisticStore get(Dispatcher dispatcher) {
        SaleStatisticStore insttemp = instance;  // <<< 在这里创建临时变量
        if (insttemp == null) {
            synchronized (SaleStatisticStore.class) {
                insttemp = instance;
                if (insttemp == null) {
                    insttemp = new SaleStatisticStore(dispatcher);
                    instance = insttemp;
                }
            }
        }
        return insttemp;  // <<< 注意这里返回的是临时变量
    }


    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case SaleStatisticActions.LOADSALEORDERS:
                break;
            case SaleStatisticActions.LOADSALESTATICBYMONTH:
                break;
            case SaleStatisticActions.LOADSALESTATICINMONTHBYCATEGORY:
                break;
            case SaleStatisticActions.QUERYSALEORDERDETAILS:
                break;
            default: // IMPORTANT if we don't modify the store just ignore
                return;
        }
        postChange(new RxStoreChange(ID, action));
    }
}
