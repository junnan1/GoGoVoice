package com.ftf.gogo.menu.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ftf.gogo.R;
import com.ftf.gogo.menu.selfme.SelfMeActivity;

public class SetActivity extends Activity {
	private LinearLayout ll_feedback,ll_about,ll_weibo,ll_voice,ll_shake,ll_message;
	private Intent intent;
	
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.setting_layout);
		
		ll_feedback = (LinearLayout) findViewById(R.id.ll_feedback);
		ll_about = (LinearLayout) findViewById(R.id.ll_about);
		ll_weibo = (LinearLayout) findViewById(R.id.ll_weibo);
		ll_voice = (LinearLayout) findViewById(R.id.ll_voice);
		ll_shake = (LinearLayout) findViewById(R.id.ll_shake);
		ll_message = (LinearLayout) findViewById(R.id.ll_message);
	}
	
	public void my_feedback(View v){
		intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://weibo.com/fengtengfei90"));
	    startActivity(intent); 
	}
	
	public void myAbout(View v){
		intent.setClass(SetActivity.this,SelfMeActivity.class);
  	    startActivity(intent);
	}
	public void myWeibo(View v){
		intent = new Intent(Intent.ACTION_WEB_SEARCH);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(SearchManager.QUERY, "http://weibo.com/fengtengfei90");
		Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
		intent.putExtra(SearchManager.APP_DATA, appData);
		startActivity(intent);
	}
	
	public void myVoice(View v){
		final String[] voices = {"大", "中", "小", "静音"};
		final String name = "声音";
		select(voices, name);
		
	}
	public void myShake(View v){
		 final String[] shakes = {"较强", "适中", "较弱", "无"};
		final String name = "震动提示方式";
		select(shakes,name);
	}
	
	public void myMessage(View v){
		final String[] message = {"弹出显示内容", "弹出不显示内容", "静音"};
		final String name = "消息提示方式";
		select(message,name);
	}

	public void select(final String[] strs,final String name){
		AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
		builder.setIcon(R.drawable.ic_select);
        builder.setTitle("选择"+name);
        //    指定下拉列表的显示数据
        //final String[] strs = {"弹出显示内容", "弹出不显示内容", "静音"};
        //    设置一个下拉的列表选择项
        builder.setItems(strs, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(SetActivity.this, "您选择的"+name+"为： "+ strs[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
	}
	
	
}
