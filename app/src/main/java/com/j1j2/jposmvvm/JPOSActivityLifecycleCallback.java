package com.j1j2.jposmvvm;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by alienzxh on 16-12-8.
 */

public class JPOSActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {

    private int activityCount = 0;
    private boolean isBackGround = true;

    public int getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }

    public boolean isBackGround() {
        return isBackGround;
    }

    public void setBackGround(boolean backGround) {
        isBackGround = backGround;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        activityCount++;
        if (activityCount > 0) {
            isBackGround = false;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityCount--;
        if (activityCount <= 0) {
            isBackGround = true;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
