package com.bluewebspark.happyservice.custome;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Sohel Khan on 12-Apr-18.
 */

public class QuicksandBold extends android.support.v7.widget.AppCompatTextView {

    public QuicksandBold(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "helvetica_neue_bold.otf");
        this.setTypeface(face);
    }

    public QuicksandBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "helvetica_neue_bold.otf");
        this.setTypeface(face);
    }

    public QuicksandBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "helvetica_neue_bold.otf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

}