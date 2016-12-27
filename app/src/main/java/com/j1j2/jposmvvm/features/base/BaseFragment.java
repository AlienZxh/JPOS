package com.j1j2.jposmvvm.features.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.jposmvvm.JPOSApplicationLike;
import com.j1j2.jposmvvm.features.event.BaseEvent;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by alienzxh on 16-5-5.
 */
public abstract class BaseFragment extends SupportFragment {

    protected abstract void setupActivityComponent();

    protected abstract View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initViews();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initBinding(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        RefWatcher refWatcher = JPOSApplicationLike.get().getRefWatcher();
        refWatcher.watch(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaseEvent(BaseEvent baseEvent) {

    }
}
