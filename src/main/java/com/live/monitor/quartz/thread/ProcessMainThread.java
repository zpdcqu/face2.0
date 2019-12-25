package com.live.monitor.quartz.thread;

import com.live.monitor.service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * 处理图片主线程
 *
 * @author zpd
 */
@Component
public class ProcessMainThread extends Thread {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ExecutorService threadPool;
	@Autowired
	FfmpegService ffmpegService;
	@Autowired
	GetPhotoService getPhotoService;
	@Autowired
	ListLibraryService listLibraryService;
	@Autowired
	HistoryTaskService historyTaskService;
	@Autowired
	AsyncChanelNumService asyncChanelNumService;
	@Autowired
	ErgodicHistoryVideoService ergodicHistoryVideoService;
	@Autowired
	HandleVideoService handleVideoService;

	@Override
	public void run() {
		// 开启直播的线程
		//threadPool.execute(new CameraThread(ffmpegService));
		// 开启获取封面图片的线程
		//threadPool.execute(new GetPhotoThread(getPhotoService));
		// 开启获取人脸信息库的线程
		threadPool.execute(new ListLibraryThread(listLibraryService));
		// 开启视频历史回流处理下载的线程
		//threadPool.execute(new HistoryVideoDownloadThread(historyTaskService, asyncChanelNumService));
		// 开启调用创新奇智接口处理历史视频
		//threadPool.execute(new HandleVideoThread(handleVideoService, ergodicHistoryVideoService));


	}

}

