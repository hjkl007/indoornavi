package com.example.indoornavi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OpenCamera extends Activity implements SurfaceHolder.Callback,
		Camera.PreviewCallback {

	Button bt1;
	Camera camera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opencamera);

		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		// surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		bt1 = (Button) findViewById(R.id.button1);
		bt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OpenCamera.this, Scan.class);
				String deviceId = "003";
				intent.putExtra("ID", deviceId);
				startActivity(intent);
			}
		});
	}


	
	private void initCamera()  {
		try {
			camera = Camera.open();
		} catch (RuntimeException e) {
			System.err.println(e);
			return;
		}
		Camera.Parameters param = camera.getParameters();
		param.setPreviewSize(640, 480);
		param.setPreviewFpsRange(20000, 20000);
		param.set("orientation", "portrait");   
        param.set("rotation", 90); // 镜头角度转90度（默认摄像头是横拍）   
        camera.setDisplayOrientation(90); // 在2.2以上可以使用  
		camera.setParameters(param);
		camera.setPreviewCallback(this);
		
		try {
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
			camera.autoFocus(null);         //自动对焦  
		} catch (Exception e) {
			System.err.println(e);
			return;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
  
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub	
		initCamera();	
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if(camera != null){  
            camera.setPreviewCallback(null);  
            //if(isPreview)  
                camera.stopPreview();  
            camera.release();  
            camera = null;  
        }  
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		// 刚刚拍照的文件名
		Log.i("zhr", "call onPreviewFrame");
		/*String fileName = "IMG_"
				+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
						.toString() + ".jpg";
		File sdRoot = Environment.getExternalStorageDirectory();
		String dir = "/Camera/";
		File mkDir = new File(sdRoot, dir);
		if (!mkDir.exists())
			mkDir.mkdirs();
		File pictureFile = new File(sdRoot, dir + fileName);
		if (!pictureFile.exists()) {
			try {
				pictureFile.createNewFile();
				Camera.Parameters parameters = camera.getParameters();
				Size size = parameters.getPreviewSize();
				YuvImage image = new YuvImage(data,
						parameters.getPreviewFormat(), size.width, size.height,
						null);
				FileOutputStream filecon = new FileOutputStream(pictureFile);
				image.compressToJpeg(
						new Rect(0, 0, image.getWidth(), image.getHeight()),
						90, filecon);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}
	
	private native boolean sendImageData(byte[] data, String format, int width, int height);
	private native String getDeviceId();
	
	static {
		System.loadLibrary("algo");
	}
}
















