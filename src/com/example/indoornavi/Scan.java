package com.example.indoornavi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.indoornavi.FileHelper;
import com.jiahuan.svgmapview.SVGMapView;
import com.jiahuan.svgmapview.SVGMapViewListener;
import com.jiahuan.svgmapview.overlay.SVGMapLocationOverlay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

public class Scan extends Activity {

	String url = "http://192.168.1.106/api/";
	EditText et;
	String filename = "";
	String dir = "/IndoorNavi/";
	File sdRoot = Environment.getExternalStorageDirectory();
	boolean isExist = false;
	final int file_exist = 1;
	final int file_no_exist = 2;
	private SVGMapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_view);
		et = new EditText(this);
		mapView = (SVGMapView) findViewById(R.id.location_mapview);

		mapView.registerMapViewListener(new SVGMapViewListener() {
			@Override
			public void onMapLoadComplete() {
				SVGMapLocationOverlay locationOverlay = new SVGMapLocationOverlay(
						mapView);
				locationOverlay.setIndicatorArrowBitmap(BitmapFactory
						.decodeResource(getResources(), R.drawable.indicator_arrow));
				locationOverlay.setPosition(new PointF(400, 500));
				locationOverlay.setIndicatorCircleRotateDegree(90);
				locationOverlay.setMode(SVGMapLocationOverlay.MODE_COMPASS);
				locationOverlay.setIndicatorArrowRotateDegree(-45);
				mapView.getOverLays().add(locationOverlay);
				mapView.refresh();
			}

			@Override
			public void onMapLoadError() {
			}

			@Override
			public void onGetCurrentMap(Bitmap bitmap) {
			}
		});

		new AlertDialog.Builder(this).setTitle("请输入：")
				.setIcon(android.R.drawable.ic_dialog_info).setView(et)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {// ���ȷ����ť
							@Override
							public void onClick(DialogInterface dialog,
									int which) {// ȷ����ť����Ӧ�¼�
								// TODO Auto-generated method stub
								filename = et.getText().toString() + ".svg";
								Log.i("zhr", "locate = " + filename);

								File mkDir = new File(sdRoot, dir);
								if (!mkDir.exists())
									mkDir.mkdirs();

								if (mkDir.listFiles().length > 0) {
									for (File file : mkDir.listFiles()) {
										if (filename.equals(file.getName())) {
											isExist = true;
											break;
										}
									}
									Message message = new Message();
									if (isExist)
										message.what = file_exist;
									else
										message.what = file_no_exist;
									mHandler.sendMessage(message);
								}

							}

						}).setNegativeButton("取消", null).show();

	}

	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case file_exist:
				String f = sdRoot.getPath() + dir + filename;
				Log.i("zhr", f);
				mapView.loadMap(FileHelper.getContent(f));
				break;

			case file_no_exist:
				String url_api = url + "?zip=" + filename.substring(0, 6)
						+ "&s=" + filename.substring(6, 9);
				Log.i("zhr", url_api);
				FileHelper.getURL(url_api);
				String f2 = sdRoot.getPath() + dir + filename;
				mapView.loadMap(FileHelper.getContent(f2));

				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

}
