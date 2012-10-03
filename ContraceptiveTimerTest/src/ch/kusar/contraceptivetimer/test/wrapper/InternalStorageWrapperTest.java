package ch.kusar.contraceptivetimer.test.wrapper;

import java.math.BigInteger;
import java.security.SecureRandom;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import ch.kusar.contraceptivetimer.MainApplicationContext;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.calculator.AlarmCalculationData;
import ch.kusar.contraceptivetimer.calculator.AlarmTime;
import ch.kusar.contraceptivetimer.wrapper.InternalStorageWrapper;

public class InternalStorageWrapperTest extends AndroidTestCase {

	private AlarmCalculationData alarmCalculationData;
	private InternalStorageWrapper internalStorageWrapper;
	private SecureRandom random;

	@Override
	public void setUp() throws Exception {
		this.random = new SecureRandom();

		this.internalStorageWrapper = new InternalStorageWrapper(MainApplicationContext.getAppContext());

		this.alarmCalculationData = new AlarmCalculationData();
		this.alarmCalculationData.setContraceptiveType(ContraceptiveType.CONTRACEPTION_RING);
		this.alarmCalculationData.setAlarmActive(true);
		this.alarmCalculationData.setAlarmTime(new AlarmTime(20, 0));
	}

	@Override
	public void tearDown() throws Exception {
		this.alarmCalculationData = null;
		this.internalStorageWrapper = null;
		this.random = null;
	}

	public void testLoadFromStorage_LoadsStoredFileIntoObject_ShouldLoadTheSavedDataCorrectly() {
		this.internalStorageWrapper.saveToStorage(this.alarmCalculationData);
		AlarmCalculationData alarmDataLoaded = this.internalStorageWrapper.loadFromStorage();

		Assert.assertEquals(20, alarmDataLoaded.getAlarmTimeHourOfDay());
		Assert.assertEquals(0, alarmDataLoaded.getAlarmTimeMinutes());
		Assert.assertEquals(this.alarmCalculationData.getContraceptiveType(), ContraceptiveType.CONTRACEPTION_RING);
		Assert.assertEquals(28, alarmDataLoaded.getIntervalDays());
		Assert.assertTrue(this.alarmCalculationData.isAlarmActive());
	}

	public void testLoadFromStorage_LoadsStoredFileIntoObjectWithAnotherStorageWrapperInstance_ShouldLoadTheSavedDataCorrectly() {
		this.internalStorageWrapper.saveToStorage(this.alarmCalculationData);

		InternalStorageWrapper internalStorageWrapperLoader = new InternalStorageWrapper(this.getContext().getApplicationContext());

		AlarmCalculationData alarmDataLoaded = internalStorageWrapperLoader.loadFromStorage();

		Assert.assertEquals(20, alarmDataLoaded.getAlarmTimeHourOfDay());
		Assert.assertEquals(0, alarmDataLoaded.getAlarmTimeMinutes());
		Assert.assertEquals(this.alarmCalculationData.getContraceptiveType(), ContraceptiveType.CONTRACEPTION_RING);
		Assert.assertEquals(28, alarmDataLoaded.getIntervalDays());
		Assert.assertTrue(this.alarmCalculationData.isAlarmActive());
	}

	public void testLoadFromStorage_TryToLoadNotSavedFileFromStorage_ObjectIsNull() {
		this.internalStorageWrapper.setFileName(new BigInteger(130, this.random).toString(32));
		AlarmCalculationData alarmDataLoaded = this.internalStorageWrapper.loadFromStorage();

		Assert.assertNull(alarmDataLoaded);
	}

	public void testSaveToStorage_StoresTheFileToInternalStorage_ShouldBeSavedToStorageWithoutError() {
		Assert.assertTrue(this.internalStorageWrapper.saveToStorage(this.alarmCalculationData));
	}

	public void testsaveUpdatedLastUseOfContraceptionToStorage_StoresTheFileToInternalStorage_ShouldBeSavedToStorageWithoutError() {
		Assert.assertTrue(this.internalStorageWrapper.saveToStorage(this.alarmCalculationData));
		Assert.assertEquals(0, this.alarmCalculationData.getLastUseOfContraceptiveDayOfYear());

		this.internalStorageWrapper.saveUpdatedLastUseOfContraceptionToStorage(42);

		AlarmCalculationData alarmCalculationData = this.internalStorageWrapper.loadFromStorage();
		Assert.assertEquals(0, alarmCalculationData.getLastBreakDayOfYear());
		Assert.assertEquals(42, alarmCalculationData.getLastUseOfContraceptiveDayOfYear());
	}

	public void testsaveUpdatedLastBreakOfContraceptionToStorage_StoresTheFileToInternalStorage_ShouldBeSavedToStorageWithoutError() {
		Assert.assertTrue(this.internalStorageWrapper.saveToStorage(this.alarmCalculationData));
		Assert.assertEquals(0, this.alarmCalculationData.getLastBreakDayOfYear());

		this.internalStorageWrapper.saveUpdatedLastBreakOfContraceptionToStorage(42);

		AlarmCalculationData alarmCalculationData = this.internalStorageWrapper.loadFromStorage();
		Assert.assertEquals(42, alarmCalculationData.getLastBreakDayOfYear());
		Assert.assertEquals(0, alarmCalculationData.getLastUseOfContraceptiveDayOfYear());
	}
}
