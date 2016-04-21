package com.youku.player.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FitScaleImageView extends ImageView {

    public boolean isFullscreen = false;

    public FitScaleImageView(Context context) {
        super(context);
    }

    public FitScaleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FitScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d != null) {
            // ceil not round - avoid thin vertical gaps along the left/right
            // edges
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) Math.ceil((float) width
                    * (float) d.getIntrinsicHeight()
                    / (float) d.getIntrinsicWidth());
            int height2 = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(width, isFullscreen ? height2 : height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
