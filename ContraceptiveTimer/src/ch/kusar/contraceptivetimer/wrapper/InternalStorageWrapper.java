package ch.kusar.contraceptivetimer.wrapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import ch.kusar.contraceptivetimer.businessobjects.AlarmData;

public class InternalStorageWrapper {

	private static final String FILENAME = "ContraceptiveTimerData";
	private final Context fileContext;

	public InternalStorageWrapper(Context fileContext) {
		this.fileContext = fileContext;
	}

	public AlarmData loadFromStorage() {
		try {
			FileInputStream fis = fileContext.openFileInput(this.FILENAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			AlarmData alarmData = (AlarmData) ois.readObject();
			ois.close();
			return alarmData;
		} catch (ClassNotFoundException e) {
			String msg = String.format(
					"%s: ClassNotFoundException with error: %s",
					this.toString(), e.getStackTrace());
			LoggerWrapper.LogError(msg);
			e.printStackTrace();
		} catch (IOException e) {
			String msg = String.format("%s: IOException with error: %s",
					this.toString(), e.getStackTrace());
			LoggerWrapper.LogError(msg);
			e.printStackTrace();
		}
		return null;
	}

	public boolean saveToStorage(AlarmData alarmData) {
		try {
			FileOutputStream fos = fileContext.openFileOutput(this.FILENAME,
					Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(alarmData);
			oos.close();
			return true;
		} catch (IOException e) {
			String msg = String.format("%s: IOException with error: %s",
					this.toString(), e.getStackTrace());
			LoggerWrapper.LogError(msg);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
}
