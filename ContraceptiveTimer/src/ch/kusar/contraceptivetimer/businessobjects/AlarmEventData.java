package ch.kusar.contraceptivetimer.businessobjects;

public class AlarmEventData {
	private static final String intentNameAlarmMessage = "AlarmEventDataNameForAlarmMessage";
	private static final String intentNameAlarmInMilliSeconds = "AlarmEventDataNameForAlarmInMilliSeconds";
	private static final String intentNameEventType = "AlarmEventDataNameForEventType";

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

	public static String getIntentNameAlarmMmessage() {
		return AlarmEventData.intentNameAlarmMessage;
	}

	public static String getIntentNameAlarmInMilliseconds() {
		return AlarmEventData.intentNameAlarmInMilliSeconds;
	}

	public static String getIntentNameEventType() {
		return AlarmEventData.intentNameEventType;
	}

	@Override
	public String toString() {
		String msg = String.format("Alarmtime in milliseconds is: %1$s, Eventtype is: %2$s, Message is %3$s", this.getAlarmTimeInMilliSeconds(),
				this.getEventType(), this.getAlarmMessage());
		return msg;
	}
}