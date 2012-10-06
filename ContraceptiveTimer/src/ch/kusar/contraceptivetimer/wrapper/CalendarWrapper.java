package ch.kusar.contraceptivetimer.wrapper;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CalendarWrapper {
	private int hourOfDay;
	private int minutes;
	private int dayOfYear;

	public int getHourOfDay() {
		return this.hourOfDay;
	}

	public void setHourOfDay(int hourOfDay) {
		this.hourOfDay = hourOfDay;
	}

	public int getMinutes() {
		return this.minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getDayOfYear() {
		return this.dayOfYear;
	}

	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}

	public long getTimeInMillis() {
		GregorianCalendar calInstance = CalendarWrapper.getGregorianCalendarInstance();

		GregorianCalendar cal = this.convertToCalendarWithoutHourMinutesSeconds(calInstance);
		cal.set(Calendar.DAY_OF_YEAR, this.dayOfYear);
		cal.set(Calendar.HOUR_OF_DAY, this.hourOfDay);
		cal.set(Calendar.MINUTE, this.minutes);

		return cal.getTimeInMillis();
	}

	private static GregorianCalendar getGregorianCalendarInstance() {
		return (GregorianCalendar) Calendar.getInstance(TimeZone.getDefault());
	}

	private GregorianCalendar convertToCalendarWithoutHourMinutesSeconds(Calendar calendar) {
		Calendar cal = calendar;

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return (GregorianCalendar) cal;
	}

	public static int getTodayAsDayOfYear() {
		GregorianCalendar cal = CalendarWrapper.getGregorianCalendarInstance();
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	public static int getTodayOneWeekAgoAsDayOfYear() {
		int today = CalendarWrapper.getTodayAsDayOfYear();
		int todayOneWeekAgoAsDayOfYear = today - 7;
		return todayOneWeekAgoAsDayOfYear;
	}
}
