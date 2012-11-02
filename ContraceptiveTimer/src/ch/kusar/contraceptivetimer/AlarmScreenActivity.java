package ch.kusar.contraceptivetimer;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import ch.kusar.contraceptivetimer.businessobjects.AlarmEventData;
import ch.kusar.contraceptivetimer.businessobjects.AlarmMessage;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.businessobjects.EventType;
import ch.kusar.contraceptivetimer.calculator.AlarmCalculationData;
import ch.kusar.contraceptivetimer.calculator.AlarmTime;
import ch.kusar.contraceptivetimer.dialog.CancelDialog;
import ch.kusar.contraceptivetimer.dialog.CancelDialogListener;
import ch.kusar.contraceptivetimer.dialog.StartDialog;
import ch.kusar.contraceptivetimer.dialog.StartDialogListener;
import ch.kusar.contraceptivetimer.wrapper.AlarmManagerWrapper;
import ch.kusar.contraceptivetimer.wrapper.CalendarWrapper;
import ch.kusar.contraceptivetimer.wrapper.InternalStorageWrapper;
import ch.kusar.contraceptivetimer.wrapper.LoggerWrapper;

//import android.view.MenuItem;

public class AlarmScreenActivity extends FragmentActivity implements CancelDialogListener, StartDialogListener {

	private InternalStorageWrapper internalStorageWrapper;
	private ContraceptiveType contraceptiveType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_alarmscreen);

		this.internalStorageWrapper = new InternalStorageWrapper(MainApplicationContext.getAppContext());

		this.setupViews();
	}

	private void setupViews() {
		TimePicker tp = this.getTimePicker();
		tp.setIs24HourView(true);

		AlarmCalculationData acd = this.internalStorageWrapper.loadAlarmCalculationDataFromStorage();
		if (acd != null) {
			LoggerWrapper.LogDebug(acd.toString());
			tp.setCurrentHour(acd.getAlarmTimeHourOfDay());
			tp.setCurrentMinute(acd.getAlarmTimeMinutes());

			if (acd.isAlarmActive()) {
				this.setupButtonsOnStart();
			} else {
				this.setStartButtonEnabled(true);
				this.setStopButtonEnabled(false);
				this.setUpdateAlarmTimeEnabled(false);
				this.setToggleButtonEnabled(true);
			}

			this.turnToggleButtonOn(acd.getContraceptiveType());
		} else {
			this.turnToggleButtonOn(ContraceptiveType.CONTRACEPTION_PILL);
		}
	}

	private TimePicker getTimePicker() {
		return (TimePicker) this.findViewById(R.id.timePicker);
	}

	private void setStartButtonEnabled(boolean enabled) {
		Button button = (Button) this.findViewById(R.id.button_Start);
		button.setEnabled(enabled);
	}

	private void setStopButtonEnabled(boolean enabled) {
		Button button = (Button) this.findViewById(R.id.button_Stop);
		button.setEnabled(enabled);
	}

	private void setUpdateAlarmTimeEnabled(boolean enabled) {
		Button button = (Button) this.findViewById(R.id.buttonUpdateAlarmTime);
		button.setEnabled(enabled);
	}

	private void turnToggleButtonOn(ContraceptiveType contraceptiveType) {

		ToggleButton tbPill = (ToggleButton) this.findViewById(R.id.toggleButton_Pill);
		ToggleButton tbPatch = (ToggleButton) this.findViewById(R.id.toggleButton_Patch);
		ToggleButton tbRing = (ToggleButton) this.findViewById(R.id.toggleButton_Ring);

		switch (contraceptiveType) {
		case CONTRACEPTION_PILL:
			tbPill.setChecked(true);
			this.contraceptiveType = ContraceptiveType.CONTRACEPTION_PILL;
			break;
		case CONTRACEPTION_PATCH:
			tbPatch.setChecked(true);
			this.contraceptiveType = ContraceptiveType.CONTRACEPTION_PATCH;
			break;
		case CONTRACEPTION_RING:
			this.contraceptiveType = ContraceptiveType.CONTRACEPTION_RING;
			tbRing.setChecked(true);
			break;
		}
	}

	public void onToggleButtonPillClicked(View view) {
		boolean on = ((ToggleButton) view).isChecked();
		if (on) {
			this.contraceptiveType = ContraceptiveType.CONTRACEPTION_PILL;
			this.turnToggleButtonOff(false, true, true);
		}
	}

	public void onToggleButtonPatchClicked(View view) {
		boolean on = ((ToggleButton) view).isChecked();
		if (on) {
			this.contraceptiveType = ContraceptiveType.CONTRACEPTION_PATCH;
			this.turnToggleButtonOff(true, false, true);
		}
	}

	public void onToggleButtonRingClicked(View view) {
		boolean on = ((ToggleButton) view).isChecked();
		if (on) {
			this.contraceptiveType = ContraceptiveType.CONTRACEPTION_RING;
			this.turnToggleButtonOff(true, true, false);
		}
	}

	private void turnToggleButtonOff(boolean pill, boolean patch, boolean ring) {

		ToggleButton tbPill = (ToggleButton) this.findViewById(R.id.toggleButton_Pill);
		ToggleButton tbPatch = (ToggleButton) this.findViewById(R.id.toggleButton_Patch);
		ToggleButton tbRing = (ToggleButton) this.findViewById(R.id.toggleButton_Ring);

		if (pill) {
			tbPill.setChecked(false);
		}
		if (patch) {
			tbPatch.setChecked(false);
		}
		if (ring) {
			tbRing.setChecked(false);
		}
	}

	public void onButtonUpdateAlarmTimeClicked(View view) {
		TimePicker tp = this.getTimePicker();

		AlarmTime alarmTime = new AlarmTime();
		alarmTime.setHourOfDay(tp.getCurrentHour());
		alarmTime.setMinuteOfDay(tp.getCurrentMinute());

		this.internalStorageWrapper.saveUpdatedAlarmTime(alarmTime);

		this.restartAlarm();
	}

	private void restartAlarm() {
		AlarmManagerWrapper alarmManagerWrapper = new AlarmManagerWrapper();
		alarmManagerWrapper.CancelAlarm();
		alarmManagerWrapper.SetupAlarm();
	}

	private void cancelAlarm() {
		AlarmManagerWrapper alarmManagerWrapper = new AlarmManagerWrapper();
		alarmManagerWrapper.CancelAlarm();
	}

	private void setToggleButtonEnabled(boolean enabled) {
		ToggleButton tbPill = (ToggleButton) this.findViewById(R.id.toggleButton_Pill);
		tbPill.setEnabled(enabled);
		ToggleButton tbPatch = (ToggleButton) this.findViewById(R.id.toggleButton_Patch);
		tbPatch.setEnabled(enabled);
		ToggleButton tbRing = (ToggleButton) this.findViewById(R.id.toggleButton_Ring);
		tbRing.setEnabled(enabled);
	}

	public void onButtonStartClicked(View view) {
		DialogFragment dialog = StartDialog.newInstance(this);
		dialog.show(this.getSupportFragmentManager(), "StartDialog");
	}

	public void onButtonStopClicked(View view) {
		DialogFragment dialog = CancelDialog.newInstance(this);
		dialog.show(this.getSupportFragmentManager(), "CancelDialog");
	}

	public void onCancelDialogYesClick(DialogFragment dialog) {
		this.cancelAlarm();
		this.internalStorageWrapper.saveUpdatedAlarmActivatedTo(false);
		this.internalStorageWrapper.saveUpdatedResetedUsedTimes();

		this.setStartButtonEnabled(true);
		this.setStopButtonEnabled(false);
		this.setUpdateAlarmTimeEnabled(false);
		this.setToggleButtonEnabled(true);
	}

	public void onCancelDialogNoClick(DialogFragment dialog) {
		dialog.dismiss();
	}

	public void onStartDialogNowClick(DialogFragment dialog) {
		this.setupAlarmCalculationData(CalendarWrapper.getToday());
		this.setupButtonsOnStart();
		this.restartAlarm();
	}

	private void setupButtonsOnStart() {
		this.setStartButtonEnabled(false);
		this.setStopButtonEnabled(true);
		this.setUpdateAlarmTimeEnabled(true);
		this.setToggleButtonEnabled(false);
	}

	public void onStartDialogStartOnSpecificDateClick(DialogFragment dialog) {
		DatePicker dp = (DatePicker) dialog.getDialog().findViewById((R.id.datePicker));
		CalendarWrapper calendarWrapper = new CalendarWrapper(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());

		this.setupAlarmCalculationData(calendarWrapper);
		this.setupButtonsOnStart();
		this.restartAlarm();
	}

	private void setupAlarmCalculationData(CalendarWrapper firstUseWasOn) {
		TimePicker tp = this.getTimePicker();
		tp.clearFocus();
		int hours = tp.getCurrentHour();
		int minutes = tp.getCurrentMinute();

		AlarmCalculationData acd = new AlarmCalculationData();
		acd.setAlarmActive(true);
		acd.setAlarmTimeHourOfDay(hours);
		acd.setAlarmTimeMinuteOfDay(minutes);
		acd.setContraceptiveType(this.contraceptiveType);
		acd.setFirstUseWasOn(firstUseWasOn);

		this.internalStorageWrapper.saveToStorage(acd);

		this.setTodaysAlarm(firstUseWasOn, hours, minutes);

		LoggerWrapper.LogError(acd.toString());
	}

	private void setTodaysAlarm(CalendarWrapper firstUseWasOn, int hours, int minutes) {
		if (firstUseWasOn.toString().equals(CalendarWrapper.getToday().toString())) {
			AlarmManagerWrapper alarmManagerWrapper = new AlarmManagerWrapper();
			AlarmEventData aed = new AlarmEventData();
			aed.setAlarmMessage(this.getCorrectMessage());
			aed.setEventType(EventType.EVENT_AFTER_BREAK);

			CalendarWrapper cal = CalendarWrapper.getToday();
			cal.setHourOfDay(hours);
			cal.setMinutes(minutes);
			aed.setAlarmTimeInMilliSeconds(CalendarWrapper.getNowAsMilliseconds());

			alarmManagerWrapper.setAlarm(MainApplicationContext.getAppContext(), aed);
			Toast.makeText(this, "Today: " + firstUseWasOn.toString(), Toast.LENGTH_LONG).show();
		}
	}

	public String getCorrectMessage() {
		if (this.contraceptiveType == ContraceptiveType.CONTRACEPTION_PATCH) {
			return AlarmMessage.getPatchChangeMessage(1);
		} else if (this.contraceptiveType == ContraceptiveType.CONTRACEPTION_RING) {
			return AlarmMessage.getRingChangeMessage();
		} else if (this.contraceptiveType == ContraceptiveType.CONTRACEPTION_PILL) {
			return AlarmMessage.getPillChangeMessage(1);
		}
		return "";
	}
}
