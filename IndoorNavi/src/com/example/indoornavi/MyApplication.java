package com.example.indoornavi;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Intent;
import android.graphics.PointF;

public class MyApplication extends Application {

	public ArrayList<Element> elements = new ArrayList<Element>();
	private Element searchElement = null;
	//public PointF point;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		

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