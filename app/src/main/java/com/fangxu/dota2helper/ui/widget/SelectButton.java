package com.fangxu.dota2helper.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fangxu.dota2helper.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenov0 on 2016/4/30.
 */
public class SelectButton extends FrameLayout {
    @Bind(R.id.btn_select_button)
    TextView mSelectButton;

    private int mIndex;

    public SelectButton(Context context) {
        super(context);
        init(context);
    }

    public SelectButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SelectButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.select_button, this);
        ButterKnife.bind(view);
    }

    public void setIndex(int index) {
        mIndex = index;
        mSelectButton.setText(String.valueOf(index + 1));
    }

    public int getIndex() {
        return mIndex;
    }

    public void setSelected(boolean selected) {
        mSelectButton.setSelected(selected);
    }
}
