package com.wikitude.example;


import java.lang.reflect.Type;
import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * 
 * @Nombre SMD
 * @Fecha 01/03/2013
 * @Descripcion Clase encargada de conectar al server
 *
 */
public class GestorServer{

//	private String direccionBase = "http://192.168.1.67:8000/"; //Josue	
//	private String direccionBase = "http://192.168.1.83:8000/";  //Rusel
	private String direccionBase = "http://jd732.o1.gondor.io/"; 

	public void buscarDentroDeRangoMax(Posicion posicion, double rangoMaximo,
			VisorInterface visor) {
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams rp = new RequestParams();

		rp.put("longitud", String.valueOf(posicion.getLongitud()));
		rp.put("latitud", String.valueOf(posicion.getLatitud()));
		rp.put("rangoMaximoAlcance", String.valueOf(rangoMaximo));
		client.post(direccionBase + "/geoAdds/pdi/lista/", rp,
				new RespuestaHandler(visor));

	}

	public void buscarPDIsPorCategoria(Posicion posicion, double rangoMaximo,
			String clave, String categoria, VisorInterface visor) {		
		
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("longitud", String.valueOf(posicion.getLongitud()));
		peticion.put("latitud", String.valueOf(posicion.getLatitud()));
		peticion.put("rangoMaximoAlcance", "100");
		peticion.put("searchString", clave);
		peticion.put("categoria", categoria);  
		httpClient.post(direccionBase + "/geoAdds/pdi/categoria/", peticion,
				new RespuestaHandler(visor));		
		
		System.out.println("buscarPDIsPorCategoria");		
	}
	
	public void verificarInicioSesionEnServidor(Sesion sesion, IniciarSesionActivity act){
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("correo", String.valueOf( sesion.getCorreo()));
		peticion.put("contrasenia", String.valueOf( sesion.getContrasenia()));
		httpClient.post(direccionBase + "/geoAdds/usuario/iniciarSesion/", peticion, new InicioSesionHandler(act));
	}
	
	public void registrarUsuarioEnServidor(Sesion sesion, RegistrarUsuarioActivity act){
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("correo", String.valueOf( sesion.getCorreo()));
		peticion.put("contrasenia", String.valueOf( sesion.getContrasenia()));
		httpClient.post(direccionBase + "/geoAdds/usuario/registrar/", peticion, new RegistroUsuarioHandler(act));
	}
	
}
