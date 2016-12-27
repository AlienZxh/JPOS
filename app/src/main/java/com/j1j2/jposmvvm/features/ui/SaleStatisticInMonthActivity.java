package com.j1j2.jposmvvm.features.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;
import com.j1j2.jposmvvm.JPOSApplication;
import com.j1j2.jposmvvm.JPOSApplicationLike;
import com.j1j2.jposmvvm.R;
import com.j1j2.jposmvvm.common.utils.ScreenUtils;
import com.j1j2.jposmvvm.common.utils.Toastor;
import com.j1j2.jposmvvm.data.model.SaleStatic;
import com.j1j2.jposmvvm.data.model.SaleStaticByCategory;
import com.j1j2.jposmvvm.data.model.WebReturn;
import com.j1j2.jposmvvm.databinding.ActivitySaleStatisticInmonthBinding;
import com.j1j2.jposmvvm.features.actions.Keys;
import com.j1j2.jposmvvm.features.actions.SaleStatisticActionCreator;
import com.j1j2.jposmvvm.features.actions.SaleStatisticActions;
import com.j1j2.jposmvvm.features.base.BaseActivity;
import com.j1j2.jposmvvm.features.base.Navigate;
import com.j1j2.jposmvvm.features.di.modules.SaleStatisticModule;
import com.j1j2.jposmvvm.features.stores.SaleStatisticStore;
import com.j1j2.jposmvvm.features.viewmodel.SaleStatisticViewModel;
import com.j1j2.jposmvvm.features.viewmodel.UIViewModel;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.yatatsu.autobundle.AutoBundleField;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alienzxh on 16-8-16.
 */
public class SaleStatisticInMonthActivity extends BaseActivity implements OnDateSetListener {

    ActivitySaleStatisticInmonthBinding binding;

    @AutoBundleField
    long timeInMillis;
    int year;
    int month;

    @Inject
    SaleStatisticStore saleStatisticStore;
    @Inject
    SaleStatisticActionCreator saleStatisticActionCreator;
    @Inject
    Toastor toastor;
    @Inject
    Navigate navigate;
    @Inject
    UIViewModel uiViewModel;
    @Inject
    Dispatcher dispatcher;

    SaleStatisticViewModel saleStatisticViewModel = new SaleStatisticViewModel();

    TimePickerDialog mDialogAll;

    @Override
    protected void setupActivityComponent() {
        SaleStatisticInMonthActivityAutoBundle.bind(this, getIntent());
        JPOSApplicationLike.get().getShopComponent().plus(new SaleStatisticModule(this)).inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sale_statistic_inmonth);
    }

    @Override
    protected void initViews() {
        //___________________________________________________________________________
        binding.setSaleVM(saleStatisticViewModel);
        //______________________________-
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        binding.actionBarTitle.setText("" + year + "年" + month + "月报表");

        //______________________________________________________________________________
        binding.saleLineChart.setDescription("");
        binding.saleLineChart.setNoDataText("暂无数据");

        XAxis xAxis = binding.saleLineChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = binding.saleLineChart.getAxisLeft();
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        YAxis rightAxis = binding.saleLineChart.getAxisRight();
        rightAxis.enableGridDashedLine(10f, 10f, 0f);

        // get the legend (only possible after setting data)
        Legend saleLineChartLegend = binding.saleLineChart.getLegend();
        // modify the legend ...
        saleLineChartLegend.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        saleLineChartLegend.setForm(Legend.LegendForm.LINE);

        //______________________________________________________________________________

        binding.categoryPieChart.setExtraOffsets(-AutoUtils.getPercentHeightSize(70), 0, 0, 0);

        binding.categoryPieChart.setUsePercentValues(true);
        binding.categoryPieChart.setDescription("");
        binding.categoryPieChart.setNoDataText("暂无数据");

        binding.categoryPieChart.setDrawHoleEnabled(true);
        binding.categoryPieChart.setHoleColor(Color.WHITE);

        binding.categoryPieChart.setTransparentCircleColor(Color.WHITE);
        binding.categoryPieChart.setTransparentCircleAlpha(110);

        binding.categoryPieChart.setHoleRadius(45f);
        binding.categoryPieChart.setTransparentCircleRadius(61f);

        binding.categoryPieChart.setDrawCenterText(true);

        binding.categoryPieChart.setRotationEnabled(true);
        binding.categoryPieChart.setHighlightPerTapEnabled(true);

        binding.categoryPieChart.setEntryLabelColor(Color.GRAY);
        binding.categoryPieChart.setEntryLabelTextSize(12f);

        Legend l = binding.categoryPieChart.getLegend();
        l.setEnabled(true);
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(20f);
        //______________________________________________________________________________
        saleStatisticActionCreator.loadSaleStaticByMonth(year, month);
        saleStatisticActionCreator.loadSaleStaticInMonthByCategory(year, month);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case SaleStatisticStore.ID:
                switch (change.getRxAction().getType()) {
                    case SaleStatisticActions.LOADSALESTATICBYMONTH:
                        WebReturn<List<SaleStatic>> saleStaticWebReturn =
                                (WebReturn<List<SaleStatic>>) change.getRxAction().get(Keys.LOADSALESTATICBYMONTH_WEBRETURN);
                        if (saleStaticWebReturn.isValue()) {

                            if (saleStaticWebReturn.getDetail() != null && saleStaticWebReturn.getDetail().size() > 0) {
                                //_____________________________________________________________________
                                initSaleLineChart(saleStaticWebReturn.getDetail());
                                //__________________________________________________________________
                                saleStatisticViewModel.setSaleStatics(saleStaticWebReturn.getDetail());
                            }


                        } else {
                            toastor.showSingletonToast(saleStaticWebReturn.getErrorMessage());
                        }
                        break;
                    case SaleStatisticActions.LOADSALESTATICINMONTHBYCATEGORY:
                        WebReturn<List<SaleStaticByCategory>> saleStaticByCategoryWebReturn =
                                (WebReturn<List<SaleStaticByCategory>>) change.getRxAction().get(Keys.LOADSALESTATICINMONTHBYCATEGORY_WEBRETURN);
                        if (saleStaticByCategoryWebReturn.isValue()) {
                            if (saleStaticByCategoryWebReturn.getDetail() != null && saleStaticByCategoryWebReturn.getDetail().size() > 0)
                                initCategoryPieChart(saleStaticByCategoryWebReturn.getDetail());
                        } else {
                            toastor.showSingletonToast(saleStaticByCategoryWebReturn.getErrorMessage());
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

    public void initSaleLineChart(List<SaleStatic> saleStaticList) {
        int day = 0;
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(new Date());
        if (month == (nowCalendar.get(Calendar.MONTH) + 1)) {
            day = nowCalendar.get(Calendar.DAY_OF_MONTH);
        } else {
            nowCalendar.setTimeInMillis(timeInMillis);
            day = nowCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        List<Entry> values = new ArrayList<>();
        for (int i = 1; i <= day; i++) {
            for (int j = 0; j < saleStaticList.size(); j++) {
                if (i == saleStaticList.get(j).getDay()) {
                    values.add(new Entry(i, (float) saleStaticList.get(j).getOrderAmount()));
                    break;
                }
                if (j == saleStaticList.size() - 1) {
                    values.add(new Entry(i, 0f));
                    break;
                }
            }

        }

//        Logger.d("zxh" + values.toString());

        LineDataSet set = new LineDataSet(values, "销售额");

        set.setLineWidth(1f);
        set.setCircleRadius(3f);
        set.setDrawCircleHole(false);
        set.setValueTextSize(9f);
        set.setValueTextColor(Color.DKGRAY);
        set.setDrawFilled(true);

        LineData data = new LineData(set);

        // set data
        binding.saleLineChart.setData(data);
        binding.saleLineChart.animateX(2000);
        binding.saleLineChart.invalidate();
    }

    public void initCategoryPieChart(List<SaleStaticByCategory> saleStaticByCategories) {
        ArrayList<PieEntry> values = new ArrayList<PieEntry>();

        for (int i = 0; i < saleStaticByCategories.size(); i++) {
            values.add(new PieEntry((float) saleStaticByCategories.get(i).getOrderAmount(), saleStaticByCategories.get(i).getCategory()));
        }

        PieDataSet dataSet = new PieDataSet(values, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        //_________________________________________________
        dataSet.setValueLinePart1OffsetPercentage(70.f);
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.5f);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        dataSet.setValueLineColor(Color.GRAY);

        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        binding.categoryPieChart.setData(data);
        binding.categoryPieChart.animateY(2000, Easing.EasingOption.EaseInOutQuad);
        binding.categoryPieChart.invalidate();
    }

    public void timePick(View v) {
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
                    .setMinMillseconds(System.currentTimeMillis() - 311040000000l)
                    .setMaxMillseconds(System.currentTimeMillis())
                    .setCurrentMillseconds(System.currentTimeMillis())
                    .setThemeColor(getResources().getColor(R.color.colorAccent))
                    .setType(Type.YEAR_MONTH)
                    .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                    .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                    .setWheelItemTextSize(16)
                    .build();
        mDialogAll.show(getSupportFragmentManager(), "timePick");
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millseconds);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;

        binding.actionBarTitle.setText("" + year + "年" + month + "月报表");

        binding.saleLineChart.clear();
        binding.categoryPieChart.clear();
        saleStatisticViewModel.clear();
        saleStatisticActionCreator.loadSaleStaticByMonth(year, month);
        saleStatisticActionCreator.loadSaleStaticInMonthByCategory(year, month);
    }
}
