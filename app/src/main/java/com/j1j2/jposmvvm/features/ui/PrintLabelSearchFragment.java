package com.j1j2.jposmvvm.features.ui;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.constants.Constants;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.data.model.PrintPriceLabelProduct;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.FragmentPrintlabelSearchBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.PrintProductPriceLabelActionCreator;
import com.j1j2.jposmvvm.features.actions.PrintProductPriceLabelActions;
import com.j1j2.jposmvvm.features.adapter.PrintLabelSearchAdapter;
import com.j1j2.jposmvvm.features.base.BaseFragment;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.base.di.HasComponent;
import com.j1j2.jposmvvm.features.di.components.PrintProductPriceLabelComponent;
import com.j1j2.jposmvvm.features.stores.PrintProductPriceLabelStore;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-10-23.
 */

public class PrintLabelSearchFragment extends BaseFragment implements RxViewDispatch, SwipeRefreshLayout.OnRefreshListener, PrintLabelSearchAdapter.PrintLabelSearchAdapterListener {

    public interface PrintLabelSearchFragmentListener extends HasComponent<PrintProductPriceLabelComponent> {
        String getKey();

        int getOrderId();

        void onSearchFinish();

        void onSelectStockAction(PrintPriceLabelProduct printPriceLabelProduct);
    }

    PrintLabelSearchFragmentListener listener;
    FragmentPrintlabelSearchBinding binding;

    @Inject
    PrintProductPriceLabelActionCreator printProductPriceLabelActionCreator;
    @Inject
    PrintProductPriceLabelStore printProductPriceLabelStore;
    @Inject
    Toastor toastor;
    @Inject
    Navigate navigate;

    PrintLabelSearchAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (PrintLabelSearchFragmentListener) activity;
    }

    @Override
    protected void setupActivityComponent() {
        listener.getComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }


    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_printlabel_search, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        binding.stockList.setRefreshingColorResources(R.color.colorPrimary);
        binding.stockList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.stockList.setAdapterWithProgress(adapter = new PrintLabelSearchAdapter(getContext()));
        adapter.setNoMore(R.layout.view_nomore_footer);
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        binding.stockList.setRefreshListener(this);
        adapter.setPrintLabelSearchAdapterListener(this);
        onRefresh();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case PrintProductPriceLabelStore.ID:
                switch (change.getRxAction().getType()) {
                    case PrintProductPriceLabelActions.QUERYPRODUCTINPRINTPRICELABEL:
                        WebReturn<List<PrintPriceLabelProduct>> listWebReturn =
                                (WebReturn<List<PrintPriceLabelProduct>>) change.getRxAction().get(Keys.QUERYPRODUCTINPRINTPRICELABEL_WEBRETURN);
                        if (listWebReturn.isValue()) {
                            if (listWebReturn.getDetail() != null && listWebReturn.getDetail().size() > 0)
                                adapter.addAll(listWebReturn.getDetail());
                            else {
                                toastor.showSingletonToast("未搜索到商品");
                                onBackPressedSupport();
                            }
                        } else {
                            toastor.showSingletonToast(listWebReturn.getErrorMessage());
                        }
                        break;
                }
                break;
        }
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

    @Override
    public void onRefresh() {
        adapter.clear();
        printProductPriceLabelActionCreator.queryProductInPrintPriceLabel(listener.getKey());
    }


    @Override
    public void onSelectStockAction(PrintPriceLabelProduct printPriceLabelProduct) {
        listener.onSelectStockAction(printPriceLabelProduct);
        onBackPressedSupport();
    }

    @Override
    public void onItemClickAction(int position, PrintPriceLabelProduct printPriceLabelProduct) {
        navigate.navigateToStockProductDetailActivity(getActivity(), null, false, printPriceLabelProduct.getStockId(), false, Constants.FROM_PRINTLABEL_SEARCH, position, 0, "");
    }

    @Override
    public boolean onBackPressedSupport() {
        listener.onSearchFinish();
        hideSoftInput();
        pop();
        return true;
    }
}
