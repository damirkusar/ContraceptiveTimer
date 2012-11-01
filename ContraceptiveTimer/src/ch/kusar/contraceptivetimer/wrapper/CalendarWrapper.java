package ch.kusar.contraceptivetimer.wrapper;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CalendarWrapper implements Serializable {

	private static final long serialVersionUID = -4959482964453259671L;
	private int hourOfDay;
	private int minutes;
	private int year;
	private int month;
	private int dayOfMonth;

	public CalendarWrapper() {
	}

	public CalendarWrapper(int year, int month, int dayOfMonth) {
		this.year = year;
		this.month = month;
		this.dayOfMonth = dayOfMonth;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return this.month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDayOfMonth() {
		return this.dayOfMonth;
	}

	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

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

	public long getTimeInMillis() {
		GregorianCalendar calInstance = CalendarWrapper.getGregorianCalendarInstance();

		GregorianCalendar cal = this.convertToCalendarWithoutHourMinutesSeconds(calInstance);
		cal.set(Calendar.YEAR, this.year);
		cal.set(Calendar.MONTH, this.month);
		cal.set(Calendar.DAY_OF_MONTH, this.dayOfMonth);

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

	public static CalendarWrapper getToday() {
		GregorianCalendar cal = CalendarWrapper.getGregorianCalendarInstance();
		CalendarWrapper cw = new CalendarWrapper(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		return cw;
	}

	public static CalendarWrapper getTodayOneWeekAgo() {
		GregorianCalendar cal = CalendarWrapper.getGregorianCalendarInstance();
		int todayOneWeekAgoAsDayOfYear = cal.get(Calendar.DAY_OF_YEAR) - 7;
		cal.set(Calendar.DAY_OF_YEAR, todayOneWeekAgoAsDayOfYear);

		CalendarWrapper cw = new CalendarWrapper(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		return cw;
	}

	public static long getNowAsMilliseconds() {
		GregorianCalendar cal = CalendarWrapper.getGregorianCalendarInstance();
		// cal.add(Calendar.SECOND, 2);
		return cal.getTimeInMillis();
	}

	public static int daysBetweenTodayAnd(final CalendarWrapper startDate) {
		CalendarWrapper cal = new CalendarWrapper();

		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		long endInstant = cal.convertToCalendarWithoutHourMinutesSeconds(CalendarWrapper.getGregorianCalendarInstance()).getTimeInMillis();

		int presumedDays = (int) ((endInstant - startDate.getTimeInMillis()) / MILLIS_IN_DAY);
		Calendar cursor = new GregorianCalendar(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth());
		cursor.add(Calendar.DAY_OF_YEAR, presumedDays);
		long instant = cursor.getTimeInMillis();

		if (instant == endInstant) {
			return presumedDays;
		}

		final int step = instant < endInstant ? 1 : -1;

		do {
			cursor.add(Calendar.DAY_OF_MONTH, step);
			presumedDays += step;
		} while (cursor.getTimeInMillis() != endInstant);

		return presumedDays;
	}

	public void addDays(int days) {
		GregorianCalendar gc = this.convertCalendarWrapperIntoGregorianCalendar();
		gc.add(Calendar.DAY_OF_YEAR, days);

		this.convertGregorianCalendarToCalendarWrapper(gc);
	}

	public void subDays(int days) {
		GregorianCalendar gc = this.convertCalendarWrapperIntoGregorianCalendar();
		gc.set(Calendar.DAY_OF_YEAR, gc.get(Calendar.DAY_OF_YEAR) - days);

		this.convertGregorianCalendarToCalendarWrapper(gc);
	}

	public void convertGregorianCalendarToCalendarWrapper(GregorianCalendar gc) {
		this.setYear(gc.get(Calendar.YEAR));
		this.setMonth(gc.get(Calendar.MONTH));
		this.setDayOfMonth(gc.get(Calendar.DAY_OF_MONTH));
	}

	public GregorianCalendar convertCalendarWrapperIntoGregorianCalendar() {
		GregorianCalendar cal = new GregorianCalendar(this.year, this.month, this.dayOfMonth);
		cal.set(Calendar.HOUR_OF_DAY, this.hourOfDay);
		cal.set(Calendar.MINUTE, this.minutes);
		return cal;
	}

	@Override
	public CalendarWrapper clone() {
		CalendarWrapper cw = new CalendarWrapper(this.year, this.month, this.dayOfMonth);
		return cw;
	}

	@Override
	public String toString() {
		String msg = String.format("%1$s-%2$s-%3$s", this.year, this.month, this.dayOfMonth);
		return msg;
	}

	public static CalendarWrapper getYesterday() {
		CalendarWrapper cal = CalendarWrapper.getToday();
		cal.subDays(1);
		return cal;
	}
}