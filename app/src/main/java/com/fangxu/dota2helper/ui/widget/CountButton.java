package com.fangxu.dota2helper.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fangxu.dota2helper.R;

/**
 * Created by dear33 on 2016/7/24.
 */
public class CountButton extends TextView {
    private String mDeleteString;

    public CountButton(Context context) {
        this(context, null);
    }

    public CountButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDeleteString = context.getResources().getString(R.string.delete);
        setText(mDeleteString);
    }

    public void setCount(int count) {
        if (count > 0) {
            setText(mDeleteString + "(" + count + ")");
        } else {
            setText(mDeleteString);
        }
    }
}
