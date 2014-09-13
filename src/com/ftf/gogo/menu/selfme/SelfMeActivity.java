package com.ftf.gogo.menu.selfme;

import com.ftf.gogo.Main;
import com.ftf.gogo.MyContenxt;
import com.ftf.gogo.R;
import com.ftf.gogo.R.id;
import com.ftf.gogo.R.layout;
import com.ftf.gogo.openapp.LaunchApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SelfMeActivity extends Activity {
	
private TextView mChangeCommentTextView;
	
	private EditText mCommentEditText;
	
	private boolean  mBCommentEditable;
	
	private InputMethodManager mKeyBorad;
	
	private String mCommentTmpString;			
	
	
	
	private LinearLayout mWeibo;
	private LinearLayout mMaiLayout;
	private LinearLayout mChat;
	MyContenxt mct = new MyContenxt();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.self_info_me);
		
		init();
	}
	
	
private void init(){		
		final Context context = mct.getInstance();	
	
		mCommentEditText = (EditText) findViewById(R.id.edit_comment);
		
		mBCommentEditable = false;

		mKeyBorad = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);  
		
		View view1 = findViewById(R.id.self_info_wmc);
		mWeibo = (LinearLayout) view1.findViewById(R.id.weibo);
		mWeibo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(SelfMeActivity.this, "You Press QZONE", Toast.LENGTH_SHORT).show();
				if(isPkgInstalled(context,"com.sina.weibo")){
					Intent intent = new LaunchApp(SelfMeActivity.this).getLaunchIntentForPackage("com.sina.weibo");
					startActivity(intent);
					return;
				}
				Toast.makeText(SelfMeActivity.this, "手机中没有安装微博，请登陆网页版！", Toast.LENGTH_SHORT).show();
				//不限制浏览器，打开开发者微博的界面
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://weibo.com/fengtengfei90"));
			    startActivity(intent); 
			}
		});
		
		mMaiLayout = (LinearLayout) view1.findViewById(R.id.mail);
		mMaiLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isPkgInstalled(context,"com.netease.mobimail")){
					Intent intent = new LaunchApp(SelfMeActivity.this).getLaunchIntentForPackage("com.netease.mobimail");
					startActivity(intent);
					return;
				}
				Toast.makeText(SelfMeActivity.this, "手机中没有安装163邮箱，请登陆网页版！", Toast.LENGTH_SHORT).show();
				//不限浏览器，打开163邮箱的登陆界面
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mail.163.com/"));
			    startActivity(intent); 
			}
		});
		
		mChat = (LinearLayout) view1.findViewById(R.id.chat);
		mChat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// com.tencent.mobileqq
				if(isPkgInstalled(context,"com.tencent.mobileqq")){
					//启动QQ
					Intent intent = new LaunchApp(SelfMeActivity.this).getLaunchIntentForPackage("com.tencent.mobileqq");
					startActivity(intent);
					return;
				}
				Toast.makeText(SelfMeActivity.this, "您的手机中没有按照QQ应用，请下载！",Toast.LENGTH_SHORT).show();
			}
		});
	}


	private  boolean isPkgInstalled(Context context, String packageName) {  
	    PackageManager packageManager = context.getPackageManager();  
	    try {  
	        PackageInfo pInfo = packageManager.getPackageInfo(packageName,  
	                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT); 
	        //判断是否获取到了对应的包名信息 
	        if(pInfo!=null){  
	            return true;
	        }  
	    } catch (NameNotFoundException e) {  
	        e.printStackTrace();  
	    }  
	    return false;
	} 

	//判断手机中是否存在在某个应用
	private boolean isPkgInstalled(String pkgName) {
		PackageInfo packageInfo = null;
		try {
			packageInfo = this.getPackageManager().getPackageInfo(pkgName, 0);
		} catch (NameNotFoundException e) {
			packageInfo = null;
			e.printStackTrace();
		}
		if (packageInfo == null) {
			return false;
		} else {
			return true;
		}
	}
}
