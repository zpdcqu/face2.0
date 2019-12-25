/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: HandelVideoServiceImpl
 * Author:   pc
 * Date:     2019/12/10 14:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.live.monitor.service.HandleVideoService;
import com.live.monitor.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.Date;

/**
 * 〈功能：〉<br>
 * 〈〉
 *
 * @author liuhaoran
 * @create 2019/12/10 14:38
 * @since 1.0.0
 */
@Service
@PropertySource(value = {"file:./properties/init.properties"})
public class HandelVideoServiceImpl implements HandleVideoService {
	@Value("${qizhiLocalIp}")
	private String qizhiLocalIp;
	@Value("${historyStartTime}")
	private String historyStartTime;
	@Value("${historyEndTime}")
	private String historyEndTime;
	@Value("${historyVideoAddress}")
	private String historyVideoAddress;

	/**
	 * 获取视频Josn对象用于调取创新奇智的接口
	 *
	 * @param bginTime
	 * @param taskId
	 * @param deviceId
	 * @param resourceUri
	 * @param useToCounter
	 * @param info
	 * @return
	 */
	@Override
	public JSONObject getVideoHistoryJson(String bginTime, String taskId, String deviceId, String resourceUri, String useToCounter, String info) throws IOException {
		JSONObject param = new JSONObject();
		param.put("beginTime", bginTime);
		param.put("taskId", taskId);
		param.put("deviceId", deviceId);
		param.put("resourceUri", resourceUri);
		param.put("useToCounter", useToCounter);
		param.put("info", info);
		return param;
		//handleVideo(param);
	}

	/**
	 * 调用接口处理视频
	 *
	 * @param param
	 * @throws IOException
	 */
	@Override
	public String handleVideo(JSONObject param) throws IOException, InterruptedException {
		String content = null;
		try {
			content = HttpClientUtil.postRequest(qizhiLocalIp + "/api/Task/historyTask", param);
		} catch (Exception e) {
			e.printStackTrace();
			Thread.sleep(1000*60*3);
			System.out.println("Server is not turned on"); //服务器未开启
		}
		System.out.println(content);
		return content;

	}

	/**
	 * 获取视频属性值
	 *
	 * @param videoName
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Override
	public JSONObject getHistoryVideVlues(String videoName) throws IOException {
		System.out.println("---getHistoryVideVlues---");
		Date beginTime = null;
		String[] channelNum = videoName.split("\\_");
		String ymd = channelNum[3];
		String hms = channelNum[4];
		String[] hmsNum = hms.split("\\.");
		String[] newhms = hmsNum[0].split("\\-");
		String random = String.valueOf((int) (Math.random() * (999)));
		String bginTimeString = ymd + "T" + newhms[0] + ':' + newhms[1] + ':' + newhms[2] + "."+random+"Z";
		String resourceUri = historyVideoAddress + videoName;
		// 0 楼盘 1 通道号 2 摄像头ID
		String info = channelNum[2] + "_" + channelNum[1];
		String randomStr = String.valueOf(100 + (int) (Math.random() * (99999 - 100 + 1)));
		System.out.println(randomStr);
		String taskId = channelNum[0] + "_" + channelNum[1] + "_" + channelNum[2] + "_" + randomStr;
		System.out.println(taskId);
		JSONObject videoHistoryJson = getVideoHistoryJson(bginTimeString, taskId, channelNum[2], resourceUri, "1", info);
		return videoHistoryJson;

//		System.out.println("----beginTime" + beginTime);
//		System.out.println("info:" + info);
//		System.out.println("taskId:" + taskId);
//		System.out.println("resourceUri:" + resourceUri);
	}
}