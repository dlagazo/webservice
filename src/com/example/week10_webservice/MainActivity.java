package com.example.week10_webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		InputStream is = getInputStreamFromUrl("http://dlagazo.site90.net/ronjo");
		StringBuilder sb = inputStreamToString(is);
		String result = sb.toString();
		TextView tv = new TextView(this);
		tv.setText(result);
		this.setContentView(tv);
		parseJSON(result);
	}

	public static InputStream getInputStreamFromUrl(String url)
	{
	    InputStream content = null;
	            
	    try
	    {
	          HttpClient httpclient= new DefaultHttpClient();
	          HttpGet get = new HttpGet(url);
	          //get.setHeader("Accept", "text/xml");
	          HttpResponse response = httpclient.execute(get);
	          content = response.getEntity().getContent();
	    }
	    catch(Exception e)
	    {
	          Log.e("[GET REQUEST]" , "Network exception" , e);
	    }
	    return content;
	}

	private StringBuilder inputStreamToString(InputStream is)
	{
	            String line = "";
	            StringBuilder total = new StringBuilder();
	            
	            BufferedReader rd = new BufferedReader( new InputStreamReader(is));
	             try
	            {
	                   while ((line = rd.readLine()) != null)
	                  {
	                        total.append(line);
	                  }
	            }
	             catch (IOException e)
	            {
	                  
	            }
	             return total;
	}

	public void parseJSON(String string)
	{
		
		
		JSONObject json;
		try 
		{
			
			json = new JSONObject(string);
			JSONArray apps = json.getJSONArray("news");
			for(int i = 0; i < apps.length(); i++)
			{
				JSONObject temp = apps.getJSONObject(i);
				Toast.makeText(getApplicationContext(), temp.getString("title"), Toast.LENGTH_SHORT).show();
			}
						
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Drawable LoadImageFromWeb(String url)
	{
		try
		{
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "icon");
			return d;
		} 
		catch(Exception e )
		{
			return null;
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
