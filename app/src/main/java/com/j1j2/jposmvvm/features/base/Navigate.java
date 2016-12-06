package com.j1j2.jposmvvm.features.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.features.scanner.camera.activity.CaptureActivity;
import com.j1j2.jposmvvm.features.ui.CashActivity;
import com.j1j2.jposmvvm.features.ui.CashOrderPayActivityAutoBundle;
import com.j1j2.jposmvvm.features.ui.IndexActivityAutoBundle;
import com.j1j2.jposmvvm.features.ui.LoginActivity;
import com.j1j2.jposmvvm.features.ui.MemberDetailActivityAutoBundle;
import com.j1j2.jposmvvm.features.ui.PrintLabelOrderCreateActivity;
import com.j1j2.jposmvvm.features.ui.PrintLabelOrderDetailsActivityAutoBundle;
import com.j1j2.jposmvvm.features.ui.SaleOrderActivityAutoBundle;
import com.j1j2.jposmvvm.features.ui.SaleOrderDetailActivityAutoBundle;
import com.j1j2.jposmvvm.features.ui.SaleStatisticActivity;
import com.j1j2.jposmvvm.features.ui.SaleStatisticInMonthActivityAutoBundle;
import com.j1j2.jposmvvm.features.ui.StockCheckOrderActivity;
import com.j1j2.jposmvvm.features.ui.StockCheckOrderDetailActivityAutoBundle;
import com.j1j2.jposmvvm.features.ui.StockNoPicturesActivity;
import com.j1j2.jposmvvm.features.ui.StockProductDetailActivityAutoBundle;
import com.j1j2.jposmvvm.features.ui.StockSortActivity;
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

    public void navigateToStockProductDetailActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int stockId, boolean isNew, int fromType, int position, int orderId, String orderNO) {
        Intent intent = StockProductDetailActivityAutoBundle.createIntentBuilder(isNew, fromType)
                .orderId(orderId)
                .stockId(stockId)
                .position(position)
                .orderNO(orderNO)
                .build(context);
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

    public void navigateToCashActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        Intent intent = new Intent(context, CashActivity.class);
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

    public void navigateToMemberDetailActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int userId) {
        Intent intent = MemberDetailActivityAutoBundle.createIntentBuilder(userId).build(context);
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

    public void navigateToCashOrderPayActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, String orderNO, int payType) {
        Intent intent = CashOrderPayActivityAutoBundle.createIntentBuilder(orderNO, payType).build(context);
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

    public void navigateToSaleOrderActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, String beginTime, String endTime) {
        Intent intent = SaleOrderActivityAutoBundle.createIntentBuilder(beginTime, endTime).build(context);
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

    public void navigateToSaleStatisticActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        Intent intent = new Intent(context, SaleStatisticActivity.class);
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

    public void navigateToSaleStatisticInMonthActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, long time) {
        Intent intent = SaleStatisticInMonthActivityAutoBundle.createIntentBuilder(time).build(context);
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

    public void navigateToSaleOrderDetailActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int orderId) {
        Intent intent = SaleOrderDetailActivityAutoBundle.createIntentBuilder(orderId).build(context);
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

    public void navigateToStockCheckOrderActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        Intent intent = new Intent(context, StockCheckOrderActivity.class);
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

    public void navigateToStockCheckOrderDetailActivity(Activity context, ActivityOptionsCompat options, boolean isFinish, int stockDetailId) {
        Intent intent = StockCheckOrderDetailActivityAutoBundle.createIntentBuilder(stockDetailId)
                .build(context);
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

    public void navigateToPrintLabelOrderCreateActivity(Activity context, ActivityOptionsCompat options, boolean isFinish) {
        Intent intent = new Intent(context, PrintLabelOrderCreateActivity.class);
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

    public void navigateToPrintLabelOrderDetailsActivity(Activity context, ActivityOptionsCompat options, boolean isFinish,int orderId) {
        Intent intent = PrintLabelOrderDetailsActivityAutoBundle.createIntentBuilder(orderId).build(context);
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
