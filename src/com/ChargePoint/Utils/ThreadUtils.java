package com.ChargePoint.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {

//	创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
	private static ExecutorService cachedThreadPool;
	
	/**创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
	 * @param runnable
	 */
	public static void createCachedThread(Runnable runnable){
		cachedThreadPool = Executors.newCachedThreadPool();
//		实现runnnable接口并执行线程
		cachedThreadPool.execute(runnable);
	}//循环结束，线程池为无限大，
	//当执行第二个任务时第一个任务已经完成，
	//会复用执行第一个任务的线程，而不用每次新建线程。
}
