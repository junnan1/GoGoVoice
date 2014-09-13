package com.ftf.gogo.openapp;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
/**
 * 启动 第三方 应用
 * 
 * @author tengfei
 * 功能描述：通过应用包名 启动第三方 应用
 * 
 */
public class LaunchApp {
 private Context context;// 上下文对象
 private PackageManager mPackManager;// PackageManager对象
 /**
  * 有参 构造函数
  * 
  * @param context 上下文对象
  * 
  */
 public LaunchApp(Context context) {
  // 获取PackageManager对象
  mPackManager = context.getPackageManager();
  // 上下文 对象
  this.context = context;
 }
 /**
  * 根据 应用包名 启动 第三方 应用
  * 
  * @param package_name
  *            应用包名
  * @return 第三方应用启动对象
  */
 public Intent getLaunchIntentForPackage(String package_name) {
  if (package_name == null || context == null || mPackManager == null )//|| mPacks == null
   return null;
  Intent launch_intent = null;// 第三应用启动对象
  // 根据 包名 获取 第三方应用启动对象
  launch_intent = mPackManager.getLaunchIntentForPackage(package_name);
  return launch_intent;
 }
 /**
  * 释放 资源函数
  */
 public void release() {
  mPackManager=null;
  this.context = null;
 }
 
}