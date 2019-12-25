/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FfmpegService
 * Author:   pc
 * Date:     2019/10/29 16:36
 * Description: ffmpeg脚本
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.service;

/**
 * 〈功能：〉<br>
 * 〈ffmpeg脚本〉
 *
 * @author liuhaoran
 * @create 2019/10/29 16:36
 * @since 1.0.0
 */
public interface FfmpegService {
	/**
	 * 发送推流请求
	 *
	 * @return
	 */
	boolean pushTencent();
}