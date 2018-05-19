package com.henninghall.date_picker.wheels;

import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.WheelChangeListener;
import java.util.Calendar;
import java.util.Locale;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class HourWheel extends Wheel {

    public HourWheel(NumberPickerView p, WheelChangeListener listener, Locale locale){
        super(p, listener, locale);
    }

    @Override
    void init() {
        int numberOfHours = Utils.usesAmPm(locale) ? 12 : 24;
        Calendar cal = Calendar.getInstance();
        for(int i=0; i<numberOfHours; i++) {
            values.add(format.format(cal.getTime()));
            displayValues.add(format.format(cal.getTime()));
            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
        picker.setDisplayedValues(values.toArray(new String[0]),true);
        picker.setMinValue(0);
        picker.setMaxValue(numberOfHours - 1);

    }

    @Override
    public boolean visible() {
        return true;
    }

    @Override
    public String getFormatTemplate() {
        return Utils.usesAmPm(locale) ? "h": "HH";
    }

}