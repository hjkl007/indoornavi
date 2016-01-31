package com.example.indoornavi.helper;

import com.example.indoornavi.MyApplication;
import com.example.indoornavi.MyApplication.Element;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.util.Log;

public class BroadcastReceiverHelper extends BroadcastReceiver {
	
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		MyApplication application = (MyApplication) context.getApplicationContext();  
		String id = intent.getStringExtra("id");
		String nextPoint[] = intent.getStringExtra("nextPoint").split(",");
		float[] center = intent.getFloatArrayExtra("center");
		int x = Integer.parseInt(nextPoint[0]);
		int y = Integer.parseInt(nextPoint[1]);
		
		Element ele = new Element(id, "rect", new PointF(x, y), new PointF(center[0], center[1]));
		application.elements.add(ele);
		
		Log.i("zhr", "id = " + id);
		
		
		
	}

}
