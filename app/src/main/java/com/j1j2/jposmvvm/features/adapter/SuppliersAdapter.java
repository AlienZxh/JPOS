package com.j1j2.jposmvvm.features.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.BaseViewHolder;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.StorageOrder;
import com.j1j2.jposmvvm.data.model.Supplier;

/**
 * Created by alienzxh on 16-7-26.
 */
public class SuppliersAdapter extends RecyclerArrayAdapter<Supplier> {
    public SuppliersAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SuppliersViewHolder(parent);
    }

    public class SuppliersViewHolder extends BaseViewHolder<Supplier> {
        private TextView supplierName;
        private TextView supplierContact;
        private TextView supplierPhone;
        private TextView supplierRemark;

        public SuppliersViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_suppliers);
            supplierName = $(R.id.supplierName);
            supplierContact = $(R.id.supplierContact);
            supplierPhone = $(R.id.supplierPhone);
            supplierRemark = $(R.id.supplierRemark);
        }

        @Override
        public void setData(Supplier data) {
            super.setData(data);
            supplierName.setText("　　名称：" + data.getName());
            supplierContact.setText("　联系人：" + data.getContacter());
            supplierPhone.setText("联系电话：" + data.getContact());
            supplierRemark.setText("　　备注：" + data.getRemark());
        }
    }
}

