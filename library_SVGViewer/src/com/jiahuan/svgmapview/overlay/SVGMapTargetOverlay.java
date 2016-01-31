package com.jiahuan.svgmapview.overlay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import com.jiahuan.svgmapview.SVGMapView;
import com.jiahuan.svgmapview.core.helper.RectHelper;

public class SVGMapTargetOverlay extends SVGMapBaseOverlay
{
	int drawLevel = LOCATION_LEVEL - 1;
	static final String TAG = "SVGMapTargetOverlay";
	private Bitmap IndicatorBitmap;
	private PointF currentPosition = null;
	private Context context;
	public final String CLICK_ACTION = "android.intent.action.click";
	
	public SVGMapTargetOverlay(SVGMapView svgMapView, Context con)
    {
        initLayer(svgMapView);
        this.context = con;
    }
	
	private void initLayer(SVGMapView svgMapView)
    {
		this.showLevel = drawLevel;
		
		
    }
	
	public void setIndicatorBitmap(Bitmap bitmap)
    {
        this.IndicatorBitmap = bitmap;
    }
	
	public PointF getPosition()
    {
        return currentPosition;
    }

    public void setPosition(PointF position)
    {
        this.currentPosition = position;
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
		if(RectHelper.withRect(event, currentPosition.x, currentPosition.y, 50) || true){
			Intent intent = new Intent();
			intent.setAction(CLICK_ACTION);
			context.sendBroadcast(intent);
		}
		Log.i(TAG, "currentPosition = "+currentPosition);
		Log.i(TAG, "ClickPosition = "+event.getX()+", " + event.getY());
	}

	@Override
	public void draw(Canvas canvas, Matrix matrix, float currentZoom,
			float currentRotateDegrees) {
		// TODO Auto-generated method stub
		canvas.save();
        if (this.isVisible && this.currentPosition != null)
        {
        	float goal[] = {currentPosition.x, currentPosition.y};
            matrix.mapPoints(goal);
            
            if (IndicatorBitmap != null)
            {
            	canvas.drawBitmap(IndicatorBitmap, goal[0] - IndicatorBitmap.getWidth() / 2, goal[1] - IndicatorBitmap.getHeight(), new Paint());
            }
            
        }
        canvas.restore();
	}
	
}