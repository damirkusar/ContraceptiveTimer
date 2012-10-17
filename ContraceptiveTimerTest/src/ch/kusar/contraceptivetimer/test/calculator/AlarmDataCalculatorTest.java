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
import ch.kusar.contraceptivetimer.calculator.AlarmTime;
import ch.kusar.contraceptivetimer.wrapper.CalendarWrapper;
import ch.kusar.test.calendarWrapper.CalendarWrapperForTest;

public class AlarmDataCalculatorTest extends AndroidTestCase {

	private AlarmCalculationData alarmCalculationData;
	private AlarmDataCalculator alarmDataCalculator;
	private GregorianCalendar calendar;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.alarmCalculationData = new AlarmCalculationData();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		this.alarmCalculationData = null;
		this.alarmDataCalculator = null;
	}

	private void setupCalendarWithLastBreakAgoInDays(int lastBreakAgoInDays, ContraceptiveType contraceptiveType) {

		this.calendar = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds();

		this.calendar.set(Calendar.DAY_OF_YEAR, this.calendar.get(Calendar.DAY_OF_YEAR) - lastBreakAgoInDays);

		this.alarmCalculationData.setLastBreak(CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(this.calendar));
		this.alarmCalculationData.setContraceptiveType(contraceptiveType);

		this.alarmCalculationData.setAlarmTime(new AlarmTime(20, 0));

		this.alarmDataCalculator = new AlarmDataCalculator(this.alarmCalculationData);
	}

	private void setupCalendarWithLastUseAgoInDays(int lastUseAgoInDays, ContraceptiveType contraceptiveType) {

		this.calendar = CalendarWrapperForTest.getActualCalendarWithoutHourMinutesSeconds();

		this.calendar.set(Calendar.DAY_OF_YEAR, this.calendar.get(Calendar.DAY_OF_YEAR) - lastUseAgoInDays);

		this.alarmCalculationData.setLastUseOfContraceptive(CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(this.calendar));
		this.alarmCalculationData.setContraceptiveType(contraceptiveType);

		this.alarmCalculationData.setAlarmTime(new AlarmTime(20, 0));

		this.alarmDataCalculator = new AlarmDataCalculator(this.alarmCalculationData);
	}

	private void setupAlarmData(ContraceptiveType contraceptiveType, CalendarWrapper lastUseOfContraceptive, CalendarWrapper lastBreak, boolean isAlarmActive) {
		this.alarmCalculationData.setContraceptiveType(contraceptiveType);
		this.alarmCalculationData.setLastUseOfContraceptive(lastUseOfContraceptive);
		this.alarmCalculationData.setLastBreak(lastBreak);
		this.alarmCalculationData.setAlarmTime(new AlarmTime(20, 0));
		this.alarmCalculationData.setAlarmActive(isAlarmActive);

		this.alarmDataCalculator = new AlarmDataCalculator(this.alarmCalculationData);
	}

	public void testNumberOfDaysSinceLastBreak_StartTimeAndLastBreakIs1DayAgo_Expected1() {
		this.setupCalendarWithLastBreakAgoInDays(1, ContraceptiveType.CONTRACEPTION_PILL);

		int numberOfDaysSinceLastBreak = this.alarmDataCalculator.getNumberOfDaysSinceLastBreak();

		Assert.assertEquals(1, numberOfDaysSinceLastBreak);
	}

	public void testNumberOfDaysSinceLastBreak_StartTimeAndLastBreakIs7DaysAgo_Expected7() {
		this.setupCalendarWithLastBreakAgoInDays(7, ContraceptiveType.CONTRACEPTION_PATCH);

		this.alarmCalculationData.setLastBreak(CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(this.calendar));

		int numberOfDaysSinceLastBreak = this.alarmDataCalculator.getNumberOfDaysSinceLastBreak();

		Assert.assertEquals(7, numberOfDaysSinceLastBreak);
	}

	public void testNumberOfDaysSinceLastBreak_StartTimeAndLastBreakIs21DaysAgo_Expected21() {
		this.setupCalendarWithLastBreakAgoInDays(21, ContraceptiveType.CONTRACEPTION_RING);

		int numberOfDaysSinceLastBreak = this.alarmDataCalculator.getNumberOfDaysSinceLastBreak();

		Assert.assertEquals(21, numberOfDaysSinceLastBreak);
	}

	public void testNumberOfDaysSinceLastUse_StartTimeAndLastUseIs1DayAgo_Expected1() {
		this.setupCalendarWithLastUseAgoInDays(1, ContraceptiveType.CONTRACEPTION_PILL);

		int numberOfDaysSinceLastBreak = this.alarmDataCalculator.getNumberOfDaysSinceLastUse();

		Assert.assertEquals(1, numberOfDaysSinceLastBreak);
	}

	public void testNumberOfDaysSinceLastUse_StartTimeAndLastUseIs7DaysAgo_Expected7() {
		this.setupCalendarWithLastUseAgoInDays(7, ContraceptiveType.CONTRACEPTION_PATCH);

		this.alarmCalculationData.setLastBreak(CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(this.calendar));

		int numberOfDaysSinceLastBreak = this.alarmDataCalculator.getNumberOfDaysSinceLastUse();

		Assert.assertEquals(7, numberOfDaysSinceLastBreak);
	}

	public void testNumberOfDaysSinceLastUse_StartTimeAndLastUseIs21DaysAgo_Expected21() {
		this.setupCalendarWithLastUseAgoInDays(21, ContraceptiveType.CONTRACEPTION_RING);

		int numberOfDaysSinceLastBreak = this.alarmDataCalculator.getNumberOfDaysSinceLastUse();

		Assert.assertEquals(21, numberOfDaysSinceLastBreak);
	}

	public void testNextAlarmEvent_LastUseWasLastSundayAndLastBreakWasLastSundayAndContraceptiveTypeIsRing_ExpectedMessageForRemoveRingAndCalendarDayIsNextSunday() {

		CalendarWrapper sundayThreeWeeksAgo = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getSundayThreeWeekAgo());

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_RING, sundayThreeWeeksAgo, sundayThreeWeeksAgo, true);

		GregorianCalendar nextBreakFromLastUse = sundayThreeWeeksAgo.convertCalendarWrapperIntoGregorianCalendar();
		nextBreakFromLastUse.add(Calendar.DAY_OF_YEAR, 28);
		nextBreakFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextBreakFromLastBreak = sundayThreeWeeksAgo.convertCalendarWrapperIntoGregorianCalendar();
		nextBreakFromLastBreak.add(Calendar.DAY_OF_YEAR, 28);
		nextBreakFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextBreakFromLastBreak, nextBreakFromLastUse);

		AlarmEventData alarmEventData = this.alarmDataCalculator.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getRingRemoveMessage(), alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextBreakFromLastBreak.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
		Assert.assertEquals(nextBreakFromLastUse.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
	}

	public void testNextAlarmEvent_LastUseWasLastSundayAndLastBreakSundayAWeekAgoAndContraceptiveTypeIsRing_ExpectedMessageForRemoveRingAndCalendarDayIsLastSundayIn21Days() {

		CalendarWrapper lastSunday = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getLastSunday());
		CalendarWrapper sundayWeekAgo = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getSundayOneWeekAgo());

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_RING, lastSunday, sundayWeekAgo, true);

		GregorianCalendar nextBreakFromLastUse = lastSunday.convertCalendarWrapperIntoGregorianCalendar();
		nextBreakFromLastUse.add(Calendar.DAY_OF_YEAR, 21);
		nextBreakFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextBreakFromLastBreak = sundayWeekAgo.convertCalendarWrapperIntoGregorianCalendar();
		nextBreakFromLastBreak.add(Calendar.DAY_OF_YEAR, 28);
		nextBreakFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextBreakFromLastBreak, nextBreakFromLastUse);

		AlarmEventData alarmEventData = this.alarmDataCalculator.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getRingRemoveMessage(), alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextBreakFromLastBreak.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
		Assert.assertEquals(nextBreakFromLastUse.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
	}

	public void testNextAlarmEvent_LastUseWasLastSundayTwoWeeksAgoAndLastBreakWasLastSundayThreeWeeksAgoAndContraceptiveTypeIsRing_ExpectedMessageForRemoveRingAndCalendarDayIsNextSunday() {

		CalendarWrapper sundayTwoWeeksAgo = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getSundayTwoWeeksAgo());
		CalendarWrapper sundayThreeWeekAgo = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getSundayThreeWeekAgo());

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_RING, sundayTwoWeeksAgo, sundayThreeWeekAgo, true);

		GregorianCalendar nextEventFromLastUse = sundayTwoWeeksAgo.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 21);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextEventFromLastBreak = sundayThreeWeekAgo.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastBreak.add(Calendar.DAY_OF_YEAR, 28);
		nextEventFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextEventFromLastUse, nextEventFromLastBreak);

		AlarmEventData alarmEventData = this.alarmDataCalculator.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getRingRemoveMessage(), alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
		Assert.assertEquals(nextEventFromLastBreak.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
	}

	public void testNextAlarmEvent_LastUseWasLastSundayThreeWeeksAgoAndLastBreakWasLastSundayAndContraceptiveTypeIsRing_ExpectedMessageForChangeRingAndCalendarDayIsNextSunday() {

		CalendarWrapper lastSunday = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getLastSunday());
		CalendarWrapper sundayThreeWeekAgo = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getSundayThreeWeekAgo());

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_RING, sundayThreeWeekAgo, lastSunday, true);

		GregorianCalendar nextEventFromLastUse = sundayThreeWeekAgo.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 28);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextEventFromLastBreak = lastSunday.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastBreak.add(Calendar.DAY_OF_YEAR, 7);
		nextEventFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextEventFromLastUse, nextEventFromLastBreak);

		AlarmEventData alarmEventData = this.alarmDataCalculator.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getRingChangeMessage(), alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
		Assert.assertEquals(nextEventFromLastBreak.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
	}

	public void testNextAlarmEvent_LastUseWasLastSundayAndLastBreakSundayAWeekAgoAndContraceptiveTypeIsPatch_ExpectedMessageForChangePatchAndCalendarDayIsNextSunday() {

		CalendarWrapper lastSunday = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getLastSunday());
		CalendarWrapper sundayWeekAgo = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getSundayOneWeekAgo());

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PATCH, lastSunday, sundayWeekAgo, true);

		GregorianCalendar nextBreakFromLastUse = lastSunday.convertCalendarWrapperIntoGregorianCalendar();
		nextBreakFromLastUse.add(Calendar.DAY_OF_YEAR, 7);
		nextBreakFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextBreakFromLastBreak = sundayWeekAgo.convertCalendarWrapperIntoGregorianCalendar();
		nextBreakFromLastBreak.add(Calendar.DAY_OF_YEAR, 14);
		nextBreakFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextBreakFromLastBreak, nextBreakFromLastUse);

		AlarmEventData alarmEventData = this.alarmDataCalculator.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPatchChangeMessage(1), alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextBreakFromLastBreak.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
		Assert.assertEquals(nextBreakFromLastUse.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
	}

	public void testNextAlarmEvent_LastUseWasLastSundayOneWeeksAgoAndLastBreakWasLastSundayAndContraceptiveTypeIsPatch_ExpectedMessageForChangePatchAndCalendarDayIsNextSunday() {

		CalendarWrapper lastSunday = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getLastSunday());
		CalendarWrapper sundayOneWeekAgo = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getSundayOneWeekAgo());

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PATCH, sundayOneWeekAgo, lastSunday, true);

		GregorianCalendar nextEventFromLastUse = sundayOneWeekAgo.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 14);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextEventFromLastBreak = lastSunday.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastBreak.add(Calendar.DAY_OF_YEAR, 7);
		nextEventFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextEventFromLastUse, nextEventFromLastBreak);

		AlarmEventData alarmEventData = this.alarmDataCalculator.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPatchChangeMessage(1), alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
		Assert.assertEquals(nextEventFromLastBreak.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
	}

	public void testLastSunday() {
		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();
		int lastSundayDayOfYear = lastSunday.get(Calendar.DAY_OF_YEAR);

		Assert.assertEquals(lastSundayDayOfYear, CalendarWrapperForTest.getLastSunday().get(Calendar.DAY_OF_YEAR));
	}

	public void testNextAlarmEvent_LastUseWasYesterdayAndLastBreakWasSundayAWeekAgoAndContraceptiveTypeIsPill_ExpectedMessageForChangePillAndCalendarDayIsToday() {

		CalendarWrapper yesterday = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getYesterday());
		CalendarWrapper sundayWeekAgo = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getSundayOneWeekAgo());

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PILL, yesterday, sundayWeekAgo, true);

		GregorianCalendar nextEventFromLastUse = yesterday.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 1);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);

		AlarmEventData alarmEventData = this.alarmDataCalculator.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPillChangeMessage(1), alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
	}

	public void testNextAlarmEvent_LastUseWasYesterdayAndLastBreakWasYesterDayAWeekAgoAndContraceptiveTypeIsPill_ExpectedMessageForChangePillAndCalendarDayIsToday() {

		CalendarWrapper yesterday = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getYesterday());
		CalendarWrapper sundayWeekAgo = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getYesterdayAWeekAgo());

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PILL, yesterday, sundayWeekAgo, true);

		GregorianCalendar nextEventFromLastUse = yesterday.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 1);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);

		AlarmEventData alarmEventData = this.alarmDataCalculator.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPillChangeMessage(1), alarmEventData.getAlarmMessage());
		Assert.assertEquals(EventType.EVENT_CHANGE, alarmEventData.getEventType());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
	}

	public void testNextAlarmEvent_LastUseWasLastSaturdayAndLastBreakWasLastSundayThreeWeeksAgoAndContraceptiveTypeIsPill_ExpectedMessageForRemovePillAndCalendarDayIsOneDayAfterLastUse() {

		CalendarWrapper lastSaturday = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getLastSaturday());
		CalendarWrapper sundayFourWeeksAgo = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getSundayFourWeekAgo());

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PILL, lastSaturday, sundayFourWeeksAgo, true);

		GregorianCalendar nextEventFromLastUse = lastSaturday.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 1);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextEventFromLastBreak = sundayFourWeeksAgo.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastBreak.add(Calendar.DAY_OF_YEAR, 28);
		nextEventFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextEventFromLastUse, nextEventFromLastBreak);

		AlarmEventData alarmEventData = this.alarmDataCalculator.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPillRemoveMessage(), alarmEventData.getAlarmMessage());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
		Assert.assertEquals(nextEventFromLastBreak.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
	}

	public void testNextAlarmEvent_LastUseWasLastSaturdayAndLastBreakWasLastSundayAndContraceptiveTypeIsPill_ExpectedMessageForChangePillAndCalendarDayIsNextSunday() {

		CalendarWrapper lastSaturday = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getLastSaturday());
		CalendarWrapper lastSunday = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getLastSunday());

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PILL, lastSaturday, lastSunday, true);

		GregorianCalendar nextEventFromLastUse = lastSaturday.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 8);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);

		GregorianCalendar nextEventFromLastBreak = lastSunday.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastBreak.add(Calendar.DAY_OF_YEAR, 7);
		nextEventFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(), nextEventFromLastBreak.getTimeInMillis());

		AlarmEventData alarmEventData = this.alarmDataCalculator.getNextAlarmEvent();

		Assert.assertEquals(AlarmMessage.getPillChangeMessage(1), alarmEventData.getAlarmMessage());
		Assert.assertEquals(EventType.EVENT_AFTER_BREAK, alarmEventData.getEventType());

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(alarmEventData.getAlarmTimeInMilliSeconds());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
		Assert.assertEquals(nextEventFromLastBreak.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
	}

	public void testNextAlarmEvent_LastUseWasTodayAndLastBreakWasTodayOneWeekAgoAndContraceptiveTypeIsPill_ExpectedMessageForChangePillAndCalendarDayIsTomorrow() {

		CalendarWrapper todayCalendar = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getToday());
		CalendarWrapper todayOneWeekAgoCalendar = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getTodayOneWeekAgo());

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PILL, todayCalendar, todayOneWeekAgoCalendar, true);

		GregorianCalendar nextEventFromLastUse = todayCalendar.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 1);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);

		GregorianCalendar nextEventFromLastBreak = todayOneWeekAgoCalendar.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastBreak.add(Calendar.DAY_OF_YEAR, 8);
		nextEventFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(), nextEventFromLastBreak.getTimeInMillis());

		AlarmEventData alarmEventData = this.alarmDataCalculator.getNextAlarmEvent();

		Assert.assertEquals(0, this.alarmDataCalculator.getNumberOfDaysSinceLastUse());

		Assert.assertEquals(AlarmMessage.getPillChangeMessage(1), alarmEventData.getAlarmMessage());
		Assert.assertEquals(EventType.EVENT_CHANGE, alarmEventData.getEventType());

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(alarmEventData.getAlarmTimeInMilliSeconds());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
		Assert.assertEquals(nextEventFromLastBreak.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
	}

	public void testNextAlarmEvent_LastUseWasTodayAndLastBreakWasTodayOneWeekAgoAndContraceptiveTypeIsPatch_ExpectedMessageForChangePatchAndCalendarDayIsInOneWeek() {

		CalendarWrapper todayCalendar = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getToday());
		CalendarWrapper todayOneWeekAgoCalendar = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getTodayOneWeekAgo());

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PATCH, todayCalendar, todayOneWeekAgoCalendar, true);

		GregorianCalendar nextEventFromLastUse = todayCalendar.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 7);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);

		GregorianCalendar nextEventFromLastBreak = todayOneWeekAgoCalendar.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastBreak.add(Calendar.DAY_OF_YEAR, 14);
		nextEventFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(), nextEventFromLastBreak.getTimeInMillis());

		AlarmEventData alarmEventData = this.alarmDataCalculator.getNextAlarmEvent();

		Assert.assertEquals(0, this.alarmDataCalculator.getNumberOfDaysSinceLastUse());

		Assert.assertEquals(AlarmMessage.getPatchChangeMessage(1), alarmEventData.getAlarmMessage());
		Assert.assertEquals(EventType.EVENT_CHANGE, alarmEventData.getEventType());

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(alarmEventData.getAlarmTimeInMilliSeconds());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
		Assert.assertEquals(nextEventFromLastBreak.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
	}

	public void testNextAlarmEvent_LastUseWasTodayAndLastBreakWasTodayOneWeekAgoAndContraceptiveTypeIsRing_ExpectedMessageForRemoveRingAndCalendarDayIsIn3Weeks() {

		CalendarWrapper todayCalendar = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getToday());
		CalendarWrapper todayOneWeekAgoCalendar = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(CalendarWrapperForTest.getTodayOneWeekAgo());

		this.setupAlarmData(ContraceptiveType.CONTRACEPTION_RING, todayCalendar, todayOneWeekAgoCalendar, true);

		GregorianCalendar nextEventFromLastUse = todayCalendar.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastUse.add(Calendar.DAY_OF_YEAR, 21);
		nextEventFromLastUse.set(Calendar.HOUR_OF_DAY, 20);

		GregorianCalendar nextEventFromLastBreak = todayOneWeekAgoCalendar.convertCalendarWrapperIntoGregorianCalendar();
		nextEventFromLastBreak.add(Calendar.DAY_OF_YEAR, 28);
		nextEventFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(), nextEventFromLastBreak.getTimeInMillis());

		AlarmEventData alarmEventData = this.alarmDataCalculator.getNextAlarmEvent();

		Assert.assertEquals(0, this.alarmDataCalculator.getNumberOfDaysSinceLastUse());

		Assert.assertEquals(AlarmMessage.getRingRemoveMessage(), alarmEventData.getAlarmMessage());
		Assert.assertEquals(EventType.EVENT_BREAK_ALARM, alarmEventData.getEventType());

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(alarmEventData.getAlarmTimeInMilliSeconds());

		Assert.assertEquals(nextEventFromLastUse.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
		Assert.assertEquals(nextEventFromLastBreak.getTimeInMillis(), alarmEventData.getAlarmTimeInMilliSeconds());
	}

	public void testLastSundayWeekAgo() {
		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();
		int lastSundayDayOfYear = lastSunday.get(Calendar.DAY_OF_YEAR);
		int lastSundayDayOfYearOneWeekAgo = lastSundayDayOfYear - 7;

		Assert.assertEquals(lastSundayDayOfYearOneWeekAgo, CalendarWrapperForTest.getSundayOneWeekAgo().get(Calendar.DAY_OF_YEAR));
	}
}