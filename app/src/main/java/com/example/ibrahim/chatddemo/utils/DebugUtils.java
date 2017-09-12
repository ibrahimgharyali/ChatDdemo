package com.example.ibrahim.chatddemo.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.ibrahim.chatddemo.BuildConfig;


/**
 * By katepratik on 6/8/16.
 */
public class DebugUtils {

    public static void log(@Nullable String message) {
        if (BuildConfig.DEBUG && message != null)
            Log.d("DEBUG", message);
    }

    public static void toast(Context context, String message) {
        if (BuildConfig.DEBUG && context != null && message != null)
            Toast.makeText(context, "DEBUG : " + message, Toast.LENGTH_LONG).show();
    }
}
