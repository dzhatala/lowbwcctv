package com.example.cmservice;

import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		start = (Button) findViewById(R.id.startButton);
		start.setOnClickListener(new View.OnClickListener() {
			// @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startCSService();
				timerHandler.postDelayed(timerRunnable, 5000);
			}
		});
		text1 = (TextView) findViewById(R.id.textView1);

		// AUTOMATIC CaAPTURE
		// timerHandler.postDelayed(timerRunnable, 5000);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void startCSService() {
		Intent front_translucent = new Intent(getApplication()
				.getApplicationContext(), CameraService.class);
		// front_translucent.putExtra("Front_Request", true);
		front_translucent.putExtra("Front_Request", isFront);
		// front_translucent.putExtra("Quality_Mode",
		// camCapture.getQuality());

		// zoel
		front_translucent.putExtra("Quality_Mode", 10);
		getApplication().getApplicationContext()
				.startService(front_translucent);
		Toast.makeText(getApplicationContext(), "Camera Service started",
				Toast.LENGTH_LONG).show();
	}

	private long counter = 0;
	Handler timerHandler = new Handler();
	Runnable timerRunnable = new Runnable() {
		@Override
		public void run() {
			// other stuff
			counter++;
			CheckBox cf = (CheckBox) findViewById(R.id.checkFront);
			isFront = cf.isChecked();
			EditText em = (EditText) findViewById(R.id.editMinutes);
			try {
				minutes = Integer.parseInt(em.getText().toString());
			} catch (NumberFormatException nfe) {
				minutes = MINUTE_DEFAULT;
			}
			text1.setText("c#:" + counter + " m#:" + minutes + " fr#:"
					+ isFront);
			startCSService();
			// reschedule
			timerHandler.postDelayed(this, 60 * 1000 * minutes);

		}
	};

	int minutes = 2;
	final int MINUTE_DEFAULT = 2;
	TextView text1;
	Button start;
	boolean isFront = false;

}
