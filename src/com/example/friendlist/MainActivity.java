package com.example.friendlist;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	
	 @SuppressWarnings("deprecation")
	Facebook facebook = new Facebook("755345174478792");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Facebook();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	//Facebook Token取得メソッド
	@SuppressWarnings("deprecation")
	public void Facebook() {
		facebook.authorize(this, new String[] { "email", "offline_access", "publish_checkins" }, 
				new DialogListener(){
			@Override
			public void onComplete(Bundle values) {
				// TODO Auto-generated method stub
				Log.e("成功", "成功");
				
			}

			@Override
			public void onFacebookError(FacebookError e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(DialogError e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

}
