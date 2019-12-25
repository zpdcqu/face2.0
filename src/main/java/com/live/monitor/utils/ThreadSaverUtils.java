package com.live.monitor.utils;

import org.apache.log4j.Logger;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2017/9/5 16:54.
 */
public class ThreadSaverUtils<T> extends Observable {
	T t;
	private static Logger loger = Logger.getLogger("ThreadSaver");

	public void Set(T t) {
		this.t = t;
		Saver<T> S = new Saver<>(this);
	}

	public void SaveThread(ExecutorService exec) {
		setChanged();
		notifyObservers(exec);
	}


	private static class Saver<T> implements Observer {
		public Saver(Observable o) {
			o.addObserver(this);
		}

		@Override
		public void update(Observable o, Object arg) {
			if (arg instanceof ExecutorService) {
				loger.info("重启 " + o.getClass().getName() + " 线程！");
				ExecutorService executorService = (ExecutorService) arg;
				executorService.execute((Runnable) o);
				loger.info("重启 " + o.getClass().getName() + " 线程成功！");
			} else {
				loger.error("没有传入线程池，无法激活失败线程！");
			}
		}
	}
}
