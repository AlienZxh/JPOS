package com.j1j2.jposmvvm;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.hardsoftstudio.rxflux.RxFlux;
import com.j1j2.jposmvvm.common.constants.Constants;
import com.j1j2.jposmvvm.common.utils.ScreenUtils;
import com.j1j2.jposmvvm.common.widgets.viewstatemanager.LoadingAndRetryManager;
import com.j1j2.jposmvvm.data.model.ShopInfo;
import com.j1j2.jposmvvm.features.base.FrescoImageLoader;
import com.j1j2.jposmvvm.features.base.di.components.AppComponent;
import com.j1j2.jposmvvm.features.base.di.components.DaggerAppComponent;
import com.j1j2.jposmvvm.features.base.di.components.ShopComponent;
import com.j1j2.jposmvvm.features.base.di.modules.AppModule;
import com.j1j2.jposmvvm.features.base.di.modules.ShopModule;
import com.j1j2.jposmvvm.features.ui.LanuchActivity;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import io.realm.Realm;

/**
 * Created by alienzxh on 16-12-6.
 */

public class JPOSApplicationLike extends DefaultApplicationLike {

    public interface OnPatchResultListener {
        void OnPatchResult(PatchResult patchResult);
    }

    private static JPOSApplicationLike jposApplicationLike;

    private OnPatchResultListener onPatchResultListener;

    private JPOSActivityLifecycleCallback jposActivityLifecycleCallback;

    protected String processName;

    private RefWatcher refWatcher;
    private AppComponent appComponent;
    private ShopComponent shopComponent;
    private RxFlux rxFlux;


    public JPOSApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    public static JPOSApplicationLike get() {
        return jposApplicationLike;
    }

    public void registerOnPatchResultListener(OnPatchResultListener onPatchResultListener) {
        this.onPatchResultListener = onPatchResultListener;
    }

    public void releaseOnPatchResultListener() {
        this.onPatchResultListener = null;
    }

    public int getActivityCount() {
        return jposActivityLifecycleCallback.getActivityCount();
    }

    public boolean isBackGround() {
        return jposActivityLifecycleCallback.isBackGround();
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public ShopComponent getShopComponent() {
        return shopComponent;
    }

    public RxFlux getRxFlux() {
        return rxFlux;
    }


    public boolean isLogin() {
        return null != shopComponent;
    }

    public void login(ShopInfo shopInfo) {
        shopComponent = appComponent.plus(new ShopModule(shopInfo));
    }

    public void logout() {
        shopComponent = null;
    }


    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        jposApplicationLike = this;
        jposActivityLifecycleCallback = new JPOSActivityLifecycleCallback();
        registerActivityLifecycleCallback(jposActivityLifecycleCallback);

        processName = getProcessName();
        if (!TextUtils.isEmpty(processName) && processName.equals(
                getApplication().getPackageName())) {//判断进程名，保证只有主进程运行
            initLogger();
            initAndroidDevMetrics();
            initLoadingAndRetryManager();
            initThreeTenABP();
            initRxFlux();
            initRealm();
            initComponent();
            initFresco();
            initGallyFinal();
            initEasyRecyclerView();
            initLeakCanary();
            initBugly();
        }
    }


    private void initAndroidDevMetrics() {
        if (BuildConfig.DEBUG)
            AndroidDevMetrics.initWith(getApplication());
        Logger.d("AndroidDevMetrics初始化完成");
    }

    private void initLogger() {
        if (BuildConfig.DEBUG) {
            Logger.init(" jposmvvm debug ").logLevel(LogLevel.FULL);
        } else {
            Logger.init(" jposmvvm debug ").logLevel(LogLevel.NONE);
        }
        Logger.d("Logger初始化完成");
    }

    private void initThreeTenABP() {
        AndroidThreeTen.init(getApplication());
        Logger.d("AndroidThreeTen初始化完成");
    }


    private void initComponent() {
        this.appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule((JPOSApplication) getApplication(), this.rxFlux))
                .build();
        Logger.d("AppComponent初始化完成");
    }


    private void initRealm() {
        Realm.init(getApplication().getApplicationContext());
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .build();
//        Realm.setDefaultConfiguration(config);
    }

    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {
            refWatcher = LeakCanary.install(getApplication());
        } else {
            refWatcher = RefWatcher.DISABLED;
        }
        Logger.d("LeakCanary初始化完成");
    }

    private void initRxFlux() {
        RxFlux.LOG_LEVEL =
                BuildConfig.DEBUG ? com.hardsoftstudio.rxflux.util.LogLevel.FULL
                        : com.hardsoftstudio.rxflux.util.LogLevel.NONE;
        this.rxFlux = RxFlux.init(getApplication());
        Logger.d("RxFlux初始化完成");
    }

    private void initFresco() {
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(getApplication())
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(getApplication(), imagePipelineConfig);
    }

    private void initGallyFinal() {
        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(getApplication().getResources().getColor(R.color.colorPrimary))
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setCropHeight(ScreenUtils.getScreenWidth(getApplication()))
                .setCropWidth(ScreenUtils.getScreenWidth(getApplication()))
                .setForceCrop(true)
                .setEnablePreview(true)
                .build();

        //配置imageloader
        ImageLoader imageloader = new FrescoImageLoader(getApplication());
        CoreConfig coreConfig = new CoreConfig.Builder(getApplication(), imageloader, theme)
                .setFunctionConfig(functionConfig)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

    private void initEasyRecyclerView() {
        EasyRecyclerView.DEBUG = BuildConfig.DEBUG;
    }

    private void initLoadingAndRetryManager() {
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.view_error;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.view_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.view_empty;
    }

    private void initBugly() {
        Beta.autoInit = true;//自动初始化开关
        Beta.autoCheckUpgrade = true;//true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
        //只允许在***上显示更新弹窗，其他activity上不显示弹窗; 不设置会默认所有activity都可以显示弹窗;
        Beta.canShowUpgradeActs.add(LanuchActivity.class);

        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);//设置sd卡的Download为更新资源存储目录
        BuglyStrategy strategy = new BuglyStrategy();
        strategy.setAppChannel(BuildConfig.DEBUG ? "jpos-debug" : "jpos");

        Bugly.init(getApplication(), Constants.BUGLY_ID, BuildConfig.DEBUG, strategy);
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

//        // or you can just use DefaultLoadReporter
//        LoadReporter loadReporter = new DefaultLoadReporter(getApplication());
//        // or you can just use DefaultPatchReporter
//        PatchReporter patchReporter = new DefaultPatchReporter(getApplication());
//        // or you can just use DefaultPatchListener
//        PatchListener patchListener = new DefaultPatchListener(getApplication());
//        // you can set your own upgrade patch if you need
//        AbstractPatch upgradePatchProcessor = new UpgradePatch();
//        // you can set your own repair patch if you need
//        AbstractPatch repairPatchProcessor = new RepairPatch();
//        TinkerManager.TinkerPatchResultListener patchResultListener = new TinkerManager.TinkerPatchResultListener() {
//            @Override
//            public void onPatchResult(PatchResult result) {
//                // you can get the patch result
//                if (onPatchResultListener != null)
//                    onPatchResultListener.OnPatchResult(result);
//            }
//        };
//
//        Beta.installTinker(this, loadReporter, patchReporter,
//                patchListener, patchResultListener,
//                upgradePatchProcessor, repairPatchProcessor);
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }
}
