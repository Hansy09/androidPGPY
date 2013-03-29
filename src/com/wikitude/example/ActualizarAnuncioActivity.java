package com.wikitude.example;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
		String descripcion = ((EditText) this.findViewById(R.id.editText1)).getText().toString();
		if(!descripcion.equals(anuncio.getDescripcion())){
			anuncio.setDescripcion(descripcion);
			contAnuncio.actualizarAnuncio(anuncio, idPDI, this);
		} else {
			Toast.makeText(this, "No se realizaron cambios", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void cargarDatosAnuncio() {
		Log.d("log","idPDI intent "+idPDI);
		ArrayList<PuntoDeInteres> puntosDeInteres = contSesion.getSesion().getMisPDI();
		for (int i = 0; i < puntosDeInteres.size(); i++) {
			if (puntosDeInteres.get(i).getId() == idPDI) {
				pdi = puntosDeInteres.get(i);
				Log.d("log","idPDI obtenido "+pdi.getId());
			}
		}
		Log.d("log","idAnuncio intent "+idAnuncio);
		ArrayList<Anuncio> anunciosPDI = pdi.getListaAnuncios();
		for (int i = 0; i < anunciosPDI.size(); i++) {
			if (anunciosPDI.get(i).getId() == idAnuncio) {
				anuncio = anunciosPDI.get(i);
				Log.d("log","idAnuncio obtenido "+anuncio.getId());
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
			contSesion.getSesion().getMisPDI().get(numeroPDI).getListaAnuncios().remove(anuncio);
			contSesion.getSesion().getMisPDI().get(numeroPDI).getListaAnuncios().add(anuncioActualizado);
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

	private int numeroPDI = 1;
	private int idPDI = 0;
	private int idAnuncio = 0;
	private ControladorSesion contSesion = ControladorSesion.getInstance();
	private ControladorAnuncio contAnuncio = ControladorAnuncio.getInstance();
	private Anuncio anuncio = new Anuncio();
	private PuntoDeInteres pdi = new PuntoDeInteres();
	
}
