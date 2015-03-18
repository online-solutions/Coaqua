package com.success.coaqua.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.success.coaqua.R;
import com.success.coaqua.entity.Account;
import com.success.coaqua.helper.DatabaseHelper;
import com.success.coaqua.validate.TextValidator;
import com.success.coaqua.validate.Validation;
import com.success.element.DatePickerFragment;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class AccountActivity extends FragmentActivity implements
		OnDateSetListener {

	private static final String LOG = "ANDROID DEMO";
    private int MY_SCAN_REQUEST_CODE = 100; // arbitrary int
    final String TAG = getClass().getName();
    private boolean scanSuccess = false;

	private EditText etCardNumber;
	private EditText etExpireDate;
	private EditText etNameOnCard;
	private EditText etDigits;
    private ImageButton imgAccountChecked;
    private ImageButton getImgAccountClear;
	private Button btnCamera;


	private DatabaseHelper db;

	private Account acc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);

		// init
		etCardNumber = (EditText) findViewById(R.id.etCardNumber);
		etExpireDate = (EditText) findViewById(R.id.etExpireDate);
		etNameOnCard = (EditText) findViewById(R.id.etNameOnCard);
		etDigits = (EditText) findViewById(R.id.etDigits);
		imgAccountChecked = (ImageButton) findViewById(R.id.imgAccountChecked);
        getImgAccountClear = (ImageButton) findViewById(R.id.imgAccountClear);
        btnCamera = (Button) findViewById(R.id.btnCamera);

		// fake data for test only
		Toast.makeText(getApplicationContext(), "sample data", Toast.LENGTH_SHORT).show();
		etCardNumber.setText("5105105105105100");
		etExpireDate.setText("12/23");
		etNameOnCard.setText("COAQUA DEVELOPER");
		etDigits.setText("911");

		etExpireDate.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					// show date picker when user touch on edittext
					showDatePickerDialog(v);
					Toast.makeText(getApplicationContext(), "got the focus",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), "lost the focus",
							Toast.LENGTH_LONG).show();
				}
			}
		});

        getImgAccountClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // hide camera button
        btnCamera.setVisibility(View.INVISIBLE);

        // show camera when focus on etCardNumber
        // should show camera button when soft keyboard showed on etCardNumber,
        // but I don't know keyboard show/hide event
        etCardNumber.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    btnCamera.setVisibility(View.VISIBLE);
                } else {
                    btnCamera.setVisibility(View.GONE);
                }
            }
        });

        // on click back to hide  keyboard -> hide scan button

        // etCardNumber on click show btCamera
        etCardNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCamera.setVisibility(View.VISIBLE);
            }
        });

		// create validate
		validateAccount();
	}

    @Override
    protected void onResume() {
        super.onResume();

//        if (CardIOActivity.canReadCardWithCamera()) {
//            btnCamera.setText("Scan a credit card with card.io");
//        } else {
//            btnCamera.setText("Enter credit card information");
//        }
    }

	private void validateAccount() {
		// validate on key press
		etCardNumber.addTextChangedListener(new TextValidator(etCardNumber) {
			@Override
			public void validate(EditText editText, String text) {
				Validation.isNumber(editText, true);
			}
		});

		etExpireDate.addTextChangedListener(new TextValidator(etExpireDate) {
			@Override
			public void validate(EditText editText, String text) {
				Validation.isMonthYear(editText, true);
			}
		});

		etNameOnCard.addTextChangedListener(new TextValidator(etNameOnCard) {
			@Override
			public void validate(EditText editText, String text) {
				Validation.hasText(editText);
			}
		});

		etDigits.addTextChangedListener(new TextValidator(etDigits) {
			@Override
			public void validate(EditText editText, String text) {
				Validation.isCvn(editText, true);
			}
		});

		imgAccountChecked.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				/*
				 * Validation class will check the error and display the error
				 * on respective fields but it won't resist the form submission,
				 * so we need to check again before submit
				 */
				if (checkAccountValidation())
					submitAccountForm();
				else
					Toast.makeText(getApplicationContext(),
							"Please correct all fields", Toast.LENGTH_SHORT)
							.show();
			}
		});

	}

	private boolean checkAccountValidation() {
		boolean isValidForm = true;

		if (!Validation.isNumber(etCardNumber, true))
			isValidForm = false;
		if (!Validation.isMonthYear(etExpireDate, true))
			isValidForm = false;
		if (!Validation.hasText(etNameOnCard))
			isValidForm = false;
		if (!Validation.isCvn(etDigits, true))
			isValidForm = false;

		return isValidForm;
	}

	private void submitAccountForm() {
		// save to db or session
		db = new DatabaseHelper(getApplicationContext());
		acc = new Account(etCardNumber.getText().toString(), etExpireDate
				.getText().toString(), etNameOnCard.getText().toString(),
				etDigits.getText().toString());
		db.insertAccount(acc);
		
		Intent intent = new Intent(this, OrderActivity.class);
		startActivity(intent);
	}

	public void showDatePickerDialog(View v) {
		DatePickerFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");

	}

    public void onScanPress(View v) {
        // This method is set up as an onClick handler in the layout xml
        // e.g. android:onClick="onScanPress"

        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false); // default: true
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

        // hides the manual entry button
        // if set, developers should provide their own manual entry mechanism in the app
        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default: false

        // matches the theme of your application
        scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String resultStr;
        if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
            Log.d(LOG, scanResult.cardNumber);

            etCardNumber.setText(scanResult.cardNumber);
            scanSuccess = true;

            // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
            resultStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

            // Do something with the raw number, e.g.:
            // myService.setCardNumber( scanResult.cardNumber );

            if (scanResult.isExpiryValid()) {
                resultStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
            }

            if (scanResult.cvv != null) {
                // Never log or display a CVV
                resultStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
            }

            if (scanResult.postalCode != null) {
                resultStr += "Postal Code: " + scanResult.postalCode + "\n";
            }
        } else {
            resultStr = "Scan was canceled.";
            scanSuccess = false;
        }
//        resultTextView.setText(resultStr);
        Log.d(LOG, resultStr);

        // focus next
        if(scanSuccess){
            etExpireDate.requestFocus();

        } else {
            btnCamera.setVisibility(View.GONE);
        }

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// not use
	public void startOrderActivity(View view) {
		Intent intent = new Intent(this, OrderActivity.class);
		startActivity(intent);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// do some stuff for example write on log and update TextField on
		// activity

		Calendar cal = new GregorianCalendar(year, month, day);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");
		Log.d(LOG, "Date = " + formatter.format(cal.getTime()));

		// set value on datepicker to edittext
		((EditText) findViewById(R.id.etExpireDate)).setText(formatter
				.format(cal.getTime()));

		// focus on next element
		// etNameOnCard.requestFocus();
		// show keyboard
		// hold
	}
}
