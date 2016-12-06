package com.j1j2.jposmvvm.features.stores;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.features.actions.PrintProductPriceLabelActions;

/**
 * Created by alienzxh on 16-10-22.
 */

public class PrintProductPriceLabelStore extends RxStore implements PrintProductPriceLabelStoreInterface {

    public static final String ID = "SaleStatisticStore";
    private static volatile PrintProductPriceLabelStore instance;

    public PrintProductPriceLabelStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static synchronized PrintProductPriceLabelStore get(Dispatcher dispatcher) {
        PrintProductPriceLabelStore insttemp = instance;  // <<< 在这里创建临时变量
        if (insttemp == null) {
            synchronized (PrintProductPriceLabelStore.class) {
                insttemp = instance;
                if (insttemp == null) {
                    insttemp = new PrintProductPriceLabelStore(dispatcher);
                    instance = insttemp;
                }
            }
        }
        return insttemp;  // <<< 注意这里返回的是临时变量
    }


    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case PrintProductPriceLabelActions.CREATEPRINTPRICELABELPRODUCT:
                break;
            case PrintProductPriceLabelActions.CREATEPRODUCTPRICELABELORDER:
                break;
            case PrintProductPriceLabelActions.QUERYPRINTPRICELABELORDERDETAILS:
                break;
            case PrintProductPriceLabelActions.QUERYPRODUCTINPRINTPRICELABEL:
                break;
            case PrintProductPriceLabelActions.QUERYPRODUCTPRICELABELORDERS:
                break;
            case PrintProductPriceLabelActions.REMOVEPRINTPRICELABELPRDOUCT:
                break;
            case PrintProductPriceLabelActions.SCANSTOCK:
                break;
            default: // IMPORTANT if we don't modify the store just ignore
                return;
        }
        postChange(new RxStoreChange(ID, action));
    }

}
