package ch.kusar.contraceptivetimer.test.retriever;

import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import ch.kusar.contraceptivetimer.businessobjects.AlarmEventData;
import ch.kusar.contraceptivetimer.businessobjects.AlarmMessage;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.calculator.AlarmCalculationData;
import ch.kusar.contraceptivetimer.calculator.AlarmDataCalculator;
import ch.kusar.contraceptivetimer.calculator.AlarmTime;
import ch.kusar.contraceptivetimer.retriever.AlarmEventRetriever;
import ch.kusar.contraceptivetimer.wrapper.CalendarWrapper;
import ch.kusar.contraceptivetimer.wrapper.InternalStorageWrapper;
import ch.kusar.test.calendarWrapper.CalendarWrapperForTest;

public class AlarmEventRetrieverTest extends AndroidTestCase {

	private AlarmEventRetriever alarmEventRetriever;
	private AlarmCalculationData alarmCalculationData;
	private InternalStorageWrapper internalStorageWrapper;

	@Override
	public void setUp() {
		this.alarmEventRetriever = new AlarmEventRetriever();
		this.alarmCalculationData = new AlarmCalculationData();
		this.internalStorageWrapper = new InternalStorageWrapper(this.getContext().getApplicationContext());
	}

	@Override
	public void tearDown() {
		this.alarmEventRetriever = null;
		this.alarmCalculationData = null;
		this.internalStorageWrapper = null;
	}

	public void testRetrieveAlarmEventData_AlarmCalculationDataFileIsAvailable_ShouldNotReturnNull() {
		this.internalStorageWrapper.saveToStorage(this.alarmCalculationData);
		AlarmEventData alarmEventData = this.alarmEventRetriever.retrieveAlarmEventData();

		Assert.assertNotNull(alarmEventData);
	}

	public void testRetrieveAlarmEventData_AlarmCalculationDataFileIsAvailableDefaultData_ShouldReturnExpectedData() {
		CalendarWrapper today = CalendarWrapperForTest.todayAsCalendarWrapper();
		CalendarWrapper todayAWeekAgo = CalendarWrapperForTest.todayAWeekAgoAsCalendarWrapper();

		AlarmCalculationData acd = this.setupAlarmData(ContraceptiveType.CONTRACEPTION_PILL, today, todayAWeekAgo);

		AlarmDataCalculator alarmDataCalculator = new AlarmDataCalculator(acd);
		AlarmEventData alarmEventDataFromCalculator = alarmDataCalculator.getNextAlarmEvent();

		this.internalStorageWrapper.saveToStorage(acd);

		AlarmEventData alarmEventDataFromRetriever = this.alarmEventRetriever.retrieveAlarmEventData();

		Assert.assertNotNull(alarmEventDataFromCalculator);
		Assert.assertNotNull(alarmEventDataFromRetriever);

		Assert.assertEquals(alarmEventDataFromCalculator.getAlarmMessage(), alarmEventDataFromRetriever.getAlarmMessage());
		Assert.assertEquals(alarmEventDataFromCalculator.getEventType(), alarmEventDataFromRetriever.getEventType());
		Assert.assertEquals(alarmEventDataFromCalculator.getAlarmTimeInMilliSeconds(), alarmEventDataFromRetriever.getAlarmTimeInMilliSeconds());
		Assert.assertEquals(AlarmMessage.getPillChangeMessage(1), alarmEventDataFromRetriever.getAlarmMessage());

		GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
		cal.setTimeInMillis(alarmEventDataFromRetriever.getAlarmTimeInMilliSeconds());

		Assert.assertEquals(20, cal.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(0, cal.get(Calendar.MINUTE));
	}

	public void testRetrieveAlarmEventData_AlarmCalculationDataFileIsAvailableWithValidData_ShouldReturnExpectedData() {
		GregorianCalendar lastSunday = CalendarWrapperForTest.getLastSunday();
		CalendarWrapper lastSundayAsCW = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(lastSunday);
		GregorianCalendar sundayWeekAgo = CalendarWrapperForTest.getSundayOneWeekAgo();
		CalendarWrapper sundayWeekAgoAsCW = CalendarWrapperForTest.convertGregorianCalendarToCalendarWrapper(sundayWeekAgo);

		GregorianCalendar nextBreakFromLastUse = (GregorianCalendar) lastSunday.clone();
		nextBreakFromLastUse.add(Calendar.DAY_OF_YEAR, 21);
		nextBreakFromLastUse.set(Calendar.HOUR_OF_DAY, 20);
		GregorianCalendar nextBreakFromLastBreak = (GregorianCalendar) sundayWeekAgo.clone();
		nextBreakFromLastBreak.add(Calendar.DAY_OF_YEAR, 28);
		nextBreakFromLastBreak.set(Calendar.HOUR_OF_DAY, 20);
		Assert.assertEquals(nextBreakFromLastBreak, nextBreakFromLastUse);

		AlarmCalculationData acd = this.setupAlarmData(ContraceptiveType.CONTRACEPTION_RING, lastSundayAsCW, sundayWeekAgoAsCW);

		AlarmDataCalculator alarmDataCalculator = new AlarmDataCalculator(acd);
		AlarmEventData alarmEventDataFromCalculator = alarmDataCalculator.getNextAlarmEvent();

		this.internalStorageWrapper.saveToStorage(acd);

		AlarmEventData alarmEventDataFromRetriever = this.alarmEventRetriever.retrieveAlarmEventData();

		Assert.assertNotNull(alarmEventDataFromCalculator);
		Assert.assertNotNull(alarmEventDataFromRetriever);

		Assert.assertEquals(alarmEventDataFromCalculator.getAlarmMessage(), alarmEventDataFromRetriever.getAlarmMessage());
		Assert.assertEquals(alarmEventDataFromCalculator.getEventType(), alarmEventDataFromRetriever.getEventType());
		Assert.assertEquals(alarmEventDataFromCalculator.getAlarmTimeInMilliSeconds(), alarmEventDataFromRetriever.getAlarmTimeInMilliSeconds());
		Assert.assertEquals(AlarmMessage.getRingRemoveMessage(), alarmEventDataFromRetriever.getAlarmMessage());

		GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
		cal.setTimeInMillis(alarmEventDataFromRetriever.getAlarmTimeInMilliSeconds());

		Assert.assertEquals(nextBreakFromLastUse.get(Calendar.DAY_OF_YEAR), cal.get(Calendar.DAY_OF_YEAR));
		Assert.assertEquals(20, cal.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(0, cal.get(Calendar.MINUTE));
	}

	private AlarmCalculationData setupAlarmData(ContraceptiveType contraceptiveType, CalendarWrapper lastUseOfContraceptiveCalendar,
			CalendarWrapper lastBreakDayOfYearCalendar) {

		AlarmCalculationData acd = new AlarmCalculationData();

		acd.setContraceptiveType(contraceptiveType);
		acd.setLastUseOfContraceptive(lastUseOfContraceptiveCalendar);
		acd.setLastBreak(lastBreakDayOfYearCalendar);
		acd.setAlarmTime(new AlarmTime(20, 0));
		acd.setAlarmActive(true);

		return acd;
	}

}
