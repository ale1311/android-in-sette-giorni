package com.androidinsettegiorni_cap5;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.androidinsettegiorni_cap5.task.NetworkAsynckTask;

public class MainActivity extends Activity {

	private TextView ipView, providerView, ispView, locationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ipView = (TextView) findViewById(R.id.current_ip);
		providerView = (TextView) findViewById(R.id.current_provider);
		ispView = (TextView) findViewById(R.id.current_isp);
		locationView = (TextView) findViewById(R.id.current_localtion);

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

	public void calculate_ip(View v) {

		new NetworkAsynckTask(this, ipView, providerView, ispView, locationView).execute();
	}
}
