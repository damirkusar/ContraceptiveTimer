package ch.kusar.contraceptivetimer.dialog;

import android.support.v4.app.DialogFragment;

public interface CancelDialogListener {

	public void onCancelDialogYesClick(DialogFragment dialog);

	public void onCancelDialogNoClick(DialogFragment dialog);
}