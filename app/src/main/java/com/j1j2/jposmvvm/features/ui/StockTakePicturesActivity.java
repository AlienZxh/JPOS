package com.j1j2.jposmvvm.features.ui;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.constants.UIState;
import com.j1j2.jposmvvm.data.model.PageManager;
import com.j1j2.jposmvvm.data.model.Product;
import com.j1j2.jposmvvm.data.model.ProductDetail;
import com.j1j2.jposmvvm.data.model.StockImg;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.ActivityStockTakepicturesBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.StockActionCreator;
import com.j1j2.jposmvvm.features.actions.StockActions;
import com.j1j2.jposmvvm.features.adapter.StockTakePictureProductAdapter;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.JPOSApplication;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.modules.StockTakePicturesModule;
import com.j1j2.jposmvvm.features.stores.StockStore;
import com.j1j2.jposmvvm.features.viewmodel.UIViewModel;
import com.yatatsu.autobundle.AutoBundleField;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by alienzxh on 16-6-8.
 */
public class StockTakePicturesActivity extends BaseActivity implements StockTakePictureProductAdapter.OnStockTakePictureActionClickListener {
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;

    public static final int From_SORT = 9;
    public static final int From_SEARCH = 99;

    ActivityStockTakepicturesBinding binding;

    @Inject
    StockActionCreator stockActionCreator;
    @Inject
    StockStore stockStore;
    @Inject
    Navigate navigate;
    @Inject
    UIViewModel uiViewModel;
    @Inject
    Dispatcher dispatcher;

    @AutoBundleField
    int stockId;
    @AutoBundleField
    int fromType;
    @AutoBundleField
    int position;

    private int imgId = 0;
    private boolean isDefaultImg = false;

    private ProductDetail productDetail;
    private StockTakePictureProductAdapter adapter;

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
        StockTakePicturesActivityAutoBundle.bind(this, getIntent());
        JPOSApplication.get(this).getShopComponent().plus(new StockTakePicturesModule(this)).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stock_takepictures);
        binding.setMUIViewModel(uiViewModel);
    }

    @Override
    protected void initViews() {
        binding.imgList.setLayoutManager(new GridLayoutManager(this, 3));
        uiViewModel.setLoadingMessage("加载中......");
        uiViewModel.setUiState(UIState.STATE_LOADING);
        stockActionCreator.queryStockDetail(stockId);

    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case StockStore.ID:
                switch (change.getRxAction().getType()) {

                    case StockActions.QUERYSTOCKDETAIL:
                        WebReturn<ProductDetail> productDetailWebReturn =
                                (WebReturn<ProductDetail>) change.getRxAction().get(Keys.PRODUCTDETAIL_WEBRETURN);
                        binding.setProductDetail(productDetailWebReturn.getDetail());
                        adapter = new StockTakePictureProductAdapter(productDetailWebReturn.getDetail().getStockImgs());
                        adapter.setListener(this);
                        binding.imgList.setAdapter(adapter);
                        stockActionCreator.refreshList(fromType, position, productDetailWebReturn.getDetail());
                        break;

                    case StockActions.CREATEORUPDATESTOCK:
                        WebReturn<String> stringWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.CREATEORUPDATESTOCK_WEBRETURN);
                        finish();
                        break;
                    case StockActions.REMOVESTOCKIMG:

                        WebReturn<String> removeWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.REMOVESTOCKIMG_WEBRETURN);
                        if (removeWebReturn.isValue()) {
                            stockActionCreator.queryStockDetail(stockId);
                        }
                        break;
                    case StockActions.UPLOADIMG:
                        WebReturn<String> upLoadWebReturn =
                                (WebReturn<String>) change.getRxAction().get(Keys.UPLOADSTOCKIMG_WEBRETURN);
                        if (upLoadWebReturn.isValue()) {
                            stockActionCreator.queryStockDetail(stockId);
                        }
                        break;
                }
                break;
        }

        uiViewModel.setUiState(UIState.STATE_CONTENT);
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
//        return Collections.<RxStore>singletonList(stockStore);
        return null;
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
//        return Collections.<RxStore>singletonList(stockStore);
        return null;
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
        imgId = 0;
        isDefaultImg = position == 0;
        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
    }

    public void save(View v) {
        onBackPressed();
    }

//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//        stockActionCreator.createOrUpdateStock(binding.getProductDetail());
//        uiViewModel.setLoadingMessage("数据保存中......");
//        uiViewModel.setUiState(UIState.STATE_LOADING);
//    }

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
}
