package com.youku.player.plugin;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baseproject.utils.Logger;
import com.baseproject.utils.UIUtils;
import com.baseproject.utils.Util;
import com.youku.player.base.GoplayException;
import com.youku.player.base.Plantform;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.goplay.AdvInfo;
import com.youku.player.goplay.Profile;
import com.youku.player.goplay.Stat;
import com.youku.player.goplay.VideoAdvInfo;
import com.youku.player.service.DisposableHttpTask;
import com.youku.player.ui.R;
import com.youku.player.ui.interf.IMediaPlayerDelegate;
import com.youku.player.util.DetailMessage;

public class PluginADPlay extends PluginOverlay implements DetailMessage {

	LayoutInflater mLayoutInflater;
	View containerView;
	TextView endPage;
//	TextView ad_more;
	YoukuBasePlayerManager mBasePlayerManager;
	Activity mActivity;
	IMediaPlayerDelegate mediaPlayerDelegate;
	private TextView mCountUpdateTextView;
	private ImageView mSwitchPlayer;
	// youku控件
	private LinearLayout mCountUpdateWrap;
//	private TextView mAdSkip;
	private LinearLayout mAdSkipBlank;

	// 去详情的父view
	private View mSwitchParent;
	protected String TAG = "PluginADPlay";
	private View seekLoadingContainerView;
	private ImageButton play_adButton;

	public static final int ADMORE_BACKGROUND_COLOR_YOUKU = 0xcc292929;
	public static final int ADMORE_BACKGROUND_COLOR_TUDOU = 0xffff6600;

	public static int sAdMoreBackgroundColor = ADMORE_BACKGROUND_COLOR_YOUKU;

	private RelativeLayout mAdPageHolder = null;
	// interactive ad
	private RelativeLayout mInteractiveAdContainer = null;
	private RelativeLayout mInteractiveAdGoFull;
	private org.json.JSONObject mCurrentAdData;
	private boolean isInteractiveAdShow = false;
	private boolean isInteractiveAdHide = false;
	private String mInteractiveAdVideoRs = null; //互动广告对应视频素材

	public PluginADPlay(YoukuBasePlayerManager basePlayerManager,
			IMediaPlayerDelegate mediaPlayerDelegate) {
		super(basePlayerManager.getBaseActivity(), mediaPlayerDelegate);
		this.mediaPlayerDelegate = mediaPlayerDelegate;
		mBasePlayerManager = basePlayerManager;
		mActivity = mBasePlayerManager.getBaseActivity();
		mLayoutInflater = LayoutInflater.from(mActivity);
		init(mActivity);
	}

	private void init(Context context) {
//		if (Profile.PLANTFORM == Plantform.YOUKU) {
			containerView = mLayoutInflater.inflate(
					R.layout.yp_player_ad_youku, null);
//		} else {
//			containerView = mLayoutInflater.inflate(
//					R.layout.yp_player_ad_tudou, null);
//		}
		addView(containerView);
		mCountUpdateTextView = (TextView) containerView
				.findViewById(R.id.my_ad_count);
		if (Profile.PLANTFORM == Plantform.YOUKU) {
			mAdPageHolder = (RelativeLayout) containerView
					.findViewById(R.id.ad_page_holder);
			mInteractiveAdContainer = (RelativeLayout) containerView
					.findViewById(R.id.interactive_ad_container);
			mInteractiveAdGoFull = (RelativeLayout) containerView
					.findViewById(R.id.interactive_ad_gofull_layout);
			mInteractiveAdGoFull.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					isInteractiveAdHide = false;

					mInteractiveAdGoFull.setVisibility(View.GONE);
					mInteractiveAdContainer.setVisibility(View.VISIBLE);
					if (mAdPageHolder != null) {
						mAdPageHolder.setVisibility(View.GONE);
					}
					mBasePlayerManager.goFullScreen();
					mBasePlayerManager.setOrientionDisable();
				}
				
			});
			mCountUpdateWrap = (LinearLayout) containerView
					.findViewById(R.id.my_ad_count_wrap);
			mAdSkipBlank = (LinearLayout) containerView
					.findViewById(R.id.my_ad_blank);
/*			mAdSkip = (TextView) containerView.findViewById(R.id.my_ad_skip);
			mAdSkip.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent();
						intent.setClassName(mActivity.getPackageName(),
								"com.youku.phone.vip.activity.VipProductActivity");
						intent.putExtra("from", 1001);
						intent.putExtra("isVip", false);
						intent.putExtra("video_id",
								mediaPlayerDelegate.videoInfo.getVid());
						intent.putExtra("isFromLocal",
								mediaPlayerDelegate.videoInfo.playType
										.equals(StaticsUtil.PLAY_TYPE_LOCAL));
						intent.putExtra("playlist_id",
								mediaPlayerDelegate.videoInfo.playlistId);
						mActivity.startActivity(intent);
					} catch (Exception e) {

					} finally {
						mActivity.finish();
					}
				}
			});*/
		}
		mSwitchPlayer = (ImageView) containerView
				.findViewById(R.id.gofullscreen);
		mSwitchParent = containerView.findViewById(R.id.gofulllayout);
/*		ad_more = (TextView) containerView.findViewById(R.id.ad_more);
		ad_more.setBackgroundColor(sAdMoreBackgroundColor);*/
		play_adButton = (ImageButton) containerView
				.findViewById(R.id.ib_detail_play_control_ad_play);
		play_adButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Util.hasInternet()
						&& !Util.isWifi()
						&& !PreferenceManager.getDefaultSharedPreferences(
								mActivity).getBoolean("allowONline3G", true)) {
					Toast.makeText(mActivity, "请设置3g/2g允许播放", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				startPlay();
				play_adButton.setVisibility(View.GONE);
			}
		});
		mSwitchParent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mediaPlayerDelegate.isFullScreen) {
					mBasePlayerManager.goSmall();
					if (Profile.PLANTFORM == Plantform.TUDOU) {
						mSwitchPlayer
								.setImageResource(R.drawable.plugin_ad_gofull_tudou);
					} else {
						mSwitchPlayer
								.setImageResource(R.drawable.plugin_ad_gofull_youku);
					}
				} else {
					mBasePlayerManager.goFullScreen();
					if (Profile.PLANTFORM == Plantform.TUDOU) {
						mSwitchPlayer
								.setImageResource(R.drawable.plugin_ad_gosmall_tudou);
					} else {
						mSwitchPlayer
								.setImageResource(R.drawable.plugin_ad_gosmall_youku);
					}
				}
			}
		});

		seekLoadingContainerView = containerView
				.findViewById(R.id.seek_loading_bg);
		initSeekLoading();
	}

	private void startPlay() {
		if (null == mMediaPlayerDelegate)
			return;
		if (!mMediaPlayerDelegate.isAdvShowFinished()) {
			mBasePlayerManager.startPlay();
		} else {
			mMediaPlayerDelegate.start();
		}
	}

	@Override
	public void onBufferingUpdateListener(int percent) {

	}

	@Override
	public void onCompletionListener() {

	}

	@Override
	public boolean onErrorListener(int what, int extra) {
		mActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				containerView.setVisibility(View.GONE);
			}
		});
		return false;
	}

	@Override
	public void OnPreparedListener() {

	}

	@Override
	public void OnSeekCompleteListener() {

	}

	@Override
	public void OnVideoSizeChangedListener(int width, int height) {

	}

	@Override
	public void OnTimeoutListener() {

	}

	@Override
	public void OnCurrentPositionChangeListener(int currentPosition) {
	}

	@Override
	public void onLoadedListener() {
		((Activity) mActivity).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				play_adButton.setVisibility(View.GONE);
				hideLoading();
			}
		});
	}

	@Override
	public void onLoadingListener() {
		((Activity) mActivity).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showLoading();
			}
		});
	}

	@Override
	public void onUp() {

	}

	@Override
	public void onDown() {

	}

	@Override
	public void onFavor() {
	}

	@Override
	public void onUnFavor() {
	}

	@Override
	public void newVideo() {
	}

	@Override
	public void onVolumnUp() {
	}

	@Override
	public void onVolumnDown() {
	}

	@Override
	public void onMute(boolean mute) {
	}

	@Override
	public void onVideoChange() {
		mActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				mCountUpdateTextView.setText("");
				play_adButton.setVisibility(View.GONE);
				mSwitchPlayer.setVisibility(View.GONE);
//				ad_more.setVisibility(View.GONE);
				mSwitchParent.setVisibility(View.GONE);
                if (Profile.PLANTFORM == Plantform.YOUKU) {
//                	mAdSkip.setVisibility(View.GONE);
                	mAdSkipBlank.setVisibility(View.GONE);
                	mCountUpdateWrap.setVisibility(View.GONE);
                }
			}
		});
	}

	boolean isADPluginShowing = false;

	@Override
	public void onVideoInfoGetting() {
		if (isADPluginShowing) {
			/*
			Track.onError(mActivity, mediaPlayerDelegate.nowVid,
					Profile.GUID, mediaPlayerDelegate.videoInfo.playType,
					PlayCode.VIDEO_ADV_RETURN);
					*/
			mBasePlayerManager.interuptAD();
		}
	}

	@Override
	public void onVideoInfoGetted() {
	}

	@Override
	public void onVideoInfoGetFail(boolean needRetry) {
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			isADPluginShowing = true;
			containerView.setVisibility(View.VISIBLE);
		} else {
			isADPluginShowing = false;
			containerView.setVisibility(View.GONE);
		}
	}

	public void notifyUpdate(int count) {

		if (count <= 0) {
			mCountUpdateTextView.setText("");
			mCountUpdateTextView.setVisibility(View.GONE);
			if (Profile.PLANTFORM == Plantform.YOUKU) {
				mCountUpdateWrap.setVisibility(View.GONE);
			}
			return;
		}
		if (mCountUpdateTextView != null) {
			
			if (Profile.PLANTFORM != Plantform.YOUKU) {
				StringBuilder mytext = new StringBuilder("广告剩余时间");
				mytext.append(count).append("秒");
				mCountUpdateTextView.setText(mytext);
				mCountUpdateTextView.setVisibility(View.VISIBLE);
			} else {
				String str = String.valueOf(count);
				mCountUpdateTextView.setText(str);
				mCountUpdateTextView.setVisibility(View.VISIBLE);
				mCountUpdateWrap.setVisibility(View.VISIBLE);
			}
			
		}

		int visibility = mediaPlayerDelegate.isPlayLocalType() ? View.GONE : View.VISIBLE;
		// TODO:要保持“广告剩余时间”和“全屏”,“详细了解”的同步显示，需要把三者处理显示的时机要一致。
		// 目前onStartPlayAD中没有倒计时的参数，故暂时放在这里处理。这些应该在onStartPlayAD方法中处理。
		mSwitchParent.setVisibility(visibility);
		mSwitchPlayer.setVisibility(visibility);

		if (mediaPlayerDelegate.videoInfo.videoAdvInfo != null) {
			AdvInfo advInfo = getAdvInfo();
			if (advInfo == null) {
				Logger.e("PlayFlow", "PlugiADPlay->notifyUpdate    advInfo = null,   return");
				return;
			}

/*			if (TextUtils.isEmpty(advInfo.CU)) {
				ad_more.setVisibility(View.GONE);
			} else {
				if (AdForward.YOUKU_VIDEO == advInfo.CUF) {
					ad_more.setText(R.string.playersdk_ad_descrip_play_youku);
				} else {
					ad_more.setText(R.string.playersdk_ad_descrip_youku);
				}
				ad_more.setVisibility(View.VISIBLE);
			}*/
		}
	}

	@Override
	public void onPluginAdded() {
		super.onPluginAdded();
		if (mediaPlayerDelegate.isFullScreen) {
			if (Profile.PLANTFORM == Plantform.TUDOU) {
				mSwitchPlayer
						.setImageResource(R.drawable.plugin_ad_gosmall_tudou);
			} else {
				mSwitchPlayer.setImageResource(R.drawable.plugin_ad_gosmall_youku);
			}
		} else {
			if (Profile.PLANTFORM == Plantform.TUDOU) {
				mSwitchPlayer
						.setImageResource(R.drawable.plugin_ad_gofull_tudou);
			} else {
				mSwitchPlayer.setImageResource(R.drawable.plugin_ad_gofull_youku);
			}
		}
		if (mediaPlayerDelegate.videoInfo.videoAdvInfo != null) {
			final VideoAdvInfo adInfo = mediaPlayerDelegate.videoInfo.videoAdvInfo;

/*			ad_more.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (adInfo.VAL.size() <= 0) {
						return;
					}

					AdvInfo advInfo = adInfo.VAL.get(0);
					if (advInfo == null) {
						return;
					}
					String url = advInfo.CU;
					Logger.e("PlayFlow", "点击url-->" + url);

					if (url == null || TextUtils.getTrimmedLength(url) <= 0) {
						return;
					}
					DisposableStatsUtils.disposeCUM(advInfo);
					new AdvClickProcessor().processAdvClick(mActivity, url, advInfo.CUF);
				}
			});*/
		}
		if (UIUtils.hasKitKat()) {
			mBasePlayerManager.hideSystemUI(this);
		}
		mBasePlayerManager.setPluginHolderPaddingZero();
	}

	/**
	 * 获取广告信息
	 * 
	 * @return
	 */
	private AdvInfo getAdvInfo() {
		try {
			return mediaPlayerDelegate.videoInfo.videoAdvInfo.VAL.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送广告统计信息
	 * 
	 * @param stat
	 */
	private void sendStat(Stat stat) {
		new DisposableHttpTask(stat.U).start();
	}

	private void initSeekLoading() {
		if (null == seekLoadingContainerView)
			return;
		playLoadingBar = (SeekBar) seekLoadingContainerView
				.findViewById(R.id.loading_seekbar);
		if (null != playLoadingBar)
			playLoadingBar
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {

						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {

						}

						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
							if (fromUser) {
								//Track.setTrackPlayLoading(false);
								return;
							} else {
								seekBar.setProgress(progress);
							}

						}
					});
	}

	private int seekcount = 0;

	public void showLoading() {

		if (null != seekLoadingContainerView) {
			if (seekLoadingContainerView.getVisibility() == View.GONE) {
				seekLoadingContainerView.setVisibility(View.VISIBLE);
				seekcount = 0;
				seekHandler.sendEmptyMessageDelayed(0, 50);

			}
			if (null != mMediaPlayerDelegate
					&& mMediaPlayerDelegate.getCurrentPosition() > 1000) {
				seekendHandler.sendEmptyMessageDelayed(0, 50);
				seekLoadingContainerView.setBackgroundResource(0);
			} else {
				seekLoadingContainerView
						.setBackgroundResource(R.drawable.bg_play);
			}
		}
	}

	public void hideLoading() {
		((Activity) mActivity).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (null != seekLoadingContainerView) {
					seekLoadingContainerView.setVisibility(View.GONE);
					playLoadingBar.setProgress(0);
				}
				if (null != seekHandler)
					seekHandler.removeCallbacksAndMessages(null);
			}
		});
	}

	private Handler seekHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (seekcount < 50) {
				seekcount++;
				playLoadingBar.setProgress(seekcount);
				Thread temp = new Thread(new Runnable() {

					@Override
					public void run() {
						seekHandler.sendEmptyMessageDelayed(0, 50);
					}
				});
				temp.run();
			} else {
				playLoadingBar.setProgress(50);
			}

		}

	};

	private SeekBar playLoadingBar;
	private Handler seekendHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			if (seekcount < 100) {
				seekcount++;
				playLoadingBar.setProgress(seekcount);
				Thread temp = new Thread(new Runnable() {

					@Override
					public void run() {
						seekHandler.sendEmptyMessageDelayed(0, 10);
					}
				});
				temp.run();
			}

		}

	};

	@Override
	public void onNotifyChangeVideoQuality() {

	}

	@Override
	public void onRealVideoStart() {
	}

	@Override
	public void onADplaying() {
	}

	@Override
	public void onRealVideoStarted() {

	}

	@Override
	public void onStart() {

	}

	@Override
	public void onClearUpDownFav() {

	}

	@Override
	public void onPause() {

	}

	public void showPlayIcon() {
		play_adButton.setVisibility(View.VISIBLE);
	}

	@Override
	public void back() {
	}

	@Override
	public void onPlayNoRightVideo(GoplayException e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayReleateNoRightVideo() {
		// TODO Auto-generated method stub

	}

	public static void setAdMoreBackgroundColor(boolean isTudouPlatform) {
		if (isTudouPlatform) {
			sAdMoreBackgroundColor = ADMORE_BACKGROUND_COLOR_TUDOU;
			return;
		}
		sAdMoreBackgroundColor = ADMORE_BACKGROUND_COLOR_YOUKU;
	}

	public boolean isCountUpdateVisible() {
		if (mCountUpdateTextView != null) {
			return mCountUpdateTextView.getVisibility() == View.VISIBLE ? true
					: false;
		}
		return false;
	}

	public void setSkipVisible(boolean visible) {
/*		if (MediaPlayerConfiguration.getInstance().showSkipAdButton() && mAdSkip != null) {
			mAdSkip.setVisibility(visible ? View.VISIBLE : View.GONE);
			if (mAdSkipBlank != null) {
				mAdSkipBlank.setVisibility(visible ? View.VISIBLE : View.GONE);
			}
		}*/
	}
}
