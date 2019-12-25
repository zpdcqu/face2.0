/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: AsyncConfiguration
 * Author:   pc
 * Date:     2019/12/11 18:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.live.monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 〈功能：〉<br> 
 * 〈〉
 * @author liuhaoran
 * @create 2019/12/11 18:58
 * @since 1.0.0
 */
@Configuration
@EnableAsync  // 启用异步任务
public class AsyncConfiguration {
	// 声明一个线程池(并指定线程池的名字)
	@Bean("downloadVideo")
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		//核心线程数5：线程池创建时候初始化的线程数
		executor.setCorePoolSize(2000000);
		//最大线程数5：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
		executor.setMaxPoolSize(2000000);
		//缓冲队列500：用来缓冲执行任务的队列
		executor.setQueueCapacity(500000000);
		//允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
		executor.setKeepAliveSeconds(120);
		//线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
		//executor.setThreadNamePrefix("DailyAsync-");
		executor.initialize();
		return executor;
	}

}