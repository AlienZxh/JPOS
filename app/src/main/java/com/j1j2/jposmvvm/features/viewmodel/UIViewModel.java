package com.j1j2.jposmvvm.features.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.j1j2.jposmvvm.BR;
import com.j1j2.jposmvvm.common.constants.UIState;
import com.j1j2.jposmvvm.common.widgets.LoadingLayout;

/**
 * Created by alienzxh on 16-5-20.
 */
public class UIViewModel extends BaseObservable {

    private int uiState;
    private String loadingMessage = "加载中";
    private String errorMessage="出错了";
    private LoadingLayout.OnErrorActionClickListener onErrorActionClickListener;

    public UIViewModel(@UIState.UIStateDef int uiState) {
        this.uiState = uiState;
    }

    @Bindable
    public int getUiState() {
        return uiState;
    }

    public void setUiState(@UIState.UIStateDef int uiState) {
        this.uiState = uiState;
        notifyPropertyChanged(BR.uiState);
    }

    @Bindable
    public String getLoadingMessage() {
        return loadingMessage;
    }

    public void setLoadingMessage(String loadingMessage) {
        this.loadingMessage = loadingMessage;
        notifyPropertyChanged(BR.loadingMessage);
    }

    @Bindable
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        notifyPropertyChanged(BR.errorMessage);
    }

    @Bindable
    public LoadingLayout.OnErrorActionClickListener getOnErrorActionClickListener() {
        return onErrorActionClickListener;
    }

    public void setOnErrorActionClickListener(LoadingLayout.OnErrorActionClickListener onErrorActionClickListener) {
        this.onErrorActionClickListener = onErrorActionClickListener;
        notifyPropertyChanged(BR.onErrorActionClickListener);
    }
}
