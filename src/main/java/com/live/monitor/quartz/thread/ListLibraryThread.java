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

import com.live.monitor.service.ListLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 〈功能：〉<br>
 * 〈〉
 *
 * @author liuhaoran
 * @create 2019/11/26 16:34
 * @since 1.0.0
 */
@Service
public class ListLibraryThread implements Runnable {
	@Autowired
	private ListLibraryService listLibraryService;

	public ListLibraryThread(ListLibraryService listLibraryService) {
		this.listLibraryService = listLibraryService;
	}

	@Override
	@Scheduled(cron = "0 0/30 * * * ? ") // 30分钟更新一次
	public void run() {
		try {
			listLibraryService.ListLibrary();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}