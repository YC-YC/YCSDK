package com.zhonghong.utils;

import java.util.HashMap;
import java.util.Map;

import android.os.SystemClock;
import android.util.Log;

/**
 * 打印信息封装
 * @author YC
 * @time 2017-4-7 上午11:32:32
 * TODO:
 */
public class LogUtil {
	private static final String TAG = "L";
	static boolean d = true;
	static boolean e = true; 
	static boolean i = true;
	static boolean w = true;

	static {
		d = true;
		w = true;
	}

	public static void d(String tag, String str) {
		if (d)
			Log.d(tag, str);
	}

	public static void e(String tag, String str) {
		if (e)
			Log.e(tag, str);
	}

 
	public static void i(String tag, String str) {
		if (i)
			Log.i(tag, str);
	}
	
	public static void LogMethod(String tag, String str) {
		String METHOD = null;
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts != null) {
			for (StackTraceElement st : sts) {
				if (st.isNativeMethod()) {
					continue;
				}
				if (st.getClassName().equals(Thread.class.getName())) {
					continue;
				}
				if (st.getClassName().equals(LogUtil.class.getName())) {
					continue;
				}
				METHOD = st.getMethodName() + ":" + st.getLineNumber();
				break;
			}
		}
		Log.i(tag, METHOD + " " + str);
	}

	public static void w(String tag, String str) {
		if (w)
			Log.w(tag, str);
	}
	
private static Map<String, Long> timeRecord = new HashMap<String, Long>();	
	
	/**
	 * 打印开始时间
	 * @param tip
	 */
	public static void startTime(String tip){
		timeRecord.put(tip, SystemClock.elapsedRealtime());
	}
	
	/**
	 * 结束时间
	 * @param tip
	 */
	public static void endUseTime(String tip){
		if (timeRecord != null && !timeRecord.containsKey(tip)){
			Log.i(TAG, "未配置开始时间");
			return;
		}
		Long startTime = timeRecord.get(tip);
		if (startTime != null){
			long diff = SystemClock.elapsedRealtime() - startTime;
			timeRecord.remove(tip);
			Log.i(TAG, tip + "耗时：" + diff);
		}
	}
}
