package com.example.indoornavi;


import com.jiahuan.svgmapview.SVGMapView;
import com.jiahuan.svgmapview.overlay.SVGMapLocationOverlay;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Handler;

import android.view.animation.AccelerateInterpolator;

public class UpdateCompass{
	Context ct;
	
	private final float MAX_ROATE_DEGREE = 1.0f;
    private SensorManager mSensorManager;
    private Sensor mOrientationSensor;
    private float mDirection;
    private float mTargetDirection;
    private AccelerateInterpolator mInterpolator;
    protected final Handler mHandler;
    private boolean mStopDrawing;

    private SVGMapView mapView;
    private SVGMapLocationOverlay locationOverlay;
	
	
	public UpdateCompass(Context context, SVGMapView mv, SVGMapLocationOverlay lo){
		ct = context;
		mapView = mv;
		locationOverlay = lo;
		
		
		initResources();
		initServices();
		
		if (mOrientationSensor != null) {
            mSensorManager.registerListener(mOrientationSensorEventListener, mOrientationSensor,
                    SensorManager.SENSOR_DELAY_GAME);
        }
        mStopDrawing = false;
        mHandler = new Handler(ct.getMainLooper());
        mHandler.postDelayed(mCompassViewUpdater, 20);
        
	}
	
	public void compassPause(){
		mStopDrawing = true;
        if (mOrientationSensor != null) {
            mSensorManager.unregisterListener(mOrientationSensorEventListener);
        }
	}
	
	
	protected Runnable mCompassViewUpdater = new Runnable() {
        @Override
        public void run() {
            if (!mStopDrawing) {
                if (mDirection != mTargetDirection) {

                    // calculate the short routine
                    float to = mTargetDirection;
                    if (to - mDirection > 180) {
                        to -= 360;
                    } else if (to - mDirection < -180) {
                        to += 360;
                    }

                    // limit the max speed to MAX_ROTATE_DEGREE
                    float distance = to - mDirection;
                    if (Math.abs(distance) > MAX_ROATE_DEGREE) {
                        distance = distance > 0 ? MAX_ROATE_DEGREE : (-1.0f * MAX_ROATE_DEGREE);
                    }

                    // need to slow down if the distance is short
                    mDirection = normalizeDegree(mDirection
                            + ((to - mDirection) * mInterpolator.getInterpolation(Math
                                    .abs(distance) > MAX_ROATE_DEGREE ? 0.4f : 0.3f)));
                    //mPointer.updateDirection(mDirection);
                    locationOverlay.setIndicatorArrowRotateDegree(-mDirection);
                    mapView.refresh();
                }

                //updateDirection();

                mHandler.postDelayed(mCompassViewUpdater, 20);
            }
        }
    };
    
    
    private void initResources() {
        mDirection = 0.0f;
        mTargetDirection = 0.0f;
        mInterpolator = new AccelerateInterpolator();
        mStopDrawing = true;
        
    }

    private void initServices() {
        // sensor manager
        mSensorManager = (SensorManager) ct.getSystemService(Context.SENSOR_SERVICE);
        mOrientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

    }
    
    private float normalizeDegree(float degree) {
        return (degree + 720) % 360;
    }

    private SensorEventListener mOrientationSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float direction = event.values[0] * -1.0f;
            mTargetDirection = normalizeDegree(direction);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
	
}