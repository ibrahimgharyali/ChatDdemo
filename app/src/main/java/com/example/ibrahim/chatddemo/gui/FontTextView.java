package com.example.ibrahim.chatddemo.gui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;


import com.example.ibrahim.chatddemo.enums.Fonts;


/**
 * By katepratik on 27/2/17.
 */

public class FontTextView extends AppCompatTextView {
    private Context context;

    public FontTextView(@NonNull Context context) {
        super(context);
        this.context = context;
        init(context, null, 0);
    }

    public FontTextView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs, 0);
    }

    public FontTextView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs, defStyleAttr);
    }

    private void init(@NonNull Context context, AttributeSet attrs, int defStyle) {

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.textStyle, typedValue, true);

        int[] textStyleAttr = new int[]{android.R.attr.textStyle};
        int indexOfAttrTextSize = 0;
        TypedArray a = context.obtainStyledAttributes(typedValue.data, textStyleAttr);
        String textStyle = a.getString(indexOfAttrTextSize);
        a.recycle();

//        DebugUtils.log("BOLD : "+getText()+" TF : "+getTypeface().getStyle());

        if (textStyle != null && textStyle.equals("bold"))
            setFont(Fonts.MontserratRegular);

        if (getTypeface() != null && getTypeface().getStyle() == 1) {
            // Bold
//            DebugUtils.log("BOLD : "+getText());
            setFont(Fonts.MontserratRegular);
        } else {
            setFont(Fonts.MontserratLight);
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }


    public void setFont(@NonNull Fonts font) {
        setTypeface(Typeface.createFromAsset(context.getAssets(), font.toString()));
    }
}
