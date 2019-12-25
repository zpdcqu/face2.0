/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: HandleVideoService
 * Author:   pc
 * Date:     2019/12/10 14:37
 * Description: 处理历史数据调用创新奇智接口类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.service;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * 〈功能：〉<br> 
 * 〈处理历史数据调用创新奇智接口类〉
 * @author liuhaoran
 * @create 2019/12/10 14:37
 * @since 1.0.0
 */
public interface HandleVideoService {
	/**
	 * 获取视频Josn对象用于调取创新奇智的接口
	 * @param BginTime
	 * @return
	 */
	public JSONObject getVideoHistoryJson(String BginTime, String taskId, String deviceId, String resourceUri, String useToCounter, String info) throws IOException;

	/**
	 * 处理视频
	 * @param param
	 */
	public String handleVideo(JSONObject param) throws IOException, InterruptedException;

	/**
	 * 获取视频属性值
	 * @param videoName
	 * @throws IOException
	 */
	public JSONObject getHistoryVideVlues(String videoName) throws IOException;

}