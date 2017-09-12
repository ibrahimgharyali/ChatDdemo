package com.example.ibrahim.chatddemo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ibrahim.chatddemo.R;


/**
 * By katepratik on 6/8/16.
 */
public class ErrorUtils {

    public static void showInternetErrorToast(@NonNull Context context) {

        ShowUtils.message(context, "Uh-oh, your internet\'s down");

    }

}
