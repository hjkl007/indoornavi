package com.example.indoornavi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

/**
 * Created by doom on 15/4/2.
 */
public class FileHelper {
	
	public static String getContent(String fileName) {
		try {

			InputStreamReader inputReader = new InputStreamReader(
					new FileInputStream(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;

			//System.out.print(Result);
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressLint("NewApi")
	public static void getURL(String path) {
		String fileName = "";
		String dir = "/IndoorNavi/";
		File sdRoot = Environment.getExternalStorageDirectory();
		try {
			// Open the URLConnection for reading
			URL u = new URL(path);
			// URL u = new URL("http://www.baidu.com/");
			HttpURLConnection uc = (HttpURLConnection) u.openConnection();

			int code = uc.getResponseCode();
			String response = uc.getResponseMessage();
			//System.out.println("HTTP/1.x " + code + " " + response);
			for (int j = 1;; j++) {
				String key = uc.getHeaderFieldKey(j);
				String header = uc.getHeaderField(j);
				if (!(key == null)) {
					if (key.equals("Content-Name"))
						fileName = header;
				}
				if (header == null || key == null)
					break;
				//System.out.println(uc.getHeaderFieldKey(j) + ": " + header);
			}
			Log.i("zhr", fileName);
			//System.out.println();

			try (InputStream in = new BufferedInputStream(uc.getInputStream())) {

				// chain the InputStream to a Reader
				Reader r = new InputStreamReader(in);
				int c;
				File mapFile = new File(sdRoot, dir + fileName);
				mapFile.createNewFile();
				FileOutputStream filecon = new FileOutputStream(mapFile);
				while ((c = r.read()) != -1) {
					//System.out.print((char) c);
					filecon.write(c);
					filecon.flush();

				}
				filecon.close();
			}

		} catch (MalformedURLException ex) {
			System.err.println(path + " is not a parseable URL");
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}