package com.example.indoornavi;

import java.util.ArrayList;
import com.example.indoornavi.algorithm.Dijkstra;

import android.app.Application;
import android.graphics.PointF;

public class MyApplication extends Application {

	public ArrayList<Element> elements = new ArrayList<Element>();
	private Element searchElement = null;
	public Dijkstra dijkstra = new Dijkstra();
	private PointF currentPoint = new PointF(465, 488);
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		

	}
	
	public void setCurrentPoint(PointF p){
		this.currentPoint.set(p);
	}
	
	public PointF getCurrentPoint(){
		return this.currentPoint;
	}
	
	public void setSearchElement(Element search){
		this.searchElement = search;
	}
	public Element getSearchElement(){
		return this.searchElement;
	}
	
	public static class Element{
		String id;
		String elementName;
		PointF nextPoint;
		PointF centerPoint;

		public Element(String mid, String melementName, PointF mp, PointF cen){
			this.id = mid;
			this.elementName = melementName;
			this.nextPoint = mp;
			this.centerPoint = cen;
		}
		
		public String getId(){
			return this.id;
		}
		
		public void setNextPoint(PointF point){
			this.nextPoint = point;
		}
		public PointF getNextPoint(){
			return this.nextPoint;
		}
		public void setCenterPoint(PointF point){
			this.centerPoint = point;
		}
		public PointF getCenterPoint(){
			return this.centerPoint;
		}
	}

}