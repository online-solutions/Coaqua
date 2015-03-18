package com.success.coaqua.activities;

import com.success.coaqua.R;
import com.success.coaqua.entity.Account;
import com.success.coaqua.helper.DatabaseHelper;
import com.success.coaqua.stable.Info;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends Activity {
	
	static Integer totalBox = 1;
	private TextView tvTotalPrice;


	private EditText etTotalBox;
	private ImageButton imgOrderChecked;
    private ImageButton imgOrderClear;
	private DatabaseHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		
		// init
		tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
		imgOrderChecked = (ImageButton) findViewById(R.id.imgOrderChecked);
        imgOrderClear = (ImageButton) findViewById(R.id.imgOrderClear);
		etTotalBox = (EditText) findViewById(R.id.etTotalBox);
		
		tvTotalPrice.setText(String.valueOf(Info.rate));
		
		imgOrderChecked.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(etTotalBox.getText().toString().length() == 0){
					Builder alert = new AlertDialog.Builder(OrderActivity.this);
		            alert.setTitle("Alert");
		            alert.setMessage("How many Coa box do you want?");
		            alert.setPositiveButton("OK", null);
		            alert.show();
					Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
					etTotalBox.setText("1");
				} else {
					startConfirmActivity(v);
				}
			}
		});

        imgOrderClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
		
		// test account
		db = new DatabaseHelper(getApplicationContext());
		Account acc = db.getAccount();
		Toast.makeText(getApplicationContext(), acc.getNameOnCard(), Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order, menu);
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
	
	public void startConfirmActivity(View view){
		etTotalBox = (EditText) findViewById(R.id.etTotalBox);
		totalBox = Integer.valueOf(etTotalBox.getText().toString());
		
		Intent intent = new Intent(this, ConfirmActivity.class);
		Bundle b = new Bundle();
		b.putInt("totalBox", totalBox);
		intent.putExtras(b);
		startActivity(intent);
	}
	
	public void increaseCount(View view){
		// need validate 
		// 1. if total = ""
		// 2. if total > 999
		
		EditText etTotalBox = (EditText) findViewById(R.id.etTotalBox);
		totalBox = Integer.valueOf(etTotalBox.getText().toString()) + 1;
		etTotalBox.setText(String.valueOf(totalBox));
		
		TextView tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
		tvTotalPrice.setText(Info.getTotalAmount(totalBox));
	}
	
	// need a function: change edit text by keyboard
	
}
