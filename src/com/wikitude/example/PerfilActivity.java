package com.wikitude.example;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * 
 * @Autor SMD
 * @Fecha 01/03/2013
 * @Descripcion Clase encargada de la actividad del perfil
 *
 */
public class PerfilActivity extends Activity implements RespuestaInterface{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil);
		contAnuncio.obtenerAnunciosDePDI(contSesion.getSesion().getMisPDI().get(1).getId(), this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_perfil, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		switch (itemId) {
		case R.id.menu_RegistrarAnuncio:
			this.registrarAnuncio();
			break;
		case R.id.menu_VerAnunciosPDI:
			this.verAnuncios();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void registrarAnuncio() {
		Intent intent = new Intent(this, RegistrarAnuncioActivity.class);
		Log.d("log", "Mandando a registrar");
		intent.putExtra("idPDI", contSesion.getSesion().getMisPDI().get(numeroPDI).getId());
		startActivity(intent);
	}
	
	/**
	 * Muestra los anuncios segun el PDI que se haya seleccionado previamente, 
	 * con la variable numeroPDI
	 */
	public void verAnuncios(){
		Intent intent = new Intent(this, ListaMisAnunciosPDIActivity.class);
		intent.putExtra("idPDI", contSesion.getSesion().getMisPDI().get(numeroPDI).getId());
		startActivity(intent);		
	}
	
	@Override
	public void procesarRespuestaServidor(JSONObject jObject) {
		String tipoRespuesta;
		try {
			tipoRespuesta = jObject.get("codigo").toString();
		if(tipoRespuesta.equals("100")){
			Gson gson = new Gson();
			List<Anuncio> myTypes = null;
			myTypes = gson.fromJson(jObject.getString("objeto"),
					new TypeToken<List<Anuncio>>() {
					}.getType());
			ArrayList<Anuncio> anuncios = (ArrayList<Anuncio>) myTypes;
			contSesion.getSesion().getMisPDI().get(numeroPDI).setListaAnuncios(anuncios);
			Toast.makeText(this, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
		}
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private int numeroPDI = 1;
	private ControladorAnuncio contAnuncio = ControladorAnuncio.getInstance();
	private ControladorSesion contSesion = ControladorSesion.getInstance();

}
