package ch.kusar.test.calendarWrapper;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ch.kusar.contraceptivetimer.wrapper.CalendarWrapper;

public class CalendarWrapperForTest {

	public static GregorianCalendar getActualCalendarWithoutHourMinutesSeconds() {
		return CalendarWrapperForTest.convertToCalendarWithoutHourMinutesSeconds(Calendar.getInstance());
	}

	private static GregorianCalendar convertToCalendarWithoutHourMinutesSeconds(Calendar calendar) {
		Calendar cal = calendar;

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return (GregorianCalendar) cal;
	}

	public static GregorianCalendar getLastSunday() {
		int todaysWeekDay = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds().get(Calendar.DAY_OF_WEEK);
		int realTodaysWeekDay = CalendarWrapperForTest.convertFromCalendarDayToNormal7DaysScale(todaysWeekDay);

		Calendar calendar = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds();
		calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR) - realTodaysWeekDay));
		return (GregorianCalendar) calendar;
	}

	public static GregorianCalendar getLastSaturday() {
		int todaysWeekDay = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds().get(Calendar.DAY_OF_WEEK);

		Calendar calendar = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds();
		calendar.set(Calendar.DAY_OF_YEAR,
				(calendar.get(Calendar.DAY_OF_YEAR) - CalendarWrapperForTest.convertFromCalendarDayToNormal7DaysScale(todaysWeekDay) - 1));
		return (GregorianCalendar) calendar;
	}

	public static GregorianCalendar getSundayOneWeekAgo() {
		int todaysWeekDay = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds().get(Calendar.DAY_OF_WEEK);

		Calendar calendar = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - CalendarWrapperForTest.convertFromCalendarDayToNormal7DaysScale(todaysWeekDay)
				- 7);
		return (GregorianCalendar) calendar;
	}

	public static GregorianCalendar getYesterday() {
		CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds().get(Calendar.DAY_OF_WEEK);

		Calendar calendar = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
		return (GregorianCalendar) calendar;
	}

	public static GregorianCalendar getYesterdayAWeekAgo() {
		CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds().get(Calendar.DAY_OF_WEEK);

		Calendar calendar = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 8);
		return (GregorianCalendar) calendar;
	}

	public static GregorianCalendar getToday() {
		CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds().get(Calendar.DAY_OF_WEEK);

		Calendar calendar = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
		return (GregorianCalendar) calendar;
	}

	public static GregorianCalendar getTodayOneWeekAgo() {
		CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds().get(Calendar.DAY_OF_WEEK);

		Calendar calendar = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
		return (GregorianCalendar) calendar;
	}

	public static GregorianCalendar getSundayTwoWeeksAgo() {
		int todaysWeekDay = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds().get(Calendar.DAY_OF_WEEK);

		Calendar calendar = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - CalendarWrapperForTest.convertFromCalendarDayToNormal7DaysScale(todaysWeekDay)
				- 14);
		return (GregorianCalendar) calendar;
	}

	public static GregorianCalendar getSundayThreeWeekAgo() {
		int todaysWeekDay = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds().get(Calendar.DAY_OF_WEEK);

		Calendar calendar = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - CalendarWrapperForTest.convertFromCalendarDayToNormal7DaysScale(todaysWeekDay)
				- 21);
		return (GregorianCalendar) calendar;
	}

	public static GregorianCalendar getSundayFourWeekAgo() {
		int todaysWeekDay = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds().get(Calendar.DAY_OF_WEEK);

		Calendar calendar = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - CalendarWrapperForTest.convertFromCalendarDayToNormal7DaysScale(todaysWeekDay)
				- 28);
		return (GregorianCalendar) calendar;
	}

	public static CalendarWrapper convertGregorianCalendarToCalendarWrapper(GregorianCalendar gc) {
		return CalendarWrapperForTest.createCalendarWrapper(gc);
	}

	public static int convertFromCalendarDayToNormal7DaysScale(int dayOfWeek) {
		switch (dayOfWeek) {
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			return dayOfWeek - 1;
		case 1:
			return 7;
		default:
			return 0;
		}
	}

	public static CalendarWrapper todayAsCalendarWrapper() {
		GregorianCalendar gc = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds();

		return CalendarWrapperForTest.createCalendarWrapper(gc);
	}

	public static CalendarWrapper todayAWeekAgoAsCalendarWrapper() {
		GregorianCalendar gc = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds();

		int daysOfWeekAsDaysOfYear = gc.get(Calendar.DAY_OF_YEAR) - 7;

		gc.set(Calendar.DAY_OF_YEAR, daysOfWeekAsDaysOfYear);

		return CalendarWrapperForTest.createCalendarWrapper(gc);
	}

	private static CalendarWrapper createCalendarWrapper(GregorianCalendar gc) {
		CalendarWrapper cw = new CalendarWrapper();
		cw.setYear(gc.get(Calendar.YEAR));
		cw.setMonth(gc.get(Calendar.MONTH));
		cw.setDayOfMonth(gc.get(Calendar.DAY_OF_MONTH));

		return cw;
	}
}