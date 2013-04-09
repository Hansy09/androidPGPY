package com.wikitude.example;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;


/**
 * 
 * @Autor SMD
 * @Fecha 29/03/2013
 * @Descripcion Clase encargada de la actividad de registrar anuncio
 *
 */
public class RegistrarAnuncioActivity extends Activity implements RespuestaInterface, ToastInterface{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrar_anuncio);
		Spinner spinner_categorias = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.categoriasAnuncio , android.R.layout.simple_spinner_item);
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_categorias.setAdapter(spinner_adapter);
	}
	
	public void onRegistrarAnuncio(View view){
		
		Intent intent = getIntent();
		idPDI = intent.getIntExtra("idPDI", 0);
		EditText editText = (EditText) findViewById(R.id.editText1);
		String titulo = editText.getText().toString();
		Spinner mySpinner = (Spinner)findViewById(R.id.spinner1);
		int catego=(int) mySpinner.getSelectedItemId()+1;
		String categoria=String.valueOf(catego);
		EditText editText2 = (EditText) findViewById(R.id.editText2);
		String descripcion = editText2.getText().toString();;
		
		if(controladorAnuncio.confirmarCamposObligatorios(titulo, categoria, descripcion)){
			anuncio = new Anuncio();
			anuncio.setTitulo(titulo);
			anuncio.setCategoria(categoria);
			anuncio.setDescripcion(descripcion);
			Log.d("log", "Mandando a registrar");
			controladorAnuncio.registrarAnuncio(anuncio, idPDI, this);
		}
		else Toast.makeText(this, "No puede dejar campos vacios", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void mostrarMensaje(String mensaje) {
	    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void procesarRespuestaServidor(JSONObject jObject) {
		String tipoRespuesta;
		try {
			tipoRespuesta = jObject.get("codigo").toString();
		if(tipoRespuesta.equals("100")){
			Log.d("log", "Entro a 100 registrar");
			Gson gson = new Gson();
			Anuncio anuncioRegistrado=  gson.fromJson(jObject.getString("objeto"),Anuncio.class);
			contSesion.getSesion().getMisPDI().get(numeroPDI).getListaAnuncios().add(anuncioRegistrado);
			mostrarMensaje(jObject.get("mensaje").toString());
			controladorAnuncio.enviarNotificacion(idPDI, anuncio, this);
		} else
			mostrarMensaje(jObject.get("mensaje").toString());
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private int numeroPDI = 1;
	private int idPDI = 0;
	private ControladorSesion contSesion = ControladorSesion.getInstance();
	private ControladorAnuncio controladorAnuncio = ControladorAnuncio.getInstance();
	private Anuncio anuncio = null;
}
