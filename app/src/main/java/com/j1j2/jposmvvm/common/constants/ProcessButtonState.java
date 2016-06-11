package com.j1j2.jposmvvm.common.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by alienzxh on 16-5-17.
 */
public class ProcessButtonState {
    public final static int STATE_ERROR = -1;
    public final static int STATE_NORMAL = 0;
    public final static int STATE_LOADING = 50;
    public final static int STATE_COMPLETE = 100;

    @IntDef({STATE_NORMAL, STATE_LOADING, STATE_COMPLETE, STATE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ProcessButtonStateDef {
    }
}
