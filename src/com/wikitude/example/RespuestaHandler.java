package com.wikitude.example;
import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Clase que maneja las respuesta de tipo filtrarPDIs cercanos por parte del servidor
 * @author Hansy
 *
 */
public class RespuestaHandler extends JsonHttpResponseHandler{	
	
	public RespuestaHandler(VisorInterface activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}
	
	@Override
	 public void onSuccess(JSONObject jObject){ 
		System.out.println(jObject.toString());
		try {
			
			String tipoRespuesta=jObject.get("codigo").toString();
			
			if(tipoRespuesta.equals("100")){
				Gson gson = new Gson();
				ControladorPDIs controlador = ControladorPDIs.getInstance();
				List<PuntoDeInteres> myTypes = null;
				myTypes = gson.fromJson(jObject.getString("mensaje"),
						new TypeToken<List<PuntoDeInteres>>() {
						}.getType());
				ArrayList<PuntoDeInteres> puntosDeInteres = (ArrayList<PuntoDeInteres>) myTypes;
				CalculadoraGeografica calculadora= CalculadoraGeografica.getInstance();
				calculadora.actualizarDistanciaLista(puntosDeInteres);
				controlador.setPuntosDeInteres(puntosDeInteres);
				controlador.actualizarJSONArrayPDIs();

				if (controlador.esUnaBusquedaAvanzada() || controlador.esUnaBusquedaSimple()) {
					SimpleARBrowserActivity arBrowser = (SimpleARBrowserActivity) activity;
					arBrowser.visualizarLista();
				}
				
				System.out.println(jObject.getString("onSuccess"));
				System.out.println(jObject.getString("mensaje"));
			}						
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		activity.loadSampleWorld();
		
	
	     
	 }   
	
	 @Override
	 public void onFailure(Throwable arg0){
	
		 Toast.makeText(((Activity)activity), "No hay servidor",
					Toast.LENGTH_LONG).show();
	 }
	 	 
	 private VisorInterface activity=null;
}
