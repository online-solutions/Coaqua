package com.success.coaqua.session;

import java.util.HashMap;

import com.success.coaqua.entity.Customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
 
public class UserSessionManager {
     
    // Shared Preferences reference
    SharedPreferences pref;
     
    // Editor reference for Shared preferences
    Editor editor;
     
    // Context
    Context _context;
     
    // Shared pref mode
    int PRIVATE_MODE = 0;
     
    // Sharedpref file name
    private static final String PREFER_NAME = "AndroidPref";
     
    // All Shared Preferences Keys
    private static final String IS_CUSTOMER_EXIST = "isCustomerExist";
     
    //make variable public to access from outside  
    public static final String KEY_FULL_NAME = "fullName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_STREET = "street";
    public static final String KEY_CITY = "city";
    public static final String KEY_PHONE = "phone";
     
    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
     
    //Create login session
    public void createUserSession(Customer customer){
        // Storing login value as TRUE
        editor.putBoolean(IS_CUSTOMER_EXIST, true);
         
        // Storing in pref
        editor.putString(KEY_FULL_NAME, customer.getFullName());
        editor.putString(KEY_EMAIL, customer.getEmail());
//        editor.putString(KEY_STREET, customer.getStreet());
//        editor.putString(KEY_CITY, customer.getCity());
        editor.putString(KEY_PHONE, customer.getPhoneNumber());
         
        // commit changes
        editor.commit();
    }   
     
    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
//    public boolean checkExist(){
//        // Check exist status
//        if(!this.isUserExist()){
//             
//            // user is not logged in redirect him to Login Activity
//            Intent i = new Intent(_context, SignUpActivity.class);
//             
//            // Closing all the Activities from stack
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//             
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//             
//            // Staring SignUp Activity
//            _context.startActivity(i);
//             
//            return true;
//        }
//        return false;
//    }
     
     
     
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getCustomerDetails(){
         
        //Use hashmap to store user credentials
        HashMap<String, String> customer = new HashMap<String, String>();
         
        // user infomation
        customer.put(KEY_FULL_NAME, pref.getString(KEY_FULL_NAME, null));
        customer.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        customer.put(KEY_STREET, pref.getString(KEY_STREET, null));
        customer.put(KEY_CITY, pref.getString(KEY_CITY, null));
        customer.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
         
        // return user
        return customer;
    }
     
    /**
     * Clear session details
     * */
//    public void logoutUser(){
//         
//        // Clearing all user data from Shared Preferences
//        editor.clear();
//        editor.commit();
//         
//        // After logout redirect user to Login Activity
//        Intent i = new Intent(_context, LoginActivity.class);
//         
//        // Closing all the Activities
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//         
//        // Add new Flag to start new Activity
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//         
//        // Staring Login Activity
//        _context.startActivity(i);
//    }
     
     
    // Check for login
    public boolean isCustomerExist(){
        return pref.getBoolean(IS_CUSTOMER_EXIST, false);
    }
}