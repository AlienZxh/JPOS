package com.j1j2.jposmvvm.common.custombinding;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.dd.processbutton.iml.ActionProcessButton;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.j1j2.jposmvvm.common.constants.ProcessButtonState;
import com.j1j2.jposmvvm.common.constants.UIState;
import com.j1j2.jposmvvm.common.widgets.LoadingLayout;
import com.j1j2.jposmvvm.features.viewmodel.UIViewModel;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by alienzxh on 16-6-6.
 */
public class CustomBinding {
    @BindingAdapter("bind:viewState")
    public static void setViewState(View view, int viewState) {

        switch (viewState) {
            case UIState.STATE_LOADING:
                view.setEnabled(false);
                break;
            case UIState.STATE_NORMAL:
            case UIState.STATE_CONTENT:
            case UIState.STATE_ERROR:
                view.setEnabled(true);
                break;
        }
    }

    @BindingAdapter({"bind:viewState", "bind:uiViewModel"})
    public static void setActionProcessButtonState(ActionProcessButton view, int viewState, UIViewModel uiViewModel) {
        if (uiViewModel == null)
            return;
        switch (viewState) {
            case UIState.STATE_NORMAL:
                view.setProgress(ProcessButtonState.STATE_NORMAL);
                break;
            case UIState.STATE_LOADING:
                view.setLoadingText(uiViewModel.getLoadingMessage());
                view.setProgress(ProcessButtonState.STATE_LOADING);
                break;
            case UIState.STATE_CONTENT:
                view.setProgress(ProcessButtonState.STATE_COMPLETE);
                break;
            case UIState.STATE_ERROR:
                view.setLoadingText(uiViewModel.getErrorMessage());
                view.setProgress(ProcessButtonState.STATE_ERROR);
                break;
        }
    }

    @BindingAdapter({"bind:viewState", "bind:uiViewModel"})
    public static void setViewState(LoadingLayout view, int viewState, UIViewModel uiViewModel) {
        if (uiViewModel == null)
            return;
        view.setUIViewModel(uiViewModel);
    }

    @BindingAdapter("bind:viewState")
    public static void setViewState(LoadingLayout view, int viewState) {
        switch (viewState) {
            case UIState.STATE_ERROR:
                view.showError();
                break;
            case UIState.STATE_LOADING:
                view.showLoading();
                break;
            case UIState.STATE_CONTENT:
            case UIState.STATE_NORMAL:
            default:
                view.showContent();
                break;
        }
    }

    @BindingAdapter("bind:errorMessage")
    public static void setErrorMessage(LoadingLayout view, String errorMessage) {
        if (!TextUtils.isEmpty(errorMessage)) {
            view.setErrorText(errorMessage);
        }
    }

    @BindingAdapter("bind:loadingMessage")
    public static void setLoadingMessage(LoadingLayout view, String loadingMessage) {
        if (!TextUtils.isEmpty(loadingMessage)) {
            view.setLoadingText(loadingMessage);
        }
    }


    @BindingAdapter({"bind:imgUrl", "bind:imgWidth", "bind:imgHeight"})
    public static void setImgUrl(SimpleDraweeView view, String imgUrl, int width, int height) {
        Uri uri = Uri.parse(imgUrl == null ? "" : imgUrl);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(AutoUtils.getPercentWidthSize(width), AutoUtils.getPercentWidthSize(height)))
                .build();
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(view.getController())
                .setImageRequest(request)
                .build();
        view.setController(controller);
    }
}

