package ch.kusar.contraceptivetimer.wrapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import ch.kusar.contraceptivetimer.businessobjects.FileStorageWriter;
import ch.kusar.contraceptivetimer.businessobjects.LicenseStatus;
import ch.kusar.contraceptivetimer.calculator.AlarmCalculationData;
import ch.kusar.contraceptivetimer.calculator.AlarmTime;

public class InternalStorageWrapper {

	private final Context fileContext;

	public InternalStorageWrapper(Context fileContext) {
		this.fileContext = fileContext;
	}

	public void setFileName(String fileName) {
	}

	public boolean saveToStorage(FileStorageWriter data) {
		try {
			FileOutputStream fos = this.fileContext.openFileOutput(data.getFileName(), Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(data);

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

	private FileStorageWriter loadDataFromStorage(FileStorageWriter data) {
		try {
			FileInputStream fis = this.fileContext.openFileInput(data.getFileName());
			ObjectInputStream ois = new ObjectInputStream(fis);
			FileStorageWriter alarmData = (FileStorageWriter) ois.readObject();

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

	public LicenseStatus loadLicenseFileFromStorage() {
		LicenseStatus ls = new LicenseStatus(false);
		LicenseStatus loadedLS = (LicenseStatus) this.loadDataFromStorage(ls);
		return loadedLS;
	}

	public AlarmCalculationData loadAlarmCalculationDataFromStorage() {
		AlarmCalculationData acd = new AlarmCalculationData();
		AlarmCalculationData loadedACD = (AlarmCalculationData) this.loadDataFromStorage(acd);
		return loadedACD;
	}

	public void saveUpdatedLastUseOfContraceptionToStorage(CalendarWrapper lastUseOfContraception) {
		AlarmCalculationData alarmCalculationData = this.loadAlarmCalculationDataFromStorage();
		alarmCalculationData.setLastUseOfContraceptive(lastUseOfContraception);
		this.saveToStorage(alarmCalculationData);

		LoggerWrapper.LogDebug(alarmCalculationData.toString());
	}

	public void saveUpdatedLastBreakOfContraceptionToStorage(CalendarWrapper lastBreakOfContraception) {
		AlarmCalculationData alarmCalculationData = this.loadAlarmCalculationDataFromStorage();
		alarmCalculationData.setLastBreak(lastBreakOfContraception);
		this.saveToStorage(alarmCalculationData);

		LoggerWrapper.LogDebug(alarmCalculationData.toString());
	}

	public void saveUpdatedAlarmActivatedTo(boolean isAlarmActive) {
		AlarmCalculationData alarmCalculationData = this.loadAlarmCalculationDataFromStorage();
		alarmCalculationData.setAlarmActive(isAlarmActive);
		this.saveToStorage(alarmCalculationData);

		LoggerWrapper.LogDebug(alarmCalculationData.toString());
	}

	public void saveUpdatedAlarmTime(AlarmTime alarmTime) {
		AlarmCalculationData alarmCalculationData = this.loadAlarmCalculationDataFromStorage();
		alarmCalculationData.setAlarmTime(alarmTime);
		this.saveToStorage(alarmCalculationData);

		LoggerWrapper.LogDebug(alarmCalculationData.toString());
	}

	public void saveUpdatedIncrementedUsedTimes() {
		AlarmCalculationData alarmCalculationData = this.loadAlarmCalculationDataFromStorage();
		alarmCalculationData.incrementTimesUsed();
		this.saveToStorage(alarmCalculationData);

		LoggerWrapper.LogDebug(alarmCalculationData.toString());
	}

	public void saveUpdatedResetedUsedTimes() {
		AlarmCalculationData alarmCalculationData = this.loadAlarmCalculationDataFromStorage();
		alarmCalculationData.resetTimesUsed();
		this.saveToStorage(alarmCalculationData);

		LoggerWrapper.LogDebug(alarmCalculationData.toString());
	}

	@Override
	public String toString() {
		return this.getClass().getName();
	}
}
