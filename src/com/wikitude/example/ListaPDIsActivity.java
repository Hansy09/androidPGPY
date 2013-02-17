package com.wikitude.example;

import java.util.ArrayList;
import java.util.List;

import android.R.array;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Nombre: Santiago Martin 
 * Fecha: 5 Febrero 2013 
 * Descripcion: Actividad para
 * mostrar los PDI en forma de Lista
 */
public class ListaPDIsActivity extends ListActivity {

	private ArrayList<PuntoDeInteres> pdiLista=null;
	private final static float TEST_LATITUDE = 47.77318f;
	private final static float TEST_LONGITUDE = 13.069730f;
	private final static float TEST_ALTITUDE = 150;
	String listaPDI[] = new String[50];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ControladorPDIs controlador = ControladorPDIs.getInstance();
		pdiLista = controlador.getPuntosDeInteres();
		for (int i = 0; i < pdiLista.size(); i++) {
			listaPDI[i] = pdiLista.get(i).getNombre();
		}
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listaPDI));
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position,
			long id) {
		super.onListItemClick(list, view, position, id);
		PuntoDeInteres pdi = pdiLista.get(position);
		Intent intent = new Intent(this, PDIDetalle.class);
		intent.putExtra("id", String.valueOf(pdi.getId()));
		this.startActivity(intent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_visualizar_lista, menu);
		return true;
	}

	private double[] createRandLocation() {

		return new double[] { TEST_LATITUDE + ((Math.random() - 0.5) / 500),
				TEST_LONGITUDE + ((Math.random() - 0.5) / 500),
				TEST_ALTITUDE + ((Math.random() - 0.5) * 10) };
	}

}
