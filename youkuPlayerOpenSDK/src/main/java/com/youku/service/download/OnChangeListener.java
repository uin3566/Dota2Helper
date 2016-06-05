/*
 * Copyright © 2012-2013 LiuZhongnan. All rights reserved.
 * 
 * Email:qq81595157@126.com
 * 
 * PROPRIETARY/CONFIDENTIAL.
 */

package com.youku.service.download;

/**
 * OnChangeListener.下载状态改变的监听实现
 * 
 * @author 刘仲男 qq81595157@126.com
 * @version v3.5
 * @created time 2013-10-17 下午1:16:02
 */
public interface OnChangeListener {
	void onChanged(DownloadInfo info);

	void onFinish();
}
