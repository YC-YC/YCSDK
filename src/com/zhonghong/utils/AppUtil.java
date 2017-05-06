/**
 * 
 */
package com.zhonghong.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 应用信息获取
 * @author YC
 * @time 2017-4-8 上午9:59:39
 * TODO:
 */
public class AppUtil {

	/**
	 * 获取版本名称
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context){
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			return pInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取应用信息
	 * @param context
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context context){
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			return pInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过包名启动应用
	 * @param context
	 * @param packgeName
	 * @return
	 */
	public static boolean startAppByPackgeName(Context context, String packgeName) {
			PackageManager packageManager = context.getPackageManager();
			if (packageManager != null && packgeName != null) {
				Intent intent = packageManager
						.getLaunchIntentForPackage(packgeName);
				if (intent != null) {
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
					return true;
				}
			}
		return false;
	}
}
