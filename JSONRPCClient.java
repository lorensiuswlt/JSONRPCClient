package my.com.plusmiles.android.http.core;

import my.com.plusmiles.android.util.Cons;
import my.com.plusmiles.android.util.Debug;
import my.com.plusmiles.android.util.StringUtil;

import java.io.InputStream;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * JSON RPC Client.
 * 
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 *
 */
public class JSONRPCClient {
	private String mBaseUrl;
	private String mMethod;
	
	private HashMap<String, Integer> mIntParams;
	private HashMap<String, String> mStrParams;
	private HashMap<String, JSONObject> mJsonParams;
	
	public JSONRPCClient() {
		mBaseUrl 	= Cons.API_URL;
		
		mIntParams 	= new HashMap<String, Integer>();
		mStrParams	= new HashMap<String, String>();
		mJsonParams	= new HashMap<String, JSONObject>();
	}
	
	public void setBaseUrl(String url) {
		mBaseUrl = url;
	}
	
	public void setMethod(String method) {
		mMethod = method;
	}
	
	public void addParam(String name, String value) {
		mStrParams.put(name, value);
	}
	
	public void addParam(String name, int value) {
		mIntParams.put(name, value);
	}
	
	public void addParam(String name, JSONObject value) {
		mJsonParams.put(name, value);
	}
	
	public JSONObject connect() throws Exception {
		HttpClient httpClient 	= new DefaultHttpClient();
		HttpPost httpPost 		= new HttpPost(mBaseUrl); 		
		JSONObject jsonResult	= null;
		
		try {
			JSONObject params 	= buildParam();
			String entity		= params.toString();
			
			Debug.i("POST " + mBaseUrl);
			Debug.i("Data: " + entity);
			
			HttpParams httpParams 		= httpClient.getParams();
			
			HttpConnectionParams.setConnectionTimeout(httpParams, Cons.HTTP_CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, Cons.HTTP_SOCKET_TIMEOUT);
			
			httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
	        httpPost.setEntity(new StringEntity(entity));
	        
	        HttpResponse httpResponse 	= httpClient.execute(httpPost);
			
			HttpEntity httpEntity 		= httpResponse.getEntity();
			
			if (httpEntity == null) throw new Exception("");
			
			InputStream stream 			= httpEntity.getContent();
			
			if (stream != null) {
				String strResponse 		= StringUtil.streamToString(stream);
				
				Debug.i(strResponse);
				
				JSONObject jsonResponse = (JSONObject) new JSONTokener(strResponse).nextValue();
				
				if (!jsonResponse.isNull("result")) {
					jsonResult = jsonResponse.getJSONObject("result");
				}
				
				stream.close();
			}
		} catch (Exception e) {
			throw e;
		}
		
		return jsonResult;
	}
	
	private JSONObject buildParam() {
		JSONObject object = new JSONObject();
		JSONObject params = new JSONObject();
		
		try {
			if (mIntParams.size() > 0) {
				for (String key : mIntParams.keySet()) {
					params.put(key, mIntParams.get(key));
				}
			}
			
			if (mStrParams.size() > 0) {
				for (String key : mStrParams.keySet()) {
					params.put(key, mStrParams.get(key));
				}
			}
			
			if (mJsonParams.size() > 0) {
				for (String key : mJsonParams.keySet()) {
					params.put(key, mJsonParams.get(key));
				}
			}
			
			object.put("jsonrpc", 	"2.0");
			object.put("method", 	mMethod);
			object.put("params", 	params);
			object.put("id", 		1);
			
		} catch (Exception e) { }
		
		return object;
	}
}