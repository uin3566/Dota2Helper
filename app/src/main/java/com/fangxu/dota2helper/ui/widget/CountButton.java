package com.fangxu.dota2helper.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fangxu.dota2helper.R;

/**
 * Created by dear33 on 2016/7/24.
 */
public class CountButton extends TextView {
    private String mStr;

    public CountButton(Context context) {
        this(context, null);
    }

    public CountButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CountButton, 0, 0);
        mStr = ta.getString(R.styleable.CountButton_cb_text);
        ta.recycle();

        setText(mStr == null ? "" : mStr);
    }

    public void setContent(String str) {
        mStr = str;
        setText(mStr);
    }

    public void setCount(int count) {
        if (count > 0) {
            setText(mStr + "(" + count + ")");
        } else {
            setText(mStr);
        }
    }
}
