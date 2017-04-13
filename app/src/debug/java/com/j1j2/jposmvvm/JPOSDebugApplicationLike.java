package com.j1j2.jposmvvm;

import android.app.Application;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

/**
 * Created by alienzxh on 16-12-6.
 */

public class JPOSDebugApplicationLike extends JPOSApplicationLike {

    public JPOSDebugApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!TextUtils.isEmpty(processName) && processName.equals(
                getApplication().getPackageName())) {//判断进程名，保证只有主进程运行
            initStetho();
        }
    }


    private void initStetho() {
//        Stetho.initializeWithDefaults(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(getApplication())
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(getApplication()))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(getApplication()).build())
                        .build());

    }
}
