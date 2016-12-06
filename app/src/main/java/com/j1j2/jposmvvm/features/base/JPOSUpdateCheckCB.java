package com.j1j2.jposmvvm.features.base;

import android.app.Activity;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;


import com.j1j2.jposmvvm.R;

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
        if (activityWeakReference.get() != null) {
            View sharedView = activityWeakReference.get().findViewById(R.id.logo);
            navigate.navigateToLoginActivity(activityWeakReference.get(),
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activityWeakReference.get(), sharedView, activityWeakReference.get().getResources().getString(R.string.logo_share_name))
                    , true);
        }
    }

    @Override
    public void onCheckError(int code, String errorMsg) {
        if (activityWeakReference.get() != null) {
            View sharedView = activityWeakReference.get().findViewById(R.id.logo);
            navigate.navigateToLoginActivity(activityWeakReference.get(),
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activityWeakReference.get(), sharedView, activityWeakReference.get().getResources().getString(R.string.logo_share_name))
                    , true);
        }
    }

    @Override
    public void onUserCancel() {
        if (activityWeakReference.get() != null) {
            View sharedView = activityWeakReference.get().findViewById(R.id.logo);
            navigate.navigateToLoginActivity(activityWeakReference.get(),
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activityWeakReference.get(), sharedView, activityWeakReference.get().getResources().getString(R.string.logo_share_name))
                    , true);
        }
    }

    @Override
    public void onCheckIgnore(Update update) {

    }
}
