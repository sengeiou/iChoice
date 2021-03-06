package com.choicemmed.ichoice.healthcheck.fragment.temperature;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.choicemmed.common.DateUtils;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.common.SharePreferenceUtil;
import com.choicemmed.common.StringUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.activity.CalenderActivity;
import com.choicemmed.ichoice.healthcheck.custom.WeekFormatter;
import com.choicemmed.ichoice.healthcheck.db.TemperatureOperation;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import pro.choicemmed.datalib.CFT308Data;

import static com.choicemmed.common.FormatUtils.template_Date;
import static com.choicemmed.common.FormatUtils.template_DbDateTime;


public class TemperatureWeekFragment extends BaseFragment {

    private static final String TAG = TemperatureWeekFragment.class.getSimpleName();
    @BindView(R.id.temperature_chart)
    LineChart temperature_chart;
    @BindView(R.id.tv_year)
    TextView tv_year;
    @BindView(R.id.tv_month)
    TextView tv_month;
    @BindView(R.id.tv_day)
    TextView tv_day;
    @BindView(R.id.tv_unit)
    TextView tv_unit;

    @BindView(R.id.calendar_left)
    ImageView calendarLeft;
    @BindView(R.id.calendar_right)
    ImageView calendarRight;

    @BindView(R.id.tv_time)
    TextView times;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    Calendar beginCalendar;
    private Calendar calendar = Calendar.getInstance();
    private static String beginDate;
    private Calendar endCalendar = Calendar.getInstance();
    int xValueMax;
    private int unit;
    TemperatureOperation temperatureOperation;
    private int spo2, PR, RR;
    Map<String, CFT308Data> cft308DataHashMapC = new HashMap<>();
    Map<Integer, CFT308Data> cft308DataMap = new HashMap<>();
    List<Entry> entriesUnitC = new ArrayList<>();
    List<Entry> entriesUnitF = new ArrayList<>();
    private CFT308Data currentSelectData;

    String[] weeks;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                int year = intent.getIntExtra("year", CalendarDay.today().getYear());
                int month = intent.getIntExtra("month", CalendarDay.today().getMonth());
                int day = intent.getIntExtra("day", CalendarDay.today().getDay());
                calendar.set(year, month, day);
                setTextDate();
                CalendarMoveChart(calendar);
                calculationCurrentDayLastValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected int contentViewID() {
        return R.layout.fragment_temperature_day_trend;
    }

    @Override
    protected void initialize() {
        initData();
        initChart();
        initReceiver();
        setChartData();
        setTextDate();
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.d(TAG, "onStart");
        if (unit != (int) SharePreferenceUtil.get(getActivity(), "temp_unit", 1)) {
            unit = (int) SharePreferenceUtil.get(getActivity(), "temp_unit", 1);
            calculationYValue();
            if (currentSelectData != null) {
                if (unit == 1) {
                    tv1.setText(currentSelectData.getTemp().substring(0, currentSelectData.getTemp().length() - 2));
                    tv2.setText(currentSelectData.getTemp().substring(currentSelectData.getTemp().length() - 2));
                } else {
                    String f = String.valueOf((Float.parseFloat(currentSelectData.getTemp()) * 9 / 5) + 32);
                    DecimalFormat df = new DecimalFormat("#.0");
                    df.setRoundingMode(RoundingMode.DOWN);
                    f = df.format(Float.parseFloat(f));
                    if (!StringUtils.isEmpty(f)) {
                        tv1.setText(f.substring(0, f.length() - 2));
                        tv2.setText(f.substring(f.length() - 2));
                    }
                }
            }
        }
    }

    private void calculationYValue() {
        if (unit == 1) {
            tv3.setText(getString(R.string.temp_unit));
            tv_unit.setText(getString(R.string.temp_unit));
            YAxis leftAxis = temperature_chart.getAxisLeft();
            leftAxis.setAxisMaximum(43);
            leftAxis.setAxisMinimum(32);
            leftAxis.setLabelCount(5);
            YAxis rightAxis = temperature_chart.getAxisRight();
            rightAxis.setAxisMaximum(43);
            rightAxis.setAxisMinimum(32);
            rightAxis.setLabelCount(5);
        } else {
            tv3.setText(getString(R.string.temp_unit1));
            tv_unit.setText(getString(R.string.temp_unit1));
            YAxis leftAxis = temperature_chart.getAxisLeft();
            leftAxis.setAxisMaximum(110);
            leftAxis.setAxisMinimum(85);
            leftAxis.setLabelCount(5);
            YAxis rightAxis = temperature_chart.getAxisRight();
            rightAxis.setAxisMaximum(110);
            rightAxis.setAxisMinimum(85);
            rightAxis.setLabelCount(5);
        }
        LineDataSet dataSet;
        if (temperature_chart.getData() != null && temperature_chart.getData().getDataSetCount() > 0) {
            dataSet = (LineDataSet) temperature_chart.getData().getDataSetByIndex(0);
            if (unit == 1) {
                dataSet.setValues(entriesUnitC);
            } else {
                dataSet.setValues(entriesUnitF);
            }
            temperature_chart.getData().notifyDataChanged();
            temperature_chart.notifyDataSetChanged();
        } else {
            if (unit == 1) {
                dataSet = new LineDataSet(entriesUnitC, "");
            } else {
                dataSet = new LineDataSet(entriesUnitF, "");
            }
            int colorId = getActivity().getResources().getColor(R.color.blue_8c9eff);
            dataSet.setDrawCircleHole(false);
            dataSet.setColor(colorId);
            dataSet.setCircleColor(colorId);
            dataSet.setLineWidth(1f);
            dataSet.setCircleSize(3f);
            LineData spo2Data = new LineData(dataSet);
            temperature_chart.setData(spo2Data);
        }
        temperature_chart.invalidate();
        CalendarMoveChart(calendar);
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.d(TAG, "onStop");
    }

    /**
     * 初始化日期广播
     */
    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("CalenderSelect");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }


    private void setTextDate() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(calendar.getTimeInMillis());
        tv_year.setText(calendar1.get(Calendar.YEAR) + "");
        int day = calendar1.get(Calendar.DAY_OF_WEEK);
        calendar1.add(Calendar.DATE, day == Calendar.SUNDAY ? 0 : -(day - Calendar.SUNDAY));
        tv_month.setText(calendar1.get(Calendar.MONTH) + 1 + "");
        int sun = calendar1.get(Calendar.DAY_OF_MONTH);
        calendar1.add(Calendar.DATE, 6);
        int sat = calendar1.get(Calendar.DAY_OF_MONTH);
        tv_day.setText(sun + "-" + sat);
        changeArrowImage(calendar);
    }

    public void changeArrowImage(Calendar calendar) {
        Calendar beginCalendar = DateUtils.strToCalendar(beginDate);
        if (isWeek(calendar, beginCalendar)) {
            calendarLeft.setImageResource(R.mipmap.left_gay);
        } else {
            calendarLeft.setImageResource(R.mipmap.calendar_left);
        }
        if (isWeek(calendar, Calendar.getInstance())) {
            calendarRight.setImageResource(R.mipmap.right_gay);
        } else {
            calendarRight.setImageResource(R.mipmap.arrow_right);
        }

    }

    private boolean isWeek(Calendar cal1, Calendar cal2) {
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (subYear == 0) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }

        } else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        } else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        }
        return false;
    }


    @OnClick({R.id.calendar_left, R.id.calendar_right, R.id.calender_select})
    public void onClick(View view) {
        Calendar beginCalendar = DateUtils.strToCalendar(beginDate);
        switch (view.getId()) {
            case R.id.calendar_left:
                if (calendar.before(beginCalendar) || isWeek(calendar, beginCalendar)) {
                    return;
                }
                calendar.add(Calendar.DATE, -7);
                setTextDate();
                CalendarMoveChart(calendar);
                calculationCurrentDayLastValue();
                break;
            case R.id.calendar_right:
                if (calendar.after(Calendar.getInstance()) || DateUtils.isToday(calendar)) {
                    return;
                }
                calendar.add(Calendar.DATE, 7);
                setTextDate();
                CalendarMoveChart(calendar);
                calculationCurrentDayLastValue();
                break;
            case R.id.calender_select:
                Bundle bundle = new Bundle();
                Calendar calendar1 = Calendar.getInstance();
                String current_date;
                if (calendar.getTimeInMillis() > calendar1.getTimeInMillis()) {
                    current_date = FormatUtils.getDateTimeString1(calendar1.getTimeInMillis(), FormatUtils.template_DbDateTime);
                } else {
                    current_date = FormatUtils.getDateTimeString1(calendar.getTimeInMillis(), FormatUtils.template_DbDateTime);
                }
                bundle.putInt("type", DevicesType.Thermometer);
                bundle.putString("current_date", current_date);
                startActivity(CalenderActivity.class, bundle);
                getActivity().overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                break;
            default:
                break;

        }
    }

    /**
     * 日期变化
     *
     * @param calendar1
     */
    private void CalendarMoveChart(Calendar calendar1) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar1.getTimeInMillis());
        int beginday = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, -(beginday - Calendar.SUNDAY));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Log.d(TAG, "CalendarMoveChart    " + FormatUtils.getDateTimeString(calendar.getTimeInMillis(), FormatUtils.template_DbDateTime));
        long nh = 1000 * 60 * 60 * 24;//一天的毫秒数
        int startX = (int) ((calendar.getTimeInMillis() - beginCalendar.getTimeInMillis()) / nh);//计算差多少天
        temperature_chart.moveViewToX(startX);
    }

    public void initData() {
        unit = (int) SharePreferenceUtil.get(getActivity(), "temp_unit", 1);
        temperatureOperation = new TemperatureOperation(getContext());
        weeks = new String[]{getString(R.string.sum), getString(R.string.mon), getString(R.string.tue), getString(R.string.wed), getString(R.string.thu), getString(R.string.fri), getString(R.string.sat)};
        beginDate = FormatUtils.getDateTimeString(Long.parseLong(IchoiceApplication.getAppData().userProfileInfo.getSignupDateTime().substring(6, IchoiceApplication.getAppData().userProfileInfo.getSignupDateTime().length() - 2)), template_DbDateTime);
        beginCalendar = DateUtils.strToCalendar(beginDate);
        beginCalendar.add(Calendar.DATE, Calendar.SUNDAY - beginCalendar.get(Calendar.DAY_OF_WEEK));
        beginCalendar.set(Calendar.HOUR_OF_DAY, 0);
        beginCalendar.set(Calendar.MINUTE, 0);
        beginCalendar.set(Calendar.SECOND, 0);
        endCalendar.add(Calendar.DATE, Calendar.SATURDAY + 1 - Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        xValueMax = DateUtils.differentWeekCalendar(beginCalendar, Calendar.getInstance()) * 7;
    }

    /**
     * 初始化Spo2折线图
     */
    private void initChart() {
        temperature_chart.setBackgroundColor(Color.WHITE);
        temperature_chart.setDrawGridBackground(true);
        temperature_chart.setDrawBorders(false);
        temperature_chart.setGridBackgroundColor(Color.WHITE);
        temperature_chart.getDescription().setEnabled(false);
        temperature_chart.setScaleEnabled(false);
        temperature_chart.setTouchEnabled(true);
        temperature_chart.getAxisRight().setEnabled(false);
        temperature_chart.setDragXEnabled(true);
        temperature_chart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewPortHandler handler = temperature_chart.getViewPortHandler();
                MPPointD bottomRight = temperature_chart.getValuesByTouchPoint(handler.contentRight(), handler.contentBottom(), YAxis.AxisDependency.LEFT);
                LogUtils.d(TAG, " topLeft.x  " + bottomRight.x);
                calendar.setTimeInMillis((long) ((bottomRight.x - 0.001) * 1000 * 60 * 60 * 24 + beginCalendar.getTimeInMillis()));
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                setTextDate();
                calculationCurrentDayLastValue();
                return false;
            }
        });


        temperature_chart.setOnChartValueSelectedListener(valueSelectedListener);

        XAxis xAxis = temperature_chart.getXAxis();
        xAxis.setTextSize(12f);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(xValueMax);
        WeekFormatter weekFormatter = new WeekFormatter();
        weekFormatter.setWeeks(weeks);
        xAxis.setValueFormatter(weekFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        temperature_chart.setVisibleXRange(0, 7);
        xAxis.setLabelCount(7);

        Description description = new Description();
        description.setText("(h)");
        temperature_chart.setDescription(description);
        Legend legend = temperature_chart.getLegend();
        legend.setEnabled(false);

        //90~100 间隔2
        YAxis leftAxis = temperature_chart.getAxisLeft();
        leftAxis.setAxisMaximum(43);
        leftAxis.setAxisMinimum(32);
        leftAxis.setLabelCount(5);
        YAxis rightAxis = temperature_chart.getAxisRight();
        rightAxis.setAxisMaximum(43);
        rightAxis.setAxisMinimum(32);
        rightAxis.setLabelCount(5);
    }

    private void setChartData() {
        List<CFT308Data> cft308DataList = temperatureOperation.queryAVGDataByDay(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (!cft308DataList.isEmpty()) {
            for (int i = 0; i < cft308DataList.size(); i++) {
                long nh = 1000 * 60 * 60 * 24;//一天的毫秒数

                String startTime = cft308DataList.get(i).getMeasureTime();
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(FormatUtils.parseDate(startTime, template_DbDateTime));
                int startX = (int) ((calendar1.getTimeInMillis() - beginCalendar.getTimeInMillis()) / (nh));//计算差多少天
                LogUtils.d(TAG, "startX" + startX + "  getTemp  " + cft308DataList.get(i).getTemp());
                entriesUnitC.add(new Entry(startX, Float.parseFloat(cft308DataList.get(i).getTemp())));
                cft308DataMap.put(startX, cft308DataList.get(i));
                String f = String.valueOf((Float.parseFloat(cft308DataList.get(i).getTemp()) * 9 / 5) + 32);
                DecimalFormat df = new DecimalFormat("#.0");
                df.setRoundingMode(RoundingMode.DOWN);
                f = df.format(Float.parseFloat(f));
                entriesUnitF.add(new Entry(startX, Float.parseFloat(f)));
                String everyDayLastMeasureTime = FormatUtils.getDateTimeString1(calendar1.getTimeInMillis(), template_Date);
                cft308DataHashMapC.put(everyDayLastMeasureTime, cft308DataList.get(i));
                LogUtils.d(TAG, "everyDayLastMeasureTime   " + everyDayLastMeasureTime);


            }
        }
        calculationYValue();
        calculationCurrentDayLastValue();
    }

    private void calculationCurrentDayLastValue() {
        if (cft308DataHashMapC.size() > 0) {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTimeInMillis(calendar.getTimeInMillis());
            int day = calendar1.get(Calendar.DAY_OF_WEEK);
            calendar1.add(Calendar.DATE, -(day - Calendar.SUNDAY));
            boolean hasData = false;
            for (int i = 0; i < 7; i++) {
                String everyDayLastMeasureTime = FormatUtils.getDateTimeString1(calendar1.getTimeInMillis(), template_Date);
                CFT308Data cft308Data = cft308DataHashMapC.get(everyDayLastMeasureTime);
                LogUtils.d(TAG, "everyDayLastMeasureTime   " + everyDayLastMeasureTime);
                if (cft308Data != null) {
                    hasData = true;
                    currentSelectData = cft308Data;
                    LogUtils.d(TAG, "cft308Data  " + cft308Data.toString());
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(FormatUtils.parseDate(cft308Data.getMeasureTime(), template_DbDateTime));
                    if (unit == 1) {
                        tv1.setText(cft308Data.getTemp().substring(0, cft308Data.getTemp().length() - 2));
                        tv2.setText(cft308Data.getTemp().substring(cft308Data.getTemp().length() - 2));
                    } else {
                        String f = String.valueOf((Float.parseFloat(cft308Data.getTemp()) * 9 / 5) + 32);
                        DecimalFormat df = new DecimalFormat("#.0");
                        df.setRoundingMode(RoundingMode.DOWN);
                        f = df.format(Float.parseFloat(f));
                        if (!StringUtils.isEmpty(f)) {
                            tv1.setText(f.substring(0, f.length() - 2));
                            tv2.setText(f.substring(f.length() - 2));
                        }
                    }
                    times.setText(weeks[calendar2.get(Calendar.DAY_OF_WEEK) - 1]);
                    tv3.setVisibility(View.VISIBLE);
                }
                calendar1.add(Calendar.DATE, 1);

            }
            if (!hasData) {
                currentSelectData = null;
                tv1.setText("");
                tv2.setText("--");
                times.setText("--");
            }
        }
    }

    private OnChartValueSelectedListener valueSelectedListener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
            try {
                float x = e.getX();
                CFT308Data cft308Data = cft308DataMap.get((int) x);
                currentSelectData = cft308Data;
                LogUtils.d(TAG, "cft308Data " + cft308Data.toString());
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(FormatUtils.parseDate(cft308Data.getMeasureTime(), template_DbDateTime));
                times.setText(weeks[calendar1.get(Calendar.DAY_OF_WEEK) - 1]);
                if (unit == 1) {
                    tv1.setText(cft308Data.getTemp().substring(0, cft308Data.getTemp().length() - 2));
                    tv2.setText(cft308Data.getTemp().substring(cft308Data.getTemp().length() - 2));
                } else {
                    String f = String.valueOf((Float.parseFloat(cft308Data.getTemp()) * 9 / 5) + 32);
                    DecimalFormat df = new DecimalFormat("#.0");
                    df.setRoundingMode(RoundingMode.DOWN);
                    f = df.format(Float.parseFloat(f));
                    if (!StringUtils.isEmpty(f)) {
                        tv1.setText(f.substring(0, f.length() - 2));
                        tv2.setText(f.substring(f.length() - 2));
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected() {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}
