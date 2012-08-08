package ch.kusar.contraceptivetimer.businessobjects;

import java.io.Serializable;
import java.util.Calendar;

public class AlarmData implements Serializable {

	private static final long serialVersionUID = 5921434769798781785L;
	private final int millisecondsInDay = 1000 * 60 * 60 * 24;
	private final int numberOfDaysToMakeSevenDaysBreakAfterBreak = 28;
	private final int numberOfDaysToMakeSevenDaysBreakAfterFirstContraceptive = 21;

	private ContraceptiveType contraceptiveType;

	private Calendar lastUseOfContraceptive;
	private Calendar lastBreak;

	private Calendar alarmTime;

	private boolean isAlarmActive;

	public AlarmData() {
		super();
	}

	public AlarmData(ContraceptiveType contraceptiveType, Calendar alarmTime) {
		super();
		this.contraceptiveType = contraceptiveType;
		this.alarmTime = alarmTime;
	}

	public ContraceptiveType getContraceptiveType() {
		return this.contraceptiveType;
	}

	public void setContraceptiveType(ContraceptiveType contraceptiveType) {
		this.contraceptiveType = contraceptiveType;
	}

	public Calendar getLastBreak() {
		return this.lastBreak;
	}

	public void setLastBreak(Calendar lastBreak) {
		this.lastBreak = lastBreak;
	}

	public Calendar getAlarmTime() {
		return this.alarmTime;
	}

	public int getNumberOfDaysToMakeSevenDaysBreakAfterBreak() {
		return this.numberOfDaysToMakeSevenDaysBreakAfterBreak;
	}

	public void setAlarmTime(Calendar alarmTime) {
		this.alarmTime = alarmTime;
	}

	public int getIntervalDays() {
		return this.contraceptiveType.getMask();
	}

	public int getMillisecondsinday() {
		return this.millisecondsInDay;
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

	public Calendar getLastUseOfContraceptive() {
		return this.lastUseOfContraceptive;
	}

	public void setLastUseOfContraceptive(Calendar lastUseOfContraceptive) {
		this.lastUseOfContraceptive = lastUseOfContraceptive;
	}
}
