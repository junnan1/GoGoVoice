package com.ftf.gogo.openapp;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
/**
 * ���� ������ Ӧ��
 * 
 * @author tengfei
 * ����������ͨ��Ӧ�ð��� ���������� Ӧ��
 * 
 */
public class LaunchApp {
 private Context context;// �����Ķ���
 private PackageManager mPackManager;// PackageManager����
 /**
  * �в� ���캯��
  * 
  * @param context �����Ķ���
  * 
  */
 public LaunchApp(Context context) {
  // ��ȡPackageManager����
  mPackManager = context.getPackageManager();
  // ������ ����
  this.context = context;
 }
 /**
  * ���� Ӧ�ð��� ���� ������ Ӧ��
  * 
  * @param package_name
  *            Ӧ�ð���
  * @return ������Ӧ����������
  */
 public Intent getLaunchIntentForPackage(String package_name) {
  if (package_name == null || context == null || mPackManager == null )//|| mPacks == null
   return null;
  Intent launch_intent = null;// ����Ӧ����������
  // ���� ���� ��ȡ ������Ӧ����������
  launch_intent = mPackManager.getLaunchIntentForPackage(package_name);
  return launch_intent;
 }
 /**
  * �ͷ� ��Դ����
  */
 public void release() {
  mPackManager=null;
  this.context = null;
 }
 
}