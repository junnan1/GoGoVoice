package com.ftf.gogo.makephone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

public class CallPhone {

	 public Context context;
	    public CallPhone(Context context) {
	        this.context = context;
	    }
	
	 public void MakePhone(String number){
			//��ͼ�����ʲô��
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_CALL);
			//uri ͬ����Դ��λ�� > url
			intent.setData(Uri.parse("tel:"+number));
			context.startActivity(intent);
	    }
	
}
