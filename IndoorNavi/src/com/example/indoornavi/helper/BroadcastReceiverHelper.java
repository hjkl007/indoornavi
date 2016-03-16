package com.example.indoornavi.helper;

import com.example.indoornavi.HelloView;
import com.example.indoornavi.MyApplication;
import com.example.indoornavi.MyApplication.Element;
import com.example.indoornavi.algorithm.Dijkstra.Node;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.util.Log;

public class BroadcastReceiverHelper extends BroadcastReceiver {
	
	public static final String RECTDESC_ACTION = "android.intent.action.rectdesc.data";
	public static final String DOCUMENTDESC_ACTION = "android.intent.action.documentdesc.data";
	public static final String SCALE_ACTION = "android.intent.action.scale.data";
	final String mPerfName = "com.example.indoornavi";
	MyApplication application;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		application = (MyApplication) context.getApplicationContext();  
		
		if(intent.getAction().equals(RECTDESC_ACTION)){
			
			String id = intent.getStringExtra("id");
			String nextPoint[] = intent.getStringExtra("nextPoint").split(",");
			float[] center = intent.getFloatArrayExtra("center");
			int x = Integer.parseInt(nextPoint[0]);
			int y = Integer.parseInt(nextPoint[1]);
			
			Element ele = new Element(id, "rect", new PointF(x, y), new PointF(center[0], center[1]));
			//阻止重复添加变量，不是根本解决方法
			boolean isElementExist = false;
			for(Element e : application.elements){
				if(e.getId().equals(ele.getId()))
					isElementExist = true;
			}
			if(!isElementExist)
				application.elements.add(ele);
			
			Log.i("zhr", "id = " + id);
		}else if(intent.getAction().equals(DOCUMENTDESC_ACTION)){
			String naviInfo[] = intent.getStringExtra("naviInfo").split(":");
			//点的位置
			String po[] = naviInfo[1].split(",");
			PointF position = new PointF(Integer.parseInt(po[0]), Integer.parseInt(po[1]));
			String nextNodeName[] = naviInfo[2].split(",");
			
			boolean isNodeExist = false;
			Node node1 = new Node();
			boolean helperFlag = false;
			for(Node n : application.dijkstra.getNaviNodes()){
				//检测是否已存在节点,已存在
				if(n.getName().equals(naviInfo[0])) {
					isNodeExist = true;
					n.setPosition(position);
					//添加相邻节点
					for(String nodeName : nextNodeName){
						boolean isNameExist = false;
						//检测相邻点是否已存在于naviNodes
						for(Node node : application.dijkstra.getNaviNodes()){
							if(node.getName().equals(nodeName)){
								n.getChild().add(node);
								isNameExist = true;
								break;
							}
						}
						if(!isNameExist){
							Node node2 = new Node(nodeName);
							node1 = node2;
							n.getChild().add(node2);
							helperFlag = true;
							
							
						}
					}
				}
			}
			
			if(helperFlag){
				application.dijkstra.getNaviNodes().add(node1);
			}
			
			if(!isNodeExist){
				Node temp = new Node(naviInfo[0], position);
				application.dijkstra.getNaviNodes().add(temp);
				//添加相邻节点
				for(String nodeName : nextNodeName){
					boolean isNameExist = false;
					for(Node node : application.dijkstra.getNaviNodes()){
						if(node.getName().equals(nodeName)){
							temp.getChild().add(node);
							isNameExist = true;
							break;
						}
					}
					if(!isNameExist){
						Node node2 = new Node(nodeName);
						temp.getChild().add(node2);
						application.dijkstra.getNaviNodes().add(node2);
					}
				}
				
				
			}
				
		}		
		
	}

}
