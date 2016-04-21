package com.youku.player.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class YoukuImageView extends ImageView {

	public YoukuImageView(Context context) {
		super(context);
	}

	public YoukuImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// setMeasuredDimension(measuredWidth, measuredHeight)
		int w = MeasureSpec.getSize(widthMeasureSpec);
		int h = (int) (w * 9 / 16);
		// h = MeasureSpec.makeMeasureSpec((int) size, MeasureSpec.EXACTLY);
		// h = MeasureSpec.getSize(h);
		setMeasuredDimension(w, h);
		// Logger.d("Youku",
		// "widthMeasureSpec = "+MeasureSpec.getSize(widthMeasureSpec) +
		// " heightMeasureSpec = "+h);
		// super.onMeasure(widthMeasureSpec, (int) (widthMeasureSpec*size));
	}
}
