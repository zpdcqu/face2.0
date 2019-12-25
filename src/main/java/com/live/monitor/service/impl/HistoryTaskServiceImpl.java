/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: HistoryTaskServiceImpl
 * Author:   pc
 * Date:     2019/12/4 11:54
 * Description: 处理历史源任务
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.live.monitor.service.AsyncChanelNumService;
import com.live.monitor.service.ErgodicHistoryVideoService;
import com.live.monitor.service.HandleVideoService;
import com.live.monitor.service.HistoryTaskService;
import com.live.monitor.video.HCNetSdkUtils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * 〈功能：〉<br>
 * 〈处理历史源任务〉
 *
 * @author liuhaoran
 * @create 2019/12/4 11:54
 * @since 1.0.0
 */
@Service
@PropertySource(value = {"file:./properties/init.properties"})
public class HistoryTaskServiceImpl implements HistoryTaskService {
	@Value("${historyStartTime}")
	private String historyStartTime;
	@Value("${historyEndTime}")
	private String historyEndTime;
	@Value("${chanelNum}")
	private String chanelNum;
	@Value("${buildingProjectId}")
	private String buildingProjectId;
	@Autowired
	AsyncChanelNumService asyncChanelNumService;
	@Autowired
	ErgodicHistoryVideoService ergodicHistoryVideoService;
	@Autowired
	HandleVideoService handleVideoService;

	Properties properties = new Properties();
	private String[] cameraId = new String[8];
	private Date dateHistoryStartTime;
	private Date endTime;
	private Date dateHistoryEndTime;

//
//	/**
//	 * xiazai
//	 * @throws InterruptedException
//	 * @throws ParseException
//	 * @throws IOException
//	 */
//	@Override
//	public void downloadVideoTask() throws InterruptedException, ParseException, IOException {
//
//		dateHistoryStartTime=DateUtils.parseStrToDate(historyStartTime,DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
//		dateHistoryEndTime = DateUtils.parseStrToDate(historyEndTime,DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
//
//		Long distance = dateHistoryEndTime.getTime()-dateHistoryStartTime.getTime();
//		//分割 60秒 1分钟
//		Long period = 1000 * 60L;
//
//		//循环多少次
//		Long times = distance/period;
//
//		for (int i = 0; i < times ; i++) {
//			//单次循环的开始时间和结束时间
//			Date start = DateUtils.addDate(dateHistoryStartTime,i);
//			Date end = DateUtils.addDate(dateHistoryStartTime,i+1);
//			//判断是否在9点到19点之间
//			String newStart = DateUtils.parseDateToStr(end, DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
//			if (isInThe9_19(start,end)){
//				List<String> videos = sendHistoryTask(Integer.parseInt(chanelNum), start, end);
//				for (String video:videos){
//					File file = new File(video);
//					String fileName = file.getName();
//					JSONObject param = handleVideoService.getHistoryVideVlues(fileName);
//					String result = handleVideoService.handleVideo(param);
//					System.out.println("param"+param+"   result:"+result);
//
//				}
//			}else{
//				System.out.println("is not in 9 and 19"+newStart);
//				boolean historyStartTime = writeProperties("historyStartTime", newStart);
//				continue;
//			}
//
//			boolean historyStartTime = writeProperties("historyStartTime", newStart);
//
//			System.out.println("已发送创新奇智处理视频，更新开始时间"+newStart+historyStartTime);
//			Thread.sleep(period);
//		}


	/**
	 * xia
	 *
	 * @throws InterruptedException
	 * @throws ParseException
	 * @throws IOException
	 */
	@Override
	public void downloadAndHandelVideoTask(String startTimeHistory, String endTimeHistory) throws InterruptedException, IOException {

		dateHistoryStartTime = DateUtils.parseStrToDate(startTimeHistory, DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
		dateHistoryEndTime = DateUtils.parseStrToDate(endTimeHistory, DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);

		Long distance = dateHistoryEndTime.getTime() - dateHistoryStartTime.getTime();
		//分割 60秒 1分钟
		Long period = 1000 * 60L;

		//循环多少次
		Long times = distance / period;

		for (int i = 0; i < times; i++) {
			//单次循环的开始时间和结束时间
			Date start = DateUtils.addDate(dateHistoryStartTime, i);
			Date end = DateUtils.addDate(dateHistoryStartTime, i + 1);
			//判断是否在9点到19点之间
			String newStart = DateUtils.parseDateToStr(end, DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
			if (isInThe9_19(start, end)) {
				List<String> videos = sendHistoryTask(Integer.parseInt(chanelNum), start, end);
				for (String video : videos) {
					File file = new File(video);
					String fileName = file.getName();
					JSONObject param = handleVideoService.getHistoryVideVlues(fileName);
					String result = handleVideoService.handleVideo(param);
					System.out.println("param" + param + "   result:" + result);
				}
			} else {
				System.out.println("is not in 9 and 19" + newStart);
				boolean historyStartTime = writeProperties("historyStartTime", newStart);
				continue;
			}
			boolean historyStartTime = writeProperties("historyStartTime", newStart);
			System.out.println("handelVideoUpdateDate" + newStart + historyStartTime);
			// 缓冲1分钟
			Thread.sleep(period);
		}

	}


	//		while (true) {
//			// 判断是否到达下载视频最后的时间点
//			if (endTime.getTime() <= dateHistoryEndTime.getTime()) {
//				SimpleDateFormat fmt = new SimpleDateFormat("HH");
//				String time=fmt.format(endTime);
//				// 判断是不是夜间19点 如果是时间更先到第二天8点
//				if (time.equals("19") ) {
//					sendHistoryTask(Integer.parseInt(chanelNum), dateHistoryStartTime, endTime);
//					dateHistoryStartTime = DateUtils.lastTimeAddDay(dateHistoryStartTime);
//					endTime = DateUtils.lastTime(dateHistoryStartTime);
//				} else {
//					sendHistoryTask(Integer.parseInt(chanelNum), dateHistoryStartTime, endTime);
//					dateHistoryStartTime = endTime;
//					endTime = DateUtils.lastTime(dateHistoryStartTime);
//				}
//			} else {
//				List<String> videos = sendHistoryTask(Integer.parseInt(chanelNum), dateHistoryStartTime, endTime);
//				break;
//			}
//		}
//	}
	private boolean isInThe9_19(Date start, Date end) {
		int startHour = DateUtils.getHour(start.getTime());
		int endHour = DateUtils.getHour(start.getTime());
		if (startHour >= 9 && endHour < 19) {
			return true;
		}
		return false;
	}

	private boolean writeProperties(String keyname, String keyvalue) {

		Properties props = new Properties();
		try {
			props.load(new FileInputStream("./properties/init.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.exit(-1);
		}

		try {
			// 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream("./properties/init.properties");
			props.setProperty(keyname, keyvalue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			props.store(fos, "Update '" + keyname + "' value");
		} catch (IOException e) {
			System.err.println("属性文件更新错误");
			return false;
		}
		return true;
	}


	/**
	 * 发送异步请求n个通道同时下载视频
	 *
	 * @param startDate
	 * @param endDate
	 * @throws InterruptedException
	 */

	public List<String> sendHistoryTask(Integer chanelNum, Date startDate, Date endDate) throws InterruptedException {
		File file = new File("./properties/camera.properties");
		boolean isPlayAddressFile = false;
		try {
			FileInputStream fis = new FileInputStream(file);
			properties.load(fis);
			// 遍历Properties集合
			Set<String> set = properties.stringPropertyNames();
			int i = 0;
			for (String key : set) {
				cameraId[i++] = key;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int chanelNumVlues = 33;
		List<String> videos = new ArrayList<>();
		for (int i = 0; i < chanelNum; i++) {
			// 缓冲压力
			Thread.sleep(1200);
			String s = asyncChanelNumService.chanelNum(startDate, endDate, chanelNumVlues++, cameraId[i]);
			if (s != null) {
				System.out.println(s);
				videos.add(s);
			} else {
				System.out.println("download failed");
			}
		}
		return videos;
	}
}