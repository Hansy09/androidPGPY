package com.wikitude.example;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * 
 * Nombre: Santiago Martin
 * Fecha: 5 Febrero 2013
 * Descripcion: Activity donde se obtiene un parametro de busqueda
 * para buscar entre los PDI cercanos
 */
public class BusquedaSimpleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the message from the intent
		Intent intent = getIntent();
		String fraseBuscar = intent
				.getStringExtra(SimpleARBrowserActivity.MENSAJE_BUSQUEDA);
		TextView textView = new TextView(this);
		textView.setTextSize(20);
		textView.setText("Buscando: " + fraseBuscar);
		setContentView(textView);
		// getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_busqueda_simple, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
