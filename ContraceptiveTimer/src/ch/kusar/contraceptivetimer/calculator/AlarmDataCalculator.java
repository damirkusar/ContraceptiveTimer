package ch.kusar.contraceptivetimer.calculator;

import ch.kusar.contraceptivetimer.businessobjects.AlarmEventData;
import ch.kusar.contraceptivetimer.businessobjects.AlarmMessage;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.businessobjects.EventType;
import ch.kusar.contraceptivetimer.wrapper.CalendarWrapper;

public class AlarmDataCalculator {

	private final AlarmCalculationData AlarmCalculationData;

	public AlarmDataCalculator(AlarmCalculationData alarmData) {
		this.AlarmCalculationData = alarmData;
	}

	public AlarmEventData getNextAlarmEvent() {
		if (this.isNextEventABreakAlarmEvent()) {
			return this.getNextBreakAlarmEvent();
		} else {
			if ((this.getNumberOfDaysSinceLastBreak() <= 7) && !(this.getNumberOfDaysSinceLastUse() == 0)) {
				return this.getNextAlarmEventAfterBreak();
			}
			return this.getNextChangeAlarmEvent();
		}
	}

	private boolean isNextEventABreakAlarmEvent() {
		CalendarWrapper lb = this.AlarmCalculationData.getLastBreak();
		CalendarWrapper lu = this.AlarmCalculationData.getLastUseOfContraceptive();

		if (lb.toString().equals(lu.toString())) {
			return true;
		}
		if ((lb.getTimeInMillis() < lu.getTimeInMillis() && this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_RING)) {
			return true;
		}
		if ((this.getNumberOfDaysSinceLastBreak() > 21 && this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PATCH)) {
			return true;
		}
		if (this.getNumberOfDaysSinceLastBreak() >= 28) {
			return true;
		}

		return false;
	}

	public int getNumberOfDaysSinceLastBreak() {
		return CalendarWrapper.daysBetweenTodayAnd(this.AlarmCalculationData.getLastBreak());
	}

	public int getNumberOfDaysSinceLastUse() {
		return CalendarWrapper.daysBetweenTodayAnd(this.AlarmCalculationData.getLastUseOfContraceptive());
	}

	private AlarmEventData getNextAlarmEventAfterBreak() {
		AlarmEventData alarmEventData = new AlarmEventData();

		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_RING) {
			alarmEventData.setAlarmMessage(AlarmMessage.getRingChangeMessage());
		}
		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PATCH) {
			alarmEventData.setAlarmMessage(AlarmMessage.getPatchChangeMessage(this.AlarmCalculationData.getNextNumberOfUsedContraception()));
		}
		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PILL) {
			alarmEventData.setAlarmMessage(AlarmMessage.getPillChangeMessage(this.AlarmCalculationData.getNextNumberOfUsedContraception()));
		}

		CalendarWrapper lastBreak = this.AlarmCalculationData.getLastBreak();
		CalendarWrapper nextAlarm = new CalendarWrapper(lastBreak.getYear(), lastBreak.getMonth(), lastBreak.getDayOfMonth());
		nextAlarm.addDays(7);

		alarmEventData.setEventType(EventType.EVENT_AFTER_BREAK);

		this.setAlarmEventData(alarmEventData, nextAlarm);

		return alarmEventData;
	}

	private AlarmEventData getNextBreakAlarmEvent() {
		AlarmEventData alarmEventData = new AlarmEventData();

		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_RING) {
			alarmEventData.setAlarmMessage(AlarmMessage.getRingRemoveMessage());
		}
		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PATCH) {
			alarmEventData.setAlarmMessage(AlarmMessage.getPatchRemoveMessage());
		}
		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PILL) {
			alarmEventData.setAlarmMessage(AlarmMessage.getPillRemoveMessage());
		}

		CalendarWrapper lastBreak = this.AlarmCalculationData.getLastBreak();
		CalendarWrapper nextAlarm = new CalendarWrapper(lastBreak.getYear(), lastBreak.getMonth(), lastBreak.getDayOfMonth());
		nextAlarm.addDays(28);

		this.setAlarmEventData(alarmEventData, nextAlarm);
		alarmEventData.setEventType(EventType.EVENT_BREAK_ALARM);

		return alarmEventData;
	}

	private AlarmEventData getNextChangeAlarmEvent() {
		AlarmEventData alarmEventData = new AlarmEventData();

		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_RING) {
			alarmEventData.setAlarmMessage(AlarmMessage.getRingChangeMessage());
		}
		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PATCH) {
			alarmEventData.setAlarmMessage(AlarmMessage.getPatchChangeMessage(this.AlarmCalculationData.getNextNumberOfUsedContraception()));
		}
		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PILL) {
			alarmEventData.setAlarmMessage(AlarmMessage.getPillChangeMessage(this.AlarmCalculationData.getNextNumberOfUsedContraception()));
		}

		CalendarWrapper lastUseOfContraceptive = this.AlarmCalculationData.getLastUseOfContraceptive();
		CalendarWrapper nextAlarm = new CalendarWrapper(lastUseOfContraceptive.getYear(), lastUseOfContraceptive.getMonth(),
				lastUseOfContraceptive.getDayOfMonth());
		nextAlarm.addDays(this.AlarmCalculationData.getIntervalDays());

		this.setAlarmEventData(alarmEventData, nextAlarm);
		alarmEventData.setEventType(EventType.EVENT_CHANGE);

		return alarmEventData;
	}

	private void setAlarmEventData(AlarmEventData alarmEventData, CalendarWrapper cal) {

		CalendarWrapper calendarWrapper = new CalendarWrapper();
		calendarWrapper.setYear(cal.getYear());
		calendarWrapper.setMonth(cal.getMonth());
		calendarWrapper.setDayOfMonth(cal.getDayOfMonth());
		calendarWrapper.setHourOfDay(this.AlarmCalculationData.getAlarmTimeHourOfDay());
		calendarWrapper.setMinutes(this.AlarmCalculationData.getAlarmTimeMinutes());

		alarmEventData.setAlarmTimeInMilliSeconds(calendarWrapper.getTimeInMillis());
	}
}
