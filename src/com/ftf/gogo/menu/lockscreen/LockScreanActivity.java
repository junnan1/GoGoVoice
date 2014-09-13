package com.ftf.gogo.menu.lockscreen;



import com.ftf.gogo.Main;
import com.ftf.gogo.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LockScreanActivity extends Activity {

	/**
	 * 设备的超级管理员。
	 */
	private DevicePolicyManager dpm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.lock_screan);
		dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

		lock();
	}

	/*// 锁定屏幕
	@SuppressLint("NewApi")
	public void lockScreen(View view) {
		// 判断 是否已经开启了设备的超级管理员。
		// 指定 超级管理员的组件名
		ComponentName mDeviceAdminSample = new ComponentName(this,MyAdmin.class);
		if (dpm.isAdminActive(mDeviceAdminSample)) {
			//dpm.resetPassword("123", 0); 可以设置密码，
			dpm.resetPassword("", 0);
			dpm.lockNow();
		}else{
			Toast.makeText(this, "请先开启设备的超级管理员", 0).show();
		}
	}*/
	
	@SuppressLint("NewApi")
	public void lock() {
		// 判断 是否已经开启了设备的超级管理员。
		// 指定 超级管理员的组件名
		ComponentName mDeviceAdminSample = new ComponentName(this,
				MyAdmin.class);
		if (dpm.isAdminActive(mDeviceAdminSample)) {
			// dpm.resetPassword("123", 0); 可以设置密码，
			dpm.resetPassword("", 0);
			dpm.lockNow();
		} else {
			Toast.makeText(this, "请先开启设备的超级管理员", 0).show();
		}
		//跳回主界面
		 Intent intent = new Intent();  
		 intent.setClass(LockScreanActivity.this,Main.class);
    	 startActivity(intent);
	}

	// 开启设备的超级管理员
	public void activieAdmin(View view) {
		// Launch the activity to have the user enable our admin.
		// 实例化一个意图 动作 添加一个超级管理员 DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		// 指定 超级管理员的组件名
		ComponentName mDeviceAdminSample = new ComponentName(this,MyAdmin.class);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,mDeviceAdminSample);

		// 告诉用户 为什么要添加这个超级管理员
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"亲，开启我后你就可以一键锁屏了");
		startActivity(intent);
	}
}
