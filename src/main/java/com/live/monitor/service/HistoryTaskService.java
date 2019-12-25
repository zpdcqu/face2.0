/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: HistoryTaskService
 * Author:   pc
 * Date:     2019/12/7 10:21
 * Description: 历史回流视频服务类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.service;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.text.ParseException;

/**
 * 〈功能：〉<br>
 * 〈历史回流视频服务类〉
 * @author liuhaoran
 * @create 2019/12/7 10:21
 * @since 1.0.0
 */
public interface HistoryTaskService {
	/**
	 * 开启程序自动处理历史视频
	 * @param startTimeHistory
	 * @param endTimeHistory
	 * @throws InterruptedException
	 * @throws IOException
	 */
	//void downloadVideoTask() throws InterruptedException, ParseException, IOException;

	/**
	 * 接口调用时间段处理数据
	 * @param startTimeHistory
	 * @param endTimeHistory
	 * @throws InterruptedException
	 * @throws IOException
	 */
	void downloadAndHandelVideoTask(String startTimeHistory, String endTimeHistory) throws InterruptedException, IOException;
}