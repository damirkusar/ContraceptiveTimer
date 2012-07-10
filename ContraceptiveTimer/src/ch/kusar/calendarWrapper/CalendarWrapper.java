package ch.kusar.calendarWrapper;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarWrapper {

	public static GregorianCalendar getActualCalendar() {
		return new GregorianCalendar();
	}

	public static GregorianCalendar getActualCalendarWithoutHourMinutesSeconds() {
		return CalendarWrapper
				.convertToCalendarWithoutHourMinutesSeconds(new GregorianCalendar());
	}

	public static GregorianCalendar convertToCalendarWithoutHourMinutesSeconds(
			Calendar calendar) {
		Calendar cal = calendar;

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return (GregorianCalendar) cal;
	}
}
