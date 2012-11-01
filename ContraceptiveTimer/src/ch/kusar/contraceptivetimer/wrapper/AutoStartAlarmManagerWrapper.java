package ch.kusar.contraceptivetimer.wrapper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStartAlarmManagerWrapper extends BroadcastReceiver {
	AlarmManagerWrapper alarmWrapper = new AlarmManagerWrapper();

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			LoggerWrapper.LogInfo("ContraceptiveTimer is now in onReceive method while device booted.");
			this.alarmWrapper.SetupAlarm();
		}
	}
}
