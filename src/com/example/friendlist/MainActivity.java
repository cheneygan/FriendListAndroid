package com.example.friendlist;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.navdrawer.SimpleSideDrawer;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class MainActivity extends Activity {
	
	 @SuppressWarnings("deprecation")
	Facebook facebook = new Facebook("755345174478792");
	SimpleSideDrawer sideMenu;
	RequestQueue rQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		
		Facebook();
		
		//SideMenu関連
		sideMenu = new SimpleSideDrawer(this);
		sideMenu.setRightBehindContentView(R.layout.side_menu);		
        findViewById(R.id.side_menu).setOnClickListener(new OnClickListener() {
            @Override 
            public void onClick(View v) {
            	sideMenu.toggleRightDrawer();
            }
        });

		String url = "http://10.0.2.2:3000/api/getgroups?user_id=1";
		rQueue = Volley.newRequestQueue(this);
		 JsonArrayRequest request = new  JsonArrayRequest(url,
				new Listener<JSONArray>(){
					@Override
					public void onResponse(JSONArray result) {
						// TODO Auto-generated method stub
						Log.e("成功", result.toString());
						
					}
				}, 
				new Response.ErrorListener() {
		
					@Override
					public void onErrorResponse(VolleyError result) {
						// TODO Auto-generated method stub
						Log.e("失敗", result.toString());
						
					}
				});
		rQueue.add(request);


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
