package ch.kusar.contraceptivetimer.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import ch.kusar.contraceptivetimer.R;

public class CancelDialog extends DialogFragment {

	static CancelDialogListener cancelListener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

		LayoutInflater layoutInflater = this.getActivity().getLayoutInflater();
		builder.setView(layoutInflater.inflate(R.layout.dialog_cancel, null));

		builder.setPositiveButton(R.string.cancelDialog_yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				CancelDialog.cancelListener.onDialogPositiveClick(CancelDialog.this);
			}
		});
		builder.setNegativeButton(R.string.cancelDialog_no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				CancelDialog.cancelListener.onDialogNegativeClick(CancelDialog.this);
			}
		});
		return builder.create();
	}

	public static CancelDialog newInstance(Activity activity) {
		try {
			CancelDialog.cancelListener = (CancelDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
		}
		CancelDialog frag = new CancelDialog();
		return frag;
	}
}