package com.example.indoornavi;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Intent;

public class MyApplication extends Application {

	public List<Element> elements = new ArrayList<Element>();
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		

	}
	
	public static class Element{
		String id;
		String elementName;
		Point p;	

		public Element(String mid, String melementName, Point mp){
			this.id = mid;
			this.elementName = melementName;
			this.p = mp;
		}
	}
	
	public static class Point {
		protected int x;
		protected int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		Point(Point p) {
			this.x = p.getx();
			this.y = p.gety();
		}

		public int getx() {
			return x;
		}

		public int gety() {
			return y;
		}

		public void setx(int x) {
			this.x = x;
		}

		public void sety(int y) {
			this.y = y;
		}
	}
}