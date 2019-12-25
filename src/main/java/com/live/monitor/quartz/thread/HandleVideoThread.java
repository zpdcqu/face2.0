/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: HandleVideoThread
 * Author:   pc
 * Date:     2019/12/7 15:38
 * Description: 处理历史视频的线程
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.quartz.thread;

import com.live.monitor.service.ErgodicHistoryVideoService;
import com.live.monitor.service.GetPhotoService;
import com.live.monitor.service.HandleVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

/**
 * 〈功能：〉<br> 
 * 〈处理历史视频的线程〉
 * @author liuhaoran
 * @create 2019/12/7 15:38
 * @since 1.0.0
 */
public class HandleVideoThread implements Runnable{
	@Autowired
	private ErgodicHistoryVideoService ergodicHistoryVideoService;
	@Autowired
	private HandleVideoService handleVideoService;

	public HandleVideoThread(HandleVideoService handleVideoService, ErgodicHistoryVideoService ergodicHistoryVideoService)
	{
		this.ergodicHistoryVideoService = ergodicHistoryVideoService;
		this.handleVideoService = handleVideoService;
	}

	@Override
	public void run() {
		//Thread.sleep(1000 * 60 * 60 * 20);
		//ergodicHistoryVideoService.requestQizhiHistory();
	}


}