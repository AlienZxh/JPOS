package com.j1j2.jposmvvm.features.stores;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.data.model.TopCategory;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StockActions;

import java.util.List;

/**
 * Created by alienzxh on 16-6-7.
 */
public class StockStore extends RxStore implements StockStoreInterface {

    public static final String ID = "StockStore";
    private static volatile StockStore instance;
    private List<TopCategory> topCategorys;

    public StockStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static synchronized StockStore get(Dispatcher dispatcher) {
        StockStore insttemp = instance;  // <<< 在这里创建临时变量
        if (insttemp == null) {
            synchronized (ShopStore.class) {
                insttemp = instance;
                if (insttemp == null) {
                    insttemp = new StockStore(dispatcher);
                    instance = insttemp;
                }
            }
        }
        return insttemp;  // <<< 注意这里返回的是临时变量
    }


    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case StockActions.QUERYSTOCKCATEGORY:
                break;
            case StockActions.QUERYSTOCKSIFNOTFOUNDTHENCREATE:
                break;
            case StockActions.SEARCHSTOCKS:
                break;
            case StockActions.QUERYSTOCKDETAIL:
                break;
            case StockActions.CREATEORUPDATESTOCK:
                break;
            case StockActions.UPLOADIMG:
                break;
            case StockActions.REMOVESTOCKIMG:
                break;
            case StockActions.REFRESHLIST:
                break;
            default: // IMPORTANT if we don't modify the store just ignore
                return;
        }
        postChange(new RxStoreChange(ID, action));
    }

}
