package com.wikitude.example;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

public class PerfilUsuarioHandler extends JsonHttpResponseHandler {

	private Activity activity = null;

	public PerfilUsuarioHandler(PerfilActivity act) {
		this.activity = act;
	}

	@Override
	public void onSuccess(JSONObject jObject) {		
		String tipoRespuesta;
		System.out.println("PerfilUsuarioHandler:\n"+jObject);
		try {
			tipoRespuesta = jObject.get("codigo").toString();
			if (tipoRespuesta.equals("100")) {								
				Toast.makeText(activity, "Cargando perfil",Toast.LENGTH_SHORT).show();				
				
				JSONObject objeto = (JSONObject) jObject.get("objeto");
				
				Sesion sesion = ControladorSesion.getInstance().getSesion();
				sesion.setNombre(objeto.getString("nombre"));									
				sesion.setApellido(objeto.getString("apellido"));
				sesion.setCorreo(objeto.getString("correo electronico"));
				sesion.setURLImagen(objeto.getString("URLimagen"));
				
				establecerDatosDePerfilEnLayout();
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
	
	private void establecerDatosDePerfilEnLayout(){
		PerfilActivity perfilActivity = (PerfilActivity) this.activity;		
		
		TextView elNombreDelUsuario = (TextView) perfilActivity.findViewById(R.id.textView2);
		TextView elApellidoDelUsuario = (TextView) perfilActivity.findViewById(R.id.textView4);
		TextView elCorreoDelUsuario = (TextView) perfilActivity.findViewById(R.id.textView6);
		ImageView imagenDePerfil = (ImageView) perfilActivity.findViewById(R.id.imageView1);				
		
		Sesion sesion = ControladorSesion.getInstance().getSesion();
		
		elNombreDelUsuario.setText(sesion.getNombre());
		
		elApellidoDelUsuario.setText(sesion.getApellido());
		
		elCorreoDelUsuario.setText(sesion.getCorreo());
		
		int loader = R.drawable.images;			
		String image_url = sesion.getURLImagenDelUsuario();
		ImageLoader imgLoader = new ImageLoader(perfilActivity.getApplicationContext());
		imgLoader.DisplayImage(image_url, loader, imagenDePerfil);
	}
}
