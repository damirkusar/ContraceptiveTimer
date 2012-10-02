package ch.kusar.contraceptivetimer.calculator;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ch.kusar.contraceptivetimer.businessobjects.AlarmEventData;
import ch.kusar.contraceptivetimer.businessobjects.AlarmMessage;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.businessobjects.EventType;
import ch.kusar.contraceptivetimer.wrapper.CalendarWrapper;

public class AlarmDataCalculator {

	private final AlarmCalculationData alarmData;

	public AlarmDataCalculator(AlarmCalculationData alarmData) {
		this.alarmData = alarmData;
	}

	public AlarmEventData getNextAlarmEvent() {
		if (this.isNextEventABreakAlarmEvent()) {
			return this.getNextBreakAlarmEvent();
		} else {
			if (this.getNumberOfDaysSinceLastBreak() <= 7) {
				return this.getNextAlarmEventAfterBreak();
			}
			return this.getNextChangeAlarmEvent();
		}
	}

	private boolean isNextEventABreakAlarmEvent() {
		int breakAlarmEventDataDayOfYear = this.alarmData.getLastBreak().get(
				Calendar.DAY_OF_YEAR);
		int changeAlarmEventDataDayOfYear = this.alarmData
				.getLastUseOfContraceptive().get(Calendar.DAY_OF_YEAR);

		if (breakAlarmEventDataDayOfYear == changeAlarmEventDataDayOfYear) {
			return true;
		}
		if (breakAlarmEventDataDayOfYear < changeAlarmEventDataDayOfYear
				&& this.alarmData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_RING) {
			return true;
		}
		if (this.getNumberOfDaysSinceLastBreak() >= 21) {
			return true;
		}

		return false;
	}

	public int getNumberOfDaysSinceLastBreak() {
		Calendar actulCalendar = CalendarWrapper
				.getActualCalendarWithoutHourMinutesSeconds();

		Calendar lastBreakCalendar = CalendarWrapper
				.convertToCalendarWithoutHourMinutesSeconds(this.alarmData
						.getLastBreak());

		return (int) ((actulCalendar.getTimeInMillis() - lastBreakCalendar
				.getTimeInMillis()) / this.alarmData.getMillisecondsinday());
	}

	private AlarmEventData getNextAlarmEventAfterBreak() {
		AlarmEventData alarmEventData = new AlarmEventData();

		if (this.alarmData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_RING) {
			alarmEventData.setAlarmMessage(AlarmMessage.getRingChangeMessage());
		}
		if (this.alarmData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PATCH) {
			alarmEventData
					.setAlarmMessage(AlarmMessage.getPatchChangeMessage());
		}
		if (this.alarmData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PILL) {
			alarmEventData.setAlarmMessage(AlarmMessage.getPillChangeMessage());
		}

		int nextAlarmDay = this.alarmData.getLastBreak().get(
				Calendar.DAY_OF_YEAR) + 7;

		alarmEventData.setEventType(EventType.EVENT_AFTER_BREAK);

		this.setAlarmEventData(alarmEventData, nextAlarmDay);

		return alarmEventData;
	}

	private AlarmEventData getNextBreakAlarmEvent() {
		AlarmEventData alarmEventData = new AlarmEventData();

		if (this.alarmData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_RING) {
			alarmEventData.setAlarmMessage(AlarmMessage.getRingRemoveMessage());
		}
		if (this.alarmData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PATCH) {
			alarmEventData
					.setAlarmMessage(AlarmMessage.getPatchRemoveMessage());
		}
		if (this.alarmData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PILL) {
			alarmEventData.setAlarmMessage(AlarmMessage.getPillRemoveMessage());
		}

		int nextAlarmDay = this.alarmData.getLastBreak().get(
				Calendar.DAY_OF_YEAR) + 28;

		this.setAlarmEventData(alarmEventData, nextAlarmDay);
		alarmEventData.setEventType(EventType.EVENT_BREAK_ALARM);

		return alarmEventData;
	}

	private AlarmEventData getNextChangeAlarmEvent() {
		AlarmEventData alarmEventData = new AlarmEventData();

		if (this.alarmData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_RING) {
			alarmEventData.setAlarmMessage(AlarmMessage.getRingChangeMessage());
		}
		if (this.alarmData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PATCH) {
			alarmEventData
					.setAlarmMessage(AlarmMessage.getPatchChangeMessage());
		}
		if (this.alarmData.getContraceptiveType() == ContraceptiveType.CONTRACEPTION_PILL) {
			alarmEventData.setAlarmMessage(AlarmMessage.getPillChangeMessage());
		}

		int nextAlarmDay = this.alarmData.getLastUseOfContraceptive().get(
				Calendar.DAY_OF_YEAR)
				+ this.alarmData.getIntervalDays();

		this.setAlarmEventData(alarmEventData, nextAlarmDay);
		alarmEventData.setEventType(EventType.EVENT_CHANGE);

		return alarmEventData;
	}

	private void setAlarmEventData(AlarmEventData alarmEventData,
			int nextAlarmDay) {
		GregorianCalendar calendar = CalendarWrapper
				.getGregorianCalendarInstance();

		calendar.set(Calendar.DAY_OF_YEAR, nextAlarmDay);
		calendar.set(Calendar.HOUR_OF_DAY,
				this.alarmData.getAlarmTime().get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,
				this.alarmData.getAlarmTime().get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		alarmEventData.setAlarm(calendar.getTimeInMillis());
	}
}
