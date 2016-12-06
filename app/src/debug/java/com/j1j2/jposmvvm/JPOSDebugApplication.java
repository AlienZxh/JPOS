package com.j1j2.jposmvvm;

import android.text.TextUtils;

import com.facebook.stetho.Stetho;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

/**
 * Created by alienzxh on 16-8-6.
 */
public class JPOSDebugApplication extends JPOSApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = getProcessName();
        if (!TextUtils.isEmpty(processName) && processName.equals(
                this.getPackageName())) {//判断进程名，保证只有主进程运行
            initStetho();
        }

    }


    private void initStetho() {
//        Stetho.initializeWithDefaults(this);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

    }
}
