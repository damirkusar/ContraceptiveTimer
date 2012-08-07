package ch.kusar.contraceptivetimer.test.businessobjects;

import java.util.Calendar;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import ch.kusar.calendarWrapper.CalendarWrapper;
import ch.kusar.contraceptivetimer.businessobjects.AlarmData;
import ch.kusar.contraceptivetimer.businessobjects.AlarmDataCalculator;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.wrapper.InternalStorageWrapper;

public class AlarmDataCalculatorTest extends AndroidTestCase {

	AlarmData alarmData;
	AlarmDataCalculator alarmDataCalculator;
	InternalStorageWrapper internalStorageWrapper;
	Calendar calendar;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		this.calendar = CalendarWrapper.getActualCalendar();
		this.calendar.set(Calendar.MINUTE,
				this.calendar.get(Calendar.MINUTE) + 1);

		Calendar alarmCalendar = this.calendar;

		this.alarmData = new AlarmData();
		this.alarmData.setAlarmTime(alarmCalendar);

		this.internalStorageWrapper = new InternalStorageWrapper(this
				.getContext().getApplicationContext());
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		this.alarmData = null;
		this.alarmDataCalculator = null;
		this.internalStorageWrapper = null;
	}

	private void setupCalendarWithLastBreakAgoInDays(int lastBreakAgoInDays,
			ContraceptiveType contraceptiveType) {
		this.calendar.set(Calendar.DAY_OF_YEAR,
				this.calendar.get(Calendar.DAY_OF_YEAR) - lastBreakAgoInDays);
		this.calendar.set(Calendar.MINUTE,
				this.calendar.get(Calendar.MINUTE) - 5);

		this.alarmData.setLastBreak(this.calendar);
		this.alarmData.setContraceptiveType(contraceptiveType);

		this.alarmDataCalculator = new AlarmDataCalculator(this.alarmData);
	}

	public void testNumberOfDaysSinceLastBreak_StartTimeAndLastBreakIs1DayAgo_Expected1() {
		this.setupCalendarWithLastBreakAgoInDays(1,
				ContraceptiveType.CONTRACEPTION_PILL);

		int numberOfDaysSinceLastBreak = this.alarmDataCalculator
				.getNumberOfDaysSinceLastBreak();

		Assert.assertEquals(1, numberOfDaysSinceLastBreak);
	}

	public void testNumberOfDaysSinceLastBreak_StartTimeAndLastBreakIs7DaysAgo_Expected7() {
		this.setupCalendarWithLastBreakAgoInDays(7,
				ContraceptiveType.CONTRACEPTION_PATCH);

		this.alarmData.setLastBreak(this.calendar);

		int numberOfDaysSinceLastBreak = this.alarmDataCalculator
				.getNumberOfDaysSinceLastBreak();

		Assert.assertEquals(7, numberOfDaysSinceLastBreak);
	}

	public void testNumberOfDaysSinceLastBreak_StartTimeAndLastBreakIs21DaysAgo_Expected21() {
		this.setupCalendarWithLastBreakAgoInDays(21,
				ContraceptiveType.CONTRACEPTION_RING);

		int numberOfDaysSinceLastBreak = this.alarmDataCalculator
				.getNumberOfDaysSinceLastBreak();

		Assert.assertEquals(21, numberOfDaysSinceLastBreak);
	}

	public void testNextAlarmEvent() {
		// todo
	}
}