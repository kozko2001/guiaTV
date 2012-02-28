package net.coscolla.android.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpHelper {

	public static String doGet(String url)
	{
		DefaultHttpClient  httpclient = new DefaultHttpClient();
		HttpGet httppost = new HttpGet(url);

//		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1); 
//		nameValuePairs.add(new BasicNameValuePair("nameOfParameter", "parameter"));
//		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		StringBuilder total;
		try{
		
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity ht = response.getEntity();
	
			BufferedHttpEntity buf = new BufferedHttpEntity(ht);
	
			InputStream is = buf.getContent();
			
			BufferedReader r = new BufferedReader(new InputStreamReader(is));

			total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
			     total.append(line);
			}
			return total.toString();
		}catch(Exception e )
		{
			int b = 2;
		}
		
		return "";
	}
}
