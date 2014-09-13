package com.ftf.gogo.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author tengfei
 * 
 */
public class MaxMatch {
	/**
	 * 查找出最大匹配字符串
	 * 
	 * @param s
	 * @param s2
	 * @return
	 */
	public String getTransLate(String s, String s2) {
		boolean flag = false;
		String result = "";
		for (int i = s2.length(); i > 0; i--) {
			List<String> sList = getCom(s2, i);
			for (int j = 0; j < sList.size(); j++) {
				if (s.indexOf(sList.get(j).toString()) != -1) {
					result = sList.get(j).toString();
					flag = true;
					break;
				}
			}
			if (flag) {
				break;
			}
		}
		return result;
	}

	
	
	public String getMaxMath_2(String s, HashMap<String,String> map) {
		boolean flag = false;
		String result = "";
		
		Set<Map.Entry<String, String>> set = map.entrySet();
		Iterator<Map.Entry<String, String>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			for (int i = entry.getKey().length(); i > 0; i--) {
				List<String> sList = getCom(entry.getKey(), i);
				for (int j = 0; j < sList.size(); j++) {
					if (s.indexOf(sList.get(j).toString()) != -1) {
						result = sList.get(j).toString();
						flag = true;
						break;
					}
				}
				if (flag) {
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * 固定长度的所有组合
	 * 
	 * @param s
	 * @param n
	 * @return
	 */
	public static List<String> getCom(String s, int n) {
		List list = new ArrayList<String>();
		for (int i = 0; i <= s.length() - n; i++) {
			list.add(s.substring(i, i + n));
		}
		return list;
	}

	 public static String getAppMaxMath(String s, HashMap<String,String> map) {
			boolean flag = false;
			String result = "";
			
			Set<Map.Entry<String, String>> set = map.entrySet();
			Iterator<Map.Entry<String, String>> iterator = set.iterator();
			//设置一个Arraylist把符合要求的，全部放到集合中，再到集合中进行对比
			ArrayList<String> list = new ArrayList<String>();

			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				if(s.toUpperCase().contains(entry.getKey())){
					list.add(entry.getKey());
				}
			}
			for(String str:list){
				System.out.println("list集合中放的内容: "+str);
			}
			if(list != null && list.size() != 0){
				String str = fingMinLength(list);
				System.out.println("str:　"+str);
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
	    
	    //在符合
	    public static String fingMinLength(List<String> list){
	    	String result = list.get(0);
	    	for(int i=1;i<list.size();i++){
	    		if (result.length() < list.get(i).length()){
	    			result = list.get(i);
	    		}
	    	}
	    	return result;
	    }
}