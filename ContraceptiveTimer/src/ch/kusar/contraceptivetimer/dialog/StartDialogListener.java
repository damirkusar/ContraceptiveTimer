package ch.kusar.contraceptivetimer.dialog;

import android.support.v4.app.DialogFragment;

public interface StartDialogListener {

	public void onStartDialogNowClick(DialogFragment dialog);

	public void onStartDialogStartOnSpecificDateClick(DialogFragment dialog);
}