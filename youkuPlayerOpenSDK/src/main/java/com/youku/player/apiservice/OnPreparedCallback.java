package com.youku.player.apiservice;

public abstract class OnPreparedCallback {

	/** 当全部下载已准备的时候 */
	public abstract void onAllPrepared();

	/** 当下载准备的时候 */
	public void onOnePrepared() {
	}

	/** 下载失败 */
	public void onOneFailed() {
	}
}
