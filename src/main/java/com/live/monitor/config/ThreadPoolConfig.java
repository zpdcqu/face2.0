package com.live.monitor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 〈功能：〉
 * 〈线程池配置类〉
 *
 * @author liuhaoran
 * @create 2019/10/20 15:04
 * @since 1.0.0
 */
@Configuration
public class ThreadPoolConfig {
	private static final Logger logger = LoggerFactory.getLogger(ThreadPoolConfig.class);
	@Bean
	public ExecutorService threadPool() {
		ExecutorService executor = Executors.newCachedThreadPool();
		return executor;
	}
}
