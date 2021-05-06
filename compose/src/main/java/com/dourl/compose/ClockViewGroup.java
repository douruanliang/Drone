package com.dourl.compose;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;


public class ClockViewGroup extends LinearLayout {
    public ClockViewGroup(Context context) {
        super(context);
    }

    public ClockViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
