package ch.kusar.contraceptivetimer.wrapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import ch.kusar.contraceptivetimer.calculator.AlarmCalculationData;
import ch.kusar.contraceptivetimer.calculator.AlarmTime;
import ch.kusar.contraceptivetimer.licenceing.LicenseStatus;

public class InternalStorageWrapper {

	private String fileNameData = "ContraceptiveTimerData";
	private final String fileNameLicense = "ContraceptiveLicense";
	private final Context fileContext;

	public InternalStorageWrapper(Context fileContext) {
		this.fileContext = fileContext;
	}

	public void setFileName(String fileName) {
		this.fileNameData = fileName;
	}

	public LicenseStatus loadLicenseFileFromStorage() {
		try {
			FileInputStream fis = this.fileContext.openFileInput(this.fileNameLicense);
			ObjectInputStream ois = new ObjectInputStream(fis);
			LicenseStatus licenseStatus = (LicenseStatus) ois.readObject();

			fis.close();
			ois.close();

			return licenseStatus;
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

	public AlarmCalculationData loadAlarmCalculationDataFromStorage() {
		try {
			FileInputStream fis = this.fileContext.openFileInput(this.fileNameData);
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

	public boolean saveToStorage(AlarmCalculationData data) {
		try {
			FileOutputStream fos = this.fileContext.openFileOutput(this.fileNameData, Context.MODE_PRIVATE);
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

	public boolean saveToStorage(LicenseStatus data) {
		try {
			FileOutputStream fos = this.fileContext.openFileOutput(this.fileNameLicense, Context.MODE_PRIVATE);
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
