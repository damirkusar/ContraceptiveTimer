package ch.kusar.contraceptivetimer.dialog;

import android.support.v4.app.DialogFragment;

public interface CancelDialogListener {
	public void onDialogPositiveClick(DialogFragment dialog);

	public void onDialogNegativeClick(DialogFragment dialog);
}