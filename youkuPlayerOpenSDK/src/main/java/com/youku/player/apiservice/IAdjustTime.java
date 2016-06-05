package com.youku.player.apiservice;

/**
 * 时间校正接口，获取服务器时间与本地时间差值，用服务器时间减去本地时间。 
 *
 */
public interface IAdjustTime {
	public long getTimeDifference();
}
