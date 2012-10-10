package ch.kusar.contraceptivetimer.businessobjects;

import android.app.Activity;
import ch.kusar.contraceptivetimer.R;
import ch.kusar.contraceptivetimer.wrapper.ResourceWrapper;

public class AlarmMessage extends Activity {

	public static String getPillChangeMessage(int timesUsed) {
		String msg = String.format("It's time, take your %1$s pill", AlarmMessage.convertToText(timesUsed));
		return msg;
	}

	public static String getPatchChangeMessage(int timesUsed) {
		String msg = String.format("It's time, stick your %1$s patch", AlarmMessage.convertToText(timesUsed));
		return msg;
	}

	public static String getRingChangeMessage() {
		String msg = String.format("It's time, use a new ring");
		return msg;
	}

	public static String getPatchRemoveMessage() {
		String msg = String.format("Time for a break, remove patch");
		return msg;
	}

	public static String getRingRemoveMessage() {
		String msg = String.format("Time for a break, remove ring");
		return msg;
	}

	public static String getPillRemoveMessage() {
		String msg = String.format("Time for a break, DO NOT take a new Pill");
		return msg;
	}

	private static String convertToText(int timesUsed) {
		switch (timesUsed) {
		case 1:
			return ResourceWrapper.getStringFromResource(R.string._1);
		case 2:
			return ResourceWrapper.getStringFromResource(R.string._2);
		case 3:
			return ResourceWrapper.getStringFromResource(R.string._3);
		case 4:
			return ResourceWrapper.getStringFromResource(R.string._4);
		case 5:
			return ResourceWrapper.getStringFromResource(R.string._5);
		case 6:
			return ResourceWrapper.getStringFromResource(R.string._6);
		case 7:
			return ResourceWrapper.getStringFromResource(R.string._7);
		case 8:
			return ResourceWrapper.getStringFromResource(R.string._8);
		case 9:
			return ResourceWrapper.getStringFromResource(R.string._9);
		case 10:
			return ResourceWrapper.getStringFromResource(R.string._10);
		case 11:
			return ResourceWrapper.getStringFromResource(R.string._11);
		case 12:
			return ResourceWrapper.getStringFromResource(R.string._12);
		case 13:
			return ResourceWrapper.getStringFromResource(R.string._13);
		case 14:
			return ResourceWrapper.getStringFromResource(R.string._14);
		case 15:
			return ResourceWrapper.getStringFromResource(R.string._15);
		case 16:
			return ResourceWrapper.getStringFromResource(R.string._16);
		case 17:
			return ResourceWrapper.getStringFromResource(R.string._17);
		case 18:
			return ResourceWrapper.getStringFromResource(R.string._18);
		case 19:
			return ResourceWrapper.getStringFromResource(R.string._19);
		case 20:
			return ResourceWrapper.getStringFromResource(R.string._20);
		case 21:
			return ResourceWrapper.getStringFromResource(R.string._21);
		default:
			return "";
		}
	}
}
