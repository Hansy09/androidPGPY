package com.wikitude.example;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @Autor SMD
 * @Fecha 29/03/2013
 * @Descripcion Clase encargada de la actividad de mostrar
 * los anuncios de PDI en forma de lista, para actualizarlos o borrarlos
 *
 */
public class ListaMisAnunciosPDIActivity extends Activity implements RespuestaInterface, ToastInterface, ExisteFavoritoInterface{

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mis_anuncios_pdi);
        listView = (ListView) findViewById(R.id.listView1);
        Intent intent = getIntent();
        idPDI = intent.getIntExtra("idPDI", 0);
		contAnuncio.obtenerAnunciosDePDI(idPDI, this);
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
			ControladorPDIs controlador = ControladorPDIs.getInstance();
			ArrayList<PuntoDeInteres> pdiLista = controlador.getPuntosDeInteres();
			for(int i=0;i<pdiLista.size();i++){
				if(pdiLista.get(i).getId()==idPDI){
					controlador.getPuntosDeInteres().get(i).getListaAnuncios().remove(anuncio);
					pdi.getListaAnuncios().remove(anuncio);
				}
			}
			customAdapter.notifyDataSetChanged();
			mostrarMensaje(jObject.get("mensaje").toString());
		} else
			mostrarMensaje(jObject.get("mensaje").toString());
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void procesarExisteRespuestaServidor(JSONObject jObject) {
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
			ControladorPDIs controlador = ControladorPDIs.getInstance();
			ArrayList<PuntoDeInteres> pdiLista = controlador.getPuntosDeInteres();
			for(int i=0;i<pdiLista.size();i++){
				if(pdiLista.get(i).getId()==idPDI){
					pdi=pdiLista.get(i);
					controlador.getPuntosDeInteres().get(i).setListaAnuncios(anuncios);
				}
			}
			pdi.setListaAnuncios(anuncios);
	        customAdapter = new AdaptadorListAnuncios(pdi.getListaAnuncios(),idPDI, this);
	        listView.setAdapter(customAdapter);
			Toast.makeText(this, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
		}
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void mostrarMensaje(String mensaje) {
		Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();		
	}
	
	private ListView listView = null;
	private Anuncio anuncio = null;
	private int idPDI= 0;
	private PuntoDeInteres pdi = null;
	private ControladorAnuncio contAnuncio = ControladorAnuncio.getInstance();
	private AdaptadorListAnuncios customAdapter = null;
	private ControladorPDIs contPDI = ControladorPDIs.getInstance();
	
}
