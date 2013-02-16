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

	 ControladorPDIs controlador= ControladorPDIs.getInstance();
	 ArrayList<PuntoDeInteres> puntosDeInteres=controlador.getPuntosDeInteres();
	String listaPDI[] = new String[puntosDeInteres.size()];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		for (int i = 0; i < puntosDeInteres.size(); i++) {
			listaPDI[i] = puntosDeInteres.get(i).getNombre();
		}
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaPDI));
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position,long id) {
		super.onListItemClick(list, view, position, id);
		PuntoDeInteres pdi = puntosDeInteres.get(position);
		Intent intent = new Intent(this, PoiDetailActivity.class);
		intent.putExtra("id", pdi.getId());
		this.startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_visualizar_lista, menu);
		return true;
	}

	
}
