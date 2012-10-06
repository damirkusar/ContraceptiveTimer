package ch.kusar.contraceptivetimer.retriever;

import ch.kusar.contraceptivetimer.MainApplicationContext;
import ch.kusar.contraceptivetimer.businessobjects.AlarmEventData;
import ch.kusar.contraceptivetimer.calculator.AlarmCalculationData;
import ch.kusar.contraceptivetimer.calculator.AlarmDataCalculator;
import ch.kusar.contraceptivetimer.wrapper.InternalStorageWrapper;

public class AlarmEventRetriever {

	public AlarmEventData retrieveAlarmEventData() {
		InternalStorageWrapper internalStorageWrapper = new InternalStorageWrapper(MainApplicationContext.getAppContext());

		AlarmCalculationData alarmCalculationData = internalStorageWrapper.loadFromStorage();

		if (alarmCalculationData != null) {
			AlarmDataCalculator alarmDataCalculator = new AlarmDataCalculator(alarmCalculationData);
			return alarmDataCalculator.getNextAlarmEvent();
		}
		return null;
	}
}