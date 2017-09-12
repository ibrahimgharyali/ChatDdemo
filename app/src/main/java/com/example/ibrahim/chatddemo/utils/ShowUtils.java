package com.example.ibrahim.chatddemo.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * By katepratik on 6/8/16.
 */
public class ShowUtils {

    public static void message(Context context, String message) {
        shortToast(context, message, Toast.LENGTH_LONG);
    }

    public static void shortMessage(Context context, String message) {
        shortToast(context, message, Toast.LENGTH_SHORT);
    }

    private static void shortToast(Context context, String message, int length) {
        Toast.makeText(context, message, length).show();
    }

    public static void hideShowKeyboard(Activity activity) {
// Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
