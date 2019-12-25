package com.live.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.ServletContextListener;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class MonitorApplication implements ServletContextListener {
	public static void main(String[] args) {
		SpringApplication.run(MonitorApplication.class, args);
	}
}
