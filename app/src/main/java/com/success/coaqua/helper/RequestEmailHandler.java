package com.success.coaqua.helper;

import android.os.AsyncTask;

import com.success.coaqua.entity.RequestEmail;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phungdinh on 27/02/2015.
 */
public class RequestEmailHandler extends AsyncTask<RequestEmail, Integer, String>{

    @Override
    protected String doInBackground(RequestEmail... params) {
        postData(params[0]);
        return null;
    }

    public void postData(RequestEmail reqEmail) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.2.248:8080/CoaquaServer/");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("customerName", reqEmail.getCustomerName()));
            nameValuePairs.add(new BasicNameValuePair("customerAddress", reqEmail.getCustomerAddress()));
            nameValuePairs.add(new BasicNameValuePair("customerPhone", reqEmail.getCustomerPhone()));
            nameValuePairs.add(new BasicNameValuePair("customerEmail", reqEmail.getCustomerEmail()));

            nameValuePairs.add(new BasicNameValuePair("ewayAuthCode", reqEmail.getEwayAuthCode()));
            nameValuePairs.add(new BasicNameValuePair("ewayTrxnReference", reqEmail.getEwayTrxnReference()));

            nameValuePairs.add(new BasicNameValuePair("totalBox", reqEmail.getTotalBox()));
            nameValuePairs.add(new BasicNameValuePair("grandTotal", reqEmail.getGrandTotal()));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }
}
