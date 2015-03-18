package com.success.element;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

// extend DatePickerDialog to modify title
public class MyDatePickerDialog extends DatePickerDialog {
	
	private static final String LOG = "ANROID DEMO";

//	private CharSequence title;

    public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
    }

    // permanently title on datePicker
//    public void setPermanentTitle(CharSequence title) {
//        this.title = title;
//        setTitle(title);
//    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        
        // change datepicker's title to value in datepicker
        Log.d(LOG,"month = " + (month + 1) + "year= "+ year);
        setTitle((month + 1) + "/" + year);
        
        // set disable date < current
        Calendar cal = Calendar.getInstance();
        int myear = cal.get(Calendar.YEAR);
        int mmonth = cal.get(Calendar.MONTH) + 1;
        int mday = cal.get(Calendar.DAY_OF_MONTH);
        if(year < myear)
        	view.updateDate(myear, month, mday);
        if (month < mmonth && year == myear)
            view.updateDate(myear, mmonth, mday);
    }

}
