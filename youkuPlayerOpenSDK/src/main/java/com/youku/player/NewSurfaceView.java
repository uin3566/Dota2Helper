package com.youku.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

public class NewSurfaceView extends SurfaceView{

	private int mForceHeight = 0;
	private int mForceWidth = 0;
	
	public NewSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public NewSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NewSurfaceView(Context context) {
		super(context);
	}

	public void setDimensions(int w, int h) {
		this.mForceHeight = h;
		this.mForceWidth = w;
		invalidate();
	}
	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if(mForceHeight == 0)
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		else
			setMeasuredDimension(mForceWidth, mForceHeight);
	}
	
	public void recreateSurfaceHolder() {
		setVisibility(View.INVISIBLE);
		setVisibility(View.VISIBLE);
	}
}
