package com.fangxu.dota2helper.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.fangxu.dota2helper.R;
import com.fangxu.dota2helper.bean.NewsList;
import com.fangxu.dota2helper.ui.Activity.NewsDetailActivity;
import com.fangxu.dota2helper.util.DimenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xf on 2015/8/20.
 */
public class FlipperBanner extends FrameLayout implements  View.OnClickListener, View.OnTouchListener{

    private Context mContext;
    private LinearLayout mDotContainer;
    private ViewFlipper mViewFlipper;
    private TextView mTitleTextView;
    private float startX;
    private float endX;
    private int mLastIndex;
    private int mCurIndex;
    private int mBannerCount;
    private long mStartPressTime;
    private long mEndPressTime;

    private float startY;
    private float curX;
    private float curY;
    private float absDx;
    private float absDy;

    private boolean mShowTitle;
    private boolean mShowDot;
    private int mTitleBackgroundColor;
    private int mDotSelectedId;
    private int mDotNormalId;

    private List<NewsList.BannerEntity> mBannerEntityList = new ArrayList<>();

    private Runnable mAutoSwitchRunnable = new Runnable() {
        @Override
        public void run() {
            mViewFlipper.setInAnimation(mContext, R.anim.in_right_left);
            mViewFlipper.setOutAnimation(mContext, R.anim.out_right_left);
            mViewFlipper.showNext();
            mLastIndex = mCurIndex;
            mCurIndex++;
            if (mCurIndex == mBannerCount){
                mCurIndex = 0;
            }
            if (mLastIndex == mBannerCount){
                mLastIndex = 0;
            }
            setSelectedIndicator();
            postDelayed(this, 3000);
        }
    };

    public FlipperBanner(Context context) {
        this(context, null);
    }

    public FlipperBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_flipper_banner, this);
        mViewFlipper = (ViewFlipper)view.findViewById(R.id.vf_banner);
        mDotContainer = (LinearLayout)view.findViewById(R.id.ll_dot_container);
        LinearLayout bottomContainer = (LinearLayout)view.findViewById(R.id.ll_bottom_container);
        mTitleTextView = (TextView)view.findViewById(R.id.tv_title);

        setOnTouchListener(this);
        setOnClickListener(this);

        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.FlipperBanner, 0, 0);
        mShowTitle = ta.getBoolean(R.styleable.FlipperBanner_fb_show_title, false);
        mShowDot = ta.getBoolean(R.styleable.FlipperBanner_fb_show_dot, false);
        mTitleBackgroundColor = ta.getColor(R.styleable.FlipperBanner_fb_title_background, 0x00000000);
        mDotNormalId = ta.getResourceId(R.styleable.FlipperBanner_fb_dot_normal, mDotNormalId);
        mDotSelectedId = ta.getResourceId(R.styleable.FlipperBanner_fb_dot_selected, mDotSelectedId);
        ta.recycle();

        if (mShowTitle || mShowDot) {
            bottomContainer.setBackgroundColor(mTitleBackgroundColor);
        }
        mTitleTextView.setVisibility(mShowTitle ? VISIBLE : GONE);
        mDotContainer.setVisibility(mShowDot ? VISIBLE : GONE);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mStartPressTime = System.currentTimeMillis();
                stopAutoSwitch();
                startX = event.getX();
                startY = event.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                curY = event.getY();
                absDx = Math.abs(curX - startX);
                absDy = Math.abs(curY - startY);
                if (absDx < absDy){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                mEndPressTime = System.currentTimeMillis();
                startAutoSwitch();
                endX = event.getX();
                if (endX - startX > 100){//right
                    mViewFlipper.setInAnimation(mContext, R.anim.in_left_right);
                    mViewFlipper.setOutAnimation(mContext, R.anim.out_left_right);
                    mViewFlipper.showPrevious();
                    mLastIndex = mCurIndex;
                    mCurIndex--;
                    if (mCurIndex == -1){
                        mCurIndex = mBannerCount - 1;
                    }
                    setSelectedIndicator();
                } else if (endX - startX< -100){//left
                    mViewFlipper.setInAnimation(mContext, R.anim.in_right_left);
                    mViewFlipper.setOutAnimation(mContext, R.anim.out_right_left);
                    mViewFlipper.showNext();
                    mLastIndex = mCurIndex;
                    mCurIndex++;
                    if (mCurIndex == mBannerCount){
                        mCurIndex = 0;
                }
                    setSelectedIndicator();
                }
                break;
        }

        if (mEndPressTime - mStartPressTime > 1000 || Math.abs(endX - startX) > 100){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        NewsList.BannerEntity bannerEntity = mBannerEntityList.get(mCurIndex);
        NewsDetailActivity.toNewsDetailActivity(mContext, bannerEntity.getDate(), bannerEntity.getNid());
    }

    public void setBanner(List<NewsList.BannerEntity> bannerEntityList) {
        mBannerEntityList = bannerEntityList;
        mViewFlipper.removeAllViews();
        int size = bannerEntityList.size();
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mViewFlipper.addView(imageView);
            if (mShowTitle) {
                mTitleTextView.setText(bannerEntityList.get(i).getTitle());
            }
            Glide.with(mContext).load(bannerEntityList.get(i).getBackground()).placeholder(R.drawable.image_background_default).into(imageView);
        }
        mBannerCount = mViewFlipper.getChildCount();
        mLastIndex = 0;
        mCurIndex = 0;
        addDots();
    }

    private void addDots(){
        mDotContainer.removeAllViews();
        if (mShowDot) {
            for (int i = 0; i < mBannerCount; i++){
                ImageView imageView = new ImageView(mContext);
                imageView.setImageResource(mDotNormalId);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DimenUtil.dp2px(mContext, 5), DimenUtil.dp2px(mContext, 5));
                lp.setMargins(DimenUtil.dp2px(mContext, 2.5f), 0, DimenUtil.dp2px(mContext, 2.5f), 0);
                imageView.setLayoutParams(lp);
                mDotContainer.addView(imageView, i);
            }
            setSelectedIndicator();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAutoSwitch();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAutoSwitch();
    }

    private void stopAutoSwitch(){
        removeCallbacks(mAutoSwitchRunnable);
    }

    private void startAutoSwitch(){
        removeCallbacks(mAutoSwitchRunnable);
        postDelayed(mAutoSwitchRunnable, 3000);
    }

    private void setSelectedIndicator(){
        NewsList.BannerEntity bannerEntity = mBannerEntityList.get(mCurIndex);
        mTitleTextView.setText(bannerEntity.getTitle());

        ((ImageView)mDotContainer.getChildAt(mLastIndex)).setImageResource(mDotNormalId);
        ((ImageView)mDotContainer.getChildAt(mCurIndex)).setImageResource(mDotSelectedId);
    }
}
