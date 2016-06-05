/*
 * Copyright © 2012-2013 LiuZhongnan. All rights reserved.
 * 
 * Email:qq81595157@126.com
 * 
 * PROPRIETARY/CONFIDENTIAL.
 */

package com.youku.service.download;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

import com.baseproject.utils.Logger;
import com.youku.player.YoukuPlayerConfiguration;
import com.youku.player.util.PlayerUtil;
import com.youku.service.download.SDCardManager.SDCardInfo;

/**
 * BaseDownload.下载管理抽象类
 * 
 * @author 刘仲男 qq81595157@126.com
 * @version v3.5
 * @created time 2012-10-15 下午1:16:02
 */
public abstract class BaseDownload implements IDownload {

	public Context context;

	/** SD卡列表 */
	public ArrayList<SDCardInfo> sdCard_list;

	@Override
	public final boolean existsDownloadInfo(String videoId) {
		return getDownloadInfo(videoId) == null ? false : true;
	}

	@Override
	public final boolean isDownloadFinished(String vid) {
		DownloadInfo info = getDownloadInfo(vid);
		if (info != null && info.getState() == DownloadInfo.STATE_FINISH)
			return true;
		return false;
	}

	@Override
	public final DownloadInfo getDownloadInfo(String videoId) {
		if (sdCard_list == null
				&& (sdCard_list = SDCardManager.getExternalStorageDirectory()) == null) {
			return null;
		}
		for (int i = 0; i < sdCard_list.size(); i++) {
			DownloadInfo info = getDownloadInfoBySavePath(sdCard_list.get(i).path
					+ YoukuPlayerConfiguration.getDownloadPath() + videoId + "/");
			if (info != null) {
				return info;
			}
		}
		return null;
	}

	/**
	 * 根据存储路径获得DownloadInfo
	 */
	public final DownloadInfo getDownloadInfoBySavePath(String savePath) {
		try {
			File f = new File(savePath + FILE_NAME);
			if (f.exists() && f.isFile()) {
				String s = PlayerUtil.convertStreamToString(new FileInputStream(
						f));
				DownloadInfo i = DownloadInfo.jsonToDownloadInfo(s);
				if (i != null && i.getState() != DownloadInfo.STATE_CANCEL) {
					i.savePath = savePath;
					return i;
				}
			}
		} catch (Exception e) {
			Logger.e("Download_BaseDownload",
					"getDownloadInfoBySavePath()#savePath:" + savePath, e);
		}
		return null;
	}

	/**
	 * 重新获取正在下载的数据
	 * 
	 * @return
	 */
	protected HashMap<String, DownloadInfo> getNewDownloadingData() {
		HashMap<String, DownloadInfo> downloadingData = new HashMap<String, DownloadInfo>();
		if (sdCard_list == null
				&& (sdCard_list = SDCardManager.getExternalStorageDirectory()) == null) {
			return downloadingData;
		}
		for (int j = 0; j < sdCard_list.size(); j++) {
			File dir = new File(sdCard_list.get(j).path + YoukuPlayerConfiguration.getDownloadPath());
			if (!dir.exists())
				continue;
			String[] dirs = dir.list();
			for (int i = dirs.length - 1; i >= 0; i--) {
				String vid = dirs[i];
				DownloadInfo info = getDownloadInfoBySavePath(sdCard_list
						.get(j).path + YoukuPlayerConfiguration.getDownloadPath() + vid + "/");
				if (info != null
						&& info.getState() != DownloadInfo.STATE_FINISH
						&& info.getState() != DownloadInfo.STATE_CANCEL) {
					info.downloadListener = new DownloadListenerImpl(context,
							info);
					downloadingData.put(info.taskId, info);
				}
			}
		}
		return downloadingData;
	}
	
}
