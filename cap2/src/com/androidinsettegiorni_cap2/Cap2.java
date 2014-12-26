package com.androidinsettegiorni_cap2;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Cap2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cap2);

		/*
		 * Impostiamo le diverse informazioni dell' Action Bar
		 */

		ActionBar bar = getActionBar();
		bar.setTitle(R.string.action_bar_name);
		bar.setSubtitle(R.string.action_bar_subtitle);
		bar.setDisplayUseLogoEnabled(true);
		bar.setDisplayShowTitleEnabled(true);

		// Impostazione background dell'action bar
		Resources r = getResources();
		Drawable drawableBackground = r.getDrawable(R.drawable.ab_stacked_solid_112);
		bar.setBackgroundDrawable(drawableBackground);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cap2, menu);
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

	private ArrayList<ElementoGrafico> creazioneElementi() {

		ArrayList<ElementoGrafico> listElement = new ArrayList<ElementoGrafico>();

		return listElement;
	}
}
