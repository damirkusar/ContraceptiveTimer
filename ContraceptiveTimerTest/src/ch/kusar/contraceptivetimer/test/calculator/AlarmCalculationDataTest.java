package ch.kusar.contraceptivetimer.test.calculator;

import java.util.Calendar;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.calculator.AlarmCalculationData;
import ch.kusar.test.calendarWrapper.CalendarWrapperForTest;

public class AlarmCalculationDataTest extends AndroidTestCase {

	AlarmCalculationData alarmData;
	Calendar calendar;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		this.alarmData = new AlarmCalculationData();
		this.alarmData.setAlarmTime(CalendarWrapperForTest
				.getActualCalendarWithoutHourMinutesSeconds());
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		this.alarmData = null;
	}

	public void testGetIntervalDays_TypeIsRing_Returns28() {
		this.alarmData
				.setContraceptiveType(ContraceptiveType.CONTRACEPTION_RING);

		Assert.assertEquals(28, this.alarmData.getIntervalDays());
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
}