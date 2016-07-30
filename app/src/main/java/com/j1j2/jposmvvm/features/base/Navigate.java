package com.j1j2.jposmvvm.features.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.features.scanner.camera.activity.CaptureActivity;
import com.j1j2.jposmvvm.features.ui.IndexActivityAutoBundle;
import com.j1j2.jposmvvm.features.ui.LoginActivity;
import com.j1j2.jposmvvm.features.ui.StockNoPicturesActivity;
import com.j1j2.jposmvvm.features.ui.StockProductDetailActivity;
import com.j1j2.jposmvvm.features.ui.StockProductDetailActivityAutoBundle;
import com.j1j2.jposmvvm.features.ui.StockSortActivity;
import com.j1j2.jposmvvm.features.ui.StockTakePicturesActivity;
import com.j1j2.jposmvvm.features.ui.StockTakePicturesActivityAutoBundle;
import com.j1j2.jposmvvm.features.ui.StorageOrderActivity;
import com.j1j2.jposmvvm.features.ui.StorageProductsActivityAutoBundle;

/**
 * Created by alienzxh on 16-5-5.
 */
public class Navigate {
    public void navigateToLoginActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (null == options || Build.VERSION.SDK_INT < 16) {
            context.startActivity(intent);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, intent, options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToIndexActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int shopId) {
        Intent intent = IndexActivityAutoBundle.createIntentBuilder(shopId).build(context);
        if (null == options || Build.VERSION.SDK_INT < 16) {
            context.startActivity(intent);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, intent, options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToStockNoPicturesActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        Intent intent = new Intent(context, StockNoPicturesActivity.class);
        if (null == options || Build.VERSION.SDK_INT < 16) {
            context.startActivity(intent);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, intent, options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToStockSortActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        Intent intent = new Intent(context, StockSortActivity.class);
        if (null == options || Build.VERSION.SDK_INT < 16) {
            context.startActivity(intent);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, intent, options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToStockTakePicturesActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int stockId, int fromType, int position) {
        Intent intent = StockTakePicturesActivityAutoBundle.createIntentBuilder(stockId, fromType, position).build(context);
        if (null == options || Build.VERSION.SDK_INT < 16) {
            context.startActivity(intent);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, intent, options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToStockProductDetailActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int stockId, int fromType, int position) {
        Intent intent = StockProductDetailActivityAutoBundle.createIntentBuilder(stockId, fromType, position).build(context);
        if (null == options || Build.VERSION.SDK_INT < 16) {
            context.startActivity(intent);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, intent, options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToStorageOrderActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        Intent intent = new Intent(context, StorageOrderActivity.class);
        if (null == options || Build.VERSION.SDK_INT < 16) {
            context.startActivity(intent);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, intent, options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToStorageProductsActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int orderId) {
        Intent intent = StorageProductsActivityAutoBundle.createIntentBuilder(orderId).build(context);
        if (null == options || Build.VERSION.SDK_INT < 16) {
            context.startActivity(intent);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivity(context, intent, options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }

    public void navigateToCaptureActivityForResult(Activity context, ActivityOptionsCompat options, boolean isFinish, int requestCode) {
        Intent intent = new Intent(context, CaptureActivity.class);
        if (null == options || Build.VERSION.SDK_INT < 16) {
            context.startActivityForResult(intent, requestCode);
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ActivityCompat.startActivityForResult(context, intent, requestCode, options.toBundle());
        }
        if (isFinish) {
            ActivityCompat.finishAfterTransition(context);
        }
    }


}
