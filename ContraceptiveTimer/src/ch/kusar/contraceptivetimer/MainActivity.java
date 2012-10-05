package ch.kusar.contraceptivetimer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TimePicker;
import ch.kusar.contraceptivetimer.wrapper.AlarmManagerWrapper;

//import android.view.MenuItem;
//import android.support.v4.app.NavUtils;
public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		// TODO Remove it when it goes live.
		AlarmManagerWrapper alarmManagerWrapper = new AlarmManagerWrapper();
		alarmManagerWrapper.SetAlarm();

		TimePicker tp = (TimePicker) this.findViewById(R.id.timePicker);
		tp.setIs24HourView(true);
	}

	// TODO: Remove Menu?!
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
