package com.j1j2.jposmvvm.features.ui;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.widgets.recyclerviewadapter.RecyclerArrayAdapter;
import com.j1j2.jposmvvm.data.model.IndexModule;
import com.j1j2.jposmvvm.databinding.FragmentIndexBinding;
import com.j1j2.jposmvvm.features.adapter.IndexModuleAdapter;
import com.j1j2.jposmvvm.features.base.BaseFragment;
import com.yatatsu.autobundle.AutoBundleField;

import java.util.ArrayList;

/**
 * Created by alienzxh on 16-10-22.
 */

public class IndexFragment extends BaseFragment {

    public interface IndexFragmentListener {
        void navigateToStock();

        void navigateToPhoto();

        void navigateToStorage();

        void navigateToCash();

        void navigateToReport();

        void navigateToStockCheck();

        void navigateToPrintLabel();
    }

    IndexFragmentListener listener;

    FragmentIndexBinding binding;

    @AutoBundleField
    ArrayList<IndexModule> modules;

    @Override
    protected void setupActivityComponent() {
        IndexFragmentAutoBundle.bind(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (IndexFragmentListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_index, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.indexList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        final IndexModuleAdapter adapter;
        binding.indexList.setAdapter(adapter = new IndexModuleAdapter(getContext(), modules));
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String title = adapter.getItem(position).getTitle();
                if (title.equals("库存管理"))
                    listener.navigateToStock();
                if (title.equals("商品拍照"))
                    listener.navigateToPhoto();
                if (title.equals("商品入库"))
                    listener.navigateToStorage();
                if (title.equals("商品收银"))
                    listener.navigateToCash();
                if (title.equals("销售报表"))
                    listener.navigateToReport();
                if (title.equals("库存盘点"))
                    listener.navigateToStockCheck();
                if (title.equals("标签打印"))
                    listener.navigateToPrintLabel();
            }
        });
    }


}
