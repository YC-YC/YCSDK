/**
 * 
 */
package com.zhonghong.utils.widget;

import android.content.Context;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.zhonghong.ycsdk.R;

/**
 * @author YC
 * @time 2016-10-20 下午4:54:41 
 * TODO:
 */
public class ToastUtils {

	public static final int SCREEN_ID_NO = 0;
	/**显示在主屏*/
	public static final int SCREEN_ID_MAIN = 1;
	/**显示在辅屏*/
	public static final int SCREEN_ID_VICE = 2;
	
	private static final String TAG = "ToastUtils";

	/**
	 * 
	 * @param context
	 * @param text
	 * @param screenId :use as {@link #SCREEN_ID_MAIN } {@link #SCREEN_ID_VICE }
	 * @see #SCREEN_ID_MAIN
     * @see #SCREEN_ID_VICE
	 */
	public static void showCustomToast(final Context context,
			final String text, int screenId) {

		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		int screenWidth = wm.getDefaultDisplay().getWidth();
//		Log.i(TAG, "screen width = " + screenWidth);
		View v = inflate.inflate(R.layout.layout_toast, null);
		TextView tv = (TextView) v.findViewById(R.id.toast_text);
		tv.setText(text);
		Toast toast = new Toast(context);
		int xOffset;
		if (screenId == SCREEN_ID_MAIN) {
			xOffset = -screenWidth * 1 / 4;
		} else if (screenId == SCREEN_ID_VICE){
			xOffset = screenWidth * 1 / 4;
		}
		else{
			xOffset = 0;
		}
		toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, xOffset, 70);
		toast.setView(v);
		toast.show();
	}
	
	private static long lastToastTime = 0;
	/**
	 * 防止频繁显示(2m内第二次调用无作用)
	 * @param context
	 * @param text
	 * @param screenId
	 * @see #showCustomToast(Context, String, int)
	 */
	private static void showCustomToastLimit(final Context context,
			final String text, int screenId){
		long nowTime = SystemClock.elapsedRealtime();
		if (nowTime - lastToastTime > 2*1000){
			showCustomToast(context, text, screenId);
			lastToastTime = nowTime;
		}
	}
	
	public static void showCustomToastLimitMain(final Context context, int id){
		String text = context.getResources().getString(id);
		showCustomToastLimit(context, text, SCREEN_ID_MAIN);
	}
	
	public static void showCustomToastLimitVice(final Context context, int id){
		String text = context.getResources().getString(id);
		showCustomToastLimit(context, text, SCREEN_ID_VICE);
	}
	
	public static void showCustomToastLimit(final Context context, int id){
		String text = context.getResources().getString(id);
		showCustomToastLimit(context, text, SCREEN_ID_NO);
	}
}
