/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: AsyncChanelNumServiceImpl
 * Author:   pc
 * Date:     2019/12/6 10:26
 * Description: 通道异步处理服务类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.service.impl;

import com.live.monitor.config.Constants;
import com.live.monitor.service.AsyncChanelNumService;
import com.live.monitor.video.HCNetSdkUtils.HCNetSdkUtils;
import com.live.monitor.video.hikvision.HCNetSDK;
import com.sun.jna.NativeLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

/**
 * 〈功能：〉<br>
 * 〈通道异步处理服务类〉
 *
 * @author liuhaoran
 * @create 2019/12/6 10:26
 * @since 1.0.0
 */
@Service
@PropertySource(value = {"file:./properties/init.properties"})
public class AsyncChanelNumServiceImpl implements AsyncChanelNumService {
	@Value("${buildingProjectId}")
	private String buildingProjectId;
	// sdk初始化实例
	public static HCNetSDK sdk;
	private static NativeLong userId = new NativeLong(-1);
	private static Logger logger = LoggerFactory.getLogger(HCNetSdkUtils.class);
	@Autowired
	private Constants constants;
	public static HCNetSdkUtils hCNetSdkUtils;
	Properties properties = new Properties();
	private String[] cameraId = new String[8];


	@Override
	public String chanelNum(Date startDate, Date endDate, Integer ChannelNum, String CameraId ) throws InterruptedException {
		String file = HCNetSdkUtils.hCNetSdkUtils.getVideoTimeHistory(buildingProjectId, ChannelNum, startDate, endDate, CameraId);
		return file;
	}


	/**
	 * 转换设备时间
	 *
	 * @param timeMills 时间戳 毫秒级
	 * @return
	 */
	private HCNetSDK.NET_DVR_TIME toNetDvrTime(long timeMills) {
		HCNetSDK.NET_DVR_TIME net_dvr_time = new HCNetSDK.NET_DVR_TIME();
		net_dvr_time.dwYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(timeMills));
		net_dvr_time.dwMonth = Integer.parseInt(new SimpleDateFormat("MM").format(timeMills));
		net_dvr_time.dwDay = Integer.parseInt(new SimpleDateFormat("dd").format(timeMills));
		net_dvr_time.dwHour = Integer.parseInt(new SimpleDateFormat("HH").format(timeMills));
		net_dvr_time.dwMinute = Integer.parseInt(new SimpleDateFormat("mm").format(timeMills));
		net_dvr_time.dwSecond = Integer.parseInt(new SimpleDateFormat("ss").format(timeMills));
		return net_dvr_time;
	}


}