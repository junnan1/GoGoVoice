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
 * 	���������
 */
public class Main extends Activity implements OnItemClickListener, OnClickListener{
	//����Сԭ��˵�
	private ImageView imgPlusLB;
	private MenuItemView myViewRB;
	private ImageView imgPlusRB;
	
	
	private Button btn,button2;
	private EditText mResultsText;
	private RecognizerDialog dialog = null;
	private AlertDialog alertDialog = null;
	public Intent intent;

	// ����listview�����
	private ListView listView;
	private View listItem;
	private List<ListItem> list;
	private ListAdapter listAdapter;
	GetPinyin gpy = new GetPinyin();
	private static String number;
	private static String name;
	//ʵ�������Ĺؼ���
	private String keyword;
	private static int j;//���õ�ȫ�������������ڷ���Ϣ��ʱ���ܹ���ȡ����Ӧ�б��ֵ
	
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
	
	//������ʾ��
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
		
		//СԲ��˵�
		findViewById(R.id.relLayRB).setOnClickListener(this);
		
		myViewRB = (MenuItemView) findViewById(R.id.myViewRB);
		myViewRB.setPosition(MenuItemView.POSITION_RIGHT_BOTTOM);
		myViewRB.setRadius(150);
		imgPlusRB = (ImageView) findViewById(R.id.imgPlusRB);
		//����+���ϵĲ˵���
		setMenuItemView();
		
		//ListView
		listView = (ListView) findViewById(R.id.listitem);
		
		listAdapter = new ListAdapter(this);
		listView.setAdapter(listAdapter);

		list = new ArrayList<ListItem>();
		
		//��ʼ��app�����Ľ���
		lanchInit();
	}

	private void setListeners() {
		// Click Listener
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//����ʶ��ʼ������
				playBeeps.startBeep();
				
				wholeResult = new ArrayList<List<String>>();
				mResultsText.setText("");
				dialog.setAutoStop(true);// ����¼���Ƿ��Զ�ֹͣ
				dialog.setContinuous(false);// �����Ƿ�����ʶ��
				dialog.show();
			}
		});
		
	}

	//��ʶ�����ŵ�һ��List������
	// add recognized results to the wholeResult
	private void addToWholeResult(List<List<String>> ds) {
		//����ʶ�������
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
		
		/*��ͨ�ı���ʶ��ʶ�����ŵ�EditTest����
		showListItem(result);
		textView.append(result);*/
		
		System.out.println("start result: "+result);
		
		HashMap<String, String> contactsMap = new GetContacts(Main.this).getNamePhone();
		Set<Map.Entry<String, String>> contractSet = contactsMap.entrySet();
		Iterator<Map.Entry<String, String>> contractIterator = contractSet.iterator();
		Map.Entry<String, String> contractEntry;
		// ʵ�ָ��ӹ���

		// ������ϵ�˼��ϣ�ʵ�ִ�绰������Ϣ�Ĺ���
		if (result.contains("�绰")) {
			System.out.println("ʶ��ʼ��������");

			int sumPeople = 0;
			boolean flag = true;
			
			while (contractIterator.hasNext()) {
				contractEntry = contractIterator.next();
				
				//��������ʹ��ƴ���ķ�ʽ����ƥ��
				if (gpy.getPingYin(result).contains(gpy.getPingYin(contractEntry.getValue()))) {
					
					System.out.println("result �滻ǰ"+result);
					result = new ReplaceStr().getFinalStr(result, contractEntry.getValue());
					System.out.println("result �滻��"+result);
					//���滻��Ľ����ʾ
					/*if (!contractIterator.hasNext()) {
						
						sumPeople =  + 100;
						System.out.println("sumPeople 1: "+sumPeople);
						showListItem(result);
					}*/
					flag = false;
					sumPeople =  100;
					System.out.println("sumPeople 2: "+sumPeople);
					
					// ʵ�ִ�绰�Ĺ���
					System.out.println(contractEntry.getKey() + " "+ contractEntry.getValue());// ֱ�ӻ�ȡ����ֵ
					// ���ñ���࣬���д�绰����
					System.out.println("call phone start��������");
					callPhone.MakePhone(contractEntry.getKey());
					System.out.println("call phone Calling��������");
				}
				
				if(flag = false){
					sumPeople = 1;
				}
				/*else{
					if (!contractIterator.hasNext()){
						showListItem(result);
						alertDialog = new AlertDialog.Builder(this).setTitle("��ʾ")
								.setMessage("��Ҫ����绰����ϵ�˲����ڣ�")
								.setPositiveButton("ȷ��", null).show();
						
					}
				} */
			}
			showListItem(result);
			System.out.println("sumPeople 3:��"+sumPeople);
			if(sumPeople < 100){
				
				alertDialog = new AlertDialog.Builder(this).setTitle("��ʾ")
						.setMessage("��Ҫ����绰����ϵ�˲����ڣ�")
						.setPositiveButton("ȷ��", null).show();
			}
		}else{
			showListItem(result);
			// ʵ�ִ򿪳���Ĺ���
			 if (result.contains("��") || result.contains("����") || result.contains("���")) {
				// ����GetApplicationName ��ȡ�ֻ���װ��Ӧ���б�
				HashMap<String, String> appMap = new GetApplicationName(Main.this).getApplicationName();
				// ��һ�ַ�ʽ���ó������ƥ����㷨
				String str = new MaxMatch().getAppMaxMath(result, appMap);

				// ����û�Ҫ��򿪵ĳ����ֻ��ϲ����ڣ�
				if (str.trim().length() == 0) {
					alertDialog = new AlertDialog.Builder(this).setTitle("��ʾ")
							.setMessage("�ֻ���û�������").setPositiveButton("ȷ��", null)
							.show();

					return;
				}
				//����app
				intent = launchApp.getLaunchIntentForPackage(str);
				startActivity(intent);
				//ʵ�ַ���Ϣ�Ĺ���
			}else if(result.contains("����")||result.contains("��Ϣ")){
				System.out.println("seng message star��������");
				
				System.out.println("smsresult: "+result);
				//������ϵ�ˣ�����ƥ�䡣
				while (contractIterator.hasNext()) {
					contractEntry = contractIterator.next();
					if (gpy.getPingYin(result).contains(gpy.getPingYin(contractEntry.getValue()))) {
						
						//���ŷ�����Ϣ����ʾ��
						playBeeps.smsBeep();
						
						//���number��name
						number = contractEntry.getKey();
						name = contractEntry.getValue();

						System.out.println("number: "+number+" ,name: "+name);
						//չ�ַ�����Ϣ�Ľ��� 
						lanchSendSmsGUI();
					}
					
				}
				//ʵ���������� 
			}else if(gpy.getPingYin(result).contains("sou")||gpy.getPingYin(result).contains("sousuo")||gpy.getPingYin(result).contains("shousuo")||gpy.getPingYin(result).contains("soushuo")||gpy.getPingYin(result).contains("shoushuo")){
				//if(gpy.getPingYin(result).contains("shousuo")||gpy.getPingYin(result).contains("soushuo")||gpy.getPingYin(result).contains("shoushuo")){
					keyword = result.substring(2, result.length());
					System.out.println("sousou: "+keyword);
				//����ʵ�������ķ���
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

	//��ͨ�ı���ʶ��ʶ�����ŵ�EditTest����
	public void showListItem(String result) {
		ListItem item1 = new ListItem(0, result);
		list.add(item1);
		listAdapter.setList(list);
		j++;
		listAdapter.notifyDataSetChanged();
	}
	
	
	// ��ʼ����ListView�еļ�����Ŀ���ó���������ʱ�򣬾��ܿ�����ع��ܵ���ʾ��
	public void lanchInit() {
	/*	ListItem item1 = new ListItem(0, "�����Դ�绰");
		ListItem item2 = new ListItem(0, "�����Է���Ϣ");
		ListItem item3 = new ListItem(0, "�����Դ��ֻ�����");
		list.add(item1);
		list.add(item2);
		list.add(item3);
		listAdapter.setList(list);
		j=3;//��ʼ����3��edittext
		listAdapter.notifyDataSetChanged();*/
		ListItem item1 = new ListItem(2);
		list.add(item1);
		listAdapter.setList(list);
		j=3;//��ʼ����3��edittext
		listAdapter.notifyDataSetChanged();
		
	}
	
	//������Ϣ�Ľ���
	public void lanchSendSmsGUI(){
		ListItem item1 = new ListItem(1, "����Ϣ�����ռ���:"+getName()+",����:"+getNumber());
		list.add(item1);
		listAdapter.setList(list);
		j++;
		listAdapter.notifyDataSetChanged();
		System.out.println("j: "+j);
	}

	//����ʵ�������ķ������õ���ϵͳ��װ���������
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
	//����+�ϵĲ˵�
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
	//�����ν�����Ӳ˵������м���
	@Override
	public void onclick(int item) {
		Intent intent=new Intent();
		LaunchApp launchApp = new LaunchApp(Main.this);
//		Toast.makeText(Main.this, ""+item, 1).show();
		   switch(item){
		   //ʵ���������ܣ��Զ������������
		   case 0:
			   intent.setClass(Main.this,LockScreanActivity.class);
			   startActivity(intent);
			break;
			  //����Ϣ����
			// com.android.mms
		   case 1:
			   intent = launchApp.getLaunchIntentForPackage("com.android.mms");
			   startActivity(intent);
			break;
			//ʵ�ִ�绰
		   case 2:
			   intent.setAction("android.intent.action.DIAL");
			   startActivity(intent);
			break;
			//�����
		   case 3:
			   intent = launchApp.getLaunchIntentForPackage("com.lge.camera");
			   startActivity(intent);
			break;
		   //���ý���
           case 4:
        	   //��ǰ��ActivityΪTest,Ŀ��ActivityΪName
        	   intent.setClass(Main.this,SetActivity.class);
        	   startActivity(intent);
               break;
               //������������
           case 5:
        	   //��ǰ��ActivityΪTest,Ŀ��ActivityΪName
        	   intent.setClass(Main.this,RateActivity.class);
        	   startActivity(intent);
        	   break;
           case 6:                 
           		//��ǰ��ActivityΪTest,Ŀ��ActivityΪName
           		intent.setClass(Main.this,SelfMeActivity.class);
           		startActivity(intent);
               break;
		   	}

           }
	
	 //���+������
		@Override
		public void onClick(View v) {
			MyAnimations.getRotateAnimation(imgPlusRB, 0f, 270f, 300);
			MyAnimations.startAnimations(Main.this, myViewRB, 300);
		}

			
	//�����˳������˵����������˳�������
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	    	//�������ؼ�
	        if(keyCode==KeyEvent.KEYCODE_BACK){  
	            new AlertDialog.Builder(this)  
	            .setTitle("ע��")  
	            .setMessage("ȷ��Ҫ�˳�GoGo������")  
	            .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {  
	                @Override  
	                public void onClick(DialogInterface dialog, int which) {
	                	
	                }  
	            }).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
	                  
	                @Override  
	                public void onClick(DialogInterface dialog, int which) {  
	                    finish();  
	                }  
	            }).show();  
	            return true;  
	        }else if(keyCode == KeyEvent.KEYCODE_MENU) {
		        // ��ز˵���
		        Toast.makeText(Main.this, "Menu", Toast.LENGTH_SHORT).show();
		        
	            new AlertDialog.Builder(this)  
	            .setTitle("ע��")  
	            .setMessage("ȷ��Ҫ�˳�GoGo������")  
	            .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {  
	                  
	                @Override  
	                public void onClick(DialogInterface dialog, int which) {  
	                      
	                }  
	            }).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
	                  
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
			
	
	/*// ��ť�ļ�������
	public void clickclick(View v) {
		//search("�����ٶ�");
		//sendSmsGui();
		name = "���ڷ�";
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