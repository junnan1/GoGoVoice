package com.ftf.gogo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ftf.gogo.R.string;
import com.ftf.gogo.makephone.CallPhone;
import com.ftf.gogo.makephone.GetContacts;
import com.ftf.gogo.menu.lockscreen.LockScreanActivity;
import com.ftf.gogo.menu.rate.RateActivity;
import com.ftf.gogo.menu.sector.MenuItemView;
import com.ftf.gogo.menu.sector.MyAnimations;
import com.ftf.gogo.menu.sector.OnItemClickListener;
import com.ftf.gogo.menu.selfme.SelfMeActivity;
import com.ftf.gogo.menu.setting.SetActivity;
import com.ftf.gogo.openapp.GetApplicationName;
import com.ftf.gogo.openapp.LaunchApp;
import com.ftf.gogo.playbeep.PlapBeeps;
import com.ftf.gogo.sendsms.ListAdapter;
import com.ftf.gogo.sendsms.ListItem;
import com.ftf.gogo.sendsms.SendSMS;
import com.ftf.gogo.tools.GetPinyin;
import com.ftf.gogo.tools.MaxMatch;
import com.ftf.gogo.tools.ReplaceStr;
import com.sogou.speech.listener.RecognizerListener;
import com.sogou.speech.ui.RecognizerDialog;

/**
 * @author tengfei
 * 
 * 	程序主入口
 */
public class Main extends Activity implements OnItemClickListener, OnClickListener{
	//定义小原点菜单
	private ImageView imgPlusLB;
	private MenuItemView myViewRB;
	private ImageView imgPlusRB;
	
	
	private Button btn,button2;
	private EditText mResultsText;
	private RecognizerDialog dialog = null;
	private AlertDialog alertDialog = null;
	public Intent intent;

	// 定义listview的组件
	private ListView listView;
	private View listItem;
	private List<ListItem> list;
	private ListAdapter listAdapter;
	GetPinyin gpy = new GetPinyin();
	private static String number;
	private static String name;
	//实现搜索的关键字
	private String keyword;
	private static int j;//设置的全部变量，方便在发信息的时候，能够获取到相应列表的值
	
	// applied APP ID
	private String appId = "DF465202";
	// applied Access Key
	private String accessKey = "EA382D6A";
	// set isAutoStop true in order to stop recording automatically,
	// default value is true
	private boolean isAutoStop = true;
	// set isContinuous true in order to get real-time partial recognized
	// results, default value is false
	private boolean isContinuous = false;

	// Save the results of recognizing
	private List<List<String>> wholeResult;
	
	//播放提示音
	PlapBeeps playBeeps =  new PlapBeeps(Main.this);

	private RecognizerListener listener = new RecognizerListener() {

		@Override
		// call it when the last package has received valid results
		public void onResults(List<List<String>> results) {
			addToWholeResult(results);
			showResults(mResultsText, results);
		}

		@Override
		// call it when some error occurs
		public void onError(int err) {
			// TODO: show error code for users when necessary
		}

		@Override
		// call it when the former packages have received valid results
		public void onPartResults(List<List<String>> results) {
			addToWholeResult(results);
			showResults(mResultsText, results);
		}

		@Override
		// call it when the last package has no valid result but the former
		// packages have some valid results
		public void onQuitQuietly(int err) {
			// TODO: this is not an error, no need to show it for users
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dialog = new RecognizerDialog(Main.this, appId, accessKey, isAutoStop,isContinuous);
		dialog.setlistener(listener);
		setContentView(R.layout.main);

		bindViews();
		setListeners();
	}

	@Override
	protected void onDestroy() {
		if (dialog != null)
			dialog.dismiss();
		super.onDestroy();
	}

	private void bindViews() {
		mResultsText = (EditText) findViewById(R.id.text);
		btn = (Button) findViewById(R.id.button1);
		
		//小圆点菜单
		findViewById(R.id.relLayRB).setOnClickListener(this);
		
		myViewRB = (MenuItemView) findViewById(R.id.myViewRB);
		myViewRB.setPosition(MenuItemView.POSITION_RIGHT_BOTTOM);
		myViewRB.setRadius(150);
		imgPlusRB = (ImageView) findViewById(R.id.imgPlusRB);
		//设置+号上的菜单。
		setMenuItemView();
		
		//ListView
		listView = (ListView) findViewById(R.id.listitem);
		
		listAdapter = new ListAdapter(this);
		listView.setAdapter(listAdapter);

		list = new ArrayList<ListItem>();
		
		//初始化app启动的界面
		lanchInit();
	}

	private void setListeners() {
		// Click Listener
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//播放识别开始的声音
				playBeeps.startBeep();
				
				wholeResult = new ArrayList<List<String>>();
				mResultsText.setText("");
				dialog.setAutoStop(true);// 设置录音是否自动停止
				dialog.setContinuous(false);// 设置是否连续识别
				dialog.show();
			}
		});
		
	}

	//把识别结果放到一个List集合中
	// add recognized results to the wholeResult
	private void addToWholeResult(List<List<String>> ds) {
		//播放识别结束的
		playBeeps.stopBeep();
		
		if (ds != null) {
			int tmpAmount = ds.size();
			for (int i = 0; i < tmpAmount; i++) {
				List<String> tmpS = new ArrayList<String>();

				for (int j = 0; j < ds.get(i).size(); j++) {
					tmpS.add(ds.get(i).get(j));
				}
				wholeResult.add(tmpS);
			}
		}
	}

	// show wholeResult for users when necessary
	private void showResults(EditText textView, List<List<String>> results) {
		CallPhone callPhone = new CallPhone(Main.this);
		LaunchApp launchApp = new LaunchApp(Main.this);
		
		String result = "";
		for (List<String> list : results) {
			result += list.get(0);
		}
		
		/*普通文本的识别，识别结果放到EditTest框中
		showListItem(result);
		textView.append(result);*/
		
		System.out.println("start result: "+result);
		
		HashMap<String, String> contactsMap = new GetContacts(Main.this).getNamePhone();
		Set<Map.Entry<String, String>> contractSet = contactsMap.entrySet();
		Iterator<Map.Entry<String, String>> contractIterator = contractSet.iterator();
		Map.Entry<String, String> contractEntry;
		// 实现附加功能

		// 遍历联系人集合，实现打电话，发信息的功能
		if (result.contains("电话")) {
			System.out.println("识别开始…………");

			int sumPeople = 0;
			boolean flag = true;
			
			while (contractIterator.hasNext()) {
				contractEntry = contractIterator.next();
				
				//程序现在使用拼音的方式进行匹配
				if (gpy.getPingYin(result).contains(gpy.getPingYin(contractEntry.getValue()))) {
					
					System.out.println("result 替换前"+result);
					result = new ReplaceStr().getFinalStr(result, contractEntry.getValue());
					System.out.println("result 替换后"+result);
					//把替换后的结果显示
					/*if (!contractIterator.hasNext()) {
						
						sumPeople =  + 100;
						System.out.println("sumPeople 1: "+sumPeople);
						showListItem(result);
					}*/
					flag = false;
					sumPeople =  100;
					System.out.println("sumPeople 2: "+sumPeople);
					
					// 实现打电话的功能
					System.out.println(contractEntry.getKey() + " "+ contractEntry.getValue());// 直接获取键和值
					// 调用别的类，进行打电话操作
					System.out.println("call phone start…………");
					callPhone.MakePhone(contractEntry.getKey());
					System.out.println("call phone Calling…………");
				}
				
				if(flag = false){
					sumPeople = 1;
				}
				/*else{
					if (!contractIterator.hasNext()){
						showListItem(result);
						alertDialog = new AlertDialog.Builder(this).setTitle("提示")
								.setMessage("您要拨打电话的联系人不存在！")
								.setPositiveButton("确定", null).show();
						
					}
				} */
			}
			showListItem(result);
			System.out.println("sumPeople 3:　"+sumPeople);
			if(sumPeople < 100){
				
				alertDialog = new AlertDialog.Builder(this).setTitle("提示")
						.setMessage("您要拨打电话的联系人不存在！")
						.setPositiveButton("确定", null).show();
			}
		}else{
			showListItem(result);
			// 实现打开程序的功能
			 if (result.contains("打开") || result.contains("启动") || result.contains("点击")) {
				// 调用GetApplicationName 获取手机安装的应用列表
				HashMap<String, String> appMap = new GetApplicationName(Main.this).getApplicationName();
				// 另一种方式调用程序，最大匹配度算法
				String str = new MaxMatch().getAppMaxMath(result, appMap);

				// 如果用户要求打开的程序，手机上不存在，
				if (str.trim().length() == 0) {
					alertDialog = new AlertDialog.Builder(this).setTitle("提示")
							.setMessage("手机上没这个程序！").setPositiveButton("确定", null)
							.show();

					return;
				}
				//启动app
				intent = launchApp.getLaunchIntentForPackage(str);
				startActivity(intent);
				//实现发信息的功能
			}else if(result.contains("短信")||result.contains("信息")){
				System.out.println("seng message star…………");
				
				System.out.println("smsresult: "+result);
				//遍历联系人，进行匹配。
				while (contractIterator.hasNext()) {
					contractEntry = contractIterator.next();
					if (gpy.getPingYin(result).contains(gpy.getPingYin(contractEntry.getValue()))) {
						
						//播放发送信息的提示音
						playBeeps.smsBeep();
						
						//获得number，name
						number = contractEntry.getKey();
						name = contractEntry.getValue();

						System.out.println("number: "+number+" ,name: "+name);
						//展现发送信息的界面 
						lanchSendSmsGUI();
					}
					
				}
				//实现搜索功能 
			}else if(gpy.getPingYin(result).contains("sou")||gpy.getPingYin(result).contains("sousuo")||gpy.getPingYin(result).contains("shousuo")||gpy.getPingYin(result).contains("soushuo")||gpy.getPingYin(result).contains("shoushuo")){
				//if(gpy.getPingYin(result).contains("shousuo")||gpy.getPingYin(result).contains("soushuo")||gpy.getPingYin(result).contains("shoushuo")){
					keyword = result.substring(2, result.length());
					System.out.println("sousou: "+keyword);
				//调用实现搜索的方法
				search(keyword);
			}
		}
		
		System.out.println("-------------------");
		System.out.println("end result= " + result);
	}

	// the functions below is useful only for UI design and it is never called
	// in the API
	/**
	 * Show candidates for the clicked sentence, the candidates can be
	 * List<String> defined in Activity
	 */
	public void onClientClick(int sentenceSquenceNo) {
		// TODO: when use continuous recognition and allow user modify displayed
		// sentences with candidates, realize it
	}

	/**
	 * replace the displayed sentence with the chosen candidate, the replaced
	 * parameter is defined in Activity
	 */
	public void onClientChoose(int sentenceSequenceNo, int resultOrderId) {
		// TODO: when use continuous recognition and allow user modify displayed
		// sentences with candidates, realize it
	}

	/**
	 * clear its candidates when the displayed sentence is modified, the
	 * candidates is defined in Activity
	 */
	public void onClientUpdate(int sentenceSequenceNo, int resultOrderId) {
		// TODO: when use continuous recognition and allow user modify displayed
		// sentences with candidates, realize it
	}

	//普通文本的识别，识别结果放到EditTest框中
	public void showListItem(String result) {
		ListItem item1 = new ListItem(0, result);
		list.add(item1);
		listAdapter.setList(list);
		j++;
		listAdapter.notifyDataSetChanged();
	}
	
	
	// 初始化，ListView中的几个条目，让程序启动的时候，就能看到相关功能的提示。
	public void lanchInit() {
	/*	ListItem item1 = new ListItem(0, "您可以打电话");
		ListItem item2 = new ListItem(0, "您可以发信息");
		ListItem item3 = new ListItem(0, "您可以打开手机程序");
		list.add(item1);
		list.add(item2);
		list.add(item3);
		listAdapter.setList(list);
		j=3;//初始化了3条edittext
		listAdapter.notifyDataSetChanged();*/
		ListItem item1 = new ListItem(2);
		list.add(item1);
		listAdapter.setList(list);
		j=3;//初始化了3条edittext
		listAdapter.notifyDataSetChanged();
		
	}
	
	//发送信息的界面
	public void lanchSendSmsGUI(){
		ListItem item1 = new ListItem(1, "发信息……收件人:"+getName()+",号码:"+getNumber());
		list.add(item1);
		listAdapter.setList(list);
		j++;
		listAdapter.notifyDataSetChanged();
		System.out.println("j: "+j);
	}

	//定义实现搜索的方法，用的是系统安装的浏览器。
	public void search(String keyword){
		Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
		search.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		search.putExtra(SearchManager.QUERY, keyword);
		final Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
		if (appData != null) {
			search.putExtra(SearchManager.APP_DATA, appData);
		}
		startActivity(search);
	}
	//设置+上的菜单
	private void setMenuItemView() {
			ImageButton imgBtn0 = new ImageButton(this);
			imgBtn0.setBackgroundResource(R.drawable.composer_lockscrean);
			ImageButton imgBtn1 = new ImageButton(this);
			imgBtn1.setBackgroundResource(R.drawable.composer_sms);
			ImageButton imgBtn2 = new ImageButton(this);
			imgBtn2.setBackgroundResource(R.drawable.composer_dail);
			ImageButton imgBtn3 = new ImageButton(this);
			imgBtn3.setBackgroundResource(R.drawable.composer_camera);
			ImageButton imgBtn4 = new ImageButton(this);
			imgBtn4.setBackgroundResource(R.drawable.composer_setting);
			ImageButton imgBtn5 = new ImageButton(this);
			imgBtn5.setBackgroundResource(R.drawable.composer_rate);
			ImageButton imgBtn6 = new ImageButton(this);
			imgBtn6.setBackgroundResource(R.drawable.composer_selfme);
			
			myViewRB.addView(imgBtn0);
			myViewRB.addView(imgBtn1);
			myViewRB.addView(imgBtn2);
			myViewRB.addView(imgBtn3);
			myViewRB.addView(imgBtn4);
			myViewRB.addView(imgBtn5);
			myViewRB.addView(imgBtn6);
		}
	//对扇形界面的子菜单，进行监听
	@Override
	public void onclick(int item) {
		Intent intent=new Intent();
		LaunchApp launchApp = new LaunchApp(Main.this);
//		Toast.makeText(Main.this, ""+item, 1).show();
		   switch(item){
		   //实现锁屏功能，自定义的锁屏功能
		   case 0:
			   intent.setClass(Main.this,LockScreanActivity.class);
			   startActivity(intent);
			break;
			  //发信息功能
			// com.android.mms
		   case 1:
			   intent = launchApp.getLaunchIntentForPackage("com.android.mms");
			   startActivity(intent);
			break;
			//实现打电话
		   case 2:
			   intent.setAction("android.intent.action.DIAL");
			   startActivity(intent);
			break;
			//打开相机
		   case 3:
			   intent = launchApp.getLaunchIntentForPackage("com.lge.camera");
			   startActivity(intent);
			break;
		   //设置界面
           case 4:
        	   //当前的Activity为Test,目标Activity为Name
        	   intent.setClass(Main.this,SetActivity.class);
        	   startActivity(intent);
               break;
               //设置流量界面
           case 5:
        	   //当前的Activity为Test,目标Activity为Name
        	   intent.setClass(Main.this,RateActivity.class);
        	   startActivity(intent);
        	   break;
           case 6:                 
           		//当前的Activity为Test,目标Activity为Name
           		intent.setClass(Main.this,SelfMeActivity.class);
           		startActivity(intent);
               break;
		   	}

           }
	
	 //点击+触发的
		@Override
		public void onClick(View v) {
			MyAnimations.getRotateAnimation(imgPlusRB, 0f, 270f, 300);
			MyAnimations.startAnimations(Main.this, myViewRB, 300);
		}

			
	//监听退出键，菜单键，设置退出动作。
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	    	//监听返回键
	        if(keyCode==KeyEvent.KEYCODE_BACK){  
	            new AlertDialog.Builder(this)  
	            .setTitle("注意")  
	            .setMessage("确定要退出GoGo语音吗？")  
	            .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
	                @Override  
	                public void onClick(DialogInterface dialog, int which) {
	                	
	                }  
	            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	                  
	                @Override  
	                public void onClick(DialogInterface dialog, int which) {  
	                    finish();  
	                }  
	            }).show();  
	            return true;  
	        }else if(keyCode == KeyEvent.KEYCODE_MENU) {
		        // 监控菜单键
		        Toast.makeText(Main.this, "Menu", Toast.LENGTH_SHORT).show();
		        
	            new AlertDialog.Builder(this)  
	            .setTitle("注意")  
	            .setMessage("确定要退出GoGo语音吗？")  
	            .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
	                  
	                @Override  
	                public void onClick(DialogInterface dialog, int which) {  
	                      
	                }  
	            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	                  
	                @Override  
	                public void onClick(DialogInterface dialog, int which) {  
	                    finish();  
	                }  
	            }).show();  
	            return true;  
		    }else{  
	            return super.onKeyDown(keyCode, event);  
	        }  
	    }  
			
	
	/*// 按钮的监听操作
	public void clickclick(View v) {
		//search("搜索百度");
		//sendSmsGui();
		name = "冯腾飞";
		number = "18601779568";
		lanchSendSmsGUI();
		

	}*/
	
	
	public int getJ(){
		return j;
	}

	
	public String getName(){
		return name;
	}
	
	public String getNumber(){
		return number;
	}
	
}