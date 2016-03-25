package com.example.indoornavi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
	Button bt2;
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
	public void initView() {

		tv = (TextView) findViewById(R.id.location);
		bt1 = (Button) findViewById(R.id.button1);
		bt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(camera, 100);
			}
		});

		bt2 = (Button) findViewById(R.id.button2);
		bt2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent picture = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(picture, 101);
			}
		});

		initSlidingMenu();
	}

	@Override
	public void onPause() {
		super.onPause();
		// stopAmap();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String path = "";
		if (requestCode == 100 && resultCode == Activity.RESULT_OK
				&& null != data) {
			Bundle bundle = data.getExtras();
			// 获取相机返回的数据，并转换为图片格式
			Bitmap bitmap = (Bitmap) bundle.get("data");
			String fileName = "indoornavi_"
					+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
							.toString() + ".jpg";
			path = saveMyBitmap(fileName,bitmap);
			
		}
		if (requestCode == 101 && resultCode == Activity.RESULT_OK
				&& null != data) {
			path = getImagePath(data);
		}
		if (requestCode == 103 && resultCode == Activity.RESULT_OK
				&& null != data) {
			path = getImagePath(data);
			Intent intent = new Intent(this, UploadImage.class);
			intent.putExtra("imagePath", path);
			startActivity(intent);
		}
		

	}
	
	public String getImagePath(Intent data){
		Uri selectedImage = data.getData();
		String[] filePathColumns = { MediaStore.Images.Media.DATA };
		Cursor c = this.getContentResolver().query(selectedImage,
				filePathColumns, null, null, null);
		c.moveToFirst();
		int columnIndex = c.getColumnIndex(filePathColumns[0]);
		String picturePath = c.getString(columnIndex);
		Log.i("zhr", picturePath);
		c.close();
		return picturePath;
	}
	
	public String saveMyBitmap(String bitName, Bitmap mBitmap) {
		String filePath = "/sdcard/DCIM/" + bitName;
		File f = new File(filePath);
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
			return filePath;
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void startAmap() {
		aMapManager = LocationManagerProxy.getInstance(this);
		/*
		 * mAMapLocManager.setGpsEnable(false);
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
		 * API定位采用GPS和网络混合定位方式
		 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		 */
		aMapManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork,
				10000, 10, mAMapLocationListener);
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
					desc_cut = desc.substring(desc.length() - index,
							desc.length());

				}
				/*
				 * String str = ("定位成功:(" + geoLng + "," + geoLat + ")" +
				 * "\n精    度    :" + location.getAccuracy() + "米" + "\n定位方式:" +
				 * location.getProvider() + "\n定位时间:" + new
				 * Date(location.getTime()).toLocaleString() + "\n城市编码:" +
				 * cityCode + "\n位置描述:" + desc + "\n省:" + location.getProvince()
				 * + "\n市:" + location.getCity() + "\n区(县):" +
				 * location.getDistrict() + "\n区域编码:" + location .getAdCode());
				 * // mTextView.setText("高德定位\n" + str); // Log.i("zhr", str);
				 */stopAmap();

				SharedPreferences.Editor prefs = HelloView.this
						.getSharedPreferences(mPerfName, 0).edit();
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
