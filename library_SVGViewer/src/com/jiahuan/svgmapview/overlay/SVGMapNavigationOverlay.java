package com.jiahuan.svgmapview.overlay;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.jiahuan.svgmapview.SVGMapView;

public class SVGMapNavigationOverlay extends SVGMapBaseOverlay
{
	
	int drawLevel = LOCATION_LEVEL - 2;
	static final String TAG = "SVGMapNavigationOverlay";
	private ArrayList<PointF> path;
	private Paint dashPaint;
	
	public SVGMapNavigationOverlay(SVGMapView svgMapView){
		
		this.showLevel = drawLevel;
		path = new ArrayList<PointF>();
		dashPaint = new Paint();

	}
	
	public void setPathLine(ArrayList<PointF> p){
		this.path = p;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTap(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Canvas canvas, Matrix matrix, float currentZoom,
			float currentRotateDegrees) {
		// TODO Auto-generated method stub
		dashPaint.setAntiAlias(true);
		dashPaint.setARGB(255, 0, 128, 255);
		dashPaint.setStyle(Paint.Style.STROKE);
		dashPaint.setPathEffect(new DashPathEffect(new float[]{ 10, 40, }, 0));
		dashPaint.setStrokeWidth(12);
		if(path != null){
			Path pathLine = new Path();
			pathLine.moveTo(path.get(0).x, path.get(0).y);
			for(int i = 1; i < path.size(); i++){
				pathLine.lineTo(path.get(i).x, path.get(i).y);
			}
			canvas.drawPath(pathLine, dashPaint);
		}
		
		
	}
	
	
}