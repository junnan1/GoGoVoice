/*package com.ftf.gogo.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GetMatch {
	static GetPinyin gpy = new GetPinyin();

	public static String getAppMaxMath(String s, HashMap<String,String> map) {
		boolean flag = false;
		String result = "";
		String ss = gpy.getPingYin(s);
		
		Set<Map.Entry<String, String>> set = map.entrySet();
		Iterator<Map.Entry<String, String>> iterator = set.iterator();
		//����һ��Arraylist�ѷ���Ҫ��ģ�ȫ���ŵ������У��ٵ������н��жԱ�
		ArrayList<String> list = new ArrayList<String>();

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			if(ss.toUpperCase().contains(gpy.getPingYin(entry.getKey()))){
				list.add(entry.getKey());
			}
		}
		for(String str:list){
			System.out.println("list�����зŵ�����: "+str);
		}
		if(list != null && list.size() != 0){
			String str = fingMinLength(list);
			System.out.println("str:��"+str);
			Iterator<Map.Entry<String, String>> oIterator = set.iterator();
			while (oIterator.hasNext()) {
				Map.Entry<String, String> oEntry = oIterator.next();
				if(str == oEntry.getKey()){
					result = oEntry.getValue();
				}
			}
		}
		return result;
	}
	
}
*/