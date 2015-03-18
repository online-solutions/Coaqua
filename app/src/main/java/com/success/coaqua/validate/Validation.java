package com.success.coaqua.validate;

import android.widget.EditText;
import java.util.regex.Pattern;
 
// validation for validate EditText
public class Validation {
 
    // Regular Expression
    // you can change the expression based on your need
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";
    private static final String NUMBER_REGEX = "\\d+";
    private static final String DATE_REGEX = "(?:0[1-9]|1[0-2])/[0-9]{2}";
    private static final String CVN_REGEX = "^[0-9]{3,4}$";
 
    // Error Messages
    private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";
    private static final String PHONE_MSG = "###-#######";
    private static final String NUMBER_MSG = "number only";
    private static final String DATE_MSG = "mm/yy";
    private static final String CVN_MSG = "3 or 4 digits";
    
    // call this method when you need to check email validation
    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }
 
    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(EditText editText, boolean required) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    }
    
 // call this method when you need to check number validation
    public static boolean isNumber(EditText editText, boolean required) {
        return isValid(editText, NUMBER_REGEX, NUMBER_MSG, required);
    }
    
//    call this method when you need to check date format
	public static boolean isMonthYear(EditText editText, boolean required) {
		return isValid(editText, DATE_REGEX, DATE_MSG, required);
	}
	
//  call this method when you need to check CVN
	public static boolean isCvn(EditText editText, boolean required) {
		return isValid(editText, CVN_REGEX, CVN_MSG, required);
	}
 
    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {
 
        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);
 
        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;
 
        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        };
 
        return true;
    }
 
    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText) {
 
        String text = editText.getText().toString().trim();
        editText.setError(null);
 
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }
 
        return true;
    }


}
