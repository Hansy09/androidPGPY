package com.wikitude.example;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;



public class GestorServer {

	private String direccionBase="http://pgpy.dyndns-ip.com:8000";
	
	public void buscarDentroDeRangoMax(Posicion posicion, double rangoMaximo,VisorInterface visor){
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams rp = new RequestParams();
		
		rp.put("longitud", String.valueOf(posicion.getLongitud()));
		rp.put("latitud", String.valueOf(posicion.getLatitud()));
		rp.put("rangoMaximoAlcance", String.valueOf(rangoMaximo));
		client.post(direccionBase+"/geoAdds/pdi/lista/",rp, new RespuestaHandler(visor)); 		
		
	}
public void buscarPDIsPorCategoria(Posicion posicion, double rangoMaximo, String clave, String categoria, VisorInterface visor){
		
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("longitud", String.valueOf(posicion.getLongitud()));
		peticion.put("latitud", String.valueOf(posicion.getLatitud()));
		peticion.put("clave", clave);
		peticion.put("categoria", categoria);
		peticion.put("rangoMaximoAlcance", String.valueOf(rangoMaximo));
		httpClient.post(direccionBase+"/geoAdds/pdi/lista/",peticion, new RespuestaHandler(visor)); 		
		
	}
}

	

