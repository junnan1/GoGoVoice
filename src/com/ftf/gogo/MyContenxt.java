package com.ftf.gogo;

import android.app.Application;

public class MyContenxt extends Application {

	private static MyContenxt instance;

    public static MyContenxt getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
    }
}
