/*
 * Copyright © 2012-2013 LiuZhongnan. All rights reserved.
 * 
 * Email:qq81595157@126.com
 * 
 * PROPRIETARY/CONFIDENTIAL.
 */

package com.youku.service.download;

/**
 * DownloadListener.下载状态改变后的监听
 * 
 * @author 刘仲男 qq81595157@126.com
 * @version v3.5
 * @created time 2012-11-5 下午1:16:02
 */
public interface DownloadListener {
	void onStart();

	void onPause();

	void onCancel();

	void onException();

	void onFinish();

	void onProgressChange(double progress);

	void onWaiting();
}
