package com.wikitude.example;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @Autor SMD
 * @Fecha 29/03/2013
 * @Descripcion Clase encargada de la actividad para actualizar anuncios
 *
 */
public class ActualizarAnuncioActivity extends Activity implements RespuestaInterface, ToastInterface{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualizar_anuncio);
		Intent intent = getIntent();
		idPDI = intent.getIntExtra("idPDI", 0);
		idAnuncio = intent.getIntExtra("idAnuncio", 0);
		this.cargarDatosAnuncio();
	}
	
	public void onActualizarAnuncio(View view){
		String titulo = ((TextView)this.findViewById(R.id.textView2)).getText().toString();
		String categoria = ((TextView)this.findViewById(R.id.textView4)).getText().toString();
		String descripcion = ((EditText) this.findViewById(R.id.editText1)).getText().toString();
		if(contAnuncio.confirmarCamposObligatorios(titulo, categoria, descripcion)){
		if(!descripcion.equals(anuncio.getDescripcion())){
			anuncio.setDescripcion(descripcion);
			contAnuncio.actualizarAnuncio(anuncio, idPDI, this);
		} else {
			Toast.makeText(this, "No se realizaron cambios", Toast.LENGTH_SHORT).show();
		}
		} else Toast.makeText(this, "No puede dejar campos vacios", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Carga los datos del anuncio para mostrarlos dentro de la activity
	 */
	private void cargarDatosAnuncio() {
		ArrayList<PuntoDeInteres> puntosDeInteres = contPDI.getPuntosDeInteres();
		for (int i = 0; i < puntosDeInteres.size(); i++) {
			if (puntosDeInteres.get(i).getId() == idPDI) {
				pdi = puntosDeInteres.get(i);
			}
		}
		ArrayList<Anuncio> anunciosPDI = pdi.getListaAnuncios();
		for (int i = 0; i < anunciosPDI.size(); i++) {
			if (anunciosPDI.get(i).getId() == idAnuncio) {
				anuncio = anunciosPDI.get(i);
			}
		}
		
		if(anuncio !=null){
			((TextView) this.findViewById(R.id.textView2)).setText(anuncio.getTitulo());
			((TextView) this.findViewById(R.id.textView4)).setText(anuncio.getCategoria());
			((EditText) this.findViewById(R.id.editText1)).setText(anuncio.getDescripcion());
		}
		
	}
	
	@Override
	public void procesarRespuestaServidor(JSONObject jObject) {
		String tipoRespuesta;
		try {
			tipoRespuesta = jObject.get("codigo").toString();
		if(tipoRespuesta.equals("100")){
			Gson gson = new Gson();
			Anuncio anuncioActualizado=  gson.fromJson(jObject.getString("objeto"),Anuncio.class);
			pdi.getListaAnuncios().remove(anuncio);
			pdi.getListaAnuncios().add(anuncioActualizado);
			mostrarMensaje(jObject.get("mensaje").toString());
		} else
			mostrarMensaje(jObject.get("mensaje").toString());
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void mostrarMensaje(String mensaje) {
		Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
		
	}

	private int idPDI = 0;
	private int idAnuncio = 0;
	private ControladorPDIs contPDI = ControladorPDIs.getInstance();
	private ControladorAnuncio contAnuncio = ControladorAnuncio.getInstance();
	private Anuncio anuncio = new Anuncio();
	private PuntoDeInteres pdi = new PuntoDeInteres();
	
}
