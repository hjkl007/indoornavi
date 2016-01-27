package com.example.indoornavi.helper;

import com.example.indoornavi.MyApplication;
import com.example.indoornavi.MyApplication.Element;
import com.example.indoornavi.MyApplication.Point;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastReceiverHelper extends BroadcastReceiver {
	
	Context context;
	MyApplication application = (MyApplication) context.getApplicationContext();  
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String id = intent.getStringExtra("id");
		String nextPoint[] = intent.getStringExtra("nextPoint").split(",");
		int x = Integer.parseInt(nextPoint[0]);
		int y = Integer.parseInt(nextPoint[1]);
		
		application.elements.add(new Element(id, "rect", new Point(x, y)));
		
		
		
	}

}
