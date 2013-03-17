package com.wikitude.example;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ServidorHandler extends JsonHttpResponseHandler{

	
	/**
	 * Constructor de la clase
	 * @param act Activity que manejara los mensajes del servidor
	 */
	public ServidorHandler(Activity act){
		activity=(RespuestaInterface) act;
		toast=(ToastInterface) act;
	}
	
	 @Override
	 /**
	  * Metodo que maneja los mensajes correctos recibidos del server
	  */
	 public void onSuccess(JSONObject jObject) {
         // Pull out the first event on the public timeline
		 try {
				String tipoRespuesta=jObject.get("codigo").toString();
				if(tipoRespuesta.equals("100")){
					toast.mostrarMensaje(jObject.get("mensaje").toString());
					activity.procesarRespuestaServidor(jObject);
				}else{
					toast.mostrarMensaje(jObject.get("mensaje").toString());
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
