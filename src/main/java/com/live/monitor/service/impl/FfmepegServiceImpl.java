/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FfmepegServiceImpl
 * Author:   pc
 * Date:     2019/10/29 16:41
 * Description: ffmpeg脚本服务类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.live.monitor.service.CommandManager;
import com.live.monitor.service.FfmpegService;
import com.live.monitor.tencentapi.GetPushPullAddress;
import com.live.monitor.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 〈功能：〉<br>
 * 〈ffmpeg脚本服务类〉
 *
 * @author liuhaoran
 * @create 2019/10/29 16:41
 * @since 1.0.0
 */
@Service
@PropertySource(value = {"file:./properties/init.properties"})
public class FfmepegServiceImpl implements FfmpegService {

	@Value("${ffmpegurl}")
	private String ffmpegUrl;
	@Value("${buildingProjectId}")
	private String buildingProjectId;
	@Value("${vcodec}")
	private String vcodec;
	@Value("${upAddress}")
	private String upAddress;

	Properties properties = new Properties();
	Properties playProperties = new Properties();
	CommandManager manager = new CommandManagerImpl();

	/**
	 * 推流到腾讯云直播
	 *
	 * @return
	 */
	@Override
	public boolean pushTencent() {
		File file = new File("./properties/camera.properties");
		File playFile = new File("./properties/play_address.properties");
		boolean isPlayAddressFile = false;
		try {
			FileInputStream fis = new FileInputStream(file);
			properties.load(fis);
			// 读写播放地址
			FileOutputStream fos = new FileOutputStream(playFile, true);
			FileInputStream playFis = new FileInputStream(playFile);
			playProperties.load(playFis);
			// 遍历Properties集合
			Set<String> set = properties.stringPropertyNames();
			for (String key : set) {
				String value = properties.getProperty(key);
				// 不存在此摄像头地址就添加
				Map<String, String> map = GetPushPullAddress.printUrls(key, buildingProjectId); // 获取腾讯云的播放地址的推流地址
				//默认方式发布任务
				manager.start("camera" + key, ffmpegUrl + "ffmpeg  -re  -rtsp_transport tcp  -stream_loop -1  -i "
						+ value
						+ " " + vcodec + " "
						+ map.get("pushUrl"), true);
				Thread.sleep(200);
				// 向远程API发送信息 保存直播监控信息
				JSONObject jsondata = new JSONObject();
				jsondata.put("buildingProjectId", buildingProjectId);
				jsondata.put("cameraId", key);
				jsondata.put("liveAddress", map.get("playUrlFlv"));
				try {
					HttpClientUtil.postRequestSaveLiveInfo(jsondata , upAddress);
				}catch (Exception e) {
					e.printStackTrace();
					System.out.println("Server is not turned on"); //服务器未开启
				}

			}
			// 关闭文件输出流
			fos.close();
			fis.close();
			Thread.sleep(1000 * 60 * 15); // 20分钟
			restart();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 重启推流服务
	 */
	public void restart() {
		// 停止全部任务
		manager.stopAll();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pushTencent();
	}
	/**
	 *
	 */
	/**
	 * 停止推流推流服务
	 */
	public void stop() {
		// 停止全部任务
		manager.stopAll();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}