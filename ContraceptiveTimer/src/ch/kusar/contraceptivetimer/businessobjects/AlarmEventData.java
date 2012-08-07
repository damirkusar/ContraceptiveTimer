package ch.kusar.contraceptivetimer.businessobjects;

import java.util.Calendar;

public class AlarmEventData {

	private String alarmMessage;
	private Calendar alarm;

	public AlarmEventData() {

	}

	public String getAlarmMessage() {
		return this.alarmMessage;
	}

	public void setAlarmMessage(String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}

	public Calendar getAlarm() {
		return this.alarm;
	}

	public void setAlarm(Calendar alarm) {
		this.alarm = alarm;
	}
}
