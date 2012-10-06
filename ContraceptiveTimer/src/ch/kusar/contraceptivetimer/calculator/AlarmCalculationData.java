package ch.kusar.contraceptivetimer.calculator;

import java.io.Serializable;

import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;

public class AlarmCalculationData implements Serializable {

	private static final long serialVersionUID = 5921434769798781785L;
	private final int numberOfDaysToMakeSevenDaysBreakAfterBreak = 28;
	private final int numberOfDaysToMakeSevenDaysBreakAfterFirstContraceptive = 21;

	private ContraceptiveType contraceptiveType;
	private int lastUseOfContraceptiveDayOfYear;
	private int lastBreakDayOfYear;
	private AlarmTime alarmTime;
	private boolean isAlarmActive;

	public AlarmCalculationData() {
		super();
		this.alarmTime = new AlarmTime();
	}

	public void setAlarmTimeHourOfDay(int hourOfDay) {
		this.alarmTime.setHourOfDay(hourOfDay);
	}

	public int getAlarmTimeHourOfDay() {
		return this.alarmTime.getHourOfDay();
	}

	public void setAlarmTimeMinuteOfDay(int minuteOfDay) {
		this.alarmTime.setMinuteOfDay(minuteOfDay);
	}

	public int getAlarmTimeMinutes() {
		return this.alarmTime.getMinutes();
	}

	public ContraceptiveType getContraceptiveType() {
		return this.contraceptiveType;
	}

	public void setContraceptiveType(ContraceptiveType contraceptiveType) {
		this.contraceptiveType = contraceptiveType;
	}

	public int getLastBreakDayOfYear() {
		return this.lastBreakDayOfYear;
	}

	public void setLastBreakDayOfYear(int lastBreak) {
		this.lastBreakDayOfYear = lastBreak;
	}

	public AlarmTime getAlarmTime() {
		return this.alarmTime;
	}

	public int getNumberOfDaysToMakeSevenDaysBreakAfterBreak() {
		return this.numberOfDaysToMakeSevenDaysBreakAfterBreak;
	}

	public void setAlarmTime(AlarmTime alarmTime) {
		this.alarmTime = alarmTime;
	}

	public int getIntervalDays() {
		return this.contraceptiveType.getMask();
	}

	public boolean isAlarmActive() {
		return this.isAlarmActive;
	}

	public void setAlarmActive(boolean isAlarmActive) {
		this.isAlarmActive = isAlarmActive;
	}

	public int getNumberOfDaysToMakeSevenDaysBreakAfterFirstContraceptive() {
		return this.numberOfDaysToMakeSevenDaysBreakAfterFirstContraceptive;
	}

	public int getLastUseOfContraceptiveDayOfYear() {
		return this.lastUseOfContraceptiveDayOfYear;
	}

	public void setLastUseOfContraceptiveDayOfYear(int lastUseOfContraceptive) {
		this.lastUseOfContraceptiveDayOfYear = lastUseOfContraceptive;
	}

	@Override
	public String toString() {
		String msg = String
				.format("Alarm is Active: %1$s, Alarm hour is: %2$s, Alarm minute is: %3$s, Contraceptive is: %4$s, Contraceptive Interval is: %5$s,Last use was: %6$s, Last break was: %7$s.",
						this.isAlarmActive(), this.getAlarmTimeHourOfDay(), this.getAlarmTimeMinutes(), this.getContraceptiveType(), this.getIntervalDays(),
						this.getLastUseOfContraceptiveDayOfYear(), this.getLastBreakDayOfYear());
		return msg;
	}
}
