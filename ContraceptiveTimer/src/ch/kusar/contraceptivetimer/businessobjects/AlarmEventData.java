package ch.kusar.contraceptivetimer.businessobjects;

import java.util.GregorianCalendar;

public class AlarmEventData {

	private String alarmMessage;
	private GregorianCalendar alarm;

	public AlarmEventData() {

	}

	public String getAlarmMessage() {
		return this.alarmMessage;
	}

	public void setAlarmMessage(String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}

	public GregorianCalendar getAlarm() {
		if (this.alarm == null) {
			this.alarm = new GregorianCalendar();
		}
		return this.alarm;
	}

	public void setAlarm(GregorianCalendar alarm) {
		this.alarm = alarm;
	}
}
