package com.success.coaqua.helper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.success.coaqua.activities.ResultActivity;
import com.success.coaqua.entity.TransactionRequest;
import com.success.coaqua.entity.TransactionResponse;
import com.success.coaqua.helper.XMLPullParserHandler;
import com.success.coaqua.stable.Info;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class PaymentHandler extends AsyncTask<Void, Integer, TransactionResponse> {

	// Logcat tag
	private static final String LOG = "ANDROID DEMO";

	Activity activity;
	
	private ProgressDialog dialog;

    private TransactionRequest transactionRequest;
    private int totalBox;
    private String grandTotal;

	public PaymentHandler(Activity confirmActivity, TransactionRequest transactionRequest, int totalBox, String grandTotal) {
		activity = confirmActivity;
		dialog = new ProgressDialog(confirmActivity);
        this.transactionRequest = transactionRequest;
        this.totalBox = totalBox;
        this.grandTotal = grandTotal;
	}

	// run first
	@Override
    protected void onPreExecute() {
		dialog.setMessage("Processing...");
		dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
	
	// run second
	@Override
	protected TransactionResponse doInBackground(Void... params) {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// update progress bar
//		publishProgress(100);

		return sendRequestXml(transactionRequest);
	}
	
	// run third
	// update UI here
	@Override
    protected void onProgressUpdate(Integer... values) {                                 // 4
		// update values to progress bar
//        mProgressDialog.setProgress(values[0]);                                          // 5
        super.onProgressUpdate(values);
    }
    
	// run last
	@Override
    protected void onPostExecute(TransactionResponse res){
//        super.onPostExecute(result);

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        
        if(res != null){
    		// start result activity
          Intent intent = new Intent(activity.getApplicationContext(), ResultActivity.class);
          Bundle bundle = new Bundle();
          bundle.putBoolean("status", res.getEwayTrxnStatus().equalsIgnoreCase("true"));
          bundle.putString("message", res.getEwayTrxnError().substring(3));

          bundle.putString("ewayAuthCode", res.getEwayAuthCode());
          bundle.putString("ewayTrxnReference", res.getEwayTrxnReference());

          bundle.putString("totalBox", String.valueOf(totalBox));
          bundle.putString("grandTotal", grandTotal);

          intent.putExtras(bundle);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
          activity.getApplicationContext().startActivity(intent);
        }
        
        // error payment, please contact us.
    }
	
	
	public TransactionResponse sendRequestXml(TransactionRequest req) {
		try {
//			publishProgress(1);
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Info.getPaymentUrl());

			StringEntity string_entity = new StringEntity(Info.objectToXml(req),
					HTTP.UTF_8);
			string_entity.setContentType("text/xml");
			httppost.setEntity(string_entity);

			HttpResponse httpResponse = httpclient.execute(httppost);
			HttpEntity response_entity = httpResponse.getEntity();
			String response_string = EntityUtils.toString(response_entity);

            Log.d(LOG, "log here");
			Log.d(LOG, response_string);
			Log.d(LOG, "handle response, convert to object");
			XMLPullParserHandler xmlHandler = new XMLPullParserHandler();
			
			TransactionResponse res = xmlHandler.parseResponse(response_entity.getContent());
			if(res.getEwayTrxnStatus().equalsIgnoreCase("true")){
				Log.d(LOG, "Approved, your Coaqua box(es) will be delivery to your home");
				Log.d(LOG, res.getEwayReturnAmount());
			} else {
				Log.d(LOG, "Failed in payment, please see the error below");
				Log.d(LOG, res.getEwayTrxnError());
			}
			return res;
		} catch (Exception e) {
			Log.d(LOG, "ERROR: " + e.getMessage());
			return null;
		}
	}
	
}
