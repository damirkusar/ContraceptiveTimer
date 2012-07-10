package ch.kusar.contraceptivetimer.test.businessobjects;

import java.util.Calendar;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import ch.kusar.calendarWrapper.CalendarWrapper;
import ch.kusar.contraceptivetimer.businessobjects.AlarmData;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.wrapper.InternalStorageWrapper;

public class AlarmDataTest extends AndroidTestCase {

	AlarmData alarmData;
	InternalStorageWrapper internalStorageWrapper;
	Calendar calendar;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		this.calendar = CalendarWrapper.getActualCalendar();
		Calendar startCalendar = this.calendar;

		this.calendar.set(Calendar.MINUTE,
				this.calendar.get(Calendar.MINUTE) + 1);

		Calendar alarmCalendar = this.calendar;

		this.alarmData = new AlarmData();
		this.alarmData.setStartTime(startCalendar);
		this.alarmData.setAlarmTime(alarmCalendar);

		this.internalStorageWrapper = new InternalStorageWrapper(this
				.getContext().getApplicationContext());

	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		this.alarmData = null;
		this.internalStorageWrapper = null;
	}

	public void testGetIntervalDays_TypeIsRing_Returns21() {
		this.alarmData
				.setContraceptiveType(ContraceptiveType.CONTRACEPTION_RING);

		Assert.assertEquals(21, this.alarmData.getIntervalDays());
	}

	public void testGetIntervalDays_TypeIsPill_Returns1() {
		this.alarmData
				.setContraceptiveType(ContraceptiveType.CONTRACEPTION_PILL);

		Assert.assertEquals(1, this.alarmData.getIntervalDays());
	}

	public void testGetIntervalDays_TypeIsPatch_Returns7() {
		this.alarmData
				.setContraceptiveType(ContraceptiveType.CONTRACEPTION_PATCH);

		Assert.assertEquals(7, this.alarmData.getIntervalDays());
	}

	public void testNumberOfDaysSinceLastBreak_StartTimeAndLastBreakIs1DayAgo_Expected1() {
		this.calendar.set(Calendar.DAY_OF_YEAR,
				this.calendar.get(Calendar.DAY_OF_YEAR) - 1);

		this.alarmData.setLastBreak(this.calendar);

		int numberOfDaysSinceLastBreak = this.alarmData
				.getNumberOfDaysSinceLastBreak();

		Assert.assertEquals(1, numberOfDaysSinceLastBreak);
	}

	public void testNumberOfDaysSinceLastBreak_StartTimeAndLastBreakIs7DaysAgo_Expected7() {
		this.calendar.set(Calendar.DAY_OF_YEAR,
				this.calendar.get(Calendar.DAY_OF_YEAR) - 7);

		this.alarmData.setLastBreak(this.calendar);

		int numberOfDaysSinceLastBreak = this.alarmData
				.getNumberOfDaysSinceLastBreak();

		Assert.assertEquals(7, numberOfDaysSinceLastBreak);
	}

	public void testNumberOfDaysSinceLastBreak_StartTimeAndLastBreakIs21DaysAgo_Expected21() {
		this.calendar.set(Calendar.DAY_OF_YEAR,
				this.calendar.get(Calendar.DAY_OF_YEAR) - 21);

		this.alarmData.setLastBreak(this.calendar);

		int numberOfDaysSinceLastBreak = this.alarmData
				.getNumberOfDaysSinceLastBreak();

		Assert.assertEquals(21, numberOfDaysSinceLastBreak);
	}

	public void testIsTimeToMakeSevenDaysBreak_LastBreakIs21DaysAgo_ExpectedTrue() {
		this.calendar.set(Calendar.DAY_OF_YEAR,
				this.calendar.get(Calendar.DAY_OF_YEAR) - 21);

		this.alarmData.setLastBreak(this.calendar);
		this.alarmData
				.setContraceptiveType(ContraceptiveType.CONTRACEPTION_RING);

		int numberOfDaysSinceLastBreak = this.alarmData
				.getNumberOfDaysSinceLastBreak();

		Assert.assertTrue(this.alarmData.isTimeToMakeSevenDaysBreak());
	}
}
