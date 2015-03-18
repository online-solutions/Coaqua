package com.success.coaqua.helper;

import com.success.coaqua.entity.Account;
import com.success.coaqua.entity.Customer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	// LogCat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "productManager";

	// Table Names
	private static final String TABLE_CUSTOMER = "customer";
	private static final String TABLE_ORDER = "my_order";
	private static final String TABLE_CUSTOMER_ORDER = "customer_order";
	
	private static final String TABLE_ACCOUNT = "account";

	// Common column names
	private static final String KEY_ID = "id";
	private static final String KEY_CREATED_AT = "created_at";

	// CUSTOMER Table - column names
	private static final String KEY_FIRST_NAME = "first_name";
	private static final String KEY_LAST_NAME = "last_name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_PHONE = "phone";
	
	// account table
	private static final String KEY_CARD_NUMBER = "card_number";
	private static final String KEY_EXPIRE_DATE = "expire_date";
	private static final String KEY_NAME_ON_CARD = "name_on_card";
	private static final String KEY_DEGITS = "degits";
	

	// TAGS Table - column names
	private static final String KEY_RATE = "rate";
	private static final String KEY_NUBMER_OF_CASES = "num";
	private static final String KEY_TOTAL = "total";

	// CUSTOMER_ORDER Table - column names
	private static final String KEY_CUSTOMER_ID = "customer_id";
	private static final String KEY_ORDER_ID = "order_id";

	// Table Create Statements
	// CUSTOMER table create statement
	private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE "
			+ TABLE_CUSTOMER + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_FIRST_NAME + " TEXT," + KEY_LAST_NAME + " TEXT," + KEY_EMAIL
			+ " TEXT," + KEY_ADDRESS + " TEXT," + KEY_PHONE + " TEXT" + ")";

	private static final String CREATE_TABLE_ORDER = "CREATE TABLE "
			+ TABLE_ORDER + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_RATE
			+ " INTEGER," + KEY_NUBMER_OF_CASES + " INTEGER," + KEY_TOTAL
			+ " INTEGER," + KEY_CREATED_AT + " DATETIME" + ")";

	private static final String CREATE_TABLE_CUSTOMER_ORDER = "CREATE TABLE "
			+ TABLE_CUSTOMER_ORDER + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_CUSTOMER_ID + " INTEGER," + KEY_ORDER_ID + " INTEGER,"
			+ KEY_CREATED_AT + " DATETIME" + ")";
	
	private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE "
			+ TABLE_ACCOUNT + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_CARD_NUMBER + " TEXT," + KEY_EXPIRE_DATE + " TEXT," + KEY_NAME_ON_CARD
			+ " TEXT," + KEY_DEGITS + " TEXT" + ")";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables
		Log.d("ANDROID DEMO", CREATE_TABLE_CUSTOMER);
		db.execSQL(CREATE_TABLE_CUSTOMER);
		Log.d("ANDROID DEMO", CREATE_TABLE_ORDER);
		db.execSQL(CREATE_TABLE_ORDER);
		db.execSQL(CREATE_TABLE_CUSTOMER_ORDER);
		db.execSQL(CREATE_TABLE_ACCOUNT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER_ORDER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
	}

	/**
	 * Creating customer
	 */
	public long createCustomer(Customer customer) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_FIRST_NAME, customer.getFirstName());
		values.put(KEY_LAST_NAME, customer.getLastName());
		values.put(KEY_EMAIL, customer.getEmail());
		values.put(KEY_ADDRESS, customer.getAddress());
		values.put(KEY_PHONE, customer.getPhoneNumber());
		// insert row
		long customer_id = db.insert(TABLE_CUSTOMER, null, values);
		return customer_id;
	}

	public Customer getCustomer(long customerId) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_CUSTOMER + " WHERE "
				+ KEY_ID + " = " + customerId;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		return populateCustomer(c);
	}

	public Customer getCustomer() {
		// get last customer
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_CUSTOMER + " WHERE "
				+ KEY_ID + " = (SELECT MAX(" + KEY_ID + ") FROM "
				+ TABLE_CUSTOMER + ")";

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (!(c.moveToFirst()) || c.getCount() == 0)
			return null;

		return populateCustomer(c);
	}

	public Customer populateCustomer(Cursor cursor) {
		try {
			Customer customer = new Customer();
			customer.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
			customer.setFirstName((cursor.getString(cursor
					.getColumnIndex(KEY_FIRST_NAME))));
			customer.setLastName((cursor.getString(cursor
					.getColumnIndex(KEY_LAST_NAME))));
			customer.setEmail((cursor.getString(cursor
					.getColumnIndex(KEY_EMAIL))));
			customer.setAddress((cursor.getString(cursor
					.getColumnIndex(KEY_ADDRESS))));
			customer.setPhoneNumber((cursor.getString(cursor
					.getColumnIndex(KEY_PHONE))));
			return customer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public long updateCustomer(Customer customer) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_FIRST_NAME, customer.getFirstName());
		values.put(KEY_LAST_NAME, customer.getLastName());
		values.put(KEY_EMAIL, customer.getEmail());
		values.put(KEY_ADDRESS, customer.getAddress());
		values.put(KEY_PHONE, customer.getPhoneNumber());

		// updating row
		return db.update(TABLE_CUSTOMER, values, KEY_ID + " = ?",
				new String[] { String.valueOf(customer.getId()) });
	}

	public void insertAccount(Account acc) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
		db.execSQL(CREATE_TABLE_ACCOUNT);
		createAccount(acc);
	}
	
	/**
	 * Creating account
	 */
	public void createAccount(Account acc) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_CARD_NUMBER, acc.getCardNumber());
		values.put(KEY_EXPIRE_DATE, acc.getExpireDate());
		values.put(KEY_NAME_ON_CARD, acc.getNameOnCard());
		values.put(KEY_DEGITS, acc.getDigits());
		// insert row
		db.insert(TABLE_ACCOUNT, null, values);
	}
	
	public Account getAccount() {
		// get last customer
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_ACCOUNT + " WHERE "
				+ KEY_ID + " = (SELECT MAX(" + KEY_ID + ") FROM "
				+ TABLE_ACCOUNT + ")";

		Log.e(LOG, selectQuery);

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (!(cursor.moveToFirst()) || cursor.getCount() == 0)
			return null;

		return populateAccount(cursor);
	}

	private Account populateAccount(Cursor cursor) {
		try {
			Account acc = new Account();
			acc.setCardNumber(cursor.getString(cursor.getColumnIndex(KEY_CARD_NUMBER)));
			acc.setExpireDate(cursor.getString(cursor.getColumnIndex(KEY_EXPIRE_DATE)));
			acc.setNameOnCard(cursor.getString(cursor.getColumnIndex(KEY_NAME_ON_CARD)));
			acc.setDigits(cursor.getString(cursor.getColumnIndex(KEY_DEGITS)));
			return acc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
