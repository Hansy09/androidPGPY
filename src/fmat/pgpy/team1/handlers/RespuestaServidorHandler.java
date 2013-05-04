package fmat.pgpy.team1.handlers;


import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

import fmat.pgpy.team1.interfaces.RespuestaInterface;

public class RespuestaServidorHandler extends JsonHttpResponseHandler{

	
	/**
	 * Constructor de la clase
	 * @param act Activity que manejara los mensajes del servidor
	 */
	public RespuestaServidorHandler(RespuestaInterface act){
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
		//public void onFailure(Throwable arg0, JSONObject arg1) { A veces hacia que explote
			public void onFailure(Throwable arg0) {
			// TODO Auto-generated method stub
			//super.onFailure(arg0, arg1);
			activity.mostrarMensaje("Hubo un problema con el servidor");
		}
		
		
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
    	System.out.println("termino");
		progressDialog.dismiss();
			super.onFinish();
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			System.out.println("empiezo");
			progressDialog =ProgressDialog.show((Context) activity, "In progress", "Loading");
			super.onStart();
		}

	private ProgressDialog progressDialog;
	private RespuestaInterface activity;
}
