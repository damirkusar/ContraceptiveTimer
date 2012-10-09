package ch.kusar.contraceptivetimer.businessobjects;

public class AlarmMessage {

	public static String getPillChangeMessage(int timesUsed) {
		String msg = String.format("It's time, take your pill nr: %1$s", timesUsed);
		return msg;
	}

	public static String getPatchChangeMessage(int timesUsed) {
		String msg = String.format("It's time, stick new patch nr: %1$s", timesUsed);
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
}
