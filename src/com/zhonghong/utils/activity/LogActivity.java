/**
 * 
 */
package com.zhonghong.utils.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * 常用方法的Activity方法集合
 * @author YC
 * @time 2017-4-12 上午11:13:11
 * TODO:
 * 1、生命周期添加Log
 * 2、添加获取当前界面是否可见的方法
 */
public class LogActivity extends Activity {
	
	private static final boolean DEBUG = true;

	protected final String TAG = getClass().getSimpleName();
	
	private boolean isVisible = false;
	
	/**
	 * 当前Activity是否可见
	 * @return
	 */
	protected boolean isActivityVisible(){
		return isVisible;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log("onCreate");
		super.onCreate(savedInstanceState);
		isVisible = false;
	}

	@Override
	protected void onStart() {
		Log("onStart");
		super.onStart();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		Log("onNewIntent");
		super.onNewIntent(intent);
		isVisible = false;
	}

	@Override
	protected void onRestart() {
		Log("onRestart");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
		isVisible = true;
	}

	@Override
	protected void onPause() {
		Log("onPause");
		super.onPause();
		isVisible = false;
	}

	@Override
	protected void onStop() {
		Log("onStop");
		super.onStop();
		isVisible = false;
	}

	@Override
	protected void onDestroy() {
		Log("onDestroy");
		super.onDestroy();
		isVisible = false;
	}

	@Override
	public void onLowMemory() {
		Log("onLowMemory");
		super.onLowMemory();
	}

	@Override
	public void onAttachedToWindow() {
		Log("onAttachedToWindow");
		super.onAttachedToWindow();
	}

	private void Log(String msg){
		if (DEBUG){
			Log.i(TAG, msg);
		}
	}
	
}
