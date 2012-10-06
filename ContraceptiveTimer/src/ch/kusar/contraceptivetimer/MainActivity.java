package ch.kusar.contraceptivetimer;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.calculator.AlarmCalculationData;
import ch.kusar.contraceptivetimer.dialog.CancelDialog;
import ch.kusar.contraceptivetimer.dialog.CancelDialogListener;
import ch.kusar.contraceptivetimer.wrapper.AlarmManagerWrapper;
import ch.kusar.contraceptivetimer.wrapper.CalendarWrapper;
import ch.kusar.contraceptivetimer.wrapper.InternalStorageWrapper;
import ch.kusar.contraceptivetimer.wrapper.LoggerWrapper;

//import android.view.MenuItem;

public class MainActivity extends FragmentActivity implements CancelDialogListener {

	private InternalStorageWrapper internalStorageWrapper;
	private ContraceptiveType contraceptiveType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		this.internalStorageWrapper = new InternalStorageWrapper(MainApplicationContext.getAppContext());

		this.setupViews();
	}

	private void setupViews() {
		TimePicker tp = (TimePicker) this.findViewById(R.id.timePicker);
		tp.setIs24HourView(true);

		AlarmCalculationData acd = this.internalStorageWrapper.loadFromStorage();
		if (acd != null) {
			LoggerWrapper.LogDebug(acd.toString());
			tp.clearFocus();
			tp.setCurrentHour(acd.getAlarmTimeHourOfDay());
			tp.setCurrentMinute(acd.getAlarmTimeMinutes());

			ToggleButton tbAlarm = (ToggleButton) this.findViewById(R.id.toggleButtonAlarm);
			tbAlarm.setChecked(acd.isAlarmActive());

			this.turnToggleButtonOn(acd.getContraceptiveType());
		} else {
			this.turnToggleButtonOn(ContraceptiveType.CONTRACEPTION_PILL);
		}
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

	public void onButtonSetTimePickerClicked(View view) {
		boolean on = ((ToggleButton) view).isChecked();
		if (on) {
			this.contraceptiveType = ContraceptiveType.CONTRACEPTION_RING;
			this.turnToggleButtonOff(true, true, false);
		}
	}

	public void onToggleButtonAlarmClicked(View view) {
		boolean on = ((ToggleButton) view).isChecked();
		AlarmManagerWrapper alarmManagerWrapper = new AlarmManagerWrapper();
		if (on) {
			this.setupAlarmCalculationData();
			alarmManagerWrapper.SetAlarm();
		} else {
			DialogFragment dialog = CancelDialog.newInstance(this);
			dialog.show(this.getSupportFragmentManager(), "CancelDialog");
		}
	}

	private void setupAlarmCalculationData() {
		TimePicker tp = (TimePicker) this.findViewById(R.id.timePicker);
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

	// TODO: Remove Menu?!
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onDialogPositiveClick(DialogFragment dialog) {
		AlarmManagerWrapper alarmManagerWrapper = new AlarmManagerWrapper();
		alarmManagerWrapper.CancelAlarm();
		this.internalStorageWrapper.saveUpdatedAlarmActivatedTo(false);
	}

	public void onDialogNegativeClick(DialogFragment dialog) {
		dialog.dismiss();

		ToggleButton toggleButtonAlarm = (ToggleButton) this.findViewById(R.id.toggleButtonAlarm);
		toggleButtonAlarm.setChecked(true);
	}
}
