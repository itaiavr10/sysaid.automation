package com.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.core.base.LogManager;

public class HttpSender {

	private static HttpSender instance;

	public static HttpSender getInstance() {
		if (instance == null) {
			instance = new HttpSender();
		}
		return instance;
	}

	
	//TODO: check
	public String postJson(String postUrl, Object msgAsJson) throws Exception {
		return postJson(postUrl, msgAsJson, new HashMap<String, String>());
	}

	//TODO: check
	public String postJson(String postUrl, Object msgAsJson, Map<String, String> additionalHeaders) throws Exception {
		String jsonAsString = msgAsJson.toString();
		LogManager.debug("Post json: " + jsonAsString);
		HttpPost httpPost = new HttpPost(postUrl);
		StringEntity entity = new StringEntity(jsonAsString);
		httpPost.setEntity(entity);
		for (String header : additionalHeaders.keySet()) {
			httpPost.setHeader(header, additionalHeaders.get(header));
		}
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		return post(httpPost);
	}

	//return response content
	private String post(final HttpPost httpPost) throws Exception {

		CloseableHttpClient client = null;
		StringBuffer response = null;

		BufferedReader reader = null;
		try {
			client = HttpClients.createDefault();
			LogManager.debug("Execute http post..");
			CloseableHttpResponse httpResponse = client.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == 200)
				LogManager.debug("posted successfully..");
			else
				throw new Exception("Post process failed , code status: " + statusCode);
			reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			String inputLine;
			response = new StringBuffer();
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			LogManager.debug("Post response: " + response.toString());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
			try {
				client.close();
			} catch (IOException e) {
			}
		}
		return response.toString();

	}

}
