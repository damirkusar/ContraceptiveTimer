package ch.kusar.contraceptivetimer.calculator;

import java.io.Serializable;

public class AlarmTime implements Serializable {
	private static final long serialVersionUID = -6949624393798961333L;
	private int hourOfDay;
	private int minuteOfDay;

	public AlarmTime() {
	}

	public AlarmTime(int alarmHours, int alarmMinutes) {
		this.hourOfDay = alarmHours;
		this.minuteOfDay = alarmMinutes;
	}

	public int getHourOfDay() {
		return this.hourOfDay;
	}

	public void setHourOfDay(int hourOfDay) {
		this.hourOfDay = hourOfDay;
	}

	public int getMinutes() {
		return this.minuteOfDay;
	}

	public void setMinuteOfDay(int minuteOfDay) {
		this.minuteOfDay = minuteOfDay;
	}
}
