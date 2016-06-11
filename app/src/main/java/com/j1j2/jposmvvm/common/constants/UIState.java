package com.j1j2.jposmvvm.common.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by alienzxh on 16-5-17.
 */
public class UIState {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_CONTENT = 1;
    public final static int STATE_LOADING = 2;
    public final static int STATE_ERROR = 3;

    @IntDef({STATE_NORMAL, STATE_CONTENT, STATE_LOADING, STATE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UIStateDef {
    }
}
