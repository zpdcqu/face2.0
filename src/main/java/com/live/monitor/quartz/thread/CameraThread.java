/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CameraThread
 * Author:   pc
 * Date:     2019/10/31 10:49
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.quartz.thread;

import com.live.monitor.service.FfmpegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 〈功能：〉<br>
 * 〈〉
 *
 * @author liuhaoran
 * @create 2019/10/31 10:49
 * @since 1.0.0
 */
@Component
public class CameraThread implements Runnable {
	@Autowired
	private FfmpegService ffmepegService;

	public CameraThread(FfmpegService ffmpegService) {
		this.ffmepegService = ffmpegService;
	}

	/**
	 * 运行推流程序 中断重启处理
	 */
	@Override
	public void run() {
		while (true) {
			if (ffmepegService.pushTencent()) {
				break;
			} else {
				ffmepegService.pushTencent();
			}
		}
	}
}