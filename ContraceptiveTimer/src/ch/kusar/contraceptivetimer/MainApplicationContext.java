package ch.kusar.contraceptivetimer;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

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

	public void startNewActivity(Class<?> cls) {
		Intent intent = new Intent(this, cls);
		this.startActivity(intent);
	}
}
