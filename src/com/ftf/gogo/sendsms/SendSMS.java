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
	// �����������Ի���
	ProgressDialog xh_pDialog;
	
	 public Context context;
	    public SendSMS(Context context) {
	        this.context = context;
	    }
	    
	    public void sendMessage(String mobile,String message){
			// �ƶ���Ӫ������ÿ�η��͵��ֽ��������ޣ����ǿ���ʹ��Android�������ṩ �Ķ��Ź��ߡ�
			if (message != null) {
				SmsManager sms = SmsManager.getDefault();
				try {
					 if(message.length() >= 70){  
						// �������û�г������Ƴ��ȣ��򷵻�һ�����ȵ�List��
						List<String> texts = sms.divideMessage(message);
						for (String text : texts) {
							sms.sendTextMessage(mobile, null, text, null, null);
						}
					 }else{
						 sms.sendTextMessage(mobile, null, message, null, null);
					 }
					 insertMesToSys(mobile, message);
					 
//					 progress();
					
					Toast.makeText(context, "���ͳɹ�", Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(context, "����ʧ��", Toast.LENGTH_LONG).show();
				}
			}
		}
		
		public void insertMesToSys(String mobile,String message){
//			Context context = context.getApplicationContext();
			ContentResolver resolver = context.getContentResolver();
			Uri uri = Uri.parse("content://sms/");
			ContentValues values = new ContentValues();
			values.put("address", mobile);
			// 1:���˷����� 2:�㷢������
			values.put("type", 2);
			values.put("date", System.currentTimeMillis());
			values.put("body", message);
			resolver.insert(uri, values);
		}
		
		public void progress(){


			xh_count = 0;

			// ����ProgressDialog����
			xh_pDialog = new ProgressDialog(context);

			// ���ý�������񣬷��ΪԲ�Σ���ת��
			xh_pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

			// ����ProgressDialog ����
			xh_pDialog.setTitle("��ʾ");

			// ����ProgressDialog��ʾ��Ϣ
			xh_pDialog.setMessage("����һ�����ν������Ի���");

			// ����ProgressDialog����ͼ��
			xh_pDialog.setIcon(R.drawable.ic_select);

			// ����ProgressDialog �Ľ������Ƿ���ȷ false ���ǲ�����Ϊ����ȷ
			xh_pDialog.setIndeterminate(false);

			// ����ProgressDialog ����������
			xh_pDialog.setProgress(100);

			// ����ProgressDialog �Ƿ���԰��˻ؼ�ȡ��
			xh_pDialog.setCancelable(true);

			// ��ProgressDialog��ʾ
			xh_pDialog.show();

			new Thread() {
				@Override
				public void run() {
					try {
						while (xh_count <= 100) {
							// ���߳������ƽ���
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
