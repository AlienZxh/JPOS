package com.j1j2.jposmvvm.features.stores;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.data.model.TopCategory;
import com.j1j2.jposmvvm.features.actions.StockActions;
import com.j1j2.jposmvvm.features.actions.StockCheckActions;

import java.util.List;

/**
 * Created by alienzxh on 16-9-19.
 */
public class StockCheckStore extends RxStore implements StockCheckStoreInterface {

    public static final String ID = "StockCheckStore";
    private static volatile StockCheckStore instance;


    public StockCheckStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static synchronized StockCheckStore get(Dispatcher dispatcher) {
        StockCheckStore insttemp = instance;  // <<< 在这里创建临时变量
        if (insttemp == null) {
            synchronized (StockCheckStore.class) {
                insttemp = instance;
                if (insttemp == null) {
                    insttemp = new StockCheckStore(dispatcher);
                    instance = insttemp;
                }
            }
        }
        return insttemp;  // <<< 注意这里返回的是临时变量
    }


    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case StockCheckActions.CREATEORUPDATESTOCKCHECKITEM:
                break;
            case StockCheckActions.CREATESTOCKCHECKORDER:
                break;
            case StockCheckActions.QUERYSTOCKCHECKORDERDETAILS:
                break;
            case StockCheckActions.QUERYSTOCKCHECKORDERS:
                break;
            case StockCheckActions.QUERYSTOCKS:
                break;
            default: // IMPORTANT if we don't modify the store just ignore
                return;
        }
        postChange(new RxStoreChange(ID, action));
    }
}
