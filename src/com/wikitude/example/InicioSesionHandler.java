package com.wikitude.example;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

public class InicioSesionHandler extends JsonHttpResponseHandler  {
	
	public InicioSesionHandler(IniciarSesionActivity act) {
		// TODO Auto-generated constructor stub
		this.activity = act;
	}
	
    @Override
    public void onSuccess(JSONObject jObject) {
    	ControladorSesion contSesion = ControladorSesion.getInstance();
    	String tipoRespuesta;
		try {
			tipoRespuesta = jObject.get("codigo").toString();
		if(tipoRespuesta.equals("100")){
			contSesion.setSesionIniciada(true);
			Gson gson = new Gson();
			ControladorSesion controlador = ControladorSesion.getInstance();
			List<PuntoDeInteres> myTypes = null;
			myTypes = gson.fromJson(jObject.getString("objeto"),
					new TypeToken<List<PuntoDeInteres>>() {
					}.getType());
			ArrayList<PuntoDeInteres> puntosDeInteres = (ArrayList<PuntoDeInteres>) myTypes;
			controlador.getSesion().setMisPDI(puntosDeInteres);
			Toast.makeText(activity, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(activity, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
		}
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    public void onFailure(Throwable arg0){
    	Toast.makeText(activity, "Hubo un problema con la conexion al servidor",Toast.LENGTH_SHORT).show();
    }
    
    private Activity activity = null;
 }
