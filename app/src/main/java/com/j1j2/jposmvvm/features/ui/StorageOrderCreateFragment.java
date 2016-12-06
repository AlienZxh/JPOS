package com.j1j2.jposmvvm.features.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.StorageOrder;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.FragmentStorageOrderCreateBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StorageActionCreator;
import com.j1j2.jposmvvm.features.actions.StorageActions;
import com.j1j2.jposmvvm.features.base.BaseFragment;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.base.di.HasComponent;
import com.j1j2.jposmvvm.features.di.components.StorageComponent;
import com.j1j2.jposmvvm.features.stores.StorageStore;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-7-26.
 */
public class StorageOrderCreateFragment extends BaseFragment implements RxViewDispatch, OnDateSetListener {

    public static final int REQ_CODE = 0;

    public interface StorageOrderCreateFragmentListener extends HasComponent<StorageComponent> {
        void setActionBarTitle(String title);

        void showCreateStorageOrderActionBarBtn();

        void showNoActionBarBtn();
    }

    private StorageOrderCreateFragmentListener listener;

    FragmentStorageOrderCreateBinding binding;


    @Inject
    StorageActionCreator storageActionCreator;
    @Inject
    StorageStore storageStore;
    @Inject
    Dispatcher dispatcher;
    @Inject
    Toastor toastor;
    @Inject
    Navigate navigate;

    StorageOrder storageOrder;

    TimePickerDialog mDialogAll;
    long helfYear = 15768000000L;
    private int orderId = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (StorageOrderCreateFragmentListener) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        dispatcher.subscribeRxView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.setActionBarTitle("新增入库单");
        listener.showCreateStorageOrderActionBarBtn();
        binding.remarkEdit.setText("");
    }

    @Override
    public void onStop() {
        super.onStop();
        dispatcher.unsubscribeRxView(this);
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_storage_order_create, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initViews() {
        storageOrder = new StorageOrder();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case StorageStore.ID:
                switch (change.getRxAction().getType()) {
                    case StorageActions.CREATEUPDATESTORAGEORDER:
                        WebReturn<String> storageOrderWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.CREATEUPDATESTORAGEORDER_WEBRETURN);
                        if (storageOrderWebReturn.isValue()) {
                            orderId = Integer.parseInt(storageOrderWebReturn.getDetail());
                            toastor.showSingletonToast("创建入库单成功");
                            storageActionCreator.refreshStorageOrders();
                        } else {
                            toastor.showSingletonToast(storageOrderWebReturn.getErrorMessage());
                        }
                        break;
                    case StorageActions.QUERYSTORAGEORDERS:
                        if (orderId != 0) {
                            navigate.navigateToStorageProductsActivity(getActivity(), null, false, orderId);
                            onBackPressedSupport();
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
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (data != null)
            Logger.d("onFragmentResult " + getClass().getSimpleName() + data.toString());
        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
            storageOrder.setSupplerName(data.getString("selectSupplierName"));
            storageOrder.setSupplerId(data.getInt("selectSupplierId"));
            binding.supplierName.setText(data.getString("selectSupplierName"));
            binding.supplierName.setTextColor(0xff333333);
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        pop();
        listener.setActionBarTitle("入库记录");
        listener.showNoActionBarBtn();
        return true;
    }

    public void createStorageOrder() {
        if (TextUtils.isEmpty(storageOrder.getSupplerName())) {
            toastor.showSingletonToast("请选择供应商");
            return;
        }
        if (TextUtils.isEmpty(storageOrder.getCreateOrderSubmitTimeStr())) {
            toastor.showSingletonToast("请选择入库时间");
            return;
        }
        storageOrder.setRemark(binding.remarkEdit.getText().toString());
        storageActionCreator.createUpdateStorageOrder(storageOrder);
    }

    public void showSelectTimeDialog() {
        if (mDialogAll == null)
            mDialogAll = new TimePickerDialog.Builder()
                    .setCallBack(this)
                    .setCancelStringId("取消")
                    .setSureStringId("确定")
                    .setTitleStringId("请选择时间")
                    .setYearText("年")
                    .setMonthText("月")
                    .setDayText("日")
                    .setHourText("时")
                    .setMinuteText("分")
                    .setCyclic(false)
                    .setMinMillseconds(System.currentTimeMillis() - helfYear)
                    .setMaxMillseconds(System.currentTimeMillis() + helfYear)
                    .setCurrentMillseconds(System.currentTimeMillis())
                    .setThemeColor(getResources().getColor(R.color.colorAccent))
                    .setType(Type.MONTH_DAY_HOUR_MIN)
                    .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                    .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                    .setWheelItemTextSize(14)
                    .build();
        mDialogAll.show(getChildFragmentManager(), "timePick");
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        storageOrder.setCreateOrderSubmitTimeStr(format.format(new Date(millseconds)));
        binding.createTime.setText(format.format(new Date(millseconds)));
        binding.createTime.setTextColor(0xff333333);
    }
}
