package com.ftf.gogo.playbeep;


import com.ftf.gogo.R;

import android.content.Context;
import android.media.MediaPlayer;


public class PlapBeeps {
	 public Context context;
	    public PlapBeeps(Context context) {
	        this.context = context;
	    }
	    
	    
	  //发送信息前，播放语音提示
	    public void smsBeep() {
			MediaPlayer mediaPlayer;
			mediaPlayer = MediaPlayer.create(context, R.raw.sendsms);// 音频文件
			mediaPlayer.start();

			try {
				new Thread().sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		 //每次打开gogo的时候
	    public void startBeep() {
			MediaPlayer mediaPlayer;
			mediaPlayer = MediaPlayer.create(context, R.raw.beep_start);// 音频文件
			mediaPlayer.start();

			try {
				new Thread().sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	    
		
		//每次识别完毕的语音
	    public void stopBeep() {
			MediaPlayer mediaPlayer;
			mediaPlayer = MediaPlayer.create(context, R.raw.beep_stop_ogg);// 音频文件
			mediaPlayer.start();
			try {
				new Thread().sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
