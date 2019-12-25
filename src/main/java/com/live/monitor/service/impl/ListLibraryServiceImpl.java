/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ListLibraryServiceImpl
 * Author:   pc
 * Date:     2019/11/26 10:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.live.monitor.service.ListLibraryService;
import com.live.monitor.utils.HttpClientUtil;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 〈功能：〉<br>
 * 〈〉
 *
 * @author liuhaoran
 * @create 2019/11/26 10:44
 * @since 1.0.0
 */
@Service
@Component
@PropertySource(value = {"file:./properties/init.properties"})
public class ListLibraryServiceImpl implements ListLibraryService {
	@Value("${qizhiLocalIp}")
	private String qizhiLocalIp;
	private static List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();

	/**
	 * 启动程序获取人脸库信息查询
	 */
	@Override
	public void ListLibrary() throws InterruptedException {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Map<String, Object> map2 = new HashMap<>();
		Map<String, Object> map3 = new HashMap<>();
		Map<String, Object> map4 = new HashMap<>();
		String content;
		for (int iSum = 2; iSum <= 4; iSum++) {
			String requestUrl = qizhiLocalIp + "/api/CustomRepo/getRepoInfo?repoType=" + iSum;
			JSONArray repoes = null;
			try {
				content = HttpClientUtil.getHttpApi(requestUrl);
				JSONObject contentObject = JSON.parseObject(content);
				System.out.println("content:" + content);
				JSONObject param = (JSONObject) contentObject.get("data");
				repoes = param.getJSONArray("repoes");//获取数组
			} catch (Exception e) {
				e.printStackTrace();
			}
			/**
			 * 遍历人脸库信息存入对应的map中
			 */
			switch (iSum) {
				case 2:
					map2.put("repoes", repoes); // 后面的数字代表repoType
				case 3:
					map3.put("repoes", repoes);
				case 4:
					map4.put("repoes", repoes);
			}
		}
		// 放入 listmaps 方便遍历
		listMaps.add(map2);
		System.out.println("map2"+map2);
		listMaps.add(map3);
		System.out.println("map3"+map3);
		listMaps.add(map4);
		System.out.println("map4"+map4);
	}

	/**
	 * 创建一个新的JSONObject数据 接收是传过来的param JSON值
	 *
	 * @return
	 */
	public JSONObject getNewMsg(String repoType, JSONObject param) throws InterruptedException {
		String requestUrl = qizhiLocalIp + "/api/CustomRepo/getRepoInfo?repoType=" + repoType;
		JSONArray repoes = null;
		try {
			String content = HttpClientUtil.getHttpApi(requestUrl);
			JSONObject contentObject = JSON.parseObject(content);
			JSONObject paramString = (JSONObject) contentObject.get("data");
			repoes = paramString.getJSONArray("repoes");//获取数组
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date startDate = null, endDate = null, ctime = null;
		JSONArray repoIdsArray = new JSONArray();
		String repoIds = "0";
		String endDateTime = (String) param.get("endDateTime");
		String startDateTime = (String) param.get("startDateTime");
		JSONArray data = repoes; //获取数组
		for (int i = 0; i < data.size(); i++) { //提取出所有
			SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd");
			JSONObject dataContent = data.getJSONObject(i);
			String ctimeDate = String.valueOf(dataContent.get("ctime"));
			try {
				startDate = utcFormat.parse(startDateTime);
				endDate = utcFormat.parse(endDateTime);
				ctime = utcFormat.parse(ctimeDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if ((ctime.getTime() >= startDate.getTime()) && (ctime.getTime() <= endDate.getTime())) {
				repoIds = dataContent.get("id").toString();
				repoIdsArray.add(Integer.parseInt(repoIds));
			}
		}
		param.put("repoIds", repoIdsArray);
		return param;
	}
}