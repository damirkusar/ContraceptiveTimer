package ch.kusar.contraceptivetimer.test.calculator;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.calculator.AlarmCalculationData;
import ch.kusar.contraceptivetimer.wrapper.CalendarWrapper;
import ch.kusar.test.calendarWrapper.CalendarWrapperForTest;

public class AlarmCalculationDataTest extends AndroidTestCase {

	private AlarmCalculationData alarmCalculationData;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		this.alarmCalculationData = new AlarmCalculationData();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		this.alarmCalculationData = null;
	}

	public void testGetIntervalDays_TypeIsRing_Returns28() {
		this.alarmCalculationData.setContraceptiveType(ContraceptiveType.CONTRACEPTION_RING);

		Assert.assertEquals(28, this.alarmCalculationData.getIntervalDays());
	}

	public void testGetIntervalDays_TypeIsPill_Returns1() {
		this.alarmCalculationData.setContraceptiveType(ContraceptiveType.CONTRACEPTION_PILL);

		Assert.assertEquals(1, this.alarmCalculationData.getIntervalDays());
	}

	public void testGetIntervalDays_TypeIsPatch_Returns7() {
		this.alarmCalculationData.setContraceptiveType(ContraceptiveType.CONTRACEPTION_PATCH);

		Assert.assertEquals(7, this.alarmCalculationData.getIntervalDays());
	}

	public void testSetFirstUseWasOn_TypeIsPill_FirstUseWas1WeeksAgo_TimesUsedIs7() {
		CalendarWrapper cw = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getTodayOneWeekAgo());

		this.alarmCalculationData.setContraceptiveType(ContraceptiveType.CONTRACEPTION_PILL);
		this.alarmCalculationData.setFirstUseWasOn(cw);

		Assert.assertEquals(7, this.alarmCalculationData.getTimesUsed());
	}
}