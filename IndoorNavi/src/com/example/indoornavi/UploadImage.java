package com.example.indoornavi;

import java.io.File;
import java.io.FileInputStream;

import android.app.Activity;
import android.os.Bundle;

import com.litesuits.http.LiteHttp;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.AbstractRequest;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.InputStreamBody;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;


public class UploadImage extends Activity{
	// 替换自己的服务器地址
	public static final String uploadUrl = "http://192.168.0.0:8080/upload";
	LiteHttp liteHttp ;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uploadimage);
		String imagePath = getIntent().getStringExtra("imagePath");
		
		
		
		
		

/*		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(imagePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unchecked")
		final StringRequest upload = (StringRequest) new StringRequest(
				uploadUrl).setMethod(HttpMethods.Post)
				.setHttpListener(uploadListener)
				.setHttpBody(new InputStreamBody(fis));
		liteHttp.executeAsync(upload);*/
	}
	
	
	
	
	
	
	
	
	
	
	
	

	HttpListener uploadListener = new HttpListener<String>(true, false, true) {
	    @Override
	    public void onSuccess(String s, Response<String> response) {
	        response.printInfo();
	    }
	    @Override
	    public void onFailure(HttpException e, Response<String> response) {
	        response.printInfo();
	    }
	    @Override
	    public void onUploading(AbstractRequest<String> request, long total, long len) {
	    }
	};
}