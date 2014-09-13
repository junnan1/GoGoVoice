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

	// 得到手机系统应用列表、
	public HashMap<String, String> getApplicationName() {
		HashMap<String, String> map = null;
		PackageManager pm = context.getPackageManager();
		// 得到PackageManager对象
		List<PackageInfo> packs = pm.getInstalledPackages(0);
		map = new HashMap<String, String>();
		// 得到系统 安装的所有程序包的PackageInfo对象
		for (PackageInfo pi : packs) {
			map.put((String) pi.applicationInfo.loadLabel(pm), pi.packageName);
		  //System.out.println("application info: "+pi.applicationInfo+",packageName: "+pi.packageName+",className: "+pi.getClass());
		}
		return map;
	}
	

}
