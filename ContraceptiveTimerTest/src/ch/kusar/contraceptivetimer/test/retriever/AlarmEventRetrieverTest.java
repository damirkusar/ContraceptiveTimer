package ch.kusar.contraceptivetimer.test.retriever;

import android.test.AndroidTestCase;
import ch.kusar.contraceptivetimer.businessobjects.AlarmEventData;
import ch.kusar.contraceptivetimer.retriever.AlarmEventRetriever;

public class AlarmEventRetrieverTest extends AndroidTestCase {

	private AlarmEventRetriever alarmEventRetriever;

	@Override
	public void setUp() {
		this.alarmEventRetriever = new AlarmEventRetriever();
	}

	@Override
	public void tearDown() {

	}

	public void testRetrieveAlarmEventData_WithValidData_ShouldReturnExpectedObjectData() {
		AlarmEventData alarmEventData = this.alarmEventRetriever
				.retrieveAlarmEventData();

	}

}
