package com.live.monitor.ffmpeg.handler;

import java.util.logging.Logger;

/**
 * 默认任务消息输出处理
 * @author eguid
 * @since jdk1.7
 * @version 2017年10月13日
 */
public class DefaultOutHandlerMethod implements OutHandlerMethod{

	/**
	 * 任务是否异常中断，如果
	 */
	public boolean isBroken=false;
	private static Logger logger = Logger.getLogger("QueueConsumer");
	@Override
	public void parse(String id,String msg) {
		//过滤消息
		if (msg.indexOf("fail") != -1) {
			System.err.println(id + "任务可能发生故障：" + msg);
			System.err.println("失败，设置中断状态");
			isBroken=true;
		}else if(msg.indexOf("miss")!= -1) {
			System.err.println(id + "任务可能发生丢包：" + msg);
			System.err.println("失败，设置中断状态");
			isBroken=true;
		}else {
			isBroken=false;
			logger.info(id + "msg：" + msg);
		}

	}

	@Override
	public boolean isbroken() {
		return isBroken;
	}
	
}
