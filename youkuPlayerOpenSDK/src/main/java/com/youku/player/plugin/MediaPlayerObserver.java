package com.youku.player.plugin;

/**
 * Class MediaPlayerObserver
 */
public interface MediaPlayerObserver {

	//
	// Methods
	//

	//
	// Accessor methods
	//

	//
	// Other methods
	//

	/**
	 * 缓冲的通知
	 * 
	 * @param percent
	 *            目前缓存的百分比
	 */
	public void onBufferingUpdateListener(int percent);

	/**
	 * 播放完成的通知
	 */
	public void onCompletionListener();

	/**
	 * 出现错误的通知
	 * 
	 * @param extra
	 *            错误携带的参数
	 * @param what
	 *            错误代码
	 * @return
	 */
	public boolean onErrorListener(int what, int extra);

	/**
	 * 准备完成的通知
	 */
	public void OnPreparedListener();

	/**
	 * seek完成的通知
	 */
	public void OnSeekCompleteListener();

	/**
	 * 视频的宽高比发生变化的通知
	 * 
	 * @param height
	 *            高度
	 * @param width
	 *            宽度
	 */
	public void OnVideoSizeChangedListener(int width, int height);

	/**
	 * 超时通知
	 */
	public void OnTimeoutListener();

	/**
	 * 播放时间点变化通知
	 * 
	 * @param currentPosition
	 *            当前的时间点，单位毫秒
	 */
	public void OnCurrentPositionChangeListener(int currentPosition);

	/**
	 * 已经加载完成的通知
	 */
	public void onLoadedListener();

	/**
	 * 正在加载的通知
	 * 
	 */
	public void onLoadingListener();

	/**
	 * 改变清晰度的通知
	 */
	public void onNotifyChangeVideoQuality();

	/**
	 * 正片开始播放
	 */
	public void onRealVideoStarted();

}
