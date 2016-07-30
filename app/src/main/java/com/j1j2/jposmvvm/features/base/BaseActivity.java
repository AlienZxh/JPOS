package com.j1j2.jposmvvm.features.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch;

import me.yokeyword.fragmentation.SupportActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by alienzxh on 16-3-4.
 */
public abstract class BaseActivity extends SupportActivity implements RxViewDispatch {


//    protected Fragment currentFragment;

    protected abstract void setupActivityComponent();

    protected abstract void initBinding();

    protected void initActionBar() {
    }

    protected abstract void initViews();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initActionBar();
        initViews();
    }

//    protected void changeFragment(int resView, Fragment targetFragment) {
//        if (targetFragment.equals(currentFragment)) {
//            return;
//        }
//        FragmentTransaction transaction = getSupportFragmentManager()
//                .beginTransaction();
//        if (!targetFragment.isAdded()) {
//            transaction.add(resView, targetFragment, targetFragment.getClass()
//                    .getName());
//        }
//        if (targetFragment.isHidden()) {
//            transaction.show(targetFragment);
//        }
//        if (currentFragment != null
//                && currentFragment.isVisible()) {
//            transaction.hide(currentFragment);
//        }
//        currentFragment = targetFragment;
//        transaction.commit();
//    }


    public void onBack(View v) {
        onBackPressed();
    }

}
