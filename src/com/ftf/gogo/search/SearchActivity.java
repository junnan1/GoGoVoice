package com.ftf.gogo.search;

import com.ftf.gogo.R;
import com.ftf.gogo.R.id;
import com.ftf.gogo.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends Activity {
	private Button bt_search;
	private EditText et_search;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		bt_search =  (Button) findViewById(R.id.bt_search);
		et_search = (EditText) findViewById(R.id.et_search);
		
	}
	
	
	public void GoGoSearch(View view){
		String content = bt_search.getText().toString();
		
		
	}
	
}
