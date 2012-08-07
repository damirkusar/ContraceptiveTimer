package ch.kusar.contraceptivetimer.businessobjects;

import java.util.Calendar;

import ch.kusar.calendarWrapper.CalendarWrapper;

public class AlarmDataCalculator {

	private AlarmData alarmData;

	public AlarmDataCalculator(AlarmData alarmData) {
		this.setAlarmData(alarmData);
	}

	public AlarmData getAlarmData() {
		return this.alarmData;
	}

	public void setAlarmData(AlarmData alarmData) {
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

	public int getNumberOfDaysSinceLastBreak() {
		Calendar actulCalendar = CalendarWrapper.getActualCalendar();

		Calendar lastBreakCalendar = this.alarmData.getLastBreak();

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

		alarmEventData.setAlarm(this.alarmData.getLastBreak());

		alarmEventData.getAlarm().set(Calendar.HOUR_OF_DAY,
				this.alarmData.getAlarmTime().get(Calendar.HOUR_OF_DAY));
		alarmEventData.getAlarm().set(Calendar.MINUTE,
				this.alarmData.getAlarmTime().get(Calendar.MINUTE));

		alarmEventData.getAlarm().set(Calendar.DAY_OF_YEAR,
				this.alarmData.getAlarmTime().get(Calendar.DAY_OF_YEAR) + 7);

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

		alarmEventData.setAlarm(CalendarWrapper
				.getActualCalendarWithoutHourMinutesSeconds());

		alarmEventData.getAlarm().set(Calendar.DAY_OF_YEAR,
				this.alarmData.getAlarmTime().get(Calendar.DAY_OF_YEAR) + 28);

		alarmEventData.getAlarm().set(Calendar.HOUR_OF_DAY,
				this.alarmData.getAlarmTime().get(Calendar.HOUR_OF_DAY));
		alarmEventData.getAlarm().set(Calendar.MINUTE,
				this.alarmData.getAlarmTime().get(Calendar.MINUTE));
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

		alarmEventData.setAlarm(this.alarmData.getLastUseOfContraceptive());

		alarmEventData.getAlarm().set(Calendar.HOUR_OF_DAY,
				this.alarmData.getAlarmTime().get(Calendar.HOUR_OF_DAY));
		alarmEventData.getAlarm().set(Calendar.MINUTE,
				this.alarmData.getAlarmTime().get(Calendar.MINUTE));

		alarmEventData.getAlarm().set(
				Calendar.DAY_OF_YEAR,
				this.alarmData.getAlarmTime().get(Calendar.DAY_OF_YEAR)
						+ this.alarmData.getIntervalDays());

		return alarmEventData;
	}

	private boolean isNextEventABreakAlarmEvent() {
		int breakAlarmEventDataDayOfYear = this.getNextBreakAlarmEvent()
				.getAlarm().get(Calendar.DAY_OF_YEAR);
		int changeAlarmEventDataDayOfYear = this.getNextChangeAlarmEvent()
				.getAlarm().get(Calendar.DAY_OF_YEAR);

		if (breakAlarmEventDataDayOfYear == changeAlarmEventDataDayOfYear) {
			return true;
		}
		if (breakAlarmEventDataDayOfYear < changeAlarmEventDataDayOfYear) {
			return true;
		}

		return false;
	}
}
