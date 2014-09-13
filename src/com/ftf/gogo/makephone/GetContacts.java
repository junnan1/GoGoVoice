package com.ftf.gogo.makephone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class GetContacts{
	public HashMap<String, String> myContacts = null;

	 private  Context context;
	 public GetContacts(Context context){
        this.context=context;
     }
	 
	public HashMap<String, String> getNamePhone(){
		myContacts = new HashMap<String,String>();
		
		ContentResolver contentResolver = context.getContentResolver();
		Uri uri = Uri.parse("content://com.android.contacts/contacts");
		Cursor cursor = contentResolver.query(uri, null, null, null, null);
		while(cursor.moveToNext()){
			//得到联系人id
			int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			//得到联系人的名称
			//System.out.println("id = "+id);
			String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			//System.out.println("name="+name);
			// 联系人电话号码需要对另一个表进行查询，所以用到另一个 uri：content://com.android.contacts/data/phones 
			Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="+ id,
					null,
					null);
			while(phones.moveToNext()){
				String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				//System.out.println("phone:"+phone);
				
				//把联系人放到集合myContacts
				myContacts.put(phone.trim(), name);
				//myMap.put(name, phone);
			}
			phones.close();
		}
		cursor.close();
		
		//
		/*Set<Map.Entry<String, String>> contractSet = myContacts.entrySet();
		Iterator<Map.Entry<String, String>> contractIterator = contractSet.iterator();
		while (contractIterator.hasNext()) {
			Map.Entry<String, String> contractEntry = contractIterator.next();
			System.out.println("getContacts: "+contractEntry.getKey()+ contractEntry.getValue());// 直接获取键和值
		}*/
		//
		
		return myContacts;
	}
}
