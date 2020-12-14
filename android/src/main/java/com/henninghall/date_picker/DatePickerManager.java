package com.henninghall.date_picker;


import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.henninghall.date_picker.props.DividerHeightProp;
import com.henninghall.date_picker.props.Is24hourSourceProp;
import com.henninghall.date_picker.props.VariantProp;
import com.henninghall.date_picker.props.DateProp;
import com.henninghall.date_picker.props.FadeToColorProp;
import com.henninghall.date_picker.props.LocaleProp;
import com.henninghall.date_picker.props.MaximumDateProp;
import com.henninghall.date_picker.props.MinimumDateProp;
import com.henninghall.date_picker.props.MinuteIntervalProp;
import com.henninghall.date_picker.props.ModeProp;
import com.henninghall.date_picker.props.TextColorProp;
import com.henninghall.date_picker.props.UtcProp;

import net.time4j.android.ApplicationStarter;

import java.lang.reflect.Method;
import java.util.Map;

public class DatePickerManager extends SimpleViewManager<PickerView>  {

  private static final String REACT_CLASS = "DatePickerManager";
  private static final int SCROLL = 1;
  public static ThemedReactContext context;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public PickerView createViewInstance(ThemedReactContext reactContext) {
    DatePickerManager.context = reactContext;
    ApplicationStarter.initialize(reactContext, false); // false = no need to prefetch on time data background tread
    return new PickerView();
  }

  @ReactProp(name = "mode")
  public void setMode(PickerView view, String mode) {
    Mode m;
    try {
      m = Mode.valueOf(mode);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid mode. Valid modes: 'datetime', 'date', 'time'");
    }
    view.setMode(m);
  }

  @ReactProp(name = "date")
  public void setDate(PickerView view, String date) {
    this.date = date;
  }

  @ReactProp(name = "locale")
  public void setLocale(PickerView view, String locale) {
    view.setLocale(LocaleUtils.toLocale(locale.replace('-','_')));
  }

  @ReactProp(name = "minimumDate")
  public void setMinimumDate(PickerView view, String date) {
    view.setMinimumDate(date);
  }

  @ReactProp(name = "is24Hour")
  public void is24Hour(PickerView view, boolean date) {
    view.is24Hour(date);
  }

  @ReactProp(name = "maximumDate")
  public void setMaximumDate(PickerView view, String date) {
    view.setMaximumDate(date);
  }

  @ReactProp(name = "fadeToColor")
  public void setFadeToColor(PickerView view, String color) {
    view.style.setFadeToColor(color);
  }

  @ReactPropGroup(names = { DateProp.name, ModeProp.name, LocaleProp.name, MaximumDateProp.name,
          MinimumDateProp.name, FadeToColorProp.name, TextColorProp.name, UtcProp.name, MinuteIntervalProp.name,
          VariantProp.name, DividerHeightProp.name, Is24hourSourceProp.name
  })
  public void setProps(PickerView view, int index, Dynamic value) {
    updateProp("setProps", view, index, value);
  }

  @ReactPropGroup(names = {"height"}, customType = "Style")
  public void setStyle(PickerView view, int index, Dynamic value) {
    updateProp("setStyle", view, index, value);
  }

  @Override
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("scroll", SCROLL);
  }

  @Override
  protected void onAfterUpdateTransaction(PickerView pickerView) {
   super.onAfterUpdateTransaction(pickerView);
     try{
       pickerView.update();
     }
     catch (Exception e){
       e.printStackTrace();
     }
  }

  public void receiveCommand(final PickerView view, int command, final ReadableArray args) {
    if (command == SCROLL) {
      int wheelIndex = args.getInt(0);
      int scrollTimes = args.getInt(1);
      view.scroll(wheelIndex, scrollTimes);
    }
  }

  public Map getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.builder()
            .put("dateChange", MapBuilder.of("phasedRegistrationNames",
                    MapBuilder.of("bubbled", "onChange")
                    )
            ).build();
  }

  private void updateProp(String methodName, PickerView view, int index, Dynamic value){
    String[] propNames = getMethodAnnotation(methodName).names();
    String propName = propNames[index];
    view.updateProp(propName, value);
  }

  private ReactPropGroup getMethodAnnotation(String methodName) {
    Method[] methods = this.getClass().getMethods();
    Method method = null;
    for (Method m : methods) {
      if (m.getName().equals(methodName))
        method = m;
    }
    return method.getAnnotation(ReactPropGroup.class);
  }

}


