package ch.kusar.contraceptivetimer.businessobjects;

public class AlarmMessage {

	public static String getPillChangeMessage() {
		String msg = String.format("It's time, please take your pill");
		return msg;
	}

	public static String getPatchChangeMessage() {
		String msg = String.format("It's time, please stick your new patch");
		return msg;
	}

	public static String getRingChangeMessage() {
		String msg = String.format("It's time, please use a new ring");
		return msg;
	}

	public static String getPatchMessage() {
		String msg = String.format("It's time, please remove your patch");
		return msg;
	}

	public static String getRingRemoveMessage() {
		String msg = String.format("It's time, please remove your ring");
		return msg;
	}

}
