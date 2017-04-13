package com.j1j2.jposmvvm.features.ui;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.JPOSApplicationLike;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.utils.TinyDB;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.data.model.ShopInfo;
import com.j1j2.jposmvvm.databinding.ActivityLanuchBinding;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.base.di.modules.ActivityModule;
import com.orhanobut.logger.Logger;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LanuchActivity extends BaseActivity {
    ActivityLanuchBinding binding;

    @Inject
    Navigate navigate;


    @Inject
    Toastor toastor;
    @Inject
    TinyDB tinyDB;

    @Override
    protected void setupActivityComponent() {
        JPOSApplicationLike.get().getAppComponent().plus(new ActivityModule(this)).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lanuch);
    }

    @Override
    protected void initViews() {
        Observable.fromCallable(new Callable<ShopInfo>() {
            @Override
            public ShopInfo call() throws Exception {
                ShopInfo shopInfo = null;

                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(Uri.parse("content://com.j1j2.jposlauncher.dao.tokenContentProvider/token"), // Uri
                        null, // projection
                        null, // selection
                        null, // selectionArgs
                        null  // sortOrder
                );
                String tokenStr = null;
                int shopId = -1;
                boolean isExpired = true;
                Logger.e("LanuchActivity cursor isnull" + (cursor == null) + "  count" + cursor.getCount());
                if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) { //不为空的Cursor
                    tokenStr = cursor.getString(1);
                    isExpired = (cursor.getInt(2) == 1);
                    shopId = cursor.getInt(4);
                }
                if (cursor != null) {
                    cursor.close();
                }
                Logger.e("LanuchActivity " + shopId + "  " + isExpired);
                if (tokenStr != null && shopId > 0 && !isExpired) {
                    tinyDB.putString("loginCookies", tokenStr);
                    cursor = contentResolver.query(Uri.parse("content://com.j1j2.jposlauncher.dao.shopContentProvider/shop"), // Uri
                            null, // projection
                            null, // selection
                            null, // selectionArgs
                            null  // sortOrder
                    );
                    if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) { //不为空的Cursor
                        do {
                            if (cursor.getInt(0) == shopId) {
                                shopInfo = new ShopInfo();
                                shopInfo.setShopId(cursor.getInt(0));
                                shopInfo.setShopName(cursor.getString(1));
                                shopInfo.setClerkId(cursor.getInt(2));
                                shopInfo.setClerkName(cursor.getString(3));
                                shopInfo.setClerkAccount(cursor.getString(4));
                                shopInfo.setAdmin(cursor.getInt(5) == 1);
                                shopInfo.setUpdateTime(new Date(System.currentTimeMillis()));
                                Logger.e("LanuchActivity shopInfo" + shopInfo.toString());
                                break;
                            }
                        } while (cursor.moveToNext());
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                }
                return shopInfo;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ShopInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("LanuchActivity " + e.toString());
                        navigate.navigateToLoginActivity(LanuchActivity.this, null, true);
                    }

                    @Override
                    public void onNext(final ShopInfo shopInfo) {
                        Logger.e("LanuchActivity shopInfo" + shopInfo == null ? "空" : shopInfo.toString());
                        if (shopInfo != null) {
                            JPOSApplicationLike.get().login(shopInfo);
                            Realm realm = Realm.getDefaultInstance();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.copyToRealmOrUpdate(shopInfo);
                                }
                            });
                            realm.close();

                            navigate.navigateToIndexActivity(LanuchActivity.this, null, true, shopInfo.getShopId());
                        } else {
                            navigate.navigateToLoginActivity(LanuchActivity.this, null, true);
                        }
                    }
                });
    }

    @SuppressLint("InlinedApi")
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

    }

    @Override
    public void onRxError(@NonNull RxError error) {

    }

    @Override
    public void onRxViewRegistered() {

    }

    @Override
    public void onRxViewUnRegistered() {

    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return null;
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return null;
    }
}
