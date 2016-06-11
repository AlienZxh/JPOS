package com.j1j2.jposmvvm.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.constants.UIState;
import com.j1j2.jposmvvm.features.viewmodel.UIViewModel;
import com.zhy.autolayout.AutoFrameLayout;

/**
 * Created by alienzxh on 16-5-17.
 */
public class LoadingLayout extends AutoFrameLayout {
    private int errorView, loadingView;
    private TextView errorText, loadingText;
    private OnErrorActionClickListener onErrorActionClickListener;

    public interface OnErrorActionClickListener {

        void onErrorActionClick();
    }

    public LoadingLayout(Context context) {
        this(context, null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingLayout, 0, 0);
        try {
            errorView = a.getResourceId(R.styleable.LoadingLayout_errorView, R.layout.view_error);
            loadingView = a.getResourceId(R.styleable.LoadingLayout_loadingView, R.layout.view_loading);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            inflater.inflate(errorView, this, true);
            inflater.inflate(loadingView, this, true);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i = 0; i < getChildCount() - 1; i++) {
            getChildAt(i).setVisibility(GONE);
        }

        TextView errorText = (TextView) findViewById(R.id.error_message);

        TextView loadingText = (TextView) findViewById(R.id.loading_message);

        View errorBtn = findViewById(R.id.btn_error_action);
        if (errorBtn != null)
            errorBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onErrorActionClickListener != null)
                        onErrorActionClickListener.onErrorActionClick();
                }
            });

    }

    public void setOnActionBtnClickListener(OnErrorActionClickListener onErrorActionClickListener) {
        this.onErrorActionClickListener = onErrorActionClickListener;
    }

    public void setUIViewModel(UIViewModel uiViewModel) {
        if (errorText != null)
            errorText.setText(uiViewModel.getErrorMessage());
        if (loadingText != null)
            loadingText.setText(uiViewModel.getLoadingMessage());
        switch (uiViewModel.getUiState()) {
            case UIState.STATE_ERROR:
                showError();
                break;
            case UIState.STATE_LOADING:
                showLoading();
                break;
            case UIState.STATE_CONTENT:
            case UIState.STATE_NORMAL:
                showContent();
                break;


        }
    }

    public void setLoadingText(CharSequence charSequence) {
        if (loadingText != null)
            loadingText.setText(charSequence);
    }

    public void setErrorText(CharSequence charSequence) {
        if (errorText != null)
            errorText.setText(charSequence);
    }

    public void showError() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 0) {
                child.setVisibility(VISIBLE);
            } else {
                if (child.getVisibility() != GONE)
                    child.setVisibility(GONE);
            }
        }
    }

    public void showLoading() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 1) {
                child.setVisibility(VISIBLE);
            } else {
                if (child.getVisibility() != GONE)
                    child.setVisibility(GONE);
            }
        }
    }

    public void showContent() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 2) {
                child.setVisibility(VISIBLE);
            } else {
                if (child.getVisibility() != GONE)
                    child.setVisibility(GONE);
            }
        }
    }
}
