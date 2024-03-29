package ch.kusar.contraceptivetimer.test.businessobjects;

import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import ch.kusar.contraceptivetimer.businessobjects.AlarmData;
import ch.kusar.contraceptivetimer.businessobjects.AlarmDataCalculator;
import ch.kusar.contraceptivetimer.businessobjects.AlarmEventData;
import ch.kusar.contraceptivetimer.businessobjects.AlarmMessage;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.test.calendarWrapper.CalendarWrapperForTest;

public class AlarmDataCalculatorTest extends AndroidTestCase {

	AlarmData alarmData;
	AlarmDataCalculator alarmDataCalculator;
	GregorianCalendar calendar;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		this.alarmData = new AlarmData();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		this.alarmData = null;
		this.alarmDataCalculator = null;
	}

	private void setupCalendarWithLastBreakAgoInDays(int lastBreakAgoInDays,
			ContraceptiveType contraceptiveType) {

		this.calendar = CalendarWrapperForTest
				.getActualCalendarWithoutHourMinutesSeconds();

		this.calendar.set(Calendar.DAY_OF_YEAR,
				this.calendar.get(Calendar.DAY_OF_YEAR) - lastBreakAgoInDays);
		this.calendar.set(Calendar.MINUTE,
				this.calendar.get(Calendar.MINUTE) - 5);

		this.alarmData.setLastBreak(this.calendar);
		this.alarmData.setContraceptiveType(contraceptiveType);

		GregorianCalendar alarmCalendar = this.calendar;

		this.alarmData.setAlarmTime(alarmCalendar);

		this.alarmDataCalculator = new AlarmDataCalculator(this.alarmData);
	}

	private void setupAlarmData(ContraceptiveType contraceptiveType,
			GregorianCalendar lastUseOfContraceptive,
			GregorianCalendar lastBreak, GregorianCalendar alarmTime,
			boolean isAlarmActive) {
		this.alarmData.setContraceptiveType(contraceptiveType);
		this.alarmData.setLastUseOfContraceptive(lastUseOfContraceptive);
		this.alarmData.setLastBreak(lastBreak);
		this.alarmData.setAlarmTime(alarmTime);
		this.alarmData.setAlarmActive(isAlarmActive);

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

	public void testNextAlarmEvent_LastUseWasLastSundayAndLastBreakWasLastSundayAndContraceptiveTypeIsRing_ExpectedMessageForRemoveRingAndCalendarDayIsNextSunday() {

		GregorianCalendar sundayThreeWeeksAgo = CalendarWrapperForTest
				.getSundayThreeWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_RING,
				sundayThreeWeeksAgo, sundayThreeWeeksAgo,
				CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		int nextBreakFromLastUse = sundayThreeWeeksAgo
				.get(Calendar.DAY_OF_YEAR) + 28;
		int nextBreakFromLastBreak = sundayThreeWeeksAgo
				.get(Calendar.DAY_OF_YEAR) + 28;

		Assert.assertEquals(nextBreakFromLastBreak, nextBreakFromLastUse);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getRingRemoveMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextBreakFromLastBreak, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));

		Assert.assertEquals(nextBreakFromLastUse, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));
	}

	public void testNextAlarmEvent_LastUseWasLastSundayAndLastBreakSundayAWeekAgoAndContraceptiveTypeIsRing_ExpectedMessageForRemoveRingAndCalendarDayIsLastSundayIn21Days() {

		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();
		GregorianCalendar sundayWeekAgo = CalendarWrapperForTest
				.getSundayOneWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_RING, lastSunday,
				sundayWeekAgo, CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		int nextBreakFromLastUse = lastSunday.get(Calendar.DAY_OF_YEAR) + 21;
		int nextBreakFromLastBreak = sundayWeekAgo.get(Calendar.DAY_OF_YEAR) + 28;

		Assert.assertEquals(nextBreakFromLastBreak, nextBreakFromLastUse);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getRingRemoveMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextBreakFromLastBreak, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));

		Assert.assertEquals(nextBreakFromLastUse, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));
	}

	public void testNextAlarmEvent_LastUseWasLastSundayTwoWeeksAgoAndLastBreakWasLastSundayThreeWeeksAgoAndContraceptiveTypeIsRing_ExpectedMessageForRemoveRingAndCalendarDayIsNextSunday() {

		GregorianCalendar sundayTwoWeeksAgo = CalendarWrapperForTest
				.getSundayTwoWeeksAgo();
		GregorianCalendar sundayThreeWeekAgo = CalendarWrapperForTest
				.getSundayThreeWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_RING,
				sundayTwoWeeksAgo, sundayThreeWeekAgo,
				CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		int nextEventFromLastUse = sundayTwoWeeksAgo.get(Calendar.DAY_OF_YEAR) + 21;
		int nextEventFromLastBreak = sundayThreeWeekAgo
				.get(Calendar.DAY_OF_YEAR) + 28;

		Assert.assertEquals(nextEventFromLastUse, nextEventFromLastBreak);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getRingRemoveMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));

		Assert.assertEquals(nextEventFromLastBreak, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));
	}

	public void testNextAlarmEvent_LastUseWasLastSundayThreeWeeksAgoAndLastBreakWasLastSundayAndContraceptiveTypeIsRing_ExpectedMessageForChangeRingAndCalendarDayIsNextSunday() {

		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();
		GregorianCalendar sundayThreeWeekAgo = CalendarWrapperForTest
				.getSundayThreeWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_RING,
				sundayThreeWeekAgo, lastSunday,
				CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		int nextEventFromLastUse = sundayThreeWeekAgo.get(Calendar.DAY_OF_YEAR) + 28;
		int nextEventFromLastBreak = lastSunday.get(Calendar.DAY_OF_YEAR) + 7;

		Assert.assertEquals(nextEventFromLastUse, nextEventFromLastBreak);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getRingChangeMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));

		Assert.assertEquals(nextEventFromLastBreak, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));
	}

	public void testLastTodaysDayOfYear() {
		Assert.assertEquals(221, CalendarWrapperForTest.getTodaysDayOfYear());
	}

	public void testNextAlarmEvent_LastUseWasLastSundayAndLastBreakSundayAWeekAgoAndContraceptiveTypeIsPatch_ExpectedMessageForChangePatchAndCalendarDayIsNextSunday() {

		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();
		GregorianCalendar sundayWeekAgo = CalendarWrapperForTest
				.getSundayOneWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PATCH, lastSunday,
				sundayWeekAgo, CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		int nextBreakFromLastUse = lastSunday.get(Calendar.DAY_OF_YEAR) + 7;
		int nextBreakFromLastBreak = sundayWeekAgo.get(Calendar.DAY_OF_YEAR) + 14;

		Assert.assertEquals(nextBreakFromLastBreak, nextBreakFromLastUse);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPatchChangeMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextBreakFromLastBreak, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));

		Assert.assertEquals(nextBreakFromLastUse, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));
	}

	public void testNextAlarmEvent_LastUseWasLastSundayOneWeeksAgoAndLastBreakWasLastSundayAndContraceptiveTypeIsPatch_ExpectedMessageForChangePatchAndCalendarDayIsNextSunday() {

		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();
		GregorianCalendar sundayOneWeekAgo = CalendarWrapperForTest
				.getSundayOneWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PATCH,
				sundayOneWeekAgo, lastSunday,
				CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		int nextEventFromLastUse = sundayOneWeekAgo.get(Calendar.DAY_OF_YEAR) + 14;
		int nextEventFromLastBreak = lastSunday.get(Calendar.DAY_OF_YEAR) + 7;

		Assert.assertEquals(nextEventFromLastUse, nextEventFromLastBreak);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPatchChangeMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));

		Assert.assertEquals(nextEventFromLastBreak, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));
	}

	public void testLastSunday() {
		Assert.assertEquals(218,
				CalendarWrapperForTest.getLastSunday()
						.get(Calendar.DAY_OF_YEAR));
	}

	public void testNextAlarmEvent_LastUseWasYesterdayAndLastBreakWasSundayAWeekAgoAndContraceptiveTypeIsPill_ExpectedMessageForChangePillAndCalendarDayIsToday() {

		GregorianCalendar yesterday = CalendarWrapperForTest.getYesterday();
		GregorianCalendar sundayWeekAgo = CalendarWrapperForTest
				.getSundayOneWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PILL, yesterday,
				sundayWeekAgo, CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		int nextEventFromLastUse = yesterday.get(Calendar.DAY_OF_YEAR) + 1;

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPillChangeMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));
	}

	public void testNextAlarmEvent_LastUseWasLastSaturdayAndLastBreakWasLastSundayThreeWeeksAgoAndContraceptiveTypeIsPill_ExpectedMessageForRemovePillAndCalendarDayIsOneDayAfterLastUse() {

		GregorianCalendar lastSaturday = CalendarWrapperForTest
				.getLastSaturday();
		GregorianCalendar sundayFourWeeksAgo = CalendarWrapperForTest
				.getSundayFourWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PILL, lastSaturday,
				sundayFourWeeksAgo, CalendarWrapperForTest.getAlarmTimeAt8PM(),
				true);

		int nextEventFromLastUse = lastSaturday.get(Calendar.DAY_OF_YEAR) + 1;
		int nextEventFromLastBreak = sundayFourWeeksAgo
				.get(Calendar.DAY_OF_YEAR) + 28;

		Assert.assertEquals(nextEventFromLastUse, nextEventFromLastBreak);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPillRemoveMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));

		Assert.assertEquals(nextEventFromLastBreak, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));
	}

	public void testNextAlarmEvent_LastUseWasLastSaturdayAndLastBreakWasLastSundayAndContraceptiveTypeIsPill_ExpectedMessageForChangePillAndCalendarDayIsNextSunday() {

		GregorianCalendar lastSaturday = CalendarWrapperForTest
				.getLastSaturday();
		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PILL, lastSaturday,
				lastSunday, CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		int nextEventFromLastUse = lastSaturday.get(Calendar.DAY_OF_YEAR) + 8;
		int nextEventFromLastBreak = lastSunday.get(Calendar.DAY_OF_YEAR) + 7;

		Assert.assertEquals(nextEventFromLastUse, nextEventFromLastBreak);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPillChangeMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));

		Assert.assertEquals(nextEventFromLastBreak, alarmEventData.getAlarm()
				.get(Calendar.DAY_OF_YEAR));
	}

	public void testLastSundayWeekAgo() {
		Assert.assertEquals(211, CalendarWrapperForTest.getSundayOneWeekAgo()
				.get(Calendar.DAY_OF_YEAR));
	}
}