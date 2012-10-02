package ch.kusar.contraceptivetimer.wrapper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStartAlarmWrapper extends BroadcastReceiver {
	AlarmWrapper alarmWrapper = new AlarmWrapper();

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			this.alarmWrapper.SetAlarm(context);
		}
	}
}
