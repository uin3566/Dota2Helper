/*
 * Copyright © 2012-2013 LiuZhongnan. All rights reserved.
 * 
 * Email:qq81595157@126.com
 * 
 * PROPRIETARY/CONFIDENTIAL.
 */

package com.youku.service.download;

/**
 * OnCreateDownloadListener.创建下载文件情况监听
 * 
 * @author 刘仲男 qq81595157@126.com
 * @version v3.5
 * @created time 2013-10-17 下午1:16:02
 */
public abstract class OnCreateDownloadListener {

	/** 当每一个下载已准备的时候 */
	public void onOneReady() {
	}

	/** 当每一个下载失败 */
	public void onOneFailed() {
	}

	/**
	 * 当全部下载已准备的时候
	 * 
	 * @param isNeedRefresh
	 *            是否需要刷新数据
	 */
	public abstract void onfinish(boolean isNeedRefresh);

}
