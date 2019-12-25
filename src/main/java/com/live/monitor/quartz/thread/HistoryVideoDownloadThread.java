/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ListLibraryThread
 * Author:   pc
 * Date:     2019/11/26 16:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.quartz.thread;

import com.live.monitor.service.AsyncChanelNumService;
import com.live.monitor.service.HistoryTaskService;
import com.live.monitor.service.ListLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

/**
 * 〈功能：〉<br>
 * 〈〉
 *
 * @author liuhaoran
 * @create 2019/11/26 16:34
 * @since 1.0.0
 */
@Service
public class HistoryVideoDownloadThread implements Runnable {
	@Autowired
	HistoryTaskService historyTaskService;
	@Autowired
	AsyncChanelNumService asyncChanelNumService;


	public HistoryVideoDownloadThread(HistoryTaskService historyTaskService , AsyncChanelNumService asyncChanelNumService) {
		this.historyTaskService = historyTaskService;
		this.asyncChanelNumService = asyncChanelNumService;
	}

	@Override
	public void run() {
			//historyTaskService.downloadVideoTask();

	}
}