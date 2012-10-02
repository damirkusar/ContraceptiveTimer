package ch.kusar.contraceptivetimer.test.wrapper;

import java.math.BigInteger;
import java.security.SecureRandom;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.calculator.AlarmCalculationData;
import ch.kusar.contraceptivetimer.calculator.AlarmTime;
import ch.kusar.contraceptivetimer.wrapper.InternalStorageWrapper;

public class InternalStorageWrapperTest extends AndroidTestCase {

	private AlarmCalculationData alarmData;
	private InternalStorageWrapper internalStorageWrapper;
	private SecureRandom random;

	@Override
	public void setUp() throws Exception {
		this.random = new SecureRandom();

		this.internalStorageWrapper = new InternalStorageWrapper(this
				.getContext().getApplicationContext());

		this.alarmData = new AlarmCalculationData();
		this.alarmData
				.setContraceptiveType(ContraceptiveType.CONTRACEPTION_RING);
		this.alarmData.setAlarmActive(true);
		this.alarmData.setAlarmTime(new AlarmTime(20, 0));
	}

	@Override
	public void tearDown() throws Exception {
		this.alarmData = null;
		this.internalStorageWrapper = null;
		this.random = null;
	}

	public void testLoadFromStorage_LoadsStoredFileIntoObject_ShouldLoadTheSavedDataCorrectly() {
		this.internalStorageWrapper.saveToStorage(this.alarmData);
		AlarmCalculationData alarmDataLoaded = this.internalStorageWrapper
				.loadFromStorage();

		Assert.assertEquals(20, alarmDataLoaded.getAlarmTimeHourOfDay());
		Assert.assertEquals(0, alarmDataLoaded.getAlarmTimeMinutes());
		Assert.assertEquals(this.alarmData.getContraceptiveType(),
				ContraceptiveType.CONTRACEPTION_RING);
		Assert.assertEquals(28, alarmDataLoaded.getIntervalDays());
		Assert.assertTrue(this.alarmData.isAlarmActive());
	}

	public void testLoadFromStorage_LoadsStoredFileIntoObjectWithAnotherStorageWrapperInstance_ShouldLoadTheSavedDataCorrectly() {
		this.internalStorageWrapper.saveToStorage(this.alarmData);

		InternalStorageWrapper internalStorageWrapperLoader = new InternalStorageWrapper(
				this.getContext().getApplicationContext());

		AlarmCalculationData alarmDataLoaded = internalStorageWrapperLoader
				.loadFromStorage();

		Assert.assertEquals(20, alarmDataLoaded.getAlarmTimeHourOfDay());
		Assert.assertEquals(0, alarmDataLoaded.getAlarmTimeMinutes());
		Assert.assertEquals(this.alarmData.getContraceptiveType(),
				ContraceptiveType.CONTRACEPTION_RING);
		Assert.assertEquals(28, alarmDataLoaded.getIntervalDays());
		Assert.assertTrue(this.alarmData.isAlarmActive());
	}

	public void testLoadFromStorage_TryToLoadNotSavedFileFromStorage_ObjectIsNull() {
		this.internalStorageWrapper
				.setFileName(new BigInteger(130, this.random).toString(32));
		AlarmCalculationData alarmDataLoaded = this.internalStorageWrapper
				.loadFromStorage();

		Assert.assertNull(alarmDataLoaded);
	}

	public void testSaveToStorage_StoresTheFileToInternalStorage_ShouldBeSavedToStorageWithoutError() {
		Assert.assertTrue(this.internalStorageWrapper
				.saveToStorage(this.alarmData));
	}
}
