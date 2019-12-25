/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: AsyncChanelNum
 * Author:   pc
 * Date:     2019/12/6 10:23
 * Description: 通过通道来异步请求下载视频
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.service;

import org.springframework.scheduling.annotation.Async;

import java.util.Date;

/**
 * 〈功能：〉<br>
 * 〈通过通道来异步请求下载视频〉
 *
 * @author liuhaoran
 * @create 2019/12/6 10:23
 * @since 1.0.0
 */
public interface AsyncChanelNumService {
	/**
	 * 通道33
	 */
	String chanelNum(Date startDate, Date endDate, Integer ChannelNum, String CameraId) throws InterruptedException;


}