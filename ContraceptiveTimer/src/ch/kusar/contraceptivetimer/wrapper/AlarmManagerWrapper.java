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

	private static final int ID = 1;

	@Override
	public void onReceive(Context context, Intent intent) {
		LoggerWrapper.LogDebug("ContraceptiveTimer is now in onReceive Method of the AlarmManagerWrapper to handle the Event.");

		Bundle bundle = intent.getExtras();
		EventType eventType = (EventType) bundle.get(AlarmEventData.getIntentNameEventType());
		String alarmMessage = bundle.getString(AlarmEventData.getIntentNameAlarmMmessage());

		this.createNotification(context, alarmMessage);
		LoggerWrapper.LogDebug(alarmMessage);
		LoggerWrapper.LogDebug(eventType.name());

		InternalStorageWrapper internalStorageWrapper = new InternalStorageWrapper(MainApplicationContext.getAppContext());

		if (eventType == EventType.EVENT_CHANGE || eventType == EventType.EVENT_AFTER_BREAK) {
			internalStorageWrapper.saveUpdatedLastUseOfContraceptionToStorage(CalendarWrapper.getTodayAsDayOfYear());
			LoggerWrapper.LogDebug("EventType is EventChange or EventAfterBreak");
		} else {
			internalStorageWrapper.saveUpdatedLastBreakOfContraceptionToStorage(CalendarWrapper.getTodayAsDayOfYear());
			LoggerWrapper.LogDebug("EventType is EventBreak");
		}

		// TODO: use it when beta test.
		// this.SetAlarm();
	}

	@SuppressWarnings("deprecation")
	private void createNotification(Context context, String msg) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);

		// TODO change icon
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

		mNotificationManager.notify(AlarmManagerWrapper.ID, notification);
	}

	public void SetAlarm() {
		Context context = MainApplicationContext.getAppContext();

		LoggerWrapper.LogDebug("ContraceptiveTimer is now retrieving EventData.");

		AlarmEventRetriever alarmEventRetriever = new AlarmEventRetriever();
		AlarmEventData alarmEventData = alarmEventRetriever.retrieveAlarmEventData();
		LoggerWrapper.LogDebug(alarmEventData.toString());

		if (alarmEventData != null) {
			LoggerWrapper.LogDebug("ContraceptiveTimer is now trying to setup alarm from file..");

			AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

			Intent intent = new Intent(context, AlarmManagerWrapper.class);
			intent.putExtra(AlarmEventData.getIntentNameAlarmInMilliseconds(), alarmEventData.getAlarmTimeInMilliSeconds());
			intent.putExtra(AlarmEventData.getIntentNameAlarmMmessage(), alarmEventData.getAlarmMessage());
			intent.putExtra(AlarmEventData.getIntentNameEventType(), alarmEventData.getEventType());

			PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			// TODO: Comment it in.
			// am.set(AlarmManager.RTC_WAKEUP,
			// alarmEventData.getAlarmTimeInMilliSeconds(), pi);

			// TODO: For Tests, remove it.
			GregorianCalendar gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
			gregorianCalendar.add(Calendar.SECOND, 5);
			am.set(AlarmManager.RTC_WAKEUP, gregorianCalendar.getTimeInMillis(), pi);
			LoggerWrapper.LogDebug("ContraceptiveTimer is now finishing the setAlarm Method.");
			// this.CancelAlarm();
		} else {
			LoggerWrapper.LogDebug("ContraceptiveTimer Alarm not set.");
		}
	}

	public void CancelAlarm() {
		LoggerWrapper.LogDebug("ContraceptiveTimer canceling alarm.");

		Context context = MainApplicationContext.getAppContext();

		Intent intent = new Intent(context, AlarmManagerWrapper.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
}