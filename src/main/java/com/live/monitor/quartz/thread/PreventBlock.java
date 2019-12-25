/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: PreventBlock
 * Author:   pc
 * Date:     2019/11/25 17:46
 * Description: 防止阻塞线程
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.quartz.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 〈功能：〉<br>
 * 〈防止阻塞线程〉
 *
 * @author liuhaoran
 * @create 2019/11/25 17:46
 * @since 1.0.0
 */
public class PreventBlock {
	/**
	 * 处理process输出流和错误流，防止进程阻塞
	 * 在process.waitFor();前调用
	 *
	 * @param process
	 */
	private static void dealStream(Process process) {
		Logger logger = LoggerFactory.getLogger(PreventBlock.class);
		if (process == null) {
			return;
		}
		// 处理InputStream的线程
		new Thread() {
			@Override
			public void run() {
				BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				try {
					while ((line = in.readLine()) != null) {
						logger.info("output: " + line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		// 处理ErrorStream的线程
		new Thread() {
			@Override
			public void run() {
				BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				String line = null;
				try {
					while ((line = err.readLine()) != null) {
						logger.info("err: " + line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						err.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}