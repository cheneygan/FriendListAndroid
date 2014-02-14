package com.example.friendlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.navdrawer.SimpleSideDrawer;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {
	
	 @SuppressWarnings("deprecation")
	Facebook facebook = new Facebook("755345174478792");
	SimpleSideDrawer sideMenu;
	RequestQueue rQueue;
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String>listDataHeader;
	HashMap<String, List<String>> listDataChild;

	String url = "http://192.168.56.1:3000/api/getgroups?user_id=1"; //samole URL
	String loginurl = "http://192.168.56.1:3000/login/test?token="; //Login URL

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		//FBToken取得
		Facebook();
		//SlideMenu設定
		slideMenu();
        
        //volleyのインスタンス生成とRequestの設定
		rQueue = Volley.newRequestQueue(this);
		SampleAPI();
		
		//IDの指定は今回はじめに指定したView以外のXMLを使うのでこのような特別な指定をする必要がある
		expListView = (ExpandableListView)sideMenu.getRightBehindView().findViewById(R.id.lvExp);
		expListView.setGroupIndicator(null);
		//Listデータの準備
		prepareListData();
		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
		
		//setting list adapter
		expListView.setAdapter(listAdapter);

	}


	//Slide menu 設定
	public void slideMenu() {		
		//SideMenu関連
		sideMenu = new SimpleSideDrawer(this);
		sideMenu.setRightBehindContentView(R.layout.side_menu);		
        findViewById(R.id.side_menu).setOnClickListener(new OnClickListener() {
            @Override 
            public void onClick(View v) {
            	sideMenu.toggleRightDrawer();
            }
        });
	}
	
	
	//Facebook Token取得メソッド
	@SuppressWarnings("deprecation")
	public void Facebook() {
		facebook.authorize(this, new String[] { "user_events", "user_groups", "user_friends","read_stream" }, 
				new DialogListener(){
			@Override
			public void onComplete(Bundle values) {
				// TODO Auto-generated method stub
				Log.e("Facebook Token", facebook.getAccessToken());
				loginurl = loginurl+facebook.getAccessToken();
				loginRequest();
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
	
	//データの準備
	private void prepareListData() {
		// TODO Auto-generated method stub
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		
		//AddingChaildData
		listDataHeader.add("Top 1");
		listDataHeader.add("Top 2");
		listDataHeader.add("Top 3");
		
		//List childData
		List<String> top1 = new ArrayList<String>();
		top1.add("child1");
		top1.add("child2");
		top1.add("child3");
		
		//List childData
		List<String> top2 = new ArrayList<String>();
		top2.add("child1");
		top2.add("child2");
		top2.add("child3");
		
		//List childData
		List<String> top3 = new ArrayList<String>();
		top3.add("child1");
		top3.add("child2");
		top3.add("child3");
		
		listDataChild.put(listDataHeader.get(0), top1);
		listDataChild.put(listDataHeader.get(1), top2);
		listDataChild.put(listDataHeader.get(2), top3);
		
	}

	public void SampleAPI() {
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
	public void loginRequest() {
		Log.e("LoginRequest", "呼ばれたよ！");
		StringRequest stringRequest = new StringRequest(Method.GET, loginurl,
				new Listener<String>(){
			@Override
			public void onResponse(String result) {
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
		rQueue.add(stringRequest);		
	}
}
