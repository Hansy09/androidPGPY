package com.wikitude.example;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;



public class DaoPDI {

	private static JSONObject respuesta=null;
	private static boolean existeRespuesta=false;
	private String direccionBase="http://192.168.228.126:8000";
	
	public void buscarDentroDeRangoMax(double rangoMaximo,VisorInterface visor){
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams rp = new RequestParams();
		rp.put("longitud", "-89.5883023738861");
		rp.put("latitud", "20.9564401410137");
		rp.put("rangoMaximoAlcance", "40");
		client.post(direccionBase+"/geoAdds/pdi/lista/",rp, new RespuestaHandler(visor)); 		
		
	}
}

	

