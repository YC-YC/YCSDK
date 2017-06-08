/**
 * 
 */
package com.zhonghong.utils.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


/**
 * 隐藏状态栏与空调栏
 * @author YC
 * @time 2017-5-16 下午5:10:39
 * TODO:
 */
public class FullScreenActivity extends LogActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获取顶层窗口
		View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_IMMERSIVE;
		decorView.setSystemUiVisibility(uiOptions);
		// 设置不受状态栏，导航栏影响
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
				
	}
}
