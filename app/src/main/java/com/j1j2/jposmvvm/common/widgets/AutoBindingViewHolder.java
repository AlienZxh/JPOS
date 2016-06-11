package com.j1j2.jposmvvm.common.widgets;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by alienzxh on 16-3-12.
 */
public abstract class AutoBindingViewHolder<D extends ViewDataBinding, T> extends RecyclerView.ViewHolder  {

    protected D binding;

    public AutoBindingViewHolder(View itemView) {
        super(itemView);
        AutoUtils.autoSize(itemView);
        this.binding = getBinding(itemView);
    }

    protected abstract D getBinding(View itemView);

    public abstract void bind(@NonNull final T data, int position);


}
