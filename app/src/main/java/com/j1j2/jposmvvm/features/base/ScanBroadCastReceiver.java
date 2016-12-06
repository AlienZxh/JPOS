package com.j1j2.jposmvvm.features.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.j1j2.jposmvvm.features.event.BarCodeEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by 兴昊 on 2015/8/25.
 */
public class ScanBroadCastReceiver extends BroadcastReceiver {

    private String barCode;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals("com.barcode.sendBroadcast")) {
            barCode = intent.getStringExtra("BARCODE");
            EventBus.getDefault().post(new BarCodeEvent(barCode));
        }

    }


}
