/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: sd
 * Author:   pc
 * Date:     2019/11/18 16:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.video;

import com.live.monitor.video.HCNetSdkUtils.DateUtils;
import sun.applet.Main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 〈功能：〉<br> 
 * 〈〉
 * @author liuhaoran
 * @create 2019/11/18 16:02
 * @since 1.0.0
 */
public class sd {

	//public static void main(String[] args) {
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date dateHistoryStartTime = null;
//		Date dateHistoryEndTime = null;
//		Date endTime = null;
//		try {
//			dateHistoryStartTime = formatter.parse("2019-12-08 08:00:00");
//			dateHistoryEndTime = formatter.parse("2019-12-08 21:00:00");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		endTime = DateUtils.lastTime(dateHistoryStartTime);
//		while (true) {
//			// 判断是否到达下载视频最后的时间点
//			if (endTime.getTime() != dateHistoryEndTime.getTime()) {
//				System.out.println("dateHistoryStartTime"+dateHistoryStartTime);
//				System.out.println("endTime"+endTime);
//				SimpleDateFormat fmt = new SimpleDateFormat("HH");
//				String time=fmt.format(endTime);
//				// 判断是不是夜间21点 如果是时间更先到第二天8点
//				if (time.equals("21") ) {
//					dateHistoryStartTime = DateUtils.lastTimeAddDay(dateHistoryStartTime);
//					endTime = DateUtils.lastTime(dateHistoryStartTime);
//				} else {
//					dateHistoryStartTime = endTime;
//					endTime = DateUtils.lastTime(dateHistoryStartTime);
//				}
//			} else {
//				System.out.println("endTime"+endTime);
//				break;
//			}
	//	}
//		Date bginTime = null;
//		String videoName = String.valueOf("1728_36_144_2019-11-25_16-00-00.mp4");
//		String[] channelNum = videoName.split("\\_");
//		String ymd = channelNum[3];
//		String hms = channelNum[4];
//		String[] hmsNum = hms.split("\\.");
//		String[] newhms = hmsNum[0].split("\\-");
//		String bginTimeString = ymd+" "+newhms[0]+':'+newhms[1]+':'+newhms[2];
//		String dateStr = bginTimeString.replace("Z", " UTC");//是空格+UTC
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		try {
//			bginTime = df.parse(dateStr);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		System.out.println(bginTime);
//	}
//	public static void main(String[] args) {
//
//		System.out.println(new Date());
//	}

}