package ch.kusar.contraceptivetimer.wrapper;

import android.app.Application;
import ch.kusar.contraceptivetimer.MainApplicationContext;

public class ResourceWrapper extends Application {

	public static String getStringFromResource(int resId) {
		return MainApplicationContext.getAppContext().getString(resId);
	}

}
