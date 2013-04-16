package com.wikitude.example;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ImagenDePerfilHandler extends JsonHttpResponseHandler{		

		private Activity activity = null;		

		public ImagenDePerfilHandler(ActualizarPerfilActivity act) {
			this.activity = act;			
		}

		@Override
		public void onSuccess(JSONObject jObject) {		
			String tipoRespuesta;			
			try {
				tipoRespuesta = jObject.get("codigo").toString();
				if (tipoRespuesta.equals("100")) {																
					
					String urlImagen =  jObject.getString("mensaje");
					
					Sesion sesion = ControladorSesion.getInstance().getSesion();					
					sesion.setURLImagen(urlImagen);									
										
					new GestorServer().actualizaUsuarioEnServidor(sesion, (ActualizarPerfilActivity) activity);
				} else
					Toast.makeText(activity, jObject.getString("mensaje"), Toast.LENGTH_SHORT).show();
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		public void onFailure(Throwable arg0) {
			Toast.makeText(activity,
					"Hubo un problema con la conexion al servidor",
					Toast.LENGTH_SHORT).show();
		}				
				
	}
