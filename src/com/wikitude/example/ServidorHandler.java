package com.wikitude.example;


import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ServidorHandler extends JsonHttpResponseHandler{

	
	/**
	 * Constructor de la clase
	 * @param act Activity que manejara los mensajes del servidor
	 */
	public ServidorHandler(RespuestaInterface act){
		activity=act;
	}
	
	 @Override
	 /**
	  * Metodo que maneja los mensajes correctos recibidos del server
	  */
	 public void onSuccess(JSONObject jObject) {
         // Pull out the first event on the public timeline
		 activity.procesarRespuestaServidor(jObject);
		 
     }

		@Override
		/**
		 * Metodo que maneja los errores relacionados con el servidor
		 */
		public void onFailure(Throwable arg0, JSONObject arg1) {
			// TODO Auto-generated method stub
			super.onFailure(arg0, arg1);
			toast.mostrarMensaje("Hubo un problema con el servidor");
		}
    private ToastInterface toast;
	private RespuestaInterface activity;
}
