package ch.kusar.contraceptivetimer.businessobjects;

import java.io.Serializable;
import java.util.Calendar;

import ch.kusar.calendarWrapper.CalendarWrapper;

public class AlarmData implements Serializable {

	private static final long serialVersionUID = 5921434769798781785L;
	private static final int millisecondsInDay = 1000 * 60 * 60 * 24;
	private static final int numberOfDaysToMakeSevenDaysBreak = 21;
	private ContraceptiveType contraceptiveType;
	private Calendar startTime;
	private Calendar lastBreak;
	private Calendar alarmTime;

	public AlarmData() {
		super();
	}

	public AlarmData(ContraceptiveType contraceptiveType, Calendar startTime,
			Calendar alarmTime) {
		super();
		this.contraceptiveType = contraceptiveType;
		this.startTime = startTime;
		this.alarmTime = alarmTime;
	}

	public ContraceptiveType getContraceptiveType() {
		return this.contraceptiveType;
	}

	public void setContraceptiveType(ContraceptiveType contraceptiveType) {
		this.contraceptiveType = contraceptiveType;
	}

	public Calendar getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getLastBreak() {
		return this.lastBreak;
	}

	public void setLastBreak(Calendar lastBreak) {
		this.lastBreak = lastBreak;
	}

	public Calendar getAlarmTime() {
		return this.alarmTime;
	}

	public static int getNumberOfDaysToMakeSevenDaysBreak() {
		return AlarmData.numberOfDaysToMakeSevenDaysBreak;
	}

	public void setAlarmTime(Calendar alarmTime) {
		this.alarmTime = alarmTime;
	}

	public int getIntervalDays() {
		return this.contraceptiveType.getMask();
	}

	public int getNumberOfDaysSinceLastBreak() {
		Calendar actulCalendar = CalendarWrapper
				.getActualCalendarWithoutHourMinutesSeconds();

		Calendar lastBreakCalendar = CalendarWrapper
				.convertToCalendarWithoutHourMinutesSeconds(this.lastBreak);

		return (int) ((actulCalendar.getTimeInMillis() - lastBreakCalendar
				.getTimeInMillis()) / AlarmData.millisecondsInDay);
	}

	public boolean isTimeToMakeSevenDaysBreak() {
		if (this.getNumberOfDaysSinceLastBreak()
				- AlarmData.numberOfDaysToMakeSevenDaysBreak == 0) {
			return true;
		}
		return false;
	}
}
