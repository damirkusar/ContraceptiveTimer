package ch.kusar.contraceptivetimer.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import ch.kusar.contraceptivetimer.R;

public class StartDialog extends DialogFragment {

	static StartDialogListener startListener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

		LayoutInflater layoutInflater = this.getActivity().getLayoutInflater();
		builder.setView(layoutInflater.inflate(R.layout.dialog_start, null));

		builder.setPositiveButton(R.string.startDialog_now, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				StartDialog.startListener.onStartDialogNowClick(StartDialog.this);
			}
		});
		builder.setNegativeButton(R.string.startDialog_specificDate, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				StartDialog.startListener.onStartDialogStartOnSpecificDateClick(StartDialog.this);
			}
		});
		return builder.create();
	}

	public static StartDialog newInstance(Activity activity) {
		try {
			StartDialog.startListener = (StartDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
		}
		StartDialog frag = new StartDialog();
		return frag;
	}
}