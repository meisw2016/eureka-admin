package com.meisw.systemChek.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.StringUtils;

/**
 * 
 * <p></p>
 * @author meisw 2019年9月19日 上午11:02:38
 * @ClassName HttpClientUtils
 * @Description HttpClient工具类
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2019年9月19日
 * @modify by reason:{方法名}:{原因}
 */
public class HttpClientUtils {
	
	private static final Logger log = LoggerFactory.getLogger(HttpClientUtils.class);
	
	private HttpClientUtils() {}
	
	/**
	 * 
	 * @author meisw 2019年9月19日 上午11:04:06
	 * @Method: sendPostJsonParam 
	 * @Description: 发送POST请求
	 * @param url 请求地址
	 * @param headers 请求头信息
	 * @param params	请求参数，拼接在URL后边
	 * @param body	请求参数，在请求体中
	 * @return 
	 * @throws
	 */
	public static String sendPostJsonParam(String url,Map<String,String> headers,Map<String,String> params,String body) {
		String result = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			/********************** params参数信息 **************************/
			if(params!=null) {
				List<NameValuePair> pList = new ArrayList<NameValuePair>();
				Iterator<Entry<String,String>> par = params.entrySet().iterator();
				while(par.hasNext()) {
					Entry<String,String> elem = par.next();
					pList.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
				}
				uriBuilder.setParameters(pList);
			}
			HttpPost httpPost = new HttpPost(uriBuilder.build());
			/********************** 请求头信息 ******************************/
			Iterator<Entry<String,String>> hList = headers.entrySet().iterator();
			while(hList.hasNext()){
				Entry<String,String> elem = hList.next();
				httpPost.addHeader(elem.getKey(), elem.getValue());
			}
			/********************** 请求体信息 ******************************/
			if(!StringUtils.isEmpty(body)) {
				StringEntity entity = new StringEntity(body,Consts.UTF_8);
				httpPost.setEntity(entity);
			}
			httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpPost);
			int code = response.getStatusLine().getStatusCode();
			log.info("HTTP请求返回状态码：{}",code);
			if(response!=null && response.getStatusLine().getStatusCode()==200) {
				HttpEntity he = response.getEntity();
				result = EntityUtils.toString(he,"UTF-8");
			}else {
				log.error("HTTP请求出错!{},{}",response.getStatusLine().getReasonPhrase(),EntityUtils.toString(response.getEntity()));
				result = response.getStatusLine().getReasonPhrase()+","+EntityUtils.toString(response.getEntity());
			}
		}catch(URISyntaxException e) {
			log.error("HTTP请求异常：{}",e);
		}catch(ClientProtocolException e) {
			log.error("HTTP请求异常：{}",e);
		}catch(IOException e) {
			log.error("HTTP请求异常：{}",e);
		}finally {
			try {
				if(httpClient!=null) {
					httpClient.close();
				}
				if(response != null) {
					response.close();
				}
			}catch(IOException e) {
				log.error("关闭HTTP连接异常：{}",e);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @author meisw 2019年9月19日 下午1:42:59
	 * @Method: sendGetParams 
	 * @Description: GET请求
	 * @param url 请求地址
	 * @param headers 请求头信息
	 * @param params	请求参数
	 * @return 
	 * @throws
	 */
	public static String sendGetParams(String url,Map<String,String> headers,Map<String,String> params) {
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		HttpResponse response = null;
		String result = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			/******************* 参数信息 *************************/
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			if(params != null) {
				for(String key : params.keySet()) {
					list.add(new BasicNameValuePair(key,params.get(key)));
					uriBuilder.setParameters(list);
				}
			}
			
			/******************* 根据带参数的URI对象构建GET请求对象  ****************/
			httpGet = new HttpGet(uriBuilder.build());
			Iterator<Entry<String,String>> iteratorHeader = headers.entrySet().iterator();
			while(iteratorHeader.hasNext()) {
				Entry<String,String> h = iteratorHeader.next();
				httpGet.setHeader(h.getKey(), h.getValue());
			}
			httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			result = EntityUtils.toString(httpEntity,"UTF-8");
		}catch(URISyntaxException e) {
			log.error("HTTP请求异常：{}",e);
		}catch(ClientProtocolException e) {
			log.error("HTTP请求异常：{}",e);
		}catch(IOException e) {
			log.error("HTTP请求异常：{}",e);
		}
		return result;
	}
}
