/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ErgodicHistoryVideoService
 * Author:   pc
 * Date:     2019/12/9 10:15
 * Description: 遍历历史视频
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.service;

import java.io.IOException;

/**
 * 〈功能：〉<br> 
 * 〈遍历历史视频〉
 * @author liuhaoran
 * @create 2019/12/9 10:15
 * @since 1.0.0
 */
public interface ErgodicHistoryVideoService {
	/**
	 * 请求创新奇智历史历史回流接口
	 */
	void requestQizhiHistory() throws IOException, InterruptedException;

	/**
	 * 遍历历史视频地址 存入内存中
	 */
	void ErgodicHistoryVideoAdress();


}