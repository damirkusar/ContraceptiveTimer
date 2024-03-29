package ch.kusar.contraceptivetimer.wrapper;

import android.util.Log;

public class LoggerWrapper {
	private static final String TAG = "ch.kusar.contraceptiveTimer";
	
	public static void LogError(String msg){
		Log.e(LoggerWrapper.TAG, msg);
	}
	
	public static void LogWarn(String msg){
		Log.w(LoggerWrapper.TAG, msg);
	}
	
	public static void LogInfo(String msg){
		Log.i(LoggerWrapper.TAG, msg);
	}
	
	public static void LogDebug(String msg){
		Log.d(LoggerWrapper.TAG, msg);
	}
	
	public static void LogVerbose(String msg){
		Log.v(LoggerWrapper.TAG, msg);
	}
}
