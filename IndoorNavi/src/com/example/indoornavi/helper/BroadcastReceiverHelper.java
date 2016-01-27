package com.example.indoornavi.helper;

import com.example.indoornavi.MyApplication;
import com.example.indoornavi.MyApplication.Element;
import com.example.indoornavi.MyApplication.Point;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastReceiverHelper extends BroadcastReceiver {
	
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		MyApplication application = (MyApplication) context.getApplicationContext();  
		String id = intent.getStringExtra("id");
		String nextPoint[] = intent.getStringExtra("nextPoint").split(",");
		int x = Integer.parseInt(nextPoint[0]);
		int y = Integer.parseInt(nextPoint[1]);
		
		Element ele = new Element(id, "rect", new Point(x, y));
		application.elements.add(ele);
		
		Log.i("zhr", "id = " + id);
		
		
		
	}

}
