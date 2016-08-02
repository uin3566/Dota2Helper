package com.fangxu.dota2helper.bean;

import android.content.Context;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.fangxu.dota2helper.util.VideoQualityUtil;
import com.youku.player.VideoQuality;

/**
 * Created by dear33 on 2016/8/2.
 */
public class VideoQualityWrapper implements IPickerViewData {
    private VideoQuality mQuality;
    private Context mContext;

    public VideoQualityWrapper(Context context, VideoQuality quality) {
        mQuality = quality;
        mContext = context;
    }

    public VideoQuality getQuality() {
        return mQuality;
    }

    @Override
    public String getPickerViewText() {
        return VideoQualityUtil.getQualityString(mContext, mQuality);
    }
}
