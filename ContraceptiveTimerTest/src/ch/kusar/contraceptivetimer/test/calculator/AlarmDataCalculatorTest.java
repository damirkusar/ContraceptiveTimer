package ch.kusar.contraceptivetimer.test.calculator;

import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import ch.kusar.contraceptivetimer.businessobjects.AlarmEventData;
import ch.kusar.contraceptivetimer.businessobjects.AlarmMessage;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.businessobjects.EventType;
import ch.kusar.contraceptivetimer.calculator.AlarmCalculationData;
import ch.kusar.contraceptivetimer.calculator.AlarmDataCalculator;
import ch.kusar.test.calendarWrapper.CalendarWrapperForTest;

public class AlarmDataCalculatorTest extends AndroidTestCase {

	AlarmCalculationData alarmData;
	AlarmDataCalculator alarmDataCalculator;
	GregorianCalendar calendar;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		this.alarmData = new AlarmCalculationData();
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

		GregorianCalendar nextBreakFromLastUse = (GregorianCalendar) sundayThreeWeeksAgo
				.clone();
		nextBreakFromLastUse.add(Calendar.DAY_OF_YEAR, 28);
		nextBreakFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextBreakFromLastBreak = (GregorianCalendar) sundayThreeWeeksAgo
				.clone();
		nextBreakFromLastBreak.add(Calendar.DAY_OF_YEAR, 28);
		nextBreakFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextBreakFromLastBreak, nextBreakFromLastUse);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getRingRemoveMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextBreakFromLastBreak.getTimeInMillis(),
				alarmEventData.getAlarm());
		Assert.assertEquals(nextBreakFromLastUse.getTimeInMillis(),
				alarmEventData.getAlarm());
	}

	public void testNextAlarmEvent_LastUseWasLastSundayAndLastBreakSundayAWeekAgoAndContraceptiveTypeIsRing_ExpectedMessageForRemoveRingAndCalendarDayIsLastSundayIn21Days() {

		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();
		GregorianCalendar sundayWeekAgo = CalendarWrapperForTest
				.getSundayOneWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_RING, lastSunday,
				sundayWeekAgo, CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		GregorianCalendar nextBreakFromLastUse = (GregorianCalendar) lastSunday
				.clone();
		nextBreakFromLastUse.add(Calendar.DAY_OF_YEAR, 21);
		nextBreakFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextBreakFromLastBreak = (GregorianCalendar) sundayWeekAgo
				.clone();
		nextBreakFromLastBreak.add(Calendar.DAY_OF_YEAR, 28);
		nextBreakFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextBreakFromLastBreak, nextBreakFromLastUse);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getRingRemoveMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextBreakFromLastBreak.getTimeInMillis(),
				alarmEventData.getAlarm());
		Assert.assertEquals(nextBreakFromLastUse.getTimeInMillis(),
				alarmEventData.getAlarm());
	}

	public void testNextAlarmEvent_LastUseWasLastSundayTwoWeeksAgoAndLastBreakWasLastSundayThreeWeeksAgoAndContraceptiveTypeIsRing_ExpectedMessageForRemoveRingAndCalendarDayIsNextSunday() {

		GregorianCalendar sundayTwoWeeksAgo = CalendarWrapperForTest
				.getSundayTwoWeeksAgo();
		GregorianCalendar sundayThreeWeekAgo = CalendarWrapperForTest
				.getSundayThreeWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_RING,
				sundayTwoWeeksAgo, sundayThreeWeekAgo,
				CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		GregorianCalendar nextEventFromLastUse = (GregorianCalendar) sundayTwoWeeksAgo
				.clone();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 21);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextEventFromLastBreak = (GregorianCalendar) sundayThreeWeekAgo
				.clone();
		nextEventFromLastBreak.add(Calendar.DAY_OF_YEAR, 28);
		nextEventFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextEventFromLastUse, nextEventFromLastBreak);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getRingRemoveMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(),
				alarmEventData.getAlarm());
		Assert.assertEquals(nextEventFromLastBreak.getTimeInMillis(),
				alarmEventData.getAlarm());
	}

	public void testNextAlarmEvent_LastUseWasLastSundayThreeWeeksAgoAndLastBreakWasLastSundayAndContraceptiveTypeIsRing_ExpectedMessageForChangeRingAndCalendarDayIsNextSunday() {

		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();
		GregorianCalendar sundayThreeWeekAgo = CalendarWrapperForTest
				.getSundayThreeWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_RING,
				sundayThreeWeekAgo, lastSunday,
				CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		GregorianCalendar nextEventFromLastUse = (GregorianCalendar) sundayThreeWeekAgo
				.clone();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 28);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextEventFromLastBreak = (GregorianCalendar) lastSunday
				.clone();
		nextEventFromLastBreak.add(Calendar.DAY_OF_YEAR, 7);
		nextEventFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextEventFromLastUse, nextEventFromLastBreak);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getRingChangeMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(),
				alarmEventData.getAlarm());
		Assert.assertEquals(nextEventFromLastBreak.getTimeInMillis(),
				alarmEventData.getAlarm());
	}

	public void testLastTodaysDayOfYear() {
		int today = CalendarWrapperForTest.getTodaysDayOfYear();
		Assert.assertEquals(today, CalendarWrapperForTest.getTodaysDayOfYear());
	}

	public void testNextAlarmEvent_LastUseWasLastSundayAndLastBreakSundayAWeekAgoAndContraceptiveTypeIsPatch_ExpectedMessageForChangePatchAndCalendarDayIsNextSunday() {

		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();
		GregorianCalendar sundayWeekAgo = CalendarWrapperForTest
				.getSundayOneWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PATCH, lastSunday,
				sundayWeekAgo, CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		GregorianCalendar nextBreakFromLastUse = (GregorianCalendar) lastSunday
				.clone();
		nextBreakFromLastUse.add(Calendar.DAY_OF_YEAR, 7);
		nextBreakFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextBreakFromLastBreak = (GregorianCalendar) sundayWeekAgo
				.clone();
		nextBreakFromLastBreak.add(Calendar.DAY_OF_YEAR, 14);
		nextBreakFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextBreakFromLastBreak, nextBreakFromLastUse);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPatchChangeMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextBreakFromLastBreak.getTimeInMillis(),
				alarmEventData.getAlarm());
		Assert.assertEquals(nextBreakFromLastUse.getTimeInMillis(),
				alarmEventData.getAlarm());
	}

	public void testNextAlarmEvent_LastUseWasLastSundayOneWeeksAgoAndLastBreakWasLastSundayAndContraceptiveTypeIsPatch_ExpectedMessageForChangePatchAndCalendarDayIsNextSunday() {

		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();
		GregorianCalendar sundayOneWeekAgo = CalendarWrapperForTest
				.getSundayOneWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PATCH,
				sundayOneWeekAgo, lastSunday,
				CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		GregorianCalendar nextEventFromLastUse = (GregorianCalendar) sundayOneWeekAgo
				.clone();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 14);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextEventFromLastBreak = (GregorianCalendar) lastSunday
				.clone();
		nextEventFromLastBreak.add(Calendar.DAY_OF_YEAR, 7);
		nextEventFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextEventFromLastUse, nextEventFromLastBreak);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPatchChangeMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(),
				alarmEventData.getAlarm());
		Assert.assertEquals(nextEventFromLastBreak.getTimeInMillis(),
				alarmEventData.getAlarm());
	}

	public void testLastSunday() {
		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();
		int lastSundayDayOfYear = lastSunday.get(Calendar.DAY_OF_YEAR);

		Assert.assertEquals(lastSundayDayOfYear, CalendarWrapperForTest
				.getLastSunday().get(Calendar.DAY_OF_YEAR));
	}

	public void testNextAlarmEvent_LastUseWasYesterdayAndLastBreakWasSundayAWeekAgoAndContraceptiveTypeIsPill_ExpectedMessageForChangePillAndCalendarDayIsToday() {

		GregorianCalendar yesterday = CalendarWrapperForTest.getYesterday();
		GregorianCalendar sundayWeekAgo = CalendarWrapperForTest
				.getSundayOneWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PILL, yesterday,
				sundayWeekAgo, CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		GregorianCalendar nextEventFromLastUse = (GregorianCalendar) yesterday
				.clone();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 1);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPillChangeMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(),
				alarmEventData.getAlarm());
	}

	public void testNextAlarmEvent_LastUseWasLastSaturdayAndLastBreakWasLastSundayThreeWeeksAgoAndContraceptiveTypeIsPill_ExpectedMessageForRemovePillAndCalendarDayIsOneDayAfterLastUse() {

		GregorianCalendar lastSaturday = CalendarWrapperForTest
				.getLastSaturday();
		GregorianCalendar sundayFourWeeksAgo = CalendarWrapperForTest
				.getSundayFourWeekAgo();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PILL, lastSaturday,
				sundayFourWeeksAgo, CalendarWrapperForTest.getAlarmTimeAt8PM(),
				true);

		GregorianCalendar nextEventFromLastUse = (GregorianCalendar) lastSaturday
				.clone();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 1);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextEventFromLastBreak = (GregorianCalendar) sundayFourWeeksAgo
				.clone();
		nextEventFromLastBreak.add(Calendar.DAY_OF_YEAR, 28);
		nextEventFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextEventFromLastUse, nextEventFromLastBreak);

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPillRemoveMessage(),
				alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(),
				alarmEventData.getAlarm());
		Assert.assertEquals(nextEventFromLastBreak.getTimeInMillis(),
				alarmEventData.getAlarm());
	}

	public void testNextAlarmEvent_LastUseWasLastSaturdayAndLastBreakWasLastSundayAndContraceptiveTypeIsPill_ExpectedMessageForChangePillAndCalendarDayIsNextSunday() {

		GregorianCalendar lastSaturday = CalendarWrapperForTest
				.getLastSaturday();
		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PILL, lastSaturday,
				lastSunday, CalendarWrapperForTest.getAlarmTimeAt8PM(), true);

		GregorianCalendar nextEventFromLastUse = (GregorianCalendar) lastSaturday
				.clone();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 8);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);

		GregorianCalendar nextEventFromLastBreak = (GregorianCalendar) lastSunday
				.clone();
		nextEventFromLastBreak.add(Calendar.DAY_OF_YEAR, 7);
		nextEventFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(),
				nextEventFromLastBreak.getTimeInMillis());

		AlarmEventData alarmEventData = this.alarmDataCalculator
				.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPillChangeMessage(),
				alarmEventData.getAlarmMessage());
		Assert.assertEquals(EventType.EVENT_AFTER_BREAK,
				alarmEventData.getEventType());

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(alarmEventData.getAlarm());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(),
				alarmEventData.getAlarm());
		Assert.assertEquals(nextEventFromLastBreak.getTimeInMillis(),
				alarmEventData.getAlarm());
	}

	public void testLastSundayWeekAgo() {
		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();
		int lastSundayDayOfYear = lastSunday.get(Calendar.DAY_OF_YEAR);
		int lastSundayDayOfYearOneWeekAgo = lastSundayDayOfYear - 7;

		Assert.assertEquals(
				lastSundayDayOfYearOneWeekAgo,
				CalendarWrapperForTest.getSundayOneWeekAgo().get(
						Calendar.DAY_OF_YEAR));
	}
}