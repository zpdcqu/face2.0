/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: GetPhotoThread
 * Author:   pc
 * Date:     2019/11/22 14:23
 * Description: 获取图片的线程
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.quartz.thread;

import com.live.monitor.service.FfmpegService;
import com.live.monitor.service.GetPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

/**
 * 〈功能：〉<br> 
 * 〈获取图片的线程〉
 * @author liuhaoran
 * @create 2019/11/22 14:23
 * @since 1.0.0
 */
public class GetPhotoThread implements Runnable {
	@Autowired
	private GetPhotoService getPhotoService;

	public GetPhotoThread(GetPhotoService getPhotoService)
	{
		this.getPhotoService = getPhotoService;
	}
	@Override
	@Scheduled(cron = "0 0 9,19 * * ?") // 每天9点刷新
	public void run() {
			try {
				Thread.sleep(1000*60);// 第三个数字为分钟
				getPhotoService.picListObjectsPic();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}