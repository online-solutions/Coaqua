package com.success.coaqua.activities;

import java.util.Arrays;

import com.facebook.AppEventsLogger;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.success.coaqua.R;
import com.success.coaqua.entity.Customer;
import com.success.coaqua.helper.DatabaseHelper;
import com.success.coaqua.validate.TextValidator;
import com.success.coaqua.validate.Validation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SignUpActivity extends Activity {

	// Logcat tag
	private static final String LOG = "ANDROID DEMO";

	private LoginButton loginButton;
	private GraphUser user;

	private UiLifecycleHelper uiHelper;
	Customer customer;
	private long customer_id;

	// Database Helper
	DatabaseHelper db;
	
	private EditText etFirstName;
	private EditText etLastName;
	private EditText etEmail;
	private EditText etAddress;
	private EditText etPhone;
	private ImageButton imgSignUpChecked;
    private ImageButton imgSignUpClear;

	

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
		@Override
		public void onError(FacebookDialog.PendingCall pendingCall,
				Exception error, Bundle data) {
			Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
		}

		@Override
		public void onComplete(FacebookDialog.PendingCall pendingCall,
				Bundle data) {
			Log.d("HelloFacebook", "Success!");
		}
	};

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (exception instanceof FacebookOperationCanceledException
				|| exception instanceof FacebookAuthorizationException) {
			new AlertDialog.Builder(SignUpActivity.this)
					.setTitle(R.string.cancelled)
					.setMessage(R.string.permission_not_granted)
					.setPositiveButton(R.string.ok, null).show();
		}
		// updateUI();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

        // init
		etFirstName = (EditText) findViewById(R.id.etFirstName);
		etLastName = (EditText) findViewById(R.id.etLastName);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etAddress = (EditText) findViewById(R.id.etAddress);
		etPhone = (EditText) findViewById(R.id.etPhone);
		
		imgSignUpChecked = (ImageButton) findViewById(R.id.imgSignUpChecked);
        imgSignUpClear = (ImageButton) findViewById(R.id.imgSignUpClear);

		loginButton = (LoginButton) findViewById(R.id.login_button);
		loginButton.setReadPermissions(Arrays.asList("email"));
		loginButton
				.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
					@Override
					public void onUserInfoFetched(GraphUser user) {
						SignUpActivity.this.user = user;
						if (user != null) {
							Log.e(LOG, "First Name" + user.getFirstName());
							Log.d(LOG, "Last Name" + user.getLastName());

							Log.d(LOG, user.getProperty("email").toString());
							etFirstName.setText(user.getFirstName());
							etLastName.setText(user.getLastName());
							etEmail.setText(user.getProperty("email")
									.toString());
						} else {
							Log.e(LOG, "facebook user not exist");
						}
						// It's possible that we were waiting for this.user to
						// be populated in order to post a
						// status update.

					}
				});

        imgSignUpClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

		db = new DatabaseHelper(getApplicationContext());

		customer = db.getCustomer();

		if (customer != null) {
			etFirstName.setText(customer.getFirstName());
			etLastName.setText(customer.getLastName());
			etEmail.setText(customer.getEmail());
			etAddress.setText(customer.getAddress());
			etPhone.setText(customer.getPhoneNumber());
		}

		validateSignUp();
	}

	// not use, moved to submitForm()
	public void startSignUpSuccessActivity(View view) {
		// logout facebook
		callFacebookLogout(getApplicationContext());

		db = new DatabaseHelper(getApplicationContext());

		// set Customer Information
		Customer tempCustomer = new Customer();
		etFirstName = (EditText) findViewById(R.id.etFirstName);
		tempCustomer.setFirstName(etFirstName.getText().toString());
		
		etLastName = (EditText) findViewById(R.id.etLastName);
		tempCustomer.setLastName(etLastName.getText().toString());

		etEmail = (EditText) findViewById(R.id.etEmail);
		tempCustomer.setEmail(etEmail.getText().toString());

		etAddress = (EditText) findViewById(R.id.etAddress);
		tempCustomer.setAddress(etAddress.getText().toString());

		etPhone = (EditText) findViewById(R.id.etPhone);
		tempCustomer.setPhoneNumber(etPhone.getText().toString());

		// insert or update customer
		if (customer != null) {
			tempCustomer.setId(customer.getId());
			customer_id = db.updateCustomer(tempCustomer);
		} else {
			customer_id = db.createCustomer(tempCustomer);
		}

		// open Sign Up Activity
		Intent intent = new Intent(this, SignUpSuccessActivity.class);
		startActivity(intent);
	}

	private void validateSignUp() {
		etFirstName.addTextChangedListener(new TextValidator(etFirstName) {
			@Override
			public void validate(EditText editText, String text) {
				Validation.hasText(editText);
			}
		});
		
		etLastName.addTextChangedListener(new TextValidator(etLastName) {
			@Override
			public void validate(EditText editText, String text) {
				Validation.hasText(editText);
			}
		});

		etEmail.addTextChangedListener(new TextValidator(etEmail) {
			@Override
			public void validate(EditText editText, String text) {
				Validation.isEmailAddress(editText, true);
			}
		});

		etAddress.addTextChangedListener(new TextValidator(etAddress) {
			@Override
			public void validate(EditText editText, String text) {
				Validation.hasText(editText);
			}
		});

		etPhone.addTextChangedListener(new TextValidator(etPhone) {
			@Override
			public void validate(EditText editText, String text) {
				Validation.isPhoneNumber(editText, true);
			}
		});
		
		imgSignUpChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Validation class will check the error and display the error on respective fields
                but it won't resist the form submission, so we need to check again before submit
                 */
                if ( checkSignUpValidation() )
                    submitSignUpForm();
                else
                    Toast.makeText(getApplicationContext(), "Please correct all fields", Toast.LENGTH_SHORT).show();
            }
        });
		
	}
	
	private void submitSignUpForm() {
		// logout facebook
		callFacebookLogout(getApplicationContext());

		db = new DatabaseHelper(getApplicationContext());

		// set Customer Information
		Customer tempCustomer = new Customer();
		tempCustomer.setFirstName(etFirstName.getText().toString());

		tempCustomer.setLastName(etLastName.getText().toString());

		tempCustomer.setEmail(etEmail.getText().toString());

		tempCustomer.setAddress(etAddress.getText().toString());

		tempCustomer.setPhoneNumber(etPhone.getText().toString());

		// insert or update customer
		if (customer != null) {
			tempCustomer.setId(customer.getId());
			customer_id = db.updateCustomer(tempCustomer);
		} else {
			customer_id = db.createCustomer(tempCustomer);
		}

		// open Sign Up Activity
		Intent intent = new Intent(this, SignUpSuccessActivity.class);
		startActivity(intent);
    }
 
    private boolean checkSignUpValidation() {
        boolean ret = true;
 
        if (!Validation.hasText(etFirstName)) ret = false;
        if (!Validation.hasText(etLastName)) ret = false;
        if (!Validation.hasText(etAddress)) ret = false;
        if (!Validation.isEmailAddress(etEmail, true)) ret = false;
        if (!Validation.isPhoneNumber(etPhone, true)) ret = false;
 
        return ret;
    }

	/**
	 * Logout From Facebook
	 */
	public static void callFacebookLogout(Context context) {
		Session session = Session.getActiveSession();
		if (session != null) {

			if (!session.isClosed()) {
				session.closeAndClearTokenInformation();
				// clear your preferences if saved
			}
		} else {

			session = new Session(context);
			Session.setActiveSession(session);

			session.closeAndClearTokenInformation();
			// clear your preferences if saved

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
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

	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();

		// Call the 'activateApp' method to log an app event for use in
		// analytics and advertising reporting. Do so in
		// the onResume methods of the primary Activities that an app may be
		// launched into.
		AppEventsLogger.activateApp(this);

		// updateUI();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);

		// outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();

		// Call the 'deactivateApp' method to log an app event for use in
		// analytics and advertising
		// reporting. Do so in the onPause methods of the primary Activities
		// that an app may be launched into.
		AppEventsLogger.deactivateApp(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}
}
