package ch.kusar.contraceptivetimer.test.wrapper;

import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import ch.kusar.contraceptivetimer.businessobjects.AlarmData;
import ch.kusar.contraceptivetimer.businessobjects.ContraceptiveType;
import ch.kusar.contraceptivetimer.wrapper.InternalStorageWrapper;

public class InternalStorageWrapperTest extends AndroidTestCase {

	private AlarmData alarmData;
	private Calendar alarmTime;
	private Calendar calendar;
	private InternalStorageWrapper internalStorageWrapper;
	private Calendar startTime;

	@Override
	public void setUp() throws Exception {
		this.calendar = new GregorianCalendar();
		this.startTime = this.calendar;
		this.alarmTime = this.calendar;

		this.internalStorageWrapper = new InternalStorageWrapper(this
				.getContext().getApplicationContext());

		this.alarmData = new AlarmData();
		this.alarmData.setStartTime(this.startTime);
		this.alarmData
				.setContraceptiveType(ContraceptiveType.CONTRACEPTION_RING);
		this.alarmData.setAlarmTime(this.alarmTime);
	}

	@Override
	public void tearDown() throws Exception {
		this.alarmData = null;
		this.internalStorageWrapper = null;
	}

	public void testLoadFromStorage_LoadsStoredFileIntoObject_ShouldLoadTheSavedDataCorrectly() {
		this.internalStorageWrapper.saveToStorage(this.alarmData);
		AlarmData alarmDataLoaded = this.internalStorageWrapper
				.loadFromStorage();

		Assert.assertEquals(this.startTime, alarmDataLoaded.getStartTime());
		Assert.assertEquals(this.alarmTime, alarmDataLoaded.getAlarmTime());
		Assert.assertEquals(this.alarmData.getContraceptiveType(),
				ContraceptiveType.CONTRACEPTION_RING);
		Assert.assertEquals(21, alarmDataLoaded.getIntervalDays());
	}

	public void testSaveToStorage_StoresTheFileToInternalStorage_ShouldBeSavedToStorageWithoutError() {
		Assert.assertTrue(this.internalStorageWrapper
				.saveToStorage(this.alarmData));
	}
}
