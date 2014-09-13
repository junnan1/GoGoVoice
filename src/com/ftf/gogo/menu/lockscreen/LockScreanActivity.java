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
	 * �豸�ĳ�������Ա��
	 */
	private DevicePolicyManager dpm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.lock_screan);
		dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

		lock();
	}

	/*// ������Ļ
	@SuppressLint("NewApi")
	public void lockScreen(View view) {
		// �ж� �Ƿ��Ѿ��������豸�ĳ�������Ա��
		// ָ�� ��������Ա�������
		ComponentName mDeviceAdminSample = new ComponentName(this,MyAdmin.class);
		if (dpm.isAdminActive(mDeviceAdminSample)) {
			//dpm.resetPassword("123", 0); �����������룬
			dpm.resetPassword("", 0);
			dpm.lockNow();
		}else{
			Toast.makeText(this, "���ȿ����豸�ĳ�������Ա", 0).show();
		}
	}*/
	
	@SuppressLint("NewApi")
	public void lock() {
		// �ж� �Ƿ��Ѿ��������豸�ĳ�������Ա��
		// ָ�� ��������Ա�������
		ComponentName mDeviceAdminSample = new ComponentName(this,
				MyAdmin.class);
		if (dpm.isAdminActive(mDeviceAdminSample)) {
			// dpm.resetPassword("123", 0); �����������룬
			dpm.resetPassword("", 0);
			dpm.lockNow();
		} else {
			Toast.makeText(this, "���ȿ����豸�ĳ�������Ա", 0).show();
		}
		//����������
		 Intent intent = new Intent();  
		 intent.setClass(LockScreanActivity.this,Main.class);
    	 startActivity(intent);
	}

	// �����豸�ĳ�������Ա
	public void activieAdmin(View view) {
		// Launch the activity to have the user enable our admin.
		// ʵ����һ����ͼ ���� ���һ����������Ա DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		// ָ�� ��������Ա�������
		ComponentName mDeviceAdminSample = new ComponentName(this,MyAdmin.class);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,mDeviceAdminSample);

		// �����û� ΪʲôҪ��������������Ա
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"�ף������Һ���Ϳ���һ��������");
		startActivity(intent);
	}
}
