package ch.kusar.contraceptivetimer;

import android.app.Application;
import android.content.Context;

public class MainApplicationContext extends Application {

	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		MainApplicationContext.context = this.getApplicationContext();
	}

	public static Context getAppContext() {
		return MainApplicationContext.context;
	}
}