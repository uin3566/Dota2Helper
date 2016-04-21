package com.youku.player;

import android.content.Context;
import android.os.Handler;

import com.decapi.DecAPI;
import com.youku.player.ui.R;
import com.youku.service.download.DownloadManager;


public abstract class YoukuPlayerBaseConfiguration extends YoukuPlayerConfiguration {

	public YoukuPlayerBaseConfiguration(Context applicationContext) {
		super(applicationContext);
		DownloadManager.getInstance();		

		DecAPI.init(context,R.raw.aes);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DownloadManager.getInstance().startNewTask();
			}
		}, 1000);
	}


	@Override
	public int getNotifyLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.notify;
	}
	
	public static void exit(){
		YoukuPlayerConfiguration.exit();
		DownloadManager.getInstance().unregister();
	}


}
