package com.wikitude.example;

import java.util.ArrayList;
import android.os.Bundle;
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

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		System.out.println("Entro al ListaPDIsActivity");
		ControladorPDIs controlador = ControladorPDIs.getInstance();
		pdiLista = controlador.getPuntosDeInteres();			
		setListAdapter(new ArrayAdapter<PuntoDeInteres>(this,
				android.R.layout.simple_list_item_1, pdiLista));
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

	private ArrayList<PuntoDeInteres> pdiLista=null;
	private String listaPDI[] = new String[50];
}
