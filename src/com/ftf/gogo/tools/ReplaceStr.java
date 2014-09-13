package com.ftf.gogo.tools;


public class ReplaceStr {
	static GetPinyin gpy = new GetPinyin();
	
	public static String getFinalStr(String oldStr,String newStr){
		int len = getStartPoint( oldStr,newStr);
		String finalStr = oldStr.replace(oldStr.substring(len-newStr.length(), len), newStr);
		
		return finalStr;
	}
	
	//得到起始位置
	public static int getStartPoint(String oldStr ,String newStr){
		//GetPinyin gpy = new GetPinyin();
		int point = 0;
		for(int i = 0;i<=oldStr.length();i++){
			String subs = oldStr.substring(0, i);
			if(gpy.getPingYin(subs).indexOf(gpy.getPingYin(newStr))!=-1){
				point = subs.length();
				return point;
			}
		}
		System.out.println("point: "+point);
		return point;
	}
	
}
