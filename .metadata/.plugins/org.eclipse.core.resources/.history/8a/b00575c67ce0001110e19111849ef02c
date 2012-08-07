package ch.kusar.contraceptivetimer.businessobjects;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ch.kusar.calendarWrapper.CalendarWrapper;

public class AlarmDataCalculator {

	private AlarmData alarmData;

	public AlarmDataCalculator(AlarmData alarmData) {
		this.setAlarmData(alarmData);
	}

	public AlarmData getAlarmData() {
		return this.alarmData;
	}

	public void setAlarmData(AlarmData alarmData) {
		this.alarmData = alarmData;
	}

	public int getNumberOfDaysSinceLastBreak() {
		Calendar actulCalendar = CalendarWrapper.getActualCalendar();

		Calendar lastBreakCalendar = this.alarmData.getLastBreak();

		return (int) ((actulCalendar.getTimeInMillis() - lastBreakCalendar
				.getTimeInMillis()) / this.alarmData.getMillisecondsinday());
	}

	public boolean isTimeToMakeSevenDaysBreak() {
		if (this.getNumberOfDaysSinceLastBreak()
				- this.alarmData.getNumberOfDaysToMakeSevenDaysBreak() == 0) {
			return true;
		}
		return false;
	}

	public Calendar getNextCalendarDayForAlarm() {
		Calendar actulCalendar = CalendarWrapper.getActualCalendar();

		Calendar lastBreakCalendar = this.alarmData.getLastBreak();

		long subtratedCalendarInMillis = actulCalendar.getTimeInMillis()
				- lastBreakCalendar.getTimeInMillis();
		Calendar subtratedCalendar = new GregorianCalendar();
		subtratedCalendar.setTimeInMillis(subtratedCalendarInMillis);

		// todo

		return null;
	}

	public Calendar getLastCalendarDayForAlarm() {
		Calendar lastAlarmCalendarDay = this.alarmData.getLastBreak();

		if (this.alarmData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_RING) {
			int oneWeek = 7;
			lastAlarmCalendarDay.set(Calendar.DAY_OF_YEAR, this.alarmData
					.getLastBreak().get(Calendar.DAY_OF_YEAR) + oneWeek);
		}
		if (this.alarmData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PATCH) {
			int threeWeeks = 21;
			lastAlarmCalendarDay.set(Calendar.DAY_OF_YEAR, this.alarmData
					.getLastBreak().get(Calendar.DAY_OF_YEAR) + threeWeeks);
		}
		if (this.alarmData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PILL) {
			int fourWeeks = 28;
			lastAlarmCalendarDay.set(Calendar.DAY_OF_YEAR, this.alarmData
					.getLastBreak().get(Calendar.DAY_OF_YEAR) + fourWeeks);
		}
		return lastAlarmCalendarDay;
	}

	public Calendar getFirstAlarmCalendarDayAfterBreak() {
		Calendar firstAlarmCalendarDayForAlarm = this.alarmData.getLastBreak();

		int fourWeeks = 35;
		firstAlarmCalendarDayForAlarm.set(Calendar.DAY_OF_YEAR, this.alarmData
				.getLastBreak().get(Calendar.DAY_OF_YEAR) + fourWeeks);

		return firstAlarmCalendarDayForAlarm;
	}
}
