package com.success.coaqua.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.success.coaqua.R;
import com.success.coaqua.entity.Customer;
import com.success.coaqua.entity.RequestEmail;
import com.success.coaqua.helper.DatabaseHelper;
import com.success.coaqua.helper.RequestEmailHandler;

public class ResultActivity extends Activity {

    private DatabaseHelper db;
    private Customer customer;
    private RequestEmail reqEmail;
	private TextView tvResultPayment;
	private TextView tvMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		// init
		tvResultPayment = (TextView) findViewById(R.id.tvResultPayment);
		tvMessage = (TextView) findViewById(R.id.tvMessage);

        db = new DatabaseHelper(getApplicationContext());
        customer = db.getCustomer();
        reqEmail = new RequestEmail();
		
		// set text from payment in previous activity
		Bundle bundle = getIntent().getExtras();
		
		tvMessage.setText(bundle.getString("message"));
		
		if(bundle.getBoolean("status")){
			String message = "THANK YOU FOR CHOOSING OUR PRODUCT.\n YOUR PACKAGE WILL BE DELIVERED ON NEXT BUSINESS DAY.";
			tvResultPayment.setText(message);
			Toast.makeText(getApplicationContext(), "Thanks for using Coaqua service", Toast.LENGTH_SHORT).show();
			
			// send request to server to send 3 email
			// handle if request fail, not response...
			// maybe using service
            RequestEmailHandler handler = new RequestEmailHandler();
            // set information in post here
            reqEmail.setCustomerName(customer.getFullName());
            reqEmail.setCustomerAddress(customer.getAddress());
            reqEmail.setCustomerPhone(customer.getPhoneNumber());
            reqEmail.setCustomerEmail(customer.getEmail());

            reqEmail.setEwayAuthCode(bundle.getString("ewayAuthCode"));
            reqEmail.setEwayTrxnReference(bundle.getString("ewayTrxnReference"));

            reqEmail.setTotalBox(bundle.getString("totalBox"));
            reqEmail.setGrandTotal(bundle.getString("grandTotal"));
            handler.execute(reqEmail);

		}
        Toast.makeText(getApplicationContext(), "read comment", Toast.LENGTH_SHORT).show();
        // Do you want to buy more
        // yes to back to order
        // no to exit

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
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
}
