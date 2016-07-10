package com.fangxu.dota2helper.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fangxu.dota2helper.R;
import com.youku.player.VideoQuality;

/**
 * Created by dear33 on 2016/7/10.
 */
public class VideoQualityTextView extends LinearLayout {
    private Context mContext;
    private TextView mQualityTextView;
    private View mDivider;
    private VideoQuality mQuality;

    public VideoQualityTextView(Context context) {
        this(context, null);
    }

    public VideoQualityTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoQualityTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;

        LayoutInflater.from(context).inflate(R.layout.layout_video_quality, this);
        mQualityTextView = (TextView) findViewById(R.id.tv_quality);
        mDivider = findViewById(R.id.v_divider);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.VideoQualityTextView, 0, 0);
        int textColor = ta.getColor(R.styleable.VideoQualityTextView_vqt_text_color, 0x8affffff);
        ta.recycle();

        mQualityTextView.setTextColor(textColor);
    }

    public void setTextColor(int resId) {
        mQualityTextView.setTextColor(mContext.getResources().getColor(resId));
    }

    public void setQuality(VideoQuality videoQuality) {
        mQuality = videoQuality;
        switch (videoQuality) {
            case STANDARD:
                mQualityTextView.setText(R.string.standard);
                break;
            case HIGHT:
                mQualityTextView.setText(R.string.high);
                break;
            case SUPER:
                mQualityTextView.setText(R.string.super_high);
                break;
            case P1080:
                mQualityTextView.setText(R.string.p1080);
                break;
        }
    }

    public void setDividerVisibility(boolean show) {
        if (show) {
            mDivider.setVisibility(VISIBLE);
        } else {
            mDivider.setVisibility(GONE);
        }
    }

    public VideoQuality getQuality() {
        return mQuality;
    }
}
