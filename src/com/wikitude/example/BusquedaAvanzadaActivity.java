package com.wikitude.example;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class BusquedaAvanzadaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busqueda_avanzada);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_busqueda_avanzada, menu);
		return true;
	}

	public void realizaBusquedaAvanzada(View view) {
		Posicion posicion = new Posicion();
		posicion.setLatitud(SimpleARBrowserActivity.latitudActual);
		posicion.setLongitud(SimpleARBrowserActivity.longitudActual);
		double rangoMax = 15;
		EditText campoBusqueda = (EditText) findViewById(R.id.campoBusqueda);
		String clave = campoBusqueda.getText().toString();
		RadioButton porNombre = (RadioButton) findViewById(R.id.radio0);
		RadioButton porDescripcion = (RadioButton) findViewById(R.id.radio1);
		RadioButton porCategoria = (RadioButton) findViewById(R.id.radioButton1);
		RadioButton porDireccion = (RadioButton) findViewById(R.id.radio2);
		
		ControladorPDIs.getInstance().levantaLaFuckingBandera();
		
		if (porNombre.isChecked()) {
			ControladorPDIs.getInstance().filtrarPDIsPorCategorias(posicion,
					rangoMax, clave, "nombre", this);
			System.out.println("NOMBRE");			
		}
		if (porDescripcion.isChecked()) {
			ControladorPDIs.getInstance().filtrarPDIsPorCategorias(posicion,
					rangoMax, clave, "descripcion", SimpleARBrowserActivity.getInstance());
			System.out.println("DESCRIPCION");
		}
		if (porCategoria.isChecked()) {
			ControladorPDIs.getInstance().filtrarPDIsPorCategorias(posicion,
					rangoMax, clave, "categoria", SimpleARBrowserActivity.getInstance());
			System.out.println("CATEGORIA");
		}
		if (porDireccion.isChecked()) {
			ControladorPDIs.getInstance().filtrarPDIsPorCategorias(posicion,
					rangoMax, clave, "direccion", SimpleARBrowserActivity.getInstance());
			System.out.println("DIRECCION");
		}
	}
	

}
