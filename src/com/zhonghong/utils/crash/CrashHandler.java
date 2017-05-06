/**
 * 
 */
package com.zhonghong.utils.crash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.FileUtils;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;

import com.zhonghong.utils.AppUtil;
import com.zhonghong.utils.FileUtil;
import com.zhonghong.utils.TimeUtils;

/**
 * 程序异常退出信息获取
 * @author YC
 * @time 2017-5-5 上午11:25:49
 * TODO:需要在Application里调用init()
 */
public class CrashHandler implements UncaughtExceptionHandler{

	private static final String TAG = "Crash";

	private static CrashHandler mInstance;
	
	private Context mContext;
	private UncaughtExceptionHandler defaultHandler;
	private List<RecordInfo> mErrInfo = new ArrayList<RecordInfo>();
	
	private CrashHandler(){
		
	}
	
	public static CrashHandler getDefault(){
		if (mInstance == null){
			synchronized (CrashHandler.class) {
				if (mInstance == null){
					mInstance = new CrashHandler();
				}
			}
		}
		
		return mInstance;
	}
	
	/**
	 * 初始化，需要在Application里调用
	 * @param context
	 */
	public void init(Context context){
		mContext = context;
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handlerException(ex)){
			defaultHandler.uncaughtException(thread, ex);
		}
		else{
			SystemClock.sleep(3*1000);
			Process.killProcess(Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 异常处理
	 * @param ex
	 * @return 处理异常则返回true,否则返回false
	 */
	private boolean handlerException(Throwable ex) {
		if (ex == null){
			return false;
		}
		
		collectErrorInfo(ex);
		
		processErrorInfo();
		
		return false;
	}
	

	/**
	 * 收集异常信息
	 */
	private void collectErrorInfo(Throwable ex) {
		mErrInfo.clear();
		PackageInfo packageInfo = AppUtil.getPackageInfo(mContext);
		mErrInfo.add(new RecordInfo("versionName", packageInfo.versionName));
		mErrInfo.add(new RecordInfo("versionCode", "" + packageInfo.versionCode));
//		mErrInfo.add(new RecordInfo("crashTime", "" + TimeUtils.getCurTimeString()));
		mErrInfo.add(new RecordInfo("Process", "" + packageInfo.packageName));
		mErrInfo.add(new RecordInfo("PID", "" + Process.myPid()));
		
		/* Field[] fields = Build.class.getDeclaredFields();
	        for (Field field : fields) {
	            try {
	                field.setAccessible(true);
	                mErrInfo.add(new RecordInfo(field.getName(), field.get(null).toString()));
	                Log.d(TAG, field.getName() + " : " + field.get(null));
	            } catch (Exception e) {
	                Log.e(TAG, "an error occured when collect crash info", e);
	            }
	        }*/

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
       /* Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }*/
        printWriter.close();
        
        mErrInfo.add(new RecordInfo("", "" + writer.toString()));
	}

	/**
	 * 处理异常信息
	 */
	private void processErrorInfo() {
		StringBuffer sb = new StringBuffer();
		for (RecordInfo info : mErrInfo) {
			sb.append(info.toString());
		}
		 Log.i(TAG, "" + sb.toString());

		String logFile = null;
		String[] devices = new String[] { "/mnt/USB", "/mnt/USB1", "/mnt/USB2" };
		for (String device : devices) {
			if (FileUtil.isDeviceMounted2(mContext, device)) {
				logFile = device + File.separator + "crash" + File.separator
						+ TimeUtils.getCurTimeString() + ".log";
			}
		}
		if (logFile != null) {
			if (FileUtil.createOrExistsFile(logFile)) {
				try {
					FileOutputStream outputStream = new FileOutputStream(
							logFile);
					outputStream.write(sb.toString().getBytes());
					FileUtils.sync(outputStream);
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	/**
	 * 异常信息
	 * @author YC
	 * @time 2017-5-5 上午11:49:09
	 * TODO:
	 */
	private final class RecordInfo{
		private String name;
		private String content;
		
		/**
		 * @param name
		 * @param content
		 */
		public RecordInfo(String name, String content) {
			super();
			this.name = name;
			this.content = content;
		}

		@Override
		public String toString() {
			return name + " : " + content + "\n";
		}
	}
	
}
