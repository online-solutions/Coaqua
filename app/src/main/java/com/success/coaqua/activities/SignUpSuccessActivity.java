package com.success.coaqua.activities;

//import java.util.HashMap;

import com.success.coaqua.R;
import com.success.coaqua.entity.Customer;
import com.success.coaqua.helper.DatabaseHelper;
//import com.success.coaqua.session.UserSessionManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SignUpSuccessActivity extends Activity {

	// // User Session Manager Class
	// UserSessionManager session;

	// Database Helper
	DatabaseHelper db;

	Customer customer;

    private ImageButton imgSuccessClear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_success);

		// // Session class instance
		// session = new UserSessionManager(getApplicationContext());
		//
		// // get user data from session
		// HashMap<String, String> user = session.getCustomerDetails();

		db = new DatabaseHelper(getApplicationContext());

		customer = db.getCustomer();

        // init
		// put customer name, address into heart
		TextView tvCustomerName = (TextView) findViewById(R.id.tvCustomerName);
		tvCustomerName.setText(customer.getFullName());

		TextView tvCustomerAddress = (TextView) findViewById(R.id.tvCustomerAddress);
		tvCustomerAddress.setText(customer.getAddress());

        imgSuccessClear = (ImageButton) findViewById(R.id.imgSuccessClear);

        imgSuccessClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up_success, menu);
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

	public void startAccountActivity(View view) {
		Intent intent = new Intent(this, AccountActivity.class);
		startActivity(intent);
	}
}
