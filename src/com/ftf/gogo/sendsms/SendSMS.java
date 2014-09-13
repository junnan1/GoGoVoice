package com.ftf.gogo.sendsms;

import java.util.List;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.ftf.gogo.R;


public class SendSMS {
	int xh_count = 0;
	// 声明进度条对话框
	ProgressDialog xh_pDialog;
	
	 public Context context;
	    public SendSMS(Context context) {
	        this.context = context;
	    }
	    
	    public void sendMessage(String mobile,String message){
			// 移动运营商允许每次发送的字节数据有限，我们可以使用Android给我们提供 的短信工具。
			if (message != null) {
				SmsManager sms = SmsManager.getDefault();
				try {
					 if(message.length() >= 70){  
						// 如果短信没有超过限制长度，则返回一个长度的List。
						List<String> texts = sms.divideMessage(message);
						for (String text : texts) {
							sms.sendTextMessage(mobile, null, text, null, null);
						}
					 }else{
						 sms.sendTextMessage(mobile, null, message, null, null);
					 }
					 insertMesToSys(mobile, message);
					 
//					 progress();
					
					Toast.makeText(context, "发送成功", Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(context, "发送失败", Toast.LENGTH_LONG).show();
				}
			}
		}
		
		public void insertMesToSys(String mobile,String message){
//			Context context = context.getApplicationContext();
			ContentResolver resolver = context.getContentResolver();
			Uri uri = Uri.parse("content://sms/");
			ContentValues values = new ContentValues();
			values.put("address", mobile);
			// 1:别人发给你 2:你发给别人
			values.put("type", 2);
			values.put("date", System.currentTimeMillis());
			values.put("body", message);
			resolver.insert(uri, values);
		}
		
		public void progress(){


			xh_count = 0;

			// 创建ProgressDialog对象
			xh_pDialog = new ProgressDialog(context);

			// 设置进度条风格，风格为圆形，旋转的
			xh_pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

			// 设置ProgressDialog 标题
			xh_pDialog.setTitle("提示");

			// 设置ProgressDialog提示信息
			xh_pDialog.setMessage("这是一个长形进度条对话框");

			// 设置ProgressDialog标题图标
			xh_pDialog.setIcon(R.drawable.ic_select);

			// 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
			xh_pDialog.setIndeterminate(false);

			// 设置ProgressDialog 进度条进度
			xh_pDialog.setProgress(100);

			// 设置ProgressDialog 是否可以按退回键取消
			xh_pDialog.setCancelable(true);

			// 让ProgressDialog显示
			xh_pDialog.show();

			new Thread() {
				@Override
				public void run() {
					try {
						while (xh_count <= 100) {
							// 由线程来控制进度
							xh_pDialog.setProgress(xh_count++);
							Thread.sleep(100);
						}
						xh_pDialog.cancel();
					} catch (Exception e) {
						xh_pDialog.cancel();
					}
				}
			}.start();

		
		}
}
