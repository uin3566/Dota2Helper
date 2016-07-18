package com.fangxu.dota2helper.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangxu.dota2helper.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/18.
 */
public class ProfileItemLayout extends RelativeLayout{

    public ProfileItemLayout(Context context) {
        this(context, null);
    }

    public ProfileItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProfileItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.item_profile, this);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ProfileItemLayout, 0, 0);
        String text = ta.getString(R.styleable.ProfileItemLayout_pil_text);
        ta.recycle();

        TextView itemTextView = ButterKnife.findById(this, R.id.tv_item);
        if (text != null) {
            itemTextView.setText(text);
        }
    }
}
