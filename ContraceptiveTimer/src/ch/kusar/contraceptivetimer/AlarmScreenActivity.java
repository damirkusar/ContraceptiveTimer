package ch.kusar.contraceptivetimer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.calculator.AlarmCalculationData;
import ch.kusar.contraceptivetimer.calculator.AlarmTime;
import ch.kusar.contraceptivetimer.dialog.CancelDialog;
import ch.kusar.contraceptivetimer.dialog.CancelDialogListener;
import ch.kusar.contraceptivetimer.wrapper.AlarmManagerWrapper;
import ch.kusar.contraceptivetimer.wrapper.CalendarWrapper;
import ch.kusar.contraceptivetimer.wrapper.InternalStorageWrapper;
import ch.kusar.contraceptivetimer.wrapper.LoggerWrapper;

//import android.view.MenuItem;

public class AlarmScreenActivity extends FragmentActivity implements CancelDialogListener {

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

		AlarmCalculationData acd = this.internalStorageWrapper.loadFromStorage();
		if (acd != null) {
			LoggerWrapper.LogDebug(acd.toString());
			tp.clearFocus();
			tp.setCurrentHour(acd.getAlarmTimeHourOfDay());
			tp.setCurrentMinute(acd.getAlarmTimeMinutes());

			if (acd.isAlarmActive()) {
				this.setStartButtonEnabled(false);
				this.setStopButtonEnabled(true);
			} else {
				this.setStartButtonEnabled(true);
				this.setStopButtonEnabled(false);
			}

			this.turnToggleButtonOn(acd.getContraceptiveType());
		} else {
			this.turnToggleButtonOn(ContraceptiveType.CONTRACEPTION_PILL);
		}

		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			this.setToggleButtonSizeInLandscapeToSizeOfTextView();
		}
	}

	private void setToggleButtonSizeInLandscapeToSizeOfTextView() {
		TextView tv = (TextView) this.findViewById(R.id.textView_TypeChooser);
		int width = tv.getWidth();

		ToggleButton tbPill = (ToggleButton) this.findViewById(R.id.toggleButton_Pill);
		tbPill.setWidth(width);
		ToggleButton tbPatch = (ToggleButton) this.findViewById(R.id.toggleButton_Patch);
		tbPatch.setWidth(width);
		ToggleButton tbRing = (ToggleButton) this.findViewById(R.id.toggleButton_Ring);
		tbRing.setWidth(width);
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
		alarmManagerWrapper.SetAlarm();
	}

	private void cancelAlarm() {
		AlarmManagerWrapper alarmManagerWrapper = new AlarmManagerWrapper();
		alarmManagerWrapper.CancelAlarm();
	}

	public void onButtonStartClicked(View view) {
		this.setupAlarmCalculationData();

		this.setStartButtonEnabled(false);
		this.setStopButtonEnabled(true);

		this.restartAlarm();
	}

	private void setupAlarmCalculationData() {
		TimePicker tp = this.getTimePicker();
		tp.clearFocus();
		int hours = tp.getCurrentHour();
		int minutes = tp.getCurrentMinute();

		AlarmCalculationData acd = new AlarmCalculationData();
		acd.setAlarmActive(true);
		acd.setAlarmTimeHourOfDay(hours);
		acd.setAlarmTimeMinuteOfDay(minutes);
		acd.setContraceptiveType(this.contraceptiveType);
		acd.setLastUseOfContraceptiveDayOfYear(CalendarWrapper.getTodayAsDayOfYear());
		acd.setLastBreakDayOfYear(CalendarWrapper.getTodayOneWeekAgoAsDayOfYear());

		this.internalStorageWrapper.saveToStorage(acd);

		LoggerWrapper.LogInfo(acd.toString());
	}

	public void onButtonStopClicked(View view) {
		DialogFragment dialog = CancelDialog.newInstance(this);
		dialog.show(this.getSupportFragmentManager(), "CancelDialog");
	}

	public void onDialogPositiveClick(DialogFragment dialog) {
		this.cancelAlarm();
		this.internalStorageWrapper.saveUpdatedAlarmActivatedTo(false);

		this.setStartButtonEnabled(true);
		this.setStopButtonEnabled(false);
	}

	public void onDialogNegativeClick(DialogFragment dialog) {
		dialog.dismiss();
	}
}