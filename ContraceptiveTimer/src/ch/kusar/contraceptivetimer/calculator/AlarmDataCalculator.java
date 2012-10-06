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
		int breakAlarmEventDataDayOfYear = this.AlarmCalculationData.getLastBreakDayOfYear();
		int changeAlarmEventDataDayOfYear = this.AlarmCalculationData.getLastUseOfContraceptiveDayOfYear();

		if (breakAlarmEventDataDayOfYear == changeAlarmEventDataDayOfYear) {
			return true;
		}
		if (breakAlarmEventDataDayOfYear < changeAlarmEventDataDayOfYear
				&& this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_RING) {
			return true;
		}
		if (this.getNumberOfDaysSinceLastBreak() >= 21) {
			return true;
		}

		return false;
	}

	public int getNumberOfDaysSinceLastBreak() {
		int actualDayOfYear = CalendarWrapper.getTodayAsDayOfYear();
		int lastBreakAsDayOfYear = this.AlarmCalculationData.getLastBreakDayOfYear();

		return actualDayOfYear - lastBreakAsDayOfYear;
	}

	public int getNumberOfDaysSinceLastUse() {
		int actualDayOfYear = CalendarWrapper.getTodayAsDayOfYear();
		int lastUseAsDayOfYear = this.AlarmCalculationData.getLastUseOfContraceptiveDayOfYear();

		return actualDayOfYear - lastUseAsDayOfYear;
	}

	private AlarmEventData getNextAlarmEventAfterBreak() {
		AlarmEventData alarmEventData = new AlarmEventData();

		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_RING) {
			alarmEventData.setAlarmMessage(AlarmMessage.getRingChangeMessage());
		}
		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PATCH) {
			alarmEventData.setAlarmMessage(AlarmMessage.getPatchChangeMessage());
		}
		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PILL) {
			alarmEventData.setAlarmMessage(AlarmMessage.getPillChangeMessage());
		}

		int nextAlarmDay = this.AlarmCalculationData.getLastBreakDayOfYear() + 7;

		alarmEventData.setEventType(EventType.EVENT_AFTER_BREAK);

		this.setAlarmEventData(alarmEventData, nextAlarmDay);

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

		int nextAlarmDay = this.AlarmCalculationData.getLastBreakDayOfYear() + 28;

		this.setAlarmEventData(alarmEventData, nextAlarmDay);
		alarmEventData.setEventType(EventType.EVENT_BREAK_ALARM);

		return alarmEventData;
	}

	private AlarmEventData getNextChangeAlarmEvent() {
		AlarmEventData alarmEventData = new AlarmEventData();

		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_RING) {
			alarmEventData.setAlarmMessage(AlarmMessage.getRingChangeMessage());
		}
		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PATCH) {
			alarmEventData.setAlarmMessage(AlarmMessage.getPatchChangeMessage());
		}
		if (this.AlarmCalculationData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PILL) {
			alarmEventData.setAlarmMessage(AlarmMessage.getPillChangeMessage());
		}

		int nextAlarmDay = this.AlarmCalculationData.getLastUseOfContraceptiveDayOfYear() + this.AlarmCalculationData.getIntervalDays();

		this.setAlarmEventData(alarmEventData, nextAlarmDay);
		alarmEventData.setEventType(EventType.EVENT_CHANGE);

		return alarmEventData;
	}

	private void setAlarmEventData(AlarmEventData alarmEventData, int nextAlarmDay) {

		CalendarWrapper calendarWrapper = new CalendarWrapper();
		calendarWrapper.setDayOfYear(nextAlarmDay);
		calendarWrapper.setHourOfDay(this.AlarmCalculationData.getAlarmTimeHourOfDay());
		calendarWrapper.setMinutes(this.AlarmCalculationData.getAlarmTimeMinutes());

		alarmEventData.setAlarmTimeInMilliSeconds(calendarWrapper.getTimeInMillis());
	}
}