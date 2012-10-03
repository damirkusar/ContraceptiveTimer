package ch.kusar.contraceptivetimer.businessobjects;

import java.io.Serializable;

public class AlarmEventData implements Serializable {

	private static final long serialVersionUID = 6030101754400884480L;
	private static final String intentName = "AlarmEventDataNameForIntent";
	private String alarmMessage;
	private long alarmInMilliSeconds;
	private EventType eventType;

	public AlarmEventData() {
	}

	public String getAlarmMessage() {
		return this.alarmMessage;
	}

	public void setAlarmMessage(String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}

	public long getAlarmTimeInMilliSeconds() {
		return this.alarmInMilliSeconds;
	}

	public void setAlarmTimeInMilliSeconds(long alarmInMilliSeconds) {
		this.alarmInMilliSeconds = alarmInMilliSeconds;
	}

	public EventType getEventType() {
		return this.eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public static String getIntentname() {
		return AlarmEventData.intentName;
	}
}