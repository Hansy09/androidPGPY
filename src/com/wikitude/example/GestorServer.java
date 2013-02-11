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
	
	public void buscarDentroDeRangoMax(double longitud, double latitud, double rangoMaximo,VisorInterface visor){
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams rp = new RequestParams();
		
		rp.put("longitud", String.valueOf(longitud));
		rp.put("latitud", String.valueOf(latitud));
		rp.put("rangoMaximoAlcance", String.valueOf(rangoMaximo));
		client.post(direccionBase+"/geoAdds/pdi/lista/",rp, new RespuestaHandler(visor)); 		
		
	}
public void buscarPDIsPorCategoria(double longitud, double latitud, double rangoMaximo,ArrayList<String> categorias, VisorInterface visor){
		
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("longitud", String.valueOf(longitud));
		peticion.put("latitud", String.valueOf(latitud));
		peticion.put("categoria", categorias.get(0));
		peticion.put("rangoMaximoAlcance", String.valueOf(rangoMaximo));
		httpClient.post(direccionBase+"/geoAdds/pdi/lista/",peticion, new RespuestaHandler(visor)); 		
		
	}
}

	

