package ch.kusar.contraceptivetimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeScreenActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_homescreen);
	}

	public void onButtonReadyToStartClicked(View view) {
		Intent intent = new Intent(this, AlarmScreenActivity.class);
		this.startActivity(intent);
	}
}
