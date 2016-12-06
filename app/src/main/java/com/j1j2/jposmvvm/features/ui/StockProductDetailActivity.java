package com.j1j2.jposmvvm.features.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.constants.Constants;
import com.j1j2.jposmvvm.common.constants.UIState;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.data.model.CashOrder;
import com.j1j2.jposmvvm.data.model.CashPuzzyQueryStock;
import com.j1j2.jposmvvm.data.model.ProductDetail;
import com.j1j2.jposmvvm.data.model.StockImg;
import com.j1j2.jposmvvm.data.model.StorageOrderItem;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.ActivityStockProductDetailBinding;
import com.j1j2.jposmvvm.features.actions.CashActionCreator;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StockActionCreator;
import com.j1j2.jposmvvm.features.actions.StockActions;
import com.j1j2.jposmvvm.features.actions.StorageActionCreator;
import com.j1j2.jposmvvm.features.adapter.StockTakePictureProductAdapter;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.components.StockNoPicturesComponent;
import com.j1j2.jposmvvm.features.di.modules.StockNoPicturesModule;
import com.j1j2.jposmvvm.features.event.BarCodeEvent;
import com.j1j2.jposmvvm.features.event.CreateStockCheckOrderItemEvent;
import com.j1j2.jposmvvm.features.event.CreateStockPrintLabelEvent;
import com.j1j2.jposmvvm.features.event.RefreshStockCheckOrderDetailEvent;
import com.j1j2.jposmvvm.features.event.RefreshStockPrintLabelEvent;
import com.j1j2.jposmvvm.features.scanner.camera.activity.CaptureActivity;
import com.j1j2.jposmvvm.features.stores.StockStore;
import com.j1j2.jposmvvm.features.viewmodel.ProductViewModel;
import com.j1j2.jposmvvm.features.viewmodel.UIViewModel;
import com.orhanobut.logger.Logger;
import com.yatatsu.autobundle.AutoBundleField;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import io.realm.Realm;

/**
 * Created by alienzxh on 16-6-15.
 */
public class StockProductDetailActivity extends BaseActivity implements StockTakePictureProductAdapter.OnStockTakePictureActionClickListener {

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;

    ActivityStockProductDetailBinding binding;

    private StockNoPicturesComponent stockNoPicturesComponent;

    @Inject
    StockActionCreator stockActionCreator;
    @Inject
    StorageActionCreator storageActionCreator;
    @Inject
    CashActionCreator cashActionCreator;

    @Inject
    Navigate navigate;
    @Inject
    UIViewModel uiViewModel;
    @Inject
    Dispatcher dispatcher;
    @Inject
    Toastor toastor;


    @AutoBundleField
    boolean isNew;
    @AutoBundleField
    int fromType;

    @AutoBundleField(required = false)
    int stockId;
    @AutoBundleField(required = false)
    int position;
    @AutoBundleField(required = false)
    int orderId;
    @AutoBundleField(required = false)
    String orderNO;

    private int imgId = 0;
    private boolean isDefaultImg = false;

    private ProductViewModel productDetailViewModel;
    private StockTakePictureProductAdapter adapter;

    private Realm realm;

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            uiViewModel.setLoadingMessage("图片上传中......");
            uiViewModel.setUiState(UIState.STATE_LOADING);
            stockActionCreator.upLoadPhoto(resultList.get(0).getPhotoPath(), stockId, imgId, isDefaultImg, 2);
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    @Override
    protected void setupActivityComponent() {
        StockProductDetailActivityAutoBundle.bind(this, getIntent());
        stockNoPicturesComponent = JPOSApplication.get(this).getShopComponent().plus(new StockNoPicturesModule(this));
        stockNoPicturesComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        realm = Realm.getDefaultInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stock_product_detail);

    }

    @Override
    protected void initViews() {
        binding.setMUIViewModel(uiViewModel);
        //_________________________________________________________________________________
        binding.imgList.setLayoutManager(new GridLayoutManager(this, 3));
        productDetailViewModel = new ProductViewModel();
        if (isNew) {
            productDetailViewModel.setProductDetail(new ProductDetail());
            binding.setProductViewModel(productDetailViewModel);
            adapter = new StockTakePictureProductAdapter(new ArrayList<StockImg>());
            adapter.setListener(this);
            binding.imgList.setAdapter(adapter);
        } else {
            uiViewModel.setLoadingMessage("加载中......");
            uiViewModel.setUiState(UIState.STATE_LOADING);
            stockActionCreator.queryStockDetail(stockId);
        }

    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

        switch (change.getStoreId()) {
            case StockStore.ID:
                switch (change.getRxAction().getType()) {

                    case StockActions.QUERYSTOCKDETAIL:
                        WebReturn<ProductDetail> productDetailWebReturn =
                                (WebReturn<ProductDetail>) change.getRxAction().get(Keys.PRODUCTDETAIL_WEBRETURN);
                        if (productDetailWebReturn.isValue()) {
                            productDetailViewModel.setProductDetail(productDetailWebReturn.getDetail());
                            binding.setProductViewModel(productDetailViewModel);

                            adapter = new StockTakePictureProductAdapter(productDetailWebReturn.getDetail().getStockImgs() == null ? new ArrayList<StockImg>() : productDetailWebReturn.getDetail().getStockImgs());
                            adapter.setListener(this);
                            binding.imgList.setAdapter(adapter);
                            if (isNew) {

                            } else {
                                if (fromType == Constants.FROM_STOCK_SEARCH || fromType == Constants.FROM_STOCK_SORT)
                                    stockActionCreator.refreshList(fromType, position, productDetailWebReturn.getDetail());
                                if (fromType == Constants.FROM_STORAGE_SEARCH || fromType == Constants.FROM_STORAGE_PRODUCTS)
                                    storageActionCreator.refreshListItem(fromType, position, productDetailWebReturn.getDetail());
                                if (fromType == Constants.FROM_CASH_ORDER || fromType == Constants.FROM_CASH_SEARCH)
                                    cashActionCreator.refreshListItem(fromType, position, productDetailWebReturn.getDetail());
                            }
                        } else {
                            toastor.showSingletonToast(productDetailWebReturn.getErrorMessage());
                        }


                        break;

                    case StockActions.CREATEORUPDATESTOCK:
                        WebReturn<String> stringWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.CREATEORUPDATESTOCK_WEBRETURN);
                        if (stringWebReturn.isValue()) {
                            stockId = Integer.parseInt(stringWebReturn.getDetail());
                            stockActionCreator.queryStockDetail(stockId);
                            Snackbar.make(binding.rootLayout, "数据保存成功", Snackbar.LENGTH_LONG)
                                    .show();
                        } else {
                            Snackbar.make(binding.rootLayout, stringWebReturn.getErrorMessage(), Snackbar.LENGTH_LONG)
                                    .show();
                        }

                        break;
                    case StockActions.REMOVESTOCKIMG:

                        WebReturn<String> removeWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.REMOVESTOCKIMG_WEBRETURN);
                        if (removeWebReturn.isValue()) {
                            stockActionCreator.queryStockDetail(stockId);
                        } else {
                            toastor.showSingletonToast(removeWebReturn.getErrorMessage());
                        }
                        break;
                    case StockActions.UPLOADIMG:
                        WebReturn<String> upLoadWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.UPLOADSTOCKIMG_WEBRETURN);
                        if (upLoadWebReturn.isValue()) {
                            stockActionCreator.queryStockDetail(stockId);
                        } else {
                            toastor.showSingletonToast(upLoadWebReturn.getErrorMessage());
                        }
                        break;
                }
                break;
        }
        uiViewModel.setUiState(UIState.STATE_CONTENT);

    }

    @Override
    public void onRxError(@NonNull RxError error) {
        uiViewModel.setErrorMessage(error.getThrowable().getMessage());
        uiViewModel.setUiState(UIState.STATE_ERROR);
        Logger.e(getLocalClassName() + " error:" + error.getThrowable().toString());
        Snackbar.make(binding.rootLayout, error.getThrowable().getMessage(), Snackbar.LENGTH_LONG)
                .show();
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

    public void navigateToCaptureActivityForResult(View v) {
        navigate.navigateToCaptureActivityForResult(this, null, false, CaptureActivity.SCANNER_REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CaptureActivity.SCANNER_REQUESTCODE && resultCode == RESULT_OK) {
//            binding.editSearch.setText(data.getStringExtra("result"));
            binding.barcodeEdit.setText(data.getStringExtra("result"));
        } else {
//            binding.editSearch.clearFocus();
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onBarCodeEvent(final BarCodeEvent event) {

        binding.barcodeEdit.setText(event.getBarCode());
    }

    @Override
    public void onPictureClick(StockImg stockImg, int position) {
        imgId = stockImg.getImgId();
        isDefaultImg = position == 0;
        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
    }

    @Override
    public void onPictureDeleteClick(StockImg stockImg, int position) {
        if (position == 0) {
            showCantRemoveDialog();
        } else
            showRemoveDialog(stockImg.getImgId());
    }

    @Override
    public void onPictureAddClick(int position) {
        if (isNew && stockId == 0) {
            save(null);
        } else {
            imgId = 0;
            isDefaultImg = position == 0;
            GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
        }

    }

    public void save(View v) {

        if (TextUtils.isEmpty(binding.getProductViewModel().getUnit())) {
            toastor.showSingletonToast("商品单位不能为空");
            return;
        }
        if (TextUtils.isEmpty(binding.getProductViewModel().getName())) {
            toastor.showSingletonToast("商品名称不能为空");
            return;
        }

//        if (TextUtils.isEmpty(binding.getProductViewModel().getMemberPrice())) {
//            toastor.showSingletonToast("商品会员价不能为空");
//            return;
//        }
//
//        if (TextUtils.isEmpty(binding.getProductViewModel().getRetailPrice())) {
//            toastor.showSingletonToast("商品价格不能为空");
//            return;
//        }
        stockActionCreator.createOrUpdateStock(binding.getProductViewModel().getProductDetail());
        uiViewModel.setLoadingMessage("数据保存中......");
        uiViewModel.setUiState(UIState.STATE_LOADING);
    }


    private void showRemoveDialog(final int imgId) {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setNegativeButton("取消", null)
                .setMessage("确认删除该图片吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uiViewModel.setLoadingMessage("图片删除中......");
                        uiViewModel.setUiState(UIState.STATE_LOADING);
                        stockActionCreator.removeStockImg(stockId, imgId);
                    }
                })
                .create().show();
    }

    private void showCantRemoveDialog() {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("提示")
                .setMessage("您无法删除主图片!")
                .setPositiveButton("确定", null)
                .create().show();
    }

    @Override
    public void onBackPressedSupport() {
        if (isNew) {
            if (fromType == Constants.FROM_STORAGE_PRODUCTS) {
                StorageOrderItem storageOrderItem = new StorageOrderItem();
                storageOrderItem.setOrderId(orderId);
                storageOrderItem.setStockId(stockId);
                storageOrderItem.setQuantity(0);
                storageActionCreator.createOrUpdateStoreageOrderDetailItem(storageOrderItem, true);
            } else if (fromType == Constants.FROM_CASH_ORDER) {
                CashOrder cashOrder = realm.where(CashOrder.class).equalTo("OrderNO", orderNO).findFirst();
                realm.beginTransaction();
                CashPuzzyQueryStock cashPuzzyQueryStock = realm.createObject(CashPuzzyQueryStock.class);
                cashPuzzyQueryStock.setName(productDetailViewModel.getProductDetail().getName());
                cashPuzzyQueryStock.setRetailPrice(productDetailViewModel.getProductDetail().getRetailPrice());
                cashPuzzyQueryStock.setMemberPrice(productDetailViewModel.getProductDetail().getMemberPrice());
                cashPuzzyQueryStock.setBarCode(productDetailViewModel.getProductDetail().getBarCode());
                cashPuzzyQueryStock.setSmallImgUrl(productDetailViewModel.getProductDetail().getThumbImgUrl());
                cashPuzzyQueryStock.setSpec(productDetailViewModel.getProductDetail().getSpec());
                cashPuzzyQueryStock.setUnit(productDetailViewModel.getProductDetail().getUnit());
                cashPuzzyQueryStock.setStockId(productDetailViewModel.getProductDetail().getStockId());
                cashOrder.getCashOrderStocks().add(cashPuzzyQueryStock);
                realm.commitTransaction();
            } else if (fromType == Constants.FROM_STOCKCHECK_ORDER) {
                EventBus.getDefault().post(new CreateStockCheckOrderItemEvent(productDetailViewModel.getProductDetail()));
            } else if (fromType == Constants.FROM_PRINTLABEL_PRODUCTS) {
                EventBus.getDefault().post(new CreateStockPrintLabelEvent(productDetailViewModel.getProductDetail()));
            }
        } else {
            if (fromType == Constants.FROM_STOCKCHECK_ORDER) {
                EventBus.getDefault().post(new RefreshStockCheckOrderDetailEvent());
            } else if (fromType == Constants.FROM_PRINTLABEL_PRODUCTS) {
                EventBus.getDefault().post(new RefreshStockPrintLabelEvent(position,productDetailViewModel.getProductDetail()));
            }
        }

        super.onBackPressedSupport();
    }
}
