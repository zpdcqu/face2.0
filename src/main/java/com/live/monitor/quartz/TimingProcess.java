/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TimingProcess
 * Author:   pc
 * Date:     2019/10/31 13:45
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.quartz;

import com.live.monitor.quartz.thread.ProcessMainThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 〈功能：〉<br> 
 * 〈〉
 * @author liuhaoran
 * @create 2019/10/31 13:45
 * @since 1.0.0
 */
@Component // 被spring容器管理
@Order(1) // 如果多个自定义ApplicationRunner，用来标明执行顺序
public class TimingProcess implements ApplicationRunner {
	@Autowired
	ProcessMainThread mainThread;

	@Override
	public void run(ApplicationArguments args) {
		mainThread.start();
	}
}