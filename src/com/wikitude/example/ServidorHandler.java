package com.wikitude.example;


import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ServidorHandler extends JsonHttpResponseHandler{


	/**
	 * Constructor de la clase
	 * @param act Activity que manejara los mensajes del servidor
	 */
	public ServidorHandler(RespuestaInterface act){
		this.activity=act;
		Log.d("log", "constructor de handler");
	}

	 /**
	  * Metodo que maneja los mensajes correctos recibidos del server
	  */
	 @Override
	 public void onSuccess(JSONObject jObject) {
         // Pull out the first event on the public timeline
		 Log.d("log", "success con servidor");
		 activity.procesarRespuestaServidor(jObject);

     }

	 /**
		 * Metodo que maneja los errores relacionados con el servidor
		 */
		@Override
		public void onFailure(Throwable arg0) {
			// TODO Auto-generated method stub
			Log.d("log", "failure con servidor"+arg0);
			toast.mostrarMensaje("Hubo un problema con el servidor");
		}
    private ToastInterface toast;
	private RespuestaInterface activity=null;
}