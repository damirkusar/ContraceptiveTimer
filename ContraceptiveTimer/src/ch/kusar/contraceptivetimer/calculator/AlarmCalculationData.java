package ch.kusar.contraceptivetimer.calculator;

import java.io.Serializable;

import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.wrapper.CalendarWrapper;

public class AlarmCalculationData implements Serializable {

	private static final long serialVersionUID = 5921434769798781785L;
	private final int numberOfDaysToMakeSevenDaysBreakAfterBreak = 28;
	private final int numberOfDaysToMakeSevenDaysBreakAfterFirstContraceptive = 21;

	private ContraceptiveType contraceptiveType;
	private CalendarWrapper lastUseOfContraceptive;
	private CalendarWrapper lastBreak;
	private AlarmTime alarmTime;
	private boolean isAlarmActive;

	private int timesUsed;

	public AlarmCalculationData() {
		super();
		this.alarmTime = new AlarmTime();
		this.timesUsed = 0;
		this.lastUseOfContraceptive = new CalendarWrapper();
		this.lastBreak = new CalendarWrapper();
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

	public CalendarWrapper getLastBreak() {
		return this.lastBreak;
	}

	public void setLastBreak(CalendarWrapper lastBreak) {
		this.lastBreak = lastBreak;
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

	public int getNextNumberOfUsedContraception() {
		return this.timesUsed + 1;
	}

	public void incrementTimesUsed() {
		this.timesUsed++;
	}

	public void resetTimesUsed() {
		this.timesUsed = 0;
	}

	public int getTimesUsed() {
		return this.timesUsed;
	}

	public int getNumberOfDaysToMakeSevenDaysBreakAfterFirstContraceptive() {
		return this.numberOfDaysToMakeSevenDaysBreakAfterFirstContraceptive;
	}

	public CalendarWrapper getLastUseOfContraceptive() {
		return this.lastUseOfContraceptive;
	}

	public void setLastUseOfContraceptive(CalendarWrapper lastUseOfContraceptive) {
		this.lastUseOfContraceptive = lastUseOfContraceptive;
	}

	@Override
	public String toString() {
		String msg = String
				.format("Alarm is Active: %1$s, Alarm hour is: %2$s, Alarm minute is: %3$s, Contraceptive is: %4$s, Contraceptive Interval is: %5$s,Last use was: %6$s, Last break was: %7$s, Times used is: %8$s",
						this.isAlarmActive(), this.getAlarmTimeHourOfDay(), this.getAlarmTimeMinutes(), this.getContraceptiveType(), this.getIntervalDays(),
						this.getLastUseOfContraceptive(), this.getLastBreak(), this.timesUsed);
		return msg;
	}
}
