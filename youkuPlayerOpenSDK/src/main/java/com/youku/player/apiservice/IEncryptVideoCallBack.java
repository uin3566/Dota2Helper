package com.youku.player.apiservice;

/**
 * 加密视频回调接口
 * @author jue
 *
 */
public interface IEncryptVideoCallBack {
	
	/** 当前请求视频为加密视频，被调用 **/
	public abstract void onEncryptVideoDetected();

	/** 请求当前加密视频的密码错误,被调用 **/
	public abstract void onEncryptVideoPasswordError();

}
