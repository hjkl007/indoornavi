package com.jiahuan.svgmapview;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    
    private ImageView scalePlate;
    
    private TextView scaleData;
    
    private LinearLayout linear1;
    
    private DisplayMetrics dm;
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
        
        dm = getResources().getDisplayMetrics();
        lastZoomValue = getCurrentZoomValue();       
        SharedPreferences mPref = context.getSharedPreferences(mPerfName, 0);
        svgWidth = mPref.getFloat("svgWidth", 0);
        scaleValue = mPref.getFloat("svgScale", 0);
        
        brandImageView = new ImageView(context, attrs, defStyle);
        brandImageView.setScaleType(ScaleType.FIT_START);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, setDip(15));
        params.gravity = Gravity.BOTTOM | Gravity.LEFT;
        params.leftMargin = setDip(4);
        params.bottomMargin = setDip(10);
        addView(brandImageView, params);
        
        linear1 = new LinearLayout(context, attrs, defStyle);
        LayoutParams paramsLinear = new LayoutParams(LayoutParams.MATCH_PARENT, setDip(15));
        paramsLinear.gravity = Gravity.BOTTOM | Gravity.LEFT;
        paramsLinear.leftMargin = setDip(8);
        paramsLinear.bottomMargin = setDip(40);
        
        scaleData = new TextView(context, attrs, defStyle);
        scaleData.setTextSize(15);
        LayoutParams params2 = new LayoutParams(LayoutParams.WRAP_CONTENT, setDip(15));
        params2.gravity = Gravity.BOTTOM | Gravity.LEFT;
        linear1.addView(scaleData, params2);
        
        scalePlate = new ImageView(context, attrs, defStyle);
        scalePlate.setImageResource(R.drawable.scale);
        //scalePlate.setScaleX((float) (dm.xdpi/2.54));
        scalePlate.setScaleType(ScaleType.FIT_START);
        LayoutParams params3 = new LayoutParams(LayoutParams.WRAP_CONTENT, setDip(10));
        params3.gravity = Gravity.BOTTOM | Gravity.LEFT;
        linear1.addView(scalePlate, params3);
     
        addView(linear1, paramsLinear);
    }
    
    public int setDip(float f){
    	return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f, dm);
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
        	int finalScale = (int) (scaleValue / ((svgWidth*2.54*zoom)/dm.xdpi));
        	this.scaleData.setText(String.valueOf(finalScale)+'m');
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
