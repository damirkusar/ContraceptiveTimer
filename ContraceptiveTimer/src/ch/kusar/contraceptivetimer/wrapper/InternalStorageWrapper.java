package ch.kusar.contraceptivetimer.wrapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import ch.kusar.contraceptivetimer.calculator.AlarmCalculationData;

public class InternalStorageWrapper {

	private String fileName = "ContraceptiveTimerData";
	private final Context fileContext;

	public InternalStorageWrapper(Context fileContext) {
		this.fileContext = fileContext;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public AlarmCalculationData loadFromStorage() {
		try {
			FileInputStream fis = this.fileContext.openFileInput(this.fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			AlarmCalculationData alarmData = (AlarmCalculationData) ois.readObject();

			fis.close();
			ois.close();

			return alarmData;
		} catch (ClassNotFoundException e) {
			String msg = String.format("%s: ClassNotFoundException with error: %s", this.toString(), e.getStackTrace());
			LoggerWrapper.LogError(msg);
			e.printStackTrace();
		} catch (IOException e) {
			String msg = String.format("%s: IOException with error: %s", this.toString(), e.getStackTrace());
			LoggerWrapper.LogError(msg);
			e.printStackTrace();
		}
		return null;
	}

	public boolean saveToStorage(AlarmCalculationData alarmData) {
		try {
			FileOutputStream fos = this.fileContext.openFileOutput(this.fileName, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(alarmData);

			fos.close();
			oos.close();

			return true;
		} catch (IOException e) {
			String msg = String.format("%s: IOException with error: %s", this.toString(), e.getStackTrace());
			LoggerWrapper.LogError(msg);
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveUpdatedLastUseOfContraceptionToStorage(int lastUseOfContraception) {
		AlarmCalculationData alarmCalculationData = this.loadFromStorage();
		alarmCalculationData.setLastUseOfContraceptiveDayOfYear(lastUseOfContraception);
		return this.saveToStorage(alarmCalculationData);
	}

	public boolean saveUpdatedLastBreakOfContraceptionToStorage(int lastBreakOfContraception) {
		AlarmCalculationData alarmCalculationData = this.loadFromStorage();
		alarmCalculationData.setLastBreakDayOfYear(lastBreakOfContraception);
		return this.saveToStorage(alarmCalculationData);
	}

	@Override
	public String toString() {
		return this.getClass().getName();
	}

	public void saveUpdatedAlarmActivatedTo(boolean isAlarmActive) {
		AlarmCalculationData alarmCalculationData = this.loadFromStorage();
		alarmCalculationData.setAlarmActive(isAlarmActive);
		this.saveToStorage(alarmCalculationData);
	}
}
