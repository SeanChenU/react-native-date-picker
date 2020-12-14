package com.henninghall.date_picker;


import android.text.format.DateFormat;

public class Settings {
    public static boolean is24HourFormat = true;

    public void is24Hour(boolean data) {
      this.is24HourFormat = data;
    }

    public static boolean usesAmPm (){
      if(is24HourFormat) {
        return false;
      }
      else {
        return true;
      }
    }

}
