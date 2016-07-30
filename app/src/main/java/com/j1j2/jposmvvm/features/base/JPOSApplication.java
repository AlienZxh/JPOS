package com.j1j2.jposmvvm.features.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.stetho.Stetho;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hardsoftstudio.rxflux.RxFlux;
import com.j1j2.jposmvvm.BuildConfig;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.utils.AndroidUtil;
import com.j1j2.jposmvvm.common.utils.HttpHelper;
import com.j1j2.jposmvvm.common.utils.ScreenUtils;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.data.model.ShopInfo;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.features.base.di.components.AppComponent;
import com.j1j2.jposmvvm.features.base.di.components.DaggerAppComponent;
import com.j1j2.jposmvvm.features.base.di.components.ShopComponent;
import com.j1j2.jposmvvm.features.base.di.modules.AppModule;
import com.j1j2.jposmvvm.features.base.di.modules.ShopModule;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import org.json.JSONException;
import org.json.JSONObject;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.callback.EmptyCheckCB;
import org.lzh.framework.updatepluginlib.callback.EmptyDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.inject.Inject;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by alienzxh on 16-5-4.
 */
public class JPOSApplication extends MultiDexApplication {

    private RefWatcher refWatcher;
    private AppComponent appComponent;
    private ShopComponent shopComponent;
    private RxFlux rxFlux;

    //    @Inject
//    OkHttpClient okHttpClient;
    @Inject
    Toastor toastor;
    @Inject
    Gson gson;

    public static RefWatcher getRefWatcher(Context context) {
        return get(context).refWatcher;
    }

    public static JPOSApplication get(Context context) {
        return (JPOSApplication) context.getApplicationContext();
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
        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this);
        }
        String processName = getProcessName();
        if (!TextUtils.isEmpty(processName) && processName.equals(
                this.getPackageName())) {//判断进程名，保证只有主进程运行
            initDefendeleak();
            initLogger();
            initThreeTenABP();
            initRxFlux();
            initRealm();
            initComponent();
            initStetho();
            initFresco();
            initGallyFinal();
            initEasyRecyclerView();
            initAndroidDevMetrics();
            initLeakCanary();
            initUpdatePlugin();
        }
    }

    private void initDefendeleak() {
        try {
            Class cls = Class.forName("android.sec.clipboard.ClipboardUIManager");
            Method m = cls.getDeclaredMethod("getInstance", Context.class);
            m.setAccessible(true);
            m.invoke(null, this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void initLogger() {
        if (BuildConfig.DEBUG) {
            Logger.init(" pifalao debug ").logLevel(LogLevel.FULL);
        } else {
            Logger.init(" pifalao debug ").logLevel(LogLevel.NONE);
        }
        Logger.d("Logger初始化完成");
    }

    private void initThreeTenABP() {
        AndroidThreeTen.init(this);
        Logger.d("AndroidThreeTen初始化完成");
    }

    private void initAndroidDevMetrics() {
        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this);
        }
        Logger.d("AndroidDevMetrics初始化完成");
    }


    private void initComponent() {
        this.appComponent =
                DaggerAppComponent.builder().appModule(new AppModule(this, rxFlux)).build();
        appComponent.inject(this);
        Logger.d("AppComponent初始化完成");
    }

    private void initStetho() {
//        Stetho.initializeWithDefaults(this);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
        Logger.d("Stetho初始化完成");
    }

    private void initRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
    }

    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {
            refWatcher = LeakCanary.install(this);
        } else {
            refWatcher = RefWatcher.DISABLED;
        }
        Logger.d("LeakCanary初始化完成");
    }

    private void initRxFlux() {
        RxFlux.LOG_LEVEL =
                BuildConfig.DEBUG ? com.hardsoftstudio.rxflux.util.LogLevel.FULL
                        : com.hardsoftstudio.rxflux.util.LogLevel.NONE;
        rxFlux = RxFlux.init(this);
        Logger.d("RxFlux初始化完成");
    }

    private void initFresco() {
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, imagePipelineConfig);
    }

    private void initGallyFinal() {
        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(getResources().getColor(R.color.colorPrimary))
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setCropHeight(ScreenUtils.getScreenWidth(this))
                .setCropWidth(ScreenUtils.getScreenWidth(this))
                .setForceCrop(true)
                .setEnablePreview(true)
                .build();

        //配置imageloader
        ImageLoader imageloader = new FrescoImageLoader(this);
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, theme)
                .setFunctionConfig(functionConfig)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

    private void initEasyRecyclerView() {
        EasyRecyclerView.DEBUG = BuildConfig.DEBUG;
    }

    private void initUpdatePlugin() {
        // UpdateConfig为全局配置。当在其他页面中。使用UpdateBuilder进行检查更新时。
        // 对于没传的参数，会默认使用UpdateConfig中的全局配置
        UpdateConfig.getConfig()
                // 必填：数据更新接口
                .url(HttpHelper.buildUrl("client/v1/AppConfig/QueryAppCoinfig", new HashMap<String, String>() {
                    {
                        put("version", "" + AndroidUtil.getAppVersionCode(getApplicationContext()));
                        put("platForm", "1");
                    }
                }))
                // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
                .jsonParser(new UpdateParser() {
                    @Override
                    public Update parse(String response) {
//                        // 此处模拟一个Update对象
//                        Update update = new Update(response);
//                        // 此apk包的更新时间
//                        update.setUpdateTime(System.currentTimeMillis());
//                        // 此apk包的下载地址
//                        update.setUpdateUrl(apkFile);
//                        // 此apk包的版本号
//                        update.setVersionCode(2);
//                        // 此apk包的版本名称
//                        update.setVersionName("2.0");
//                        // 此apk包的更新内容
//                        update.setUpdateContent("测试更新");
//                        // 此apk包是否为强制更新
//                        update.setForced(false);
//                        // 是否忽略此次版本更新
//                        update.setIgnore(false);

//                        Logger.e("errorMsg " + response);
//                        WebReturn<Update> updateWebReturn = gson.fromJson(response, new TypeToken<WebReturn<Update>>() {
//                        }.getType());
//                        Logger.e("errorMsg " + updateWebReturn.getDetail().toString());
//                        return updateWebReturn.getDetail();
                        try {

                            JSONObject wenReturnJsonObject = new JSONObject(response);

                            Update update = new Update(wenReturnJsonObject.getString("Detail"));
                            JSONObject jsonObject = wenReturnJsonObject.getJSONObject("Detail");
                            // 此apk包的更新时间
                            update.setUpdateTime(System.currentTimeMillis());
                            // 此apk包的下载地址
                            update.setUpdateUrl(jsonObject.optString("ApkDownloadUrl"));
                            // 此apk包的版本号
                            update.setVersionCode(jsonObject.optInt("NewVersionTag"));
                            // 此apk包的版本名称
                            update.setVersionName(jsonObject.optString("NewVersionName"));
                            // 此apk包的更新内容
                            update.setUpdateContent(jsonObject.optString("UpdatedContents"));
                            // 此apk包是否为强制更新
                            update.setForced(jsonObject.optBoolean("ForceUpdate"));
                            // 是否忽略此次版本更新
                            update.setIgnore(jsonObject.optBoolean("Invalid"));

                            return update;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return null;
                        }


                    }
                })
                // TODO: 2016/5/11 除了以上两个参数为必填。以下的参数均为非必填项。
                .checkCB(new EmptyCheckCB() {

                    @Override
                    public void onCheckError(int code, String errorMsg) {
                        toastor.showSingletonToast("更新失败");
                    }

                    @Override
                    public void onUserCancel() {
                        toastor.showSingletonToast("取消更新");
                    }

                    @Override
                    public void noUpdate() {
                    }
                })
                // apk下载的回调
                .downloadCB(new EmptyDownloadCB() {
                    @Override
                    public void onUpdateError(int code, String errorMsg) {
                        toastor.showSingletonToast("下载失败");
                    }
                })
                .downloadWorker(new APKDownloadWorker())
                /* // 自定义更新接口的访问任务
                .checkWorker(new UpdateWorker() {
                    @Override
                    protected String check(String url) throws Exception {
                        // TODO: 2016/5/11 此处运行于子线程。在此进行更新接口访问
                        return null;
                    }
                })
                // 自定义apk下载任务
                .downloadWorker(new DownloadWorker() {
                    @Override
                    protected void download(String url, File file) throws Exception {
                        // TODO: 2016/5/11 此处运行于子线程，在此进行文件下载任务
                    }
                })
                // 自定义下载文件缓存,默认下载至系统自带的缓存目录下
                .fileCreator(new ApkFileCreator() {
                    @Override
                    public File create(String versionName) {
                        // TODO: 2016/5/11 versionName 为解析的Update实例中的update_url数据。在些可自定义下载文件缓存路径及文件名。放置于File中
                        return null;
                    }
                })
                // 自定义更新策略，默认WIFI下自动下载更新
                .strategy(new UpdateStrategy() {
                    @Override
                    public boolean isShowUpdateDialog(Update update) {
                        // 是否在检查到有新版本更新时展示Dialog。
                        return false;
                    }

                    @Override
                    public boolean isAutoInstall() {
                        // 是否自动更新。此属性与是否isShowInstallDialog互斥
                        return false;
                    }

                    @Override
                    public boolean isShowInstallDialog() {
                        // 下载完成后。是否显示提示安装的Dialog
                        return false;
                    }

                    @Override
                    public boolean isShowDownloadDialog() {
                        // 在APK下载时。是否显示下载进度的Dialog
                        return false;
                    }
                })
                        // 自定义检查出更新后显示的Dialog，
                .updateDialogCreator(new DialogCreator() {
                    @Override
                    public Dialog create(Update update, Activity activity, UpdateBuilder updateBuilder) {
                        // TODO: 2016/5/11 此处为检查出有新版本需要更新时的回调。运行于主线程，在此进行更新Dialog的创建
                        return null;
                    }
                })
                        // 自定义下载时的进度条Dialog
                .downloadDialogCreator(new DownloadCreator() {
                    @Override
                    public UpdateDownloadCB create(Update update, Activity activity) {
                        // TODO: 2016/5/11 此处为正在下载APK时的回调。运行于主线程。在此进行Dialog自定义与显示操作。
                        // TODO: 2016/5/11 需要在此创建并返回一个UpdateDownloadCB回调。用于对Dialog进行更新。
                        return null;
                    }
                })
                        // 自定义下载完成后。显示的Dialog
                .installDialogCreator(new InstallCreator() {
                    @Override
                    public Dialog create(Update update, String s, Activity activity) {
                        // TODO: 2016/5/11 此处为下载APK完成后的回调。运行于主线程。在此创建Dialog
                        return null;
                    }
                })*/;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public ShopComponent getShopComponent() {
        return shopComponent;
    }

    public void releaseUserComponent() {
        shopComponent = null;
    }

    public ShopComponent createUserComponent(ShopInfo shopInfo) {
        shopComponent = appComponent.plus(new ShopModule(shopInfo));
        return shopComponent;
    }

    public boolean isLogin() {
        return null != shopComponent;
    }

    public void login(ShopInfo shopInfo) {
        createUserComponent(shopInfo);
    }

    public void logOut() {
        releaseUserComponent();
    }
}
