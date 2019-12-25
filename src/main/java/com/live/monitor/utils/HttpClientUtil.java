/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: HttpClientUtil
 * Author:   pc
 * Date:     2019/11/22 15:04
 * Description: API请求
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.util.StringList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈功能：〉<br>
 * 〈需要API请求的工具类〉
 *
 * @author liuhaoran
 * @create 2019/11/22 15:04
 * @since 1.0.0
 */
public class HttpClientUtil {

	/**
	 * 向目的URL发送post请求 保存直播监控信息的API
	 *
	 * @param params 发送的参数
	 * @return ResultVO
	 */
	public static String postRequestSaveLiveInfo(JSONObject params, String upAddress) throws IOException, InterruptedException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		String urlString = "http://" + upAddress + "admin/face/api/data/updateLiveUrlInfo";
		System.out.println(urlString);
		HttpPost post = new HttpPost(urlString);
		StringEntity postingString = new StringEntity(params.toString().replaceAll(" ", ""));
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		// 避免消息发送过快阻塞
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		HttpResponse response = null;
		// 如果因为服务器重启导致调用接口没调用到 等待几分钟后重新调用
		try {
			response = httpClient.execute(post);
		} catch (Exception e) {
			e.printStackTrace();
			Thread.sleep(1000 * 60 * 5);
			response = httpClient.execute(post);
		}
		String content = EntityUtils.toString(response.getEntity());
		return content;
	}

	/**
	 * 发送get请求调用服务API
	 *
	 * @param url
	 * @return
	 */
	public static String getHttpApi(String url) throws InterruptedException {
		JSONObject jsTemp = null;
		JSONObject jsonObject = null;
		RestTemplate client = new RestTemplate();
		// 新建Http头，add方法可以添加参数
		HttpHeaders headers = new HttpHeaders();
		// 以表单的方式提交
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		// 避免消息发送过快阻塞
		try {
			Thread.sleep(100);
			jsonObject = client.getForObject(url, JSONObject.class);
			jsTemp = (JSONObject) JSONObject.parseObject(String.valueOf(jsonObject));
		} catch (Exception e) {
			e.printStackTrace();
			Thread.sleep(1000 * 60 * 3);
			System.out.println("Server is not turned on"); //服务器未开启
		}
		return jsTemp.toJSONString();
	}

	/**
	 * 向目的URL发送post请求 针对创新奇智接口调用
	 *
	 * @param url    目的url
	 * @param params 发送的参数
	 * @return ResultVO
	 */
	public static String postRequest(String url, JSONObject params) throws IOException, InterruptedException {
		HttpResponse response = null;
		String content = null;
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
			StringEntity postingString = new StringEntity(params.toString().replaceAll(" ", ""));
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			// 避免消息发送过快阻塞
			Thread.sleep(100);
			Date begin = new Date();
			SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//System.out.println("data_Contentmsg:" + dataContent);
			String utcDate = sdf.format(begin);
			response = httpClient.execute(post);
			Date end = new Date();
			String endDate = sdf.format(end);
			content = EntityUtils.toString(response.getEntity());
			JSONObject contentObject;
			contentObject = JSON.parseObject(content);
			// 请求接口没有数据就重新在请求三次
			if (content == null || content.equals("")) {
				for (int num = 0; num < 3; num++) {
					response = httpClient.execute(post);
					content = EntityUtils.toString(response.getEntity());
					contentObject = JSON.parseObject(content);
					// 请求接口没有数据就重新在请求三次
					if (content != null || !content.equals("")) {
						num = 3;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Thread.sleep(1000 * 60 * 3);
			System.out.println("Server is not turned on"); //服务器未开启
		}
		System.out.println(url + ":   " + content);
		return content;
	}

	/**
	 * 通过表单的方式传视频文件
	 *
	 * @param videoFile
	 * @return
	 */
	public static HttpEntity getMultiDefaultFileEntity(String videoFile) {
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		File file = new File(videoFile);
		builder.addBinaryBody("file", file);
		return builder.build();
	}

	/**
	 * Http视频传输请求
	 *
	 * @param File
	 * @return
	 */
	public static void doLivenessAuthByFile(String File, String postUrl) throws InterruptedException {
		if ("".equals(File) || "".equals(File)) {
			System.out.println("nullVideoFile");
		}
		String resJsonStr = "";
		//跳过https证书验证
		SkipHttpsUtil skipHttpsUtil = new SkipHttpsUtil();
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = (CloseableHttpClient) skipHttpsUtil.wrapClient();
			HttpPost post = new HttpPost(postUrl);
			HttpEntity dataEntity = getMultiDefaultFileEntity(File);// File文件格式上传
			post.setEntity(dataEntity);
			response = httpclient.execute(post);
			resJsonStr = EntityUtils.toString(response.getEntity());
			System.out.println("resJsonStr:" + resJsonStr);
			JSONObject cardInfoJsonObj = JSONObject.parseObject(resJsonStr);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Server is not turned on"); //服务器未开启
			Thread.sleep(1000 * 60 * 3);
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}