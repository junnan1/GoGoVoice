package com.ftf.gogo.playbeep;


import com.ftf.gogo.R;

import android.content.Context;
import android.media.MediaPlayer;


public class PlapBeeps {
	 public Context context;
	    public PlapBeeps(Context context) {
	        this.context = context;
	    }
	    
	    
	  //������Ϣǰ������������ʾ
	    public void smsBeep() {
			MediaPlayer mediaPlayer;
			mediaPlayer = MediaPlayer.create(context, R.raw.sendsms);// ��Ƶ�ļ�
			mediaPlayer.start();

			try {
				new Thread().sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		 //ÿ�δ�gogo��ʱ��
	    public void startBeep() {
			MediaPlayer mediaPlayer;
			mediaPlayer = MediaPlayer.create(context, R.raw.beep_start);// ��Ƶ�ļ�
			mediaPlayer.start();

			try {
				new Thread().sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	    
		
		//ÿ��ʶ����ϵ�����
	    public void stopBeep() {
			MediaPlayer mediaPlayer;
			mediaPlayer = MediaPlayer.create(context, R.raw.beep_stop_ogg);// ��Ƶ�ļ�
			mediaPlayer.start();
			try {
				new Thread().sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
