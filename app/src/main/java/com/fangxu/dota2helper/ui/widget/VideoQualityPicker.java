package com.fangxu.dota2helper.ui.widget;

import android.content.Context;

import com.bigkoo.pickerview.OptionsPickerView;
import com.fangxu.dota2helper.bean.VideoQualityWrapper;
import com.fangxu.dota2helper.callback.VideoQualitySelectCallback;
import com.youku.player.VideoQuality;

import java.util.ArrayList;

/**
 * Created by dear33 on 2016/8/2.
 */
public class VideoQualityPicker {
    private OptionsPickerView mPickerView;
    private Context mContext;
    public VideoQualitySelectCallback mCallback;

    public VideoQualityPicker(Context context, VideoQualitySelectCallback callback) {
        mContext = context;
        mPickerView = new OptionsPickerView(context);
        mCallback = callback;
    }

    public void show() {
        mPickerView.show();
    }

    public void initView(ArrayList<VideoQuality> qualityList) {
        final ArrayList<VideoQualityWrapper> arrayList = new ArrayList<>(qualityList.size());
        for (VideoQuality quality : qualityList) {
            VideoQualityWrapper wrapper = new VideoQualityWrapper(mContext, quality);
            arrayList.add(wrapper);
        }
        mPickerView.setPicker(arrayList);
        mPickerView.setCyclic(false);
        mPickerView.setCancelable(true);
        mPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int i, int i1, int i2) {
                if (mCallback != null) {
                    VideoQuality videoQuality = arrayList.get(i).getQuality();
                    mCallback.onVideoQualitySelected(videoQuality);
                }
            }
        });
    }
}
