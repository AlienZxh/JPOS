package com.j1j2.jposmvvm.features.base;

import android.app.Activity;

import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.model.Update;

import java.lang.ref.WeakReference;

/**
 * Created by alienzxh on 16-6-13.
 */
public class JPOSUpdateCheckCB implements UpdateCheckCB {

    private WeakReference<Activity> activityWeakReference;
    private Navigate navigate;

    public JPOSUpdateCheckCB(Activity activity, Navigate navigate) {
        this.activityWeakReference = new WeakReference<>(activity);
        this.navigate = navigate;
    }

    @Override
    public void hasUpdate(Update update) {

    }

    @Override
    public void noUpdate() {
        if (activityWeakReference.get() != null)
            navigate.navigateToLoginActivity(activityWeakReference.get(), null, true);
    }

    @Override
    public void onCheckError(int code, String errorMsg) {
        if (activityWeakReference.get() != null)
            navigate.navigateToLoginActivity(activityWeakReference.get(), null, true);
    }

    @Override
    public void onUserCancel() {
        if (activityWeakReference.get() != null)
            navigate.navigateToLoginActivity(activityWeakReference.get(), null, true);
    }
}
