/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ErgodicHistoryVideoServiceImpl
 * Author:   pc
 * Date:     2019/12/9 10:19
 * Description: 遍历历史视频服务类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.service.impl;

import com.live.monitor.service.ErgodicHistoryVideoService;
import com.live.monitor.service.HandleVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈功能：〉<br>
 * 〈遍历历史视频服务类〉
 *
 * @author liuhaoran
 * @create 2019/12/9 10:19
 * @since 1.0.0
 */
@Service
@PropertySource(value = {"file:./properties/init.properties"})
public class ErgodicHistoryVideoServiceImpl implements ErgodicHistoryVideoService {
	@Value("${historyVideoAddress}")
	private String historyVideoAddress;
	@Value("${chanelNum}")
	private String chanelNum;

	@Autowired
	HandleVideoService handleVideoService;
	Map<String, Object> map = new HashMap<>();

	/**
	 * 请求创新奇智历史历史回流接口
	 */
	@Override
	public void requestQizhiHistory() throws IOException, InterruptedException {
		System.out.println("---requestQizhiHistory---");
		ErgodicHistoryVideoAdress();
		Thread.sleep(1000*3);
		for (Object value : map.values()) {
			String videoNameString = (String) value;
			System.out.println("videoNameString:"+videoNameString);
			handleVideoService.getHistoryVideVlues(videoNameString);
			// 缓冲压力
			Thread.sleep(1000 * 60);
		}
	}

	/**
	 * 把所有文件遍历出来放入内存中
	 */
	@Override
	public void ErgodicHistoryVideoAdress() {
		System.out.println("---ErgodicHistoryVideoAdress---");
		File file = new File(historyVideoAddress); // 创建File对象
		//System.out.println("historyVideoAddress" + historyVideoAddress);
		int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0; //做循环标记map.put名字
		// 判断File对象对应的目录是否存在
		if (file.isDirectory()) {
			String[] names = file.list(); // 获得目录下的所有文件的文件名
			System.out.println(names);
			for (String name : names) {
				//System.out.println(name);
				String videoName = String.valueOf(name);
				String[] channelNum = videoName.split("\\_");
				map.put("channel_" + channelNum[1] + "_" + a++, name);
			}
		}
	}
}