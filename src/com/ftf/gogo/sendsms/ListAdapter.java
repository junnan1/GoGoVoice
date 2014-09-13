package com.ftf.gogo.sendsms;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftf.gogo.Main;
import com.ftf.gogo.MyContenxt;
import com.ftf.gogo.R;

public class ListAdapter extends BaseAdapter {

	private Activity activity;// 怎么定义一个activity？
	private List<ListItem> list;
	private  String content;
	public static int flag;
	
	
	//定义一个HashMap，用来存放EditText的值，Key是position  
    HashMap<Integer, String> hashMap = new HashMap<Integer, String>();  
	
	public ListAdapter(Activity activity) {
		this.activity = activity;
	}

	public void setList(List<ListItem> list) {// 里面存放的是，
		this.list = list;
	}

	@Override
	public int getCount() {
		if (list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (list != null && position < list.size()) {
			return list.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		if (list != null && position < list.size()) {
			return list.get(position).getType();
		}
		return super.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount() {
		return ListItem.TYPE_COUNT;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		flag = new Main().getJ();
		System.out.println("flag main : "+flag);
		
		int type = getItemViewType(position);
		switch (type) {
		//edittext
		case ListItem.TYPE_LISTVIEW_INIT: {
			ViewHolderInit holderInit = null;
			if(convertView == null) {
				System.out.println("init start");
				convertView = activity.getLayoutInflater().inflate(R.layout.list_edittext_item_init, null);
				holderInit = new ViewHolderInit();
				holderInit.et_result_Init = (EditText) convertView.findViewById(R.id.et_edittext_init_result);
				holderInit.iv_result_init = (ImageView) convertView.findViewById(R.id.iv_edittext_init_result);
				
				holderInit.et_dial_Init = (EditText) convertView.findViewById(R.id.et_edittext_init_dial);
				holderInit.iv_dial_init = (ImageView) convertView.findViewById(R.id.iv_edittext_init_dial);
				
				holderInit.et_openapp_Init = (EditText) convertView.findViewById(R.id.et_edittext_init_openapp);
				holderInit.iv_openapp_init = (ImageView) convertView.findViewById(R.id.iv_edittext_init_openapp);
				
				holderInit.et_search_Init = (EditText) convertView.findViewById(R.id.et_edittext_init_search);
				holderInit.iv_search_init = (ImageView) convertView.findViewById(R.id.iv_edittext_init_search);
				
				holderInit.et_sms_Init = (EditText) convertView.findViewById(R.id.et_edittext_init_sms);
				holderInit.iv_sms_init = (ImageView) convertView.findViewById(R.id.iv_edittext_init_sms);
//				convertView.setTag(holderInit);
				System.out.println("init end");
			} else {
				holderInit = (ViewHolderInit) convertView.getTag();
			}
			break;
		}
	case ListItem.TYPE_EDITTEXT: {
		EditViewHolder holder = null;
		if(convertView == null) {
			convertView = activity.getLayoutInflater().inflate(R.layout.list_edittext_item, null);
			holder = new EditViewHolder();
			holder.editText = (EditText) convertView.findViewById(R.id.et_edittext);
			convertView.setTag(holder);
		} else {
			holder = (EditViewHolder) convertView.getTag();
		}
		holder.editText.setText(list.get(position).getName());
		break;
		}
		//短信界面
	case ListItem.TYPE_SMS: {
			//ButtonViewHolder holder = null;
			ViewHolder holder = null;  
			
			if (convertView == null) {
				convertView = activity.getLayoutInflater().inflate(R.layout.sms_item, null);
				holder = new ViewHolder();
				holder.bt_send = (Button) convertView.findViewById(R.id.bt_send);
				holder.bt_cancel = (Button) convertView.findViewById(R.id.bt_cancel);
				holder.et_sms = (EditText) convertView.findViewById(R.id.et_sms);
				holder.tv_sms = (TextView) convertView.findViewById(R.id.tv_sms);
				
//				holder.et_sms.setTag(flag);
//				holder.et_sms.setTag(position);
				//对发送按钮实现监听，
				holder.bt_send.setOnClickListener(new BtnSendOnClickListener()); 
				holder.bt_cancel.setOnClickListener(new BtnCancelOnClickListener()); 
  				
				holder.et_sms.addTextChangedListener(new TextWatcher() {
					@Override
					public void onTextChanged(CharSequence s, int start,int before, int count) {

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
						System.out.println("flag: "+flag+", s: "+s.toString());
						hashMap.put(flag, s.toString());
					}
				});
			
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
				content = hashMap.get(flag);
				for(int i=0;i<flag;i++){
                	System.out.println("hashMap falg: "+i+",value: "+hashMap.get(i));
                }
				System.out.println("postion1: "+position+",content1: "+content);
			}
			holder.tv_sms.setText(list.get(position).getName());
			System.out.println("postion2: "+position+",content2: "+content);
			break;
		}
		
		}
		
		return convertView;
	}


	
     
   //发送  按钮监听  
     class BtnSendOnClickListener implements android.view.View.OnClickListener{  
    	 Main main = new Main();
    	 
    	 MyContenxt mct = new MyContenxt();
    	 
		public void sendSms(View v) {
			System.out.println("进入方法 ListAdapter");

			if (TextUtils.isEmpty(content)) {
				System.out.println("信息内容不能为空！");
			}

			String number = main.getNumber();
			String name = main.getName();
			
			System.out.println("ListAdapter:　" + name);
			System.out.println("ListAdapter:　" + number);

			System.out.println("ListAdapter:　" + content);
			System.out.println("执行方法结束ListAdapter");
			//得到上下文的context
			Context context = mct.getInstance();
			SendSMS sendSMS = new SendSMS(context); 
			sendSMS.sendMessage(number, content);
//			ViewHolder holder = null;  
			//发送成功后，设置内容为空
//			holder.tv_sms = (EditText)findViewById(R.id.et_sms);
//			content = null;
//			hashMap.remove(flag);
//			content = null;
			System.out.println("context："+content);
			//清除内容，防止，后续初始化后，内容还在显示
			System.out.println("button 后的flag： "+flag);
			hashMap.remove(flag);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			sendSms(v);
		}
     } 
     
     
   //取消 按钮监听  
     class BtnCancelOnClickListener implements android.view.View.OnClickListener{  

		@Override
		public void onClick(View v) {
			//发送成功后，设置内容为空
		}
     } 
     
     

   //定义一个Holder类，包含一些组件。
 	static class EditViewHolder {
 		EditText editText;
 	}
      static class ViewHolder {  
     	public TextView tv_sms;  
         public EditText et_sms;  
         public Button bt_send;
         public Button bt_cancel;
   }  
      static class ViewHolderInit {  
    	  public EditText et_result_Init;  
    	  public ImageView iv_result_init;
    	  
    	  public EditText et_dial_Init;  
    	  public ImageView iv_dial_init;
    	  
    	  public EditText et_openapp_Init;  
    	  public ImageView iv_openapp_init;
    	  
    	  public EditText et_sms_Init;  
    	  public ImageView iv_sms_init;
    	  
    	  public EditText et_search_Init;  
    	  public ImageView iv_search_init;
      }  
      
     
}
