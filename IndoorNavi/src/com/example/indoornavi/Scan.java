package com.example.indoornavi;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.indoornavi.MyApplication.Element;
import com.example.indoornavi.algorithm.Dijkstra.Node;
import com.example.indoornavi.helper.FileHelper;
import com.jiahuan.svgmapview.SVGMapView;
import com.jiahuan.svgmapview.SVGMapViewListener;
import com.jiahuan.svgmapview.overlay.SVGMapLocationOverlay;
import com.jiahuan.svgmapview.overlay.SVGMapNavigationOverlay;
import com.jiahuan.svgmapview.overlay.SVGMapTargetOverlay;

public class Scan extends Activity {

	String url = "http://192.168.1.107/api/";
	final String mPerfName = "com.example.indoornavi";
	EditText et;
	String filename = "";
	String dir = "/IndoorNavi/";
	File sdRoot = Environment.getExternalStorageDirectory();
	boolean isExist = false;
	final int file_exist = 1;
	final int file_no_exist = 2;
	private SVGMapView mapView;
	private ImageView search;
	public final static int SEARCHRESULTCODE = 10;
	MyApplication application;
	public final String CLICK_ACTION = "android.intent.action.click";
	
	UpdateCompass updateCompass;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_view);
		application = (MyApplication) this.getApplicationContext();
		et = new EditText(this);
		mapView = (SVGMapView) findViewById(R.id.location_mapview);
		mapView.registerMapViewListener(new SVGMapViewListener() {
			@Override
			public void onMapLoadComplete() {
				SVGMapLocationOverlay locationOverlay = new SVGMapLocationOverlay(
						mapView);
				locationOverlay.setIndicatorArrowBitmap(BitmapFactory
						.decodeResource(getResources(),
								R.drawable.indicator_arrow));
				locationOverlay.setMode(SVGMapLocationOverlay.MODE_COMPASS);
				locationOverlay.setPosition(application.getCurrentPoint());
				//locationOverlay.setIndicatorCircleRotateDegree(90);
				
				//locationOverlay.setIndicatorArrowRotateDegree(-45);
				mapView.getOverLays().add(locationOverlay);
				mapView.refresh();
				updateCompass = new UpdateCompass(Scan.this, mapView, locationOverlay);
				
			}

			@Override
			public void onMapLoadError() {
			}

			@Override
			public void onGetCurrentMap(Bitmap bitmap) {
			}
		});

		search = (ImageView) findViewById(R.id.top_more);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent_search = new Intent(getApplicationContext(),
						SearchActivity.class);
				startActivityForResult(intent_search, SEARCHRESULTCODE);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
			}
		});
		
		String deviceId = getIntent().getStringExtra("ID");
		SharedPreferences mPref = getSharedPreferences(mPerfName, 0);
		String adCode = mPref.getString("AdCode", "");
		filename = adCode + deviceId + ".svg";
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

/*		new AlertDialog.Builder(this).setTitle("请输入：")
				.setIcon(android.R.drawable.ic_dialog_info).setView(et)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						filename = "110114003.svg";
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

				}).setNegativeButton("取消", null).show();*/

		IntentFilter intentFilter = new IntentFilter(CLICK_ACTION);
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				
				
				showAlterBuilder(Scan.this);
			}
		}, intentFilter);

	}
	
	@Override
    protected void onPause() {
        super.onPause();
        //updateCompass.compassPause();
    }

	public void showAlterBuilder(Context context) {
		
		AlertDialog.Builder targetBuilder = new AlertDialog.Builder(context);
		LayoutInflater inflater = LayoutInflater.from(this);
		View target_builder = inflater.inflate(R.layout.target_builder_view,
				null);
		ImageView imageView = (ImageView) target_builder
				.findViewById(R.id.detail);
		imageView.setImageResource(R.drawable.target_detial);
		targetBuilder.setView(target_builder);
		targetBuilder.setCancelable(false);
		targetBuilder.setPositiveButton("到这里",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						PointF target = application.getSearchElement().getNextPoint();
						PointF start = application.getCurrentPoint();
						Node startNode = new Node("start", start);
						Node targetNode = new Node ("target", target);
						
						application.dijkstra.initNode(startNode, targetNode);
						ArrayList<Node> path = application.dijkstra.computeDistance(application.dijkstra.getStartNode());
						ArrayList<PointF> pathLine = new ArrayList<PointF>();
						pathLine.add(application.getSearchElement().centerPoint);
						for(Node n : path){
							pathLine.add(n.getPosition());
						}
						
						SVGMapNavigationOverlay navigationOverlay = new SVGMapNavigationOverlay(mapView);
						navigationOverlay.setPathLine(pathLine);
						mapView.getOverLays().add(navigationOverlay);
						mapView.refresh();
					}
				});
		targetBuilder.setNeutralButton("详情",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
					}
				});
		targetBuilder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
					}
				});
		targetBuilder.show();
		
	}
	

	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case file_exist:
				String f = sdRoot.getPath() + dir + filename;
				Log.i("zhr", f);
				mapView.loadMap(FileHelper.getContent(f), Scan.this);
				break;

			case file_no_exist:
				String url_api = url + "?zip=" + filename.substring(0, 6)
						+ "&s=" + filename.substring(6, 9);
				Log.i("zhr", url_api);
				FileHelper.getURL(url_api);
				String f2 = sdRoot.getPath() + dir + filename;
				mapView.loadMap(FileHelper.getContent(f2), Scan.this);

				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case SEARCHRESULTCODE:
			if (resultCode == SEARCHRESULTCODE) {
				Element elementChosen = application.getSearchElement();

				SVGMapTargetOverlay targetOverlay = new SVGMapTargetOverlay(
						mapView, Scan.this);
				targetOverlay.setIndicatorBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.target));
				targetOverlay.setPosition(elementChosen.getCenterPoint());
				mapView.getOverLays().add(targetOverlay);
				mapView.refresh();
			}
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
