package com.company.wanbei.app.util.widget;

import android.content.Context;
import android.widget.TextView;

import com.company.wanbei.app.R;
import com.company.wanbei.app.util.widget.bean.DateType;
import com.company.wanbei.app.util.widget.genview.WheelGeneralAdapter;
import com.company.wanbei.app.util.widget.view.WheelView1;

import java.util.Date;

/**
 * Created by codbking on 2016/8/10.
 */
class DatePicker1 extends BaseWheelPick {

    private static final String TAG = "WheelPicker";

    private WheelView1 yearView;
    private WheelView1 monthView;
    private WheelView1 dayView;
    private TextView weekView;
    private WheelView1 hourView;
    private WheelView1 minuteView;
    private WheelView1 apmView;

    private Integer[] yearArr, mothArr, dayArr, hourArr, minutArr,apmArr;
//    private String[] apmArr;
    private DatePickerHelper datePicker;

    public DateType type = DateType.TYPE_ALL;

    //开始时间
    private Date startDate = new Date();
    //年分限制，默认上下5年
    private int yearLimt = 5;

    private OnChangeLisener onChangeLisener;
    private int selectDay;

    //选择时间回调
    public void setOnChangeLisener(OnChangeLisener onChangeLisener) {
        this.onChangeLisener = onChangeLisener;
    }

    public DatePicker1(Context context, DateType type) {
        super(context);
        if(this.type!=null){
            this.type = type;
        }
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setYearLimt(int yearLimt) {
        this.yearLimt = yearLimt;
    }

    //初始化值
    public void init() {

        this.minuteView = (WheelView1) findViewById(R.id.minute);
        this.hourView = (WheelView1) findViewById(R.id.hour);
        this.weekView = (TextView) findViewById(R.id.week);
        this.dayView = (WheelView1) findViewById(R.id.day);
        this.monthView = (WheelView1) findViewById(R.id.month);
        this.yearView = (WheelView1) findViewById(R.id.year);
        this.apmView = (WheelView1) findViewById(R.id.apm);

        switch (type) {
            case TYPE_ALL:
                this.apmView.setVisibility(GONE);
                this.minuteView.setVisibility(VISIBLE);
                this.hourView.setVisibility(VISIBLE);
                this.weekView.setVisibility(VISIBLE);
                this.dayView.setVisibility(VISIBLE);
                this.monthView.setVisibility(VISIBLE);
                this.yearView.setVisibility(VISIBLE);
                break;
            case TYPE_YMDHM:
                this.apmView.setVisibility(GONE);
                this.minuteView.setVisibility(VISIBLE);
                this.hourView.setVisibility(VISIBLE);
                this.weekView.setVisibility(GONE);
                this.dayView.setVisibility(VISIBLE);
                this.monthView.setVisibility(VISIBLE);
                this.yearView.setVisibility(VISIBLE);
                break;
            case TYPE_YMDH:
                this.apmView.setVisibility(GONE);
                this.minuteView.setVisibility(GONE);
                this.hourView.setVisibility(VISIBLE);
                this.weekView.setVisibility(GONE);
                this.dayView.setVisibility(VISIBLE);
                this.monthView.setVisibility(VISIBLE);
                this.yearView.setVisibility(VISIBLE);
                break;
            case TYPE_YMD:
                this.apmView.setVisibility(GONE);
                this.minuteView.setVisibility(GONE);
                this.hourView.setVisibility(GONE);
                this.weekView.setVisibility(GONE);
                this.dayView.setVisibility(VISIBLE);
                this.monthView.setVisibility(VISIBLE);
                this.yearView.setVisibility(VISIBLE);
                break;
            case TYPE_YM:
                this.apmView.setVisibility(GONE);
                this.minuteView.setVisibility(GONE);
                this.hourView.setVisibility(GONE);
                this.weekView.setVisibility(GONE);
                this.dayView.setVisibility(GONE);
                this.monthView.setVisibility(VISIBLE);
                this.yearView.setVisibility(VISIBLE);
                break;
            case TYPE_HM:
                this.apmView.setVisibility(GONE);
                this.minuteView.setVisibility(VISIBLE);
                this.hourView.setVisibility(VISIBLE);
                this.weekView.setVisibility(GONE);
                this.dayView.setVisibility(GONE);
                this.monthView.setVisibility(GONE);
                this.yearView.setVisibility(GONE);
                break;
            case TYPE_YMDA:
                this.apmView.setVisibility(VISIBLE);
                this.minuteView.setVisibility(GONE);
                this.hourView.setVisibility(GONE);
                this.weekView.setVisibility(GONE);
                this.dayView.setVisibility(VISIBLE);
                this.monthView.setVisibility(VISIBLE);
                this.yearView.setVisibility(VISIBLE);
                break;
        }

        datePicker = new DatePickerHelper();
        datePicker.setStartDate(startDate, yearLimt);

        dayArr = datePicker.genDay();
        yearArr = datePicker.genYear();
        mothArr = datePicker.genMonth();
        hourArr = datePicker.genHour();
        minutArr = datePicker.genMinut();

        apmArr = new Integer[]{1,2};

        weekView.setText(datePicker.getDisplayStartWeek());

        setWheelListener(yearView, yearArr, false);
        setWheelListener(monthView, mothArr, true);
        setWheelListener(dayView, dayArr, true);
        setWheelListener(hourView, hourArr, true);
        setWheelListener(minuteView, minutArr, true);

        setWheelListener(apmView, apmArr, true);

        yearView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(DatePickerHelper.Type.YEAR), yearArr));
        monthView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(DatePickerHelper.Type.MOTH), mothArr));
        dayView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(DatePickerHelper.Type.DAY), dayArr));
        hourView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(DatePickerHelper.Type.HOUR), hourArr));
        minuteView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(DatePickerHelper.Type.MINUTE), minutArr));

        apmView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(DatePickerHelper.Type.APM), apmArr));

    }


    protected String[] convertData(WheelView1 wheelView1, Integer[] data) {
        if (wheelView1 == yearView) {
            return datePicker.getDisplayValue(data, "年");
        } else if (wheelView1 == monthView) {
            return datePicker.getDisplayValue(data, "月");
        } else if (wheelView1 == dayView) {
            return datePicker.getDisplayValue(data, "日");
        } else if (wheelView1 == hourView) {
            return datePicker.getDisplayValue(data, "");
        } else if (wheelView1 == minuteView) {
            return datePicker.getDisplayValue(data, "");
        } else if (wheelView1 == apmView) {
            return datePicker.getDisplayValue(data, "");
        }
        return new String[0];
    }

    @Override
    protected int getLayout() {
        return R.layout.cbk;
    }

    @Override
    protected int getItemHeight() {
        return dayView.getItemHeight();
    }


    @Override
    protected void setData(Object[] datas) {
    }

    private void setChangeDaySelect(int year, int moth) {
        dayArr = datePicker.genDay(year, moth);
        WheelGeneralAdapter adapter= (WheelGeneralAdapter) dayView.getViewAdapter();
        adapter.setData(convertData(dayView,  dayArr));

        int indxt = datePicker.findIndextByValue(selectDay, dayArr);
        if (indxt == -1) {
            dayView.setCurrentItem(0);
        } else {
            dayView.setCurrentItem(indxt);
        }
    }

    @Override
    public void onChanged(WheelView1 wheel, int oldValue, int newValue) {

        int year = yearArr[yearView.getCurrentItem()];
        int moth = mothArr[monthView.getCurrentItem()];
        int day = dayArr[dayView.getCurrentItem()];
        int hour = hourArr[hourView.getCurrentItem()];
        int minut = minutArr[minuteView.getCurrentItem()];
        int apm = apmArr[apmView.getCurrentItem()];

        if (wheel == yearView || wheel == monthView) {
            setChangeDaySelect(year, moth);
        } else {
            selectDay = day;
        }

        if (wheel == yearView || wheel == monthView || wheel == dayView) {
            weekView.setText(datePicker.getDisplayWeek(year, moth, day));
        }

        if (onChangeLisener != null) {
            onChangeLisener.onChanged(DateUtils.getDate(year, moth, day, hour, minut));
        }

    }

    @Override
    public void onScrollingStarted(WheelView1 wheel) {
    }

    @Override
    public void onScrollingFinished(WheelView1 wheel) {
    }


    //获取选中日期
    public Date getSelectDate() {

        int year = yearArr[yearView.getCurrentItem()];
        int moth = mothArr[monthView.getCurrentItem()];
        int day = dayArr[dayView.getCurrentItem()];
        int hour = hourArr[hourView.getCurrentItem()];
        int minut = minutArr[minuteView.getCurrentItem()];

        return DateUtils.getDate(year, moth, day, hour, minut);

    }



}
