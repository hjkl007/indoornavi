package com.jiahuan.svgmapview;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.jiahuan.svgmapview.core.componet.MapMainView;
import com.jiahuan.svgmapview.overlay.SVGMapBaseOverlay;

import java.util.List;


public class SVGMapView extends FrameLayout
{
    private MapMainView mapMainView;

    private SVGMapController mapController;

    private ImageView brandImageView;
    
    private TextView scaleData;
    
    private float lastZoomValue = 0;
    private float svgWidth = 0;
    private float scaleValue = 0;
    public static final String mPerfName = "svgdata";

    public SVGMapView(Context context)
    {
        this(context, null);
    }

    public SVGMapView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public SVGMapView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mapMainView = new MapMainView(context, attrs, defStyle);
        addView(mapMainView);
        brandImageView = new ImageView(context, attrs, defStyle);
        brandImageView.setScaleType(ScaleType.FIT_START);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, context.getResources().getDisplayMetrics()));
        params.gravity = Gravity.BOTTOM | Gravity.LEFT;
        params.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics());
        params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
        addView(brandImageView, params);
        scaleData = new TextView(context, attrs, defStyle);
        scaleData.setTextSize(15);
        lastZoomValue = getCurrentZoomValue();
        
        SharedPreferences mPref = context.getSharedPreferences(mPerfName, 0);
        svgWidth = mPref.getFloat("svgWidth", 0);
        scaleValue = mPref.getFloat("svgScale", 0);
        

        scaleData.setText("5");
        LayoutParams params2 = new LayoutParams(LayoutParams.WRAP_CONTENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, context.getResources().getDisplayMetrics()));
        params2.gravity = Gravity.BOTTOM | Gravity.LEFT;
        params2.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, context.getResources().getDisplayMetrics());
        params2.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
        addView(scaleData, params2);
    }

    /**
     * @return the map controller.
     */
    public SVGMapController getController()
    {
        if (this.mapController == null)
        {
            this.mapController = new SVGMapController(this);
        }
        return this.mapController;
    }

    public void registerMapViewListener(SVGMapViewListener idrMapViewListener)
    {
        this.mapMainView.registeMapViewListener(idrMapViewListener);
    }

    public void loadMap(String svgString, final Context context)
    {
        this.mapMainView.loadMap(svgString, context);
    }

    public void setBrandBitmap(Bitmap bitmap) {
        this.brandImageView.setImageBitmap(bitmap);
    }
    
    public void setScaleData(){
    	float zoom = getCurrentZoomValue();
        if(lastZoomValue != zoom){
        	DisplayMetrics dm = getResources().getDisplayMetrics();
        	int finalScale = (int) (scaleValue / ((svgWidth*2.54*zoom)/dm.xdpi));
        	this.scaleData.setText(String.valueOf(finalScale));
        	lastZoomValue = zoom;
        }
    }

    public void refresh()
    {
        this.mapMainView.refresh();
        this.scaleData.refreshDrawableState();
        
    }
    

    /**
     * @return whether the map is already loaded.
     */
    public boolean isMapLoadFinsh()
    {
        return this.mapMainView.isMapLoadFinsh();
    }

    /**
     * get the current map.
     * It will be callback in the map listener of 'onGetCurrentMap'
     */
    public void getCurrentMap()
    {
        this.mapMainView.getCurrentMap();
    }


    public float getCurrentRotateDegrees()
    {
        return this.mapMainView.getCurrentRotateDegrees();
    }


    public float getCurrentZoomValue()
    {
        return this.mapMainView.getCurrentZoomValue();
    }


    public float getMaxZoomValue()
    {
        return this.mapMainView.getMaxZoomValue();
    }


    public float getMinZoomValue()
    {
        return this.mapMainView.getMinZoomValue();
    }


    public float[] getMapCoordinateWithScreenCoordinate(float screenX, float screenY)
    {
        return this.mapMainView.getMapCoordinateWithScreenCoordinate(screenX, screenY);
    }

    public List<SVGMapBaseOverlay> getOverLays()
    {
        return this.mapMainView.getOverLays();
    }



    public void onDestroy()
    {
        this.mapMainView.onDestroy();
    }

    public void onPause()
    {
        this.mapMainView.onPause();
    }

    public void onResume()
    {
        this.mapMainView.onResume();
    }

}
