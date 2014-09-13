/**
 * 
 */
package com.ftf.gogo.openapp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * @author tengfei
 * 
 */
public final class GetApplicationName {
	public Context context;

	HashMap<String, String> map = null;
	HashMap<Object, String> map6 = null;

	public GetApplicationName(Context context) {
		this.context = context;
	}

	// �õ��ֻ�ϵͳӦ���б�
	public HashMap<String, String> getApplicationName() {
		HashMap<String, String> map = null;
		PackageManager pm = context.getPackageManager();
		// �õ�PackageManager����
		List<PackageInfo> packs = pm.getInstalledPackages(0);
		map = new HashMap<String, String>();
		// �õ�ϵͳ ��װ�����г������PackageInfo����
		for (PackageInfo pi : packs) {
			map.put((String) pi.applicationInfo.loadLabel(pm), pi.packageName);
		  //System.out.println("application info: "+pi.applicationInfo+",packageName: "+pi.packageName+",className: "+pi.getClass());
		}
		return map;
	}
	

}
