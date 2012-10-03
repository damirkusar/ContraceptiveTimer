package ch.kusar.contraceptivetimer.wrapper;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import ch.kusar.contraceptivetimer.MainApplicationContext;
import ch.kusar.contraceptivetimer.R;
import ch.kusar.contraceptivetimer.businessobjects.AlarmEventData;
import ch.kusar.contraceptivetimer.businessobjects.EventType;
import ch.kusar.contraceptivetimer.retriever.AlarmEventRetriever;

public class AlarmManagerWrapper extends BroadcastReceiver {

	private static final int HELLO_ID = 1;

	@Override
	public void onReceive(Context context, Intent intent) {
		LoggerWrapper.LogInfo("ContraceptiveTimer is now in onReceive Method of the AlarmManagerWrapper to handle the Event.");

		Bundle bundle = intent.getExtras();
		AlarmEventData alarmEventData = (AlarmEventData) bundle.get(AlarmEventData.getIntentname());

		InternalStorageWrapper internalStorageWrapper = new InternalStorageWrapper(MainApplicationContext.getAppContext());

		if (alarmEventData.getEventType() == EventType.EVENT_CHANGE || alarmEventData.getEventType() == EventType.EVENT_AFTER_BREAK) {
			internalStorageWrapper.saveUpdatedLastUseOfContraceptionToStorage(CalendarWrapper.getTodayAsDayOfYear());
		} else {
			internalStorageWrapper.saveUpdatedLastBreakOfContraceptionToStorage(CalendarWrapper.getTodayAsDayOfYear());
		}

		this.createNotification(context, alarmEventData.getAlarmMessage());
		// this.SetAlarm();
	}

	@SuppressWarnings("deprecation")
	private void createNotification(Context context, String msg) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);

		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = "Contraception Notification";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);

		CharSequence contentTitle = "Your Contraception has news";
		CharSequence contentText = msg;

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.defaults = Notification.DEFAULT_ALL;

		mNotificationManager.notify(AlarmManagerWrapper.HELLO_ID, notification);
	}

	public void SetAlarm() {
		LoggerWrapper.LogInfo("ContraceptiveTimer is now in setAlarm Method.");

		Context context = MainApplicationContext.getAppContext();

		AlarmEventRetriever alarmEventRetriever = new AlarmEventRetriever();
		AlarmEventData alarmEventData = alarmEventRetriever.retrieveAlarmEventData();

		if (alarmEventData != null) {
			LoggerWrapper.LogInfo("ContraceptiveTimer is now trying to setup alarm from file..");

			AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

			Intent intent = new Intent(context, AlarmManagerWrapper.class);
			intent.putExtra(AlarmEventData.getIntentname(), alarmEventData);

			PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
			// am.set(AlarmManager.RTC_WAKEUP,
			// alarmEventData.getAlarmTimeInMilliSeconds(), pi);

			// For Tests
			GregorianCalendar gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
			gregorianCalendar.add(Calendar.SECOND, 5);
			am.set(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), pi);
		} else {
			LoggerWrapper.LogInfo("ContraceptiveTimer is now starting the app because no file was detected.");
			// Intent intent = new Intent(context, TestActivity.class);
			// context.startActivity(intent);
		}
		// Intent intent = new Intent(context, TestActivity.class);
		// context.startActivity(intent);
		LoggerWrapper.LogInfo("ContraceptiveTimer is now finishing the setAlarm Method.");
	}

	public void CancelAlarm(Context context) {
		Intent intent = new Intent(context, AlarmManagerWrapper.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
}
