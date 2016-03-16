package com.example.indoornavi;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.example.indoornavi.view.DrawerView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class HelloView extends Activity {

	final String mPerfName = "com.example.indoornavi";
	Button bt1;
	protected SlidingMenu side_drawer;
	private LocationManagerProxy aMapManager;
	private TextView tv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_view);

		initView();
		startAmap();
	}
	
	@SuppressLint("NewApi")
	public void initView(){
		
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			Window window = getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
					| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
		}
		tv = (TextView) findViewById(R.id.location);
		bt1 = (Button) findViewById(R.id.button1);
		bt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HelloView.this, Scan.class);
				String deviceId = "003";
				intent.putExtra("ID", deviceId);
				startActivity(intent);
			}
		});

		initSlidingMenu();
	}
	
	@Override
	public void onPause(){
		super.onPause();
		//stopAmap();
	}
	

	protected void initSlidingMenu() {
		side_drawer = new DrawerView(this).initSlidingMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hello_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void startAmap() {
		aMapManager = LocationManagerProxy.getInstance(this);
		/*
		 * mAMapLocManager.setGpsEnable(false);
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
		 * API定位采用GPS和网络混合定位方式
		 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		 */
		aMapManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork, 10000, 10, mAMapLocationListener);
	}

	private void stopAmap() {
		if (aMapManager != null) {
			aMapManager.removeUpdates(mAMapLocationListener);
			aMapManager.destory();
		}
		aMapManager = null;
	}
	
private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			
		}
		
		@Override
		public void onLocationChanged(AMapLocation location) {
			if (location != null) {
				Double geoLat = location.getLatitude();
				Double geoLng = location.getLongitude();
				String cityCode = "";
				String desc = "";
				String desc_cut = "";
				Bundle locBundle = location.getExtras();
				if (locBundle != null) {
					cityCode = locBundle.getString("citycode");
					desc = locBundle.getString("desc");
					int index = desc.indexOf("靠近");
					desc_cut = desc.substring(desc.length()-index, desc.length());
					
				}
				String str = ("定位成功:(" + geoLng + "," + geoLat + ")"
						+ "\n精    度    :" + location.getAccuracy() + "米"
						+ "\n定位方式:" + location.getProvider() + "\n定位时间:"
						+ new Date(location.getTime()).toLocaleString() + "\n城市编码:"
						+ cityCode + "\n位置描述:" + desc + "\n省:"
						+ location.getProvince() + "\n市:" + location.getCity()
						+ "\n区(县):" + location.getDistrict() + "\n区域编码:" + location
						.getAdCode());
				//mTextView.setText("高德定位\n" + str);
				//Log.i("zhr", str);
				stopAmap();
				
				
				SharedPreferences.Editor prefs = HelloView.this.getSharedPreferences(mPerfName, 0).edit();
				prefs.putFloat("Latitude", geoLat.floatValue());
				prefs.putFloat("Longitude", geoLng.floatValue());
				prefs.putString("AdCode", location.getAdCode());
				prefs.putString("Desc", desc_cut);
				prefs.commit();
				
				tv.setText(desc_cut);
				
				
			}
		}
	};
}
