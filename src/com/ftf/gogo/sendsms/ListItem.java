package com.ftf.gogo.sendsms;

public class ListItem {

	public static final int TYPE_EDITTEXT = 0;
	public static final int TYPE_SMS = 1;
	public static final int TYPE_COUNT = 3;
	public static final int TYPE_LISTVIEW_INIT = 2;

	private String name;
	private int number;
	private int type;

	public ListItem(int type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public ListItem(int type) {
		this.type = type;
	}
	
	public ListItem(int type, String name,int number) {
		this.type = type;
		this.name = name;
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setType(int type) {
		this.type = type;
	}
}
