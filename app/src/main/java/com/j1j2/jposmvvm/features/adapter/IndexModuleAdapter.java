package com.j1j2.jposmvvm.features.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.BaseViewHolder;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.IndexModule;

import java.util.List;

/**
 * Created by alienzxh on 16-10-23.
 */

public class IndexModuleAdapter extends RecyclerArrayAdapter<IndexModule> {


    public IndexModuleAdapter(Context context, List<IndexModule> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new IndexModuleViewHolder(parent);
    }

    public class IndexModuleViewHolder extends BaseViewHolder<IndexModule> {

        private CardView cardView;
        private TextView icon;
        private TextView text;

        public IndexModuleViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_indexview);
            cardView = $(R.id.cardView);
            icon = $(R.id.icon);
            text = $(R.id.text);
        }

        @Override
        public void setData(IndexModule data) {
            super.setData(data);
            cardView.setCardBackgroundColor(data.getColorInt());
            icon.setText(data.getIconStr());
            text.setText(data.getTitle());
        }
    }
}
