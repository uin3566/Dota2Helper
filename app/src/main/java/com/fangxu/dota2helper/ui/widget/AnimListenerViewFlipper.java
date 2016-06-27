package com.fangxu.dota2helper.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ViewFlipper;

/**
 * Created by Administrator on 2016/6/27.
 */
public class AnimListenerViewFlipper extends ViewFlipper {
    private InAnimationListener mInAnimationListener;

    public AnimListenerViewFlipper(Context context) {
        super(context);
    }

    public AnimListenerViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setInAnimationListener(InAnimationListener inAnimationListener) {
        mInAnimationListener = inAnimationListener;
    }

    @Override
    public void setInAnimation(Context context, int resourceID) {
        super.setInAnimation(context, resourceID);
        getInAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mInAnimationListener != null) {
                    mInAnimationListener.onAnimationEnded();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public interface InAnimationListener {
        void onAnimationEnded();
    }
}
