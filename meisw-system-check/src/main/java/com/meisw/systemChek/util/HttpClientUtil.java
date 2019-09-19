//package com.meisw.systemChek.util;
//
//import java.io.IOException;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class HttpClientUtil {
///**使用Java重载可以实现if else 的效果*/
//	private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
//	
//	private HttpClientUtil(){}
//	/**
//	 * 发生GET请求
//	 * @param url
//	 * @param params
//	 * @return
//	 */
//	public static String doGet(String url,Map<String,String> params){
//		//创建HttpClient对象
//
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		String result = "";
//		CloseableHttpResponse response = null;
//		try{
//			//创建uri
//			URIBuilder builder = new URIBuilder(url);
//			if(params != null){
//				for(String key:params.keySet()){
//					builder.addParameter(key, params.get(key));
//				}
//			}
//			URI uri = builder.build();
//			//床http GET请求
//			HttpGet httpGet = new HttpGet(uri);
//			//执行请求
//			response = httpclient.execute(httpGet);
//			if(response.getStatusLine().getStatusCode() == 200){
//				result = EntityUtils.toString(response.getEntity());
//			}
//		}catch(Exception e){
//			log.error("请求地址：{},请求参数：{},请求方式：{}服务异常：{}",url,params.toString(),"GET",e);
//		}finally{
//			try{
//				if(response != null){
//					response.close();
//				}
//				httpclient.close();
//			}catch(IOException e){
//				log.error("关闭httpclient流异常：{}",e);
//			}
//		}
//		return result;
//	}
//	
//	public static String doGet(String url){
//		return doGet(url,null);
//	}
//	
//	public static String doPost(String url,Map<String,String> params){
//		//创建HttpClient对象
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		CloseableHttpResponse response = null;
//		String result = null;
//		try{
//			//创建 Http Post请求
//			HttpPost httpPost = new HttpPost(url);
//			//创建参数列表
//			if(params != null){
//				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
//				for(String key : params.keySet()){
//					paramList.add(new BasicNameValuePair(key,params.get(key)));
//				}
//				//模拟表单
//				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
//				httpPost.setEntity(entity);
//			}
//			//执行http Post请求
//			response = httpClient.execute(httpPost);
//			result = EntityUtils.toString(response.getEntity(),"utf-8");
//		}catch(Exception e){
//			log.error("请求地址：{},请求参数：{},请求方式：{}服务异常：{}",url,params.toString(),"POST",e);
//		}finally{
//			try{
//				response.close();
//			}catch(IOException e){
//				log.error("关闭httpclient流异常：{}",e);
//			}
//		}
//		return result;
//	}
//	
//	public static String doPost(String url){
//		return doPost(url,null);
//	}
//	
//	public static String doPostJson(String url,String json){
//		//创建HttpClient对象
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		CloseableHttpResponse response = null;
//		String result = "";
//		try{
//			//创建 Http Post请求
//			HttpPost httpPost = new HttpPost(url);
//			//创建请求内容
//			StringEntity entity = new StringEntity(json,ContentType.APPLICATION_JSON);
//			httpPost.setEntity(entity);
//			//执行http请求
//			response = httpClient.execute(httpPost);
//			result = EntityUtils.toString(response.getEntity(),"utf-8");
//		}catch(Exception e){
//			log.error("请求地址：{},请求参数：{},请求方式：{}服务异常：{}",url,json,"POST",e);
//		}finally{
//			try{
//				response.close();
//			}catch(IOException e){
//				log.error("关闭httpclient流异常：{}",e);
//			}
//		}
//		return result;
//	}
//}
