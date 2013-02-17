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

	private ArrayList<PoiBean> pdiLista;
	private final static float TEST_LATITUDE = 47.77318f;
	private final static float TEST_LONGITUDE = 13.069730f;
	private final static float TEST_ALTITUDE = 150;
	String listaPDI[] = new String[50];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pdiLista = new ArrayList<PoiBean>();
		for (int i = 0; i < 50; i++) {
			double[] location = createRandLocation();
			PoiBean bean = new PoiBean(
					"" + i,
					"POI #" + i,
					"Probably one of the best POIs you have ever seen. This is the description of Poi #"
							+ i, (int) (Math.random() * 3), location[0],
					location[1], location[2]);
			pdiLista.add(bean);
			listaPDI[i] = pdiLista.get(i).getName();
		}
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listaPDI));
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position,
			long id) {
		super.onListItemClick(list, view, position, id);
		PoiBean bean = pdiLista.get(position);
		Intent intent = new Intent(this, PoiDetailActivity.class);
		intent.putExtra("POI_NAME", bean.getName());
		intent.putExtra("POI_DESC", bean.getDescription());
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
