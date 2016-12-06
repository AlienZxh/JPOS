package com.j1j2.jposmvvm.features.event;

/**
 * Created by alienzxh on 16-8-9.
 */
public class BarCodeEvent {
    public final String barCode;

    public BarCodeEvent(String barCode) {
        this.barCode = barCode;
    }

    public String getBarCode() {
        return barCode;
    }
}
