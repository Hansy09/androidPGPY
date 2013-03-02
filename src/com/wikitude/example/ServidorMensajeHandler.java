package com.wikitude.example;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Clase que sirve para manejar los mensajes recibidos por las operaciones del servidor
 * @author Hansy
 *
 */
public class ServidorMensajeHandler extends JsonHttpResponseHandler{
	
	/**
	 * Constructor de la clase
	 * @param act Activity que manejara los mensajes del servidor
	 */
	public ServidorMensajeHandler(ToastInterface act){
		activity=act;
	}
	
	 @Override
	 /**
	  * Metodo que maneja la correcta conexion con el servidor
	  */
     public void onSuccess(JSONObject jObject) {
         // Pull out the first event on the public timeline
		 try {
				String tipoRespuesta=jObject.get("codigo").toString();
				if(tipoRespuesta.equals("100")){
					activity.mostrarMensaje("La peticion fue correcta");
				}else{
					activity.mostrarMensaje(jObject.get("mensaje").toString());
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     }

		@Override
		/**
		 * Metodo que maneja la incorrecta conexion con el servidor
		 */
		public void onFailure(Throwable arg0, JSONObject arg1) {
			// TODO Auto-generated method stub
			super.onFailure(arg0, arg1);
			activity.mostrarMensaje("Hubo un problema con el servidor");
		}
	private ToastInterface activity;
}
