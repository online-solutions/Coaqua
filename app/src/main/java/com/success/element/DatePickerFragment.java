package com.success.element;

import java.util.Calendar;

import com.success.coaqua.activities.AccountActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;

public class DatePickerFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it

		DatePickerDialog dpd = new MyDatePickerDialog(getActivity(),
				(AccountActivity) getActivity(), year, month, day);
		((ViewGroup) dpd.getDatePicker()).findViewById(
				Resources.getSystem().getIdentifier("day", "id", "android"))
				.setVisibility(View.GONE);
		dpd.setTitle("Please select Expire date");
		return dpd;
	}

}