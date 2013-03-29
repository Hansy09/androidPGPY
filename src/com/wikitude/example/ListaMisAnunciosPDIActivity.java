package com.wikitude.example;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * @Autor SMD
 * @Fecha 29/03/2013
 * @Descripcion Clase encargada de la actividad de mostrar
 * los anuncios de PDI en forma de lista, para actualizarlos o borrarlos
 *
 */
public class ListaMisAnunciosPDIActivity extends Activity implements RespuestaInterface, ToastInterface{

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mis_anuncios_pdi);
        ListView listView = (ListView) findViewById(R.id.listView1);
        Intent intent = getIntent();
        idPDI = intent.getIntExtra("idPDI", 0);
        int pdi = 1;
        customAdapter = new AdaptadorListAnuncios(contSesion.getSesion().getMisPDI().get(pdi).getListaAnuncios(),idPDI, this);
        listView.setAdapter(customAdapter);
    }
	
	
	public void onBorrarAnuncio(Anuncio anuncio, int id) {
		this.anuncio = anuncio;
		contAnuncio.eliminarAnuncio(id, idPDI, this);
	}
	
	@Override
	public void procesarRespuestaServidor(JSONObject jObject) {
		String tipoRespuesta;
		try {
			tipoRespuesta = jObject.get("codigo").toString();
		if(tipoRespuesta.equals("100")){
			contSesion.getSesion().getMisPDI().get(numeroPDI).getListaAnuncios().remove(anuncio);
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
	
	private Anuncio anuncio = null;
	private int numeroPDI = 1;
	private int idPDI= 0;
	private ControladorAnuncio contAnuncio = ControladorAnuncio.getInstance();
	private AdaptadorListAnuncios customAdapter = null;
	private ControladorSesion contSesion = ControladorSesion.getInstance();
	
}
