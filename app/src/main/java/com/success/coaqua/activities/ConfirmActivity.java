package com.success.coaqua.activities;

import com.success.coaqua.R;
import com.success.coaqua.entity.Account;
import com.success.coaqua.entity.Customer;
import com.success.coaqua.entity.TransactionRequest;
import com.success.coaqua.helper.DatabaseHelper;
import com.success.coaqua.helper.PaymentHandler;
import com.success.coaqua.stable.Info;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class ConfirmActivity extends Activity {

    // Logcat tag
    private static final String LOG = "ANDROID DEMO";

	// // User Session Manager
	// UserSessionManager session;

	// Database Helper
	DatabaseHelper db;
	Customer customer;
	Account acc;
	int totalBox;
	ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm);

		// setup order information

		Bundle bundle = getIntent().getExtras();
		totalBox = bundle.getInt("totalBox");

        // init
		TextView tvNumberOfCases = (TextView) findViewById(R.id.tvNumberOfCases);
		tvNumberOfCases.setText(String.valueOf(totalBox));

		TextView tvCoaCost = (TextView) findViewById(R.id.tvCoaCost);
		tvCoaCost.setText(Info.getTotalAmount(totalBox));

		TextView tvGst = (TextView) findViewById(R.id.tvGst);
		tvGst.setText(Info.getGst(totalBox));

		TextView tvGrandTotal = (TextView) findViewById(R.id.tvGrandTotal);
		tvGrandTotal.setText(Info.getGrandTotal(totalBox));

		// set delivery information

		// // User Session Manager
		// session = new UserSessionManager(getApplicationContext());

		db = new DatabaseHelper(getApplicationContext());
		customer = db.getCustomer();

		if (customer != null) {

			((TextView) findViewById(R.id.tvCustomerName)).setText(customer
					.getFullName());

			((TextView) findViewById(R.id.tvCustomerAddress)).setText(customer
					.getAddress());

			((TextView) findViewById(R.id.tvPhone)).setText(customer
					.getPhoneNumber());
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confirm, menu);
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

	public void startPayment(View view) {
		Toast.makeText(getApplicationContext(), "call Payment",
				Toast.LENGTH_SHORT).show();
		
		acc = db.getAccount();
		if(acc == null){
			Toast.makeText(getApplicationContext(), "null account, please check", Toast.LENGTH_SHORT).show();
		} else {

			
			TransactionRequest req = new TransactionRequest();
			req.setEwayCustomerID(Info.customerID);
			req.setEwayTotalAmount(200010);
			
			// uncomment where release
//			req.setEwayTotalAmount(Info.getGrandTotalCent(totalBox));
			req.setEwayCustomerFirstName(customer.getFirstName());
			req.setEwayCustomerLastName(customer.getLastName());
			req.setEwayCustomerEmail(customer.getEmail());
			req.setEwayCustomerAddress(customer.getAddress());
			req.setEwayCustomerInvoiceDescription(totalBox + " x " + Info.getRate() + " + " + Info.getGst(totalBox) + " (GST) = " + Info.getGrandTotal(totalBox));
			req.setEwayCardHoldersName(acc.getNameOnCard());
			req.setEwayCardNumber(acc.getCardNumber());
			req.setEwayCardExpiryMonth(acc.getExpireDate().substring(0, 2));
			req.setEwayCardExpiryYear(acc.getExpireDate().substring(3));
			req.setEwayCVN(acc.getDigits());
			
			Log.d(LOG, Info.objectToXml(req));
            PaymentHandler payment = new PaymentHandler(this, req, totalBox, Info.getGrandTotal(totalBox));
			payment.execute();

            // handle send request fail
            // not working
            // need implement
//            try {
//                payment.get(5000, TimeUnit.MILLISECONDS);
//            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(),"Pay fail. Please check connection and try again.", Toast.LENGTH_SHORT).show();
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            } catch (ExecutionException e) {
////                e.printStackTrace();
////            } catch (TimeoutException e) {
////                e.printStackTrace();
//            }
        }

	}
	
	
}
