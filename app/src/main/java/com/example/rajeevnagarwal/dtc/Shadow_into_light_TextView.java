package com.example.rajeevnagarwal.dtc;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by sagar on 30/4/16.
 */
public class Shadow_into_light_TextView extends TextView {
    public Shadow_into_light_TextView(Context context,AttributeSet attributeSet)
    {
        super(context,attributeSet);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ShadowsIntoLight.ttf"));


    }
}
