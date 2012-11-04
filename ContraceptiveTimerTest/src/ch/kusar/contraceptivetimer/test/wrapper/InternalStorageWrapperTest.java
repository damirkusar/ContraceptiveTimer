package ch.kusar.contraceptivetimer.test.wrapper;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import ch.kusar.contraceptivetimer.MainApplicationContext;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.businessobjects.LicenseStatus;
import ch.kusar.contraceptivetimer.calculator.AlarmCalculationData;
import ch.kusar.contraceptivetimer.calculator.AlarmTime;
import ch.kusar.contraceptivetimer.wrapper.CalendarWrapper;
import ch.kusar.contraceptivetimer.wrapper.InternalStorageWrapper;

public class InternalStorageWrapperTest extends AndroidTestCase {

	private AlarmCalculationData alarmCalculationData;
	private LicenseStatus licenseStatus;
	private InternalStorageWrapper internalStorageWrapper;

	@Override
	public void setUp() throws Exception {

		this.internalStorageWrapper = new InternalStorageWrapper(MainApplicationContext.getAppContext());

		this.alarmCalculationData = new AlarmCalculationData();
		this.alarmCalculationData.setContraceptiveType(ContraceptiveType.CONTRACEPTION_RING);
		this.alarmCalculationData.setAlarmActive(true);
		this.alarmCalculationData.setAlarmTime(new AlarmTime(20, 0));

		this.licenseStatus = new LicenseStatus();
	}

	@Override
	public void tearDown() throws Exception {
		this.alarmCalculationData = null;
		this.internalStorageWrapper = null;
	}

	public void testLoadFromStorage_LoadsStoredFileIntoObject_ShouldLoadTheSavedDataCorrectly() {
		this.internalStorageWrapper.saveToStorage(this.alarmCalculationData);
		AlarmCalculationData alarmDataLoaded = this.internalStorageWrapper.loadAlarmCalculationDataFromStorage();

		Assert.assertEquals(20, alarmDataLoaded.getAlarmTimeHourOfDay());
		Assert.assertEquals(0, alarmDataLoaded.getAlarmTimeMinutes());
		Assert.assertEquals(this.alarmCalculationData.getContraceptiveType(), ContraceptiveType.CONTRACEPTION_RING);
		Assert.assertEquals(28, alarmDataLoaded.getIntervalDays());
		Assert.assertTrue(this.alarmCalculationData.isAlarmActive());
	}

	public void testLoadFromStorage_LoadsStoredFileIntoObjectWithAnotherStorageWrapperInstance_ShouldLoadTheSavedDataCorrectly() {
		this.internalStorageWrapper.saveToStorage(this.alarmCalculationData);

		InternalStorageWrapper internalStorageWrapperLoader = new InternalStorageWrapper(this.getContext().getApplicationContext());

		AlarmCalculationData alarmDataLoaded = internalStorageWrapperLoader.loadAlarmCalculationDataFromStorage();

		Assert.assertEquals(20, alarmDataLoaded.getAlarmTimeHourOfDay());
		Assert.assertEquals(0, alarmDataLoaded.getAlarmTimeMinutes());
		Assert.assertEquals(this.alarmCalculationData.getContraceptiveType(), ContraceptiveType.CONTRACEPTION_RING);
		Assert.assertEquals(28, alarmDataLoaded.getIntervalDays());
		Assert.assertTrue(this.alarmCalculationData.isAlarmActive());
	}

	public void testSaveToStorage_StoresTheFileToInternalStorage_ShouldBeSavedToStorageWithoutError() {
		Assert.assertTrue(this.internalStorageWrapper.saveToStorage(this.alarmCalculationData));
	}

	public void testLoadFromStorage_LicenseStatus_LoadsStoredFileIntoObject_ShouldLoadTheSavedDataCorrectly() {
		Assert.assertFalse(this.licenseStatus.isPaid());

		this.licenseStatus.setPaid(true);
		this.internalStorageWrapper.saveToStorage(this.licenseStatus);

		LicenseStatus licenseStatus = this.internalStorageWrapper.loadLicenseFileFromStorage();

		Assert.assertTrue(licenseStatus.isPaid());
	}

	public void testLoadFromStorage_LicenseStatus_LoadsStoredFileIntoObjectWithAnotherStorageWrapperInstance_ShouldLoadTheSavedDataCorrectly() {
		Assert.assertFalse(this.licenseStatus.isPaid());

		this.licenseStatus.setPaid(true);
		this.internalStorageWrapper.saveToStorage(this.licenseStatus);

		InternalStorageWrapper internalStorageWrapperLoader = new InternalStorageWrapper(this.getContext().getApplicationContext());

		LicenseStatus licenseStatus = internalStorageWrapperLoader.loadLicenseFileFromStorage();

		Assert.assertTrue(licenseStatus.isPaid());
	}

	public void testSaveToStorage_LicenseStatus_StoresTheFileToInternalStorage_ShouldBeSavedToStorageWithoutError() {
		Assert.assertTrue(this.internalStorageWrapper.saveToStorage(this.licenseStatus));
	}

	public void testsaveUpdatedLastUseOfContraceptionToStorage_StoresTheFileToInternalStorage_ShouldBeSavedToStorageWithoutError() {
		Assert.assertTrue(this.internalStorageWrapper.saveToStorage(this.alarmCalculationData));
		Assert.assertEquals(0, this.alarmCalculationData.getLastUseOfContraceptive().getYear());
		Assert.assertEquals(0, this.alarmCalculationData.getLastUseOfContraceptive().getMonth());
		Assert.assertEquals(0, this.alarmCalculationData.getLastUseOfContraceptive().getDayOfMonth());

		CalendarWrapper cw = new CalendarWrapper(2012, 4, 2);
		this.internalStorageWrapper.saveUpdatedLastUseOfContraceptionToStorage(cw);

		AlarmCalculationData alarmDataLoaded = this.internalStorageWrapper.loadAlarmCalculationDataFromStorage();
		Assert.assertEquals(0, alarmDataLoaded.getLastBreak().getYear());
		Assert.assertEquals(0, alarmDataLoaded.getLastBreak().getMonth());
		Assert.assertEquals(0, alarmDataLoaded.getLastBreak().getDayOfMonth());
		Assert.assertEquals(2012, alarmDataLoaded.getLastUseOfContraceptive().getYear());
		Assert.assertEquals(4, alarmDataLoaded.getLastUseOfContraceptive().getMonth());
		Assert.assertEquals(2, alarmDataLoaded.getLastUseOfContraceptive().getDayOfMonth());
	}

	public void testsaveUpdatedLastBreakOfContraceptionToStorage_StoresTheFileToInternalStorage_ShouldBeSavedToStorageWithoutError() {
		Assert.assertTrue(this.internalStorageWrapper.saveToStorage(this.alarmCalculationData));
		Assert.assertEquals(0, this.alarmCalculationData.getLastBreak().getYear());
		Assert.assertEquals(0, this.alarmCalculationData.getLastBreak().getMonth());
		Assert.assertEquals(0, this.alarmCalculationData.getLastBreak().getDayOfMonth());

		CalendarWrapper cw = new CalendarWrapper(2012, 4, 2);
		this.internalStorageWrapper.saveUpdatedLastBreakOfContraceptionToStorage(cw);

		AlarmCalculationData alarmDataLoaded = this.internalStorageWrapper.loadAlarmCalculationDataFromStorage();
		Assert.assertEquals(0, alarmDataLoaded.getLastUseOfContraceptive().getYear());
		Assert.assertEquals(0, alarmDataLoaded.getLastUseOfContraceptive().getMonth());
		Assert.assertEquals(0, alarmDataLoaded.getLastUseOfContraceptive().getDayOfMonth());
		Assert.assertEquals(2012, alarmDataLoaded.getLastBreak().getYear());
		Assert.assertEquals(4, alarmDataLoaded.getLastBreak().getMonth());
		Assert.assertEquals(2, alarmDataLoaded.getLastBreak().getDayOfMonth());
	}

	public void testsaveUpdatedAlarmActivatedTo_StoresTheFileToInternalStorage_ShouldBeSavedToStorageWithoutError() {
		Assert.assertTrue(this.internalStorageWrapper.saveToStorage(this.alarmCalculationData));
		Assert.assertTrue(this.alarmCalculationData.isAlarmActive());

		this.internalStorageWrapper.saveUpdatedAlarmActivatedTo(false);

		AlarmCalculationData alarmDataLoaded = this.internalStorageWrapper.loadAlarmCalculationDataFromStorage();
		Assert.assertFalse(alarmDataLoaded.isAlarmActive());
	}

	public void testsaveUpdatedAlarmTime_StoresTheFileToInternalStorage_ShouldBeSavedToStorageWithoutError() {
		Assert.assertTrue(this.internalStorageWrapper.saveToStorage(this.alarmCalculationData));
		Assert.assertEquals(20, this.alarmCalculationData.getAlarmTimeHourOfDay());
		Assert.assertEquals(0, this.alarmCalculationData.getAlarmTimeMinutes());

		AlarmTime at = new AlarmTime(13, 37);
		this.internalStorageWrapper.saveUpdatedAlarmTime(at);

		AlarmCalculationData alarmDataLoaded = this.internalStorageWrapper.loadAlarmCalculationDataFromStorage();
		Assert.assertEquals(13, alarmDataLoaded.getAlarmTimeHourOfDay());
		Assert.assertEquals(37, alarmDataLoaded.getAlarmTimeMinutes());
	}

	public void testsaveUpdatedIncrementedUsedTimes_StoresTheFileToInternalStorage_ShouldBeSavedToStorageWithoutError() {
		Assert.assertTrue(this.internalStorageWrapper.saveToStorage(this.alarmCalculationData));
		Assert.assertEquals(0, this.alarmCalculationData.getTimesUsed());

		this.internalStorageWrapper.saveUpdatedIncrementedUsedTimes();

		AlarmCalculationData alarmDataLoaded = this.internalStorageWrapper.loadAlarmCalculationDataFromStorage();
		Assert.assertEquals(1, alarmDataLoaded.getTimesUsed());
	}

	public void testsaveUpdatedResetedUsedTimes_StoresTheFileToInternalStorage_ShouldBeSavedToStorageWithoutError() {
		Assert.assertTrue(this.internalStorageWrapper.saveToStorage(this.alarmCalculationData));
		this.alarmCalculationData.incrementTimesUsed();
		Assert.assertEquals(1, this.alarmCalculationData.getTimesUsed());

		this.internalStorageWrapper.saveUpdatedResetedUsedTimes();

		AlarmCalculationData alarmDataLoaded = this.internalStorageWrapper.loadAlarmCalculationDataFromStorage();
		Assert.assertEquals(0, alarmDataLoaded.getTimesUsed());
	}
}
