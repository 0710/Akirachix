package com.thomaskioko.hellodroid.app.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="kiokotomas@gmail.com">Thomas Kioko</a>
 * @version Version 1.0
 */


public class AppUtils {

    /**
     * This method validates the email address
     *
     * @param email email address
     * @return True/False where is valid or invalid email
     */
    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Displays a toast message
     * @param message Message to be displayed
     */
    public static void displayToastMessage(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
